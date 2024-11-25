<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/categories.jsp" %>
	<form id="product-search-form" action="<c:url value='/DefaultServlet'></c:url>" method="post">
		<input type="hidden" name="<%=Parameters.ACTION%>" value="<%=Actions.PRODUCT_RESULTS%>" />
		<label for="<%=Parameters.NAME%>">Nombre:</label> 
		<input type="text" name="name" placeholder="Ejemplo: Placa base" />
		<br />
		<label>Categoría:</label>
		<select id="categorySelector" name="<%=Parameters.CATEGORY_ID%>">
			<option disabled selected>Seleccionar una categoría...</option>
			<c:forEach var="category" items="${categories}">
				<option value="${category.id}">${category.name}</option>
			</c:forEach>
		</select> 
		<br />
		<label>Fecha de lanzamiento:</label><br />
		<label>desde</label>
		<input name="<%=Parameters.LAUNCH_DATE_FROM%>" type="date">
		<label>hasta</label>
		<input name="<%=Parameters.LAUNCH_DATE_TO%>" type="date" >
		<br />
		<label>Precio:</label><br />
		<label>desde</label>
		<input name="<%=Parameters.PRICE_FROM%>" type="number" step="0.01">
		<label>hasta</label>
		<input name="<%=Parameters.PRICE_TO%>" type="number" step="0.01"><br />
		<label>Stock:</label><br />
		<label>desde</label>
		<input name="<%=Parameters.STOCK_FROM%>" type="number">
		<label>hasta</label>
		<input name="<%=Parameters.STOCK_TO%>" type="number">
		<br />
		<fieldset id="attributes">
			<script type="text/javascript" src="<c:url value='/scripts/attribute_form.js'></c:url>"></script>
		</fieldset>
		<input type="submit" value="Buscar" />
	</form>
<%@ include file="/common/footer.jsp" %>
