<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 목록</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css" />

<link rel="stylesheet" href="/css/chat/chat_common.css" />
<link rel="stylesheet" href="/css/chat/chat_fileList.css" />
<script src="/js/chat/chatFileLoad.js"></script>
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
        <a href="/chat/chatList"><div class="chat-nav__chatList align-center"><i class="fa-solid fa-comment"></i></div></a>
        <a href="/chat/fileList"><div class="chat-nav__fileList align-center"><i class="fa-solid fa-folder-open nav-selected"></i></div></a>
    </div>
    
    <div class="chat-search d-flex">
        <div class="chat-search__icon align-center"><i class="fa-solid fa-magnifying-glass"></i></div>
        <input class="chat-search__txt" type="text" placeholder="파일명, 확장자, 등록자 검색">
        <div class="chat-search__cancel fontEN align-center"><i class="fa-solid fa-xmark"></i></div>
    </div>

    <div class="chat-selectFile d-flex align-center">
        <div class="chat-selectFile__count">1개 선택</div>
        <div class="chat-selectFile__right d-flex">
            <div class="chat-selectFile__download"><i class="fa-solid fa-download"></i></div>
            <div class="chat-selectFile__cancel fontEN align-center"><i class="fa-solid fa-xmark"></i></div>
        </div>
       
    </div>
    <script>
        $(document).on("change",".checkBoxCover__checkBox",function(){
            if($(this).is(":checked")){
                $(this).closest(".chat-fileList__file").css("background-color","#DCEDD4");
            }else{
                $(this).closest(".chat-fileList__file").css("background-color","#FFFFFF");
            }

            let cnt = $("input:checkbox.checkBoxCover__checkBox:checked").length;
            if(cnt > 0){
                $(".chat-selectFile__count").text(cnt+"개 선택");
                $(".chat-selectFile").css("display","flex");
                $(".chat-search").css("display","none");
            }
            else{
                $(".chat-selectFile").css("display","none");
                $(".chat-search").css("display","flex");
            }
            
        });
    </script>
    <div class="chat-fileList"></div>

    <script>
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

        $(".chat-selectFile__cancel").on("click", function () {
            $("input:checkbox.checkBoxCover__checkBox:checked").prop("checked",false);
            $(".chat-fileList__file").css("background-color","#FFFFFF");
            $(".chat-selectFile").css("display","none");
            $(".chat-search").css("display","flex");
        });

        // 윈도우 높이에 따라 인원 목록 높이 조절 (화면에 꽉 차게 나오면서 body엔 스크롤 안 생기고, 인원 목록의 스크롤 하단이 전체 크기에 딱 맞도록 하기 위함.)
        window.onresize = function () {
            let h = window.innerHeight - 251;
            $(".chat-fileList").css("height", h);

            let w = window.innerWidth;
            $(".file__fileInfo").css("width", w-60);
            $(".chat-search__txt").css("width",w-71);
        }
  
    </script>
</body>

</html>