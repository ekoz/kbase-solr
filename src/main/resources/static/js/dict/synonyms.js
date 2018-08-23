$.post($.kbase.ctx + '/dict/loadSynonyms', function(data){
	$('#panel').text(data);
	layui.use('code', function(){
		layui.code();
		$('.layui-code-ol').attr('contenteditable', true);
	});
}, 'text');
var fireKeydown = false; //监听是否发生过按键行为
$('#panel').on('keydown', '.layui-code-ol', function(){
	fireKeydown = true;
});
$('#panel').on('blur', '.layui-code-ol', function(){
	if (!fireKeydown) return false;
	var data = $(this).html().replace(/\<\/?li\>/gi, ' ');
	data = data.replace(/\s+/g, ' ');
	console.log(data); 
	$.post($.kbase.ctx + '/dict/saveSynonyms', {data: data}, function(data){
		alert(data);
	}, 'json');
});