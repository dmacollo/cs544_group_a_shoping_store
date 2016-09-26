package mum.edu.webstore.controller;

import mum.edu.webstore.model.Customer;
import mum.edu.webstore.model.User;
import mum.edu.webstore.service.CustomerService;
import mum.edu.webstore.service.SecurityService;
import mum.edu.webstore.service.UserService;
import mum.edu.webstore.validator.CustomerValidator;
import mum.edu.webstore.validator.UserValidator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Controller
public class UserController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private UserService userService;
	private Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private SecurityService securityService;
	@Autowired
	private CustomerValidator customerValidator;
	@Autowired
	private UserValidator userValidator;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("customerForm", new Customer());

		return "register";
	}

	
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("customerForm") Customer customerForm, BindingResult bindingResult, Model model) {
		customerValidator.validate(customerForm, bindingResult);
		// model.addAttribute(bindingResult.getFieldError());
		
		log.info(customerForm);
		if (bindingResult.hasErrors()) {
			log.error("Customer Form Invalid");
			return "register";
		}
		User user = customerService.getUser(customerForm);
		userValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			log.error("User Form Invalid");
			return "register";
		}
		userService.save(user);
		customerForm.setUser(user);
		customerService.save(customerForm);
		securityService.autologin(customerForm.getEmail(), customerForm.getPassword());
		return "redirect:/index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}
}
