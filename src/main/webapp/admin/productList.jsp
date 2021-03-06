<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	
	<h1>Product List</h1>
	<div>
		<table border="1">
			<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Category</th>
					<th>Price</th>
					<th>Stock</th>
					<th>Image</th>
					<th>Edit</th>
					<th>Delete</th>
			</tr>
			<c:forEach var="product" items="${products}">
				<tr>
					<td>${product.id}</td>
					<td>${product.name}</td>
					<td>${product.category.name}</td>
					<td>${product.price}</td>
					<td>${product.stock.quantity}</td>
					<td><c:if test="${not empty product.imageUrl}"><img src="${product.imageUrl}"/></c:if></td>
					<td><a href="/admin/products/${product.id}">Edit</a></td>
					<td>
						<form:form action="/admin/products/${product.id}" method="delete">
							<input type="submit" value="Delete"/>
						</form:form>
					</td>
				</tr>
			</c:forEach>
		</table>
		
	</div>
	
	<br/>
	<a href="addProduct">Add New Product</a>
</body>
</html>