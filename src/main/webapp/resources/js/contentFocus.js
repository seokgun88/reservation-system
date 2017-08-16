define(["jquery"], function($){
  "use strict";
  function init(){
    $(".review_contents").on("click", "a.review_write_info", clickReviewWrite);
    $(".review_contents").on("focusout", "textarea.review_textarea", focusoutReviewWrite);
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

  return {
    init : init
  };
});
