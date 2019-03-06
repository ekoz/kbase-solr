/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.service;

import com.eastrobot.solr.model.SolrNotice;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @version 1.0
 * @date 2019/3/6 11:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SolrTemplateTests {
    @Resource
    private SolrTemplate solrTemplate;

    /**
     * 采用 solrTemplate 查询
     * @author eko.zhan at 2018年8月7日 下午7:30:37
     * @return
     */
    public Page<SolrNotice> query(String keyword){
        Query query = new SimpleQuery(new SimpleStringCriteria("title_cn:" + keyword));
        Page<SolrNotice> page = solrTemplate.query("notice", query, SolrNotice.class);
        return page;
    }

    @Test
    public void test(){
        List<SolrInputDocument> list = new ArrayList<>();
        solrTemplate.saveDocuments("test", list);
    }
}
