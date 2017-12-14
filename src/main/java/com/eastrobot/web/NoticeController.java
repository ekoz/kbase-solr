/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eastrobot.model.Notice;
import com.eastrobot.model.SolrNotice;
import com.eastrobot.service.NoticeService;
import com.hankcs.hanlp.HanLP;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月29日 上午9:34:46
 * @version 1.0
 */
@RequestMapping("notice")
@RestController
public class NoticeController {

	@Resource
	private NoticeService noticeService;
	
	@PostMapping("loadList")
	public Iterable<SolrNotice> loadList(@RequestParam(value="keyword", required=false) String keyword, @PageableDefault(value=15, sort={"createDate_dt"}, direction=Direction.DESC) Pageable pageable) throws SolrServerException, IOException{
		if (keyword==null){
			return noticeService.findAll();
		}
		return noticeService.findByKeyword(keyword, pageable);
	}
	
	@PostMapping("save")
	public Notice save(@RequestBody Notice notice){
		if (notice.getCreateDate()==null){
			notice.setCreateDate(new Date());
		}
		notice.setModifyDate(new Date());
		noticeService.save(notice);
		return notice;
	}
	
	@PostMapping("delete")
	public void delete(@RequestParam(name="id") String id){
		noticeService.delete(id);
	}
	
	@PostMapping("loadData")
	public Notice loadData(@RequestParam(name="id") String id){
		return noticeService.findOne(id);
	}
	
	@PostMapping("extractKeyword")
	public String extractKeyword(@RequestParam(name="content") String content){
		Assert.hasText(content, "参数content不能为空");
		
		//int len = content.length()/10;
		int len = 5;
		List<String> keywordList = HanLP.extractKeyword(content, len);
		
		String result = "";
		for (String keyword : keywordList){
			result += keyword + " ";
		}
		return result;
	}
}
