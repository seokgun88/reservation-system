require.config({
  paths: {
    "jquery": "node_modules/jquery/dist/jquery.min",
    "Handlebars": "node_modules/handlebars/dist/handlebars.min",
    "egComponent": "node_modules/@egjs/component/dist/component.min"
  }
});

require([
  "jquery", "./reserve/bookingController", "./reserve/reserveBuilder"
], function($, BookingController, ReserveBuilder) {
  function init() {
    ReserveBuilder.init()
      .then(function(){
        BookingController.init();
      });
  }

  $(init());

});
