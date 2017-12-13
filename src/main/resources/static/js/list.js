//加载数据，暂时不考虑分页
$.post($.kbase.ctx + '/notice/loadList', function(data){
	$('#list').append(template('templateList', data.content));
}, 'json');

//编辑
$('#list').on('click', '.btn-edit', function(){
	var id = $(this).attr('_id');
	location.href = $.kbase.ctx + '/edit?id=' + id;
});

//删除
$('#list').on('click', '.btn-del', function(){
	var _this = this;
	var id = $(this).attr('_id');
	if (window.confirm('确定删除吗')){
		$.post($.kbase.ctx + '/notice/delete', {id: id}, function(data){
			$(_this).parent('td').parent('tr').remove();
		});
	}
});

//搜索
$('#keyword').keyup(function(e){
	if (e.keyCode=='13'){
		$('#btnSearch').click();
	}
});
$('#btnSearch').click(function(){
	if ($('#keyword').val()==''){
		$('#keyword').focus();
		return false;
	}
	
	$.post($.kbase.ctx + '/notice/loadList', {keyword: $('#keyword').val()}, function(data){
		if (data.content){
			$('#list').html(template('templateList', data.content));
		}else{
			$('#list').html(template('templateList', data));
		}
	}, 'json');	
	
});