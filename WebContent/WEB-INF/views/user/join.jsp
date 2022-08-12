<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="root" value="${pageContext.request.contextPath}/"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>미니 프로젝트</title>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<script>
	function checkUserIdExist(){
		
		var user_id = $("#user_id").val();
		
		//아이디 입력 안하면 유효성 검사 걸리기 때문에 체크 해주기.
		if(user_id.length == 0 ){
			alert('아이디를 입력해주세요')
			return
		}
		//ajax로 아이디 중복 확인 처리하기 
		$.ajax({
			url : '${root}user/checkUserIdExist/' + user_id, 
			type : 'get',
			dataType : 'text',
			success : function(result){
				if(result.trim() == 'true'){
					alert('사용할 수 있는 아이디입니다.')
					$('#userIdExist').val('true')
				}else{
					alert('사용할 수 없는 아이디입니다.')		 
					$('#userIdExist').val('false')
				}

			}
				
		})
	}
	//중복확인 누르고 다시 수정했을 경우를 대비하여 다시 값 false 로 세팅하기 
	function resetUserIdExist(){
		$('#userIdExist').val('false');
	}
</script>

<body>

<c:import url="/WEB-INF/views/include/top_menu.jsp"/>

<div class="container" style="margin-top:100px">
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6">
			<div class="card shadow">
				<div class="card-body">
				<!-- spring에서 제공하는 form태그  joinUserBean이란 이름으로 bean을 주입받을 것이다.-->
				<!-- hidden: form태그에 값이 들어가져있으면 server에서 자동으로 주입받아서 validator통과해서 유효성검사를 할 수있었다.  -->
				<!-- 사용자가 유효성검사 했는지 안했는지 서버로 보내야 하기 때문에 히든으로 그 값을 저장해서 넘겨준다. -->
				<!-- onkeyUp: 키를 눌렀다 놓았을때 이벤트가 발생한다.  -->
					<form:form action="${root }user/join_pro" method="post" modelAttribute="joinUserBean">
					<form:hidden path="userIdExist"/>
						<div class="form-group">
							<form:label path="user_name">이름</form:label>
							<form:input path="user_name" class="form-control"/>
							<form:errors path="user_name" style="color:red"/>
						</div>
						<div class="form-group">
							<form:label path="user_id">아이디</form:label>
							<div class="input-group">
								<form:input path="user_id" class="form-control" onkeypress="resetUserIdExist()"/>
								<div class="input-group-append">
									<button type="button" class="btn btn-primary" onclick="checkUserIdExist()">중복확인</button>
								</div>
							</div>
							<form:errors path="user_id" style="color:red"/>
						</div>
						<div class="form-group">
							<form:label path="user_pw">비밀번호</form:label>
							<form:password path="user_pw" class="form-control"/>
							<form:errors path="user_pw" style="color:red"/>
						</div>
						<div class="form-group">
							<form:label path="user_pw2">비밀번호 확인</form:label>
							<form:password path="user_pw2" class="form-control"/>
							<form:errors path="user_pw2" style="color:red"/>
						</div>
						<div class="form-group">
							<div class="text-right">
								<form:button class="btn btn-primary">회원가입</form:button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
		<div class="col-sm-3"></div>
	</div>
</div>

<c:import url="/WEB-INF/views/include/bottom_menu.jsp"/>
</body>
</html>








