// 예약 상단 네비게이션 영역
var Navigation = (function(){
    var goReservationMain = function(evt){
        evt.preventDefault();
        window.location.href = window.location.origin;
    }

    var goNAVER = function(evt){
        evt.preventDefault();
        window.location.href = "https://m.naver.com";
    }

    var goMyReservation = function(evt){
        evt.preventDefault();
        window.location.href = window.location.origin + "/login";
    }

    var init = function(rootElement){
        $(rootElement).find(".lnk_logo[title=\"네이버\"]").on("click", goNAVER);
        $(rootElement).find(".lnk_logo[title=\"예약\"]").on("click", goReservationMain);
        $(rootElement).find(".btn_my").on("click", goMyReservation);
    }

    return {
        init: init
    }
})();

$(function(){
    Navigation.init(".header");
});
