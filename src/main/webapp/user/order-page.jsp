<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>    
<c:choose>
	<c:when test="${not empty cart}">
		<c:set var="total" value="${0.0}" />
		<h2>Addresses</h2>
		<h3>Shipping address</h3>
		<c:if test="${not empty shippingAddress}">
			<div class="address">
				<p>Street: ${shippingAddress.streetName}
					<c:if test="${not empty shippingAddress.streetNumber }">
						, ${shippingAddress.streetNumber}
					</c:if>
				</p>
				<c:if test="${not empty shippingAddress.floor}">
					<p>Floor: ${shippingAddress.floor}</p>
				</c:if>
				<c:if test="${not empty shippingAddress.door}">
					<p>Floor: ${shippingAddress.door}</p>
				</c:if>
				<p>ZIP Code: ${shippingAddress.zipCode}</p>
				<p>City: ${shippingAddress.city}</p>
				<p>Province: ${shippingAddress.province}</p>
				<p>Country: ${shippingAddress.country}</p>
			</div>
		</c:if>
		<h3>Billing address</h3>
		<c:if test="${not empty billingAddress}">
			<div class="address">
				<p>Street: ${billingAddress.streetName}
					<c:if test="${not empty billingAddress.streetNumber }">
						, ${billingAddress.streetNumber}
					</c:if>
				</p>
				<c:if test="${not empty billingAddress.floor}">
					<p>Floor: ${billingAddress.floor}</p>
				</c:if>
				<c:if test="${not empty billingAddress.door}">
					<p>Floor: ${billingAddress.door}</p>
				</c:if>
				<p>ZIP Code: ${billingAddress.zipCode}</p>
				<p>City: ${billingAddress.city}</p>
				<p>Province: ${billingAddress.province}</p>
				<p>Country: ${billingAddress.country}</p>
			</div>
		</c:if>
		<h2>Cart</h2>
		<c:forEach var="cartItem" items="${cart}">
			<c:set var="total" value="${total + (cartItem.productDto.salePrice * cartItem.quantity)}" />
			<p>${cartItem.productDto.name} - Unit price: ${cartItem.productDto.salePrice} € - Qty: ${cartItem.quantity}</p>
		</c:forEach>
		<p>Total: ${total} €</p>
		<form action="<c:url value="/user/UserServlet"/>" method="post">
			<input type="hidden" name="${Parameters.ACTION}" value="${Actions.CONFIRM_ORDER}">
			<input type="submit" value="<fmt:message key="order.confirm"/>">
		</form>		
	</c:when>
	<c:otherwise>
		<h2>Your cart is empty.</h2>
	</c:otherwise>
</c:choose>
<%@ include file="/common/footer.jsp" %>