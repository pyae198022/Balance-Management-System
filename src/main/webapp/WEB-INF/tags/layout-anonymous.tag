<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="title" required="true"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BALANCE | ${title.toUpperCase()}</title>
<c:set value="${pageContext.request.contextPath }" scope="request" var="root"></c:set>

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

<link rel="stylesheet"
	href="${root }/resources/css/anonymous.css" />
</head>
<body>

	<div class="vh100 d-flex">

		<div class="heading d-flex">
			<svg id="headIcon" xmlns="http://www.w3.org/2000/svg" width="200" height="200" fill="currentColor" class="bi bi-bar-chart" viewBox="0 0 16 16">
  				<path d="M4 11H2v3h2zm5-4H7v7h2zm5-5v12h-2V2zm-2-1a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h2a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1zM6 7a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v7a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1zm-5 4a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1z"/>
			</svg>
			
			<h1>BALANCE MANAGEMENT SYSTEM</h1>
			<h3>Powered By PSC</h3>
		</div>

		<div class="formContainer d-flex align-items-center justify-content-center">
			<jsp:doBody></jsp:doBody>
		</div>
	</div>

</body>
</html>
