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
<div class="wizardProgress">
		<h3>Completa su perfil</h3>
		<progress value="67" max="100"></progress>
		<div class="wizardSteps">
			<p>Datos personales</p>
			<p>Dirección</p>
			<p>Métodos de pago</p>
		</div>
	</div>
<div class="columnFlex">
	<h2>Dirección introducida correctamente.</h2>
	<form>
		<div class="formSubmitElement">
			<input style="background-color: #EAD8F5; color: black;" type="submit" formaction="<%=request.getContextPath()%>/user/address.jsp" value="Introducir otra dirección">
			<input type="submit" formaction="<%=request.getContextPath()%>/user/payment-details.jsp" value="Continuar">
		</div>
	</form>
</div>
<%@ include file="/common/footer.jsp"%>