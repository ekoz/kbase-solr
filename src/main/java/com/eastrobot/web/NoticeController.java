/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eastrobot.model.Notice;
import com.eastrobot.model.SolrNotice;
import com.eastrobot.service.NoticeService;

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
	public Iterable<SolrNotice> loadList(@RequestParam(value="keyword", required=false) String keyword){
		if (keyword==null){
			return noticeService.findAll();
		}
		return noticeService.findByKeyword(keyword);
	}
	
	@PostMapping("save")
	public Notice save(@RequestBody Notice notice){
		noticeService.save(notice);
		return notice;
	}
	
	@PostMapping("delete")
	public void delete(@RequestParam(name="id") String id){
		noticeService.delete(id);
	}
	
	@PostMapping("loadData")
	public SolrNotice loadData(@RequestParam(name="id") String id){
		return noticeService.findOne(id);
	}
}
