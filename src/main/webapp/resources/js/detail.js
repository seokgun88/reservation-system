require.config({
  paths: {
    "jquery": "node_modules/jquery/dist/jquery.min",
    "Handlebars": "node_modules/handlebars/dist/handlebars.min",
    "egComponent": "node_modules/@egjs/component/dist/component.min"
  }
});

require([
  "jquery", "Handlebars", "egComponent", "util", "flicking", "asyncRequest", "productDetail"
], function($, Handlebars, egComponent, Util, Flicking, ajaxRequest, ProductDetail) {

  function init() {
    ProductDetail.init().done(initFlickings);
  }

  function initFlickings() {
    var options = {
      width: 414,
      size: $("ul.visual_img li").length,
      isAuto: false,
      btnNextClass: ".nxt",
      btnPrevClass: ".prev"
    };
    var productFlicking = new Flicking($(".group_visual"), options);
  }

  $(init());
});
