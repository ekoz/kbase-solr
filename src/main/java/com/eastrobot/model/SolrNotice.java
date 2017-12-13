/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月22日 下午5:04:06
 * @version 1.0
 */
@SolrDocument(solrCoreName="notice")
@Getter
@Setter
public class SolrNotice implements Serializable {

	@Id
	@Indexed
	private String id;
	@Indexed(boost = 1.0f)
	private String title;
	@Indexed
	private String content;
	
	public SolrNotice(String id, String title, String content){
		this.id = id;
		this.title = title;
		this.content = content;
	}
	
}
