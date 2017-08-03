"use strict";

define([], function(){
    function Flicking($list, options){
        var defaultOptions = {
            width : 0,
            intervalFlag : true,
            startX : 0,
            startY : 0
        }
        this.$list = $list;
        this.options = $.extend({}, defaultOptions, options);

        this.afterFlickFn = this.options.afterFlickFn;

        //getter, setter
        this.getWidth = function(){
            return this.options.width;
        }
        this.getStartX = function(){
            return this.options.startX;
        }
        this.setStartX = function(startX){
            this.options.startX = startX;
        }
        this.getStartY = function(){
            return this.options.startY;
        }
        this.setStartY = function(startY){
            this.options.startY = startY;
        }
        this.getIntervalFlag = function(){
            return this.options.intervalFlag;
        }
    }
    Flicking.prototype.swipedetect = function($el) {
        var swipeStart = function(e){
            var eventObj = e.type === "mousedown" ? e : e.originalEvent.changedTouches[0];
            this.setStartX(eventObj.pageX);
            this.setStartY(eventObj.pageY);
        };
        var swipeEnd = function(e){
            var eventObj = e.type === "mouseup" ? e : e.originalEvent.changedTouches[0];
            var distX = eventObj.pageX - this.getStartX();
            var distY = eventObj.pageY - this.getStartY();
            if (Math.abs(distX) >= 50 && Math.abs(distY) <= 150){
                (distX < 0) ? this.flick("next") : this.flick("prev");
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
            left: '-='+this.getWidth()+'px'
        });
        $list.data('curItem', curItem+1);
    };

    Flicking.prototype.rightRollup = function(){
        var $list = this.$list;
        var startIdx = 1;
        var listSize = $list.children().length;
        var leftInitial = (listSize - 1) * -1 * this.getWidth();
        var curItem = $list.data('curItem');
        if(curItem == startIdx) {
            $list.css('left',  + leftInitial+'px');
            curItem = listSize - 1;
        }
        $list.animate({
            left: '+='+this.getWidth()+'px'
        });
        $list.data('curItem', curItem-1);
    };

    Flicking.prototype.setFlickingInterval = function() {
        if(this.getIntervalFlag()) {
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
        $list.css('left', '-'+this.getWidth()+'px');
        this.setFlickingInterval();
        $(window).focus(this.setFlickingInterval.bind(this));
        $(window).blur(this.clearFlickingInterval.bind(this));
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

    return Flicking;
});
