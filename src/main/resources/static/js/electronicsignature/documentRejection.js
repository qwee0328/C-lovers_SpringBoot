$(document).ready(function() {
	// 전체 리스트 출력
	$.ajax({
		url: "/electronicsignature/documentRejectionList?cpage="
	}).done(function(resp){
		documentList(resp.list);
		pagination(resp.recordTotalCount, resp.recordCountPerPage, resp.naviCountPerPage, resp.lastPageNum);
	})
	
	$(document).on("click", ".document__list", function() {
		let document_id = $(this).children(".documentTable__documentId").html();
		location.href = "/electronicsignature/viewApprovalForm?document_id=" + document_id;
	})
	
	// 문서 리스트 출력
	function documentList(resp) {
		$(".document__list").empty();
		
		for(let i = 0; i < resp.length; i++) {
			let listDiv = $("<div>");
			listDiv.addClass("document__list");
			
			let idDiv = $("<div>");
			idDiv.addClass("documentTable__documentId");
			idDiv.html(resp[i].document_id);
			
			let titleDiv = $("<div>");
			titleDiv.addClass("documentTable__title");
			titleDiv.html(resp[i].title);
			
			let drafterDiv = $("<div>");
			drafterDiv.addClass("documentTable__drafter");
			drafterDiv.html(resp[i].drafter_name);
			
			let draftDateDiv = $("<div>");
			draftDateDiv.addClass("documentTable__draftDate");
			draftDateDiv.html(resp[i].report_date);
			
			let dueDateDiv = $("<div>");
			dueDateDiv.addClass("documentTable__dueDate");
			dueDateDiv.html("");
			
			let categoryDiv = $("<div>");
			categoryDiv.addClass("documentTable__documentCategory");
			categoryDiv.html(resp[i].category);
			
			let divisionDiv = $("<div>");
			divisionDiv.addClass("documentTable__division");
			divisionDiv.html(resp[i].document_type_id);
			
			listDiv.append(idDiv).append(titleDiv).append(drafterDiv).append(draftDateDiv).append(dueDateDiv).append(categoryDiv).append(divisionDiv);
			$(".documentTable__body").append(listDiv);
		}
	}
	
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
				divTag.attr("href", "/electronicsignature/documentRejectionList?cpage=1");
				let iTag = $("<i>");
				iTag.addClass("fa-solid fa-angles-left");
				divTag.append(iTag);
				pagination.append(divTag);
			}
	
			if (needPrev) {
				let divTag = $("<div>");
				divTag.attr("href", "/electronicsignature/documentRejectionList?cpage=" + (startNavi - 1));
				let iTag = $("<i>");
				iTag.addClass("fa-solid fa-chevron-left");
				divTag.append(iTag);
				pagination.append($(divTag));
			}
	
			for (let i = startNavi; i <= endNavi; i++) {
				let divTag = $("<div>");
				divTag.addClass("pageNavi__item");
				divTag.text(i);
				divTag.attr("href", "/electronicsignature/documentRejectionList?cpage=" + i);
				if (i == currentPage) {
					divTag.addClass("pageNavi__circle");
				}
				pagination.append(divTag);
			}
	
			if (needNext) {
				let divTag = $("<div>");
				divTag.attr("href", "/electronicsignature/documentRejectionList?cpage=" + (endNavi + 1));
				let iTag = $("<i>");
				iTag.addClass("fa-solid fa-chevron-right");
				divTag.append(iTag);
				pagination.append(divTag);
			}
	
			if (endNavi != pageTotalCount) {
				let divTag = $("<div>");
				divTag.attr("href", "/electronicsignature/documentRejectionList?cpage="+pageTotalCount);
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
	        documentList(resp.list)
        	pagination(resp.recordTotalCount, resp.recordCountPerPage, resp.naviCountPerPage, resp.lastPageNum);
	    })
	})
})