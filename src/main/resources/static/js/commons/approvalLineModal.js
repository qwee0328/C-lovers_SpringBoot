let processUserID = []; // 지금까지 처리로 체크된 User 아이디
let applicationUserID = []; // 지금까지 신청으로 체크된 User 아이디
$(document).ready(function() {
	// 처리 인원 처음에 초기화
	$(".process span").text(processUserID.length);

	// 로그인 아이디 해당 정보 불러오기
	$.ajax({
		url: "/office/searchUserAjax",
		dataType: "json",
		data: { keyword: $("#loginID").val() },
		type: "POST"
	}).done(function(resp) {
		console.log("ㄱ")
		let userInfo = $("<div>")
			.attr("class", "userInfo").attr("id", resp[0].id)
			.html(resp[0].name + " (" + resp[0].task_name + ")");
		$(".approvalLine__applyEmployee").append(userInfo);
		applicationUserID.push(resp[0].id);
		$(".application span").text(applicationUserID.length);
	})

	// 모달창 시작할 때 인원수 정보 불러오기 -> 맨 왼쪽 부서
	// 회사 총 인원 수
	$.ajax({
		url: "/office/empCount",
		dataType: "json",
		type: "POST"
	}).done(function(resp) {
		console.log("ㄴ")
		$("#officeEmpCount").text("(" + resp + ")");
	})

	// 부서별 총 인원 수
	$.ajax({
		url: "/office/selectDeptInfo",
		type: "POST",
	}).done(function(resp) {
		console.log("ㄷ")
		let managementId = "";
		for (let i = 0; i < resp.length; i++) {
			let deptBox = $("<div>").attr("class", "deptBox").attr("dept", resp[i].dept_name);
			let dept = $("<div>").attr("class", "dept").attr("dept_id", resp[i].department_id);
			let deptIcon = $("<i>")
			if (resp[i].dept_name === "관리부") {
				dept.addClass("pagePlus").addClass("selected");
				deptIcon.addClass("fa-solid fa-minus");
				managementId = resp[i].department_id
			} else {
				deptIcon.addClass("fa-solid fa-plus");
			}
			let deptName = $("<span>").text(" " + resp[i].dept_name);
			let deptCount = $("<span>").text(" (" + resp[i].count + ")");

			dept.append(deptIcon).append(deptName).append(deptCount);
			deptBox.append(dept);
			$(".department__body").append(deptBox);
		}

		// 팀 별 총 인원수
		$.ajax({
			url: "/office/selectTaskInfo",
			type: "POST",
		}).done(function(resp) {
			console.log("ㄹ")
			for (let i = 0; i < resp.length; i++) {
				let deptBox = $(".deptBox[dept=" + resp[i].dept_name + "]")
				let dept_task = $("<div>").attr("class", "dept_task").attr("task_id", resp[i].task_id);
				if (resp[i].dept_name !== "관리부") {
					dept_task.css("display", "none");
				}
				let taskName = $("<span>").text(resp[i].task_name);
				let taskCount = $("<span>").text(" (" + resp[i].count + ")");

				dept_task.append(taskName).append(taskCount);
				deptBox.append(dept_task);
			}
			// 관리부 인원 불러오기
			selectDepartmentEmpInfo(managementId)

		});
	});


	// 검색 박스 테두리 설정
	$("#searchInput").on("blur", function() {
		$(".search").css({ border: "1px solid #7d7d7d40", borderRadius: "4px" });
	});
	$("#searchInput").on("focus", function() {
		$(".search").css({ border: "1px solid #20412E", borderRadius: "4px" });
	});

	// 조직도 클릭했을 때 변화
	$(document).on("click", ".company, .dept", function() {
		if (// 회사 이름 축소
			$(this).attr("class").includes("company") &&
			$(this).attr("class").includes("pagePlus")
		) {
			$(".deptBox").css("display", "none");
			$(this).children("i").attr("class", "fa-solid fa-plus");
			$(this).attr("class", "company");
			$(this).addClass("selected");

			selectAllEmpInfo();
		} else if ( // 회사 이름 확대
			$(this).attr("class").includes("company") &&
			!$(this).attr("class").includes("pagePlus")
		) {
			$(this).children("i").attr("class", "fa-solid fa-minus");
			$(".deptBox").css("display", "block");
			$(this).addClass("pagePlus");
			if ($(".dept.pagePlus.selected").length !== 0) {
				$(this).removeClass("selected");
			}
			selectDepartmentEmpInfo($(".dept.pagePlus.selected").attr("dept_id"));

		} else if ( // 부서 명 축소
			$(this).attr("class").includes("dept") &&
			$(this).attr("class").includes("pagePlus")
		) {
			console.log($(this));
			$(this).parent().children(".dept_task").css("display", "none");
			$(this).children("i").attr("class", "fa-solid fa-plus");
			$(this).attr("class", "dept");
			console.log($(this).parent().children(".dept.pagePlus").first());
			if ($(this).parent().parent().find(".dept.pagePlus").first().length <= 0) {
				$(".company.pagePlus").attr("class", "company pagePlus selected");
				selectAllEmpInfo();
			} else {
				if ($(".dept_task.selected").first().length <= 0) {
					console.log("팀명 클릭되어있음")
					$(this).parent().parent().find(".dept.pagePlus").first().addClass("selected");
					selectDepartmentEmpInfo($(".dept.pagePlus.selected").attr("dept_id"));
				} else{
					console.log("팀명 클릭되어있음2")
					console.log($(this))
					console.log($(".selected").parent().find(".dept"))
				}
			}
		} else if ( // 부서 명 확대
			$(this).attr("class").includes("dept") &&
			!$(this).attr("class").includes("pagePlus")
		) {
			$(this).children("i").attr("class", "fa-solid fa-minus");
			$(this).parent().children(".dept_task").css("display", "block");
			$(this).addClass("pagePlus").addClass("selected");

			// 나머지 .dept_task에서 removeClass("selected")
			$(".dept").not($(this).parent().children(".dept")).removeClass("selected");
			$(".company").removeClass("selected");
			$(".dept_task").removeClass("selected");
			selectDepartmentEmpInfo($(this).attr("dept_id"));
		}
		$(".empChk").prop("checked", false);
		$(".updateBtns button").addClass("disabled");
	});

	// 팀별 인원 불러오기
	$(document).on("click", ".dept_task", function() {
		$.ajax({
			url: "/office/selectDetpTaskEmpInfo",
			data: { task_id: $(this).attr("task_id") },
			type: "POST",
		}).done(function(resp) {
			$(".employee__List").html("");
			for (let i = 0; i < resp.length; i++) {
				let employee__check = $("<div>").attr("class", "employee__check");
				let empChk = $("<input>", { type: "checkbox" }).attr("class", "empChk").attr("id", resp[i].id);
				let label = $("<label>").attr("for", resp[i].id);
				let nameSpan = $("<span>").text(resp[i].name);
				let taskSpan = $("<span>").text(" (" + resp[i].task_name + ")");
				label.append(nameSpan).append(taskSpan)
				employee__check.append(empChk).append(label);
				$(".employee__List").append(employee__check);
			}
		});
		$(".dept_task").removeClass("selected");
		$(this).addClass("selected");
		$(".dept").removeClass("selected");
	});

	// 체크박스 모두 선택 혹은 모두 취소
	$(".allCheck").on("click", function() {
		$(".empChk").prop("checked", true);
		if ($("#modalType").val() === "휴가신청") {
			$(".updateBtns>.updateBtns__add #processBtn").removeClass("disabled");
		} else {
			$(".updateBtns>.updateBtns__add button").removeClass("disabled");
		}
	});
	$(".allNonCheck").on("click", function() {
		$(".empChk").prop("checked", false);
		if ($("#modalType").val() === "휴가신청") {
			$(".updateBtns>.updateBtns__add #processBtn").addClass("disabled");
		} else {
			$(".updateBtns>.updateBtns__add button").addClass("disabled");
		}
	});

	// 체크박스가 선택 되어있을때 추가 삭제 버튼 활성화
	//let checkedCount = $("input[type='checkbox'].empChk:checked").length;
	//if (checkedCount === 0) {
	//$(".updateBtns button").addClass("disabled");
	//}
	$(document).on("change", "input[type='checkbox'].empChk", function() {
		let checkedCount = $("input[type='checkbox'].empChk:checked").length;
		if (checkedCount === 0) {
			$(".updateBtns>.updateBtns__add button").addClass("disabled");
		} else {
			if ($("#modalType").val() === "휴가신청") {
				$(".updateBtns>.updateBtns__add #processBtn").removeClass("disabled");
			} else {
				$(".updateBtns>.updateBtns__add button").removeClass("disabled");
			}
		}
	});

	// 추가 버튼 눌렀을 때 반영
	$("#applyBtn, #processBtn").on("click", function() {
		if ($("#modalType").val() === "휴가신청") {
			if ($(this).attr("id") !== "applyBtn") {
				processBtnClick(this);
				$(".process span").text(processUserID.length);
			}
		} else {
			if ($(this).attr("id") === "applyBtn") {
				console.log("숫자 카운트")
				applyBtnClick(this);
				$(".application span").text(applicationUserID.length);
			} else {
				processBtnClick(this);
				$(".process span").text(processUserID.length);
			}
		}
	});

	// 삭제할 user 눌렀을 때 반응
	$(document).on("click", ".userInfo", function() {
		if ($("#modalType").val() === "휴가신청") {
			if ($(this).parent().attr("class") === "approvalLine__processEmployee") {
				userInfoCheck(this);
			}
		} else {
			userInfoCheck(this);
		}
	});

	// user를 선택하고 삭제 버튼을 눌렀을 때
	$("#cancleApplyBtn, #cnacleProcessBtn").on("click", function() {
		if ($("#modalType").val() === "휴가신청") {
			if ($(this).attr("id") !== "cancleApplyBtn") {
				cancleProcessBtnClick(this);
			}
		} else {
			if ($(this).attr("id") === "cancleApplyBtn") {
				cancleApplyBtnClick(this);
			} else {
				cancleProcessBtnClick(this);
			}
		}
	});

	// 확인 버튼을 눌렀을 때 처리자가 존재하면 처리자를 넘겨주면서 모달창 닫기
	// 처리자가 없으면 창 못닫도록 변경
	$(".saveBnt").on("click", function() {
		if (!$(this).hasClass("disabled")) {
			if ($("#modalType").val() !== "휴가신청") {
				let applicationEmpList = [];
				let applicationEmpIDList = [];
				$(".approvalLine__applyEmployee .userInfo").each(function() {
					applicationEmpList.push($(this).html());
					applicationEmpIDList.push($(this).attr("id"));
				})
				$("#applicationEmployeeList").val(applicationEmpList);
				$("#applicationEmployeeIDList").val(applicationEmpIDList);

				$.ajax({
					url: "/electronicsignature/selectEmpJobLevel",
					type: "POST",
					data: { userList: applicationUserID },
				}).done(function(resp) {
					$(".table__applyLine").html("");
					let approvalLineText = "";
					let userCount = 0;
					for (let i = 0; i < resp.length; i++) {
						approvalLineText = approvalLineText + resp[i].name + " (" + resp[i].task_name + ")";
						if (i !== resp.length - 1) {
							approvalLineText = approvalLineText + "&nbsp;&nbsp;&nbsp;<i class='fas fa-circle'></i>&nbsp;&nbsp;&nbsp;";
						}
						userCount++;
						if (userCount % 5 === 0 || userCount === resp.length) {
							let lineDiv = $("<div>").attr("class", "lineDiv");
							lineDiv.html(approvalLineText);
							$(".table__applyLine").append(lineDiv);
							approvalLineText = "";
						}
					}
				})
			}

			let processEmployeeList = [];
			let processEmployeeIDList = [];
			$(".approvalLine__processEmployee .userInfo").each(function() {
				processEmployeeList.push($(this).html());
				processEmployeeIDList.push($(this).attr("id"))
				console.log(processEmployeeList)
			})
			$("#processEmployeeList").val(processEmployeeList);
			$("#processEmployeeIDList").val(processEmployeeIDList);

			$.ajax({
				url: "/electronicsignature/selectEmpJobLevel",
				type: "POST",
				data: { userList: processUserID },
			}).done(function(resp) {
				$(".table__approvalLine").html("");
				let approvalLineText = "";
				let userCount = 0;
				console.log("selectempjoblebel")
				for (let i = resp.length - 1; i >= 0; i--) {
					approvalLineText = approvalLineText + resp[i].name + " (" + resp[i].task_name + ")";
					if (i !== 0) {
						if (resp[i].sec_level > resp[i - 1].sec_level) {
							approvalLineText = approvalLineText + "&nbsp;&nbsp;&nbsp;<i class='fa-solid fa-angle-right'></i>&nbsp;&nbsp;&nbsp;";
						} else {
							approvalLineText = approvalLineText + "&nbsp;&nbsp;&nbsp;<i class='fas fa-circle'></i>&nbsp;&nbsp;&nbsp;";

						}
					}
					userCount++;
					if (userCount % 5 === 0 || userCount === resp.length) {
						let lineDiv = $("<div>").attr("class", "lineDiv");
						lineDiv.html(approvalLineText);
						$(".table__approvalLine").append(lineDiv);
						approvalLineText = "";
					}
				}
			})
			$.modal.close();
		}
	});
});

