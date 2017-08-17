define(["jquery", "Handlebars"], function($, Handlebars) {
  "use strict";

  function init() {
    $(".review_write_footer_wrap").on("change", "input.hidden_input", bindPostEvent);
    $(".lst_thumb").on("click", "a.anchor", bindDeleteEvent);
  }

  function bindPostEvent(e) {
    var files = e.target.files;
    var formData = new FormData();

    if ($(".lst_thumb li.item").length + files.length > 5) {
      alert("이미지는 5개 까지 등록 가능합니다.");
      return;
    }

    $.each(files, function(i, file) {
      if (file.size > 1024 * 1000) {
        alert("이미지의 최대 사이즈는 1MB 입니다.");
      } else {
        formData.append("files", file);
      }
    });

    postImage(formData).done(drawImage).fail(errorHandler);
  }

  function drawImage(ids) {
    var imagesTemplate = Handlebars.compile($('#review-images-template').html());
    $(".lst_thumb").append(imagesTemplate(ids));
    $("input.hidden_input").val("");
  }

  function bindDeleteEvent(e) {
    e.preventDefault();
    $(e.currentTarget).closest("li.item").remove();
  }

  function postImage(formData) {
    return $.ajax({
      url: "/api/images",
      type: "POST",
      data: formData,
      contentType: false,
      processData: false
    });
  }

  function errorHandler(error) {
    console.log(error.responseJSON);
    alert("이미지 등록에 실패했습니다.");
  }

  return {
    init: init
  };
});
