var Comment = (function(){
    var productName = "전시 1";
    var scrollFlag = true;

    var init = function (){
        requestComment();
        $(window).on("scroll", scrollViewMoreProductList);
    }

    var addComments = function(comments){
        var html = $.map(comments, function(comment){
            comment.productName = productName;
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