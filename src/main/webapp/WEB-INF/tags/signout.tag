<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<form id="signoutForm" class="d-none" action="${root }/signout" method="post">
	<sec:csrfInput/>
	
</form>
<script src="${root}/resources/js/signout.js"></script>