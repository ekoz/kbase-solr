/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.solr.repository.SolrCrudRepository;

import com.eastrobot.model.SolrNotice;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月22日 下午5:55:59
 * @version 1.0
 */
public interface SolrNoticeRepository extends SolrCrudRepository<SolrNotice, Serializable>{

	public Iterable<SolrNotice> findByTitleAndContent(String title, String content);
	
	public Iterable<SolrNotice> findByTitleOrContent(String title, String content);
	
	public Iterable<SolrNotice> findByTitle(String title);
	
	public Iterable<SolrNotice> findByContent(String content);
}
