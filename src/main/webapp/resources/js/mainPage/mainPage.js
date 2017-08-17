require.config({
  paths: {
    "jquery": "../node_modules/jquery/dist/jquery.min",
    "Handlebars": "../node_modules/handlebars/dist/handlebars.min",
    "egComponent": "../node_modules/@egjs/component/dist/component.min",
    "asyncRequest": "../asyncRequest",
    "util": "../util",
    "flicking": "../flicking"
  }
});

require([
  "jquery", "Handlebars", "egComponent", "flicking", "productlist"
], function($, Handlebars, egComponent, Flicking, List) {

  function init() {
    List.init().done(initFlicking);
  }

  function initFlicking() {
    var options = {
      width: 338,
      size: $("ul.visual_img li").length,
      isAuto: true,
      btnNextClass: ".btn_nxt_e",
      btnPrevClass: ".btn_pre_e"
    };
    var flicking = new Flicking(".container_visual", options);
  }

  $(init());

});
