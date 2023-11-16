<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주소록</title>
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
	}
</style>
</head>
<body>

	<%@ include file="../commons/header.jsp"%>
	<div class="container d-flex">
		<%@ include file="../addressbook/abNaviBar.jsp"%>
		<div class="addBook__container">
			<div class="addBook__header">
				<div class="header__searchBar d-flex">
					<div class="searchBar__icon">
						<i class="fa-solid fa-magnifying-glass align-center"></i>
					</div>
					<div class="searchBar__inputCover d-flex">
						<input type="text" class="searchBar__input"
							placeholder="이름, 회사명, 전화번호 검색">
					</div>
				</div>
			</div>
			<div class="addBook__body">
				<div class="body__addListHeader d-flex">
					<div class="addListHeader__chkBoxCover align-center">
						<input type="checkbox" class="addListHeader__chkBox">
					</div>
					<div class="addessLint__favorites"></div>
					<div class="addListHeader__name">이름</div>
					<div class="addListHeader__email">이메일</div>
					<div class="addListHeader__phone">전화번호</div>
					<div class="addListHeader__company">회사</div>
					<div class="addListHeader__tag">태그</div>
				</div>
				<div class="body__addList">
					<div class="addList__addessLine d-flex">
						<div class="addessLine__chkBoxCover align-center">
							<input type="checkbox" class="addessLine__chkBox">
						</div>
						<div class="addessLint__favorites align-center">
							<i class="fa-regular fa-star align-center favorites__icon"></i>
						</div>
						<div class="addessLine__name">김체리 대표</div>

						<div class="addessLine__email">cherrykin@cherryhongsam.kr</div>
						<div class="addessLine__phone">010-7777-7777</div>
						<div class="addessLine__company">체리홍삼(주)</div>
						<div class="addessLine__tag d-flex">
							<div class="addBook__tag align-center">ceo</div>
						</div>
					</div>
				</div>
				<div class="body__addList">
					<div class="addList__addessLine d-flex">
						<div class="addessLine__chkBoxCover align-center">
							<input type="checkbox" class="addessLine__chkBox">
						</div>
						<div class="addessLint__favorites align-center">
							<i class="fa-regular fa-star align-center favorites__icon"></i>
						</div>
						<div class="addessLine__name">김체리 대표</div>

						<div class="addessLine__email">cherrykin@cherryhongsam.kr</div>
						<div class="addessLine__phone">010-7777-7777</div>
						<div class="addessLine__company">체리홍삼(주)</div>
						<div class="addessLine__tag d-flex">
							<div class="addBook__tag align-center">ceo</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../addressbook/addBookInsertModal.jsp"%>
		<%@ include file="../addressbook/addBookViewModal.jsp"%>
		<%@ include file="../addressbook/addBookTagInsertModal.jsp"%>
	</div>



</body>
</html>