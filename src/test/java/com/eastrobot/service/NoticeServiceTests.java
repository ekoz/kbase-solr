/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.solr.core.query.SolrPageRequest;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.web.PageableDefault;
import org.springframework.test.context.junit4.SpringRunner;

import com.eastrobot.model.SolrNotice;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月29日 上午10:32:07
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeServiceTests {

	@Resource
	private NoticeService noticeService;
	
	@Test
	public void testfindAll(){
		Sort sort = new Sort(Direction.DESC, "createDate_dt");
		Pageable page = new SolrPageRequest(0, 10, sort);
		Iterable<SolrNotice> list = noticeService.findAll(page);
		list.forEach(notice -> {
			System.out.println(notice.getTitle() + " " + notice.getCreateDate());
		});
	}
	
	@Test
	public void testHighlight(){
		Pageable pageable = new SolrPageRequest(0, 15);
		List<SolrNotice> list = noticeService.findByKeyword("我", pageable);
		for (SolrNotice solrNotice : list){
			System.out.println(solrNotice.getTitle() + " -> " + solrNotice.getContent());
		}
	}
}
