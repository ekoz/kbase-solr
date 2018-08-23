layui.use('element', function(){
	var element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
	var _height = $(window).height()-90;
	$('iframe').height(_height);
	
	element.on('tab(tabs)', function(elem){
		var layId = $(this).attr('lay-id'); 
		if (layId=='mgrStopwords'){
			if ($('#panelStopwords>iframe').length==0){
				$('#panelStopwords').append('<iframe src="' + $.kbase.ctx +  '/dict/stopwords" frameborder="0" style="overflow:hidden;height:' + _height + 'px;width:100%"></iframe>');
			}
		}else if (layId=='mgrSynonyms'){
			if ($('#panelSynonyms>iframe').length==0){
				$('#panelSynonyms').append('<iframe src="' + $.kbase.ctx +  '/dict/synonyms" frameborder="0" style="overflow:hidden;height:' + _height + 'px;width:100%"></iframe>');
			}
		}
	});
});