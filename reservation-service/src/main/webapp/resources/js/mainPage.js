"use strict";

var mainPage = (function() {
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

	var getProductsAjax = function() {
		var categoryId = $('#container ul.event_tab_lst a.anchor.active').closest('.item').data('category');
		var apiUrl;
		categoryId == 0 ? apiUrl = '/api/products' : apiUrl = '/api/categories/' + categoryId + '/products';
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

			categoryId == 0 ? apiUrl = '/api/products' : apiUrl = '/api/categories/' + categoryId + '/products';
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
			rolling.setRollingSpace(338)
			rolling.getRollingAjax('/api/products');
			getProductsAjax();
			getCategoriesAjax();
			getProductsCountAjax();
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

mainPage.getPrevBtn().on('click', rolling.btnHandler.bind(this, 0, null));
mainPage.getNextBtn().on('click', rolling.btnHandler.bind(this, 1, null));

mainPage.getMoreBtn().on('click', mainPage.getMoreProductsAjax);

$(window).scroll(function(){
    if(!mainPage.loading && $(window).scrollTop() > $(document).height() - $(window).height() - 100){
			mainPage.getMoreProductsAjax();
	}
});
