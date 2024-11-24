<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<div class="pagination">
	<ul class="pagination-menu">
		<li><a href='${firstPage}'>First</a></li>
		<li><a href='${previousPage}'>Previous</a></li>
		<c:forEach var="page" items="${displayedPages.entrySet()}">
			<li><a href='${page.getValue()}'><c:out value="${page.getKey()}"></c:out></a></li>
		</c:forEach>
		<li><a href='${nextPage}'>Next</a></li>
		<li><a href='${lastPage}'>Last</a></li>
	</ul>
</div>