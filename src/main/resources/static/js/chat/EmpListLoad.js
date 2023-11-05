$(document).ready(function(){
	let userList__chatUserInfo = $("<div>").attr("class","chat-userList__chatUserInfo d-flex");
	
	let chatUserInfo__profileImg = $("<div>").attr("class","chatUserInfo__profileImg profileImg__cover");
	let profileImg__img = $("<img>").attr("class","profileImg__img").attr("src","/css/chat/profileImg.png");
	let profileImgSmall__state = $("<div>").attr("class","profileImgSmall__state backLightGreen");
	
	let chatUserInfo__txt = $("<div>").attr("class","chatUserInfo__txt d-flex align-center");
	let chatUserInfo__deptName = $("<div>").attr("class","chatUserInfo__deptName").text("직급"); //직급
	let chatUserInfo__name = $("<div>").attr("class","chatUserInfo__name").text("이름"); //이름
	
	chatUserInfo__profileImg.append(profileImg__img).append(profileImgSmall__state);
	chatUserInfo__txt.append(chatUserInfo__deptName).append(chatUserInfo__name);
	userList__chatUserInfo.append(chatUserInfo__profileImg).append(chatUserInfo__txt);
	
	$(".chat-team__userInfoList").append(userList__chatUserInfo);
});