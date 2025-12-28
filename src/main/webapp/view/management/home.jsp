<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<app:layout-management title="Home">
	<div class="d-flex justify-content-between align-item-start">
		<app:page-title title="Member Home" />

		<div class="">
			<div class="btn-group">
			
				<c:url value="${root }/admin/home/load" var="monthlyAPi">
					<c:param name="type" value="Monthly"></c:param>
				</c:url>
				<input data-rest-api="${monthlyAPi }" type="radio" name="display" class="btn-check"
					checked="checked" value="" id="monthly" /> <label for="monthly"
					class="btn btn-outline-secondary">Monthly</label> 
				
				<c:url value="${root }/admin/home/load" var="yearlyAPi">
					<c:param name="type" value="Yearly"></c:param>
				</c:url>
				<input data-rest-api="${yearlyAPi }"
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
						<i class="bi-people"></i> ${vo.lastMonth() }
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
						<i class="bi-people"></i> ${vo.lastYear() }
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
						<i class="bi-people"></i> ${vo.totalMember() }
					</h4>
				</div>
			</div>
		</div>

		<div class="col">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">
						<i class="bi-people"></i> Member Access
					</h5>

					<div class="" id="adminChart"></div>
				</div>
			</div>
		</div>
	</div>

	
	<script src="https://cdn.amcharts.com/lib/5/index.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/xy.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/themes/Animated.js"></script>
	<script src="${root}/resources/js/management-home.js"></script>

</app:layout-management>