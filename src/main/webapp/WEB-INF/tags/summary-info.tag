<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="title" required="false" %>
<%@ attribute name="value" required="false" %>
<%@ attribute name="icon" required="false" %>
<%@ attribute name="color" required="false" %>

<div class="card ${color }">
	<div class="card-body">
		<div class="text-end">
			<span>${title }</span>
		</div>
		<h3 class="d-flex justify-content-between">
			<i class="${icon }"></i> <span>${value } MMK</span>
		</h3>

	</div>
</div>