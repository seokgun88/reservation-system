define(["jquery", "egComponent", "util"], function($, egComponent, Util) {
  var Flicking = Util.extend(egComponent, {
    init: function(ele, options) {
      var defaultOptions = {
        width: 338,
        size: 3,
        isAuto: true,
        btnNextClass: ".btn_nxt_e",
        btnPrevClass: ".btn_pre_e"
      };
      this.options = $.extend({}, defaultOptions, options);

      this._$ele = $(ele);
      this._index = 1;
      this._slideLock = false;
      this._intervalId = 0;

      this._$ele.find(this.options.btnNextClass).on("click", this._slideHandler.bind(this, 1));
      this._$ele.find(this.options.btnPrevClass).on("click", this._slideHandler.bind(this, -1));
      this._$ele.on('mousedown touchstart', this._swipeStart.bind(this));
      this._$ele.on('mouseup touchend', this._swipeEnd.bind(this));

      this._expandList();
      this._autoSlide();
    },

    _slideHandler: function(direction, e) {
      this._slide(direction);
      clearInterval(this._intervalId);
      setTimeout(this._autoSlide(), 4000);
    },

    _slide: function(direction, e) {
      if (!this._slideLock) {
        this._slideLock = true;
        var $slidingBox = this._$ele.find("ul");
        this._index += direction;
        this._checkBoundary($slidingBox);

        $slidingBox.animate({
          left: (this.options.width * this._index * -1) + "px"
        }, function() {
          this._slideLock = false;
        }.bind(this));
      }
    },

    _checkBoundary: function($slidingBox) {
      if (this._index > this.options.size) {
        this._index = 1;
        $slidingBox.css("left", 0);
      }
      if (this._index === 0) {
        this._index = this.options.size;
        $slidingBox.css("left", (-1 * (this.options.size + 1) * this.options.width));
      }
    },

    _expandList: function() {
      var firstItem = this._$ele.find("li:first").clone();
      var lastItem = this._$ele.find("li:last").clone();
      this._$ele.find("ul").append(firstItem);
      this._$ele.find("ul").prepend(lastItem);
      this._$ele.find("ul").css("left", (this.options.width * -1) + "px");
    },

    _autoSlide: function() {
      if (this.options.isAuto) {
        this._intervalId = setInterval(this._slide.bind(this, 1), 2000);
      }
    },

    _swipeStart: function(e) {
      var eventObj = e.type === "mousedown" ? e : e.originalEvent.changedTouches[0];
      this._startX = eventObj.pageX;
      this._startY = eventObj.pageY;
    },

    _swipeEnd: function(e) {
      var eventObj = e.type === "mouseup" ? e : e.originalEvent.changedTouches[0];
      var distX = eventObj.pageX - this._startX;
      var distY = eventObj.pageY - this._startY;
      if (Math.abs(distX) >= 50 && Math.abs(distY) <= 150) {
        (distX < 0) ? this._slideHandler(1): this._slideHandler(-1);
      }
    }
  });

  return Flicking;
});
