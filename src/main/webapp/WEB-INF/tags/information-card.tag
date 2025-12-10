<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="label" required="true" %>
<%@ attribute name="value" required="true" %>
<%@ attribute name="bgColor" required="true" %>
<%@ attribute name="icon" required="true" %>

<div class="card ${bgColor }">
	<div class="card-body">
		<h6>
			<i class="${icon }"></i> ${label }
		</h6>
		<h5 class="mt-4">${value }</h5>
	</div>
</div>