"use strict";

require.config({
    baseUrl: "/resources/js",
    paths: {
        "jquery": "node_modules/jquery/dist/jquery"
    }
});

require([
    "jquery"
], function($){
    var categoryAdmin = (function() {
        // 수정할 수 있는 상태로 변경
        // 1.다른 수정 버튼들 비활성화
        // 2.input 수정 활성화 후 포커싱
        // 3.수정, 삭제 버튼 숨김
        // 4.확인, 취소 버튼 보임
        var changeModifyState = function($category, $categoryInput) {
            $('#category_list .modifyBtn').prop('disabled', true);

            var $modifyBtn = $category.find('.modifyBtn');
            var $removeBtn = $category.find('.removeBtn');
            var $modifyConfirmBtn = $category.find('.modifyConfirmBtn');
            var $modifyCancleBtn = $category.find('.modifyCancleBtn');

            $categoryInput.prop('readonly', false);
            $categoryInput.focus();

            $modifyBtn.hide();
            $removeBtn.hide();
            $modifyConfirmBtn.show();
            $modifyCancleBtn.show();
        };

        // 일반 카테고리 뷰 상태로 변경
        // 1.다른 수정 버튼들 활성화
        // 2.input 수정 비활성화
        // 3.수정, 삭제 버튼 보임
        // 4.확인, 취소 버튼 숨김
        var changeNormalState = function($category, $categoryInput) {
            $categoryInput.prop('readonly', true);
            $('#category_list .modifyBtn').prop('disabled', false);

            var $modifyBtn = $category.find('.modifyBtn');
            var $removeBtn = $category.find('.removeBtn');
            var $modifyConfirmBtn = $category.find('.modifyConfirmBtn');
            var $modifyCancleBtn = $category.find('.modifyCancleBtn');

            $modifyBtn.show();
            $removeBtn.show();
            $modifyConfirmBtn.hide();
            $modifyCancleBtn.hide();
        };

        return {
            removeBtnListener: function(e) {
                if ($(e.target).hasClass('removeBtn')) {
                    var li = e.target.parentNode;
                    var id = li.dataset.id;
                    $.ajax({
                        url : '/api/categories/' + id,
                        type : 'DELETE'
                    }).done(function() {
                        li.parentNode.removeChild(li);
                    }).fail(function(error) {
                        console.log(error.responseJSON)
                        alert('Category 삭제를 실패했습니다.');
                    });
                }
            },

            modifyBtnListener: function(e) {
                var $category = $(e.target).parent();
                var $categoryInput = $category.find('.categoryName');

                changeModifyState($category, $categoryInput);
            },

            modifyConfirmBtnListener: function(e) {
                var $category = $(e.target).parent();
                var $categoryInput = $category.find('.categoryName');

                var id = $category.data('id');
                var name = $categoryInput.val();

                if (!name || name.trim().length === 0) {
                    $categoryInput.val($categoryInput.prop('defaultValue'));
                    changeNormalState($category, $categoryInput);
                    alert('공백은 입력할 수 없습니다.');
                } else {
                    var categoryData = JSON.stringify({
                        name : name
                    });
                    $.ajax({
                        url : '/api/categories/' + id,
                        type : 'PUT',
                        data : categoryData,
                        contentType : "application/json"
                    }).done(function() {
                        $categoryInput.prop('defaultValue', name);
                        changeNormalState($category, $categoryInput);
                    }).fail(function(error) {
                        console.log(error.responseJSON);
                        changeNormalState($category, $categoryInput);
                        alert('Category update를 실패했습니다.');
                    });
                }
            },

            modifyCancleBtnListener: function(e) {
                var $category = $(e.target).parent();
                var $categoryInput = $category.find('.categoryName');

                $categoryInput.val($categoryInput.prop('defaultValue'));
                changeNormalState($category, $categoryInput);
            }
        };
    })();

    // jquery를 이용한 수정 이벤트 처리
    // 이벤트 델리게이션을 하기 위해 각 카테고리 <li>태그의 부모 #category_list에 이벤트리스너 등록

    $('#category_list').on('click', categoryAdmin.removeBtnListener);

    // 수정 버튼 클릭시 수정 상태로 변경
    $('#category_list').on('click', '.modifyBtn', categoryAdmin.modifyBtnListener);

    // 확인 버튼 클릭시 형제 input 태그의 내용을 put 방식 ajax로 서버에 업데이트 요청
    // 요청 성공시 또는 공백 입력시 일반 카테고리 뷰 상태로 변경
    // 요청 실패시 실패 알림 후 일반 카테고리 뷰 상태로 변경
    $('#category_list').on('click', '.modifyConfirmBtn', categoryAdmin.modifyConfirmBtnListener);

    // 취소 버튼 클릭시 일반 카테고리 뷰 상태로 변경
    $('#category_list').on('click', '.modifyCancleBtn', categoryAdmin.modifyCancleBtnListener);
});
