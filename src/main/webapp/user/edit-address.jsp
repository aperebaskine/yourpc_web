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
<script src="<c:url value="/scripts/address_form.js"/>"></script>
<h2 class="wizardFormTitle">Edit address</h2>
<div class="wizardForm">
	<form action="<c:url value="/user/UserServlet"/>" method="post">
		<input type="hidden" name="action" value="${Actions.UPSERT_ADDRESS}">
		<input type="hidden" name="${Parameters.ADDRESS_ID}" value="${address.id}">
		<input type="hidden" name="${Parameters.CALLBACK_URL}" value="
			<c:choose>
				<c:when test="${not empty param.callbackUrl}">
					${param.callbackUrl}
				</c:when>
				<c:otherwise>
					${currentUrl}
				</c:otherwise>
			</c:choose>
		">
		<div class="formElement">
			<label class="formElementLabel" for="${Parameters.STREET_NAME}">Street name</label>
			<input type="text" id="${Parameters.STREET_NAME}" name="${Parameters.STREET_NAME}" value="${address.streetName}" 
				<c:if test="${not empty errors.getFieldErrors(Parameters.STREET_NAME)}">class="invalid"</c:if>
			required>
			<c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.STREET_NAME)}">
      	      <span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
 	        </c:forEach>
		</div>
		<div class="inlineFormElements">
			<div class="smallFormElement">
				<label class="formElementLabel" for="${Parameters.STREET_NUMBER}">Street num.</label>
				<input type="number" id="${Parameters.STREET_NUMBER}" name="${Parameters.STREET_NUMBER}"  value="${address.streetNumber}"
					<c:if test="${not empty errors.getFieldErrors(Parameters.STREET_NUMBER)}">class="invalid"</c:if>>
			<c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.STREET_NUMBER)}">
	            <span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
            </c:forEach>
			</div>
			<div class="smallFormElement">
				<label class="formElementLabel" for="${Parameters.FLOOR}">Floor</label>
				<input type="number" id="${Parameters.FLOOR}" name="${Parameters.FLOOR}"  value="${address.floor}"
					<c:if test="${not empty errors.getFieldErrors(Parameters.FLOOR)}">class="invalid"</c:if>>
				<c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.FLOOR)}">
             		<span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
              	</c:forEach>
			</div>
			<div class="smallFormElement">
				<label class="formElementLabel" for="${Parameters.DOOR}">Door</label>
				<input type="text" id="${Parameters.DOOR}" name="${Parameters.DOOR}"  value="${address.door}"
					<c:if test="${not empty errors.getFieldErrors(Parameters.DOOR)}">class="invalid"</c:if>>
				<c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.DOOR)}">
                    <span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
                </c:forEach>
			</div>
		</div>
		<div class="mediumFormElement">
			<label class="formElementLabel" for="${Parameters.ZIP_CODE}">ZIP Code</label>
			<input type="number" id="${Parameters.ZIP_CODE}" name="${Parameters.ZIP_CODE}"  value="${address.zipCode}"
				<c:if test="${not empty errors.getFieldErrors(Parameters.ZIP_CODE)}">class="invalid"</c:if>>
			<c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.ZIP_CODE)}">
            	<span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
			</c:forEach>
		</div>
		<div class="formElement">
			<script>var cityIdParam = "${address.cityId}";</script>
			<label for="${Parameters.CITY}">City</label>
			<select id="${Parameters.CITY}" name="${Parameters.CITY}" disabled
				<c:if test="${not empty errors.getFieldErrors(Parameters.CITY)}">class="invalid"</c:if>>
				<option selected disabled>Select a city...</option>
			</select>
			<c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.CITY)}">
            	<span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
			</c:forEach>
		</div>
		<div class="formElement">
			<script>var provinceIdParam = "${address.provinceId}";</script>
			<label for="${Parameters.PROVINCE}">Province</label>
			<select id="${Parameters.PROVINCE}" name="${Parameters.PROVINCE}" disabled
				<c:if test="${not empty errors.getFieldErrors(Parameters.PROVINCE)}">class="invalid"</c:if>>
				<option selected disabled>Select a province...</option>
			</select>
			<c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.PROVINCE)}">
            	<span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
			</c:forEach>
		</div>
		<div class="formElement">
			<label for="${Parameters.COUNTRY}">Country</label>
			<select name="${Parameters.COUNTRY}" id="${Parameters.COUNTRY}"
				<c:if test="${not empty errors.getFieldErrors(Parameters.COUNTRY)}">class="invalid"</c:if>>
				<option selected disabled>Select a country...</option>
				<c:forEach var="country" items="${countries}">
					<option value="${country.id}"
						<c:if test="${country.id.equals(address.countryId)}">
							selected
						</c:if>
					>${country.name}</option>
				</c:forEach>
			</select>
			<c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.COUNTRY)}">
            	<span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
			</c:forEach>
		</div>
		<div>
			<span>Default <input type="checkbox" name="${Parameters.IS_DEFAULT}" value="true"
				<c:if test="${address.isDefault()}">
					checked
				</c:if>
			></span>
			<span>Billing <input type="checkbox" name="${Parameters.IS_BILLING}" value="true"
				<c:if test="${address.isBilling()}">
					checked
				</c:if>
			></span>
		</div>
		<div class="formSubmitElement">
			<input type="reset" value="Reset">
			<input type="submit" value="Submit">
		</div>
	</form>
</div>
<%@ include file="/common/footer.jsp"%>