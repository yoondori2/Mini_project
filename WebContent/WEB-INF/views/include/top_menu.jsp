<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!-- /주소를 이용하여  contextpath 를 포함한 절대경로를 구하게 된다. 그 이후 el문으로 출력한다.  -->
<!-- 이점: 상위경로가 어떻든 간에 신경쓰지 않고 경로를 설정할 수 있다. -->
<c:set var="root" value="${pageContext.request.contextPath}/"/>
<!-- 상단 메뉴 부분 -->
<nav class="navbar navbar-expand-md bg-dark navbar-dark fixed-top shadow-lg">
	<!-- 상대경로로 하면 board/main 으로 함께 계속 뜬다. 그러므로 절대경로로 구해서 쓰는것이 좋다. -->
	<a class="navbar-brand" href="${root}main">IOChord</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
	        data-target="#navMenu">
		<span class="navbar-toggler-icon"></span>        
	</button>
	<div class="collapse navbar-collapse" id="navMenu">
		<ul class="navbar-nav">
		 <c:forEach var="obj" items="${topMenuList}">
			<li class="nav-item">
				<a href="${root}board/main?board_info_idx=${obj.board_info_idx}" class="nav-link">${obj.board_info_name}</a>
			</li>
			 </c:forEach>
			 
		</ul>
		
		<ul class="navbar-nav ml-auto">
			<li class="nav-item">
				<a href="${root}user/login" class="nav-link">로그인</a>
			</li>
			<li class="nav-item">
				<a href="${root}user/join" class="nav-link">회원가입</a>
			</li>
			<li class="nav-item">
				<a href="${root}user/modify" class="nav-link">정보수정</a>
			</li>
			<li class="nav-item">
				<a href="${root}user/logout" class="nav-link">로그아웃</a>
			</li>
		</ul>
	</div>
</nav>
