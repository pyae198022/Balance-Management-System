<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<app:layout-anonymous title="Sign Up">

	<main class="loginForm">
		<h3> <i class="bi-person-plus" style="margin-right: 2px"></i> Sign Up</h3>
		
		<c:if test="${message ne null }">
			<div class="alert alert-info">
				<i class="bi-info-circle"></i> ${message }
			</div>
		</c:if>


		<sf:form action="${root }/signup" modelAttribute="form" method="post" class="mt-4">

			<app:form-group label="Member Name"  cssClass="mb-3">
				<sf:input type="text" path="name" placeholder="Enter name"
					class="form-control" />
					<sf:errors path="name" cssClass="text-secondary"></sf:errors>
			</app:form-group>

			<app:form-group label="Email" cssClass="mb-3">
				<sf:input type="text" path="username" placeholder="Enter your email"
					class="form-control"/>
					<sf:errors path="username" cssClass="text-secondary"></sf:errors>
			</app:form-group>

			<app:form-group label="Password">
				<div class="input-group">
					<sf:input type="password" path="password" id="password" placeholder="Enter password" class="form-control"/>
					<span class="input-group-text" onclick="togglePassword()" style="cursor: pointer;">
						<i id="toggleIcon" class="bi bi-eye-slash"></i>
					</span>
				</div>
				<sf:errors path="password" cssClass="text-secondary"></sf:errors>
			</app:form-group>

			<div class="mt-4">
				<a href="${root }/signin" class="btn btn-outline-primary"> 
					<i class="bi-unlock"></i> Sign In
				</a>

				<button type="submit" class="btn btn-primary">
					<i class="bi-person-plus"></i> Sign Up
				</button>
			</div>
		</sf:form>
	</main>
	
		<!-- JavaScript for toggling password visibility -->
	<script>
		function togglePassword() {
			const passwordInput = document.getElementById("password");
			const toggleIcon = document.getElementById("toggleIcon");

			if (passwordInput.type === "password") {
				passwordInput.type = "text";
				toggleIcon.classList.remove("bi-eye-slash");
				toggleIcon.classList.add("bi-eye");
			} else {
				passwordInput.type = "password";
				toggleIcon.classList.remove("bi-eye");
				toggleIcon.classList.add("bi-eye-slash");
			}
		}
	</script>

</app:layout-anonymous>