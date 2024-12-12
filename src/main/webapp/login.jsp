<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/empty_header.jsp" %>
<div class="loginForm">
	<a href="<c:url value='/index.jsp'></c:url>">
		<img class="logo" alt="Logo" src="<c:url value='/img/logo.png'></c:url>" />
	</a>
	<%@ include file="/common/global_errors.jsp" %>
	<form action="<c:url value='/DefaultServlet'></c:url>" method="post">
		<input type="hidden" name="action" value="login"/>
		<input type="hidden" name="${Parameters.CALLBACK_URL}" 
		<c:choose>
			<c:when test="${not empty param.callbackUrl}">
				value="${param.callbackUrl}"
			</c:when>
			<c:otherwise>
				value="<c:url value="${Views.HOME}"></c:url>"
			</c:otherwise>
		</c:choose>">
			<div class="formElement">
			<label class="formElementLabel" for="email"><fmt:message key="email"></fmt:message></label>
			<input name="email" type="email" value="${param.email}" placeholder="rastley@gmail.com" 
			<c:if test="${errors.hasGlobalErrors()}">class="invalid"</c:if>/>
		</div>
		<div class="formElement">
			<label class="formElementLabel" for="password"><fmt:message key="password"></fmt:message></label>
			<input name="password" type="password" <c:if test="${errors.hasGlobalErrors()}">class="invalid"</c:if>/>
		</div>
		<div class="formSubmitElement">
			<input type="submit" value="<fmt:message key="login"/>">
			<input style="background-color: #EAD8F5; color: black;" 
			type="submit" formAction="<c:url value="/register.jsp"/>" value="<fmt:message key="register"/>">
		</div>
		<div class="formElement" style="font-size: x-small;">
			<span><fmt:message key="forgotPassword"></fmt:message></span>
		</div>
	</form>
</div>
<%@ include file="/common/empty_footer.jsp" %>
