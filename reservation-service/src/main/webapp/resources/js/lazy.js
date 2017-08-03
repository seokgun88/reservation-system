"use strict";

define([], function(){
    var Lazy = (function(){
        var isInViewport = function(el) {
            var rect = el.getBoundingClientRect();

            return (
                rect.bottom >= 0 &&
                rect.right >= 0 &&
                rect.top <= (window.innerHeight || document.documentElement.clientHeight) &&
                rect.left <= (window.innerWidth || document.documentElement.clientWidth)
            );
        };

        var lazyLoad = function() {
            var $img = $('.detail_area_wrap .img_thumb');
            if(isInViewport($img[0])) {
                var src = $img.data('lazy-image');
                $img.attr('src', src);
            }
        };

        return {
            init: function() {
                window.addEventListener('load', lazyLoad);
                window.addEventListener('scroll', lazyLoad);
                window.addEventListener('resize', lazyLoad);
            },
            disable: function() {
                window.removeEventListener('load', lazyLoad);
                window.removeEventListener('scroll', lazyLoad);
                window.removeEventListener('resize', lazyLoad);
            }
        };
    })();
    return Lazy;
});
