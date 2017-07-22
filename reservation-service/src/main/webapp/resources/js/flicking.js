var Flicking = (function(){
  return {
      swipedetect: function($el, swipeHandler) {
        var startX,
            startY;

        var swipeStart = function(e){
            var eventObj = e.type === "mousedown" ? e : e.originalEvent.changedTouches[0];
            startX = eventObj.pageX;
            startY = eventObj.pageY;
        };

        var swipeEnd = function(e){
            var eventObj = e.type === "mouseup" ? e : e.originalEvent.changedTouches[0];
            var distX = eventObj.pageX - startX;
            var distY = eventObj.pageY - startY;
            var swipedir;
            if (Math.abs(distX) >= 50 && Math.abs(distY) <= 150){
                swipedir = (distX < 0) ? 'left' : 'right';
                swipeHandler(swipedir);
                e.preventDefault();
            }
        };

        $el.on('mousedown touchstart', swipeStart);
        $el.on('mouseup touchend', swipeEnd);

    }
  }
})();
