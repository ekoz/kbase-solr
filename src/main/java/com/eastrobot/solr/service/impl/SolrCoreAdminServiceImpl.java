/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.service.impl;

import com.eastrobot.solr.service.SolrCoreAdminService;
import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.common.util.NamedList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @version 1.0
 * @date 2019/3/6 11:14
 */
@Service
public class SolrCoreAdminServiceImpl implements SolrCoreAdminService {

    @Resource
    private SolrClient solrClient;
    private final String sampleColName = "sample";


    @Override
    public CoreAdminResponse create(String colName) throws IOException, SolrServerException {
        CoreAdminResponse coreAdminResponse = CoreAdminRequest.getStatus(sampleColName, solrClient);
        if (!checkIfExist(colName)){
            // 1、获取模板 sample 必须已存在
            NamedList<Object> sampleNamedList = coreAdminResponse.getCoreStatus(sampleColName);
            //E:\Lucene\solr-5.5.3\server\solr\sample
            String instanceDir = (String)sampleNamedList.get("instanceDir");
            //E:\Lucene\solr-5.5.3\server\solr\
            String baseDir = instanceDir.substring(0, instanceDir.lastIndexOf(sampleColName));

            // 2、创建 colName 路径
            Path colPath = Paths.get(baseDir, colName, "data");
            File file = colPath.toFile();
            if (!file.exists()){
                FileUtils.forceMkdir(file);
            }
            colPath = Paths.get(baseDir, colName);

            // 3、获取标准配置文件
            Path sampleConfigsPath = Paths.get(baseDir, "configsets", "sample_configs");
            for (File childFile : sampleConfigsPath.toFile().listFiles()){
                if (childFile.isDirectory()){
                    FileUtils.copyDirectoryToDirectory(childFile, colPath.toFile());
                }else{
                    FileUtils.copyFileToDirectory(childFile, colPath.toFile());
                }
            }

            // 4、创建 core
            return CoreAdminRequest.createCore(colName, colName, solrClient);
        }
        return coreAdminResponse;
    }

    @Override
    public CoreAdminResponse unload(String colName) throws IOException, SolrServerException {
        return CoreAdminRequest.unloadCore(colName, solrClient);
    }

    /**
     * 根据指定的 colName 判断是否已经存在
     * @author eko.zhan
     * @date 2019/3/6 11:17
     * @param colName
     * @return java.lang.Boolean
     */
    private Boolean checkIfExist(String colName) throws IOException, SolrServerException {
        CoreAdminResponse coreNameResponse = CoreAdminRequest.getStatus(colName, solrClient);
        return coreNameResponse.getCoreStatus(colName).get("instanceDir")!=null;
    }
}
