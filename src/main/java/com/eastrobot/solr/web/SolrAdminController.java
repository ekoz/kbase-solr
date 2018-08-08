/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.web;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年4月20日 下午2:30:50
 * @version 1.0
 */
@RestController
public class SolrAdminController {

	@Autowired
	private SolrClient solrClient;
	
	@PostMapping("reload")
	public void reload(String collectionName) throws SolrServerException, IOException{
		CoreAdminRequest coreAdminRequest = new CoreAdminRequest();
		CoreAdminResponse response = coreAdminRequest.reloadCore(collectionName, solrClient);
		
	}
	
	
}
