$(document).on("click",".invite__selectEmp",function(){
    if($(this).css("background-color")=="rgb(9, 85, 68)"){
        $(this).css({"background-color":"#DCEDD4","color":"black"});
        $(this).find(".selectEmp_remove").remove();
    }else{
        $(this).css({"background-color":"#095544","color":"white"});
        $(this).html($(this).html()+"<i class='fa-solid fa-xmark selectEmp_remove'></i>");
    }
});

$(document).on("click",".selectEmp_remove",function(){
    $(this).closest(".invite__selectEmp").remove();
});