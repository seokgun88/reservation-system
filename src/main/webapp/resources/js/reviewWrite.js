require.config({
  paths : {
    "jquery" : "node_modules/jquery/dist/jquery.min",
    "egComponent" : "node_modules/@egjs/component/dist/component.min"
  }
});

require([
  "jquery", "egComponent", "util"
], function($, egComponent, Util){
  var ReviewWrite = (function(){
    "use strict";

    function init(){
      $(".review_contents").on("click", "a.review_write_info", clickReviewWrite);
      $(".review_contents").on("focusout", "textarea.review_textarea", focusoutReviewWrite);
      $(".rating").on("click", "input.rating_rdo", paintStars);
    }

    function clickReviewWrite(e){
      var $ele = $(e.currentTarget);
      e.preventDefault();
      $ele.hide().siblings("textarea.review_textarea").focus();
    }

    function focusoutReviewWrite(e){
      var $ele = $(e.currentTarget);
      if($ele.val() === ""){
        $ele.siblings("a.review_write_info").show();
      }
    }

    function paintStars(e){
      var $ele = $(e.currentTarget),
          $eleSibling = $ele.siblings(".rating_rdo"),
          ratingValue = parseInt($ele.val()),
          numOfCheckedSiblings = $ele.siblings(".rating_rdo:checked").length;

      if(numOfCheckedSiblings !== ratingValue){
        $ele.prop("checked", true);
      }
      // $eleSibling.filter(function(){
      //   return $(this).val() < ratingValue;
      // }).prop("checked", true);
      // $eleSibling.filter(function(){
      //   return $(this).val() > ratingValue;
      // }).prop("checked", false);
      $eleSibling.filter(compareValue.bind(this, "<", ratingValue)).prop("checked", true);
      $eleSibling.filter(compareValue.bind(this, ">", ratingValue)).prop("checked", false);

      setRatingScore();
    }

    function compareValue(option, ratingValue, index, ele){
      return (option==="<") ? $(ele).val() < ratingValue : $(ele).val() > ratingValue;
    }

    function setRatingScore(){
      var ratingScore = $(".rating .rating_rdo:checked").length - 1;
      if(ratingScore > 0){
        $(".rating .star_rank").removeClass("gray_star").text(ratingScore);
      }else{
        $(".rating .star_rank").addClass("gray_star").text(0);
      }
    }

    return {
      init : init
    };
  })();

  $(ReviewWrite.init);
});
