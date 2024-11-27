<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<div class="wizardProgress">
	<h3>Completa su perfil</h3>
	<progress value="0" max="100"></progress>
	<div class="wizardSteps">
		<p>Datos personales</p>
		<p>Dirección</p>
		<p>Métodos de pago</p>
	</div>
</div>
<h2 class="wizardFormTitle">Introducir datos personales</h2>
<div class="wizardForm">
	<form id="detailsForm" class="baseForm" action="<%=request.getContextPath()%>/user/address.jsp" method="post">
	<fieldset>
		<legend>Datos personales</legend>
		<div class="formElement">
			<label class="formElementLabel" for="firstName">Nombre:</label>
			<input type="text" id="firstName" name="firstName" required>
		</div>
		<div class="formElement">
			<label class="formElementLabel" for="lastName1">Primer apellido:</label>
			<input type="text" id="lastName1" name="lastName1" required>
		</div>
		<div class="formElement">
			<label class="formElementLabel" for="lastName2">Segundo apellido:</label>
			<input type="text" id="lastName2" name="lastName2">
		</div>
		<div class="formElement">
			<label class="formElementLabel" for="tel">Teléfono:</label>
			<input type="tel" id="tel" name="tel" required>
		</div>
		<div class="formElement">
			<label class="formElementLabel" for="email">Email:</label>
			<input type="email" id="email" name="email" value="${sessionScope.customer.email}" disabled required>
		</div>
		<div class="formElement">
			<label class="formElementLabel" for="aboutMe">Sobre mi:</label>
			<textarea style="width: 240px; height: 80px;" id="aboutMe" name="aboutMe" form="detailsForm"></textarea>
		</div>
	</fieldset>
	<fieldset>
		<legend>Documento de identidad</legend>
		<div class="formElement">
			<label class="formElementLabel" for="doctype">Tipo de documento</label>
			<select id="doctype" name="doctype">
				<option value="NIF">NIF</option>
				<option value="NIE">NIE</option>
				<option value="PPT">Pasaporte</option>
			</select>
		</div>
		<div class="formElement">
			<label class="formElementLabel" for="docNumber">Número de documento:</label>
			<input type="text" id="docNumber" name="docNumber" required>
		</div>
	</fieldset>
	<div class="formSubmitElement">
		<input type="reset" value="Limpiar">
		<input type="submit" value="Siguiente">
	</div>
</form>
</div>
<%@ include file="/common/footer.jsp"%>