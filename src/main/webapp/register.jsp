<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/empty_header.jsp" %>
<%@ page import="com.pinguela.yourpc.web.constants.ErrorCodes" %>
<div class="registerForm">
    <a href="<c:url value='/index.jsp'></c:url>">
        <img class="logo" alt="Logo" src="<c:url value='/img/logo.png'></c:url>" />
    </a>
    <%@ include file="/common/global_errors.jsp" %>
    <form action="<c:url value='/DefaultServlet'></c:url>" method="post">
        <input type="hidden" name="action" value="${Actions.REGISTER}" />
        <fieldset>
            <legend><fmt:message key="login.details" /></legend>
            <div class="formElement">
                <label class="formElementLabel" for="email"><fmt:message key="email" /></label>
                <input name="email" type="email" id="email" value="${param.email}" placeholder="rastley@gmail.com"
                       <c:if test="${not empty errors.getFieldErrors(Parameters.EMAIL)}">class="invalid"</c:if> />
                <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.EMAIL)}">
                    <span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
                </c:forEach>
            </div>
            <div class="formElement">
                <label class="formElementLabel" for="password"><fmt:message key="password" /></label>
                <input name="password" type="password" id="password"
                       <c:if test="${not empty errors.getFieldErrors(Parameters.PASSWORD)
                       		|| not empty errors.getFieldErrors(Parameters.REPEAT_PASSWORD)}">class="invalid"</c:if> />
                <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.PASSWORD)}">
                    <span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
                </c:forEach>
                <c:if test="${errors.getFieldErrors(Parameters.PASSWORD).contains(ErrorCodes.INVALID_LENGTH)
                	|| errors.getFieldErrors(Parameters.PASSWORD).contains(ErrorCodes.INVALID_FORMAT)}">
                    	<span class="errorCode"><fmt:message key="password.error" /></span>
                    	<ul class="passwordRequirements">
                    		<li><fmt:message key="password.error.length" /></li>
                    		<li><fmt:message key="password.error.uppercase" /></li>
                    		<li><fmt:message key="password.error.lowercase" /></li>
                    		<li><fmt:message key="password.error.special" /></li>
                    	</ul>
                    </c:if>
            </div>
            <div class="formElement">
                <label class="formElementLabel" for="repeatPassword"><fmt:message key="repeat.password" /></label>
                <input name="repeatPassword" type="password" id="repeatPassword"
                      <c:if test="${not empty errors.getFieldErrors(Parameters.PASSWORD)
                       		|| not empty errors.getFieldErrors(Parameters.REPEAT_PASSWORD)}">class="invalid"</c:if> />
                <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.REPEAT_PASSWORD)}">
                    <span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
                </c:forEach>
            </div>
        </fieldset>
        <fieldset>
            <legend><fmt:message key="personal.data" /></legend>
            <div class="formElement">
                <label class="formElementLabel" for="firstName"><fmt:message key="first.name" /></label>
                <input name="firstName" type="text" id="firstName" value="${param.firstName}"
                       <c:if test="${not empty errors.getFieldErrors(Parameters.FIRST_NAME)}">class="invalid"</c:if> />
                <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.FIRST_NAME)}">
                    <span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
                </c:forEach>
            </div>
            <div class="formElement">
                <label class="formElementLabel" for="lastName1"><fmt:message key="last.name1" /></label>
                <input name="lastName1" type="text" id="lastName1" value="${param.lastName1}" required
                       <c:if test="${not empty errors.getFieldErrors(Parameters.LAST_NAME1)}">class="invalid"</c:if> />
                <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.LAST_NAME1)}">
                    <span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
                </c:forEach>
            </div>
            <div class="formElement">
                <label class="formElementLabel" for="lastName2"><fmt:message key="last.name2" /></label>
                <input name="lastName2" type="text" id="lastName2" value="${param.lastName2}"
                       <c:if test="${not empty errors.getFieldErrors(Parameters.LAST_NAME2)}">class="invalid"</c:if> />
                <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.LAST_NAME2)}">
                    <span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
                </c:forEach>
            </div>
            <div class="formElement">
                <label class="formElementLabel" for="phoneNumber"><fmt:message key="phone.number" /></label>
                <input name="phoneNumber" type="tel" id="phoneNumber" value="${param.phoneNumber}" required
                       <c:if test="${not empty errors.getFieldErrors(Parameters.PHONE_NUMBER)}">class="invalid"</c:if> />
                <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.PHONE_NUMBER)}">
                    <span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
                </c:forEach>
            </div>
        </fieldset>
        <fieldset>
            <legend><fmt:message key="identity.document" /></legend>
            <div class="formElement">
                <label class="formElementLabel" for="${Parameters.DOCUMENT_TYPE_ID}"><fmt:message key="document.type" /></label>
                <select id="${Parameters.DOCUMENT_TYPE_ID}" name="${Parameters.DOCUMENT_TYPE_ID}">
                    <option value="NIF" <c:if test="${param.doctype == 'NIF'}">selected</c:if>><fmt:message key="document.type.nif" /></option>
                    <option value="NIE" <c:if test="${param.doctype == 'NIE'}">selected</c:if>><fmt:message key="document.type.nie" /></option>
                    <option value="PPT" <c:if test="${param.doctype == 'PPT'}">selected</c:if>><fmt:message key="document.type.passport" /></option>
                </select>
            </div>
            <div class="formElement">
                <label class="formElementLabel" for="${Parameters.DOCUMENT_NUMBER}"><fmt:message key="document.number" /></label>
                <input name="${Parameters.DOCUMENT_NUMBER}" type="text" id="${Parameters.DOCUMENT_NUMBER}" value="${param.docNum}" required
                       <c:if test="${not empty errors.getFieldErrors(Parameters.DOCUMENT_NUMBER)}">class="invalid"</c:if> />
                <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.DOCUMENT_NUMBER)}">
                    <span class="errorCode"><fmt:message key="error.${errorCode}" /></span>
                </c:forEach>
            </div>
        </fieldset>
        <div class="formSubmitElement">
            <input type="submit" value="Register" style="width: 92px;">
        </div>
    </form>
</div>
<%@ include file="/common/empty_footer.jsp" %>
