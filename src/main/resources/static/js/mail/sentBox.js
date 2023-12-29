$(document).ready(function() {
	$.ajax({
        url: "/mail/sentBoxList?cpage=",
        type: 'POST'
    }).done(function (resp) {
        mailList(resp.mail, resp.send_date, resp.recordTotalCount);
        pagination(resp.recordTotalCount, resp.recordCountPerPage, resp.naviCountPerPage, resp.lastPageNum);
    })
    
    // 메일 리스트 출력 함수
	function mailList(mail, send_date, mailCount) {
	    $(".inBox__mailListBox").empty();
	    
	    $(".bottom__mailNum").html("편지 수 : " + mailCount);
	
	    for (let i = 0; i < mail.length; i++) {
	        let mailListDiv = $("<div>");
	        mailListDiv.addClass("sentBox__mailList");
	
	        let checkboxDiv = $("<input type='checkbox'>");
	        checkboxDiv.attr("name", "selectedMails");
	        checkboxDiv.attr("value", mail[i].id);
	        checkboxDiv.addClass("mailList__checkbox");
	
	        let nameDiv = $("<div>");
	        nameDiv.addClass("mailList__name");
	        nameDiv.html(mail[i].receiver_name);
	
	        let titleDiv = $("<a>");
	        titleDiv.attr("href", "/mail/read?id=" + mail[i].id);
	        titleDiv.addClass("mailList__title");
	        titleDiv.html(mail[i].title);
	
	        let fileIconDiv = $("<i>");
	        $.ajax({
	            url: "/mail/haveFile",
	            data: { email_id: mail[i].id }
	        }).done(function (resp2) {
	            if (resp2 == true) {
	                fileIconDiv.addClass("fa-solid");
	                fileIconDiv.addClass("fa-paperclip");
	                fileIconDiv.addClass("right__file");
	            }
	        })
	
	        let dateDiv = $("<div>");
	        dateDiv.addClass("right__date");
	        dateDiv.html(send_date[i]);
	        
	        let confirmDiv = $("<div>");
	        confirmDiv.addClass("right__confirm");
	        
	        let cancellationDiv = $("<a>");
	        cancellationDiv.addClass("right__cancallation");
	        cancellationDiv.html("발송 취소");
	        
	        if(mail[i].confirmation) {
	        	confirmDiv.html("읽음");
	        	mailListDiv.append(checkboxDiv).append(nameDiv).append(titleDiv).append(fileIconDiv).append(dateDiv).append(confirmDiv);
	        } else {
	        	mailListDiv.append(checkboxDiv).append(nameDiv).append(titleDiv).append(fileIconDiv).append(dateDiv).append(cancellationDiv);
	        }
	
	        $(".inBox__mailListBox").append(mailListDiv);
	    }
	}
	
	//	체크박스 전체 선택 클릭 시 
	$(document).on("change", ".allCheck__checkbox", function() {
		let checkAll = $(this).is(":checked");
		
		if(checkAll) {
			$(".mailList__checkbox").prop("checked", true);
			$(".mailList__checkbox").parent().css("background-color", "#DCEDD4");
		} else {
			$(".mailList__checkbox").prop("checked", false);
			$(".mailList__checkbox").parent().css("background-color", "");
		}
	})
	
	// 체크박스 개별 클릭 시
	$(document).on("change", ".mailList__checkbox", function() {
		let check = $(this).is(":checked");
		if(check) {
			$(this).prop("checked", true);
			$(this).parent().css("background-color", "#DCEDD4");
		} else {
			$(this).prop("checked", false);
			$(this).parent().css("background-color", "");
		}
	})
	
	// 삭제 버튼 클릭 시
	$("#deleteMail").on("click", function () {
	    let selectedMails = [];
	    $(".mailList__checkbox:checked").each(function () {
	        selectedMails.push($(this).val());
	    });
	
	    if (selectedMails.length > 0) {
	        $.ajax({
	            type: "POST",
	            url: "/mail/deleteMail",
	            data: { selectedMails: selectedMails }
	        }).done(function () {
	            alert("선택한 메일이 휴지통으로 이동했습니다.");
	            location.reload();
	        });
	    } else {
	        alert("삭제할 메일을 선택해주세요.");
	    }
	})
	
	// 완전 삭제 버튼 클릭 시
	$("#perDeleteMail").on("click", function () {
	    let result = confirm("메일을 완전삭제하시겠습니까? 삭제된 메일은 복구되지 않습니다.");
	    if (result) {
	        let selectedMails = [];
	        $(".mailList__checkbox:checked").each(function () {
	            selectedMails.push($(this).val());
	        });
	
	        if (selectedMails.length > 0) {
	            $.ajax({
	                type: "POST",
	                url: "/mail/perDeleteMail",
	                data: { selectedMails: selectedMails }
	            }).done(function (resp) {
	            	alert("선택한 메일이 완전삭제되었습니다.");
	                location.reload();
	            });
	        } else {
	            alert("완전삭제할 메일을 선택해주세요.");
	        }
	    }
	});
	
	// 발송 취소 버튼 클릭 시
	$(document).on("click", ".right__cancallation", function() {
		let result = confirm("발송을 취소하시겠습니까? 취소한 메일은 복구되지 않습니다.");
		let id = $(this).siblings(".mailList__checkbox").val();
		
		if(result) {
			$.ajax({
				type: "POST",
				url: "/mail/cancelSend",
				data: { id : id }
			}).done(function(resp){
				alert("취소한 메일이 완전삭제 되었습니다.");
				location.reload();
			});
		}
	})
	
	// 페이지네이션
	function pagination(recordTotalCount, recordCountPerPage, naviCountPerPage, lastPageNum) {
		$(".bottom__pageNavi").empty();
		
		if(recordTotalCount != 0) {
	
			let pageTotalCount = 0;
			pageTotalCount = Math.ceil(recordTotalCount / recordCountPerPage);
			
			let currentPage = lastPageNum;
			
			// 비정상 접근 차단
			if(currentPage < 1) {
				currentPage = 1;
			} else if (currentPage > pageTotalCount) {
				currentPage = pageTotalCount;
			}
			
			let startNavi = Math.floor((currentPage - 1) / naviCountPerPage) * naviCountPerPage + 1;
			let endNavi = startNavi + (naviCountPerPage - 1);
			if(endNavi > pageTotalCount) {
				endNavi = pageTotalCount;
			}
			
			let needPrev = true;
			let needNext = true;
			
			if(startNavi == 1) {
				needPrev = false;
			}
			
			if(endNavi == pageTotalCount) {
				needNext = false;
			}
			
			let pagination = $(".bottom__pageNavi");
			if (startNavi != 1) {
				let divTag = $("<div>");
				divTag.attr("href", "/mail/sentBoxList?cpage=1");
				let iTag = $("<i>");
				iTag.addClass("fa-solid fa-angles-left");
				divTag.append(iTag);
				pagination.append(divTag);
			}
	
			if (needPrev) {
				let divTag = $("<div>");
				divTag.attr("href", "/mail/sentBoxList?cpage=" + (startNavi - 1));
				let iTag = $("<i>");
				iTag.addClass("fa-solid fa-chevron-left");
				divTag.append(iTag);
				pagination.append($(divTag));
			}
	
			for (let i = startNavi; i <= endNavi; i++) {
				let divTag = $("<div>");
				divTag.text(i);
				divTag.attr("href", "/mail/sentBoxList?cpage=" + i);
				if (i == currentPage) {
					divTag.addClass("pageNavi__circle");
				}
				pagination.append(divTag);
			}
	
			if (needNext) {
				let divTag = $("<div>");
				divTag.attr("href", "/mail/sentBoxList?cpage=" + (endNavi + 1));
				let iTag = $("<i>");
				iTag.addClass("fa-solid fa-chevron-right");
				divTag.append(iTag);
				pagination.append(divTag);
			}
	
			if (endNavi != pageTotalCount) {
				let divTag = $("<div>");
				divTag.attr("href", "/mail/sentBoxList?cpage="+pageTotalCount);
				let iTag = $("<i>");
				iTag.addClass("fa-solid fa-angles-right");
				divTag.append(iTag);
				pagination.append(divTag);
			}
		}
	}
	
	// 페이지네이션 클릭 시 메일 리스트 출력
	$(document).on("click", ".bottom__pageNavi>div", function () {
	    let pageUrl = $(this).attr("href");
	    $.ajax({
	        url: pageUrl,
	        type: 'POST'
	    }).done(function (resp) {
	        mailList(resp.mail, resp.send_date, resp.recordTotalCount);
        	pagination(resp.recordTotalCount, resp.recordCountPerPage, resp.naviCountPerPage, resp.lastPageNum);
	    })
	})
})
