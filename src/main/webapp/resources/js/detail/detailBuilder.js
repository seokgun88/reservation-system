define(["jquery", "Handlebars", "egComponent", "util", "asyncRequest", "naverMap", "handlebarsWrapper", "formatter", "reviewMapper"],
  function($, Handlebars, egComponent, Util, ajaxRequest, NaverMap, HandlebarsWrapper, Formatter, ReviewMapper) {
    "use strict";

    var productId = window.location.pathname.split("/")[2];
    var salesEnd = null;
    var salesFlag = null;

    function init() {
      return get().then(setPage);
    }

    function get() {
      var productDetailAjax = ajaxRequest("/api/products/" + productId, "GET");
      var reviewsAjax = ajaxRequest("/api/products/" + productId + "/reviews?limit=3", "GET");

      return $.when(productDetailAjax, reviewsAjax);
    }

    function setPage(detail, review) {
      drawProductDetail(detail[0]);
      drawReviews(review[0]);

      NaverMap.init(detail[0]);
    }

    function drawProductDetail(data) {
      salesEnd = data.salesEnd;
      salesFlag = data.salesFlag;
      drawTopImages(data);
      drawTopButtons(data);
      drawTopContent(data);
      drawTopEvent(data);
      drawReviewSummary(data);
      drawBottomDetail(data);
      drawLocation(data);
    }

    function drawTopImages(data) {
      HandlebarsWrapper("#topimages-template", "ul.visual_img", data.images);
      var $topImages = $("ul.visual_img");
      $topImages.find(".visual_txt_tit span").eq(0).html(data.name);
      $topImages.find(".visual_txt_dsc").eq(0).html(data.description);
    }

    function drawReviewSummary(data) {
      var json = {
        percentage: (data.reviewTotalScore / data.reviewCount) / 10 / 5 * 100,
        score: ((data.reviewTotalScore / data.reviewCount) / 10).toFixed(1),
        totalCount: data.reviewCount
      };
      HandlebarsWrapper("#reviewsummary-template", ".grade_area", json);
    }

    function drawTopButtons(data) {
      HandlebarsWrapper("#topbuttons-template", ".section_visual", data, "append");
    }

    function drawTopContent(data) {
      HandlebarsWrapper("#topcontent-template", ".section_store_details", data);
    }

    function drawTopEvent(data) {
      HandlebarsWrapper("#topevent-template", ".section_event", data);
    }

    function drawBottomDetail(data) {
      HandlebarsWrapper("#bottomdetail-template", ".detail_area", data);
    }

    function drawLocation(data) {
      HandlebarsWrapper("#location-template", ".detail_location", data);
    }

    function drawReviews(data) {
      HandlebarsWrapper("#reviews-template", ".list_short_review", ReviewMapper.getReviews(data));
    }

    return {
      init: init
    };
  });
