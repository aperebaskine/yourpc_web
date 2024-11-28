<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<%@ page import="com.pinguela.yourpc.model.Customer, com.pinguela.yourpc.web.constants.Parameters" %>
<%
	
%>
<ul class="user-menu">
	<li>
		<form id="localeForm" action='${currentUrl}' method="post" onchange="submit();">
			<select name="<%=Parameters.SWITCH_LOCALE%>">
				<option>en-GB</option>
				<option>es-ES</option>
			</select>
		</form>
	</li>
	<c:choose>
		<c:when test="${not empty customer}">
			<span><c:out value="Hola ${customer.firstName}"></c:out></span>
			<li><a href='<c:url value="/user/index.jsp"></c:url>'>Mi cuenta</a></li>
		</c:when>
		<c:otherwise>
			<li><a href='<c:url value="/login.jsp"></c:url>'>Iniciar sesión</a></li>
			<li>Registrarse</li>
		</c:otherwise>
	</c:choose>
	<li>Carrito</li>
</ul>