<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>    
	<div id="content">
		<c:if test="${not empty results}">
			<h1><c:out value="${results.resultCount}"></c:out> resultados</h1>
			<ul>
				<c:forEach var="p" items="${results.page}">
					<li><c:out value="${p.name}"></c:out>, precio: <c:out value="${p.salePrice}"></c:out></li>
				</c:forEach>
			</ul>
		</c:if>
	</div>
<%@ include file="/common/pagination.jsp" %>
<%@ include file="/common/footer.jsp" %>