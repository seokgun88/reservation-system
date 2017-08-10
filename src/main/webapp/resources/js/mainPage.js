var MainPage = (function(){
  "use strict";
  var currentCategoryIndex = 0;
  var source = $("#category-template").html();
  var template = Handlebars.compile(source);
  function init(){
    $.ajax({
      url : "/api/categories",
			method : "GET"
		})
    .then(function(result){
      $(".event_tab_lst").append(template(result));
      $(".event_tab_lst li.item[data-category='"+currentCategoryIndex+"'] a.anchor").addClass("active");
      categoryCountUpdate($(".event_tab_lst li.item[data-category='"+currentCategoryIndex+"']"));
    });
    $(".event_tab_lst").on("click", "li.item", tabClick);
  }

  function categoryCountUpdate($ele){
    var count = $ele.data("count");
    $(".event_lst_txt .pink").text(count+"개");
  }

  function tabClick(e){
    var $ele = $(e.currentTarget);
    $(".event_tab_lst li.item[data-category='"+currentCategoryIndex+"'] a.anchor").removeClass("active");
    currentCategoryIndex = $ele.data("category");
    $ele.find(".anchor").addClass("active");
    categoryCountUpdate($ele);
  }

  return {
    init : init
  };
})();

$(MainPage.init);
