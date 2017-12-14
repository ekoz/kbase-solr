//加载数据，暂时不考虑分页
$.post($.kbase.ctx + '/notice/loadList', function(data){
	var arr = [];
	var date = new Date();
	$(data.content).each(function(i, item){
		if (item.createDate!=''){
			date.setTime(item.createDate);
			item.createDate = dateFns.format(date, 'YYYY-MM-DD HH:mm')
		}
		if (item.modifyDate!=''){
			date.setTime(item.modifyDate);
			item.modifyDate = dateFns.format(date, 'YYYY-MM-DD HH:mm')
		}
		arr.push(item);
	});
	$('#list').append(template('templateList', arr));
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
			data = data.content
		}
		var arr = [];
		var date = new Date();
		$(data).each(function(i, item){
			if (item.createDate!=''){
				date.setTime(item.createDate);
				item.createDate = dateFns.format(date, 'YYYY-MM-DD HH:mm')
			}
			if (item.modifyDate!=''){
				date.setTime(item.modifyDate);
				item.modifyDate = dateFns.format(date, 'YYYY-MM-DD HH:mm')
			}
			arr.push(item);
		});
		$('#list').html(template('templateList', arr));
	}, 'json');	
	
});