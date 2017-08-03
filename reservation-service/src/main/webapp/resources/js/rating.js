"use strict";

define(["EgComponent", "Util"], function(EgComponent, Util){
    var Rating = Util.extend(EgComponent, {
        init: function($root, option){
            this._$root = $root;
            this.afterSetScore = option.afterSetScore;
        },
        score: function(){
            return this._$root.html();
        },
        setScore: function(score){
            this._$root.html(score);
            if(this.afterSetScore){
                this.afterSetScore();
            }
        }
    });
    return Rating;
});
