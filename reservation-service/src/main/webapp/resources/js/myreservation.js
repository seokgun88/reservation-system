$(function(){
    MyReservationModule.init();
    Navigation.init(".header");
})

var reservatinosForTest = {
    canceledReservationCount: 1,
    completedReservationCount: 1,
    scheduledReservationCount: 2,
    totalReservationCount: 4,
    reservations: {
        1: [{
            id: 1,
            childTicketCount: 0,
            displayEnd: 1484059840000,
            displayStart: 1483282240000,
            generalTicketCount: 2,
            productId: 1,
            productName: "전시1",
            reservationType: 1,
            totalPrice: 10000,
            youthTicketCount: 2
        }, {
            id: 2,
            childTicketCount: 0,
            displayEnd: 1484059840000,
            displayStart: 1483282240000,
            generalTicketCount: 3,
            productId: 1,
            productName: "전시1",
            reservationType: 1,
            totalPrice: 15000,
            youthTicketCount: 1
        }],
        2: [{
            id: 3,
            childTicketCount: 1,
            displayEnd: 1484059840000,
            displayStart: 1483282240000,
            generalTicketCount: 0,
            productId: 2,
            productName: "전시2",
            reservationType: 1,
            totalPrice: 20000,
            youthTicketCount: 2
        }],
        3: [{
            id: 4,
            childTicketCount: 1,
            displayEnd: 1484059840000,
            displayStart: 1483282240000,
            generalTicketCount: 2,
            productId: 3,
            productName: "전시3",
            reservationType: 1,
            totalPrice: 30000,
            youthTicketCount: 0
        }],
        4: [{
            childTicketCount: 1,
            displayEnd: 1484059840000,
            displayStart: 1483282240000,
            generalTicketCount: 2,
            productId: 4,
            productName: "전시4",
            reservationType: 1,
            totalPrice: 40000,
            youthTicketCount: 2
        }]
    }
};

var cardItems = [];
var cardData = [];

