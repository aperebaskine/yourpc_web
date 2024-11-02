<%@page import="com.pinguela.yourpc.model.AttributeValueHandlingModes"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<fieldset class="attributes">
	<c:forEach items="${attributes}">
		<label for="${id}"><c:out value="${name}"></c:out></label><br />
		<c:choose>
			<c:when test="${value instanceof Boolean}">
				<label for="boolAttr${id}True"><fmt:message key="yes"></fmt:message></label> 
				<input name="${id}" id="boolAttr${id}True" type="radio" value="true"/>
				<label for="boolAttr${id}False"><fmt:message key="yes"></fmt:message></label>
				<input name="${id}" id="boolAttr${id}False" type="radio" value="false"/>
			</c:when>
			<c:when test="${valueHandlingMode} = <%=AttributeValueHandlingModes.RANGE %>">
				
			</c:when>
			<c:when test="${valueHandlingMode} = <%=AttributeValueHandlingModes.SET %>">
				
			</c:when>
		</c:choose>
	</c:forEach>
</fieldset>