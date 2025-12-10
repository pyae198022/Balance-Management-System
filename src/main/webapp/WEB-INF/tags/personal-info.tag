<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="name" required="true"%>
<%@ attribute name="dateOfBirth" type="java.time.LocalDate" %>
<%@ attribute name="gender" type="com.spring.balance.model.entity.consts.Gender"%>

<div class="card text-bg-light">
	<div class="card-body">
		<h5>
			<i class="bi-person"></i> Personal Info
		</h5>

		<div class="mb-3">
			<span class="text-secondary">Name</span>
			<div>${name}</div>
		</div>

		<div class="mb-3">
			<span class="text-secondary">Date Of Birth</span>
			<div>${dateOfBirth ne null ? dateTime.formatDate(dateOfBirth) : 'Undefined' }</div>
		</div>

		<div class="mb-3">
			<span class="text-secondary">Gender</span>
			<div>${gender ne null ?  gender : 'Undefined'}</div>
		</div>
	</div>
</div>
