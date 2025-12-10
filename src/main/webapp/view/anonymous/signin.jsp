<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>


<app:layout-anonymous title="Sign In">

	<main class="loginForm">
		<h3>
			<i class="bi-unlock" style="margin-right: 2px"></i> Sign In
		</h3>
		
		<c:if test="${message ne null }">
			<div class="alert alert-info">
				<i class="bi-info-circle"></i> ${message }
			</div>
		</c:if>

		<form action="${pageContext.request.contextPath}/signin" method="post" class="mt-4">
			<sec:csrfInput />

			<app:form-group label="Login ID" cssClass="mb-3">
				<input name="username" type="text" placeholder="Enter Login ID"
					class="form-control" />
			</app:form-group>

			<app:form-group label="Password">
				<div class="input-group">
					<input type="password" name="password" id="password"
						placeholder="Enter password" class="form-control"
						required="required" /> <span class="input-group-text"
						onclick="togglePassword()" style="cursor: pointer;"> <i
						id="toggleIcon" class="bi bi-eye-slash"></i>
					</span>
				</div>
			</app:form-group>

			<div class="mt-3">
				<input id="remember-me" name="remember-me" type="checkbox" class="form-check-input" />
				<label class="form-check-label" style="margin-left: 2px">
					Remember Me</label>
			</div>

			<div class="mt-3">
				<a href="${root }/signup" class="btn btn-outline-primary "> <i
					class="bi-person-plus"></i> Sign Up
				</a>

				<button class="btn btn-primary">
					<i class="bi-unlock"></i> Sign In
				</button>
			</div>
		</form>
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