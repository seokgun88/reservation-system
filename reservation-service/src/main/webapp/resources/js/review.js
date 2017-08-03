"use strict";

$(function(){
    Comment.init();
})

var Comment = (function(){
    var scrollFlag = true;

    var $photoList = $('.photo_list');
    var $indexPhoto = $('.index_photo');
    var $photoViewer = $('#photoviwer');
    var productName= $(".review_header .title").text();

    var Page = function(){
        this.page = 1;
        this.limit = 10;

        this.getPage = function(){
            return this.page;
        }

        this.pageUp = function(){
            this.page++;
        }

        this.pageDown = function(){
            this.page--;
        }

        this.getLimit = function(){
            return this.limit;
        }
    };

    var page = new Page();


    var commentPhotoFlicking = new Flicking($photoList, {
        width: $(window).width(),
        intervalFlag: false,
        afterFlickFn: function(){
            $indexPhoto.html(this.$list.data('curItem'));
        }
    });
    commentPhotoFlicking.swipedetect($photoList);

    var init = function (){
        console.log(page);
        requestComment.call(page);
        requestSummary();
        $(window).on("scroll", scrollViewMoreProductList);
    };

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
                console.log("eee");
                console.log(error.responseJSON);
                alert('Comment photos load를 실패했습니다.');
            });
    };

    var updateGradeArea = function(summary){
        var $gradeArea = $(".grade_area");
        $gradeArea.find(".grade_value").css("width", (summary.avgScore/5 * 100).toFixed());
        $gradeArea.find(".text_value > span").text(summary.avgScore.toFixed(1));
        $gradeArea.find(".join_count > em.green").text(summary.num + "건");
    }

    var requestComment = function(param){
        var apiUrl = window.location.origin + "/api" + window.location.pathname;
        var options = {};
        options.type = "GET";
        options.data = {page: this.getPage(), limit: this.getLimit()};

        var ajaxRequestComment = $.ajax(apiUrl, options);
        ajaxRequestComment.then(addComments);
        ajaxRequestComment.then(function (){
            $('.thumb').on('click', getCommentImagesAjax.bind(this, commentPhotoFlicking.listInit.bind(commentPhotoFlicking)));
            $('.btnPhotoviwerExit').on('click', function(){
                $photoViewer.fadeOut();
            });
        });
        ajaxRequestComment.then(callback.bind(this));

        function callback(){
            this.pageUp();
        }
    };

    var requestSummary = function(){
        var apiUrl = "/api" + window.location.pathname + "/summary";
        var options = {
            type: "GET"
        };
        var ajaxRequestSummary = $.ajax(apiUrl, options);
        ajaxRequestSummary.then(updateGradeArea);
    };

    var sliceList = function(comments, param){
        var $listItem = $('.list_item');
        if($listItem.length > 10 && comments){
            $listItem.slice(0,10).remove();
        }
    }

    var addComments = function(comments){
        sliceList.call(this, comments, true);

        var html = $.map(comments, function(comment){
            comment.productName = productName; // 응답으로 상품 이름도 받아오는 게 나을 듯
            var d = new Date(comment.createDate);
            var date = d.getFullYear() + '.' + (d.getMonth()+1) + '.' + d.getDate();
            comment.date = date;
            return Handlebars.templates['userCommentItem'](comment);
        }).join("");
        $('.list_short_review').append(html);
        scrollFlag = true;
    };

    var scrollViewMoreProductList = function(){
        var docTop = Math.round($(document).scrollTop());
        var docHeight = $(document).height();
        var winHeight = $(window).height();
        if(scrollFlag && docTop === docHeight - winHeight){
            scrollFlag = false;
            requestComment.call(page, true);
        }
        // if(scrollFlag && $(document).scrollTop() === 0){
        //     console.log("2");
        //     scrollFlag = false;
        //     requestComment.call(page, false);
        // }
    };

    return {
        init: init
    }
})();


