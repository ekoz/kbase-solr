/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eastrobot.solr.config.BaseController;
import com.eastrobot.solr.model.SolrGovMail;
import com.eastrobot.solr.repository.SolrGovMailRepository;
import com.eastrobot.solr.service.SolrGovMailService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年8月28日 下午2:55:37
 * @version 1.0
 */
@RestController
@RequestMapping("/import")
public class ImportController extends BaseController {

	@Resource
	SolrGovMailService solrGovMailService;
	
	/**
	 * 导入 govmail 数据
	 * @author eko.zhan at 2018年8月28日 下午2:56:30
	 */
	@RequestMapping("/govmail")
	public JSONObject govmail(String data) {
		data = StringEscapeUtils.unescapeJava(data);
		try {
			solrGovMailService.importData(data);
		}catch (Exception e) {
			return writeFailure(e.getMessage());
		}
		return writeSuccess("数据导入成功");
	}
	
}
