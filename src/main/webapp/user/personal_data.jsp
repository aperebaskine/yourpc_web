<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<h2>Introducir datos personales</h2>
<form id="detailsForm" class="baseForm" action="<%=request.getContextPath()%>/user/address.jsp" method="post">
	<fieldset>
		<legend>Datos personales</legend>
		<label for="firstName">Nombre:</label>
		<input type="text" id="firstName" name="firstName" required>
		<label for="lastName1">Primer apellido:</label>
		<input type="text" id="lastName1" name="lastName1" required>
		<label for="lastName2">Segundo apellido:</label>
		<input type="text" id="lastName2" name="lastName2">
		<label for="tel">Teléfono:</label>
		<input type="tel" id="tel" name="tel" required>
		<label for="email">Email:</label>
		<input type="email" id="email" name="email" value="${sessionScope.customer.email}" disabled required>
		<label for="aboutMe">Sobre mi:</label>
		<textarea id="aboutMe" name="aboutMe" form="detailsForm"></textarea>
	</fieldset>
	<fieldset>
		<legend>Documento de identidad</legend>
		<label for="doctype">Tipo de documento</label>
		<select id="doctype" name="doctype">
			<option value="NIF">NIF</option>
			<option value="NIE">NIE</option>
			<option value="PPT">Pasaporte</option>
		</select>
		<label for="docNumber">Número de documento:</label>
		<input type="text" id="docNumber" name="docNumber" required>
	</fieldset>
	<input type="submit" value="Siguiente">
	<input type="reset" value="Limpiar">
</form>
<%@ include file="/common/footer.jsp"%>