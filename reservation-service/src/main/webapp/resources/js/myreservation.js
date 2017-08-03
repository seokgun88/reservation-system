"use strict";

require.config({
    paths: {
        "jquery" : "node_modules/jquery/dist/jquery",
        "Handlebars" : "node_modules/handlebars/dist/handlebars.runtime",
        "templatesCompiled" : "templatesCompiled",
        "EgComponent" : "node_modules/@egjs/component/dist/component",
        "Util" : "util"
    },
    shim: {
        "templatesCompiled": {
            deps : ["Handlebars"],
            exports : "templatesCompiled"
        }
    }
});

require([
    "jquery", "Handlebars", "templatesCompiled", "EgComponent", "Util"
], function($, Handlebars, templatesCompiled, EgComponent, Util){
    templatesCompiled();
    $(function(){
        MyReservationModule.init();
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
            summaryCounts.canceledReservationCount++;
            summaryCounts.scheduledReservationCount--;
            $mySummary.find('.item:eq(1) span').text(summaryCounts.scheduledReservationCount);
            $mySummary.find('.item:last span').text(summaryCounts.canceledReservationCount);
        }

        return {
            init: init,
            updateSummary: updateSummary
        }
    })();

    var CardItem = Util.extend(EgComponent, {
        init: function (root, myReservation) {
            this.myReservation = myReservation;
            this.$root = $(root);
            this.$popupBookingWrapper = $('.popup_booking_wrapper');
            this.listHeaders = {
                $proccessingReservationList : $("li.card:eq(0)"),
                $confirmedReservationList : $("li.card:eq(1)"),
                $usedReservationList : $("li.card:eq(2)"),
                $canceledReservationList : $("li.card:eq(3)")
            }

            this.$root.find(".btn.btn_cancel").on("click", this.fadeInPopup.bind(this));
            this.$root.find(".btn.btn_review").on("click", this.goReviewWrite.bind(this));
        },
        goReviewWrite: function(evt){
            evt.preventDefault();
            var productId = this.myReservation.productId;
            var userId = $('body').data('user-id');
            window.location.href = "/products/" + productId + "/comments/form";
        },
        fadeInPopup: function (evt) {
            evt.preventDefault();
            this.updateCancelPopup();
            this.$popupBookingWrapper.fadeIn();

            this.cancelMyReservationHandler = this.cancelMyReservation.bind(this);
            this.fadeOutPopupHandler = this.fadeOutPopup.bind(this);

            this.$popBottomBtnArea = this.$popupBookingWrapper.find('.pop_bottom_btnarea');

            this.$popupBookingWrapper.on("click", '.popup_btn_close', this.fadeOutPopupHandler);
            this.$popBottomBtnArea.on("click", ".btn_green, .btn_gray", this.fadeOutPopupHandler);
            this.$popBottomBtnArea.on("click", ".btn_green", this.cancelMyReservationHandler);
        },
        updateCancelPopup: function () {
            this.$popupBookingWrapper.find('.pop_tit > span').text(this.myReservation.productName);
            this.$popupBookingWrapper.find('.pop_tit > small').text(this.myReservation.displayPeriod);
        },
        cancelMyReservation: function(evt){
            evt.preventDefault();

            this.$popBottomBtnArea.off("click", ".btn_green", this.cancelMyReservationHandler);

            MySummary.updateSummary(this.myReservation.type);
            this.sendMyReservationUpdateRequest();

            this.$root.find('.booking_cancel').remove();
            this.$root.appendTo("li.card.used:last");

            $.each(this.listHeaders, function(index, $header){
                if($header.find("article").length === 0){
                    $header.hide();
                }
            });
        },
        fadeOutPopup: function(evt) {
            evt.preventDefault();
            this.$popupBookingWrapper.fadeOut();
        },
        sendMyReservationUpdateRequest: function(){
            var apiUrl = "/api/reservations/" + this.myReservation.id;
            var ajaxCancelMyReservation = $.ajax(apiUrl, {
                type: "PUT"
            });
            ajaxCancelMyReservation.then(function(){console.log("update success!")});
        }
    });

    var MyReservationModule = (function(){
        const PATH_NAME = window.location.pathname;
        const RESERVATION_TYPES = {
            NOT_USED_RESERVATION : "1",
            CONFIRMED_RESERVATION : "2",
            USED_RESERVATION : "3",
            CANCELD_RESERVATION : "4"
        };

        var apiBaseUrl = "/api/reservations";
        var userId = $('body').data('user-id');

        var weekday = ["일", "월", "화", "수", "목", "금", "토"];

        var apiUrl = apiBaseUrl;
        var myReservationData = {};

        function init(){
            var ajaxReservations = $.ajax(apiUrl, {
                type: "GET"
            });
            ajaxReservations.then(loadMyReseravationData)
                .then(showFormattedMyReservations)
                .then(createCardComponent);
        }

        function getTicketCountString(ticketCountObj){
            var totalTicketCount = 0;
            var ticketCountString = $.map(ticketCountObj, function(count, ticketType){
                if(count > 0) {
                    totalTicketCount += count;
                    return ticketType + "(" + count + ")";
                }
            }).join(",");
            ticketCountString += " - 합계(" + totalTicketCount + ")";
            return ticketCountString;
        }

        function formattingMyReservation(type, myReservation){
            var startDate = new Date(myReservation.displayStart);
            var endDate = new Date(myReservation.displayEnd);
            myReservation.displayPeriod = formattingDisplayPeriod(startDate, endDate);

            myReservation.formattedTotalPrice = Number(myReservation.totalPrice).toLocaleString('ko');

            var ticketCountObj = {
                "일반" : myReservation.generalTicketCount,
                "청소년" : myReservation.youthTicketCount,
                "어린이" : myReservation.childTicketCount
            }

            var ticketCountString = getTicketCountString(ticketCountObj);

            myReservation.formattedReservationContents = ticketCountString;

            if(type === RESERVATION_TYPES.NOT_USED_RESERVATION || type === RESERVATION_TYPES.CONFIRMED_RESERVATION){
                myReservation.btnCancelText = "취소";
                myReservation.btnCancelTextClass = "btn_cancel";
            } else if(type === RESERVATION_TYPES.USED_RESERVATION){
                myReservation.btnCancelText = "예매자 리뷰 남기기";
                myReservation.btnCancelTextClass = "btn_review";
            }

            return myReservation;
        };

        function formattingDisplayPeriod(startDate, endDate){
            return startDate.getFullYear()+"."+(startDate.getMonth()+1)+"."+startDate.getDate() + "(" + weekday[startDate.getDay()] + ")"
                + "~" + endDate.getFullYear()+"."+(endDate.getMonth()+1)+"."+endDate.getDate() + "(" + weekday[endDate.getDay()] + ")";
        }

        function loadMyReseravationData(data) {
            myReservationData = $.extend({}, reservatinosForTest, data);
            MySummary.init(myReservationData);
        }

        function showFormattedMyReservations (){
            var reservations = myReservationData.reservations;

            if(myReservationData.totalReservationCount > 0){
                var index = 0;
                // es6 -> es5로 바꾸기
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
                cardItems[index] = new CardItem();
                cardItems[index].init(this, cardData[index]);
            });
        }

        return {
            init: init
        }
    })();
});
