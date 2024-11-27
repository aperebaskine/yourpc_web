<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/empty_header.jsp" %>
	<div class="loginLogo">
		<p><img src='<c:url value="/img/logo.png"></c:url>'></img></p>
	</div>
	<div class="loginForm">
		<form action="<c:url value='/DefaultServlet'></c:url>" method="post">
			<input type="hidden" name="action" value="login"/>
			<div class="formElement">
				<label class="formElementLabel" for="email">Email:</label>
				<input name="email" type="email" placeholder="rastley@gmail.com"/>
			</div>
			<div class="formElement">
				<label class="formElementLabel" for="password">Password:</label>
				<input name="password" type="password"/>
			</div>
			<div class="formElement">
				<span>Forgot password?</span>
			</div>
			<div class="formSubmitElement">
				<input type="submit" value="Login" style="width: 92px;">
				<input style="background-color: #EAD8F5; color: black; width: 92px;" type="submit" formAction="#" value="Register">
			</div>
		</form>
	</div>
<%@ include file="/common/empty_footer.jsp" %>
