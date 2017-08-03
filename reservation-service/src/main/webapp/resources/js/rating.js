"use strict";

var Rating = extend(eg.Component, {
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
