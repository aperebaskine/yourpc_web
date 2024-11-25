<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<div class="pagination">
	<ul class="pagination-menu">
		<c:if test="${not empty firstPage}">
			<li><a href='${firstPage}'>First</a></li>
			<li><a href='${previousPage}'>Previous</a></li>
		</c:if>
		<c:forEach var="page" items="${displayedPages.entrySet()}">
			<li><a href='${page.getValue()}'><c:out value="${page.getKey()}"></c:out></a></li>
		</c:forEach>
		<c:if test="${not empty lastPage}">
			<li><a href='${nextPage}'>Next</a></li>
			<li><a href='${lastPage}'>Last</a></li>
		</c:if>
	</ul>
</div>