// 회사내 모든 인원의 이름과 부서명 출력하기
function selectAllEmpInfo() {
	$.ajax({
		url: "/office/selectAllEmpInfo",
		type: "POST",
	}).done(function(resp) {
		$(".employee__List").html("");
		for (let i = 0; i < resp.length; i++) {
			let employee__check = $("<div>").attr("class", "employee__check");
			let empChk = $("<input>", { type: "checkbox" }).attr("class", "empChk").attr("id", resp[i].id);
			let label = $("<label>").attr("for", resp[i].id);
			let nameSpan = $("<span>").text(resp[i].name);
			let taskSpan = $("<span>").text(" (" + resp[i].task_name + ")");
			label.append(nameSpan).append(taskSpan)
			employee__check.append(empChk).append(label);
			$(".employee__List").append(employee__check);
		}
	});
}

// 회사 내 부서별 모든 인원의 이름과 부서명 출력하기
function selectDepartmentEmpInfo(id) {
	$.ajax({
		url: "/office/selectDepartmentEmpInfo",
		data: { dept_id: id },
		type: "POST",
	}).done(function(resp) {
		$(".employee__List").html("");
		for (let i = 0; i < resp.length; i++) {
			let employee__check = $("<div>").attr("class", "employee__check");
			let empChk = $("<input>", { type: "checkbox" }).attr("class", "empChk").attr("id", resp[i].id);
			let label = $("<label>").attr("for", resp[i].id);
			let nameSpan = $("<span>").text(resp[i].name);
			let taskSpan = $("<span>").text(" (" + resp[i].task_name + ")");
			label.append(nameSpan).append(taskSpan)
			employee__check.append(empChk).append(label);
			$(".employee__List").append(employee__check);
		}
	});
}

