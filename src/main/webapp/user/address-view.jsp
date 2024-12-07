<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<main>
	<div class="addresses">
		<c:forEach var="address" items="${sessionScope.customer.addresses}">
			<div class="address">
				<h3>Address ID # ${address.id}</h3>
				<p>Street: ${address.streetName}
					<c:if test="${not empty address.streetNumber }">
						, ${address.streetNumber}
					</c:if>
				</p>
				<c:if test="${not empty address.floor}">
					<p>Floor: ${address.floor}</p>
				</c:if>
				<c:if test="${not empty address.door}">
					<p>Floor: ${address.door}</p>
				</c:if>
				<p>ZIP Code: ${address.zipCode}</p>
				<p>City: ${address.city}</p>
				<p>Province: ${address.province}</p>
				<p>Country: ${address.country}</p>
				<p>
					<input type="checkbox" disabled 
						<c:if test="${address.isDefault()}">
							checked
						</c:if>
					/>
					Default
				</p>
				<p>
					<input type="checkbox" disabled 
						<c:if test="${address.isBilling()}">
							checked
						</c:if>
					/>
					Billing
				</p>
			</div>
			<form action="<c:url value='/user/UserServlet'/>">
				<input type="hidden" name="${Parameters.ADDRESS_ID}" value="${address.id}">
				<input type="hidden" name="${Parameters.CALLBACK_URL}" value="${currentUrl}">
				<button type="submit" name="action" value="${Actions.EDIT_ADDRESS}">Edit</button>
				<button type="submit" name="action" value="${Actions.DELETE_ADDRESS}">Delete</button>
			</form>
		</c:forEach>
	</div>
	<form action="<c:url value='/user/new-address.jsp'/>">
		<input type="submit" value="New address">
	</form>
</main>
<%@ include file="/common/footer.jsp" %>