<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<app:layout-member title="Profiile">

	<app:page-title title="Edit Profile" />

	<div class="row">
		<!-- Profile Image -->
		<div class="col-3">
			<img src="${root }/resources/image/${form.profileImage }"
				alt="Profile Image" class="img-fluid img-thumbnail" />

			<form action="${root }/member/profile/photo" id="profileImage"
				class="mt-3" method="post" enctype="multipart/form-data">
				<sec:csrfInput/>
				<input id="profileInput" name="file" type="file"
					class="form-control d-none" />
				<button id="profileButton" type="button"
					class="btn btn-primary w-100">
					<i class="bi-camera"></i> Change Profile Photo
				</button>
			</form>
		</div>

		<div class="col-6">
			<sf:form action="${root }/member/profile" method="post" modelAttribute="form" >
				<!-- Personal Info Inputs-->
				<!-- Name -->
				<div class="row">
					<app:form-group label="Name" cssClass="mb-3 col-8">
						<sf:input path="name" type="text" placeholder="Enter Name" class="form-control" />
						<sf:errors path="name" cssClass="text-secondary" />
					</app:form-group>
				</div>

				<div class="row mb-3">
					<!-- Gender -->
					<app:form-group label="Gender" cssClass="col-4">
						<sf:select path="gender" class="form-select">
						<option value="">Select One</option>
							<option value="Male" ${form.gender eq 'Male' ? 'selected' : '' }>Male</option>
							<option value="Female"  ${form.gender eq 'Female' ? 'selected' : '' }>Female</option>
						</sf:select>
						<sf:errors path="gender" cssClass="text-secondary" />
					</app:form-group>

					<!-- DOB -->
					<app:form-group label="Date of Birth" cssClass="col-4">
						<sf:input path="dob" type="date" class="form-control" />
						<sf:errors path="dob" cssClass="text-secondary" />
					</app:form-group>
				</div>


				<!-- Contact Info Inputs -->

				<div class="row mb-3">
					<!-- Phone -->
					<app:form-group label="Phone" cssClass="col-4">
						<sf:input path="phone" type="tel" class="form-control"
							placeholder="Enter phone number" />
						<sf:errors path="phone" cssClass="text-secondary" />
					</app:form-group>

					<!-- Email -->
					<app:form-group label="Email" cssClass="col">
						<sf:input path="email" readonly="true" type="email" class="form-control"
							placeholder="Enter email address" />
					</app:form-group>
				</div>

				<div class="row mb-3">
					<!-- Region -->
					<app:form-group label="Region" cssClass="col">
						<select id="region" data-fetch-api="${root }/member/location/district" class="form-select">
							<option value="">Select One</option>
							<c:forEach var="items" items="${regions }">
								<option value="${items.id }" ${items.id eq form.region ? 'selected' : '' }>${items.name }
							</c:forEach>
						</select>
					</app:form-group>

					<!-- Districts -->
					<app:form-group label="Districts" cssClass="col">
						<select id="district" data-fetch-api="${root }/member/location/township" class="form-select">
							<option value="">Select One</option>
							<c:forEach var="item" items="${districts}">
								<option value="${item.id}" ${item.id eq form.district ? 'selected' : '' } >${item.name}</option>
							</c:forEach>
						</select>
					</app:form-group>

					<!-- Township -->
					<app:form-group label="Township" cssClass="col">
						<sf:select path="township" id="township" class="form-select">
							<option value="">Select One</option>
							<c:forEach var="item" items="${townships}">
								<option value="${item.id}" ${item.id eq form.township ? 'selected' : '' }>${item.name}</option>
							</c:forEach>
						</sf:select>
						<sf:errors path="township" cssClass="text-secondary" />
					</app:form-group>
				</div>


				<!-- Address -->
				<div class="mb-3">
					<app:form-group label="Address">
						<sf:textarea path="address" cols="50" rows="3" class="form-control"
							placeholder="Enter Address"></sf:textarea>
						<sf:errors path="address" cssClass="text-secondary" />
					</app:form-group>
				</div>
				
				<button class="btn btn-danger">
					<i class="bi-save"></i> Save Profile
				</button>
			</sf:form>

		</div>
	</div>

	<script src="${root}/resources/js/profile-edit.js"></script>
	<script src="${root}/resources/js/member-location.js"></script>
</app:layout-member>

