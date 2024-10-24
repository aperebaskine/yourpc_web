<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="i18n.messages" />

<meta charset="UTF-8">
<title>YourPC 
	<c:if test="${not empty pageTitle}">
		<c:out value=" - ${pageTitle}" />
	</c:if>
</title>