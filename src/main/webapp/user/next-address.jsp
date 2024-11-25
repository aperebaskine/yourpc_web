<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<% 
	CountryService service = new CountryServiceImpl();
	request.setAttribute("countries", service.findAll());
%> 
<%@ page import="com.pinguela.yourpc.service.CountryService" %>
<%@ page import="com.pinguela.yourpc.service.impl.CountryServiceImpl" %>
<%@ page import="com.pinguela.yourpc.web.util.LocaleUtils" %>
<%@ include file="/common/header.jsp"%>
<h2>Dirección introducida correctamente.</h2>
<form>
	<input type="submit" formaction="<%=request.getContextPath()%>/user/address.jsp" value="Introducir otra dirección">
	<input type="submit" formaction="<%=request.getContextPath()%>/user/payment-details.jsp" value="Continuar">
</form>

<%@ include file="/common/footer.jsp"%>