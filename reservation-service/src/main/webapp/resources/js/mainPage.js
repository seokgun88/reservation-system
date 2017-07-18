"use strict";

var mainPage = (function() {
	var $promotionList = $('#container ul.visual_img');
	var $categoryList = $('#container ul.event_tab_lst');
	var $productList = $('#container ul.lst_event_box');
	var $productsCnt = $('#container p.event_lst_txt span.pink');
	var $prevBtn = $('#container .btn_pre_e');
	var $nextBtn = $('#container .btn_nxt_e, #container .nxt_fix');
	var $moreBtn = $('#container .more button.btn');

	var promotionIntervalVar;
	var promotionTimeoutVar;

	var productPage = 0;
	var productListCnt = 0;
	var loading = false;

	var promotionListInit = function(){
		var $head = $promotionList.children().last().clone();
		var $tail = $promotionList.children().first().clone();
		$promotionList.append($tail);
		$promotionList.prepend($head);
		$promotionList.css('left', '-338px');
	};

	var drawPromotions = function(data) {
		var source = $('#promotions-template').html();
		var template = Handlebars.compile(source);
		var promotions = { promotions: data };
		var html = template(promotions);
		$promotionList.append(html);
	};

	var drawCategories = function(data) {
		var source = $('#categories-template').html();
		var template = Handlebars.compile(source);
		var categories = { categories: data };
		var html = template(categories);
		$categoryList.append(html);
	};

	var drawProduct = function(data) {
		var $curProductList = $productList.eq(productListCnt % 2);
		productListCnt++;

		var source = $('#product-template').html();
		var template = Handlebars.compile(source);
		var product = { product: data };
		var html = template(product);
		$curProductList.append(html);
	};

	var drawProductsCnt = function(cnt) {
		$productsCnt.text(cnt + '개');
	};

	var leftRollup = function(){
		var endIdx = $promotionList.children().length - 2;
		var curPromotion = $promotionList.data('curPromotion');
		if(curPromotion == endIdx) {
			$promotionList.css('left', '0px');
			curPromotion = 0;
		}
		$promotionList.animate({
			left: '-=338px'
		});
		$promotionList.data('curPromotion', curPromotion+1);
	};

	var rightRollup = function(){
		var startIdx = 1;
		var promotionListSize = $promotionList.children().length;
		var leftInitial = (promotionListSize - 1) * -338;
		var curPromotion = $promotionList.data('curPromotion');
		if(curPromotion == startIdx) {
			$promotionList.css('left',  + leftInitial+'px');
			curPromotion = promotionListSize - 1;
		}
		$promotionList.animate({
			left: '+=338px'
		});
		$promotionList.data('curPromotion', curPromotion-1);
	};

	var setPromotionInterval = function() {
		promotionIntervalVar = setInterval(leftRollup, 2000);
	};

	var getPromotionsAjax = function() {
			$.ajax({
				url: '/api/products',
				type: 'GET'
			})
			.done(function(data) {
				drawPromotions(data);
				promotionListInit();
				setPromotionInterval();
			})
			.fail(function(error){
				console.log(error.responseJSON);
				alert('Promotion list load를 실패했습니다.');
			});
	};

	var getProductsAjax = function() {
		var categoryId = $('#container ul.event_tab_lst a.anchor.active').closest('.item').data('category');
		var apiUrl;
		categoryId == 0 ? apiUrl = '/api/products' : apiUrl = '/api/products/categories/' + categoryId;
		apiUrl = apiUrl + '/pages/' + (productPage + 1);
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

	var getProductsCountAjax = function() {
		var categoryId = $('#container ul.event_tab_lst a.anchor.active').closest('.item').data('category');
		var apiUrl;
		categoryId == 0 ? apiUrl = '/api/products/count' : apiUrl = '/api/products/categories/' + categoryId + '/count';

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

	var promotionBtnClicked = false;

	return {
		getCategoryList: function() {
			return $categoryList;
		},
		getPrevBtn: function() {
			return $prevBtn;
		},
		getNextBtn: function() {
			return $nextBtn;
		},
		getMoreBtn: function() {
			return $moreBtn;
		},
		getLoading: function() {
			return loading;
		},
		categoryClickListener: function(e) {
			e.preventDefault();

			var $a = $(e.target).closest('a.anchor');
			var $li = $(e.target).closest('li.item');
			var categoryId = $li.data('category');
			var apiUrl;

			categoryId == 0 ? apiUrl = '/api/products' : apiUrl = '/api/products/categories/' + categoryId;
			apiUrl = apiUrl + '/pages/1';

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
		},
		documentInit: function() {
			$promotionList.data('curPromotion', 1);
			getPromotionsAjax();
			getProductsAjax();
			getCategoriesAjax();
			getProductsCountAjax();
		},
		leftRollup,
		rightRollup,
		promotionBtnHandler: function(rollUpFn, e) {
			e.preventDefault();
			if(!promotionBtnClicked) {
				promotionBtnClicked = true;
				setTimeout(function(){promotionBtnClicked=false}, 1000);
				clearInterval(promotionIntervalVar);
				clearTimeout(promotionTimeoutVar);
				rollUpFn();
				promotionTimeoutVar = setTimeout(setPromotionInterval, 4000);
			}
		},
		getMoreProductsAjax: function(){
			if($moreBtn.css('display') !== 'none'){
				getProductsAjax();
			}
		}
	};
})();

$(document).ready(mainPage.documentInit);

mainPage.getCategoryList().on('click', 'a', mainPage.categoryClickListener);

mainPage.getPrevBtn().on('click', mainPage.promotionBtnHandler.bind(this, mainPage.rightRollup));
mainPage.getNextBtn().on('click', mainPage.promotionBtnHandler.bind(this, mainPage.leftRollup));

mainPage.getMoreBtn().on('click', mainPage.getMoreProductsAjax);

$(window).scroll(function(){
    if(!mainPage.loading && $(window).scrollTop() > $(document).height() - $(window).height() - 100){
			mainPage.getMoreProductsAjax();
	}
});
