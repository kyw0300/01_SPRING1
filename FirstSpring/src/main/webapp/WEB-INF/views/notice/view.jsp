<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.min.css" />
<title>공지게시판 상세보기</title>
</head>
<body>

	<div class="container">
		<div class="content-wrapper">
			<section class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1>공지게시판 상세보기</h1>
						</div>
					</div>
				</div>
			</section>

			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-12">
							<div class="card card-primary">
								<div class="card-header">
									<h3 class="card-title">${notice.boTitle }</h3>
									<div class="card-tools">${notice.boWriter } ${notice.boDate } ${notice.boHit }</div>
								</div>
								<div class="card-body">${notice.boContent }</div>
								<div class="card-footer">
									<button type="button" class="btn btn-primary" id="listBtn">목록</button>
									<button type="button" class="btn btn-info" id="updateBtn">수정</button>
									<button type="button" class="btn btn-danger" id="delBtn">삭제</button>
								</div>
							</div>
						</div>
						<form action="/notice/update.do" method="get" id="nFrm">
							<input type="hidden" name="boNo" value="${notice.boNo }">
						</form>
						<div class="col-md-6"></div>
					</div>
				</div>
			</section>
		</div>
	</div>
	
	<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery.min.js"></script>
</body>
<script type="text/javascript">
$(function(){
	var listBtn = $("#listBtn");
	var updateBtn = $("#updateBtn");
	var delBtn = $("#delBtn");
	var nFrm = $("#nFrm");
	
	listBtn.on("click",function(){
		location.href = "/notice/list.do";
	})
	
	updateBtn.on("click",function(){
		// 수정 처리(페이지로 이동합니다)
		nFrm.submit();
	})
	
	delBtn.on("click",function(){
		if(confirm("정말 삭제하시겠습니까?")) {
			// 삭제 처리
			nFrm.attr("method", "post");
			nFrm.attr("action", "/notice/delete.do");
			nFrm.submit();
		} else {
			// 삭제 취소
			nFrm.reset();
		}
	})
	
	if(${empty notice}) {
		alert("잘못된 접근입니다!");
		location.href = "/notice/list.do";
	}
})
</script>
</html>


