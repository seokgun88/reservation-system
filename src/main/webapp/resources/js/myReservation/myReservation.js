require.config({
  paths: {
    "jquery": "../node_modules/jquery/dist/jquery.min",
    "Handlebars": "../node_modules/handlebars/dist/handlebars.min",
    "asyncRequest": "../asyncRequest"
  }
});

require([
  "jquery", "asyncRequest"
], function($, ajaxRequest) {
  var typeCounts=[];
  function init() {
    ajaxRequest("/api/reservations/my", "GET").then(function(data) {
      typeCounts = data.typeCounts;
      setBookingType(typeCounts);

    });
  }

  function setBookingType(typeCounts) {
    $(".summary_board li.item").each(function() {
      var type = $(this).data("bkType");
      switch (type) {
        case 0:
          $(this).find(".figure").text(typeCounts[0] + typeCounts[1] + typeCounts[2] + typeCounts[3]);
          break;
        case 1:
          $(this).find(".figure").text(typeCounts[0] + typeCounts[1]);
          break;
        case 2:
          $(this).find(".figure").text(typeCounts[2]);
          break;
        case 3:
          $(this).find(".figure").text(typeCounts[3]);
          break;
        default:
          $(this).find(".figure").text(0);
          break;
      }
    });
  }

  $(init());
});
