<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/categories.jsp" %>
	<form action="<c:url value='/DefaultServlet'></c:url>" method="post">
		<input type="hidden" name="<%=Parameters.ACTION%>" value="<%=Actions.PRODUCT_RESULTS%>" />
		<label for="<%=Parameters.NAME%>">Nombre:</label> 
		<input type="text" name="name" placeholder="Ejemplo: Placa base" />
		<br />
		<label for="categoryId">Categoría:</label>
		<select id="categorySelector" name="categoryId">
			<option value="">Seleccionar una categoría...</option>
			<c:forEach var="category" items="${categories}">
				<option value="${category.id}">${category.name}</option>
			</c:forEach>
		</select> 
		<br />
		<fieldset id="attributes">
			<legend>Attributes</legend>
			<script type="text/javascript" src="<c:url value='/scripts/attribute_form.js'></c:url>"></script>
		</fieldset>
		<input type="submit" value="Buscar" />
	</form>
<%@ include file="/common/footer.jsp" %>
