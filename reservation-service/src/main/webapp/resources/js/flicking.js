var Flicking = (function(){
  return {
      swipedetect: function(el, swipeHandler) {
        var startX;
        var startY;

        el.addEventListener('touchstart', function(e){
            var touchobj = e.changedTouches[0];
            startX = touchobj.pageX;
            startY = touchobj.pageY;
            e.preventDefault();
        }, false)

        el.addEventListener('touchmove', function(e){
            e.preventDefault();
        }, false)

        el.addEventListener('touchend', function(e){
            var touchobj = e.changedTouches[0];
            var distX = touchobj.pageX - startX;
            var distY = touchobj.pageY - startY;
            var swipedir;
            if (Math.abs(distX) >= 50 && Math.abs(distY) <= 150){
                swipedir = (distX < 0) ? 'left' : 'right';
            }
            swipeHandler(swipedir);
            e.preventDefault();
        }, false)
    }
  }
})();
