<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>

<app:layout-management title="Home">
	<div class="d-flex justify-content-between align-item-start">
		<app:page-title title="Member Home" />

		<div class="">
			<div class="btn-group">
				<input type="radio" name="display" class="btn-check"
					checked="checked" value="" id="monthly" /> <label for="monthly"
					class="btn btn-outline-secondary">Monthly</label> <input
					type="radio" name="display" class="btn-check" id="yearly" /> <label
					for="yearly" class="btn btn-outline-secondary">Yearly</label>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-3">
			<!-- This Member for this month -->
			<div class="card mb-3 text-bg-light">
				<div class="card-header">
					<h5 class="card-title">Last Month</h5>
				</div>
				<div class="card-body">
					<h4>
						<i class="bi-people"></i> 12
					</h4>
				</div>
			</div>

			<!-- This Member for this year -->
			<div class="card mb-3 text-bg-secondary">
				<div class="card-header">
					<h5 class="card-title">Last Year</h5>
				</div>
				<div class="card-body">
					<h4>
						<i class="bi-people"></i> 120
					</h4>
				</div>
			</div>

			<!-- Total Members-->
			<div class="card mb-4 text-bg-dark">
				<div class="card-header">
					<h5 class="card-title">Total Members</h5>
				</div>
				<div class="card-body">
					<h4>
						<i class="bi-people"></i> 140
					</h4>
				</div>
			</div>
		</div>

		<div class="col">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">
						<i class="bi-people"></i> Member Registration
					</h5>

					<div class="" id="adminContainer"></div>
				</div>
			</div>
		</div>
	</div>

	<script src="https://cdn.amcharts.com/lib/5/index.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/xy.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/themes/Animated.js"></script>
	<script src="${root}/resources/js/management-home.js"></script>

</app:layout-management>