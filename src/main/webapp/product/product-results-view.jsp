<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>    
	<div id="content">
		<c:if test="${not empty results}">
			<h1><c:out value="${results.resultCount}"></c:out> <fmt:message key="search.results"></fmt:message></h1>
			<ul>
				<c:forEach var="p" items="${results.page}">
					<li>
						<form action="<c:url value='/DefaultServlet'></c:url>" method="get">
							<label>${p.name}</label>
							<input type="hidden" name="action" value="product-details">
							<input type="hidden" name="productId" value="${p.id}">
							<input type="submit" value='<fmt:message key="view"></fmt:message>'>
						</form>
					</li>
				</c:forEach>
			</ul>
		</c:if>
	</div>
<%@ include file="/common/pagination.jsp" %>
<%@ include file="/common/footer.jsp" %>