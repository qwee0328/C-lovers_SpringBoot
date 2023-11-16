$(document).on(
	"click",
	".modalBody__plusBtn>i",
	function() {
		if ($(this).closest(".modalBody__line").find(
			".modalBody__input").val() == ""
			|| $(this).closest(".modalBody__line").find(
				"select option:selected").val() == "") {
			Swal.fire({
				icon: "error",
				text: "내용을 먼저 채워주세요."
			});
			return false;
		}

	});

$(document).ready(function(){
	$(".modalBody__addBookType>div").on("click", function() {
	$(".activeType").removeClass("activeType");
	$(this).addClass("activeType");
	});
	
	$("#modalOpen").on("click", function() {
		console.log("클릭");
		$(".addBookInsertModal").modal({
			showClose: false
		});
	});
	
	$(".addBookModal__cancelBtn").on("click",function(){
		$.modal.close();
	})
});


$(document).on("click", ".addList__addessLine .favorites__icon", function() {
	if ($(this).hasClass("chk")) {
		$(this).removeClass("chk");
	} else {
		$(this).addClass("chk");
	}
});

$(document).on("change", ".addessLine__chkBox", function() {
	if ($(this).is(":checked")) {
		$(this).closest(".addList__addessLine").css("backgroundColor", "#DCEDD4");
	} else {
		$(this).closest(".addList__addessLine").css("backgroundColor", "transparent");
	}

	if ($(".addListHeader__chkBox").is(":checked")) $(".addListHeader__chkBox").prop("checked", false);
});

$(document).on("change", ".addListHeader__chkBox", function() {
	if ($(this).is(":checked") == true) {
		$(".addList__addessLine").css("backgroundColor", "#DCEDD4");
		$(".addessLine__chkBox").prop("checked", true);
	} else {
		$(".addList__addessLine").css("backgroundColor", "transparent");
		$(".addessLine__chkBox").prop("checked", false);
	}
});