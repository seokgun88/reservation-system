define(["jquery"], function($){
  "use strict";
  function init(){
    $(".rating").on("click", "input.rating_rdo", paintStars);
  }

  function paintStars(e){
    var $ele = $(e.currentTarget),
        $eleDelegateTarget = $(e.delegateTarget),

        $eleSibling = $eleDelegateTarget.(".rating_rdo"),
        ratingValue = parseInt($ele.val()),
        numOfCheckedSiblings = $eleSibling.find(":checked").length;

    if(numOfCheckedSiblings !== ratingValue){
      $ele.prop("checked", true);
    }
    $eleSibling.filter(compareValue.bind(this, "<", ratingValue)).prop("checked", true);
    $eleSibling.filter(compareValue.bind(this, ">", ratingValue)).prop("checked", false);

    setRatingScore();
  }

  function compareValue(option, ratingValue, index, ele){
    return (option==="<") ? $(ele).val() < ratingValue : $(ele).val() > ratingValue;
  }

  function setRatingScore(){
    var ratingScore = $(".rating .rating_rdo:checked").length - 1;
    (ratingScore > 0) ? $(".rating .star_rank").removeClass("gray_star").text(ratingScore) : $(".rating .star_rank").addClass("gray_star").text(0);
  }

  return {
    init : init
  };
});
