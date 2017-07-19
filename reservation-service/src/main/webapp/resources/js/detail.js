var productId = $('#container').data('id');
var productName;
var productDesc;

var $curImgIdx = $('span[class=num]');
var $prevBtn = $('.btn_prev');
var $nextBtn = $('.btn_nxt');
var $gotoHomeBtn = $('.btn_goto_home');
var $gotoTelBtn = $('.btn_goto_tel');
var $gotoMailBtn = $('.btn_goto_mail');
var $gotoPathBtn = $('.btn_goto_path');
var $moreOpen = $('.bk_more._open');
var $moreClose = $('.bk_more._close');
var $bookingBtn = $('.bk_btn');
var $avgScore = $('.grade_area .text_value span');
var $commentsNum = $('.grade_area .join_count em');
var $detailBtn = $('.item._detail');
var $pathViewBtn = $('.item._path');
var $detail = $('.detail_area_wrap');
var $location = $('.detail_location');
var $map = $('.store_location');
var $pathBtn = $('.btn_path');

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

var getProductDetailAjax = function() {
  $.ajax({
    url: '/api/products/' + productId + '/detail',
    type: 'GET'
  })
  .done(function(data){
    productName = data.name;
    productDesc = data.description;

		var source = $('#description-template').html();
		var template = Handlebars.compile(source);
		var html = template(data);
    $('.store_details.close3').append(html);

    var source = $('#event-template').html();
    var template = Handlebars.compile(source);
    var html = template(data);
    $('.event_info').append(html);

		var source = $('#content-template').html();
		var template = Handlebars.compile(source);
		var html = template(data);
    $('.detail_info_group').append(html);
  })
  .fail(function(error){
    console.log(error.responseJSON);
    alert('Product detail load를 실패했습니다.');
  });
};

var getCommentsSummaryAjax = function() {
  $.ajax({
    url: '/api/products/' + productId + '/comments/summary',
    type: 'GET'
  })
  .done(function(data){
    $avgScore.html(data.avgScore.toFixed(1));
    $commentsNum.html(data.num + '건');
    if(data.num > 3) {
      $('.btn_review_more').show();
    }
    var starPercentage = data.avgScore / 5 * 100;
    $('.graph_value').css('width', starPercentage+'%');
  })
  .fail(function(error){
    console.log(error.responseJSON);
    //alert('Comment summary load를 실패했습니다.');
  });
};

var getCommentAjax = function() {
  $.ajax({
    url: '/api/products/' + productId + '/comments',
    type: 'GET'
  })
  .done(function(data){
    $.each(data, function(index, value){
      value.productName = productName;
      var d = new Date(value.createDate);
      var date = d.getFullYear() + '.' + (d.getMonth()+1) + '.' + d.getDate();
      value.date = date;
      var source = $('#comment-template').html();
      var template = Handlebars.compile(source);
      var html = template(value);
      $('.list_short_review').append(html);
    });
    $('.thumb').on('click', function(){
      $.ajax({
        url: '/api/comments/' + $(this).closest('li').data('id') + '/images',
        type: 'GET'
      })
      .done(function(data){
        $('.photo_list').empty();
        $('.photo_list').data('curItem', 1);

        var source = $('#photo-viewer-template').html();
        var template = Handlebars.compile(source);
        var html = template(data);
        $('.photo_list').append(html);

        console.log(data);
        $('.total_photo').html(data.length);

        var $el = $('#photoviwer');
        var $elWidth = ~~($el.outerWidth()),
            $elHeight = ~~($el.outerHeight()),
            docWidth = $(document).width(),
            docHeight = $(document).height();

        // 화면의 중앙에 레이어를 띄운다.
        if ($elHeight < docHeight || $elWidth < docWidth) {
          $el.css({
            width: docWidth,
            height: docHeight
          })
        } else {
            $el.css({top: 0, left: 0});
        }
        $el.fadeIn();
        Lazy.disable();
      })
      .fail(function(error){
        console.log(error.responseJSON);
        alert('Comment photos load를 실패했습니다.');
      });
    });
    $('.btnPhotoviwerExit').on('click', function(){
      var $el = $('#photoviwer');
      $el.fadeOut();
      Lazy.init();
    });
  })
  .fail(function(error){
    console.log(error.responseJSON);
    alert('Comments load를 실패했습니다.');
  });
};

var getDetailImageAjax = function() {
  $.ajax({
    url: '/api/products/' + productId + '/subImage',
    type: 'GET'
  })
  .done(function(data){
		var source = $('#detail-image-template').html();
		var template = Handlebars.compile(source);
		var html = template(data);
    $('.detail_info_group').append(html);

    Lazy.init();
  })
  .fail(function(error){
    console.log(error.responseJSON);
    //alert('Detail image load를 실패했습니다.');
  });
}

