<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅방 목록</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<link rel="stylesheet" href="/css/chat/chat_common.css" />
<link rel="stylesheet" href="/css/chat/chat_chatList.css" />
<script src="/js/chat/chatListLoad.js"></script>
</head>



<body>
    <div class="header">
        <div class="header__text fontEN colorGreen">
            C-lovers
        </div>
    </div>

    <div class="myInfo d-flex">
        <div class="myInfo__profileImg d-flex">
            <div class="profileImg__cover"><img class="profileImg__img" src="/css/chat/profileImg.png">
                <div class="profileImg__state backLightGreen"></div>
            </div>
        </div>

        <div class="myInfo__infoTxt d-flex">
            <div class="infoTxt__align">
                <div class="infoTxt__name">대표이사</div>
                <div class="infoTxt__state colorGreen">업무중</div>
            </div>
        </div>
    </div>

    <div class="chat-nav backLightGreen d-flex">
        <a href="/chat/goMain"><div class="chat-nav__userList align-center"><i class="fa-solid fa-user"></i></div></a>
        <a href="/chat/chatList"><div class="chat-nav__chatList align-center"><i class="fa-solid fa-comment nav-selected"></i></div></a>
        <a href="/chat/fileList"><div class="chat-nav__fileList align-center"><i class="fa-solid fa-folder-open"></i></div></a>
    </div>

    <div class="chat-search d-flex">
        <div class="chat-search__icon align-center"><i class="fa-solid fa-magnifying-glass"></i></div>
        <input class="chat-search__txt" type="text" placeholder="참여자, 대화내용 검색">
        <div class="chat-search__cancel fontEN align-center"><i class="fa-solid fa-xmark"></i></div>
    </div>

    <div class="chat-chatList"></div>

    <script>
		// 채팅목록 누르면 채팅방으로 이동 -> 임의로 작성한 코드임. 추후에 코드 수정 필요
		$(document).on("click",".chat-chatList__chatRoom",function(){
			let option ="height=585, width=400";
	        let openUrl = "/chat/goChatRoom";
	        window.open(openUrl,"chatRoom",option);
		})

        // search input focus 시 우측에 검색 취소 버튼 생성
        $(".chat-search__txt").on("focus", function () {
            $(".chat-search__cancel").css("display", "flex");
        });

        // search input focus 되어 생성된 취소 버튼 클릭 시, 검색어 삭제 및 input tag blur
        $(".chat-search__cancel").on("click", function () {
            $(".chat-search__txt").blur();
            $(".chat-search__txt").val("");
            $(".chat-search__cancel").css("display", "none");
        });

        // 컨텍스트 메뉴 관련 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // 기존 컨텍스트 메뉴 막기
        $(document).on('contextmenu', function () {
            return false;
        });

        // 컨텍스트 메뉴 생성 함수
        function mkContextMenu(id) {
            let menuBox = $("<div>").attr("class", "ctxMenuBox");
            let menuList = $("<ul>").attr("class", "ctxMenuBox__ctxMenuList");
            let goChat = $("<li>").text("채팅하기").attr("class", "ctxMenuList__goChat").attr("id", id);
            menuList.append(goChat);

            goChat.on("mousedown", function () {
                window.open("goChat.html", '새창2', 'width=400,height=585');
            });

            let goProfile = $("<li>").text("프로필보기").attr("class", "ctxMenuList__goProfile");
            menuList.append(goProfile);
            goProfile.on("mousedown", function () {
                $(".profile__modal").modal({
                    fadeDuration: 300,
                    showClose: false
                });

                $(".close__btn").on("click", $.modal.close);
            });

            menuList.append($("<li>").text("취소하기").attr("class", "ctxMenuList__chatCancel"));
            menuBox.append(menuList);
            return menuBox;
        }

        // 프로필 우클릭 시 컨텍스트 메뉴 생성 함수 호출
        $(document).on("mouseup", ".chat-userList__chatUserInfo", function (e) {
            if ((e.button == 2) || e.which == 3) {
                $(".ctxMenuBox").remove();
                let menuBox = mkContextMenu($(this).attr("id"));
                document.body.appendChild(menuBox[0]);
                menuBox.css({ "top": e.pageY, "left": e.pageX });
            }
        });

        // 컨텍스트 메뉴 존재할 경우, 다른 곳 클릭 시 컨텍스트 메뉴 삭제, 혹은 채팅이나 프로필 버튼 눌러 해당 버튼 이벤트 실행 후 삭제
        $(document).on("mousedown", function (e) {
            if ($(".ctxMenuBox").length) {
                $(".ctxMenuBox").remove();
            }
        });

        // // 컨텍스트 메뉴 취소하기 클릭 시 메뉴 삭제 (넣을가요 말까요.. 바로 윗줄 코드를 추가해서 없애두 될 것 같기두 해)
        // $(document).on("click", ".ctxMenuList__chatCancel", function(e) {
        // 	$("#ctxMenuBox").remove();
        // });

        // 여기까지 컨텍스트 메뉴 관련 ///////////////////////////////////////////////////////////////////////////////////////////////////////

        // 윈도우 높이에 따라 인원 목록 높이 조절 (화면에 꽉 차게 나오면서 body엔 스크롤 안 생기고, 인원 목록의 스크롤 하단이 전체 크기에 딱 맞도록 하기 위함.)
        window.onresize = function () {
            let h = window.innerHeight - 251;
            $(".chat-chatList").css("height", h);

            let w = window.innerWidth;

            $(".chatRoom__txt").css("width",w-140);
            $(".chatRoom_txtalignUp").css("width",w-200);
            $(".chatRoom__lastChat").css("width",w-200);
            $(".chat-search__txt").css("width",w-72);
        }
    </script>
</body>

</html>