<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<main>
	<c:forEach var="address" items="${sessionScope.customer.addresses}">
		<c:out value="${address}"></c:out>
	</c:forEach>
</main>
<%@ include file="/common/footer.jsp" %>