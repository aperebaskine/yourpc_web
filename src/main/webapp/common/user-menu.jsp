<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<%@ page import="com.pinguela.yourpc.web.constants.Parameters,
	com.pinguela.yourpc.config.ConfigManager" %>
<c:set var="supportedLocales" value="${ConfigManager.getParameters('locale.supported')}"/>
<ul class="user-menu">
	<li>
		<form id="localeForm" action='#' method="post">
			<input type="hidden" name="${Parameters.CALLBACK_URL}" value="${currentUrl}">
			<select name="${Parameters.SWITCH_LOCALE}" onchange="submit();">
				<c:set var="currentLocale" value="${sessionScope.locale.toLanguageTag()}"></c:set>
				<c:forEach var="supportedLocale" items="${supportedLocales}">
					<option value="${supportedLocale}" <c:if test="${supportedLocale.equals(currentLocale)}">selected</c:if>>${supportedLocale}</option>
				</c:forEach>
			</select>
		</form>
	</li>
	<c:choose>
		<c:when test="${not empty customer}">
			<li><fmt:message key="hello"/> ${customer.firstName}</li>
			<li>
				<form id="logoutForm" action="<c:url value="/DefaultServlet"></c:url>">
					<input type="hidden" name="action" value="logout">
					<input type="hidden" name="${Parameters.CALLBACK_URL}" value="${currentUrl}">
					<input class="button-light" type="submit" value="<fmt:message key="logout"/>">
				</form>
			</li>
			<li>
				<form id="myaccountform" action="<c:url value="/user/UserServlet"></c:url>">
					<input type="hidden" name="action" value="user-details">
					<input class="button" type="submit" value="<fmt:message key="myaccount"/>">
				</form>
			</li>
		</c:when>
		<c:otherwise>
			<li>
				<form id="loginForm" action="<c:url value="/login.jsp"></c:url>">
					<input type="hidden" name="${Parameters.CALLBACK_URL}" value="${currentUrl}">
					<input class="button" type="submit" value="<fmt:message key="login"/>">
				</form>
			</li>
			<li>
				<form id="registerForm" action="<c:url value="/register.jsp"></c:url>">
					<input class="button-light" type="submit" value="<fmt:message key="register"/>">
				</form>
			</li>
		</c:otherwise>
	</c:choose>
	<li><fmt:message key="cart"/></li>
</ul>