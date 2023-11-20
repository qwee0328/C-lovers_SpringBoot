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
			<div class="approvalForm__formTitle">휴가 신청서</div>
			
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
						<td>${documentInfo.get(0).document_type_id }</td>
						<th scope="row">문서 번호</th>
						<td>${documentInfo.get(0).document_id }</td>
					</tr>
					<tr>
						<th scope="row">기안 부서</th>
						<td>${documentInfo.get(0).drafter_dept_name }</td>
						<th scope="row">기안자</th>
						<td>${documentInfo.get(0).drafter_name }</td>
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
										<td class="team__name">${documentInfo.get(0).drafter_rank }</td>
										<td class="team__name"></td>
										<td class="team__name"></td>
										<td class="team__name"></td>
									</tr>
									<tr>
										<td class="stamp"></td>
										<td class="stamp"></td>
										<td class="stamp"></td>
										<td class="stamp"></td>
									</tr>
									<tr>
										<td class="name">${documentInfo.get(0).drafter_name }</td>
										<td class="name"></td>
										<td class="name"></td>
										<td class="name"></td>
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
										<c:forEach var="item" items="${approver_rank}" varStatus="loop">
										    <td class="team__name">${item.job_name}</td>
										</c:forEach>
										
										<c:forEach begin="${approver_rank.size() + 1}" end="4">
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
										<c:forEach var="item" items="${approver_rank}" varStatus="loop">
										    <td class="name">${item.name}</td>
										</c:forEach>
										
										<c:forEach begin="${approver_rank.size() + 1}" end="4">
										    <td class="name"></td>
										</c:forEach>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>