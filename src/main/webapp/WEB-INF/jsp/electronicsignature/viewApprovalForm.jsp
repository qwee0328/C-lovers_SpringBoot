<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결재</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<!-- css, js -->
<link rel="stylesheet" href="/css/electronicsignature/viewApprovalForm.css">
<script src="/js/electronicsignature/viewApprovalForm.js"></script>
</head>
<body>
	<%@ include file="../commons/header.jsp" %>
	
	<div class="container">
		<%@ include file="../electronicsignature/electronicsignatureNaviBar.jsp" %>

		<div class="container__approvalForm">	
			<div class="approvalForm__formTitle">${documentInfo.get(0).document_type_id }</div>
			
			<table class="approvalForm__documentInfoTable">
				<colgroup>
					<col style="width: 12.09%;">
					<col style="width: 37.21%;">
					<col style="width: 12.09%;">
					<col style="width: 37.62%;">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">문서 종류</th>
						<td id="document_type">${documentInfo.get(0).document_type_id }</td>
						<th scope="row">문서 번호</th>
						<td id="document_id">${documentInfo.get(0).id }</td>
					</tr>
					<tr>
						<th scope="row">기안 부서</th>
						<td>${draftersInfo.get(0).task_name }</td>
						<th scope="row">기안자</th>
						<td id="drafter">${documentInfo.get(0).emp_id }</td>
					</tr>
					<tr>
						<th scope="row">보존 연한</th>
						<td>3년</td>
						<th scope="row">보안 등급</th>
						<td>${documentInfo.get(0).security_grade }</td>
					</tr>
					<tr>
						<th scope="row">기안 일시</th>
						<td>${documentInfo.get(0).report_date }</td>
						<th scope="row">완료 일시</th>
						<td>${documentInfo.get(0).approval_date }</td>
					</tr>
				</tbody>
			</table>
			<table class="approvalForm__approvalInfoTable">
				<colgroup>
					<col style="width: 12.09%;">
					<col style="width: 37.62%;">
					<col style="width: 12.09%;">
					<col style="width: 38.02%;">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" class="approvalInfoTable__agree">
							<div class="agree__choice">신청</div>
						</th>
						<td>
							<table class="approvalApplicationTable">
								<colgroup>
									<col width>
									<col width>
									<col width>
									<col width>
								</colgroup>
								<tbody>
									<tr>
										<!-- 기안자가 존재하는 만큼만 출력하고 나머지는 빈 값 출력 -->
										<c:forEach var="item" items="${draftersInfo}" varStatus="loop">
										    <td class="team__name">${item.job_name}</td>
										</c:forEach>
										
										<c:forEach begin="${draftersInfo.size() + 1}" end="4">
										    <td class="team__name"></td>
										</c:forEach>
									</tr>
									<tr>
										<td class="stamp"></td>
										<td class="stamp"></td>
										<td class="stamp"></td>
										<td class="stamp"></td>
									</tr>
									<tr>
										<!-- 기안자가 존재하는 만큼만 출력하고 나머지는 빈 값 출력 -->
										<c:forEach var="item" items="${draftersInfo}" varStatus="loop">
										    <td class="name">${item.name}</td>
										</c:forEach>
										
										<c:forEach begin="${draftersInfo.size() + 1}" end="4">
										    <td class="name"></td>
										</c:forEach>
									</tr>
								</tbody>
							</table>
						</td>
						<th scope="row" class="approvalInfoTable__agree">
							<div class="agree__choice">처리</div>
						</th>
						<td>
							<table class="approvalProcessTable">
								<colgroup>
									<col width>
									<col width>
									<col width>
									<col width>
								</colgroup>
								<tbody>
									<tr>
										<!-- 결재자가 존재하는 만큼만 출력하고 나머지는 빈 값 출력 -->
										<c:forEach var="item" items="${approversInfo}" varStatus="loop">
										    <td class="team__name">${item.job_name}</td>
										</c:forEach>
										
										<c:forEach begin="${approversInfo.size() + 1}" end="4">
										    <td class="team__name"></td>
										</c:forEach>
									</tr>
									<tr>
										<td class="stamp"></td>
										<td class="stamp"></td>
										<td class="stamp"></td>
										<td class="stamp"></td>
									</tr>
									<tr>
										<c:forEach var="item" items="${approversInfo}" varStatus="loop">
										    <td class="name">${item.name}</td>
										</c:forEach>
										
										<c:forEach begin="${approversInfo.size() + 1}" end="4">
										    <td class="name"></td>
										</c:forEach>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
			<hr>
			<c:choose>
				<c:when test="${documentInfo.get(0).document_type_id == '휴가 신청서'}">
					<div class="vacation__title">${documentInfo.get(0).title}</div>
					<table class="vacation__table">
						<colgroup>
							<col style="width: 160px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th>사용자</th>
								<td>${mainDrafter.drafter_name }</td>
							</tr>
							<tr>
								<th>부서</th>
								<td>${mainDrafter.task_name }</td>
							</tr>
							<tr>
								<th>휴가 신청</th>
								<td id="vacation_date"></td>
							</tr>
							<tr>
								<th>사유</th>
								<td id="vacation_reason"></td>
							</tr>
						</tbody>
					</table>
				</c:when>
				<c:when test="${documentInfo.get(0).document_type_id == '지출 결의서'}">
					<div>지출 결의서</div>
				</c:when>
				<c:when test="${documentInfo.get(0).document_type_id == '업무연락'}">
					<div>업무 연락</div>
				</c:when>
			</c:choose>
		</div>
	</div>
</body>
</html>