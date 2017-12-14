/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.solr.core.query.SolrPageRequest;
import org.springframework.data.solr.core.query.result.HighlightEntry.Highlight;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.web.PageableDefault;
import org.springframework.test.context.junit4.SpringRunner;

import com.eastrobot.model.Notice;
import com.eastrobot.model.SolrNotice;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月24日 上午11:30:13
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SolrNoticeRepositoryTests {

	@Resource
	private NoticeRepository noticeRepository;
	@Resource
	private SolrNoticeRepository solrNoticeRepository;
	
	@Test
	public void delete(){
		solrNoticeRepository.deleteAll();
	}
	
	@Test
	public void index(){
		Iterable<Notice> itr = noticeRepository.findAll();
		List<SolrNotice> solrNoticeList = new ArrayList<SolrNotice>();
		itr.forEach(notice -> {solrNoticeList.add(new SolrNotice(notice.getId(), notice.getTitle(), notice.getContent()));});
		solrNoticeRepository.save(solrNoticeList);
	}
	
	@Test
	public void query(){
		Iterable<SolrNotice> list = new ArrayList<SolrNotice>();
//		list = solrNoticeRepository.findByTitleAndContent("达成买断协议", "马戏城");
//		list = solrNoticeRepository.findByTitle("上海");
//		list = solrNoticeRepository.findByContent("上海");
		
		Pageable pageable = new SolrPageRequest(10, 15);
		list = solrNoticeRepository.findByTitleOrContentOrContentPinyin("wo", "wo", "wo", pageable);
		
		list.forEach(notice -> {System.out.println(notice.getTitle() + "\r\n" + notice.getContent() + "\r\n-----");});
	}
	
	@Test
	public void testQueryHighlight(){
		Pageable pageable = new SolrPageRequest(0, 15);
		HighlightPage<SolrNotice> pager = solrNoticeRepository.findByTitleOrContentOrContentPinyin("我", "我", "我", pageable);
		List<SolrNotice> list = pager.getContent();
		for (SolrNotice solrNotice : list){
			List<Highlight> highlightList = pager.getHighlights(solrNotice);
			for (Highlight highlight : highlightList){
				System.out.println(highlight.getField().getName() + " -> " + highlight.getSnipplets().toArray(new String[highlight.getSnipplets().size()])[0]);
			}
		}
		
	}
	
	@Test
	public void findAll(){
		Iterable<SolrNotice> list = solrNoticeRepository.findAll();
		list.forEach(notice -> {System.out.println(notice.getTitle() + "\r\n" + notice.getContent() + "\r\n-----");});
	}
}
