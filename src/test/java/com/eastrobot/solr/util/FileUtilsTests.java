/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年8月15日 下午7:21:08
 * @version 1.0
 */
public class FileUtilsTests {

	/**
	 * 如何将空格的字符串保存文件中变成换行
	 * @author eko.zhan at 2018年8月15日 下午7:17:43
	 * @throws IOException 
	 */
	@Test
	public void saveFile() throws IOException {
		String content = "你 我 他 周杰伦 青花瓷 听妈妈的话 山水画";
		content = content.replaceAll(" ", "\n");
		File file = new File("E:/2018.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileUtils.writeStringToFile(file, content);
	}
}
