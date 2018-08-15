/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.service;

import java.io.IOException;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年8月9日 上午9:25:33
 * @version 1.0
 */
public interface DictionaryService {
	
	public final static String TYPE_SYNONYMS = "SYNONYMS";
	public final static String TYPE_STOPWORDS = "STOPWORDS"; 

	String loadStopwords() throws IOException;
	
	void saveStopwords(String words) throws IOException;
	
	void loadSynonyms() throws IOException;
	
	void saveSynonyms(String words) throws IOException;
}
