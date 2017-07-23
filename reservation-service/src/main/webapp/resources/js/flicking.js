"use strict";

function Flicking(){
  this.width = 0;
  this.intervalFlag = true;
  this.startX = 0,
  this.startY = 0;
  this.intervalFlag = true;
}

Flicking.prototype.swipedetect = function($el, swipeHandler) {
  var swipeStart = function(e){
    var eventObj = e.type === "mousedown" ? e : e.originalEvent.changedTouches[0];
    this.startX = eventObj.pageX;
    this.startY = eventObj.pageY;
  };
  var swipeEnd = function(e){
    var eventObj = e.type === "mouseup" ? e : e.originalEvent.changedTouches[0];
    var distX = eventObj.pageX - this.startX;
    var distY = eventObj.pageY - this.startY;
    var swipedir;
    if (Math.abs(distX) >= 50 && Math.abs(distY) <= 150){
      (distX < 0) ? this.flick("next") : this.flick("prev");
      e.preventDefault();
    }
  };
  $el.on('mousedown touchstart', swipeStart.bind(this));
  $el.on('mouseup touchend', swipeEnd.bind(this));
};

Flicking.prototype.leftRollup = function(){
  var $list = this.$list;
  var endIdx = $list.children().length - 2;
  var curItem = $list.data('curItem');
  if(curItem == endIdx) {
    $list.css('left', '0px');
    curItem = 0;
  }
  $list.animate({
    left: '-='+this.width+'px'
  });
  $list.data('curItem', curItem+1);
};

Flicking.prototype.rightRollup = function(){
  var $list = this.$list;
  var startIdx = 1;
  var listSize = $list.children().length;
  var leftInitial = (listSize - 1) * -1 * this.width;
  var curItem = $list.data('curItem');
  if(curItem == startIdx) {
    $list.css('left',  + leftInitial+'px');
    curItem = listSize - 1;
  }
  $list.animate({
    left: '+='+this.width+'px'
  });
  $list.data('curItem', curItem-1);
};

Flicking.prototype.setFlickingInterval = function() {
  if(this.intervalFlag) {
    this.intervalId = setInterval(this.leftRollup.bind(this), 2000);
  }
};

Flicking.prototype.clearFlickingInterval = function(){
  clearInterval(this.intervalId);
}

Flicking.prototype.listInit = function(){
  var $list = this.$list;
  var $head = $list.children().last().clone();
  var $tail = $list.children().first().clone();
  $list.append($tail);
  $list.prepend($head);
  $list.css('left', '-'+this.width+'px');
  this.setFlickingInterval();
  $(window).focus(this.setFlickingInterval);
  $(window).blur(this.clearFlickingInterval);
};

Flicking.prototype.flick = function(type){
  var rollUpFn = type === "prev" ? this.rightRollup : this.leftRollup;
  if(!this.btnClicked) {
    this.btnClicked = true;
    setTimeout(function(){this.btnClicked=false}.bind(this), 1000);
    clearInterval(this.intervalId);
    clearTimeout(this.timeoutId);
    rollUpFn.call(this);
    this.timeoutId = setTimeout(this.setFlickingInterval.bind(this), 4000);
    if(this.afterFlickFn){
      this.afterFlickFn();
    }
  }
};

Flicking.prototype.detectClick = function($btn, type){
  $btn.on("click", this.flick.bind(this, type));
};
