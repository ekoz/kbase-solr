/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月22日 下午5:04:06
 * @version 1.0
 */
@Document(collection="Notice")
@Getter
@Setter
public class Notice implements Serializable {

	@Id
	private String id;
	private String title;
	private String content;
	private String words;
	private Date createDate;
	private Date modifyDate;
	
}
