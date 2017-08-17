define(["jquery"], function($) {
  "use strict";
  
  function ajaxRequest(url, method, data, options) {
    var _options = {
      url: url,
      method: method,
      data: data,
      contentType: "application/json; charset=UTF-8"
    };
    $.extend(_options, options);
    return $.ajax(_options);
  }

  return ajaxRequest;
});
