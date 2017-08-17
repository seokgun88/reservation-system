define(["jquery", "Handlebars", "egComponent", "util", "asyncRequest"],
  function($, Handlebars, egComponent, Util, ajaxRequest) {
    "use strict";

    var categoryIdx = 0,
      page = 1,
      promotionsTemplate = Handlebars.compile($("#promotions-template").html()),
      categoriesTemplate = Handlebars.compile($("#categories-template").html()),
      productsTemplate = Handlebars.compile($("#products-template").html());

    function init() {
      var promotionAjax = ajaxRequest("/api/products/promotion", "GET")
        .then(drawPromotions, ajaxErrorHandler);

      var categoriesAjax = ajaxRequest("/api/categories", "GET")
        .then(drawCategories, ajaxErrorHandler)
        .then(getProducts.bind(this, "html"));

      $(".event_tab_lst").on("click", "li.item", tabClick);
      $(window).on("scroll", scrollUpdate);

      return $.when(promotionAjax, categoriesAjax);
    }

    function drawCategories(data) {
      $(".event_tab_lst").html(categoriesTemplate(data));

      var $category = $(".event_tab_lst li.item[data-category=" + categoryIdx + "]");
      $category.find("a.anchor").addClass("active");

      categoryCountUpdate($category);
    }

    function tabClick(e) {
      var $ele = $(e.currentTarget);
      page = 1;

      $(".more").removeClass("hide");
      $(".event_tab_lst li.item[data-category=" + categoryIdx + "] a.anchor").removeClass("active");
      categoryIdx = $ele.data("category");
      $ele.find(".anchor").addClass("active");

      categoryCountUpdate($ele);
      getProducts("html");
    }

    function getProducts(option) {
      ajaxRequest("/api/categories/" + categoryIdx + "/products?page=" + page, "GET")
        .then(drawProducts.bind(this, option), ajaxErrorHandler);
    }

    function drawProducts(option, data) {
      var $leftBox = $(".lst_event_box.left_box"),
        $rightBox = $(".lst_event_box.right_box"),
        leftData = [],
        rightData = [];

      for (var i = 0; i < data.length; i++) {
        (i % 2 == 0) ? leftData.push(data[i]): rightData.push(data[i]);
      }
      $leftBox[option](productsTemplate(leftData));
      $rightBox[option](productsTemplate(rightData));
    }

    function drawPromotions(data) {
      $("ul.visual_img").html(promotionsTemplate(data));
    }

    function categoryCountUpdate($ele) {
      var count = $ele.data("count");
      $(".event_lst_txt .pink").text(count + "개");
    }

    function scrollUpdate() {
      var productsCount = $(".event_tab_lst li.item[data-category=" + categoryIdx + "]").data("count");
      // 전체개수 > 페이지 * 10
      if (productsCount > page * 10) {
        var moreTop = $(".more .btn").offset().top;
        var scrollY = window.scrollY;
        var windowHeight = window.innerHeight;
        if (windowHeight + scrollY > moreTop - 100) {
          page++;
          getProducts("append");
        }
      } else {
        $(".more").addClass("hide");
      }
    }

    function ajaxErrorHandler(error) {
      console.log(error);
    }

    return {
      init: init
    };
  });
