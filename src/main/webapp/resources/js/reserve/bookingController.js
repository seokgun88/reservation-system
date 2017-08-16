define(["jquery", "./ticket", "./bookingForm", "asyncRequest"],
  function($, Ticket, BookingForm, ajaxRequest) {
    "use strict";

    var tickets = {};
    var totalCount = 0;
    var totalPrice = 0;

    var $bkBtnWrap = null;
    var $bkBtn = null;
    var $btnAgreement = null;

    var bookingForm = null;

    function init() {
      bookingForm = new BookingForm();

      $bkBtnWrap = $(".bk_btn_wrap");
      $bkBtn = $bkBtnWrap.find(".bk_btn");
      $btnAgreement = $(".btn_agreement");

      initEvents();
      initTickets();
      initBookingForm();
    }

    function initEvents() {
      $bkBtn.on("click", doReservation);
      $btnAgreement.on("click", toggleAgreement);
    }

    function toggleAgreement(e) {
      e.preventDefault();
      $(e.target).closest(".agreement").toggleClass("open");
    }

    function doReservation(e) {
      e.preventDefault();
      var bookingUser = bookingForm.getBookingUser();
      var data = JSON.stringify({
        productId: window.location.pathname.split("/")[2],
        generalTicketCount: tickets[1].getCount(),
        youthTicketCount: tickets[2].getCount(),
        childTicketCount: tickets[3].getCount(),
        reservationName: bookingUser.userName,
        reservationTel: bookingUser.tel,
        reservationEmail: bookingUser.email,
        reservationType: 0,
        totalPrice: totalPrice,
        reservationDate: "2017-08-16"
      });
      ajaxRequest("/api/reservations", "POST", data).done(function(e) {
        console.log(e);
      });
    }

    function initTickets() {
      $(".qty").map(function(k, v) {
        var ticket = new Ticket($(this));
        tickets[$(this).data("price-type")] = ticket;
        ticket.on("change", function(e) {
          this.$countEle.val(this.count);
          this.$totalPriceEle.text((this.price * this.count).toLocaleString());
          if (this.isPlus) {
            totalCount++;
            totalPrice += this.price;
          } else {
            totalCount--;
            totalPrice -= this.price;
          }
          $(".total_count").text(totalCount);
        });
      });
    }

    function initBookingForm() {
      bookingForm.on("validate", function(e) {
        $bkBtnWrap.removeClass("disable");
      });
      bookingForm.on("unvalidate", function(e) {
        if (!$bkBtnWrap.hasClass("disable")) {
          $bkBtnWrap.addClass("disable");
        }
      });
    }

    return {
      init: init
    };
  });
