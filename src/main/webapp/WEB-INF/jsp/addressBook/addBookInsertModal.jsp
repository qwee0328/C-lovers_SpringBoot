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
	<div class="addBookInsertModal modal">
		<div class="addBookInsertModal__title">주소 추가</div>
		<div class="addBookInsertModal__modalBody">

			<div class="modalBody__addBookType d-flex">
				<div class="addBookType__personal align-center activeType">개인 주소록</div>
				<div class="addBookType__share align-center">공유 주소록</div>
			</div>

			<div class="modalBody__line d-flex">
				<div class="modalBody__title d-flex">이름</div>
				<div class="modalBody__content">
					<input type="text" class="modalBody__input modalBody__name"
						placeholder="이름을 입력하세요">
				</div>
			</div>
			<div class="modalBody__line d-flex">
				<div class="modalBody__title d-flex">이메일</div>
				<div class="modalBody__content">
					<input type="text" class="modalBody__input modalBody__email"
						placeholder="이메일을 입력하세요">
				</div>
				<div class="modalBody__plusBtn">
					<i class="fa-solid fa-plus"></i>
				</div>
			</div>
			<div class="modalBody__line d-flex">
				<div class="modalBody__title d-flex">전화</div>
				<div class="modalBody__content d-flex">
					<div class="modalBody__numberTypeCover">
						<select name="modalBody__numberType" class="modalBody__numberType">
							<option value="1">휴대폰</option>
							<option value="2">집</option>
							<option value="3">회사</option>
							<option value="4">Fax</option>
						</select> <span class="numberType__arrow"><i
							class="fa-solid fa-chevron-down"></i></span>
					</div>
					<input type="text" class="modalBody__input modalBody__number">
				</div>
				<div class="modalBody__plusBtn">
					<i class="fa-solid fa-plus"></i>
				</div>
			</div>
			<div class="modalBody__line d-flex">
				<div class="modalBody__title d-flex">태그</div>
				<div class="modalBody__content">
					<div class="modalBody__tagCover">
						<select name="modalBody__tag"
							class="modalBody__input modalBody__tag" placeholder="선택">
							<option value="" selected disabled>선택</option>
							<option value="0">ceo</option>
						</select> <span class="tag__arrow"><i
							class="fa-solid fa-chevron-down"></i></span>
					</div>
				</div>
				<div class="modalBody__plusBtn">
					<i class="fa-solid fa-plus"></i>
				</div>
			</div>
			<div class="modalBody__line d-flex">
				<div class="modalBody__title d-flex">생일</div>
				<div class="modalBody__content d-flex">
					<div class="modalBody__birthTypeCover">
						<select name="modalBody__birthType" class="modalBody__birthType">
							<option value="1">양력</option>
							<option value="2">음력</option>
						</select> <span class="birthType__arrow"><i
							class="fa-solid fa-chevron-down"></i></span>
					</div>
					<input type="text" class="modalBody__input modalBody__birth"
						placeholder="YYYYMMDD">
				</div>
			</div>
			<div class="modalBody__line d-flex">
				<div class="modalBody__title d-flex">홈페이지</div>
				<div class="modalBody__content modalBody__contentFull">
					<input type="text" class="modalBody__input modalBody__homepage">
				</div>
			</div>
			<div class="modalBody__line d-flex">
				<div class="modalBody__title d-flex">회사</div>
				<div
					class="modalBody__content modalBody__contentFull d-flex modalBody__companyInfo">
					<input type="text" class="modalBody__input modalBody__company mr10"
						placeholder="회사"> <input type="text"
						class="modalBody__input modalBody__dept mr10" placeholder="부서">
					<input type="text" class="modalBody__input modalBody__position"
						placeholder="직급">
				</div>
			</div>
			<div class="modalBody__line d-flex">
				<div class="modalBody__title d-flex">주소</div>
				<div class="modalBody__content modalBody__contentFull d-flex">
					<div class="modalBody__addressTypeCover">
						<select name="modalBody__addressType"
							class="modalBody__addressType">
							<option value="1">회사</option>
							<option value="2">집</option>
							<option value="3">기타</option>
						</select> <span class="addressType__arrow"><i
							class="fa-solid fa-chevron-down"></i></span>
					</div>
					<input type="text" class="modalBody__input modalBody__address"
						placeholder="주소를 입력하세요">
				</div>
				<div class="modalBody__plusBtn modalBody__plusBtnFull">
					<i class="fa-solid fa-plus"></i>
				</div>
			</div>
			<div class="modalBody__line d-flex">
				<div class="modalBody__title d-flex">메모</div>
				<div class="modalBody__content modalBody__contentFull d-flex">
					<div class="modalBody__memo" contenteditable="true"
						placeholder="내용을 입력하세요"></div>
				</div>
			</div>
		</div>
		<div class="addBookModal__btns d-flex">
			<div class="addBookInsertModal__cancelBtnCover">
				<button class="addBookModal__cancelBtn">취소</button>
			</div>
			<div class="addBookInsertModal__insertBtnCover">
				<button class="addBookModal__insertBtn">저장</button>
			</div>
		</div>
	</div>

	<script>
		$(document).ready(function() {
			$(".modalBody__tag").val("");
		});
	</script>
</body>
</html>