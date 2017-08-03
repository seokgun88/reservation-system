(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['mySummary'] = template({"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<ul class=\"summary_board\">\r\n    <li class=\"item\">\r\n        <!--[D] 선택 후 .on 추가 link_summary_board -->\r\n        <a href=\"#\" class=\"link_summary_board on\">\r\n            <i class=\"spr_book2 ico_book2\"></i>\r\n            <em class=\"tit\">전체</em>\r\n            <span class=\"figure\">"
    + alias4(((helper = (helper = helpers.totalReservationCount || (depth0 != null ? depth0.totalReservationCount : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"totalReservationCount","hash":{},"data":data}) : helper)))
    + "</span>\r\n        </a>\r\n    </li>\r\n    <li class=\"item\">\r\n        <a href=\"#\" class=\"link_summary_board\">\r\n            <i class=\"spr_book2 ico_book_ss\"></i>\r\n            <em class=\"tit\">이용예정</em>\r\n            <span class=\"figure\">"
    + alias4(((helper = (helper = helpers.scheduledReservationCount || (depth0 != null ? depth0.scheduledReservationCount : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"scheduledReservationCount","hash":{},"data":data}) : helper)))
    + "</span>\r\n        </a>\r\n    </li>\r\n    <li class=\"item\">\r\n        <a href=\"#\" class=\"link_summary_board\">\r\n            <i class=\"spr_book2 ico_check\"></i>\r\n            <em class=\"tit\">이용완료</em>\r\n            <span class=\"figure\">"
    + alias4(((helper = (helper = helpers.completedReservationCount || (depth0 != null ? depth0.completedReservationCount : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"completedReservationCount","hash":{},"data":data}) : helper)))
    + "</span>\r\n        </a>\r\n    </li>\r\n    <li class=\"item\">\r\n        <a href=\"#\" class=\"link_summary_board\">\r\n            <i class=\"spr_book2 ico_back\"></i>\r\n            <em class=\"tit\">취소·환불</em>\r\n            <span class=\"figure\">"
    + alias4(((helper = (helper = helpers.canceledReservationCount || (depth0 != null ? depth0.canceledReservationCount : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"canceledReservationCount","hash":{},"data":data}) : helper)))
    + "</span>\r\n        </a>\r\n    </li>\r\n</ul>\r\n";
},"useData":true});
templates['nolist'] = template({"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    return "<div class=\"err\">\r\n    <i class=\"spr_book ico_info_nolist\"></i>\r\n    <h1 class=\"tit\">예약 리스트가 없습니다</h1>\r\n</div>";
},"useData":true});
templates['photoViewerTemplate'] = template({"1":function(container,depth0,helpers,partials,data) {
    return "    <li style=\"display:inline-block; width:100%; height:100%; text-align:center;\">\r\n        <img src=\"http://220.230.112.236/api/images/"
    + container.escapeExpression(container.lambda(depth0, depth0))
    + "\" style=\"max-width:100%; max-hegiht:100%;\">\r\n    </li>\r\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),depth0,{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
templates['reservationCardItem'] = template({"1":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "                    <div class=\"booking_cancel\">\r\n                        <button class=\"btn "
    + alias4(((helper = (helper = helpers.btnCancelTextClass || (depth0 != null ? depth0.btnCancelTextClass : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"btnCancelTextClass","hash":{},"data":data}) : helper)))
    + "\">\r\n                                <span>"
    + alias4(((helper = (helper = helpers.btnCancelText || (depth0 != null ? depth0.btnCancelText : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"btnCancelText","hash":{},"data":data}) : helper)))
    + "</span>\r\n                        </button>\r\n                    </div>\r\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<article class=\"card_item\">\r\n    <a href=\"#\" class=\"link_booking_details\">\r\n        <div class=\"card_body\">\r\n            <div class=\"left\"></div>\r\n            <div class=\"middle\">\r\n                <div class=\"card_detail\">\r\n                    <em class=\"booking_number\">No."
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "</em>\r\n                    <h4 class=\"tit\">"
    + alias4(((helper = (helper = helpers.productName || (depth0 != null ? depth0.productName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"productName","hash":{},"data":data}) : helper)))
    + "</h4>\r\n                    <ul class=\"detail\">\r\n                        <li class=\"item\">\r\n                            <span class=\"item_tit\">일정</span>\r\n                            <em class=\"item_dsc\"> "
    + alias4(((helper = (helper = helpers.displayPeriod || (depth0 != null ? depth0.displayPeriod : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"displayPeriod","hash":{},"data":data}) : helper)))
    + " </em></li>\r\n                        <li class=\"item\">\r\n                            <span class=\"item_tit\">내역</span>\r\n                            <em class=\"item_dsc\"> "
    + alias4(((helper = (helper = helpers.formattedReservationContents || (depth0 != null ? depth0.formattedReservationContents : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"formattedReservationContents","hash":{},"data":data}) : helper)))
    + " </em></li>\r\n                        <li class=\"item\">\r\n                            <span class=\"item_tit\">상품</span>\r\n                            <em class=\"item_dsc\"> "
    + alias4(((helper = (helper = helpers.productName || (depth0 != null ? depth0.productName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"productName","hash":{},"data":data}) : helper)))
    + " </em></li>\r\n                    </ul>\r\n                    <div class=\"price_summary\">\r\n                        <span class=\"price_tit\">결제 예정금액</span>\r\n                        <em class=\"price_amount\"> <span>"
    + alias4(((helper = (helper = helpers.formattedTotalPrice || (depth0 != null ? depth0.formattedTotalPrice : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"formattedTotalPrice","hash":{},"data":data}) : helper)))
    + "</span> <span class=\"unit\">원</span></em>\r\n                    </div>\r\n                    <!-- [D] 예약 신청중, 예약 확정 만 취소가능, 취소 버튼 클릭 시 취소 팝업 활성화 -->\r\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.btnCancelText : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                </div>\r\n            </div>\r\n            <div class=\"right\"></div>\r\n        </div>\r\n        <div class=\"card_footer\">\r\n            <div class=\"left\"></div>\r\n            <div class=\"middle\"></div>\r\n            <div class=\"right\"></div>\r\n        </div>\r\n    </a>\r\n    <a href=\"#\" class=\"fn fn-share1 naver-splugin btn_goto_share\" title=\"공유하기\"></a>\r\n</article>\r\n";
},"useData":true});
templates['userCommentItem'] = template({"1":function(container,depth0,helpers,partials,data) {
    return " no_img";
},"3":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "                <div class=\"thumb_area\">\r\n                    <a href=\"#\" class=\"thumb\" title=\"이미지 크게 보기\">\r\n                        <img width=\"90\" height=\"90\" class=\"img_vertical_top\"\r\n                             src=\"http://220.230.112.236/api/images/"
    + alias4(((helper = (helper = helpers.fileId || (depth0 != null ? depth0.fileId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"fileId","hash":{},"data":data}) : helper)))
    + "\"\r\n                             alt=\"리뷰이미지\">\r\n                    </a> <span class=\"img_count\">"
    + alias4(((helper = (helper = helpers.filesNum || (depth0 != null ? depth0.filesNum : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"filesNum","hash":{},"data":data}) : helper)))
    + "</span>\r\n                </div>\r\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<li class=\"list_item\" data-id=\""
    + alias4(((helper = (helper = helpers.id || (depth0 != null ? depth0.id : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"id","hash":{},"data":data}) : helper)))
    + "\">\r\n    <div>\r\n        <div class=\"review_area"
    + ((stack1 = helpers.unless.call(alias1,(depth0 != null ? depth0.fileId : depth0),{"name":"unless","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\">\r\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.fileId : depth0),{"name":"if","hash":{},"fn":container.program(3, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "            <h4 class=\"resoc_name\">"
    + alias4(((helper = (helper = helpers.productName || (depth0 != null ? depth0.productName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"productName","hash":{},"data":data}) : helper)))
    + "</h4>\r\n            <p class=\"review\">"
    + alias4(((helper = (helper = helpers.comment || (depth0 != null ? depth0.comment : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"comment","hash":{},"data":data}) : helper)))
    + "</p>\r\n        </div>\r\n        <div class=\"info_area\">\r\n            <div class=\"review_info\">\r\n                <span class=\"grade\">"
    + alias4(((helper = (helper = helpers.score || (depth0 != null ? depth0.score : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"score","hash":{},"data":data}) : helper)))
    + "</span>\r\n                <span class=\"name\">"
    + alias4(((helper = (helper = helpers.snsId || (depth0 != null ? depth0.snsId : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"snsId","hash":{},"data":data}) : helper)))
    + "</span>\r\n                <span class=\"date\">"
    + alias4(((helper = (helper = helpers.date || (depth0 != null ? depth0.date : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"date","hash":{},"data":data}) : helper)))
    + " 방문</span>\r\n            </div>\r\n        </div>\r\n    </div>\r\n</li>";
},"useData":true});
})();
