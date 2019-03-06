/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.service;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @version 1.0
 * @date 2019/3/6 11:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SolrCoreAdminServiceTests {

    @Resource
    SolrCoreAdminService solrCoreAdminService;

    @Test
    public void testCreate() throws IOException, SolrServerException {
        solrCoreAdminService.create("test2");
    }

    @Test
    public void testUnload() throws IOException, SolrServerException {
        solrCoreAdminService.unload("test");
    }
}
