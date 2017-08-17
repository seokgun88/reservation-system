require.config({
  paths: {
    "jquery": "../node_modules/jquery/dist/jquery.min",
    "Handlebars": "../node_modules/handlebars/dist/handlebars.min",
    "egComponent": "../node_modules/@egjs/component/dist/component.min",
    "asyncRequest": "../asyncRequest",
    "util": "../util"
  }
});

require([
  "jquery", "bookingController", "reserveBuilder"
], function($, BookingController, ReserveBuilder) {
  function init() {
    ReserveBuilder.init()
      .then(function(){
        BookingController.init();
      });
  }

  $(init());

});
