define(["productModel", "asyncRequest", "Handlebars", "../handlebarsWrapper"],
  function(ProductModel, ajaxRequest, Handlebars, HandlebarsWrapper) {
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

      HandlebarsWrapper("#product-detail-template", ".section_product_detail", product.getDetail());
      HandlebarsWrapper("#booking-ticket-template", ".section_booking_ticket", product.getPrices());
      HandlebarsWrapper("#booking-form-template", ".inline_form.last  .inline_control", product.getDetail());
    }

    function getProduct() {
      return product;
    }

    return {
      init: init,
      getProduct: getProduct
    };
  });
