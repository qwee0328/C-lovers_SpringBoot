$(document).ready(function() {	
	// 전체 리스트 출력
	$.ajax({
		url: "/electronicsignature/temporaryList?cpage="
	}).done(function(resp){
		documentList(resp.list);
		pagination(resp.recordTotalCount, resp.recordCountPerPage, resp.naviCountPerPage, resp.lastPageNum);
	})
	
	// 문서 리스트 출력
	function documentList(resp) {
		$(".document__list").empty();
		
		for(let i = 0; i < resp.length; i++) {
			let listDiv = $("<div>");
			listDiv.addClass("document__list");

			let titleDiv = $("<div>");
			titleDiv.addClass("documentTable__title");
			titleDiv.html(resp[i].title);
			
			let categoryDiv = $("<div>");
			categoryDiv.addClass("documentTable__documentCategory");
			categoryDiv.html(resp[i].document_type_id);
			
			listDiv.append(titleDiv).append(categoryDiv);
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
				divTag.attr("href", "/electronicsignature/temporaryList?cpage=1");
				let iTag = $("<i>");
				iTag.addClass("fa-solid fa-angles-left");
				divTag.append(iTag);
				pagination.append(divTag);
			}
	
			if (needPrev) {
				let divTag = $("<div>");
				divTag.attr("href", "/electronicsignature/temporaryList?cpage=" + (startNavi - 1));
				let iTag = $("<i>");
				iTag.addClass("fa-solid fa-chevron-left");
				divTag.append(iTag);
				pagination.append($(divTag));
			}
	
			for (let i = startNavi; i <= endNavi; i++) {
				let divTag = $("<div>");
				divTag.addClass("pageNavi__item");
				divTag.text(i);
				divTag.attr("href", "/electronicsignature/temporaryList?cpage=" + i);
				if (i == currentPage) {
					divTag.addClass("pageNavi__circle");
				}
				pagination.append(divTag);
			}
	
			if (needNext) {
				let divTag = $("<div>");
				divTag.attr("href", "/electronicsignature/temporaryList?cpage=" + (endNavi + 1));
				let iTag = $("<i>");
				iTag.addClass("fa-solid fa-chevron-right");
				divTag.append(iTag);
				pagination.append(divTag);
			}
	
			if (endNavi != pageTotalCount) {
				let divTag = $("<div>");
				divTag.attr("href", "/electronicsignature/temporaryList?cpage="+pageTotalCount);
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