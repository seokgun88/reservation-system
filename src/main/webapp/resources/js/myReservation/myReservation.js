require.config({
  paths: {
    "jquery": "../node_modules/jquery/dist/jquery.min",
    "Handlebars": "../node_modules/handlebars/dist/handlebars.min",
    "asyncRequest": "../asyncRequest"
  }
});

require([
  "jquery", "asyncRequest", "../handlebarsWrapper", "../formatter"
], function($, ajaxRequest, HandlebarsWrapper, Formatter) {
  var typeCounts = [];
  var RESERVATIONS_TYPE = {
    NOT_USED: 0,
    CONFIRMED: 1,
    USED: 2,
    CANCELED: 3
  };

  function init() {
    ajaxRequest("/api/reservations/my", "GET").then(function(data) {
      setBookingTypeCount(data.typeCounts);
      drawReservations(data.reservations);
    });
  }

  function setBookingTypeCount(dataTypeCounts) {
    typeCounts[RESERVATIONS_TYPE.NOT_USED] = dataTypeCounts[0] + dataTypeCounts[1] + dataTypeCounts[2] + dataTypeCounts[3];
    typeCounts[RESERVATIONS_TYPE.CONFIRMED] = dataTypeCounts[0] + dataTypeCounts[1];
    typeCounts[RESERVATIONS_TYPE.USED] = dataTypeCounts[2];
    typeCounts[RESERVATIONS_TYPE.CANCELED] = dataTypeCounts[3];
    $.each(typeCounts, function(i, v) {
      $(".summary_board li.item[data-bk-type='" + i + "'] .figure").text(v);
    });
  }

  function drawReservations(dataReservations) {
    if (!dataReservations) {
      $(".wrap_mylist").addClass("hide");
      $(".err").removeClass("hide");
      return;
    }
    dataReservations["0"].forEach(function(v, i) {
      v.period = Formatter.getPeriod(v.displayStart, v.displayEnd);
      v.ticketCounts = Formatter.getTicketCountString(v.generalTicketCount, v.youthTicketCount, v.childTicketCount);
      v.totalPrice = v.totalPrice.toLocaleString();
    });
    dataReservations["1"].forEach(function(v, i) {
      v.period = Formatter.getPeriod(v.displayStart, v.displayEnd);
      v.ticketCounts = Formatter.getTicketCountString(v.generalTicketCount, v.youthTicketCount, v.childTicketCount);
      v.totalPrice = v.totalPrice.toLocaleString();
    });
    dataReservations["2"].forEach(function(v, i) {
      v.period = Formatter.getPeriod(v.displayStart, v.displayEnd);
      v.ticketCounts = Formatter.getTicketCountString(v.generalTicketCount, v.youthTicketCount, v.childTicketCount);
      v.totalPrice = v.totalPrice.toLocaleString();
    });
    dataReservations["3"].forEach(function(v, i) {
      v.period = Formatter.getPeriod(v.displayStart, v.displayEnd);
      v.ticketCounts = Formatter.getTicketCountString(v.generalTicketCount, v.youthTicketCount, v.childTicketCount);
      v.totalPrice = v.totalPrice.toLocaleString();
    });
    HandlebarsWrapper("#notused-template", ".list_cards .unused", dataReservations["0"], "append");
    HandlebarsWrapper("#confirmed-template", ".list_cards .confirmed", dataReservations["1"], "append");
    HandlebarsWrapper("#used-template", ".list_cards .used:not(.canceled)", dataReservations["2"], "append");
    HandlebarsWrapper("#canceled-template", ".list_cards .canceled", dataReservations["3"], "append");
  }

  $(init());
});
