"use strict";

var rolling = (function() {
  var $list = $('#container ul.visual_img');
	var rollingBtnClicked = false;
	var intervalVar;
	var timeoutVar;
  var rollingSpace;

	var setRollingInterval = function() {
		intervalVar = setInterval(leftRollup, 2000);
	};

	var listInit = function(){
		var $head = $list.children().last().clone();
		var $tail = $list.children().first().clone();
		$list.append($tail);
		$list.prepend($head);
		$list.css('left', '-'+rollingSpace+'px');
		setRollingInterval();
	};

	var draw = function(data) {
		var source = $('#rolling-template').html();
		var template = Handlebars.compile(source);
		var items = { items: data };
		var html = template(items);
		$list.append(html);
		listInit();
	};

	var leftRollup = function(){
		var endIdx = $list.children().length - 2;
		var curItem = $list.data('curItem');
		if(curItem == endIdx) {
			$list.css('left', '0px');
			curItem = 0;
		}
		$list.animate({
			left: '-='+rollingSpace+'px'
		});
		$list.data('curItem', curItem+1);
	};

	var rightRollup = function(){
		var startIdx = 1;
		var listSize = $list.children().length;
		var leftInitial = (listSize - 1) * -1 * rollingSpace;
		var curItem = $list.data('curItem');
		if(curItem == startIdx) {
			$list.css('left',  + leftInitial+'px');
			curItem = listSize - 1;
		}
		$list.animate({
			left: '+='+rollingSpace+'px'
		});
		$list.data('curItem', curItem-1);
	};

	return {
    getRollingSpace: function() {
      return rollingSpace;
    },
    setRollingSpace: function(space) {
      rollingSpace = space;
    },
		getRollingAjax: function(url) {
				$list.data('curItem', 1);
				$.ajax({
					url: url,
					type: 'GET'
				})
				.done(draw)
				.fail(function(error){
					console.log(error.responseJSON);
					alert('Rolling list load를 실패했습니다.');
				});
		},
		btnHandler: function(btnFlag, e) {
			//e.preventDefault();
			var rollUpFn = btnFlag === 0 ? rightRollup : leftRollup;
			if(!rollingBtnClicked) {
				rollingBtnClicked = true;
				setTimeout(function(){rollingBtnClicked=false}, 1000);
				clearInterval(intervalVar);
				clearTimeout(timeoutVar);
				rollUpFn();
				timeoutVar = setTimeout(setRollingInterval, 4000);
			}
		}
	};
})();
