$(document).ready(function(){
	for(let i=0; i<10; i++){
		let fileList__file = $("<div>").attr("class","chat-fileList__file d-flex");
		let file__checkBoxCover = $("<div>").attr("class","file__checkBoxCover align-center");
		let checkBoxCover__checkBox = $("<input>").attr("type","checkbox").attr("class","checkBoxCover__checkBox");
	
		let file__fileInfo = $("<div>").attr("class","file__fileInfo");
		let file_txtAlign = $("<div>").attr("class","file_txtAlign");
		let fileInfo__fileName = $("<div>").attr("class","fileInfo__fileName").text("파일명. 확장자명"); // 파일명. 확장자명
		let fileInfo__fileDetail = $("<div>").attr("class","fileInfo__fileDetail").text("보낸사람 | 파일용량"); //보낸 사람 | 파일 용량
		
		let file__expirationDate = $("<div>").attr("class","file__expirationDate").text("~ 2023-11-02"); // 파일 유효 기간
		
		file__fileInfo.append(file_txtAlign.append(fileInfo__fileName).append(fileInfo__fileDetail))
		fileList__file.append(file__checkBoxCover.append(checkBoxCover__checkBox)).append(file__fileInfo).append(file__expirationDate);
		$(".chat-fileList").append(fileList__file);
	}
});