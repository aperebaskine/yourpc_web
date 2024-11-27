<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/categories.jsp" %>
	<form id="product-search-form" action="<c:url value='/DefaultServlet'></c:url>" method="post">
		<input type="hidden" name="<%=Parameters.ACTION%>" value="<%=Actions.PRODUCT_RESULTS%>" />
		<div class="formElement">
			<label for="<%=Parameters.NAME%>">Nombre:</label> 
			<input type="text" name="name" placeholder="Ejemplo: Placa base" />
		</div>
		<div class="formElement">
			<label class="formElementLabel">Categoría:</label>
			<select id="categorySelector" name="<%=Parameters.CATEGORY_ID%>">
				<option disabled selected>Seleccionar una categoría...</option>
				<c:forEach var="category" items="${categories}">
					<option value="${category.id}">${category.name}</option>
				</c:forEach>
			</select> 
		</div>
		<div class="formRangeElement">
			<label class="formElementLabel">Fecha de lanzamiento:</label>
			<input name="<%=Parameters.LAUNCH_DATE_FROM%>" type="date">
			<label> - </label>
			<input name="<%=Parameters.LAUNCH_DATE_TO%>" type="date" >		
		</div>
		<div class="formRangeElement">
			<label class="formElementLabel">Precio:</label>
			<input name="<%=Parameters.PRICE_FROM%>" type="number" step="0.01">
			<label> - </label>
			<input name="<%=Parameters.PRICE_TO%>" type="number" step="0.01">
		</div>
		<div class="formRangeElement">
			<label class="formElementLabel">Stock:</label>
			<input name="<%=Parameters.STOCK_FROM%>" type="number">
			<label> - </label>
			<input name="<%=Parameters.STOCK_TO%>" type="number">
		</div>
		<div id="attributes">
			<script type="text/javascript" src="<c:url value='/scripts/attribute_form.js'></c:url>"></script>
		</div>
		<input type="submit" value="Buscar" />
	</form>
<%@ include file="/common/footer.jsp" %>
