/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.web;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月29日 上午9:35:51
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoticeControllerTests {

	@Autowired
    private MockMvc mvc;
	
	
}
