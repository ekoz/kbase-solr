/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年4月20日 下午5:54:21
 * @version 1.0
 */
@Configuration
public class SolrConfigurer {

	@Autowired
	private SolrClient solrClient;
	
	@Bean
	public SolrTemplate solrTemplate(){
		return new SolrTemplate(solrClient);
	}

}
