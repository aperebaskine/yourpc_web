<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>    
	<div id="content">
		<%
			@SuppressWarnings("unchecked")
			Results<Product> results = (Results<Product>) request.getAttribute("results");
		
			if (results == null) {
				return;
			}
				%><h1>Resultados: <%=results.getResultCount()%></h1>
				<ul><%
				
				for (Product product : results.getPage()) {
					%><li>Nombre: <%=product.getName()%>, precio: <%=product.getSalePrice()%> €</li><%
				}
		%>
	</ul>
	</div>
<%@ include file="/common/footer.jsp" %>