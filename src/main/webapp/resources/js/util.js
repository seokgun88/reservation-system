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

function ajaxRequest(url, method, data){
  return $.ajax({
    url : url,
    method : method,
    data : data,
    contentType : "application/json; charset=UTF-8"
  });
}