var MyReservationModule = (function(){
    const BASE_URL = window.location.origin;
    const PATH_NAME = window.location.pathname;
    var apiBaseUrl = BASE_URL + "/api/reservations";
    var userId = $('body').data('user-id');

    var weekday = ["일", "월", "화", "수", "목", "금", "토"];

    function formattingMyReservation(type, myReservation){
        var startDate = new Date(myReservation.displayStart);
        var endDate = new Date(myReservation.displayEnd);
        myReservation.displayPeriod = formattingDisplayPeriod(startDate, endDate);

        myReservation.formattedTotalPrice = Number(myReservation.totalPrice).toLocaleString('ko');

        var str = "";
        var totalTicketCount = 0;
        var generalTicketCount = myReservation.generalTicketCount,
            youthTicketCount = myReservation.youthTicketCount,
            childTicketCount = myReservation.childTicketCount;

        if(generalTicketCount > 0){
            str += "일반(" + generalTicketCount + "),";
            totalTicketCount += generalTicketCount;
        }
        if(youthTicketCount > 0){
            str += "청소년(" + youthTicketCount + "),";
            totalTicketCount += youthTicketCount;
        }
        if(childTicketCount > 0){
            str += "어린이(" + childTicketCount + "),";
            totalTicketCount += childTicketCount;
        }
        if(totalTicketCount > 0){
            str = str.slice(0,-1);
            str += " - 합계(" + totalTicketCount + ")";
        }
        myReservation.formattedReservationContents = str;

        if(type === "1" || type === "2"){
            myReservation.btnCancelText = "취소";
        } else if(type === "3"){
            myReservation.btnCancelText = "예매자 리뷰 남기기";
        }

        return myReservation;
    };

    function formattingDisplayPeriod(startDate, endDate){
        return startDate.getFullYear()+"."+(startDate.getMonth()+1)+"."+startDate.getDate() + "(" + weekday[startDate.getDay()] + ")"
            + "~" + endDate.getFullYear()+"."+(endDate.getMonth()+1)+"."+endDate.getDate() + "(" + weekday[endDate.getDay()] + ")";
    }

    var apiUrl = apiBaseUrl + "/users/" + userId;
    var myReservationData = {};

    function init(){
        var ajaxReservations = $.ajax(apiUrl, {
            type: "GET"
        });
        ajaxReservations.then(loadMyReseravationData);
        ajaxReservations.then(showFormattedMyReservations);
        ajaxReservations.then(createCardComponent);
    }

    function loadMyReseravationData(data) {
        myReservationData = $.extend({}, reservatinosForTest, data);
        MySummary.init(myReservationData);
    }

    function showFormattedMyReservations (){
        var reservations = myReservationData.reservations;
        console.log(myReservationData);
        if(myReservationData.totalReservationCount > 0){
            var index = 0;
            for (var [type, reservation] of Object.entries(reservations)) {
                for(var resItem of reservation){
                    formattingMyReservation(type, resItem);
                    cardData.push(resItem);
                }
                var clsSeletor = "li.card:eq(" + (type-1) + ")";
                $(clsSeletor).append(reservations[type].map(function (v, i) {
                    return Handlebars.templates['reservationCardItem'](v);
                }).join(""));
            }

            $('.card:not(:has(article))').hide();
            $('.wrap_mylist').show();
        } else {
            $(".err").show();
        }
    }

    function createCardComponent(){
        $.each($(".card_item"), function(index){
            cardItems[index] = new CardItem(this, cardData[index]);
            cardItems[index].init();
        });
    }

    function CardItem(root, options){
        var defaultValues = {
            id: 1,
            productName: "Young Graduates",
            displayPeriod: "2017.07.27.(목)-2017.07.28.(금)",
            popUpCls: '.popup_booking_wrapper',
            type: 1
        };
        this.$root = $(root);
        this.values = $.extend({}, defaultValues, options);
        this.events = {};
    }
    CardItem.prototype = new eg.Component();
    CardItem.prototype.constructor = CardItem;
    var cardFn = {
        init: function(){
            this.bindEvents();
        },
        bindEvents: function(){
            this.$root.find(".btn:contains(\"취소\")").on("click", this.fadeInPopup.bind(this));
            this.$root.find(".btn:contains(\"예매자 리뷰 남기기\")").on("click", this.goReviewWrite.bind(this));
        },
        goReviewWrite: function(evt){
            evt.preventDefault();
            var productId = this.values.productId;
            window.location.href = BASE_URL + "/products/" + productId + "/comments/users/" + userId;
        },
        fadeInPopup: function(evt){
            evt.preventDefault();
            this.updateCancelPopup();
            $(this.values.popUpCls).fadeIn();
        },
        updateCancelPopup: function(){
            this.events.fadeInPopUp = this.fadeInPopup.bind(this);
            this.events.cancelMyReservation = this.cancelMyReservation.bind(this);
            this.events.fadeOutPopup = this.fadeOutPopup.bind(this);
            this.events.updateSummaryEvt = this.updateSummary.bind(this);

            $(this.values.popUpCls).find('.pop_bottom_btnarea').off();
            $(this.values.popUpCls).find('.popup_btn_close').off();

            $(this.values.popUpCls).find('.pop_bottom_btnarea').on("click", ".btn_green", this.events.cancelMyReservation);
            $(this.values.popUpCls).find('.pop_bottom_btnarea').on("click", ".btn_green", this.events.updateSummaryEvt);
            $(this.values.popUpCls).find('.pop_bottom_btnarea').on("click", ".btn_green", this.events.fadeOutPopup);
            $(this.values.popUpCls).find('.pop_bottom_btnarea').on("click", ".btn_gray", this.events.fadeOutPopup);
            $(this.values.popUpCls).find('.pop_bottom_btnarea').on("click", ".btn_gray", this.events.fadeOutPopup);
            $(this.values.popUpCls).find('.popup_btn_close').on("click", this.events.fadeOutPopup);
            $(this.values.popUpCls).find('.pop_tit > span').text(this.values.productName);
            $(this.values.popUpCls).find('.pop_tit > small').text(this.values.displayPeriod);
        },
        updateSummary: function(evt){
            console.log(this);
            MySummary.updateSummary(this.values.type);
        },
        cancelMyReservation: function(evt){
            evt.preventDefault();
            this.$root.find('.booking_cancel').remove();
            this.$root.appendTo("li.card.used:last");
            $("li.card.used:last").show();
        },
        fadeOutPopup: function(evt){
            evt.preventDefault();
            $(this.values.popUpCls).fadeOut();
        }
    };
    for (var fnName in cardFn){
        CardItem.prototype[fnName] = cardFn[fnName];
    }

    return {
        init: init
    }
})();

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

// 전체 요약 영역
var MySummary = (function(){
    var $mySummary = $('.my_summary');
    var defaultCounts = {
        canceledReservationCount: 0,
        completedReservationCount: 0,
        scheduledReservationCount: 0,
        totalReservationCount: 0
    };

    var summaryCounts = {};

    function init(myReservationData){
        summaryCounts = $.extend({}, defaultCounts, myReservationData);
        appendMySummaryTemplate();
    }

    function appendMySummaryTemplate(){
        $mySummary.html(Handlebars.templates['mySummary'](summaryCounts));
    }

    function updateSummary(type){
        var count = Number($mySummary.find('.item:last span').text()) + 1;
        console.log(count);
        $mySummary.find('.item:last span').text(count);
        if(type === 1 || type === 2){
            var count = Number($mySummary.find('.item:eq(1) span').text()) - 1;
            var clsSeletor = '.item:eq(1) span';
            $mySummary.find(clsSeletor).text(count);
        } else if(type === 3){
            var count = Number($mySummary.find('.item:eq(2) span').text()) - 1;
            var clsSeletor = '.item:eq(2) span';
            $mySummary.find(clsSeletor).text(count);
        }
    }

    return {
        init: init,
        updateSummary: updateSummary
    }
})();
