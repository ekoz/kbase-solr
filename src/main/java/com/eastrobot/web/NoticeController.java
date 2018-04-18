/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.solr.core.query.SolrPageRequest;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.eastrobot.config.BaseController;
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
public class NoticeController extends BaseController {

	@Resource
	private NoticeService noticeService;
	
	/**
	 * 加载公告数据
	 * @author eko.zhan at 2018年3月16日 下午5:05:40
	 * @param keyword
	 * @param page
	 * @param limit
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@GetMapping("loadList")
	public JSONObject loadList(@RequestParam(value="keyword", required=false) String keyword, Integer page, Integer limit) throws SolrServerException, IOException{
		Page<SolrNotice> result = null;
		Pageable pageable = new SolrPageRequest(page-1, limit, Direction.DESC, "createDate_dt");
		if (StringUtils.isBlank(keyword)){
			result = noticeService.findAll(pageable);
		}else{
			result = noticeService.findByKeyword(keyword, pageable);
		}
		JSONObject json = (JSONObject)JSONObject.toJSON(result);
		json.put("code", 0);
		json.put("msg", "");
		
		return json;
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
	public JSONObject delete(@RequestParam(name="id") String id){
		Assert.hasText(id, "参数id不能为空");
		noticeService.delete(id);
		return writeSuccess();
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
