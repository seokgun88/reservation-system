require.config({
  paths: {
    "jquery": "../node_modules/jquery/dist/jquery.min",
    "Handlebars": "../node_modules/handlebars/dist/handlebars.min",
    "asyncRequest": "../asyncRequest"
  }
});

require([
  "jquery", "asyncRequest", "rating", "contentFocus", "imageHandler"
], function($, ajaxRequest, Rating, ContentFocus, ImageHandler) {
  function init() {
    Rating.init();
    ContentFocus.init();
    ImageHandler.init();
    $(".box_bk_btn").on("click", "button.bk_btn", submit);
  }

  function submit(e) {
    var imageIds = [];
    $(".lst_thumb li.item").each(function(i, ele) {
      imageIds.push($(ele).data("id"));
    });
    var data = JSON.stringify({
      review: {
        productId: document.location.pathname.split("/")[2],
        score: ($(".rating_rdo:checked").length - 1) * 10,
        review: $("textarea.review_textarea").val()
      },
      imageIds: imageIds
    });

    ajaxRequest("/api/reviews", "POST", data).done(function(data) {
      if (data) {
    	alert("리뷰를 등록하였습니다.");
        window.location.href = "/my";
      } else {
        alert("리뷰 등록을 실패했습니다...");
      }
    });
  }

  $(init());
});
