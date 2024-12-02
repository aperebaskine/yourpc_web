<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<nav>
	<ul>
	<li><fmt:message key="main.menu.builder"/></li>
	<li><a href="<c:url value='/product/product-search.jsp'></c:url>"><fmt:message key="main.menu.all"/></a></li>
	<li><fmt:message key="main.menu.components"/></li>
	<li><fmt:message key="main.menu.peripherals"/></li>
	<li><fmt:message key="main.menu.sales"/></li>
	</ul>
</nav>