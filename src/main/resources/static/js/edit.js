$('#keyword').hide();
$('#btnSearch').hide();

var id = $('#id').val();
if (id!=''){
	$.post($.kbase.ctx + '/notice/loadData?id=' + id, function(data){
		$('#title').val(data.title);
		$('#content').val(data.content);
		$('#words').val(data.words);
	}, 'json');
}

$('#btnSave').click(function(){
	if ($('#title').val()==''){
		$('#title').focus();
		return false;
	}
	if ($('#content').val()==''){
		$('#content').focus();
		return false;
	}
		
	var param = {
		'title': $('#title').val(),
		'content': $('#content').val(),
		'words': $('#words').val()
	}
	if (id!=''){
		param['id'] = id;
	}
	
	//注意：后台采用 @RequestBody 接收，这里一定要指定 dataType 和 contentType 否则会报 415异常
	//http://www.cnblogs.com/quanyongan/archive/2013/04/16/3024741.html
	$.ajax({
		type : 'POST',
		url : $.kbase.ctx + '/notice/save',
		dataType : 'json',
		contentType : 'application/json',
		data : JSON.stringify(param),
		success : function(data) {
			alert(data.title + '保存成功');
		}
	});
});

$('#content').on('change', function(){
	var _this = this;
	$.post($.kbase.ctx + '/notice/extractKeyword', {content: $(_this).val()}, function(data){
		$('#words').val(data);
	}, 'text');
})