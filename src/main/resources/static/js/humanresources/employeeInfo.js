$(document).ready(function () {

    $(".manageToggle, .productToggle, .saleToggle ").on("click", function () {

        if ($(this).attr("toggleView") == "true") {
            $(this).attr("toggleView", "false");

            if ($(this).attr("class").includes("manageToggle")) {
                $(".manageInner").css("display", "none");
                $(this).css("background-color","transparent");

            } else if ($(this).attr("class").includes("productToggle")) {
                $(".productInner").css("display", "none");
                $(this).css("background-color","transparent");

            } else if ($(this).attr("class").includes("saleToggle")) {
                $(".saleInner").css("display", "none");
                $(this).css("background-color","transparent");
            }
            $(this).children(".naviIcon").html(`<i class="fa-solid fa-plus"></i>`);
        } else {
            $(this).attr("toggleView", "true");

            if ($(this).attr("class").includes("manageToggle")) {
                $(".manageInner").css("display", "block");
                $(this).css("background-color","#dcedd4");
                $(".manageInner").css("background-color","#dcedd4");

            } else if ($(this).attr("class").includes("productToggle")) {
                $(".productInner").css("display", "block");
                $(this).css("background-color","#dcedd4");
                $(".productInner").css("background-color","#dcedd4");

            } else if ($(this).attr("class").includes("saleToggle")) {
                $(".saleInner").css("display", "block");
                $(this).css("background-color","#dcedd4");
                $(".saleInner").css("background-color","#dcedd4");
            }
            $(this).children(".naviIcon").html(`<i class="fa-solid fa-minus"></i>`);

        }
    });

    // 검색 창 클릭시 div border변경
    $(".em_search").on("click", function () {
       $(".searchBox").css("border","1px solid #DCEDD4");
    });

    // $(".content__Title").on("click",function(){

    // });



})

