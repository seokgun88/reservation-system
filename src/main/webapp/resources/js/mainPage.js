var MainPage = (function() {
  "use strict";

  var categoryIdx = 0,
      page = 1,
      categoriesTemplate = Handlebars.compile($("#categories-template").html()),
      productsTemplate = Handlebars.compile($("#products-template").html());

  function init() {
    ajaxRequest("/api/categories", "GET")
      .then(drawCategories, ajaxErrorHandler)
      .then(getProducts);

    $(".event_tab_lst").on("click", "li.item", tabClick);
  }

  function drawCategories(data) {
    $(".event_tab_lst").html(categoriesTemplate(data));

    var $category = $(".event_tab_lst li.item[data-category=" + categoryIdx + "]");
    $category.find("a.anchor").addClass("active");

    categoryCountUpdate($category);
  }

  function tabClick(e) {
    var $ele = $(e.currentTarget);

    $(".event_tab_lst li.item[data-category=" + categoryIdx + "] a.anchor").removeClass("active");
    categoryIdx = $ele.data("category");
    $ele.find(".anchor").addClass("active");

    categoryCountUpdate($ele);
    getProducts();
  }

  function getProducts() {
    ajaxRequest("/api/categories/"+categoryIdx+"/products?page=1", "GET")
    .then(drawProducts, ajaxErrorHandler);
  }

  function drawProducts(data) {
    var $leftBox = $(".lst_event_box.left_box"),
        $rightBox = $(".lst_event_box.right_box"),
        leftData = [],
        rightData = [];

    for (var i = 0; i < data.length; i++) {
      (i%2 == 0) ? leftData.push(data[i]) : rightData.push(data[i]) ;
    }
    $leftBox.html(productsTemplate(leftData));
    $rightBox.html(productsTemplate(rightData));
  }

  function categoryCountUpdate($ele) {
    var count = $ele.data("count");
    $(".event_lst_txt .pink").text(count + "개");
  }

  function ajaxErrorHandler(error) {
    console.log(error);
  }

  return {
    init: init
  };

})();

$(MainPage.init);
