var productId = $('#container').data('id');

var getProductDetailAjax = function() {
  $.ajax({
    url: '/api/products/' + productId + '/detail',
    type: 'GET'
  })
  .done(function(data){
		var source = $('#description-template').html();
		var template = Handlebars.compile(source);
		var html = template(data);
    $('.section_store_details').append(html);

    var source = $('#event-template').html();
    var template = Handlebars.compile(source);
    var html = template(data);
    $('.event_info').append(html);

    console.log(JSON.stringify(data));
  })
  .fail(function(error){
    console.log(error.responseJSON);
    alert('Product detail load를 실패했습니다.');
  });
};

$(document).ready(function(){
  rolling.setRollingSpace(414);
  rolling.getRollingAjax('/api/products');
  getProductDetailAjax();
});

$('.btn_prev').on('click', rolling.btnHandler.bind(this, 0));

$('.btn_nxt').on('click', rolling.btnHandler.bind(this, 1));

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
}

var el = document.getElementsByClassName('group_visual')[0];
swipedetect(el, function(swipedir){
    if (swipedir =='left') {
      rolling.btnHandler(1);
    }
    if (swipedir =='right') {
      rolling.btnHandler(0);
    }
});
