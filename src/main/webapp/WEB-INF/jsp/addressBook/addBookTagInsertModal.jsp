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
</head>
<body>
	<div class="addBookTagInsertModal modal">
		<div class="addBookModal__title">태그 만들기</div>
		<div class="addBookModal__modalBody">
			<input type="text" class="modalBody__input modalBody__tagName"
				placeholder="새로 생성할 태그명을 입력하세요">
		</div>
		<div class="addBookModal__btns d-flex">
			<div class="addBookModal__cancelBtnCover mr15">
				<button class="addBookModal__cancelBtn">취소</button>
			</div>
			<div class="addBookModal__insertBtnCover">
				<button class="addBookModal__insertBtn" id="addressBookTagInsert">저장</button>
			</div>
		</div>
	</div>
</body>
</html>