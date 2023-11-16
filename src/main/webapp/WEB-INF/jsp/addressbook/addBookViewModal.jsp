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
				<i class="fa-regular fa-star align-center favorites__icon"></i>
			</div>
			<div class="addBookViewModal__title">김체리 대표</div>
		</div>

		<div class="addBookModal__modalBody">
			<div class="modalBody__line d-flex">
				<div class="modalBody__title d-flex">이메일</div>
				<div class="modalBody__content addBookViewModal__email">
					cherrykinm@~~~~~~~~.kr</div>
			</div>
			<div class="modalBody__line d-flex">
				<div class="modalBody__title d-flex">전화</div>
				<div class="modalBody__content addBookViewModal__number">
					010-7777-7777</div>
			</div>
			<div class="modalBody__line d-flex">
				<div class="modalBody__title d-flex">회사</div>
				<div class="modalBody__content addBookViewModal__company">
					체리홍삼(주)</div>
			</div>
		</div>
		<div class="addBookModal__btns d-flex">
			<div class="addBookModal__cancelBtnCover">
				<button class="addBookModal__cancelBtn mr15">닫기</button>
			</div>
			<div class="addBookModal__insertBtnCover">
				<button class="addBookModal__insertBtn">저장</button>
			</div>
		</div>
	</div>

	<script>
		$(document).on("click", ".addBookViewModal__header .favorites__icon", function() {
			if ($(this).hasClass("chk")) {
				$(this).removeClass("chk");
			} else {
				$(this).addClass("chk");
			}
		});
	</script>
</body>
</html>