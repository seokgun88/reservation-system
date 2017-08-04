"use strict";
require.config({
  paths: {
        "jquery" : "node_modules/jquery/dist/jquery",
        "Handlebars" : "node_modules/handlebars/dist/handlebars",
        "EgComponent" : "node_modules/@egjs/component/dist/component",
        "Util" : "util"
  }
});

require([
  "jquery", "Handlebars", "EgComponent", "Util"
], function ($, Handlebars, EgComponent, Util) {
  function TicketValidator(){

  }
  TicketValidator.prototype = new EgComponent();
  TicketValidator.prototype.constructor = TicketValidator;
  TicketValidator.prototype.validate = function(){
      var regexTel = /0\d{1,2}-\d{3,4}-\d{4}/;
      var regexEmail = /[0-9a-zA-Z]+@[0-9a-zA-Z]+\.[0-9a-zA-Z]+/;
      var isValid = (Number(validator.elementMap.$totalCnt.html()) > 0)
          && (validator.elementMap.$name.val() ? true : false)
          && regexTel.test(validator.elementMap.$tel.val())
          && regexEmail.test(validator.elementMap.$email.val())
          && validator.elementMap.$chkAgree.prop("checked");

      if(isValid){
          validator.elementMap.$wrapBtnBk.removeClass("disable");
          validator.elementMap.$btnBk.prop("disabled", false);
      } else {
          validator.elementMap.$wrapBtnBk.addClass("disable");
          validator.elementMap.$btnBk.prop("disabled", true);
      }
  };
  var validator = new TicketValidator();
  validator.on("validate", validator.validate);

  function Ticket(){
  }
  Ticket.prototype = new EgComponent();
  Ticket.prototype.constructor = Ticket;
  Ticket.prototype.getElementMap = function($el){
      var $qty = $el.closest(".qty");
      var $cnt = $qty.find(".count_control_input");
      var $totalCnt = $(".total_cnt");
      return {
          $qty: $qty,
          $cnt: $cnt,
          $totalPrice: $qty.find(".total_price"),
          price: $qty.find(".discounted-price").html(),
          cnt: Number($cnt.val()),
          $plusBtn: $qty.find(".ico_plus3"),
          $minusBtn: $qty.find(".ico_minus3"),
          $totalCnt: $totalCnt,
          totalCnt: Number($totalCnt.html())
      }
  };
  Ticket.prototype.add = function(elementMap){
      console.log("add");
      elementMap.$cnt.val(elementMap.cnt + 1);
      elementMap.$totalPrice.html(elementMap.price * (elementMap.cnt + 1));
      elementMap.$totalCnt.html(elementMap.totalCnt + 1);
  };
  Ticket.prototype.sub = function(elementMap){
      console.log("sub");
      elementMap.$cnt.val(elementMap.cnt - 1);
      elementMap.$totalPrice.html(elementMap.price * (elementMap.cnt - 1));
      elementMap.$totalCnt.html(elementMap.totalCnt - 1);
  };
  Ticket.prototype.activeMinusState = function(elementMap){
      console.log("activeMinusState");
      elementMap.$minusBtn.removeClass("disabled");
      elementMap.$cnt.removeClass("disabled");
      elementMap.$totalPrice.closest(".individual_price").addClass("on_color");
  };
  Ticket.prototype.inactiveMinusState = function(elementMap){
      console.log("inactiveMinusState");
      elementMap.$minusBtn.addClass("disabled");
      elementMap.$cnt.addClass("disabled");
      elementMap.$totalPrice.closest(".individual_price").removeClass("on_color");
  };
  var ticket = new Ticket();
  ticket.on("add", function($el){
      var elementMap = ticket.getElementMap($el);
      if(elementMap.cnt === 0){
          ticket.activeMinusState(elementMap);
      }
      ticket.add(elementMap);
      validator.trigger("validate");
  });
  ticket.on("sub", function($el){
      var elementMap = ticket.getElementMap($el);
      if(elementMap.cnt === 1){
          ticket.inactiveMinusState(elementMap);
      }
      ticket.sub(elementMap);
      validator.trigger("validate");
  });

  var Reserve = (function(){
      var productId,
          userId;

      var $reservationBox = $(".ct_wrap");
      var $ticketBody = $(".ticket_body");
      var $totalCnt = $(".total_cnt");
      var $displayTime = $(".display_time");
      var $name = $("#name");
      var $tel = $("#tel");
      var $email = $("#email");
      var $chkAgree = $("#chk3");
      var $btnAgree = $(".btn_agreement");
      var $wrapBtnBk = $(".bk_btn_wrap");
      var $btnBk = $(".bk_btn");

      var setPlusMinusBtnEventHandler = function(){
          var $plusBtn = $(".btn_plus_minus.ico_plus3");
          var $minusBtn = $(".btn_plus_minus.ico_minus3");
          $plusBtn.on("click", function(e){
              ticket.trigger("add", $(this));
              e.preventDefault();
          });
          $minusBtn.on("click", function(e){
              if(!$(this).hasClass("disabled")){
                  ticket.trigger("sub", $(this));
              }
              e.preventDefault();
          });
      };

      var getPricesAjax = function(){
          $.ajax({
              url: "/api/products/" + productId + "/prices",
              type: "GET"
          })
              .done(function(data){
                  var priceDescHtml = "";
                  var ticketBodyHtml = "";
                  var ticketTemplate = Handlebars.compile($("#ticket-template").html());
                  $.each(data, function(index, value){
                      var type,
                          typeDesc;
                      if(value.priceType === 2){
                          type = "청소년";
                          typeDesc = "(만 13~18세)";
                      } else if(value.priceType === 3){
                          type = "어린이";
                          typeDesc = "(만 4~12세)"
                      } else {
                          type = "일반";
                          typeDesc = "(만 19~64세)"
                      }
                      value.type = type;
                      value.typeDesc = typeDesc;

                      value.discountedPrice = value.price * (1 - value.discountRate);
                      value.discountPercentage = value.discountRate * 100;
                      priceDescHtml += value.type + value.typeDesc + " " + value.price + "원<br>";
                      ticketBodyHtml += ticketTemplate(value);
                  })
                  var $priceDescList = $(".store_details p.dsc:last-child");
                  $priceDescList.append(priceDescHtml);
                  $ticketBody.append(ticketBodyHtml);

                  setPlusMinusBtnEventHandler();

                  validator.elementMap = {
                      $totalCnt: $totalCnt,
                      $name: $name,
                      $tel: $tel,
                      $email: $email,
                      $chkAgree: $chkAgree,
                      $wrapBtnBk: $wrapBtnBk,
                      $btnBk: $btnBk
                  };
              })
              .fail(function(error){
                  console.log(error.responseJSON);
                  alert('Price list load를 실패했습니다.');
              });
      };

      var getProductMainImgIdAjax = function(reservationInfo){
          $.ajax({
              url: "/api/products/" + productId + "/mainImage",
              type: "GET"
          })
              .done(function(data){
                  reservationInfo.imgId = data;
                  var startDate = new Date(reservationInfo.displayStart);
                  var endDate = new Date(reservationInfo.displayEnd);
                  reservationInfo.displayStartDate = startDate.getFullYear() + "년 " + (startDate.getMonth() + 1) + "월 " + startDate.getDate() + "일";
                  reservationInfo.displayEndDate = endDate.getFullYear() + "년 " + (endDate.getMonth() + 1) + "월 " + endDate.getDate() + "일";
                  var reservationInfoTemplate = Handlebars.compile($('#reservation-info-template').html());
                  $reservationBox.prepend(reservationInfoTemplate(reservationInfo));
                  $displayTime.html((startDate.getMonth() + 1) + "." + startDate.getDate() + "~" + (endDate.getMonth() + 1) + "." + endDate.getDate());
                  getPricesAjax();
              })
              .fail(function(error){
                  console.log(error.responseJSON);
                  alert('Product main image id load를 실패했습니다.');
              });
      };

      var getReservationInfoAjax = function(){
          $.ajax({
              url: "/api/products/" + productId + "/reservationInfo",
              type: "GET"
          })
              .done(getProductMainImgIdAjax)
              .fail(function(error){
                  console.log(error.responseJSON);
                  alert('Product reservation information load를 실패했습니다.');
              });
      };

      var createReservationAjax = function(){
          var generalTicketCount = $(".qty[data-type=1] .count_control_input").val();
          var youthTicketCount = $(".qty[data-type=2] .count_control_input").val();
          var childTicketCount = $(".qty[data-type=3] .count_control_input").val();
          var reservationData = JSON.stringify({
              productId: productId,
              userId: userId,
              generalTicketCount: generalTicketCount === undefined ? 0 : generalTicketCount,
              youthTicketCount: youthTicketCount === undefined ? 0 : youthTicketCount,
              childTicketCount: childTicketCount === undefined ? 0 : childTicketCount,
              reservationName: $name.val(),
              reservationTel: $tel.val(),
              reservationEmail: $email.val(),
              reservationDate: new Date(),
              reservationType: 1
          });
          console.log(reservationData);
          $.ajax({
              url: "/api/reservations",
              type: "POST",
              data: reservationData,
              contentType:"application/json"
          })
              .done(function(data){
                  alert("예약을 성공했습니다.");
                  location.href = "/myreservation";
              })
              .fail(function(error){
                  console.log(error.responseJSON);
                  alert('Reservation create를 실패했습니다.');
              });
      };

      return {
          init: function(){
              productId = $("#container").data("id");
              userId = $("#container").data("userid");
              getReservationInfoAjax();

              $name.on("keyup", function(e){
                  validator.trigger("validate");
              });

              $tel.on("keyup", function(e){
                  validator.trigger("validate");
              });

              $email.on("keyup", function(e){
                  validator.trigger("validate");
              });

              $btnAgree.on("click", function(e){
                  $(this).closest(".agreement").addClass("open");
                  e.preventDefault();
              });

              $chkAgree.on("click", function(){
                  validator.trigger("validate");
              });

              $btnBk.on("click", function(){
                  console.log("예약완료");
                  createReservationAjax();
              });
          }
      };
  })();

  $(Reserve.init);
});
