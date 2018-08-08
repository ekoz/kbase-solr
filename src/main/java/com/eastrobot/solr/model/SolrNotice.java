/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.hankcs.hanlp.HanLP;

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
	@Indexed(name="title_cn", boost = 1.0f)
	private String title;
	@Indexed(name="content_cn")
	private String content;
	@Indexed(name="createDate_dt")
	private Date createDate;
	@Indexed(name="modifyDate_dt")
	private Date modifyDate;
	@Indexed(name="content_pinyin_cn")
	private String contentPinyin;
	
	public SolrNotice(){}
	
	public SolrNotice(String id, String title, String content){
		this.id = id;
		this.title = title;
		this.content = content;
		this.contentPinyin = HanLP.convertToPinyinString(content, " ", false);
	}
	
	public SolrNotice(String id, String title, String content, Date createDate, Date modifyDate){
		this.id = id;
		this.title = title;
		this.content = content;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.contentPinyin = HanLP.convertToPinyinString(content, " ", false);
	}
	
}
