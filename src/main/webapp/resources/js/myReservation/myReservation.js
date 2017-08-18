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

  function init() {
    ajaxRequest("/api/reservations/my", "GET").then(function(data) {
      setBookingTypeCount(data.typeCounts);
      drawReservations(data.reservations);
    });
  }

  function setBookingTypeCount(dataTypeCounts) {
    typeCounts[0] = dataTypeCounts[0] + dataTypeCounts[1] + dataTypeCounts[2] + dataTypeCounts[3];
    typeCounts[1] = dataTypeCounts[0] + dataTypeCounts[1];
    typeCounts[2] = dataTypeCounts[2];
    typeCounts[3] = dataTypeCounts[3];
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
    });
    console.log(dataReservations["0"]);
    HandlebarsWrapper("#notused-template", ".list_cards", dataReservations["0"], "append");
  }

  $(init());
});
