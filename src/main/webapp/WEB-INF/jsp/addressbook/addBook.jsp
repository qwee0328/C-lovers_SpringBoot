<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css" />
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link rel="stylesheet" href="/css/addressbook/addBook_main.css" />
<link rel="stylesheet" href="/css/addressbook/addBookHome.css" />
<script src="/js/addressbook/addressBook.js"></script>
<style>
	.addBook__container{
		width: calc(100vw - 250px);
		height: calc(100vh - 60px);
		overflow:auto;
	}
	.addBook__body{
		min-width:1200px;
	}
</style>
</head>
<body>

	<%@ include file="../commons/header.jsp"%>
	<div class="container d-flex">
		<%@ include file="../addressbook/abNaviBar.jsp"%>
		<div class="addBook__container">
			<div class="addBook__header d-flex">
				<div class="header__searchBar d-flex">
					<div class="searchBar__icon">
						<i class="fa-solid fa-magnifying-glass align-center"></i>
					</div>
					<div class="searchBar__inputCover d-flex">
						<input type="text" class="searchBar__input" placeholder="이름, 회사명, 전화번호 검색">
					</div>
				</div>
				<div class="header__tagName align-center"></div>
			</div>
			<div class="body__emptyTrash">
			  <div class="emptyTrash__cover">
			      <span>휴지통으로 이동한 주소는 30일 후 자동으로 완전히 삭제됩니다.</span>
			      <button class="emptyTrash__emptyTrashBtn">지금 비우기</button>
			  </div>
			</div>
			
			<div class="addBook__body">
				<div class="body__addListHeader d-flex">
					<div class="addListHeader__chkBoxCover align-center">
						<input type="checkbox" class="addListHeader__chkBox">
					</div>
					<div class="addessLint__favorites addListHeader__default"></div>
					<div class="addListHeader__name addListHeader__default">이름</div>
					<div class="addListHeader__email addListHeader__default">이메일</div>
					<div class="addListHeader__phone addListHeader__default">전화번호</div>
					<div class="addListHeader__company addListHeader__default">회사</div>
					<div class="addListHeader__tag addListHeader__default">태그</div>
					
					<!-- 체크 박스 선택 시 -->
					<div class="addListHeader__selectCnt addListHeader__select">1</div>
					<div class="addListHeader__trash addListHeader__select">삭제</div>
					<div class="addListHeader__copy addListHeader__select">공유 주소록에 복사</div>
				
					<!-- 휴지통에사 체크 박스 선택 시 -->
					<div class="addListHeader__selectCnt addListHeader__selectInTrash">1</div>
					<div class="addListHeader__delete addListHeader__selectInTrash">영구 삭제</div>
					<div class="addListHeader__restore addListHeader__selectInTrash">복구</div>
				</div>
				<div class="body__addList"></div>
				<div class="pagination"></div>
			</div>
		</div>
		<%@ include file="../addressbook/addBookInsertModal.jsp"%>
		<%@ include file="../addressbook/addBookViewModal.jsp"%>
		<%@ include file="../addressbook/addBookTagInsertModal.jsp"%>
	</div>
</body>
</html>