<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<app:layout-management title="Member">

	<div class="d-flex justify-content-between align-items-center mb-3">

		<app:page-title title="Member Management"></app:page-title>

		<button class="btn btn-danger" id="statusChangeButton">
			<i class="${result.status() eq 'Active' ? 'bi-x' : 'bi-check' }"></i> ${result.status() eq 'Active' ? 'Denied' : 'Activate' }
		</button>
	</div>

	<div class="row">
		<div class="col-3">
			<!-- Profile management -->
			<img class="img-fluid img-thumbnail"
				style="height: 390px; width: 100%; object-fit: cover; object-position: top;"
				src="${root }/resources/image/${result.profileImage()}" />
		</div>

		<div class="col">

			<div class="row">
				<div class="col">
					<app:information-card label="Registered At" icon="bi-person-plus"
						bgColor="text-bg-info" value="${dateTime.formatDateTime(result.registeredAt()) }" />

				</div>

				<div class="col">
					<app:information-card label="Last Access" icon="bi-calendar-check"
						bgColor="text-bg-secondary" value="${dateTime.formatDateTime(result.lastLoginAt()) }" />

				</div>
				
				<div class="col">
					<app:information-card label="Status" icon="bi-shield"
						bgColor="text-bg-primary" value="${result.status() }" />
				</div>
			</div>

			<div class="row mt-4 d-flex align-items-stretch">
				<div class="col">

					<app:personal-info name="${result.name() }" dateOfBirth="${result.dateOfBirth() }" gender="${result.gender() }"/>
				</div>

				<div class="col">
					<app:contact-info phone="${result.phone() }" address="${result.displayAddresss }" email="${result.email() }"/>
				</div>
			</div>

		</div>
	</div>
	
	<div id="statusChangeDialog" class="modal">
		<div class="modal-dialog">
			<form action="${root }/admin/member/${result.id()}/update" method="post" class="modal-content">
			<sec:csrfInput/>
			<input type="hidden" name="status" value="${result.status() ne 'Active' }" />
				<div class="modal-header">
					<h5 class="modal-title">${result.status() eq 'Active' ? 'Denied' : 'Activate' } Status</h5>
				</div>
				
				<div class="modal-body">
					<app:form-group label="Reason">
						<textarea name="reason" class="form-control" required="required"></textarea>
					</app:form-group>
				</div>
				
				<div class="modal-footer">
					<button class="btn btn-outline-primary">
						<i class="bi-save"></i> Change Status
					</button>
				</div>
			</form>
		</div>
	</div>

<script src="${root }/resources/js/member-details.js"></script>
</app:layout-management>