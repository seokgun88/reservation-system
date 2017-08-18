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
  var RESERVATIONS_TYPE = {
    NOT_USED: 0,
    CONFIRMED: 1,
    USED: 2,
    CANCELED: 3
  };

  var typeCounts = [];

  var template = [{
    "templateId": "#notused-template",
    "targetTag": ".list_cards .unused"
  }, {
    "templateId": "#confirmed-template",
    "targetTag": ".list_cards .confirmed"
  }, {
    "templateId": "#used-template",
    "targetTag": ".list_cards .used:not(.canceled)"
  }, {
    "templateId": "#canceled-template",
    "targetTag": ".list_cards .canceled"
  }];

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
    $.map(dataReservations, function(v, i) {
      if (v) {
        v.forEach(addExValues);
        $(template[i].targetTag).removeClass("hide");
        HandlebarsWrapper(template[i].templateId, template[i].targetTag, v, "append");
      }
    });
  }

  function addExValues(reservation) {
    reservation.period = Formatter.getPeriod(reservation.displayStart, reservation.displayEnd);
    reservation.ticketCounts = Formatter.getTicketCountString(reservation.generalTicketCount, reservation.youthTicketCount, reservation.childTicketCount);
    reservation.totalPrice = reservation.totalPrice.toLocaleString();
  }

  $(init());
});
