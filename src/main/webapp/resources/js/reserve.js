requirejs.config({
  paths: {
    "jquery": "node_modules/jquery/dist/jquery.min",
    "Handlebars": "node_modules/handlebars/dist/handlebars.min",
    "egComponent": "node_modules/@egjs/component/dist/component.min"
  }
});

requirejs([
  "jquery", "Handlebars", "egComponent", "util", "flicking", "asyncRequest"
], function($, Handlebars, egComponent, Util, Flicking, ajaxRequest) {
  var Reserve = (function() {
    var productId = $("body").data("id");
    var PRODUCTS_API_URL = "/api/products/" + productId;

    var WEEK_DAYS = ["일", "월", "화", "수", "목", "금", "토"];

    function formatDate(inputDate) {
      return inputDate + "(" + WEEK_DAYS[new Date(inputDate).getDay()] + ")";
    }

    var priceType = {
      "1": {
        typeName: "성인",
        dsc: "(만 19~64세)"
      },
      "2": {
        typeName: "청소년",
        dsc: "(만 13~18세)"
      },
      "3": {
        typeName: "어린이",
        dsc: "(만 4~12세)"
      }
    };

    function Price(data) {
      this.type = priceType[data.priceType];
      this.price = data.price;
      this.discountRate = data.discountRate;
      this.discountedPrice = data.price * (1 - (data.discountRate / 100));
    }

    var ProductModel = function() {
      var Price = {
        discountRate: 10,
        price: 5000,
        priceType: 1,
        productId: 1
      }

      var prices = [];

      function setPrices(prices) {
        this.prices = $.extend([], price);
      }

      function setProduct() {

      }

      return {
        setPrices: setPrices
      };
    };

    var Ticket = Util.extend(egComponent, {
      init: function($ele) {
        this._$container = $ele;
        this._$minusBtn = this._$container.find(".ico_minus3");
        this._$plusBtn = this._$container.find(".ico_plus3");
        this._$countEle = this._$container.find(".count_control_input");
        this._$totalPriceEle = this._$container.find(".total_price");

        this._price = this._$container.data("price");
        this._count = 0;

        this.initEvent();
      },
      initEvent: function() {
        this._$minusBtn.on("click", this._minus.bind(this));
        this._$plusBtn.on("click", this._plus.bind(this));
      },
      _plus: function(e) {
        e.preventDefault();
        this._count++;
        this._updateCount();
      },
      _minus: function(e) {
        e.preventDefault();
        this._count--;
        this._updateCount();
      },
      _updateCount: function() {
        this._$countEle.val(this._count);
        this._$totalPriceEle.text(this._price * this._count);
      }
    });

    var productSummary = {};

    function init() {
      var combinePromise = $.when(getProductsApiData("/reservation"), getProductsApiData("/prices"));
      combinePromise.done(function(detail, price) {
        productSummary = $.extend({}, detail);
        productSummary.prices = $.extend([], price);
        var displayPeriod = formatDate(detail.displayStart) + "~" + formatDate(detail.displayEnd);
        var pricesDescription = $.map(productSummary.prices, function(val, i) {
          return priceType[val.priceType].typeName + priceType[val.priceType].dsc + " " + val.price + "원";
        }).join(" / ");
        productSummary.pricesDescription = pricesDescription;
        productSummary.displayPeriod = displayPeriod;

        $.each(productSummary.prices, function(k, v) {
          this.typeName = priceType[v.priceType].typeName;
          this.discountedPrice = this.price * (1 - (this.discountRate / 100));
        });

        drawReservationPage(".section_product_detail", "#product-detail-template", productSummary);
        drawReservationPage(".section_booking_ticket", "#booking-ticket-template", productSummary.prices);

        $(".qty").map(function(k, v) {
          new Ticket($(this));
        });
      });
    }

    function getProductsApiData(path, obj, callback) {
      var deferred = $.Deferred();
      var url = PRODUCTS_API_URL + path;

      ajaxRequest(url, "GET").then(function(data) {
        deferred.resolve(data);
      });

      return deferred.promise();
    }

    function drawReservationPage(container, templateId, data) {
      var template = Handlebars.compile($(templateId).html());
      $(container).html(template(data));
    }

    return {
      init: init
    }

  })();

  $(Reserve.init());
});
