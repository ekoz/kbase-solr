/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年8月9日 下午1:23:24
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DictionaryServiceTests {

	@Autowired
	DictionaryService dictionaryService;
	
	@Test
	public void testLoadStopwords() throws IOException {
		String s = dictionaryService.loadStopwords();
		System.out.println(s);
	}
	
}
