<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
	<form action="<c:url value='/DefaultServlet'></c:url>" method="post">
		<input type="hidden" name="<%=Parameters.ACTION%>" value="<%=Actions.PRODUCT_SEARCH%>" />
		<label for="name">Nombre:</label> 
		<input type="text" name="name" placeholder="Ejemplo: Placa base" /> 
		<input type="submit" value="Buscar" />
	</form>
<%@ include file="/common/footer.jsp" %>