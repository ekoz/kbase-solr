/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年8月9日 上午9:48:43
 * @version 1.0
 */
@Component
@Getter
@Setter
public class Dictionary {
	
	private final static String TYPE_SFTP = "SFTP";
	private final static String TYPE_FTP = "FTP";
	
	
	@Value("${dictionary.type}")
	private String type;
	@Value("${dictionary.folder}")
	private String folder;
	@Value("${dictionary.host}")
	private String host;
	@Value("${dictionary.port}")
	private Integer port;
	@Value("${dictionary.username}")
	private String username;
	@Value("${dictionary.password}")
	private String password;
	
	/**
	 * 是否是本地词典
	 * @author eko.zhan at 2018年8月9日 上午10:34:07
	 * @return
	 */
	public Boolean isLocal() {
		return StringUtils.isBlank(getType());
	}
	/**
	 * 是否是 sftp 协议上的词典
	 * @author eko.zhan at 2018年8月9日 上午10:36:18
	 * @return
	 */
	public Boolean isSftp() {
		return TYPE_SFTP.equalsIgnoreCase(getType());
	}
	/**
	 * 是否是 ftp 协议上的词典
	 * @author eko.zhan at 2018年8月9日 上午10:36:31
	 * @return
	 */
	public Boolean isFtp() {
		return TYPE_FTP.equalsIgnoreCase(getType());
	}
	
}
