"use strict";

require.config({
    paths: {
        "jquery" : "node_modules/jquery/dist/jquery",
        "Handlebars" : "node_modules/handlebars/dist/handlebars",
        "Flicking" : "flicking"
    }
});

require([
    "jquery", "Handlebars", "Flicking"
], function($, Handlebars, Flicking){
    var ReservationMain = (function() {
        var imgUrl = "/api/images/";
        var $promotionList = $('#container ul.visual_img');
        var $categoryList = $('#container ul.event_tab_lst');
        var $productList = $('#container ul.lst_event_box');
        var $productsCnt = $('#container p.event_lst_txt span.pink');
        var $prevBtn = $('#container .btn_pre_e');
        var $nextBtn = $('#container .btn_nxt_e, #container .nxt_fix');
        var $moreBtn = $('#container .more button.btn');

        var productPage = 0;
        var productListCnt = 0;
        var loading = false;

        var drawCategories = function(data) {
            var source = $('#categories-template').html();
            var template = Handlebars.compile(source);
            var categories = { categories: data };
            var html = template(categories);
            $categoryList.append(html);
        };

        var productTemplate = Handlebars.compile($('#product-template').html());
        var drawProduct = function(data) {
            var $curProductList = $productList.eq(productListCnt % 2);
            productListCnt++;
            var product = { product: data };
            var html = productTemplate(product);
            $curProductList.append(html);
        };

        var drawPromotions = function(data) {
            var promotionsTemplate = Handlebars.compile($('#promotions-template').html());
            $promotionList.append(promotionsTemplate(data));
        };

        var getMainImageAjax = function(data, drawImg){
            $.each(data, function(index, value){
                $.ajax({
                    url: '/api/products/' + value.id + '/mainImage',
                    type: 'GET'
                })
                    .done(drawImg.bind(this, value.id))
                    .fail(function(error){
                        console.log(error.responseJSON);
                        alert('Product main image load를 실패했습니다.');
                    });
            });
        };

        var getPromotionsAjax = function(listInit){
            $promotionList.data('curItem', 1);
            $.ajax({
                url: '/api/products',
                type: 'GET'
            })
                .done(function(data){
                    drawPromotions(data);
                    listInit($promotionList);
                    getMainImageAjax(data, function(id, imgId) {
                        if(imgId !== -1){
                            $('.visual_img li[data-id=' + id +']').css('background-image', 'url(' + imgUrl+ imgId + ')');
                        }
                    });
                })
                .fail(function(error){
                    console.log(error.responseJSON);
                    alert('Promotion list load를 실패했습니다.');
                });
        };

        var getProductsAjax = function() {
            var categoryId = $('#container ul.event_tab_lst a.anchor.active').closest('.item').data('category');
            var apiUrl;
            categoryId == 0 ? apiUrl = '/api/products' : apiUrl = '/api/categories/' + categoryId + '/products';
            apiUrl = apiUrl + '?page=' + (productPage + 1);
            loading = true;
            $.ajax({
                url: apiUrl,
                type: 'GET'
            })
                .done(function(data) {
                    if(data.length > 0){
                        productPage++;
                        $.each(data, function(index, value){
                            drawProduct(value);
                        });
                        getMainImageAjax(data, function(id, imgId){
                            if(imgId !== -1){
                                $('.lst_event_box li[data-id=' + id + '] img').attr('src', imgUrl + imgId);
                            }
                        });
                    } else {
                        $moreBtn.hide();
                    }
                    loading = false;
                })
                .fail(function(error){
                    console.log(error.responseJSON);
                    alert('Product list load를 실패했습니다.');
                });
        };

        var getCategoriesAjax = function() {
            $.ajax({
                url: '/api/categories',
                type: 'GET'
            })
                .done(drawCategories)
                .fail(function(error){
                    console.log(error.responseJSON);
                    alert('Category list load를 실패했습니다.');
                });
        };

        var drawProductsCnt = function(cnt) {
            $productsCnt.text(cnt + '개');
        };

        var getProductsCountAjax = function() {
            var categoryId = $('#container ul.event_tab_lst a.anchor.active').closest('.item').data('category');
            var apiUrl;
            categoryId == 0 ? apiUrl = '/api/products/count' : apiUrl = '/api/categories/' + categoryId + '/products/count';

            $.ajax({
                url: apiUrl,
                type: 'GET'
            })
                .done(function(data) {
                    drawProductsCnt(data);
                })
                .fail(function(error){
                    console.log(error.responseJSON);
                    alert('Products count load를 실패했습니다.');
                });
        };

        var categoryClickListener = function(e) {
            e.preventDefault();

            var $a = $(e.target).closest('a.anchor');
            var $li = $(e.target).closest('li.item');
            var categoryId = $li.data('category');
            var apiUrl;

            categoryId == 0 ? apiUrl = '/api/products' : apiUrl = '/api/categories/' + categoryId + '/products';
            apiUrl = apiUrl + '?page=1';

            $.ajax({
                url: apiUrl,
                type: 'GET'
            })
                .done(function(data) {
                    $categoryList.find('a.anchor').removeClass('active');
                    $a.addClass('active');

                    getProductsCountAjax();
                    $productList.empty();
                    productListCnt = 0;
                    productPage = 1;
                    $.each(data, function(index, value){
                        drawProduct(value);
                    });
                    getMainImageAjax(data, function(id, imgId){
                        if(imgId !== -1){
                            $('.lst_event_box li[data-id=' + id + '] img').attr('src', imgUrl + imgId);
                        }
                    });
                    if(data.length > 0){
                        $moreBtn.show();
                    } else {
                        $moreBtn.hide();
                    }
                })
                .fail(function(error){
                    console.log(error.responseJSON);
                    alert('Products list load를 실패했습니다.');
                });
        };

        var getMoreProductsAjax = function(){
            if($moreBtn.css('display') !== 'none'){
                getProductsAjax();
            }
        };

        return {
            init: function() {
                var promotionFlicking = new Flicking($('#container ul.visual_img'), {
                    width: 338
                });

                getPromotionsAjax(promotionFlicking.listInit.bind(promotionFlicking));
                getProductsAjax();
                getCategoriesAjax();
                getProductsCountAjax();

                promotionFlicking.detectClick($prevBtn, "prev");
                promotionFlicking.detectClick($nextBtn, "next");
                promotionFlicking.swipedetect($('.group_visual'));

                $categoryList.on('click', 'a', categoryClickListener);
                $moreBtn.on('click', getMoreProductsAjax);

                $(window).scroll(function(){
                    if(!loading && $(window).scrollTop() > $(document).height() - $(window).height() - 100){
                        getMoreProductsAjax();
                    }
                });
            }
        };
    })();

    $(ReservationMain.init);
});
