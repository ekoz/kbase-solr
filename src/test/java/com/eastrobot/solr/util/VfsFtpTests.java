/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.util;
/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年8月25日 下午4:40:28
 * @version 1.0
 */

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VfsFtpTests {

	
	@Test
	public void testCreate() throws IOException {
		FileSystemManager manager = VFS.getManager();
		FileObject fileObject = manager.resolveFile("ftp://admin:admin@127.0.0.1:2121/dict/ekoz.txt");
		IOUtils.write("ekoz", fileObject.getContent().getOutputStream());
		IOUtils.closeQuietly(fileObject);
	}
	
	@Before
	public void before() {
		System.out.println("开始执行");
	}
	
	@After
	public void after() {
		System.out.println("结束执行");
	}
}
