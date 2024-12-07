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
<h2 class="wizardFormTitle">New address</h2>
<div class="wizardForm">
	<form action="<%=request.getContextPath()%>/user/next-address.jsp" method="post">
		<div class="formElement">
			<label class="formElementLabel" for="${Parameters.STREET_NAME}">Street name</label>
			<input type="text" id="${Parameters.STREET_NAME}" name="${Parameters.STREET_NAME}" required>
		</div>
		<div class="inlineFormElements">
			<div class="smallFormElement">
				<label class="formElementLabel" for="${Parameters.STREET_NUMBER}">Street num.</label>
				<input type="number" id="${Parameters.STREET_NUMBER}" name="${Parameters.STREET_NUMBER}">
			</div>
			<div class="smallFormElement">
				<label class="formElementLabel" for="${Parameters.FLOOR}">Floor</label>
				<input type="number" id="${Parameters.FLOOR}" name="${Parameters.FLOOR}">
			</div>
			<div class="smallFormElement">
				<label class="formElementLabel" for="${Parameters.DOOR}">Door</label>
				<input type="text" id="${Parameters.DOOR}" name="${Parameters.DOOR}">
			</div>
		</div>
		<div class="mediumFormElement">
			<label class="formElementLabel" for="${Parameters.ZIP_CODE}">ZIP Code</label>
			<input type="number" id="${Parameters.ZIP_CODE}" name="${Parameters.ZIP_CODE}">
		</div>
		<div class="formElement">
			<label for="${Parameters.CITY}">City</label>
			<select id="${Parameters.CITY}" name="${Parameters.CITY}" disabled>
				<option selected disabled>Select a city...</option>
			</select>
		</div>
		<div class="formElement">
			<label for="${Parameters.PROVINCE}">Province</label>
			<select id="${Parameters.PROVINCE}" name="${Parameters.PROVINCE}" disabled>
				<option selected disabled>Select a province...</option>
			</select>
		</div>
		<div class="formElement">
			<label for="${Parameters.COUNTRY}">Country</label>
			<select name="${Parameters.COUNTRY}" id="${Parameters.COUNTRY}">
				<option selected disabled>Select a country...</option>
				<c:forEach var="country" items="${countries}">
					<option value="${country.id}">${country.name}</option>
				</c:forEach>
			</select>
		</div>
		<div>
			<span>Default <input type="checkbox" name="${Parameters.IS_DEFAULT}"></span>
			<span>Billing <input type="checkbox" name="${Parameters.IS_BILLING}"></span>
		</div>
		<div class="formSubmitElement">
			<input type="reset" value="Reset">
			<input type="submit" value="Submit">
		</div>
	</form>
</div>
<%@ include file="/common/footer.jsp"%>