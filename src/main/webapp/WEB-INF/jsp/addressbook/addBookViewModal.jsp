<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제목</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css" />
<link rel="stylesheet" href="/css/addressbook/addBook_main.css" />
<link rel="stylesheet" href="/css/addressbook/addBookModal.css" />
</head>
<body>
	<div class="addBookViewModal modal">
		<div class="addBookViewModal__header d-flex">
			<div class="addBookViewModal__favorites align-center mr15">
				<i class="fa-regular fa-star align-center favorites__icon viewFavorite"></i>
			</div>
			<div class="addBookViewModal__title"></div>
		</div>

		<div class="addBookModal__modalBody"></div>
		<div class="addBookModal__btns d-flex">
			<div class="addBookModal__cancelBtnCover mr15">
				<button class="addBookModal__cancelBtn">닫기</button>
			</div>
		
			<div class="addBookModal__deleteBtnCover mr15">
				<button class="addBookModal__deleteBtn" id="addBookModal__deleteBtn">삭제</button>
			</div>
			<div class="addBookModal__updateBtnCover">
				<button class="addBookModal__updateBtn" id="addBookModal__updateBtn">수정</button>
			</div>
		</div>
	</div>
</body>
</html>