<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<body>
	<form action="/HelloWorldWeb/UserServlet" method="post">
		<input type="hidden" name="action" value="login"/>
		<label for="email">Email:</label>
		<input name="email" type="email" placeholder="rastley@gmail.com"/>
		<label for="password">Password:</label>
		<input name="password" type="password"/>
		<input type="submit" value="Login"/>
	</form>
<%@ include file="/common/footer.jsp" %>
