/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.repository;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eastrobot.model.Notice;
import com.eastrobot.model.SolrNotice;


/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月22日 下午5:05:20
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeRepositoryTests{
	
	@Resource
	private NoticeRepository noticeRepository;
	@Resource
	private SolrNoticeRepository solrNoticeRepository;
	
	@Test
	public void save(){
		Notice notice = new Notice();
		notice.setTitle("买断协议达成！韦德将离开公牛 新下家四选一");
		notice.setContent("今年夏天，韦德选择执行下赛季价值2380万美元的合同选项，但是公牛队决定彻底重建，并且送走了当家球星吉米-巴特勒。");
		noticeRepository.save(notice);
		solrNoticeRepository.save(new SolrNotice(notice.getId(), notice.getTitle(), notice.getContent()));
	}
	
	@Test
	public void findAll(){
		Iterable<Notice> itr = noticeRepository.findAll();
		itr.forEach(notice -> System.out.println(notice.getTitle() + " -> " + notice.getContent()));
	}
}
