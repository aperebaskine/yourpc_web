<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/categories.jsp" %>
	<form id="product-search-form" action="<c:url value='/DefaultServlet'></c:url>" method="post">
		<input type="hidden" name="<%=Parameters.ACTION%>" value="<%=Actions.PRODUCT_RESULTS%>" />
		<div class="formElement">
			<label for="<%=Parameters.NAME%>"><fmt:message key="name"/></label> 
			<input type="text" name="name" placeholder="Ejemplo: Placa base" />
		</div>
		<div class="formElement">
			<label class="formElementLabel"><fmt:message key="category"/></label>
			<select id="categorySelector" name="<%=Parameters.CATEGORY_ID%>">
				<option disabled selected><fmt:message key="form.placeholder.category"/></option>
				<c:forEach var="category" items="${categories}">
					<option value="${category.id}">${category.name}</option>
				</c:forEach>
			</select> 
		</div>
		<div class="formRangeElement">
			<label class="formElementLabel"><fmt:message key="launchDate"/></label>
			<input name="<%=Parameters.LAUNCH_DATE_FROM%>" type="date">
			<label> - </label>
			<input name="<%=Parameters.LAUNCH_DATE_TO%>" type="date">		
		</div>
		<div class="formRangeElement">
			<label class="formElementLabel"><fmt:message key="price"/></label>
			<input name="<%=Parameters.PRICE_FROM%>" type="number" step="0.01">
			<label> - </label>
			<input name="<%=Parameters.PRICE_TO%>" type="number" step="0.01">
		</div>
		<div class="formRangeElement">
			<label class="formElementLabel"><fmt:message key="stock"/></label>
			<input name="<%=Parameters.STOCK_FROM%>" type="number">
			<label> - </label>
			<input name="<%=Parameters.STOCK_TO%>" type="number">
		</div>
		<div id="attributes">
			<script type="text/javascript" src="<c:url value='/scripts/attribute_form.js'></c:url>"></script>
		</div>
		<div class="formSubmitElement">
			<input style="background-color: #EAD8F5; color: black;" type="reset" value="<fmt:message key="form.reset"/>" />
			<input type="submit" value="<fmt:message key="form.search"/>" />
		</div>
	</form>
<%@ include file="/common/footer.jsp" %>
