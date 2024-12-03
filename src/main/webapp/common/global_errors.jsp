<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<div class="globalErrors">
	<c:if test="${errors.hasGlobalErrors()}">
		<span>
			<c:choose>
				<c:when test="${errors.globalErrors.size() > 1}">
					<fmt:message key="errors.global.found.multiple" />
				</c:when>
				<c:otherwise>
					<fmt:message key="errors.global.found" />
				</c:otherwise>
			</c:choose>
		</span>
		<ul>
			<c:forEach var="error" items="${errors.globalErrors}">
				<li><fmt:message key="error.${error}"></fmt:message>
			</c:forEach>
		</ul>
	</c:if>
</div>