<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<!-- c:out은 보안떄문에 많이 사용한다. -->
<body>
	<br>
	<div>
		<input  type="button" onclick="goBack();" value="뒤로가기">
	</div>
	
	<div><h2>Title : ${requestScope.data.title}</h2></div>
	<div>글번호 : ${requestScope.data.iuser}</div>
	<div><h3>Ctnt : ${requestScope.data.ctnt}</h3></div>
	<div>작성자 :${requestScope.data.writerNm}</div>
	<div>작성일 :<c:out value="${requestScope.data.regdt}"></c:out></div>
		<!-- 로그인되어있지 않을때 댓글창 지우기 -->
		<c:if test="${not empty sessionScope.loginUser}">
			<div>
				<form id="cmtFrm" onsubmit="return false;">
					<input type="text" id="cmt">
					<input type="button" value="댓글달기" onclick="regCmt();">
				</form>
			</div>
		</c:if>
	<div id="cmtList" data-login_user_pk="${sessionScope.loginUser.iuser}" data-iboard="${param.iboard}"></div>
<script src="/res/js/boardDetail.js"></script>	
</body>
