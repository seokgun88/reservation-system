function ajaxRequest(url, method, data){
  return $.ajax({
    url : url,
    method : method,
    data : data,
    contentType : "application/json; charset=UTF-8"
  });
}
