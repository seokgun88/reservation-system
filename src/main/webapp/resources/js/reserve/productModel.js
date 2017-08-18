define(["util", "egComponent", "../formatter", "priceType"],
  function(Util, egComponent, Formatter, PriceType) {
    "use strict";

    var Product = Util.extend(egComponent, {
      init: function(detail, prices) {
        this.detail = $.extend({}, detail);
        this.prices = $.extend({}, prices);

        this.addExValues();
        this.addExPriceValues();
      },
      addExValues: function() {
        this.detail.displayPeriod = Formatter.getPeriod(this.detail.displayStart, this.detail.displayEnd);
        this.detail.pricesDescription = $.map(this.prices, function(v, i) {
          return Formatter.getPriceDescription(PriceType[v.priceType], v.price);
        }).join(" / ");
      },
      addExPriceValues: function() {
        $.each(this.prices, function(k, v) {
          this.typeName = PriceType[v.priceType].typeName;
          this.discountedPrice = this.price * (1 - (this.discountRate / 100));

          this.localePrice = this.price.toLocaleString();
          this.localeDiscountedPrice = this.discountedPrice.toLocaleString();
        });
      },
      getDetail: function() {
        return this.detail;
      },
      getPrices: function() {
        return this.prices;
      }
    })

    return Product;
  });
