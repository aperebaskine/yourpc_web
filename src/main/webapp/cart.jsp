<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>    
<c:choose>
	<c:when test="${not empty cart}">
		<c:set var="total" value="${0.0}" />
		<h2>Cart</h2>
		<c:forEach var="cartItem" items="${cart}">
			<c:set var="total" value="${total + (cartItem.productDto.salePrice * cartItem.quantity)}" />
			<form action="<c:url value="/DefaultServlet"/>">
				<input type="hidden" name="${Parameters.ACTION}" value="${Actions.UPDATE_CART}">
				<input type="hidden" name="${Parameters.PRODUCT_ID}" value="${cartItem.productId}">
				<input type="hidden" name="${Parameters.MODIFY_QUANTITY}" value="true">
				<p>${cartItem.productDto.name} - Unit price: ${cartItem.productDto.salePrice} € - Qty:
					<input type="number" min="0" name="${Parameters.QUANTITY}" max="${cartItem.productDto.stock}"
					value="${cartItem.quantity}" onchange="submit();">
				</p>
			</form>
		</c:forEach>
		<p>Total: ${total} €</p>
		<form action="<c:url value="/user/UserServlet"/>">
			<input type="hidden" name="${Parameters.ACTION}" value="${Actions.REVIEW_ORDER}">
			<input type="submit" value="<fmt:message key="review.order"/>">
		</form>
	</c:when>
	<c:otherwise>
		<h2>Your cart is empty.</h2>
	</c:otherwise>
</c:choose>
<%@ include file="/common/footer.jsp" %>