<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.min.css" />
<title>공지게시판 등록</title>
</head>
<body>
	<c:set value="등록" var="name"/>
	<c:if test="${status eq 'u' }">
		<c:set value="수정" var="name"/>
	</c:if>
	
	<div class="jumbotron">
		<div class="container">
			<h3 class="display-3">공지게시판 ${name }</h3>
		</div>
	</div>
	<div class="container">
		<form class="form-horizontal" method="post" action="/notice/insert.do" id="noticeForm">
			<c:if test="${status eq 'u' }">
				<input type="hidden" name="boNo" value="${notice.boNo }">
			</c:if>
			<input name="id" type="hidden" class="form-control" value="">
			<div class="form-group row">
				<label class="col-sm-2 control-label" >제목</label>
				<div class="col-sm-5">
					<input name="boTitle" id="boTitle" type="text" class="form-control" value="${notice.boTitle }" placeholder="subject">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 control-label" >내용</label>
				<div class="col-sm-8">
					<textarea name="boContent" id="boContent" cols="50" rows="5" class="form-control" placeholder="content">${notice.boContent }</textarea>
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10 ">
					<input type="button" id="formBtn" class="btn btn-primary " value="${name }">				
					<c:if test="${status eq 'u' }">
						<a href="/notice/detail.do?boNo=${notice.boNo }">
							<input type="button" value="취소" class="btn btn-danger float-right">
						</a>
					</c:if>
					<c:if test="${status ne 'u' }">
						<a href="/notice/list.do">
							<input type="button" value="목록" class="btn btn-success float-right">
						</a>
					</c:if>
				</div>
			</div>
		</form>
		<c:if test="${not empty errors }">
			<p>
				${errors.boTitle }<br/>
				${errors.boContent }<br/>
				${errors.msg }<br/>
			</p>
		</c:if>
	</div>
	<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/dist/js/adminlte.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
</body>
<script type="text/javascript">
$(function(){
	CKEDITOR.replace("boContent");
	CKEDITOR.config.allowedContent = true;    // html태그가 사라지는걸 방지
	
	var formBtn = $("#formBtn");
	formBtn.on("click",function(){
		var title = $("#boTitle").val(); 
        var content = CKEDITOR.instances.boContent.getData() //내용 데이터 얻어오기
        
        var titleLength = title.length;
        var contentLength = content.length;
     
        var maxTitle = 132;
        var maxContent = 1300;
        
        if (titleLength > maxTitle) {
            if (titleLength > maxTitle) {
                alert("제목 입력 제한 길이를 초과했습니다. 다시 입력해 주세요.");
            }
            else if (contentLength > maxContent) {
                alert("내용 입력 제한 길이를 초과했습니다. 다시 입력해 주세요.");
            }
            return false;
        }
		
		// 제목을 입력하지 않았을 때에 대한 필터
		if(title == "") {
			alert("제목을 입력해주세요!");
			$("#boTitle").focus();
			return false;
		}
		
		// 내용을 입력하지 않았을 때에 대한 필터
		if(content == "") {
			alert("내용을 입력해주세요!");
			$("#boContent").focus();
			return false;
		}
		
		if($(this).val() == "수정") {
			$("#noticeForm").attr("action", "/notice/update.do");  // 수정을 하러 가기 위해 url경로를 틀어준다.
		}
		
		$("#noticeForm").submit();
		
	})
	
// 	if(${empty notice}) {
// 		alert("잘못된 접근입니다!");
// 		location.href = "/notice/list.do";
// 	}
})
</script>
</html>
