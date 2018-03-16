/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eastrobot.model.Notice;
import com.eastrobot.model.SolrNotice;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月29日 上午10:10:56
 * @version 1.0
 */
public interface NoticeService {

	public void save(Notice notice);
	
	/**
	 * 加载公告数据
	 * @author eko.zhan at 2018年3月16日 下午3:11:06
	 * @return
	 */
	public Page<SolrNotice> findAll(Pageable pageable);

	public Page<SolrNotice> findByKeyword(String keyword, Pageable pageable);

	public void delete(String id);

	public Notice findOne(String id);
	
	/**
	 * 从mongo中将数据同步至solr
	 * @author eko.zhan at 2017年12月13日 下午3:30:27
	 */
	public void sync();
	/**
	 * 获取分词结果
	 * @author eko.zhan at 2017年12月13日 下午5:52:23
	 * @param content
	 * @return
	 */
	public String[] getSegments(String content) throws SolrServerException, IOException ;
}
