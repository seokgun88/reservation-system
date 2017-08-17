define(["jquery", "Handlebars", "egComponent", "util", "asyncRequest"],
  function($, Handlebars, egComponent, Util, ajaxRequest) {
    "use strict";

    var productId = window.location.pathname.split("/")[2],
      reviewIdToImageIds = [],
      _options = {},
      salesEnd = new Date(),
      salesFlag = false;

    function init(options) {
      _options = options;

      var productDetailAjax = ajaxRequest("/api/products/" + productId, "GET")
        .then(drawProductDetail)
        .then(addEventHandlers);
      var reviewsAjax = ajaxRequest("/api/products/" + productId + "/reviews?limit=3", "GET")
        .then(drawReviews)
        .then(addReviewImageClickEventHandler);

      return $.when(productDetailAjax, reviewsAjax);
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
      initNaverMap(data);
    }

    function drawHandlerbarsTemplate(templateId, parentTag, data, type) {
      var template = Handlebars.compile($(templateId).html());
      type = type ? type : "html";
      $(parentTag)[type](template(data));
    }

    function drawTopImages(data) {
      drawHandlerbarsTemplate("#topimages-template", "ul.visual_img", data.images);
      var $topImages = $("ul.visual_img");
      $topImages.find(".visual_txt_tit span").eq(0).html(data.name);
      $topImages.find(".visual_txt_dsc").eq(0).html(data.description);
    }

    function drawTopButtons(data) {
      drawHandlerbarsTemplate("#topbuttons-template", ".section_visual", data, "append");
    }

    function drawTopContent(data) {
      drawHandlerbarsTemplate("#topcontent-template", ".section_store_details", data);
    }

    function drawTopEvent(data) {
      drawHandlerbarsTemplate("#topevent-template", ".section_event", data);
    }

    function drawReviewSummary(data) {
      var json = {
        percentage: (data.reviewTotalScore / data.reviewCount) / 10 / 5 * 100,
        score: ((data.reviewTotalScore / data.reviewCount) / 10).toFixed(1),
        totalCount: data.reviewCount
      };
      drawHandlerbarsTemplate("#reviewsummary-template", ".grade_area", json);
    }

    function drawReviews(data) {
      drawHandlerbarsTemplate("#reviews-template", ".list_short_review", imageDataMapper(data));
    }

    function addReviewImageClickEventHandler() {
      $(".thumb").on("click", function(e) {
        e.preventDefault();
        var reviewId = $(e.target).closest("li").data("id");
        var reviewImageIds = reviewIdToImageIds[reviewId];
        popupReviewImageViewer(reviewImageIds);
      });
      $(".btn_close").on("click", function(e) {
        $("#imageviewer").addClass("hide");
      });
    }

    function popupReviewImageViewer(ids) {
      $("#imageviewer").removeClass("hide");
      $(".total_image").text(ids.length);
      drawHandlerbarsTemplate("#imageviewer-template", ".image_list", ids);
      if (_options.popupReviewImageViewerCallback) {
        _options.popupReviewImageViewerCallback();
      }
    }

    function imageDataMapper(data) {
      var reviews = [];
      var productName = data.name;
      data.forEach(function(item) {
        var mainImageUri = "",
          imageCount = 0,
          hasImage = " hide";
        if (item.images) {
          reviewIdToImageIds[item.id] = item.images;
          mainImageUri = "/api/images/" + item.images[0];
          imageCount = item.images.length;
          hasImage = "";
        }
        var date = new Date(item.modifyDate);
        var modifyDate = date.getFullYear() + '.' + (date.getMonth() + 1) + '.' + date.getDate() + '.';
        var review = {
          id: item.id,
          mainImageUri: mainImageUri,
          imageCount: imageCount,
          hasImage: hasImage,
          productName: productName,
          review: item.review,
          score: item.score / 10,
          user: item.userEmail.substr(0, 4) + "****",
          modifyDate: modifyDate
        };
        reviews.push(review);
      });
      return reviews;
    }

    function drawBottomDetail(data) {
      drawHandlerbarsTemplate("#bottomdetail-template", ".detail_area", data);
    }

    function drawLocation(data) {
      drawHandlerbarsTemplate("#location-template", ".detail_location", data);
    }

    function initNaverMap(data) {
      naver.maps.Service.geocode({
          address: data.placeStreet
        },
        function(status, response) {
          if (status === naver.maps.Service.Status.ERROR) {
            console.log("Naver geocode error");
            return;
          }

          var item = response.result.items[0],
            point = new naver.maps.Point(item.point.x, item.point.y);

          drawNaverMap(point);
          addNaverMapButtonEventHandlers(point, data.placeName);
        });
    }

    function drawNaverMap(point) {
      var map = new naver.maps.Map('map', {
        center: new naver.maps.LatLng(point.y, point.x),
        zoom: 10,
        draggable: false
      });
      var marker = new naver.maps.Marker({
        position: new naver.maps.LatLng(point.y, point.x),
        map: map
      });
    }

    function addNaverMapButtonEventHandlers(point, placeName) {
      $("#map").on('click', function(e) {
        e.preventDefault();
        url = 'http://map.naver.com/index.nhn?enc=utf8&level=2&lng=' + point.x + '&lat=' + point.y +
          '&pinTitle=' + placeName + '&pinType=SITE';
        window.open(url);
      });

      var pathUrl = 'http://map.naver.com/?&enc=utf8&dtPathType=0&menu=route&mapMode=0&pathType=1' +
        '&elng=' + point.x + '&elat=' + point.y + '&eText=' + placeName;

      $(".btn_path").attr('href', pathUrl);
      $(".btn_goto_path").attr('href', pathUrl);
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
      if (new Date() > salesEnd) {
        alert("판매기간 종료되었습니다.");
      } else if (!salesFlag) {
        alert("매진 되었습니다.");
      } else {
        window.location.href = "/products/" + productId + "/reservation";
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

    function setOptions(options) {
      _options = options;
    }

    return {
      init: init
    };
  });
