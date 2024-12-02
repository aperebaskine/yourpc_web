<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<main>
	<div class="user-details">
		<h3><fmt:message key="user.details.title"></fmt:message></h3>
		<p>
			${customer.firstName} ${customer.lastName1} 
			<c:if test="${not empty customer.lastName2}">${customer.lastName2}</c:if>
		</p>
		<p><fmt:message key="user.details.since"></fmt:message> ${customer.creationDate}</p>
	</div>
	<div class="user-orders">
		<h3><fmt:message key="user.details.orders"></fmt:message></h3>
		<c:forEach var="order" items="${customerOrders}">
			<div class="user-order">
				<p><fmt:message key="order" /> # ${order.id}</p>
				<p><fmt:message key="order.date" />: ${order.orderDate}</p>
				<p><fmt:message key="order.state" />: ${order.state}</p>
				<p><fmt:message key="order.total" />: ${order.totalPrice} €</p>
				<ul>
					<c:forEach var="orderLine" items="${order.orderLines}">
						<li>
							${orderLine.productName} - 
							<fmt:message key="qty" />: ${orderLine.quantity} - 
							<fmt:message key="unitPrice" />: ${orderLine.salePrice} €
						</li>
					</c:forEach>
				</ul>
			</div>
		</c:forEach>
	</div>
	<div class="user-tickets">
		<h3><fmt:message key="user.details.tickets"></fmt:message></h3>
		<c:forEach var="ticket" items="${customerTickets.page}">
			<div class="user-ticket">
				<p><fmt:message key="ticket" /> # ${ticket.id}</p>
				<p><fmt:message key="ticket.date" />: ${ticket.creationDate}</p>
				<p><fmt:message key="ticket.update.timestamp" />: ${ticket.messageList.get(ticket.messageList.size() -1).timestamp}</p>
				<p><fmt:message key="ticket.state" />: ${ticket.state}</p>
				<p><fmt:message key="ticket.title" />: ${ticket.title}</p>
				<form action='<c:url value="/user/TicketServlet"></c:url>' method="get">
					<input type="hidden" name="action" value="ticket-details">
					<input type="hidden" name="ticketId" value="${ticket.id}">
					<input type="submit" value="<fmt:message key="view.details" />">
				</form>
			</div>
		</c:forEach>
	</div>
	<div class="user-rmas">
		<h3><fmt:message key="user.details.rmas"></fmt:message></h3>
		<c:forEach var="rma" items="${customerRmas}">
			<div class="user-rma">
				<p><fmt:message key="rma" /> # ${rma.id}</p>
				<p><fmt:message key="rma.date" />: ${rma.creationDate}</p>
				<p><fmt:message key="rma.state" />: ${rma.state}</p>
				<ul>
					<c:forEach var="orderLine" items="${rma.orderLines}">
						<li>
							${orderLine.productName} - 
							<fmt:message key="qty" />: ${orderLine.quantity}
						</li>
					</c:forEach>
				</ul>
			</div>
		</c:forEach>
	</div>
</main>
<%@ include file="/common/footer.jsp" %>