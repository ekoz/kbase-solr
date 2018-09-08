/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.util;
/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年8月26日 上午9:01:55
 * @version 1.0
 */

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.NamedResource;
import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.common.util.GenericUtils;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.AsyncAuthException;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.auth.pubkey.AcceptAllPublickeyAuthenticator;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.forward.AcceptAllForwardingFilter;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.scp.ScpCommandFactory;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.shell.InteractiveProcessShellFactory;
import org.apache.sshd.server.shell.ProcessShellCommandFactory;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;

public class SshdServer {

	public static void main(String[] args) throws IOException, InterruptedException {
		SshServer sshd = SshServer.setUpDefaultServer();
		sshd.setPort(22);
		sshd.setShellFactory(InteractiveProcessShellFactory.INSTANCE);
		sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(Paths.get("hostkey.ser"))); //运行起来后 hostkey.ser 会自动生成
		sshd.setPasswordAuthenticator(new PasswordAuthenticator() {
			@Override
			public boolean authenticate(String username, String password, ServerSession session) throws PasswordChangeRequiredException, AsyncAuthException {
				if ("admin".equals(username) && "admin".equals(password)) {
					return true;
				}
				return false;
			}
		});
		sshd.setPublickeyAuthenticator(AcceptAllPublickeyAuthenticator.INSTANCE);
		sshd.setForwardingFilter(AcceptAllForwardingFilter.INSTANCE);
		sshd.setCommandFactory(new ScpCommandFactory.Builder()
	            .withDelegate(ProcessShellCommandFactory.INSTANCE)
	            .build());
		
		//如果不设置 subsystems ，用 winscp 访问的时候会提示 无法初始化SFTP协议。主机是SFTP服务器吗
		List<NamedFactory<Command>> subsystems = new ArrayList<>();
		subsystems.add(new SftpSubsystemFactory.Builder().build());
        if (GenericUtils.isNotEmpty(subsystems)) {
            System.out.append("Setup subsystems=").println(NamedResource.getNames(subsystems));
            sshd.setSubsystemFactories(subsystems);
        }
        
        //设置虚拟根路径
//		VirtualFileSystemFactory fileSystemFactory = new VirtualFileSystemFactory(Paths.get("E:\\lucene\\sshd\\apache-sshd-2.0.0\\res\\home"));
        VirtualFileSystemFactory fileSystemFactory = new VirtualFileSystemFactory(Paths.get("E:\\AI\\NLP\\solr"));
		sshd.setFileSystemFactory(fileSystemFactory);
		
		sshd.start();
		Thread.sleep(Long.MAX_VALUE);
	}
}
