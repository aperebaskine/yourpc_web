<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.pinguela.yourpc.model.dto.*" %>
<%@ page import="com.pinguela.yourpc.model.Results" %>
<%@ page import="com.pinguela.yourpc.web.constants.*" %>
<%@ include file="/common/taglib.jsp" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="i18n.messages" />
<!DOCTYPE html>
<%
	String key = new StringBuilder(request.getRequestURI())
	.delete(0, request.getContextPath().length())
	.toString().replace('/', '.');
%>
<html>
	<head>
	<!-- Google tag (gtag.js) -->
		<script async src="https://www.googletagmanager.com/gtag/js?id=G-QZ7C0C0DD4"></script> 
		<script> 
			window.dataLayer = window.dataLayer || []; 
			function gtag(){dataLayer.push(arguments);} 
			gtag('js', new Date()); 
			gtag('config', 'G-QZ7C0C0DD4'); 
		</script>
		<meta charset="UTF-8">
		<title>YourPC 
			<c:if test="${not empty titleKey}">
				<c:out value=" - " />
				<fmt:message key="${titleKey}"></fmt:message>
			</c:if>
		</title>
		<link rel="stylesheet" href="<c:url value="/styles/styles.css"/>"/>
		<link rel="preconnect" href="https://fonts.googleapis.com">
		<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
		<link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">
		<script type="text/javascript" src="<c:url value="/js/jquery/jquery-3.7.1.min.js"/>"></script>
		<script>var contextPath = "${pageContext.request.contextPath}";</script>
	</head>
	<body>