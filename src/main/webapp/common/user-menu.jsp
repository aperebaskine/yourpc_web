<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.pinguela.yourpc.model.Customer" %>
<%
	Customer customer = (Customer) request.getSession().getAttribute("customer");
	if (customer == null) {
%><a href="/HelloWorldWeb/user/login.jsp">Login</a>
<%
	} else {
%><h2> Hola <%=customer.getFullName()%></h2>
<%
	}
%>