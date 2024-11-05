<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pinguela.yourpc.model.Results" %>
<%@ page import="com.pinguela.yourpc.model.dto.LocalizedProductDTO" %>
<%@ include file="/common/header.jsp" %>    
	<div id="content">
		<%
			@SuppressWarnings("unchecked")
			Results<LocalizedProductDTO> results = (Results<LocalizedProductDTO>) request.getAttribute("results");
		
			if (results == null) {
				return;
			}
				%><h1>Resultados: <%=results.getResultCount()%></h1>
				<ul><%
				
				for (LocalizedProductDTO product : results.getPage()) {
					%><li>Nombre: <%=product.getName()%>, precio: <%=product.getSalePrice()%> €</li><%
				}
		%>
	</ul>
	</div>
<%@ include file="/common/footer.jsp" %>