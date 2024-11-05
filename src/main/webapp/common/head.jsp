<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<fmt:setBundle basename="i18n.messages" />
<fmt:setLocale value="${sessionScope.locale}" />
<%
	String key = new StringBuilder(request.getRequestURI())
	.delete(0, request.getContextPath().length())
	.toString().replace('/', '.');
%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>YourPC 
			<c:if test="${not empty titleKey}">
				<c:out value=" - " />
				<fmt:message key="${titleKey}"></fmt:message>
			</c:if>
		</title>
		<link rel="stylesheet" href="<c:url value="/styles/styles.css"/>"/>
	</head>
	<body>