/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年8月28日 下午2:38:28
 * @version 1.0
 */
@SolrDocument(solrCoreName="govmail")
@Getter
@Setter
public class SolrGovMail {

	@Id
	@Indexed
	private String id;
	@Indexed(name="summary_cn", boost = 1.0f)
	private String summary; //摘要
	@Indexed(name="sender_cn")
	private String sender; //来信人
	@Indexed(name="sendDate_dt")
	private Date sendDate; //来信时间
	@Indexed(name="receiveDate_dt")
	private Date receiveDate; //答复时间
	@Indexed(name="receiver_cn")
	private String receiver;	//承办单位
	@Indexed(name="sendContent_cn")
	private String sendContent;	//主要诉求
	@Indexed(name="content_cn")
	private String content;	//处理意见
}
