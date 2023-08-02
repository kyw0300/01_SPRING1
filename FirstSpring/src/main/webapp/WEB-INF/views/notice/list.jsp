<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.min.css" />
<title>공지게시판 목록</title>

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/fontawesome-free/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/dist/css/adminlte.min.css">
</head>
<body>
<%-- <p>type : ${sessionScope.type}</p> --%>
<%-- <p>searchType : ${sessionScope.searchType}</p> --%>
<%-- <p>searchWord : ${sessionScope.searchWord}</p> --%>
	<c:set value="${pagingVO.dataList }" var="noticeList"/>
	<div class="container">
		<h1 class="display-3">공지게시판 목록</h1>
	</div>
	<div class="container">
		<form class="input-group input-group-sm" method="post" id="searchForm" style="width: 440px;">
			<input type="hidden" name="page" id="page" />
			<input type="hidden" name="type" value="notice" />
			<select class="form-control" name="searchType">
				<option value="title" <c:if test="${searchMap.searchType == 'title' }"><c:out value="selected"/></c:if>>제목</option>
				<option value="writer" <c:if test="${searchMap.searchType == 'writer' }"><c:out value="selected"/></c:if>>작성자</option>
				<option value="all" <c:if test="${searchMap.searchType == 'all' }"><c:out value="selected"/></c:if>>제목+작성자</option>
			</select>
			<input type="text" name="searchWord" class="form-control float-right" value="${searchMap.searchWord}" placeholder="Search">
			<div class="input-group-append">
				<button type="submit" class="btn btn-default">
					<i class="fas fa-search"></i>검색
				</button>
			</div>
		</form>
		<div>
			<div class="text-left">
				<span class="badge badge-info">전체 ${pagingVO.totalRecord }건	</span>
			</div>
		</div>
		
		<div style="padding-top: 10px">
			<table class="table">
				<thead class="table-dark">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>작성일</th>
						<th>작성자</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty noticeList }">
							<tr>
								<td colspan="5">조회하신 게시글이 존재하지 않습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${noticeList }" var="notice">
								<tr onclick="location.href='/notice/detail.do?boNo=${notice.boNo }'">
									<td>${notice.boNo }</td>
									<td>${notice.boTitle }</td>
									<td>${notice.boDate }</td>
									<td>${notice.boWriter }</td>
									<td>${notice.boHit }</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<div align="left">
			<a href="/notice/form.do" class="btn btn-primary">&laquo;글쓰기</a>
		</div>
		<div class="clearfix" id="pagingArea">
			${pagingVO.pagingHTML }
		</div>
		<hr>
	</div>
	<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/dist/js/adminlte.min.js"></script>
</body>
<script type="text/javascript">
$(function(){
	var searchForm = $("#searchForm");
	var pagingArea = $("#pagingArea");
	
	pagingArea.on("click", "a", function(event){
		event.preventDefault();
		var pageNo = $(this).data("page");
		searchForm.find("#page").val(pageNo);
		searchForm.submit();
	})
})
</script>
</html>
