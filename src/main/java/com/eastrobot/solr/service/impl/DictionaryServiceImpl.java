/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.eastrobot.solr.service.DictionaryService;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.eastrobot.solr.config.Dictionary;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年8月9日 上午9:39:00
 * @version 1.0
 */
@Service
@Slf4j
public class DictionaryServiceImpl implements DictionaryService {

	private final static String FILENAME_SYNONYMS = "synonyms.txt";
	private final static String FILENAME_STOPWORDS = "stopwords.txt";
	
	@Autowired
	Dictionary dictionary;
	@Autowired
	private SolrClient solrClient;
	
	private static FileSystemManager manager = null;
	private static String stopwordsFilepath = "";
	private static String synonymsFilepath = "";
	private static DefaultFileMonitor fileMonitor;
	private static FileObject folderObject = null;
	
	@PostConstruct
	public void init() {
		try {
			manager = VFS.getManager();
			synonymsFilepath = dictionary.getBaseUrl() + "/" + FILENAME_SYNONYMS;
			stopwordsFilepath = dictionary.getBaseUrl() + "/" + FILENAME_STOPWORDS;
			folderObject = manager.resolveFile(dictionary.getBaseUrl());
			
			//启动监听器，监听 folder 下文件的变化
			if (fileMonitor==null) {
				log.debug("初始化 DefaultFileMonitor");
				fileMonitor = new DefaultFileMonitor(new FileListener() {
					@Override
					public void fileDeleted(FileChangeEvent event) throws Exception {
						FileObject fileObject = event.getFile();
						log.debug("文件 [" + fileObject.getName() + "] 被删除了");
					}
					@Override
					public void fileCreated(FileChangeEvent event) throws Exception {
						FileObject fileObject = event.getFile();
						log.debug("文件 [" + fileObject.getName() + "] 被创建了");
					}
					@Override
					public void fileChanged(FileChangeEvent event) throws Exception {
						FileObject fileObject = event.getFile();
						//TODO 为什么被修改了的提示语会打印多次？ devtools 每次重启会增加一次打印，原因待查
						log.debug("文件 [" + fileObject.getName() + "] 被修改了");
						//TODO 如果文件被修改，则要重新 reload notice collection
						CoreAdminRequest.reloadCore("notice", solrClient);
					}
				});
				fileMonitor.setRecursive(false);
				fileMonitor.addFile(folderObject);
			}
			fileMonitor.start(); 
			IOUtils.closeQuietly(folderObject);
		} catch (FileSystemException e) {
			e.printStackTrace();
		}
	}
	
	@PreDestroy
	public void destroy() {
		if (fileMonitor!=null) {
			fileMonitor.stop();
		}
		log.debug("destroy"); 
	}
	
	@Override
	public String loadStopwords() throws IOException {
		FileObject fileObject = manager.resolveFile(stopwordsFilepath);
		String content = IOUtils.toString(fileObject.getContent().getInputStream(), Charsets.UTF_8);
		IOUtils.closeQuietly(fileObject);
		return content;
	}

	@Override
	public void saveStopwords(String words) throws IOException {
		//空格替换成回车，去重处理，保留顺序
		String[] wordArr = words.trim().split(" ");
		List<String> list = Arrays.asList(wordArr).stream().distinct().collect(Collectors.toList());
		words = StringUtils.join(list, " ");
		words = words.trim().replaceAll(" ", "\n");
		FileObject fileObject = manager.resolveFile(stopwordsFilepath);
		OutputStream out = null;
		try {
			out = fileObject.getContent().getOutputStream();
			IOUtils.write(words, out, Charsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(fileObject);
		}
	}

	@Override
	public String loadSynonyms() throws IOException {
		FileObject fileObject = manager.resolveFile(synonymsFilepath);
		return IOUtils.toString(fileObject.getContent().getInputStream(), Charsets.UTF_8);
	}

	@Override
	public void saveSynonyms(String words) throws IOException {
		//空格替换成回车，去重处理，保留顺序
		String[] wordArr = words.trim().split(" ");
		List<String> list = Arrays.asList(wordArr).stream().distinct().collect(Collectors.toList());
		words = StringUtils.join(list, " ");
		words = words.trim().replaceAll(" ", "\n");
		FileObject fileObject = manager.resolveFile(synonymsFilepath);
		OutputStream out = null;
		try {
			out = fileObject.getContent().getOutputStream();
			IOUtils.write(words, out, Charsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
	
	

}
