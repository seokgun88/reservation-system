// javscript addEventListener를 이용한 삭제 버튼 이벤트 처리
var categoryList = document.getElementById('category_list');
categoryList.addEventListener('click', function(e) {
	if (e.target && e.target.className === 'removeBtn') {
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
});

// jquery를 이용한 수정, 삭제 이벤트 처리
// 이벤트 델리게이션을 하기 위해 각 카테고리 <li>태그의 부모 #category_list에 이벤트리스너 등록
// 수정 버튼 클릭시 형제 input이 수정가능하게 바뀌고 다른 수정 버튼들 비활성화, 자신은 보이지 않게 하고 수정 확인 버튼을 보이게 함
$('#category_list').on('click', '.modifyBtn', function(e) {
	$('#category_list .categoryName').prop('readonly', true);
	$('#category_list .modifyBtn').prop('disabled', true);

	var $modifyBtn = $(e.target);
	var $categoryInput = $modifyBtn.siblings('.categoryName').first();
	$categoryInput.prop('readonly', false);
	$categoryInput.focus();

	var $modifyConfirmBtn = $modifyBtn.next();
	$modifyBtn.hide();
	$modifyConfirmBtn.show();
});

// 확인 버튼 클릭시 형제 input 태그의 내용을 put 방식 ajax로 서버에 업데이트 요청
// 요청 성공시 형제 input 태그를 수정 불가로 변경 후 자신은 숨김, 수정 버튼을 보이게 하고 모든 수정 버튼들 활성화
$('#category_list').on('click', '.modifyConfirmBtn', function(e) {
	var $modifyConfirmBtn = $(e.target);
	var $categoryInput = $modifyConfirmBtn.siblings('.categoryName').first();

	var name = $categoryInput.val();
	var id = $modifyConfirmBtn.parent().data('id');

	var categoryData = JSON.stringify({
		name : name
	});
	$.ajax({
		url : '/api/categories/' + id,
		type : 'PUT',
		data : categoryData,
		contentType : "application/json"
	}).done(function() {
		$categoryInput.prop('readonly', true);
		$('#category_list .modifyBtn').prop('disabled', false);

		var $modifyBtn = $modifyConfirmBtn.prev();
		$modifyBtn.show();
		$modifyConfirmBtn.hide();
	}).fail(function(error) {
		console.log(error.responseJSON);
		alert('Category update를 실패했습니다.');
	});
});