// 내용이 충분한지 확인
function contentCompleted() {
	console.log("확인중")
	if ($(".approvalLine__applyEmployee").html() !== "" && $(".approvalLine__processEmployee").html() !== "") {
		$(".saveBnt").removeClass("disabled");
		console.log("충분")
	} else {
		$(".saveBnt").addClass("disabled");
	}
}

// 추가 버튼 눌렀을 때 이벤트
function applyBtnClick(element) {
	let checkedLabels = []; // 현재 체크된 User의 네임
	let checkedUserID = []; // 현재 체크된 user의 아이디
	//$(".approvalLine__applyEmployee").html("");
	if (!$(element).hasClass("disabled")) {
		$(".empChk:checked").each(function() {
			checkedLabels = [];
			checkedUserID = [];
			if (!applicationUserID.includes($(this).attr("id"))) {
				checkedLabels.push(
					$(this).parent().children("label").find("span:first").text() + $(this).parent().children("label").find("span:nth-child(2)").text()
				);
				applicationUserID.push($(this).attr("id"));
				console.log(applicationUserID)
				checkedUserID.push($(this).attr("id"));
				console.log(checkedUserID)
			}
			// 체크된 checkbox의 부모인 label을 선택하여 라벨의 내용을 가져오고 배열에 추가
			console.log($(this));

			for (let i = 0; i < checkedLabels.length; i++) {
				let userInfo = $("<div>")
					.attr("class", "userInfo").attr("id", checkedUserID[i])
					.html(checkedLabels[i]);
				$(".approvalLine__applyEmployee").append(userInfo);
			}
		});
	}
	contentCompleted();
}


