<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<h2>Ticket # ${ticket.id}</h2>
<p><fmt:message key="ticket" /> # ${ticket.id}</p>
<p><fmt:message key="ticket.date" />: ${ticket.creationDate}</p>
<p><fmt:message key="ticket.state" />: ${ticket.state}</p>
<p><fmt:message key="ticket.title" />: ${ticket.title}</p>
<span><fmt:message key="ticket.description" />:</span>
<p>${ticket.description}</p>
<span><fmt:message key="ticket.messages" /></span>
<c:forEach var="message" items="${ticket.messageList}">
	
</c:forEach>
<%@ include file="/common/footer.jsp" %>