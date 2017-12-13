/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import com.eastrobot.model.Notice;
import com.eastrobot.model.SolrNotice;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月29日 上午10:10:56
 * @version 1.0
 */
public interface NoticeService {

	public void save(Notice notice);
	
	public Iterable<SolrNotice> findAll();

	public Iterable<SolrNotice> findByKeyword(String keyword);

	public void delete(String id);

	public SolrNotice findOne(String id);
	
	/**
	 * 从mongo中将数据同步至solr
	 * @author eko.zhan at 2017年12月13日 下午3:30:27
	 */
	public void sync();
}
