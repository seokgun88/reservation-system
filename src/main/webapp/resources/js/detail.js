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
    var options = {
      popupReviewImageViewerCallback: initImageViewerFlicking
    };
    ProductDetail.init(options).done(initProductFlickings);
  }

  function initProductFlickings() {
    var options = {
      width: 414,
      size: $("ul.visual_img li").length,
      isAuto: false,
      btnNextClass: ".nxt",
      btnPrevClass: ".prev"
    };
    var productFlicking = new Flicking($(".group_visual"), options);
  }

  function initImageViewerFlicking() {
    $("#imageviewer").off("mousedown touchstart");
    $("#imageviewer").off("mouseup touchend");

    options = {
      width: $(window).width(),
      size: $("ul.image_list li").length,
      isAuto: false
    };
    var imageViewerFlicking = new Flicking($("#imageviewer"), options);
    imageViewerFlicking.on("afterAnimate", function(e) {
      $(".index_image").text(e.index);
    });
  }

  $(init);
});