function processBtnClick(element) {
	let checkedLabels = []; // 현재 체크된 User의 네임
	let checkedUserID = []; // 현재 체크된 user의 아이디

	if (!$(element).hasClass("disabled")) {
		$(".empChk:checked").each(function() {
			checkedLabels = [];
			checkedUserID = [];
			if (!processUserID.includes($(this).attr("id"))) {
				console.log($(this));
				checkedLabels.push(
					$(this).parent().children("label").find("span:first").text() + $(this).parent().children("label").find("span:nth-child(2)").text()
				);
				processUserID.push($(this).attr("id"));
				checkedUserID.push($(this).attr("id"));
			}
			// 체크된 checkbox의 부모인 label을 선택하여 라벨의 내용을 가져오고 배열에 추가
			for (let i = 0; i < checkedLabels.length; i++) {
				let userInfo = $("<div>")
					.attr("class", "userInfo").attr("id", checkedUserID[i])
					.html(checkedLabels[i]);
				$(".approvalLine__processEmployee").append(userInfo);
			}
		});
	}
	contentCompleted();
}



// 삭제 버튼 눌렀을 때 이벤트
function cancleApplyBtnClick(element) {
	if (!$(element).hasClass("disabled")) {
		console.log($(element).attr("id"))
		// 선택된 userInfo div 삭제
		$(".approvalLine__applyEmployee .userInfo").filter(function() {
			let currentColor = $(this).css("backgroundColor");
			let currentId = $(this).attr("id");
			console.log(currentColor)
			if (currentColor === "rgb(243, 247, 241)" || currentColor === "#f3f7f1") {
				console.log(currentId)
				applicationUserID = applicationUserID.filter(id => id !== currentId);
				console.log(applicationUserID)
				$(this).remove();
			}
		});
		if ($(".approvalLine__applyEmployee").html() === "") {
			$(element).addClass("disabled")
			applicationUserID = [];
		}
		$(".application span").text(applicationUserID.length);
	}
	contentCompleted();
}
function cancleProcessBtnClick(element) {
	if (!$(element).hasClass("disabled")) {
		console.log($(element).attr("id"))

		$(".approvalLine__processEmployee .userInfo").filter(function() {
			let currentColor = $(this).css("backgroundColor");
			let currentId = $(this).attr("id");
			if (currentColor === "rgb(243, 247, 241)" || currentColor === "#f3f7f1") {
				processUserID = processUserID.filter(id => id !== currentId);
				$(this).remove();
			}
		});

		// 선택된 userInfo div 삭제

		if ($(".approvalLine__processEmployee").html() === "") {
			$(element).addClass("disabled");
			processUserID = [];
		}
		$(".process span").text(processUserID.length);
	}
	contentCompleted();
}

