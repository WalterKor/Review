<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<link rel="stylesheet" href="/res/css/boardlist.css">

<h1>리스트</h1>
<table>
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>글쓴이</th>
		<th>작성일시</th>
	</tr>
	<c:forEach items="${requestScope.list}" var="item">
		<tr class="record" onclick="moveToDetail(${item.iboard})">
			<td>${item.iboard}</td>
			<td>
			<c:choose>
				<c:when test="${param.searchType eq 1 || param.searchType eq 2}">
					${item.title.replace(param.searchText,"<mark>" += param.searchText += '</mark>')}			
				</c:when>
				<c:otherwise>
					${item.title}
				</c:otherwise>
			</c:choose>
			</td>
			<td>
				<c:choose>
					<c:when test="${param.searchType eq 4}">
						${item.writerNm.replace(param.searchText, '<mark>' += param.searchText += '</mark>') }
					</c:when>
					<c:otherwise>
						${item.writerNm}
					</c:otherwise>		
				</c:choose>
			</td>
			<td>${item.regdt}</td>
		</tr>
	</c:forEach>
</table>

	<div>
		<c:forEach begin="1" end="${requestScope.pagingCnt}" var="page">
		
			<c:choose> 
				<c:when test="${page eq param.cPage || (empty param.cPage && page eq 1)}"> <!-- 맞다면 -->
					<span class="colorRed">${page}</span>			
				</c:when>
				<c:otherwise> <!-- 아니라면-->
					<span><a href="list?cPage=${page}&searchType=${param.searchType}&searchText=${param.searchText}">${page}</a></span>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
	
	<div>
		<form action="list" method="get">
			<div>
				<!-- 아이디검색 그런거 -->
				<select name="searchType" >
					<option value="1" ${param.searchType == 1 ? 'selected' : ''}>제목+내용</option>
					<option value="2" ${param.searchType == 2 ? 'selected' : ''}>제목</option>
					<option value="3" ${param.searchType == 3 ? 'selected' : ''}>내용</option>
					<option value="4" ${param.searchType == 4 ? 'selected' : ''}>글쓴이</option>
					
				</select> 
				<input type="search" name="searchText">
				<input type="submit" value="검색">				
			</div>
		</form>
	</div>
<script src="/res/js/boardList.js"></script>	