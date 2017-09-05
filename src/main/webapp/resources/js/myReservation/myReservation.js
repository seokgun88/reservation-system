require.config({
  paths: {
    "jquery": "../node_modules/jquery/dist/jquery.min",
    "Handlebars": "../node_modules/handlebars/dist/handlebars.min",
    "asyncRequest": "../asyncRequest"
  }
});

require([
  "jquery", "asyncRequest", "../handlebarsWrapper", "../formatter", "cards"
], function($, ajaxRequest, HandlebarsWrapper, Formatter, cards) {
  var STATE = {
    NOT_USED: 0,
    CONFIRMED: 1,
    USED: 2,
    CANCELED: 3
  };

  var typeCounts = [];

  var $unUsedCards = $(cards[STATE.NOT_USED].targetTag);
  var $confirmedCards = $(cards[STATE.CONFIRMED].targetTag);
  var $usedCards = $(cards[STATE.USED].targetTag);
  var $canceledCards = $(cards[STATE.CANCELED].targetTag);
  var $popupBookingWrapper = $(".popup_booking_wrapper");

  function init() {
    ajaxRequest("/api/reservations/my", "GET").then(function(data) {
      setBookingTypeCount(data.typeCounts);
      drawReservations(data.reservations);
      initEvent();
    });
  }

  var $currentCard = null;

  function initEvent() {
    $unUsedCards.on("click", "button.btn", showCancelPopup);
    $confirmedCards.on("click", "button.btn", showCancelPopup);
    $usedCards.on("click", "button.btn", moveReviewWrite);
    $popupBookingWrapper.on("click", ".btn_gray", hideCancelPopup);
    $popupBookingWrapper.on("click", ".btn_green", cancel);
    $popupBookingWrapper.on("click", hideCancelPopup);
  }

  function hideCancelPopup(e) {
    $popupBookingWrapper.hide();
    e.preventDefault();
  }

  function showCancelPopup(e) {
    $currentCard = $(e.currentTarget).closest("article");
    var productName = $currentCard.find(".tit").text();
    var period = $currentCard.find(".item_dsc:eq(0)").text();

    updateCancelPopup(productName, period);

    $popupBookingWrapper.show();
    e.preventDefault();
  }

  function updateCancelPopup(productName, period) {
    $popupBookingWrapper.find(".pop_tit > span").text(productName);
    $popupBookingWrapper.find(".pop_tit > .sm").text(period);
  }

  function cancel(e) {
    if (isEmpty($currentCard.siblings("article"))) {
      $currentCard.closest("li").hide();
    }
    typeCounts[STATE.CONFIRMED]--;
    typeCounts[STATE.CANCELED]++;
    $.each(typeCounts, function(i, v) {
      $(".summary_board li.item[data-bk-type='" + i + "'] .figure").text(v);
    });
    $currentCard.find("div.booking_cancel").addClass("hide");
    if($(cards[STATE.CANCELED].targetTag).hasClass("hide")){
      $(cards[STATE.CANCELED].targetTag).removeClass("hide");
    }
    $currentCard.appendTo(cards[STATE.CANCELED].targetTag);
    hideCancelPopup(e);

    var id = $currentCard.data("id");
    updateBookingState(id, STATE.CANCELED);
    e.preventDefault();
  }

  function updateBookingState(id, state) {
    ajaxRequest("/api/reservations/" + id, "PUT", JSON.stringify({
      "reservationType": state
    })).then(function() {
      console.log("success");
    }, function() {
      console.log("failed");
    });
  }

  function isEmpty($target) {
    if ($target.length === 0) {
      return true;
    }
    return false;
  }

  function moveReviewWrite(e) {
    $currentCard = $(e.currentTarget).closest("article");
    var productId = $currentCard.data("productId");
    window.location.href = "/products/" + productId + "/review";
    e.preventDefault();
  }

  function setBookingTypeCount(dataTypeCounts) {
    typeCounts[STATE.NOT_USED] = dataTypeCounts[0] + dataTypeCounts[1] + dataTypeCounts[2] + dataTypeCounts[3];
    typeCounts[STATE.CONFIRMED] = dataTypeCounts[0] + dataTypeCounts[1];
    typeCounts[STATE.USED] = dataTypeCounts[2];
    typeCounts[STATE.CANCELED] = dataTypeCounts[3];
    $.each(typeCounts, function(i, v) {
      $(".summary_board li.item[data-bk-type='" + i + "'] .figure").text(v);
    });
  }

  function drawReservations(dataReservations) {
    for ( var i in dataReservations ) {
        if (!dataReservations.hasOwnProperty(i)) {
          $(".wrap_mylist").addClass("hide");
          $(".err").removeClass("hide");
          return;
        }
    }
    $.map(dataReservations, function(v, i) {
      if (v) {
        v.forEach(addExValues);
        $(cards[i].targetTag).removeClass("hide");
        HandlebarsWrapper(cards[i].templateId, cards[i].targetTag, v, "append");
      }
    });
  }

  function addExValues(reservation) {
    reservation.period = Formatter.getPeriod(reservation.product.productDisplay.displayStart, reservation.product.productDisplay.displayEnd);
    reservation.ticketCounts = Formatter.getTicketCountString(reservation.generalTicketCount, reservation.youthTicketCount, reservation.childTicketCount);
    reservation.totalPrice = reservation.totalPrice.toLocaleString();
  }

  $(init());
});
