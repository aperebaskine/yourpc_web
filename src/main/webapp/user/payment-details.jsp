<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<div class="wizardProgress">
	<h3>Completa su perfil</h3>
	<progress value="67" max="100"></progress>
	<div class="wizardSteps">
		<p>Datos personales</p>
		<p>Dirección</p>
		<p>Métodos de pago</p>
	</div>
</div>
<h2 class="wizardFormTitle">Introducir métodos de pago</h2>
<div class="wizardForm">
	<form class="baseForm" action="<%=request.getContextPath()%>/user/index.jsp" method="post">
	<fieldset>
		<legend>Banco</legend>
		<div class="formElement">
			<label class="formElementLabel" for="accountHolder">Titular de la cuenta:</label>
			<input type="text" id="accountHolder" name="accountHolder" required>
		</div>
		<div class="longFormElement">
			<label class="formElementLabel" for="iban">IBAN:</label>
			<input type="text" id="iban" name="iban" required>
		</div>
		<div class="formElement">
			<label class="formElementLabel" for="bic">BIC:</label>
			<input type="text" id="bic" name="bic" style="width: 128px;" required>
		</div>
	</fieldset>
	<fieldset>
		<legend>Tarjeta de crédito</legend>
		<div class="formElement">
			<label class="formElementLabel" for="cardHolder">Titular de la tarjeta:</label>
			<input type="text" id="cardHolder" name="cardHolder" required>
		</div>
		<div class="formElement">
			<label class="formElementLabel" for="cardNumber">Número de tarjeta</label>
			<input type="number" id="cardNumber" name="cardNumber" required>
		</div>
		<div class="inlineFormElements">
			<div class="mediumFormElement">
				<label class="formElementLabel" for="expiry">Expiración:</label>
				<input type="text" id="expiry" name="expiry" style="width: 64px;"
				pattern="[0-9]{2}\/[0-9]{2}" placeholder="01/70" required>	
			</div>
			<div class="mediumFormElement">
				<label class="formElementLabel" for="cvc">CVC:</label>
				<input type="text" id="cvc" name="cvc" style="width: 64px;"
				pattern="[0-9]{3}" required>	
			</div>
		</div>
	</fieldset>
	<div class="formSubmitElement">
		<input type="reset" value="Limpiar">
		<input type="submit" value="Confirmar">
	</div>
</form>
</div>
<%@ include file="/common/footer.jsp"%>