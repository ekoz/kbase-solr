/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.eastrobot.model.SolrNotice;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年4月20日 下午7:22:36
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SolrAdminServiceTests {

	@Autowired
	private SolrAdminService solrAdminService;
	
	@Test
	public void testQuery(){
		Page<SolrNotice> pager = solrAdminService.query("詹姆斯");
		System.out.println(pager.getTotalElements());
		System.out.println(JSONObject.toJSONString(pager));
	}
}
