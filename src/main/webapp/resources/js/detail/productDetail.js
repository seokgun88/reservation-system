define(["jquery", "Handlebars", "egComponent", "util", "asyncRequest", "formatter", "reviewMapper", "handlebarsWrapper"],
  function($, Handlebars, egComponent, Util, ajaxRequest, Formatter, ReviewMapper, HandlebarsWrapper) {
    "use strict";

    var reviewIdToImageIds = [],
      _options = {};

    function init(options) {
      _options = options;

      addEventHandlers();
      addReviewImageClickEventHandler();
    }

    function addReviewImageClickEventHandler() {
      $(".thumb").on("click", function(e) {
        e.preventDefault();
        var reviewId = $(e.target).closest("li").data("id");
        var reviewImageIds = ReviewMapper.getReviewImageIds(reviewId);
        popupReviewImageViewer(reviewImageIds);
      });
      $(".btn_close").on("click", function(e) {
        $("#imageviewer").addClass("hide");
      });
    }

    function popupReviewImageViewer(ids) {
      $("#imageviewer").removeClass("hide");
      $(".total_image").text(ids.length);
      HandlebarsWrapper("#imageviewer-template", ".image_list", ids);
      if (_options.popupReviewImageViewerCallback) {
        _options.popupReviewImageViewerCallback();
      }
    }

    function addEventHandlers() {
      $(".bk_more._open").on("click", moreOpenBtnHandler);
      $(".bk_more._close").on("click", moreCloseBtnHandler);
      $(".bk_btn").on("click", booking);
      $("._detail").on("click", "a", detailBtnHandler);
      $("._path").on("click", "a", locationBtnHandler);
      $(window).on("scroll", lazyLoad);
    }

    function moreOpenBtnHandler(e) {
      e.preventDefault();
      $(".bk_more._open").addClass("hide");
      $(".bk_more._close").removeClass("hide");
      $(".store_details").removeClass("close3");
    }

    function moreCloseBtnHandler(e) {
      e.preventDefault();
      $(".bk_more._open").removeClass("hide");
      $(".bk_more._close").addClass("hide");
      $(".store_details").addClass("close3");
    }

    function booking() {
      if (new Date() > _options.productData.salesEnd) {
        alert("판매기간 종료되었습니다.");
      } else if (!_options.productData.salesFlag) {
        alert("매진 되었습니다.");
      } else {
        window.location.href = "/products/" + _options.productData.productId + "/reservation";
      }
    }

    function detailBtnHandler(e) {
      e.preventDefault();
      $("._detail a").addClass("active");
      $("._path a").removeClass("active");
      $(".detail_area_wrap").removeClass("hide");
      $(".detail_location").addClass("hide");
    }

    function locationBtnHandler(e) {
      e.preventDefault();
      $("._detail a").removeClass("active");
      $("._path a").addClass("active");
      $(".detail_area_wrap").addClass("hide");
      $(".detail_location").removeClass("hide");
    }

    function lazyLoad() {
      var $lazyLoadedImage = $(".img_thumb[data-lazy-image]");
      var subImageTop = $lazyLoadedImage.offset().top;
      var scrollY = window.scrollY;
      var windowHeight = window.innerHeight;
      if (windowHeight + scrollY > subImageTop - 100) {
        var imgSrc = $lazyLoadedImage.data("lazyImage");
        $lazyLoadedImage.attr("src", imgSrc);
        $(window).off("scroll", lazyLoad);
      }
    }

    return {
      init: init
    };
  });
