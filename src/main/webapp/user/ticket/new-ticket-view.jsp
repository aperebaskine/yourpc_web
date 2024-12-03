<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<div class="new-ticket">
	<h2><fmt:message key="ticket.new" /></h2>
	<form id="new-ticket-form" action="<c:url value="/user/TicketServlet"></c:url>" method="get">
		<input type="hidden" name="action" value="${Actions.INSERT_TICKET}">
		<div class="formElement">
			<select name="${Parameters.TYPE}">
				<option value="BIL"><fmt:message key="billing.inquiry"></fmt:message></option>
				<option value="PRO"><fmt:message key="product.inquiry"></fmt:message></option>
				<option value="SUP"><fmt:message key="technical.support"></fmt:message></option>
				<option value="RMA"><fmt:message key="warranty.returns"></fmt:message></option>
			</select>
		</div>
		<div class="formElement">
			<label class="formElementLabel"><fmt:message key="ticket.title"/></label>
			<input type="text" name="${Parameters.TITLE}"
			<c:if test="${not empty errors.getFieldError(Parameters.TITLE)}">
				class="invalid"
			</c:if>></input>
			<c:if test="${not empty errors.getFieldError(Parameters.TITLE)}">
				<span class="fieldErrorMessage"><fmt:message key="error.${errors.getFieldError(Parameters.TITLE)}"/></span>
			</c:if>
		</div>
		<div class="formElement">
			<label class="formElementLabel"><fmt:message key="ticket.description"/></label>
			<textarea name="${Parameters.DESCRIPTION}"
			<c:if test="${not empty errors.getFieldError(Parameters.DESCRIPTION)}">
				class="invalid"
			</c:if>></textarea>
			<c:if test="${not empty errors.getFieldError(Parameters.DESCRIPTION)}">
				<span class="fieldErrorMessage"><fmt:message key="error.${errors.getFieldError(Parameters.DESCRIPTION)}"/></span>
			</c:if>
		</div>
		<input type="submit" value="<fmt:message key="confirm"/>">
	</form>
</div>
<%@ include file="/common/footer.jsp" %>