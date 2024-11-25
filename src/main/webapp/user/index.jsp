<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<main>
	<c:forEach var="address" items="${sessionScope.customer.addresses}">
		<c:out value="${address}"></c:out>
	</c:forEach>
	<div class="wizard" style="width: 360px;">
		<p>Introduzca sus datos para poder realizar pedidos</p>
		<ul>
			<li><a href="<%=request.getContextPath()%>/user/personal_data.jsp">Datos personales</a></li>
			<li><a href="<%=request.getContextPath()%>/user/address.jsp">Direcciones</a></li>
			<li><a href="<%=request.getContextPath()%>/user/payment-details.jsp">Métodos de pago</a></li>
		</ul>
		<br />
		<form>
			<input type="submit" formaction="<%=request.getContextPath()%>/user/personal_data.jsp" value="Empezar">
		</form>
	</div>
</main>
<%@ include file="/common/footer.jsp" %>