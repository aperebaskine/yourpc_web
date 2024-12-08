<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>    
<c:if test="${not empty product}">
	<h1>${product.name}</h1>
	<h2>${product.salePrice} €</h2>
	<c:choose>
		<c:when test="${product.stock > 0}">
			<p><fmt:message key="product.details.instock"></fmt:message></p>
			<form action="<c:url value="/DefaultServlet"/>" method="get">
				<input type="hidden" name="${Parameters.ACTION}" value="${Actions.UPDATE_CART}">
				<input type="hidden" name="${Parameters.PRODUCT_ID}" value="${product.id}">
				<input type="number" name="${Parameters.QUANTITY}" min="1" max="${product.stock}" value="1">
				<input type="submit" value="<fmt:message key="cart.add"/>">
			</form>
		</c:when>
		<c:otherwise>
			<p><fmt:message key="product.details.nostock"></fmt:message></p>
		</c:otherwise>
	</c:choose>
	<p>${product.launchDate}</p>
	<p>${product.description}</p>
	<p><fmt:message key="product.details.attributes"></fmt:message>:</p>
	<ul>
		<c:forEach var="attribute" items="${product.attributes.values()}">
			<p>${attribute.name}: 
				<c:forEach var="value" items="${attribute.values}">
					${value.value}
				</c:forEach>
			</p>
		</c:forEach>
	</ul>
</c:if>
<%@ include file="/common/footer.jsp" %>