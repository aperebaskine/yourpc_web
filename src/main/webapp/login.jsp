<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/empty_header.jsp" %>
	<form action="<c:url value='/DefaultServlet'></c:url>" method="post">
		<input type="hidden" name="action" value="login"/>
		<label for="email">Email:</label>
		<input name="email" type="email" placeholder="rastley@gmail.com"/>
		<label for="password">Password:</label>
		<input name="password" type="password"/>
		<input type="submit" value="Login"/>
	</form>
<%@ include file="/common/empty_footer.jsp" %>
