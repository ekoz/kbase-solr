/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.stereotype.Service;

import com.eastrobot.model.SolrNotice;

/**
 * 用 solrTemplate 进行数据的增删改查
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年4月20日 下午7:22:36
 * @version 1.0
 */
@Service
public class SolrAdminService {

	@Resource
	private SolrTemplate solrTemplate;
	
	/**
	 * 采用 solrTemplate 查询
	 * @author eko.zhan at 2018年8月7日 下午7:30:37
	 * @return
	 */
	public Page<SolrNotice> query(String keyword){
		Query query = new SimpleQuery(new SimpleStringCriteria("title_cn:" + keyword));
		Page<SolrNotice> page = solrTemplate.query("notice", query, SolrNotice.class);
		return page;
	}
}