// userInfo 클릭했을 때 이벤트
function userInfoCheck(element) {
	if (
		$(element).css("backgroundColor") === "rgb(255, 255, 255)" ||
		$(element).css("backgroundColor") === "white" ||
		$(element).css("backgroundColor") === "rgba(0, 0, 0, 0)"
	) {
		$(element).css("backgroundColor", "#f3f7f1");

		// 신청의 삭제 버튼 누를 수 있음
		if ($(element).parent().attr("class") === "approvalLine__applyEmployee") {
			$("#cancleApplyBtn").removeClass("disabled");
			applicationUserID = applicationUserID.filter(function(userID) {
				return userID !== $(element).attr("id")
			})
			//$(".application span").text(applicationUserID.length);
		} else if (
			$(element).parent().attr("class") === "approvalLine__processEmployee"
		) {
			$("#cnacleProcessBtn").removeClass("disabled");
			// 처리 취소하기
			processUserID = processUserID.filter(function(userID) {
				return userID !== $(element).attr("id")
			})
			//$(".process span").text(processUserID.length);
		}
	} else {
		// 삭제할 사람을 아무도 선택하지 않음
		$(element).css("backgroundColor", "white");
		if (
			$(element)
				.parent()
				.find(".userInfo")
				.filter(function() {
					var currentColor = $(this).css("backgroundColor");
					return (
						currentColor === "rgb(243, 247, 241)" ||
						currentColor === "#f3f7f1"
					);
				}).length === 0
		) {
			if ($(element).parent().attr("class") === "approvalLine__applyEmployee") {
				$("#cancleApplyBtn").addClass("disabled");
			} else if (
				$(element).parent().attr("class") === "approvalLine__processEmployee"
			) {
				$("#cnacleProcessBtn").addClass("disabled");
			}
		}
	}
}
