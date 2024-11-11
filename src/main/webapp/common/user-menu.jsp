<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<%@ page import="com.pinguela.yourpc.model.Customer" %>
<ul class="user-menu">
	<c:choose>
		<c:when test="${not empty customer}">
			<c:out value="Hola ${customer.firstName}"></c:out>
			<li><a href='<c:url value="/user/index.jsp"></c:url>'>Mi cuenta</a></li>
		</c:when>
		<c:otherwise>
			<li><a href='<c:url value="/login.jsp"></c:url>'>Iniciar sesión</a></li>
			<li>Registrarse</li>
		</c:otherwise>
	</c:choose>
	<li>Carrito</li>
</ul>