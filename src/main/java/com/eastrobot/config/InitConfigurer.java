/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.eastrobot.annotation.Interval;
import com.eastrobot.service.NoticeService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月13日 下午3:26:48
 * @version 1.0
 */
@Component
public class InitConfigurer implements CommandLineRunner{

	private final Log logger = LogFactory.getLog(InitConfigurer.class);
	
	@Autowired
	private NoticeService noticeService;
	
	@Interval
	@Override
	public void run(String... args) throws Exception {
		logger.info("service started.");
		
		noticeService.sync();
	}

}
