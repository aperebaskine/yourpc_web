<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<h2>Introducir métodos de pago</h2>
<form class="baseForm" action="<%=request.getContextPath()%>/user/index.jsp" method="post">
	<fieldset>
		<legend>Banco</legend>
		<label for="accountHolder">Titular de la cuenta:</label>
		<input type="text" id="accountHolder" name="accountHolder" required>
		<label for="iban">IBAN:</label>
		<input type="text" id="iban" name="iban" required>
		<label for="bic">BIC:</label>
		<input type="text" id="bic" name="bic" style="width: 128px;" required>
	</fieldset>
	<fieldset>
		<legend>Tarjeta de crédito</legend>
		<label for="cardHolder">Titular de la tarjeta:</label>
		<input type="text" id="cardHolder" name="cardHolder" required>
		<label for="cardNumber">Número de tarjeta</label>
		<input type="number" id="cardNumber" name="cardNumber" required>
		<label for="expiry">Expiración:</label>
		<input type="text" id="expiry" name="expiry" style="width: 64px;"
		pattern="[0-9]{2}\/[0-9]{2}" placeholder="01/70" required>
		<br />
		<label for="cvc">CVC:</label>
		<input type="text" id="cvc" name="cvc" style="width: 64px;" 
		pattern="[0-9]{3}" required>
	</fieldset>
	<input type="submit" value="Confirmar">
	<input type="reset" value="Limpiar">
</form>
<%@ include file="/common/footer.jsp"%>