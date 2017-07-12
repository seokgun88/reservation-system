"use strict";

var $promotionList = $('#container ul[class=visual_img]');
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

var drawPromotion = function(id, name, placeName, description) {
	var promotionHtml =
		'<li class="item" data-id=' + id + ' style="background-image: url(http://naverbooking.phinf.naver.net/20170119_48/1484802596907hmVDm_JPEG/image.jpg); width: 338px;">\
				<a href="/resources/html/detail.html">\
				<span class="img_btm_border"></span>\
				<span class="img_right_border"></span>\
				<span class="img_bg_gra"></span>\
				<div class="event_txt">\
					<h4 class="event_txt_tit">' + name + '</h4>\
      <p class="event_txt_adr">' + placeName + '</p>\
					<p class="event_txt_dsc">' + description + '</p>\
				</div>\
			</a>\
		</li>';
	$promotionList.append(promotionHtml);
};

var drawCategory = function(id, name) {
	$categoryList.append('<li class="item" data-category="' + id + '"><a class="anchor"> <span>' + name + '</span></a></li>');
};

var drawProduct = function(id, name, place, description) {
	var curProductList = $productList.eq(productListCnt % 2);
	productListCnt++;
	curProductList.append(
		'<li class="item">\
			<a href="/resources/html/detail.html" class="item_book">\
			<div class="item_preview">\
				<img alt="뮤지컬 드림걸즈(DREAMGIRLS) 최초 내한" class="img_thumb"\
					src="https://ssl.phinf.net/naverbooking/20170303_271/1488514705030TuUK4_JPEG/17%B5%E5%B8%B2%B0%C9%C1%EE_%B8%DE%C0%CE%C6%F7%BD%BA%C5%CD_%C3%D6%C1%BE.jpg?type=l591_945">\
				<span class="img_border"></span>\
			</div>\
			<div class="event_txt">\
				<h4 class="event_txt_tit">\
					<span>' + name + '</span>\
					<small class="sm">' + place + '</small>\
				</h4>\
				<p class="event_txt_dsc">' + description + '</p>\
			</div>\
			</a>\
		</li>'
	);
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
			$.each(data, function(index, value) {
				drawPromotion(value.id, value.name, value.placeName, value.description);
			});
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
			$.each(data, function(index, value) {
				drawProduct(value.id, value.name, value.placeName, value.description);
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
	.done(function(data) {
		$.each(data, function(index, value) {
			drawCategory(value.id, value.name);
		});
	})
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

$(document).ready(function getProductsSetPromotionRolling(){
	$promotionList.data('curPromotion', 1);
	getPromotionsAjax();
	getProductsAjax();
	getCategoriesAjax();
	getProductsCountAjax();
});

$categoryList.on('click', 'a', function(e){
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
			drawProduct(value.id, value.name, value.placeName, value.description);
		});
		if(data.length > 0){
			$moreBtn.show();
		} else {
			$moreBtn.hide();
		}
	})
	.fail(function(error){
		console.log(error.responseJSON);
		alert('Products count load를 실패했습니다.');
	});
});

var promotionBtnClicked = false;
var promotionBtnHandler = function(rollUpFn, e) {
	e.preventDefault();
	if(!promotionBtnClicked) {
		promotionBtnClicked = true;
		setTimeout(function(){promotionBtnClicked=false}, 1000);
		clearInterval(promotionIntervalVar);
		clearTimeout(promotionTimeoutVar);
		rollUpFn();
		promotionTimeoutVar = setTimeout(setPromotionInterval, 4000);
	}
};
$prevBtn.on('click', promotionBtnHandler.bind(this, rightRollup));
$nextBtn.on('click', promotionBtnHandler.bind(this, leftRollup));

var getMoreProductsAjax = function(){
	if($moreBtn.css('display') !== 'none'){
		getProductsAjax();
	}
};

$moreBtn.on('click', getMoreProductsAjax);

$(window).scroll(function(){
    if(!loading && $(window).scrollTop() > $(document).height() - $(window).height() - 100){
			getMoreProductsAjax();
	}
});
