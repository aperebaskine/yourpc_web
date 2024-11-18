<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pinguela.yourpc.web.constants.Attributes" %>
<%@ page import="com.pinguela.yourpc.service.CategoryService" %>
<%@ page import="com.pinguela.yourpc.service.impl.CategoryServiceImpl" %>
<%@ page import="com.pinguela.yourpc.web.util.LocaleUtils" %>
<%
	CategoryService service = new CategoryServiceImpl();
	request.setAttribute(Attributes.CATEGORIES, service.findAll(LocaleUtils.getLocale(request)).values());
%>