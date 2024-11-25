<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<% 
	CountryService service = new CountryServiceImpl();
	request.setAttribute("countries", service.findAll());
%> 
<%@ page import="com.pinguela.yourpc.service.CountryService" %>
<%@ page import="com.pinguela.yourpc.service.impl.CountryServiceImpl" %>
<%@ page import="com.pinguela.yourpc.web.util.LocaleUtils" %>
<%@ include file="/common/header.jsp"%>
<h2>Introducir dirección</h2>
<form class="baseForm" action="<%=request.getContextPath()%>/user/next-address.jsp" method="post">
	<label for="streetName">Calle:</label>
	<input type="text" id="streetName" name="streetName" required>
	<label for="streetNumber">Número:</label>
	<input type="number" id="streetNumber" name="streetNumber">
	<label for="floor">Piso:</label>
	<input type="number" id="floor" name="floor">
	<label for="door">Puerta:</label>
	<input type="text" id="door" name="door">
	<label for="zipCode">Código postal:</label>
	<input type="number" id="zipCode" name="zipCode">
	<label for="city">Ciudad:</label>
	<input type="text" id="city" name="city">
	<label for="province">Provincia:</label>
	<input type="text" id="province" name="province">
	<label for="country">País:</label>
	<select name="country" id="country">
		<c:forEach var="country" items="${countries}">
			<option value="${country.id}">${country.name}</option>
		</c:forEach>
	</select>
	<input type="submit" value="Añadir">
	<input type="reset" value="Limpiar">
</form>
<%@ include file="/common/footer.jsp"%>