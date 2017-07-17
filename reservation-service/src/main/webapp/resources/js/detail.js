var productId = $('#container').data('id');
var productName;
var productDesc;

var $curImgIdx = $('span[class=num]');
var $prevBtn = $('.btn_prev');
var $nextBtn = $('.btn_nxt');
var $moreOpen = $('.bk_more._open');
var $moreClose = $('.bk_more._close');
var $bookingBtn = $('.bk_btn');
var $avgScore = $('.grade_area .text_value span');
var $commentsNum = $('.grade_area .join_count em');
var $detailBtn = $('.item._detail');
var $pathBtn = $('.item._path');
var $detail = $('.detail_area_wrap');
var $location = $('.detail_location');

var lazy = (function(){
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
    $avgScore.html(data.avgScore);
    $commentsNum.html(data.num + '건');
  })
  .fail(function(error){
    console.log(error.responseJSON);
    alert('Comment summary load를 실패했습니다.');
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

    lazy.init();
  })
  .fail(function(error){
    console.log(error.responseJSON);
    alert('Detail image load를 실패했습니다.');
  });
}

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
  $pathBtn.find('a.anchor').removeClass('active');
  $detail.removeClass('hide');
  $location.addClass('hide');
  e.preventDefault();
});

$pathBtn.on('click', function(e){
  $pathBtn.find('a.anchor').addClass('active');
  $detailBtn.find('a.anchor').removeClass('active');
  $detail.addClass('hide');
  $location.removeClass('hide');
  e.preventDefault();
});

var swipedetect = function(el, swipeHandler) {
    var startX;
    var startY;

    el.addEventListener('touchstart', function(e){
        var touchobj = e.changedTouches[0];
        startX = touchobj.pageX;
        startY = touchobj.pageY;
        e.preventDefault();
    }, false)

    el.addEventListener('touchmove', function(e){
        e.preventDefault();
    }, false)

    el.addEventListener('touchend', function(e){
        var touchobj = e.changedTouches[0];
        var distX = touchobj.pageX - startX;
        var distY = touchobj.pageY - startY;
        var swipedir;
        if (Math.abs(distX) >= 50 && Math.abs(distY) <= 150){
            swipedir = (distX < 0) ? 'left' : 'right';
        }
        swipeHandler(swipedir);
        e.preventDefault();
    }, false)
};

var el = document.getElementsByClassName('group_visual')[0];
swipedetect(el, function(swipedir){
    if (swipedir =='left') {
      rolling.btnHandler(1);
    }
    if (swipedir =='right') {
      rolling.btnHandler(0);
    }
});
