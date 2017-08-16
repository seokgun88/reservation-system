define(["util", "egComponent"], function(Util, egComponent) {
  "use strict";

  var Ticket = Util.extend(egComponent, {
    init: function($ele) {
      this._$container = $ele;
      this._$minusBtn = this._$container.find(".ico_minus3");
      this._$plusBtn = this._$container.find(".ico_plus3");
      this.$countEle = this._$container.find(".count_control_input");
      this.$totalPriceEle = this._$container.find(".total_price");

      this.price = this._$container.data("price");
      this.count = 0;
      this.isPlus = true;

      this.initEvent();
    },
    initEvent: function() {
      this._$minusBtn.on("click", this._minus.bind(this));
      this._$plusBtn.on("click", this._plus.bind(this));
    },
    _plus: function(e) {
      e.preventDefault();
      if (this.count === 0) {
        this._$minusBtn.prop("disabled", false);
        this.$countEle.prop("disabled", false);
        this.$totalPriceEle.closest("div").addClass("on_color");
      }
      this.count++;
      this.isPlus = true;
      this.trigger("change");
    },
    _minus: function(e) {
      e.preventDefault();
      if (this.count > 0) {
        this.count--;
        this.isPlus = false;
        if (this.count === 0) {
          this._$minusBtn.prop("disabled", true);
          this.$countEle.prop("disabled", true);
          this.$totalPriceEle.closest("div").removeClass("on_color");
        }
        this.trigger("change");
      }
    },
    getCount: function() {
      return this.count;
    }
  });

  return Ticket;
});
