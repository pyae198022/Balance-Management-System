<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<app:layout-member title="Access History">
	
	<app:page-title title="Access History" />
	
	<!-- Search Form -->
	<form class="row" id="searchForm">
	
	<input type="hidden" name="page" id="pageInput" />
	<input type="hidden" name="size" id="sizeInput" />
	
		<app:form-group label="Status" cssClass="col-auto">
			<select name="status" class="form-select">
				<option value="">Search All</option>
				<option value="Success" ${param.status eq 'Success' ? 'selected' : ''  }>Success</option>
				<option value="Fail" ${param.status eq 'Fail' ? 'selected' : ''  }>Fails</option>
			</select>
		</app:form-group>
		
		<app:form-group label="Date From" cssClass="col-auto">
			<input type="date" name="dateFrom" class="form-control" value="${param.dateFrom }"/>
		</app:form-group>
		
			
		<app:form-group label="Date To" cssClass="col-auto">
			<input type="date" name="dateTo" class="form-control" value="${param.dateTo }"/>
		</app:form-group>
		
			
		<app:form-group label="Keyword" cssClass="col-auto">
			<input class="form-control" name="keyword" placeholder="Search keyword" value="${param.keyword }"/>
		</app:form-group>
		
		<div class="col btn-wrapper">
			<button class="btn btn-primary">
				<i class="bi-search"></i> Search
			</button>
		</div>
	</form>
	<!-- Result Table -->
	<table class="table table-striped table-bordered table-hover my-3">
		<thead>
			<tr>
				<th>Access At</th>
				<th>Access Type</th>
				<th>Status</th>
				<th>Remark</th>
			</tr>
		</thead>
		
		<tbody>
		
		<c:forEach var="item" items="${result.contents() }">
			<tr>
				<td>${dateTime.formatDateTime(item.accessAtLocal )}</td>
				<td>${item.type() }</td>
				<td>${item.status() }</td>
				<td>${item.remark() }</td>
			</tr>
		</c:forEach>
			
		</tbody>
	</table>
	
	
	<app:pagination pageResult="${result }"></app:pagination>
</app:layout-member>