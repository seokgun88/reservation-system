"use strict";

function extend(superClass, def) {
    var extendClass = function extendClass() {
        // Call a parent constructor
        superClass.apply(this, arguments);

        // Call a child constructor
        if (typeof def.init === "function") {
            def.init.apply(this, arguments);
        }
    };

    var ExtProto = function() {};
    ExtProto.prototype = superClass.prototype;

    var extProto = new ExtProto();
    for (var i in def) {
        extProto[i] = def[i];
    }
    extProto.constructor = extendClass;
    extendClass.prototype = extProto;

    return extendClass;
};

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
