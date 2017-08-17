define(["jquery"], function($) {

  function initNaverMap(data) {
    naver.maps.Service.geocode({
        address: data.placeStreet
      },
      function(status, response) {
        if (status === naver.maps.Service.Status.ERROR) {
          console.log("Naver geocode error");
          return;
        }

        var item = response.result.items[0],
          point = new naver.maps.Point(item.point.x, item.point.y);

        drawNaverMap(point);
        addNaverMapButtonEventHandlers(point, data.placeName);
      });
  }

  function drawNaverMap(point) {
    var map = new naver.maps.Map('map', {
      center: new naver.maps.LatLng(point.y, point.x),
      zoom: 10,
      draggable: false
    });
    var marker = new naver.maps.Marker({
      position: new naver.maps.LatLng(point.y, point.x),
      map: map
    });
  }

  function addNaverMapButtonEventHandlers(point, placeName) {
    $("#map").on('click', function(e) {
      e.preventDefault();
      url = 'http://map.naver.com/index.nhn?enc=utf8&level=2&lng=' + point.x + '&lat=' + point.y +
        '&pinTitle=' + placeName + '&pinType=SITE';
      window.open(url);
    });

    var pathUrl = 'http://map.naver.com/?&enc=utf8&dtPathType=0&menu=route&mapMode=0&pathType=1' +
      '&elng=' + point.x + '&elat=' + point.y + '&eText=' + placeName;

    $(".btn_path").attr('href', pathUrl);
    $(".btn_goto_path").attr('href', pathUrl);
  }

  return {
    init: initNaverMap
  };
});
