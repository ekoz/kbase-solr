/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.service;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.CoreAdminResponse;

import java.io.IOException;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @version 1.0
 * @date 2019/3/6 11:14
 */
public interface SolrCoreAdminService {

    /**
     * 根据指定的 colName 创建 collection
     * @author eko.zhan
     * @date 2019/3/6 11:28
     * @param colName
     * @return org.apache.solr.client.solrj.response.CoreAdminResponse
     */
    CoreAdminResponse create(String colName) throws IOException, SolrServerException;

    /**
     * 卸载指定的 colName collection
     * @author eko.zhan
     * @date 2019/3/6 11:30
     * @param colName
     * @return org.apache.solr.client.solrj.response.CoreAdminResponse
     */
    CoreAdminResponse unload(String colName) throws IOException, SolrServerException;
}
