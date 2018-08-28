/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.eastrobot.solr.model.SolrGovMail;
import com.eastrobot.solr.repository.SolrGovMailRepository;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年8月28日 下午4:55:12
 * @version 1.0
 */
@Service
public class SolrGovMailServiceImpl implements SolrGovMailService {
	
	@Resource
	SolrGovMailRepository solrGovMailRepository;
	@Autowired
	private SolrClient solrClient;
	
	@Override
	public void importData(String data) throws Exception {
		List<SolrGovMail> govmailList = JSON.parseArray(data, SolrGovMail.class);
		govmailList.forEach(govmail -> {
			if (StringUtils.isBlank(govmail.getId())) {
				govmail.setId(UUID.randomUUID().toString());
			}
		});
		solrGovMailRepository.deleteAll();
		solrGovMailRepository.save(govmailList);
	}

}
