<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<div class="new-ticket">
	<h2><fmt:message key="ticket.new" /></h2>
	<form id="new-ticket-form" action="<c:url value="/user/TicketServlet"></c:url>" method="get">
		<input type="hidden" name="action" value="${Actions.INSERT_TICKET}">
	
		<div class="formElement">
			<label class="formElementLabel"><fmt:message key="message"/></label>
			<textarea name="message-text"></textarea>
		</div>
		
		
		<input type="submit">
	</form>
</div>
<%@ include file="/common/footer.jsp" %>