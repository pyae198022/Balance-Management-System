<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>

<app:layout-member title="${type.name().toUpperCase()}">

	<app:page-title title="${type } Management" />


	<!-- Search Form -->
	<form class="row">
		<app:form-group label="Date From" cssClass="col-auto">
			<input type="date" class="form-control" />
		</app:form-group>

		<app:form-group label="Date To" cssClass="col-auto">
			<input type="date" class="form-control" />
		</app:form-group>

		<app:form-group label="Keyword" cssClass="col-auto">
			<input type="text" class="form-control" placeholder="Search keyword" />
		</app:form-group>

		<div class="col btn-wrapper">
			<button class="btn btn-primary">
				<i class="bi-search"></i> Search
			</button>
			
			<a href="${root }/member/entry/${type.name().toLowerCase()}/create" class="btn btn-danger">
				<i class="bi-plus"></i> New Entry
			</a>
		</div>
	</form>

	<!-- Result Table -->
	<table class="table table-striped table-bordered table-hovered my-3">
		<thead>
			<tr>
				<th>Code</th>
				<th>Issue At</th>
				<th>Ledger</th>
				<th>Particular</th>
				<th class="text-end">Amount</th>
				<th></th>
			</tr>
		</thead>

		<tbody>
			<tr>
				<td>2025234-001</td>
				<td>2025-02-10 10:00</td>
				<td>Service Charges</td>
				<td>Maintenance Fees for POS</td>
				<td class="text-end">100,000</td>
				<td class="text-center">
					<a href="${root }/member/balance/2025">
						<i class="bi-arrow-right"></i>
					</a>
				</td>
			</tr>
		</tbody>
	</table>


	<!-- Pagnation  -->
	<app:pagination/>

</app:layout-member>