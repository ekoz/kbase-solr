/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.FieldAnalysisRequest;
import org.apache.solr.client.solrj.response.AnalysisResponseBase.AnalysisPhase;
import org.apache.solr.client.solrj.response.AnalysisResponseBase.TokenInfo;
import org.apache.solr.client.solrj.response.FieldAnalysisResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightEntry.Highlight;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
	@Autowired
    private SolrClient client;
	
	@Transactional
	@Override
	public void save(Notice notice) {
		noticeRepository.save(notice);
		solrNoticeRepository.save(transfer(notice));
	}

	@Override
	public Page<SolrNotice> findAll(Pageable pageable) {
		return solrNoticeRepository.findAll(pageable);
	}

	@Override
	public Page<SolrNotice> findByKeyword(String keyword, Pageable pageable) {
		String title = keyword;
		String content = keyword;
		String contentPinyin = keyword;
//		HighlightPage<SolrNotice> pager = solrNoticeRepository.findByTitleOrContentOrContentPinyin(title, content, contentPinyin, pageable);
		HighlightPage<SolrNotice> pager = solrNoticeRepository.findByTitleOrContent(title, content, pageable);
		
		List<SolrNotice> list = pager.getContent();
		for (SolrNotice entity : list){
			List<Highlight> highlightList = pager.getHighlights(entity);
			for (Highlight highlight : highlightList){
				if ("title_cn".equals(highlight.getField().getName())){
					entity.setTitle(highlight.getSnipplets().get(0));
				}
				if ("content_cn".equals(highlight.getField().getName())){
					entity.setContent(highlight.getSnipplets().get(0));
				}
			}
		}
		
		return pager;
	}

	@Transactional
	@Override
	public void delete(String id) {
		Assert.notNull(id, "参数 id 不能为空");
		noticeRepository.delete(id);
		solrNoticeRepository.delete(id);
	}

	@Override
	public Notice findOne(String id) {
		return noticeRepository.findOne(id);
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
//		return new SolrNotice(notice.getId(), notice.getTitle(), notice.getContent());
		return new SolrNotice(notice.getId(), notice.getTitle(), notice.getContent(), notice.getCreateDate(), notice.getModifyDate());
	}

	@Override
	public String[] getSegments(String content) throws SolrServerException, IOException {
		List<String> list = new ArrayList<String>();
		FieldAnalysisRequest request = new FieldAnalysisRequest("/analysis/field");
		List<String> fieldNameList = new ArrayList<String>();
		fieldNameList.add("content_cn");
		List<String> fieldTypeList = new ArrayList<String>();
		fieldTypeList.add("text_cn");
		request.setFieldNames(fieldNameList);
		request.setFieldTypes(fieldTypeList);
		request.setFieldValue(content);
		FieldAnalysisResponse response = request.process(client, "notice");
		Iterator<AnalysisPhase> it = response.getFieldTypeAnalysis("text_cn").getIndexPhases().iterator();//分词结果  
		//  Iterator it = response.getFieldTypeAnalysis(fieldType).getQueryPhases().iterator(); //检索中的切分效果  
		List<TokenInfo> tokenInfoList = null;  
		while (it.hasNext()) {  
			AnalysisPhase pharse = (AnalysisPhase)it.next();  
			tokenInfoList = pharse.getTokens();  
		}
		for (TokenInfo tokenInfo : tokenInfoList){
			list.add(tokenInfo.getText());
		}
		return list.toArray(new String[list.size()]);
	}
}
