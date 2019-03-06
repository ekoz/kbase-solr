/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.common.params.CoreAdminParams;
import org.apache.solr.common.util.NamedList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年4月23日 上午10:44:07
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CoreAdminRequestTests {
	
	@Autowired
	private SolrClient solrClient;
	String sampleColName = "sample";

	private Boolean checkIfExist(String colName) throws IOException, SolrServerException {
        CoreAdminResponse coreNameResponse = CoreAdminRequest.getStatus(colName, solrClient);
        return coreNameResponse.getCoreStatus(colName).get("instanceDir")!=null;
    }


	@Test
	public void testCreateCollection() throws IOException, SolrServerException {
	    String colName = "test";
	    if (!checkIfExist(colName)){
            CoreAdminResponse coreAdminResponse = CoreAdminRequest.getStatus(sampleColName, solrClient);
            NamedList<Object> sampleNamedList = coreAdminResponse.getCoreStatus(sampleColName);
            String instanceDir = (String)sampleNamedList.get("instanceDir"); //E:\Lucene\solr-5.5.3\server\solr\sample
            String baseDir = instanceDir.substring(0, instanceDir.lastIndexOf(sampleColName)); //E:\Lucene\solr-5.5.3\server\solr\

            Path colPath = Paths.get(baseDir, colName, "data");
            File file = colPath.toFile();
            if (!file.exists()){
                FileUtils.forceMkdir(file);
            }
            colPath = Paths.get(baseDir, colName);

            Path sampleConfigsPath = Paths.get(baseDir, "configsets", "sample_configs");
            for (File childFile : sampleConfigsPath.toFile().listFiles()){
                if (childFile.isDirectory()){
                    FileUtils.copyDirectoryToDirectory(childFile, colPath.toFile());
                }else{
                    FileUtils.copyFileToDirectory(childFile, colPath.toFile());
                }
            }

            CoreAdminRequest.createCore(colName, colName, solrClient);
        }
    }

	@Test
	public void testGetStatus(){
		CoreAdminRequest coreAdminRequest = new CoreAdminRequest();
		System.out.println(coreAdminRequest.getBasicAuthUser());
		System.out.println(coreAdminRequest.getPath());
		try {
			CoreAdminResponse coreAdminResponse = CoreAdminRequest.getStatus("notice", solrClient);
			System.out.println(coreAdminResponse.getStatus());
			NamedList<NamedList<Object>> coreStatus = coreAdminResponse.getCoreStatus();
			coreStatus.forEach(status -> {
				System.out.println(status.getKey() + "->" + status.getValue());
				status.getValue().forEach(val -> {
					System.out.println(val.getKey() + "  -->" + val.getValue());
				});
			});
			System.out.println(coreAdminResponse.getRequestUrl());
			CoreAdminRequest.reloadCore("notice", solrClient);
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReloadCore(){
		try {
			CoreAdminResponse coreAdminResponse = CoreAdminRequest.reloadCore("notice", solrClient);
			System.out.println(coreAdminResponse.getStatus());
			System.out.println(coreAdminResponse.getElapsedTime()); //14602
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}
}