var initMap = function (address) {
  naver.maps.Service.geocode({
    address: address
  }, function(status, response) {
    if (status === naver.maps.Service.Status.ERROR) {
      //return alert('Something Wrong!');
    }
    var item = response.result.items[0],
      point = new naver.maps.Point(item.point.x, item.point.y);

    var map = new naver.maps.Map('map', {
      center: new naver.maps.LatLng(point.y, point.x),
      zoom: 10
    });
    console.log(map.getCenter());
    var marker = new naver.maps.Marker({
      position: new naver.maps.LatLng(point.y, point.x),
      map: map
    });

    $map.on('click', function(e){
      url = 'http://map.naver.com/index.nhn?enc=utf8&level=2&lng='+ point.x +'&lat='+ point.y
      +'&pinTitle=' + $('.store_addr.addr_detail').html() +'&pinType=SITE';
      window.open(url);
    });

    var pathUrl = 'http://map.naver.com/?&enc=utf8&dtPathType=0&menu=route&mapMode=0&pathType=1'
      +'&elng=' + point.x
      +'&elat=' + point.y
      +'&eText=' + $('.store_addr.addr_detail').html();
    $pathBtn.on('click', function(e){
      url = pathUrl;
      window.open(url);
    });
    $gotoPathBtn.attr('href', pathUrl);
  });
};

var getDisplayInfoAjax = function() {
  $.ajax({
    url: '/api/products/' + productId + '/displayInfo',
    type: 'GET'
  })
  .done(function(data){
    data.productName = productName;
		var source = $('#location-template').html();
		var template = Handlebars.compile(source);
		var html = template(data);
    $('.box_store_info').append(html);
    initMap(data.placeStreet);
    $gotoHomeBtn.attr('href', data.homepage);
    $gotoTelBtn.attr('href', 'tel:' + data.tel);
    $gotoMailBtn.attr('href', 'mailto:' + data.email);
  })
  .fail(function(error){
    console.log(error.responseJSON);
    alert('Display info load를 실패했습니다.');
  });
}

$(document).ready(function(){
  getProductDetailAjax();
  rolling.setRollingSpace(414);
  rolling.setIntervalFlag(false);
  rolling.getRollingAjax('/api/products/' + productId + "/files?type=0", function(data){
    $('.visual_txt_tit span').eq(1).html(productName);
    $('.visual_txt_dsc').eq(1).html(productDesc);
    $('.visual_txt_tit span').eq(-1).html(productName);
    $('.visual_txt_dsc').eq(-1).html(productDesc);
    $('.num.off').html('/ <span>' + data.length + '</span>');
  });
  getDetailImageAjax();
  getDisplayInfoAjax();
  getCommentsSummaryAjax();
  getCommentAjax();
});

$prevBtn.on('click', rolling.btnHandler.bind(this, 0, function(){
  $curImgIdx.html($('#container ul.visual_img').data('curItem'));
}));

$nextBtn.on('click', rolling.btnHandler.bind(this, 1, function(){
  $curImgIdx.html($('#container ul.visual_img').data('curItem'));
}));

$moreOpen.on('click', function(e){
  e.preventDefault();
  $('.store_details').addClass('close3');
  $moreOpen.hide();
  $moreClose.show();
});

$moreClose.on('click', function(e){
  e.preventDefault();
  $('.store_details').removeClass('close3');
  $moreClose.hide();
  $moreOpen.show();
});

$('.bk_btn').on('click', function(e){
  $.ajax({
    url: '/api/products/' + productId + '/detail',
    type: 'GET'
  })
  .done(function(data){
    if(data.salesEnd < Date.now()){
      alert('판매기간 종료');
    } else if(data.salesFlag === 0){
      alert('매진');
    }
    console.log(JSON.stringify(data));
    console.log(Date.now());
  })
  .fail(function(error){
    console.log(error.responseJSON);
    alert('Product detail load를 실패했습니다.');
  });
});

$detailBtn.on('click', function(e){
  $detailBtn.find('a.anchor').addClass('active');
  $pathViewBtn.find('a.anchor').removeClass('active');
  $detail.removeClass('hide');
  $location.addClass('hide');
  e.preventDefault();
});

$pathViewBtn.on('click', function(e){
  $pathViewBtn.find('a.anchor').addClass('active');
  $detailBtn.find('a.anchor').removeClass('active');
  $detail.addClass('hide');
  $location.removeClass('hide');
  e.preventDefault();
});

var el = document.getElementsByClassName('group_visual')[0];
Flicking.swipedetect(el, function(swipedir){
    if (swipedir =='left') {
      rolling.btnHandler(1);
    }
    if (swipedir =='right') {
      rolling.btnHandler(0);
    }
});

Flicking.swipedetect($('.photo_list')[0], function(swipedir){
    if (swipedir =='left') {
      var curItem = $('.photo_list').data('curItem');
      if(curItem < $('.total_photo').html()){
        $('.photo_list').data('curItem', curItem+1);
        $('.index_photo').html(curItem+1);
        $('.photo_list').animate({
          left: '-=424px'
        });
      }
    }
    if (swipedir =='right') {
      var curItem = $('.photo_list').data('curItem');
      if(curItem > 1){
        $('.photo_list').data('curItem', curItem-1);
        $('.index_photo').html(curItem-1);
        $('.photo_list').animate({
          left: '+=424px'
        });
      }
    }
});