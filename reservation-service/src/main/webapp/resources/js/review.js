var Comment = (function(){
    var scrollFlag = true;

    var init = function (){
        requestComment();
        $(window).on("scroll", scrollViewMoreProductList);
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
    }

    var requestComment = function (){
        var apiUrl = window.location.origin + "/api" + window.location.pathname;
        var options = {
            type: "GET",
            data: {
                size: 10
            }
        }
        var ajaxrequestComment = $.ajax(apiUrl, options);
        ajaxrequestComment.then(addComments.bind($('.list_short_review')));
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