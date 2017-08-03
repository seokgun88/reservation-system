"use strict";

require.config({
    paths: {
        "jquery" : "node_modules/jquery/dist/jquery",
        "Handlebars" : "node_modules/handlebars/dist/handlebars",
        "Flicking" : "flicking",
        "Lazy" : "lazy"
    }
});

require([
    "jquery", "Handlebars", "Flicking", "Lazy"
], function($, Handlebars, Flicking, Lazy){

    const CURRENT_URL = window.location.href;

    var productName;
    var productDesc;

    var ProductDetail = (function(){
        var productId = $('#container').data('id');

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
        var $detailBtn = $('.item._detail');
        var $pathViewBtn = $('.item._path');
        var $detail = $('.detail_area_wrap');
        var $location = $('.detail_location');
        var $displayInfo = $('.box_store_info');
        var $map = $('.store_location');
        var $pathBtn = $('.btn_path');

        var mainImageFlicking = new Flicking($mainImageList, {
            width: 414,
            intervalFlag: false,
            afterFlickFn: function(){
                $curImgIdx.html(this.$list.data('curItem'));
            }
        });
        mainImageFlicking.detectClick($prevBtn, "prev");
        mainImageFlicking.detectClick($nextBtn, "next");
        mainImageFlicking.swipedetect($('.group_visual'));

        var getProductDetailAjax = function(afterAjaxFn) {
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

                    afterAjaxFn();
                })
                .fail(function(error){
                    console.log(error.responseJSON);
                    alert('Product detail load를 실패했습니다.');
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
                    e.preventDefault();
                    url = 'http://map.naver.com/index.nhn?enc=utf8&level=2&lng='+ point.x +'&lat='+ point.y
                        +'&pinTitle=' + $('.store_addr.addr_detail').html() +'&pinType=SITE';
                    window.open(url);
                });

                var pathUrl = 'http://map.naver.com/?&enc=utf8&dtPathType=0&menu=route&mapMode=0&pathType=1'
                    +'&elng=' + point.x
                    +'&elat=' + point.y
                    +'&eText=' + $('.store_addr.addr_detail').html();
                $pathBtn.on('click', function(e){
                    e.preventDefault();
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
                    $displayInfo.append(html);
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
                url: '/api/products/' + productId + "/images?type=0",
                type: 'GET'
            })
                .done(function(data){
                    drawMainImages(data);
                    listInit($mainImageList);
                    var $mainImageTitle = $('.visual_txt_tit span');
                    var $mainImageDesc = $('.visual_txt_dsc');
                    var $mainImageNum = $('.num.off');
                    $mainImageTitle.eq(1).html(productName);
                    $mainImageDesc.eq(1).html(productDesc);
                    $mainImageTitle.eq(-1).html(productName);
                    $mainImageDesc.eq(-1).html(productDesc);
                    $mainImageNum.html('/ <span>' + data.length + '</span>');
                })
                .fail(function(error){
                    console.log(error.responseJSON);
                    alert('Main image list load를 실패했습니다.');
                });
        };

        var moreToggle = function(e){
            e.preventDefault();
            $('.store_details').toggleClass("close3");
            $moreOpen.toggle();
            $moreClose.toggle();
        };

        var switchTabToDetail = function(e){
            $detailBtn.find('a.anchor').addClass('active');
            $pathViewBtn.find('a.anchor').removeClass('active');
            $detail.removeClass('hide');
            $location.addClass('hide');
            e.preventDefault();
        };

        var switchTabToPathView = function(e){
            $pathViewBtn.find('a.anchor').addClass('active');
            $detailBtn.find('a.anchor').removeClass('active');
            $detail.addClass('hide');
            $location.removeClass('hide');
            e.preventDefault();
        };

        var setIsReservableBtn = function(e){
            $.ajax({
                url: '/api/products/' + productId + '/detail',
                type: 'GET'
            })
                .done(function(data){
                    console.log(JSON.stringify(data));
                    console.log(Date.now());
                    if(data.salesEnd < Date.now()){
                        alert('판매기간 종료');
                    } else if(data.salesFlag === 0){
                        alert('매진');
                    } else {
                        location.href = "/products/" + productId + "/reservation";
                    }
                })
                .fail(function(error){
                    console.log(error.responseJSON);
                    alert('Product detail load를 실패했습니다.');
                });
        };

        return {
            init: function(){
                getProductDetailAjax(getMainImagesAjax.bind(this, mainImageFlicking.listInit.bind(mainImageFlicking)));
                getDetailImageAjax();
                getDisplayInfoAjax();

                $moreOpen.on("click", moreToggle);
                $moreClose.on("click", moreToggle);
                $bookingBtn.on("click", setIsReservableBtn);
                $detailBtn.on('click', switchTabToDetail);
                $pathViewBtn.on('click', switchTabToPathView);
            }
        };
    })();

    var Comment = (function(){
        var productId = $('#container').data('id');

        var $avgScore = $('.grade_area .text_value span');
        var $commentsNum = $('.grade_area .join_count em');
        var $photoViewer = $('#photoviwer');
        var $photoList = $('.photo_list');
        var $indexPhoto = $('.index_photo');

        var commentPhotoFlicking = new Flicking($photoList,{
            width: $(window).width(),
            intervalFlag: false,
            afterFlickFn: function(){
                $indexPhoto.html(this.$list.data('curItem'));
            }
        });
        commentPhotoFlicking.swipedetect($photoList);

        var init = function (){
            getCommentsSummaryAjax();
            getCommentsAjax(commentPhotoFlicking.listInit.bind(commentPhotoFlicking));
            $('.btn_review_more').on("click", goMoreCommentsView);
        }

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

        var getCommentImagesAjax = function(listInit, e){
            $.ajax({
                url: '/api/comments/' + $(e.target).closest('li').data('id') + '/images',
                type: 'GET'
            })
                .done(function(data){
                    $photoList.empty();
                    $photoList.data('curItem', 1);

                    var source = $('#photo-viewer-template').html();
                    var template = Handlebars.compile(source);
                    var html = template(data);
                    $photoList.append(html);

                    $('.total_photo').html(data.length);

                    $photoViewer.css({
                        width: $(document).width(),
                        height: $(document).height()
                    })
                    $photoViewer.fadeIn();
                    Lazy.disable();
                    listInit();
                })
                .fail(function(error){
                    console.log(error.responseJSON);
                    alert('Comment photos load를 실패했습니다.');
                });
        };

        var getCommentsAjax = function(listInit) {
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
                    $('.thumb').on('click', getCommentImagesAjax.bind(this, listInit));
                    $('.btnPhotoviwerExit').on('click', function(){
                        $photoViewer.fadeOut();
                        Lazy.init();
                    });
                })
                .fail(function(error){
                    console.log(error.responseJSON);
                    alert('Comments load를 실패했습니다.');
                });
        };

        var goMoreCommentsView = function(evt){
            evt.preventDefault();
            window.location.href = CURRENT_URL + "/comments";
        };

        return {
            init: init
        }
    })();

    $(function(){
        ProductDetail.init();
        Comment.init();
    });
});
