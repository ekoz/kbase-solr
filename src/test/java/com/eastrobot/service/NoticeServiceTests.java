/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eastrobot.model.SolrNotice;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月29日 上午10:32:07
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeServiceTests {

	@Resource
	private NoticeService noticeService;
	
	@Test
	public void findAll(){
		Iterable<SolrNotice> list = noticeService.findAll();
		list.forEach(notice -> {System.out.println(notice.getTitle());});
	}
	
}
