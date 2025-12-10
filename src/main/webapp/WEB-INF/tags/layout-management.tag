<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="title" required="true"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BALANCE | ${title.toUpperCase()}</title>
<c:set value="${pageContext.request.contextPath }" scope="request"
	var="root"></c:set>
	
<link rel="stylesheet" href="${root }/resources/css/common.css" />

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-SgOJa3DmI69IUzQ2PVdRZhwQ+dy64/BUtbMJw1MZ8t5HZApcHrRKUc4W0kG879m7"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
	crossorigin="anonymous"></script>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>

	<!-- Navigation -->
	<nav class="navbar sticky-top navbar-expand navbar-dark bg-dark">
		<div class="container">
			<a href="${root }/admin/home" class="navbar-brand"> <i
				class="bi-house"></i> Balance Mangement
			</a>

			<ul class="navbar-nav">
			
				<li class="nav-item"><a href="${root }/admin/access" class="nav-link" ${title eq 'Access History' ? 'active' : '' }> <i
						class="bi-calendar"></i> Access History
				</a></li>

				<li class="nav-item"><a href="${root }/admin/member" class="nav-link" ${title eq 'Members' ? 'active' : '' }> <i
						class="bi-people"></i> Members
				</a></li>
				
				<li class="nav-item"><a href="#" id="signOutMenu" class="nav-link"><i
						class="bi-lock"></i> SignOut</a></li>
			</ul>
		</div>
	</nav>

	<main class="mt-4 container">
		<jsp:doBody />
		
		<script src="${root}/resources/js/signout.js"></script>
	</main>
	<app:signout />
	
</body>
</html>
