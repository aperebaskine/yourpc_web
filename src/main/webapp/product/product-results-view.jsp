<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
				
				for (LocalizedProductDTO dto : results.getPage()) {
					%><li>Nombre: <%=dto.getName()%>, precio: <%=dto.getSalePrice()%> €</li><%
				}
		%>
	</ul>
	</div>
<%@ include file="/common/footer.jsp" %>