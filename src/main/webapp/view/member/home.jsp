<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>

<app:layout-member title="Member Home">

	<div class="d-flex justify-content-between align-item-start">
		<app:page-title title="Member Home" />

		<div class="">
			<div class="btn-group">
				<input type="radio" name="display" class="btn-check"
					checked="checked" value="" id="monthly" /> <label for="monthly"
					class="btn btn-outline-primary">Monthly</label> <input type="radio"
					name="display" class="btn-check" id="yearly" /> <label
					for="yearly" class="btn btn-outline-primary">Yearly</label>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-3">
			<!-- Profile  -->
			<div class="card">
				<div class="card-body">
					<div class="d-flex justify-content-between align-items-start">
						<h5 class="card-title">
							<i class="bi-person"></i> Profile
						</h5>
						<a href="${root }/member/profile" class="btn-link"> <i class="bi-pencil"></i>
						</a>
					</div>
				</div>

				<img src="${root }/resources/image/${profile.profileImage()}"
					class="img-fluid img-thumbnail" alt="Profile Image" />

				<div class="card-body">
					<div class="list-group list-group-flush">
						<div class="list-group-item">
							<i class="bi-person"></i> ${profile.name() }
						</div>

						<div class="list-group-item">
							<i class="bi-telephone"></i> ${profile.phone() ne null ? profile.phone() : 'Undefined' }
						</div>

						<div class="list-group-item">
							<i class="bi-envelope"></i> ${profile.email() }
						</div>

						<div class="list-group-item">
							<i class="bi-map"></i> ${profile.address() ne '' ? profile.address() : 'Undefined' }
						</div>
					</div>
				</div>
			</div>
			<!-- Access History -->
			<div class="card mt-4">
				<div class="card-body">
					<div class="d-flex justify-content-between align-item-start">
						<h5 class="card-title">
							<i class="bi-shield"></i> Access
						</h5>
						
						<a href="${root }/member/access" class="btn-link">
							<i class="bi-send"></i>
						</a>
					</div>
					
					<div class="list-group list-group-flush">
						<div class="list-group-item">
							<div class="fw-bold">Registered At</div>
							<span>${dateTime.formatDateTime(profile.registeredAt()) }</span>
						</div>
						<div class="list-group-item">
							<div class="fw-bold">Last Access</div>
							<span>${dateTime.formatDateTime(profile.lastAccessedAt()) }</span>
						</div>
					</div>
				</div>
			</div>
		</div>


		<div class="col">
			<!-- Summary -->
			<div class="row">
				<div class="col">
					<app:summary-info title="Expenses" value="100,000" icon="bi-cart"
						color="text-bg-danger">
					</app:summary-info>
				</div>
				<div class="col">
					<app:summary-info title="Incomes" value="500,000" icon="bi-flag"
						color="text-bg-warning">
					</app:summary-info>
				</div>
				<div class="col">
					<app:summary-info title="Balances" value="400,000"
						icon="bi-bar-chart" color="text-bg-primary">
					</app:summary-info>
				</div>
			</div>

			<!-- Balance Bar Charts -->
			<div class="card mt-4">
				<div class="card-body">
					<h5 class="card-title">
						<i class="bi-bar-chart"></i> Balances
					</h5>

					<div id="balanceChart"></div>
				</div>
			</div>

			<div class="row m-3">
				<!-- Expenses Pie Chart -->
				<div class="col">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">
								<i class="bi-cart"></i> Expenses
							</h5>

							<div id="expensesChart" class="pieChart"></div>
						</div>
					</div>
				</div>

				<!-- Incomes Pie Chart -->
				<div class="col">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">
								<i class="bi-cart"></i> Incomes
							</h5>

							<div id="incomesChart" class="pieChart"></div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<script src="https://cdn.amcharts.com/lib/5/index.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/xy.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/percent.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/themes/Animated.js"></script>
	<script src="${root }/resources/js/member-home.js"></script>
</app:layout-member>