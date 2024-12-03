<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<h2><fmt:message key="ticket" /> # ${ticket.id}</h2>
<p><fmt:message key="ticket.date" />: ${ticket.creationDate}</p>
<c:if test="${not empty ticket.messageList}">
	<p><fmt:message key="ticket.update.timestamp" />: ${ticket.messageList.get(ticket.messageList.size() -1).timestamp}</p>
</c:if>
<p><fmt:message key="ticket.state" />: ${ticket.state}</p>
<p><fmt:message key="ticket.title" />: ${ticket.title}</p>
<span><fmt:message key="ticket.description" />:</span>
<p>${ticket.description}</p>
<span><fmt:message key="ticket.messages" />:</span>
<c:forEach var="message" items="${ticket.messageList}">
	<div class="message">
		<span>${message.firstName} ${message.lastName1} ${message.lastName2} - ${message.timestamp}</span>
		<p>${message.text}</p>
	</div>
</c:forEach>
<div class="addMessage">
	<h3><fmt:message key="reply"/></h3>
	<form id="add-message-form" action="<c:url value="/user/TicketServlet"></c:url>" method="get">
		<input type="hidden" name="action" value="add-ticket-message">
		<input type="hidden" name="ticketId" value="${ticket.id}">
		<textarea name="message-text"></textarea>
		<input type="submit">
	</form>
</div>
<%@ include file="/common/footer.jsp" %>