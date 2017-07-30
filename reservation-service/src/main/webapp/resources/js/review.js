var Comment = (function(){
    var scrollFlag = true;

    var $photoList = $('.photo_list');
    var $indexPhoto = $('.index_photo');
    var $photoViewer = $('#photoviwer');
    var productName= $(".review_header .title").text();

    var commentPhotoFlicking = new Flicking($photoList, {
        width: $(window).width(),
        intervalFlag: false
    });
    commentPhotoFlicking.afterFlickFn = function(){
        $indexPhoto.html(this.$list.data('curItem'));
    };
    commentPhotoFlicking.swipedetect($photoList);

    var getCommentImagesAjax = function(listInit, e){
        $.ajax({
            url: '/api/comments/' + $(e.target).closest('li').data('id') + '/images',
            type: 'GET'
        })
            .done(function(data){
                $photoList.empty();
                $photoList.data('curItem', 1);

                var html = Handlebars.templates.photoViewerTemplate(data);
                $photoList.append(html);

                $('.total_photo').html(data.length);

                $photoViewer.css({
                    width: $(document).width(),
                    height: $(document).height()
                })
                $photoViewer.fadeIn();
                listInit();
            })
            .fail(function(error){
                console.log(error.responseJSON);
                alert('Comment photos load를 실패했습니다.');
            });
    };

    var init = function (){
        requestComment();
        requestSummary();
        $(window).on("scroll", scrollViewMoreProductList);
    };

    var updateGradeArea = function(summary){
        var $gradeArea = $(".grade_area");
        $gradeArea.find(".grade_value").css("width", (summary.avgScore/5 * 100).toFixed());
        $gradeArea.find(".text_value > span").text(summary.avgScore.toFixed(1));
        $gradeArea.find(".join_count > em.green").text(summary.num + "건");
    }

    var requestComment = function(){
        var apiUrl = window.location.origin + "/api" + window.location.pathname;
        var options = {
            type: "GET",
            data: {
                size: 10
            }
        };
        var ajaxRequestComment = $.ajax(apiUrl, options);
        ajaxRequestComment.then(addComments.bind($('.list_short_review')));
        ajaxRequestComment.then(function (){
            $('.thumb').on('click', getCommentImagesAjax.bind(this, commentPhotoFlicking.listInit.bind(commentPhotoFlicking)));
            $('.btnPhotoviwerExit').on('click', function(){
                $photoViewer.fadeOut();
            });
        });
    };

    var requestSummary = function(){
        var apiUrl = window.location.origin + "/api" + window.location.pathname + "/summary";
        var options = {
            type: "GET"
        };
        var ajaxRequestSummary = $.ajax(apiUrl, options);
        ajaxRequestSummary.then(updateGradeArea);
    }

    var addComments = function(comments){
        var html = $.map(comments, function(comment){
            comment.productName = productName; // 응답으로 상품 이름도 받아오는 게 나을 듯
            var d = new Date(comment.createDate);
            var date = d.getFullYear() + '.' + (d.getMonth()+1) + '.' + d.getDate();
            comment.date = date;
            return Handlebars.templates['userCommentItem'](comment);
        }).join("");
        this.append(html);
        scrollFlag = true;
    };

    var scrollViewMoreProductList = function(){
        var docTop = Math.round($(document).scrollTop());
        var docHeight = $(document).height();
        var winHeight = $(window).height();
        if(scrollFlag && docTop === docHeight - winHeight){
            scrollFlag = false;
            requestComment();
        }
    };

    return {
        init: init
    }
})();

$(function(){
    Comment.init();
})