define(["./productModel", "asyncRequest", "Handlebars"],
  function(ProductModel, ajaxRequest, Handlebars) {
    "use strict";

    var productId = $("body").data("id");
    var PRODUCTS_API_URL = "/api/products/" + productId;

    var product = null;
    var templates = [];

    function init() {
      return get().then(setPage);
    }

    function get() {
      return $.when(ajaxRequest(PRODUCTS_API_URL + "/reservation", "GET"),
        ajaxRequest(PRODUCTS_API_URL + "/prices", "GET"));
    }

    function setPage(productReservationAjaxResult, priceAjaxResult) {
      var productDetail = productReservationAjaxResult[0];
      var productPrice = priceAjaxResult[0];

      product = new ProductModel(productDetail, productPrice);

      drawReservationPage(".section_product_detail", "#product-detail-template", product.getDetail());
      drawReservationPage(".section_booking_ticket", "#booking-ticket-template", product.getPrices());
      drawReservationPage(".inline_form.last  .inline_control", "#booking-form-template", product.getDetail());
    }

    function drawReservationPage(container, templateId, data) {
      if (!templates[templateId]) {
        templates[templateId] = Handlebars.compile($(templateId).html());
      }
      $(container).html(templates[templateId](data));
    }

    return {
      init: init
    };
  });
