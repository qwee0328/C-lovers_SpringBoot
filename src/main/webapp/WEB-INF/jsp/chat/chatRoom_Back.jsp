<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅창</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css"/>

<link rel="stylesheet" href="/css/chat/chat_common.css" />
<link rel="stylesheet" href="/css/chat/chat_invite.css">
<link rel="stylesheet" href="/css/chat/chat_rename.css">
<link rel="stylesheet" href="/css/chat/chat_profile.css">
<link rel="stylesheet" href="/css/chat/chat_chatRoom.css">
<script src="/js/chat/chat_invite.js"></script>
<script src="/js/chat/chatMessageLoad.js"></script>
</head>

<body>
    <div class="container">
        <div class="reName__modal">
            <div class="reName__header d-flex">
                <div class="reName__name">채팅방 이름 바꾸기</div><div class="reName__cancel"><i class="fa-solid fa-xmark"></i></div>
            </div>
            <div class="reName__body">
                <div class="reName__input" contenteditable="true">
                    채팅방 이름
                </div>
            </div>
            <div class="reName__footer align-center">
                <button class="reName__cancelBtn">취소</button><div class="reName_margin"></div><button class="reName__submitBtn">확인</button>
            </div>
           
        </div>

        <!-- 최하단으로 이동 -->
        <div class="container__chat-down align-center"><i class="fa-solid fa-arrow-down"></i></div>
        <script>
            $(".container__chat-down").on("click",function(){
                $(".chatContainer__chatArea").animate({scrollTop:$(".chatContainer__chatArea")[0].scrollHeight},1000);
            })
        </script>
        <!-- 최하단으로 이동 끝 -->

        <div class="chatHeader">
            <div class="header d-flex">
                <div class="header__chatNameCover d-flex"><div class="header__chatName">채팅방 이름dddddddddddddddddddddddddddd</div>
                <div class="header__numOfPPL fontEN"><i class="fa-solid fa-user"></i>&nbsp;&nbsp;10</div>
                </div>
                <div class="header__menuIcon d-flex">
                    <div class="menuIcon__searchBtn"><i class="fa-solid fa-magnifying-glass"></i></div>
                    <div class="menuIcon__hamBtn"><i class="fa-solid fa-bars"></i></div>
                </div>
                <div class="hamBtn__hamMenu">
                    <div class="hamMenu__invite hamMenu_menu">초대하기</div>
                    <div class="hamMenu__reName hamMenu_menu">이름 변경</div>
                    <div class="hamMenu__exit hamMenu_menu">나가기</div>
                </div>
            </div>
            <div class="header__chatSearch d-flex">
                <div class="chatSearch__inputCover d-flex">
                    <i class="fa-solid fa-magnifying-glass"></i><input type="text" class="chatSearch__input" placeholder="대화 내용 검색">
                </div>
                <div class="chatSearch__cancel"><i class="fa-solid fa-xmark"></i></div>
            </div>
            <div class="offlineCnt">최사장님 외 4명은 현재 부재중 입니다.</div>
        </div>

        <div class="chatContainer">
            <div class="chatContainer__chatArea">

                <div class="chatArea__DayLine align-center">2023년 10월 30일 월요일</div>

                <div class="chatArea__otherPersonChat d-flex">
                    <div class="otherPersonChat__profileImg"></div>
                    <div class="otherPersonChat__chatInfo">
                        <div class="otherPersonChat__userName">이사원</div>
                        <div class="otherPersonChat__chatContents d-flex">
                            <div class="otherPersonChat__chatMsg">
                                hi</div>
                            <div class="otherPersonChat__chatMsgRight d-flex">
                                <div class="chatMsgRight__align">
                                    <div class="otherPersonChat__readCnt">4</div>
                                    <div class="otherPersonChat__sendTime">오후 7:46</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="chatArea__otherPersonChat d-flex">
                    <div class="otherPersonChat__profileImg"></div>
                    <div class="otherPersonChat__chatInfo">
                        <div class="otherPersonChat__userName">이사원</div>
                        <div class="otherPersonChat__chatContents d-flex">
                            <div class="otherPersonChat__chatMsg">채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥</div>
                            <div class="otherPersonChat__chatMsgRight d-flex">
                                <div class="chatMsgRight__align">
                                    <div class="otherPersonChat__readCnt">4</div>
                                    <div class="otherPersonChat__sendTime">오후 7:46</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="chatArea__myChat d-flex">
                    <div class="myChat__chatContents d-flex">
                        <div class="myChat__chatMsgLeft d-flex">
                            <div class="chatMsgLeft__align">
                                <div class="myChat__readCnt">4</div>
                                <div class="myChat__sendTime">오후 7:46</div>
                            </div>
                        </div>
                        <div class="myChat__chatMsg">채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥</div>
                    </div>
                </div>  
                
                <div class="chatArea__myChat d-flex">
                    <div class="myChat__chatContents d-flex">
                        <div class="myChat__chatMsgLeft d-flex">
                            <div class="chatMsgLeft__align">
                                <div class="myChat__readCnt">4</div>
                                <div class="myChat__sendTime">오후 7:46</div>
                            </div>
                        </div>
                        <div class="myChat__chatMsg d-flex">
                            <div class="myChat__fileMsg">
                                <div class="fileMsg__fileName">파일명.확장자명</div>
                                <div class="fileMsg__volume">파일 용량: 33.6kb</div>
                                <div class="fileMsg__expirationDate">유효기간: ~ 2023.11.13</div>
                            </div>
                            <div class="chatMsg__download align-center"><i class="fa-solid fa-download"></i></div>
                            <script>
                                $(".chatMsg__download").on("click",function(){

                                })
                            </script>
                        </div>
                    </div>
                </div>  

                <div class="chatArea__myChat d-flex">
                    <div class="myChat__chatContents d-flex">
                        <div class="myChat__chatMsgLeft d-flex">
                            <div class="chatMsgLeft__align">
                                <div class="myChat__readCnt">4</div>
                                <div class="myChat__sendTime">오후 7:46</div>
                            </div>
                        </div>
                        <div class="myChat__chatMsg">채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥</div>
                    </div>
                </div>  
                

                <div class="chatArea__myChat d-flex">
                    <div class="myChat__chatContents d-flex">
                        <div class="myChat__chatMsgLeft d-flex">
                            <div class="chatMsgLeft__align">
                                <div class="myChat__readCnt">4</div>
                                <div class="myChat__sendTime">오후 7:46</div>
                            </div>
                        </div>
                        <div class="myChat__chatMsg">채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥채팅내용인데욥</div>
                    </div>
                </div>  
                

            </div>




            <div class="chatContainer__inputArea">
                <div class="inputArea__msg" contenteditable="true"></div>
                <div class="inputArea__btns d-flex">
                    <div class="inputArea__fileIcon"><i class="fa-solid fa-paperclip"></i></div>
                    <div class="inputArea__subminBtnCover"><button class="inputArea__subminBtn">전송</button></div>
                </div>
            </div>
        </div>
    </div>

   

    <!-- 초대하기 모달 -->
    <div class="invite__modal">
        <div class="invite__header d-flex">
            <div class="invite__name">대화 상대 추가</div>
            <div class="invite__cancel"><i class="fa-solid fa-xmark"></i></div>
        </div>
        <div class="invite-search d-flex">
            <div class="invite-search__icon align-center"><i class="fa-solid fa-magnifying-glass"></i></div>
            <input class="invite-search__txt" type="text" placeholder="이름으로 검색해주세요.">
        </div>
        <div class="invite__body">
            <div class="invite__selectList">
                <div class="invite__selectEmp">황민경</div>
                <div class="invite__selectEmp">장유나</div>
                <div class="invite__selectEmp">김서영</div>
                <div class="invite__selectEmp">이윤진</div>
                <div class="invite__selectEmp">김창균</div>
                <div class="invite__selectEmp">오부장</div>
                <div class="invite__selectEmp">최사장</div>
                <div class="invite__selectEmp">김이사</div>
                <div class="invite__selectEmp">오부장</div>
                <div class="invite__selectEmp">최사장</div>
                <div class="invite__selectEmp">김이사</div>
                <div class="invite__selectEmp">오부장</div>
            </div>
            <div class="invite__allList">
                <div class="allList__companyName">c-lover</div>
                <div class="allList__list">
                    <div class="allList__employee d-flex">
                        <div class="allList__empProfileImgCover"><div class="allList__empProfileImg"></div></div>
                        <div class="allList__empName">이름임</div>
                        <div class="allList__empcheckBox"><input type="checkBox"></div>
                    </div>
                    <div class="allList__employee d-flex">
                        <div class="allList__empProfileImgCover"><div class="allList__empProfileImg"></div></div>
                        <div class="allList__empName">이름임</div>
                        <div class="allList__empcheckBox"><input type="checkBox"></div>
                    </div>
                    <div class="allList__employee d-flex">
                        <div class="allList__empProfileImgCover"><div class="allList__empProfileImg"></div></div>
                        <div class="allList__empName">이름임</div>
                        <div class="allList__empcheckBox"><input type="checkBox"></div>
                    </div>
                    <div class="allList__employee d-flex">
                        <div class="allList__empProfileImgCover"><div class="allList__empProfileImg"></div></div>
                        <div class="allList__empName">이름임</div>
                        <div class="allList__empcheckBox"><input type="checkBox"></div>
                    </div>
                    <div class="allList__employee d-flex">
                        <div class="allList__empProfileImgCover"><div class="allList__empProfileImg"></div></div>
                        <div class="allList__empName">이름임</div>
                        <div class="allList__empcheckBox"><input type="checkBox"></div>
                    </div>
                    <div class="allList__employee d-flex">
                        <div class="allList__empProfileImgCover"><div class="allList__empProfileImg"></div></div>
                        <div class="allList__empName">이름임</div>
                        <div class="allList__empcheckBox"><input type="checkBox"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="invite__footer align-center">
            <button class="invite__cancelBtn">취소</button>
            <div class="invite_margin"></div><button class="invite__submitBtn">확인</button>
        </div>
    </div>


    <!-- 프로필보기 모달창 -->
    <div class="profile__modal">
        <div class="profile__card">
            <div class="profile__head">
                <button class="close__btn">&times;</button>
                <p>사용자 프로필</p>
            </div>
            <hr class="profile__hr">
            <div class="profile__body">
                <table class="profile__table">
                    <tbody>
                        <tr>
                            <td rowspan="3">
                                <div class="profileModalImg__cover">
                                    <img class="profileImg__img" src="/css/chat/profileImg.png">
                                    <div class="profileImg__state backLightGreen"></div>
                                </div>
    
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <div class="fontKR__Small profile__text">최사장</div>
                            </td>
                            <td class="fontKR__Small profile__text">사장 / 영업</td>
                        </tr>
                        <tr>
                            <td>
                                <div class="fontKR__Small profile__text colorGreen">업무중</div>
                            </td>
                            <td>
                                <div class="fontKR__Small profile__text">클로버산업 > 관리부 > 인사팀</div>
                            </td>
                        </tr>
                    </tbody>
                </table>
    
                <div class="profile__details">
                    <table class="profile__details__table">
                        <tr>
                            <td>
                                <div class="fontKR__Small profile__text">
                                    <div class="profile__icon">
                                        <i class="fa-solid fa-envelope"></i>
                                    </div>
                                    <div class="profile__subject">
                                        이메일
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div class="fontKR__Small profile__text">이메일 주소</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="fontKR__Small profile__text">
                                    <div class="profile__icon">
                                        <i class="fa-solid fa-phone fa-flip-horizontal"></i>
                                    </div>
                                    <div class="profile__subject">
                                        사내전화
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div class="fontKR__Small profile__text">101</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="fontKR__Small profile__text">
                                    <div class="profile__icon">
                                        <i class="fa-solid fa-mobile-button"></i>
                                    </div>
                                    <div class="profile__subject">
                                        휴대전화
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div class="fontKR__Small profile__text">휴대전화 번호</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="fontKR__Small profile__text">
                                    <div class="profile__icon">
                                        <i class="fa-solid fa-calendar-days"></i>
                                    </div>
                                    <div class="profile__subject">
                                        입사일
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div class="fontKR__Small profile__text">2023-11-01</div>
                            </td>
                        </tr>
    
                    </table>
    
    
                </div>
                <div class="profile__button">
                    <button class="chat__btn">채팅하기</button>
                </div>
    
    
            </div>
    
        </div>

    </div>

    <script>
        window.onresize = function () {
            let h = window.innerHeight - 165;
            $(".chatContainer__chatArea").css("height", h - 0.1);

            let w = window.innerWidth - 30;
            $(".chatSearch__input").css("width",w);
        };

        $(".chatSearch__cancel").on("click",function(){
            $(".header__chatSearch").css("display","none");
            $(".chatSearch__input").val("");
        })

        $(".menuIcon__searchBtn").on("click",function(){
            if($(".hamBtn__hamMenu").css("display")=="block") $(".hamBtn__hamMenu").css("display","none"); 
            if($(".header__chatSearch").css("display")=="flex") $(".header__chatSearch").css("display","none");
            else $(".header__chatSearch").css("display","flex");
        });

        $(".menuIcon__hamBtn").on("click",function(){
            if($(".hamBtn__hamMenu").css("display")=="none") $(".hamBtn__hamMenu").css("display","block");
            else $(".hamBtn__hamMenu").css("display","none");
            if($(".header__chatSearch").css("display")=="flex") $(".header__chatSearch").css("display","none");
        });

        $(".hamMenu__reName").on("click",function(){
            $(".reName__modal").modal({
                fadeDuration:300,
                showClose:false
            });
            $(".reName__cancel").on("click",$.modal.close);
            $(".reName__cancelBtn").on("click",$.modal.close);
        });

        $(".hamMenu__invite").on("click",function(){
            $(".invite__modal").modal({
                fadeDuration:300,
                showClose:false
            });
            $(".invite__cancel").on("click",$.modal.close);
            $(".invite__cancelBtn").on("click",$.modal.close);
        })


        // 컨텍스트 메뉴 존재할 경우, 다른 곳 클릭 시 컨텍스트 메뉴 삭제, 메뉴 누르면 해당 버튼 이벤트 실행
        $(document).on("mousedown", function(e) {   
            if($(".hamBtn__hamMenu").css("display")=="block") {
                if(!$(e.target).hasClass("hamMenu_menu")) $(".hamBtn__hamMenu").css("display","none");  
            }
        });

        // 프로필 누르면 프로필 모달창 띄우기
        $(document).on("click",".otherPersonChat__profileImg",function(e){
            $(".profile__modal").modal({
                fadeDuration:300,
                showClose:false
            });

            $(".close__btn").on("click",$.modal.close);
        });

</script>
</body>

</html>