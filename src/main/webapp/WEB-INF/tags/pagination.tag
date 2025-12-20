<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="pageResult"
	type="com.spring.balance.model.PageResult"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="d-flex justify-content-between">

	<c:if test="${pageResult ne null }">
		<div class="d-flex">
			<!-- Page Select -->
			<div class="input-group me-2">
				<span class="input-group-text">Size</span> <select id="sizeSelect"
					class="form-select">
					<option value="10" ${pageResult.size() eq '10' ? 'selected' : '' }>10</option>
					<option value="25" ${pageResult.size() eq '25' ? 'selected' : '' }>25</option>
					<option value="50" ${pageResult.size() eq '50' ? 'selected' : '' }>50</option>
				</select>
			</div>

			<!-- Page Links -->
			<div class="d-flex page-links">
				
				<c:if test="${not empty pageResult.pageLinks }">
					<a href="#" data-page-number="0"
						class="btn btn-outline-primary me-1"> <i class="bi-arrow-left"></i>
					</a>
				
					<c:forEach var="item" items="${pageResult.pageLinks }">
						<a href="#" data-page-number="${item }"
							class="btn btn-outline-primary me-1"> ${item + 1} </a>
					</c:forEach>
					
					
					<a href="#" data-page-number="${pageResult.totalPage - 1 }"
						class="btn btn-outline-primary me-1"> <i class="bi-arrow-right"></i>
					</a>
				</c:if>
				
			</div>
		</div>


		<!-- Page Result Info -->
		<div class="d-flex">
			<div class="input-group me-2">
				<span class="input-group-text">Total Pages</span> <span
					class="form-control">${pageResult.totalPage }</span>
			</div>

			<div class="input-group">
				<span class="input-group-text">Total Result</span> <span
					class="form-control">${pageResult.count() }</span>
			</div>
		</div>

	</c:if>

	<script type="text/javascript"
		src="${root }/resources/js/pagintaion.js"></script>
</div>
