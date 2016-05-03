<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/mylib"%>
<section id="main">
	<div class="container">
		<h1 id="homeTitle">
			<c:out value="${requestScope.computerNumber}" />
			Computers found
		</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="#" method="GET" class="form-inline">
					<input type="search" id="searchbox" name="search"
						class="form-control" placeholder="Search name"
						<c:if test="${not empty requestScope.search}">
							value="${requestScope.search}"
						</c:if>
					/>
					<input type="submit" id="searchsubmit" value="Filter by name"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer" href="http://localhost:8080/cdb/computer-add">
					AddComputer 
				</a>
				<a class="btn btn-default" id="editComputer" href="#"
					onclick="$.fn.toggleEditMode();">
					Edit
				</a>
			</div>
		</div>
	</div>

	<form id="deleteForm" action="#" method="POST">
		<input type="hidden" name="selection" value="">
	</form>

	<div class="container" style="margin-top: 10px;">
		<%@include file="/WEB-INF/views/computers/table.jsp"%>
	</div>
</section>