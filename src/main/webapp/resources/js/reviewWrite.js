require.config({
  paths : {
    "jquery" : "node_modules/jquery/dist/jquery.min",
    "Handlebars" : "node_modules/handlebars/dist/handlebars.min",
    "egComponent" : "node_modules/@egjs/component/dist/component.min"
  }
});

require([
  "jquery", "Handlebars", "egComponent", "util", "asyncRequest", "rating", "contentFocus", "imageHandler"
], function($, Handlebars, egComponent, Util, ajaxRequest, Rating, ContentFocus, ImageHandler){
  function init(){
    Rating.init();
    ContentFocus.init();
    ImageHandler.init();
    $("box_bk_btn").on("click", "button.bk_btn", function(e){
      var data = {
        productId : ,
        ratingScore : ,
        comment : ,
        images : []
      };

      $.ajax({
        url : "/api/review",
        type : "POST",
        data : data,
        contentType : "application/json; charset=UTF-8"
      });

    });
  }

  $(init());
});
