/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.util;
/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年8月8日 下午7:44:04
 * @version 1.0
 */

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.provider.local.LocalFile;
import org.junit.Test;

public class VfsTests {



	@Test
	public void testLocalFile() throws IOException {
		FileSystemManager manager = VFS.getManager();
		FileObject fileObject = manager.resolveFile("E:/AI/NLP/data/dictionary/stopwords.txt");
//		System.out.println(fileObject.getName());
//		System.out.println(fileObject.getType().getName());
		String content = IOUtils.toString(fileObject.getContent().getInputStream(), "UTF-8");
		System.out.println(content);
//		if (fileObject instanceof LocalFile) {
//			LocalFile localFile = (LocalFile) fileObject;
//			
//		}
	}
}
