var ProductDetail = (function(){
  var mainImageFlicking = new Flicking();
  var commentPhotoFlicking = new Flicking();

  var productId = $('#container').data('id');
  var productName;
  var productDesc;

  var $mainImageList = $('#container ul.visual_img');
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
  var $indexPhoto = $('.index_photo');

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

  var getCommentAjax = function(listInit) {
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
          listInit();
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
  };

	var drawMainImages = function(data) {
	  var mainImagesTemplate = Handlebars.compile($('#main-images-template').html());
		$mainImageList.append(mainImagesTemplate(data));
	};

  var getMainImagesAjax  = function(listInit) {
      $mainImageList.data('curItem', 1);
      $.ajax({
        url: '/api/products/' + productId + "/files?type=0",
        type: 'GET'
      })
      .done(function(data){
        drawMainImages(data);
        listInit($mainImageList);
        $('.visual_txt_tit span').eq(1).html(productName);
        $('.visual_txt_dsc').eq(1).html(productDesc);
        $('.visual_txt_tit span').eq(-1).html(productName);
        $('.visual_txt_dsc').eq(-1).html(productDesc);
        $('.num.off').html('/ <span>' + data.length + '</span>');
      })
      .fail(function(error){
        console.log(error.responseJSON);
        alert('Rolling list load를 실패했습니다.');
      });
  };

  return {
    init: function(){
      mainImageFlicking.width = 414;
      mainImageFlicking.$list = $('#container ul.visual_img');
      mainImageFlicking.intervalFlag = false;
      mainImageFlicking.afterFlickFn = function(){
        $curImgIdx.html(this.$list.data('curItem'));
      };
      mainImageFlicking.detectClick($prevBtn, "prev");
      mainImageFlicking.detectClick($nextBtn, "next");
      mainImageFlicking.swipedetect($('.group_visual'));

      commentPhotoFlicking.width = $(window).width();
      commentPhotoFlicking.$list = $('.photo_list');
      commentPhotoFlicking.intervalFlag = false;
      commentPhotoFlicking.afterFlickFn = function(){
        $indexPhoto.html(this.$list.data('curItem'));
      };
      commentPhotoFlicking.swipedetect($('.photo_list'));

      getProductDetailAjax();
      getMainImagesAjax(mainImageFlicking.listInit.bind(mainImageFlicking));
      getDetailImageAjax();
      getDisplayInfoAjax();
      getCommentsSummaryAjax();
      getCommentAjax(commentPhotoFlicking.listInit.bind(commentPhotoFlicking));

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
    }
  };
})();

$(ProductDetail.init);
