/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.eastrobot.model.SolrNotice;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月22日 下午5:55:59
 * @version 1.0
 */
public interface SolrNoticeRepository extends SolrCrudRepository<SolrNotice, Serializable>{

	@Highlight(prefix = "<b class=\"highlight\">", postfix = "</b>")
	public HighlightPage<SolrNotice> findByTitleOrContentOrContentPinyin(String title, String content, String contentPinyin, Pageable pageable);
	
	public Iterable<SolrNotice> findByTitle(String title);
	
	public Iterable<SolrNotice> findByContent(String content);
	
	public Page<SolrNotice> findByTitleAndContent(String title, String content, Pageable pageable);
	
	public Page<SolrNotice> findAll(Pageable pageable);
}
