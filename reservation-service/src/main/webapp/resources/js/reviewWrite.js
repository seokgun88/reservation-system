var ReviewWrite = (function(){
    var productId,
        userId,
        rating,
        fileSizeOverFlag = false;

    var $rating = $(".rating"),
        $reviewWriteInfo = $(".review_write_info"),
        $reviewTextarea = $(".review_textarea"),
        $imageFileInput = $("#reviewImageFileOpenInput"),
        $imageList = $(".lst_thumb"),
        $btnBooking = $(".bk_btn");

    var removeImageAjax = function($el, id){
        $.ajax({
            url: "/api/images/" + id,
            type: "DELETE"
        })
        .done(function(){
            $el.remove();
        })
        .fail(function(error){
            console.log(error.responseJSON);
            alert("Image delete를 실패했습니다.");
        });
    };

    var addImageRemoveHandler = function($imageList){
        $imageList.find("a.anchor").on("click", function(e){
            e.preventDefault();
            $li = $(e.target).closest("li.item");
            removeImageAjax($li, $li.data("id"));
        });
    };

    var drawImage = function(ids){
        var imagesTemplate = Handlebars.compile($('#images-template').html());
        $imageList.append(imagesTemplate(ids));
        addImageRemoveHandler($imageList);
    };

    var createImageAjax = function(formData){
        $.ajax({
            url: "/api/images/users/" + userId,
            type: "POST",
            data: formData,
            processData: false,
            contentType: false
        })
        .done(function(data){
            drawImage(data);
            $imageFileInput.val("");
        })
        .fail(function(error){
            console.log(error.responseJSON);
            alert("Image upload를 실패했습니다.");
        });
    };

    var createCommentAjax = function(e){
        var comment = $reviewTextarea.val(),
            score = rating.score(),
            fileIds = [];
        $imageList.children("li.item").each(function(i, el){
            fileIds.push($(el).data("id"));
        });
        var commentData = JSON.stringify({
            "comment" : comment,
            "score" : score,
            "fileIds" : fileIds
        });
        console.log(commentData);
        $.ajax({
            url: "/api/products/" + productId + "/comments/users/" + userId,
            type: "POST",
            data: commentData,
            contentType: "application/json"
        })
        .done(function(){
            location.href = "/myreservation";
        })
        .fail(function(error){
            console.log(error.responseJSON);
            alert("Comment create를 실패했습니다.");
        });
    };

    return {
        init: function(){
            productId = $("#container").data("productid");
            userId = $("#container").data("userid");

            rating = new Rating($(".star_rank"), {
                afterSetScore : function(){
                    this._$root.removeClass("gray_star");
                }
            });
            rating.on("change", function(e){
                rating.setScore(e.score);
            });

            $rating.on("click", "input[type=checkbox]", function(e){
                var score = $(e.target).val();
                rating.trigger("change", {"score": score});
                $.each($(e.target).siblings("input[type=checkbox]").addBack(), function(index, item){
                    var isChecked = ($(item).val() <= score);
                    $(item).prop("checked", isChecked);
                });
            });

            $reviewWriteInfo.on("click", function(e){
                $(e.target).closest(".review_write_info").addClass("hide");
                $reviewTextarea.focus();
            });

            $reviewTextarea.on("focusout", function(e){
                if(!$(e.target).val()){
                    $reviewWriteInfo.removeClass("hide");
                }
            });

            $imageFileInput.on("change", function(e){
                var files = e.target.files,
                    reader = new FileReader(),
                    idx = 0,
                    formData = new FormData();

                for(var i=0; i<files.length; i++){
                    if(files[i].size > 1024 * 1000){
                        fileSizeOverFlag = true;
                    } else {
                        formData.append('files', files[i]);
                    }
                }
                createImageAjax(formData);
                if(fileSizeOverFlag){
                    alert("이미즈 최대 사이즈는 1MB 입니다.");
                    fileSizeOverFlag = false;
                }
            });

            $btnBooking.on("click", createCommentAjax);
        }
    }
})();

$(ReviewWrite.init);