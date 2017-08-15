define(["jquery", "Handlebars", "egComponent", "util", "asyncRequest"],
  function($, Handlebars, egComponent, Util, ajaxRequest) {
    var productId = window.location.pathname.split("/")[2];
    var productName = "";

    function init() {
      var productDetailAjax = ajaxRequest("/api/products/" + productId, "GET")
        .then(drawProductDetail)
        .then(addButtonEventHandlers);
      var reviewsAjax = ajaxRequest("/api/products/" + productId + "/reviews?limit=3", "GET")
        .then(drawReviews);

      return $.when(productDetailAjax, reviewsAjax);
    }

    function drawProductDetail(data) {
      productName = data.name;
      drawTopImages(data);
      drawTopButtons(data);
      drawTopContent(data);
      drawTopEvent(data);
      drawReviewSummary(data);
      drawBottomDetail(data);
      drawLocation(data);
    }

    function addButtonEventHandlers() {
      $("._detail").on("click", "a", detailBtnHandler);
      $("._path").on("click", "a", locationBtnHandler);
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

    function drawTopImages(data) {
      // var topImagesTemplate = Handlebars.compile($("#topimages-template").html());
      // $topImages.html(topImagesTemplate(data.images));
      drawHandlerbarsTemplate("#topimages-template", "ul.visual_img", data);
      var $topImages = $("ul.visual_img");
      $topImages.find(".visual_txt_tit span").eq(0).html(data.name);
      $topImages.find(".visual_txt_dsc").eq(0).html(data.description);
    }

    function drawTopButtons(data) {
      drawHandlerbarsTemplate("#topbuttons-template", ".section_visual", data);
      // var topButtonsTemplate = Handlebars.compile($("#topbuttons-template").html());
      // $(".section_visual").append(topButtonsTemplate(data));
    }

    function drawTopContent(data) {
      drawHandlerbarsTemplate("#topcontent-template", ".section_store_details", data);
      // var topContentTemplate = Handlebars.compile($("#topcontent-template").html());
      // $(".section_store_details").html(topContentTemplate(data));
    }

    function drawTopEvent(data) {
      drawHandlerbarsTemplate("#topevent-template", ".section_event", data);
      // var topEventTemplate = Handlebars.compile($("#topevent-template").html());
      // $(".section_event").html(topEventTemplate(data));
    }

    function drawReviewSummary(data) {
      // var reviewSummaryTemplate = Handlebars.compile($("#reviewsummary-template").html());
      var json = {
        percentage: (data.reviewTotalScore / data.reviewCount) / 10 / 5 * 100,
        score: ((data.reviewTotalScore / data.reviewCount) / 10).toFixed(1),
        totalCount: data.reviewCount
      };
      drawHandlerbarsTemplate("#reviewsummary-template", ".grade_area", json);
      // $(".grade_area").html(reviewSummaryTemplate(json));
    }

    function drawReviews(data) {
      drawHandlerbarsTemplate("#reviews-template", ".list_short_review", data);
      // var reviewsTemplate = Handlebars.compile($("#reviews-template").html());
      // $(".list_short_review").html(reviewsTemplate(imageDataMapper(data)));
    }

    function imageDataMapper(data) {
      var images = [];
      data.forEach(function(item) {
        var mainImageUri = "",
          imageCount = 0,
          hasImage = " hide";
        if (item.images) {
          mainImageUri = "/api/images/" + item.images[0];
          imageCount = item.images.length;
          hasImage = "";
        }
        var date = new Date(item.modifyDate);
        var modifyDate = date.getFullYear() + '.' + (date.getMonth() + 1) + '.' + date.getDate() + '.';
        var image = {
          mainImageUri: mainImageUri,
          imageCount: imageCount,
          hasImage: hasImage,
          productName: productName,
          review: item.review,
          score: item.score / 10,
          user: item.userEmail.substr(0, 4) + "****",
          modifyDate: modifyDate
        };
        images.push(image);
      });
      return images;
    }

    function drawBottomDetail(data) {
      drawHandlerbarsTemplate("#bottomdetail-template", ".detail_area", data);
      // var bottomDetailTemplate = Handlebars.compile($("#bottomdetail-template").html());
      // $(".detail_area").html(bottomDetailTemplate(data));
    }

    function drawLocation(data) {
      drawHandlerbarsTemplate("#location-template", ".detail_location", data);
      // var locationTemplate = Handlebars.compile($("#location-template").html());
      // $(".detail_location").html(locationTemplate(data));
    }

    function drawHandlerbarsTemplate(templateId, parentTag, data) {
      var template = Handlebars.compile($(templateId).html());
      $(parentTag).html(template(data));
    }

    return {
      init: init
    };
  });
