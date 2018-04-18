layui.use('table', function(){
	var table = layui.table;

	table.render({
		id: 'grid',
		elem: '#grid',
		url: $.kbase.ctx + '/notice/loadList',
		cellMinWidth: 100,
		method: 'get',
		response: {
			countName: 'totalElements', //数据总数的字段名称，默认：count
			dataName: 'content' //数据列表的字段名称，默认：data
		},
		cols: [[
	        {type:'checkbox'},
	        {field:'title', title: '标题', width: 320},
	        {field:'content', title: '摘要', width: 500},
	        {field:'createDate', title: '创建日期', templet: function(record){
	        	return dateFns.format(record.createDate, 'YYYY-MM-DD HH:mm');
	        }, width: 160},
	        {field:'modifyDate', title: '修改日期', templet: function(record){
	        	return dateFns.format(record.modifyDate, 'YYYY-MM-DD HH:mm');
	        }, width: 160},
	        {align:'center', toolbar: '#toolbar', width: 140}
	    ]],
	    page: true
	});
	
	table.on('tool(grid)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确认删除吗', function(index){
				$.post($.kbase.ctx + '/notice/delete', {id: data.id}, function(data){
					obj.del();
					layer.close(index);
				}, 'json');
			});
		} else if(obj.event === 'edit'){
			$('#id').val(data.id);
			$('#title').val(data.title);
			$('#content').val(data.content);
			$('#words').val(data.words);
			openForm();
		}
	});
});

$('#btnAdd').click(function(){
	var _this = this;
	$('#id').val('');
	$('#title').val('');
	$('#content').val('');
	$('#words').val('');
	
	openForm();
});

/**
 * 新增或编辑
 */
function openForm(){
	layui.use(['layer', 'table'], function(){
		var layer = layui.layer;
		var table = layui.table;
		
		layer.open({
			type: 1,
			btn: ['保存'],
			area: ['auto', 'auto'],
			content: $('.panel'),
			yes: function(index, layero){
				var param = {
					title: $('#title').val(),
					content: $('#content').val(),
					words: $('#words').val()
				}
				if (param.title.trim()==''){
					$('#title').focus();
					return false;
				}else if (param.content.trim()==''){
					$('#content').focus();
					return false;
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
						layer.alert(data.title + '保存成功', function(){
							table.reload('grid', {page: {curr: 1}});
							layer.closeAll();
						});
					}
				});
			}
		});
	});
}

////搜索
$('#keyword').keyup(function(e){
	if (e.keyCode=='13'){
		$('#btnQuery').click();
	}
});
$('#btnQuery').click(function(){
	if ($('#keyword').val().trim()==''){
		$('#keyword').focus();
		return false;
	}
	$('#btnClear').show();
	layui.use(['layer', 'table'], function(){
		var layer = layui.layer;
		var table = layui.table;
		table.reload('grid', {page: {curr: 1}, where: {keyword: $('#keyword').val().trim()}});
	});
});
$('#btnClear').click(function(){
	$('#keyword').val('');
	$('#btnClear').hide();
	layui.use(['layer', 'table'], function(){
		var layer = layui.layer;
		var table = layui.table;
		table.reload('grid', {page: {curr: 1}, where: {keyword: $('#keyword').val().trim()}});
	});
});