/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.request.FieldAnalysisRequest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.server.support.SolrClientUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eastrobot.model.Notice;
import com.eastrobot.model.SolrNotice;
import com.eastrobot.repository.NoticeRepository;
import com.eastrobot.repository.SolrNoticeRepository;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月29日 上午10:12:10
 * @version 1.0
 */
@Service
public class NoticeServiceImpl implements NoticeService {

	@Resource
	private NoticeRepository noticeRepository;
	@Resource
	private SolrNoticeRepository solrNoticeRepository;
	
	@Transactional
	@Override
	public void save(Notice notice) {
		noticeRepository.save(notice);
		solrNoticeRepository.save(transfer(notice));
	}

	@Override
	public Iterable<SolrNotice> findAll() {
		return solrNoticeRepository.findAll();
	}

	@Override
	public Iterable<SolrNotice> findByKeyword(String keyword) {
		return solrNoticeRepository.findByTitleOrContent(keyword, keyword);
	}

	@Transactional
	@Override
	public void delete(String id) {
		noticeRepository.delete(id);
		solrNoticeRepository.delete(id);
		
	}

	@Override
	public SolrNotice findOne(String id) {
		return solrNoticeRepository.findOne(id);
	}

	@Override
	public void sync() {
		solrNoticeRepository.deleteAll();
		Iterable<Notice> noticeList = noticeRepository.findAll();
		
		List<SolrNotice> list = new ArrayList<SolrNotice>();
		for (Notice notice : noticeList){
			list.add(transfer(notice));
		}
		
		solrNoticeRepository.save(list);
	}

	private SolrNotice transfer(Notice notice){
		return new SolrNotice(notice.getId(), notice.getTitle(), notice.getContent());
	}
}
