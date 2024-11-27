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

<h2 class="wizardFormTitle">Introducir dirección</h2>
<div class="wizardForm">
	<form action="<%=request.getContextPath()%>/user/next-address.jsp" method="post">
		<div class="formElement">
			<label class="formElementLabel" for="streetName">Calle:</label>
			<input type="text" id="streetName" name="streetName" required>
		</div>
		<div class="inlineFormElements">
			<div class="smallFormElement">
				<label class="formElementLabel" for="streetNumber">Número:</label>
				<input type="number" id="streetNumber" name="streetNumber">
			</div>
			<div class="smallFormElement">
				<label class="formElementLabel" for="floor">Piso:</label>
				<input type="number" id="floor" name="floor">
			</div>
			<div class="smallFormElement">
				<label class="formElementLabel" for="door">Puerta:</label>
				<input type="text" id="door" name="door">
			</div>
		</div>
		<div class="mediumFormElement">
			<label class="formElementLabel" for="zipCode">Código postal:</label>
			<input type="number" id="zipCode" name="zipCode">
		</div>
		<div class="formElement">
			<label for="city">Ciudad:</label>
			<input type="text" id="city" name="city">
		</div>
		<div class="formElement">
			<label for="province">Provincia:</label>
			<input type="text" id="province" name="province">
		</div>
		<div class="formElement">
			<label for="country">País:</label>
			<select name="country" id="country">
				<c:forEach var="country" items="${countries}">
					<option value="${country.id}">${country.name}</option>
				</c:forEach>
			</select>
		</div>
		<input type="submit" value="Añadir">
		<input type="reset" value="Limpiar">
	</form>
</div>
<%@ include file="/common/footer.jsp"%>