/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private static FileSystemManager manager = null;
	private static String stopwordsFilepath = "";
	private static String synonymsFilepath = "";
	private static DefaultFileMonitor fileMonitor;
	
	@PostConstruct
	public void init() {
		try {
			manager = VFS.getManager();
			String folder = dictionary.getFolder();
			folder = folder.replaceAll("\\\\", "/");
			if (!folder.endsWith("/")) folder = folder + "/";
			synonymsFilepath = folder + FILENAME_SYNONYMS;
			stopwordsFilepath = folder + FILENAME_STOPWORDS;
			
			FileObject folderObject = manager.resolveFile(folder);
			
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
						//TODO 为什么被修改了的提示语会打印多次？devtools每次重启会增加一次打印，原因待查
						log.debug("文件 [" + fileObject.getName() + "] 被修改了");
						//TODO 如果文件被修改，则要重新 reload notice collection
					}
				});
				fileMonitor.setRecursive(false);
				fileMonitor.addFile(folderObject);
			}
			fileMonitor.start(); 
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
		if (dictionary.isLocal()) {
			FileObject fileObject = manager.resolveFile(stopwordsFilepath);
			return IOUtils.toString(fileObject.getContent().getInputStream(), Charsets.UTF_8);
		}
		return null;
	}

	@Override
	public void saveStopwords(String words) throws IOException {
		if (dictionary.isLocal()) {
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
			}
		}
	}

	@Override
	public void loadSynonyms() {
		
	}

	@Override
	public void saveSynonyms(String words) {
		
	}
	
	

}
