<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
	<form action="/HelloWorldWeb/ProductServlet" method="post">
		<label for="name">Nombre:</label> <input type="text" name="name"
			placeholder="Ejemplo: Placa base" /> <input type="submit"
			value="Buscar" />
		<select>
			<c:forEach var=""></c:forEach>
		</select>
	</form>
<%@ include file="/common/footer.jsp" %>