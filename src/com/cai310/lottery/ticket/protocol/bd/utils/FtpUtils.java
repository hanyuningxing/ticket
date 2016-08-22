package com.cai310.lottery.ticket.protocol.bd.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpDirEntry;
import sun.net.ftp.FtpProtocolException;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * @describe 读取FTP上的文件
 * @date 2013-11-18 下午4:07:34
 */
public class FtpUtils {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	private FtpClient ftpClient;
	// 测试---------------
//	private String ip = "101.251.195.152"; // 服务器IP地址
//	private String userName = "zhuoer"; // 用户名
//	private String path = "/"; // 密码
//	private String userPwd = "123123"; // 密码
	// private int port = 21; // 端口号
	// 正式
	 private String ip = "59.151.41.184"; // 服务器IP地址
	 private String userName = "zhuoer"; // 用户名
	 private String path = "/award/40"; // 密码
	 private String userPwd = "TaDeR6J4"; // 密码
	 private int port = 21; // 端口号

	public FtpUtils() {
		connectServer(ip, userName, userPwd, path);
	}

	/**
	 * connectServer 连接ftp服务器
	 * 
	 * @throws java.io.IOException
	 * @param path
	 *            文件夹，空代表根目录
	 * @param password
	 *            密码
	 * @param user
	 *            登陆用户
	 * @param server
	 *            服务器地址
	 */
	public void connectServer(String server, String user, String password,
			String path) {

		try {
			ftpClient = FtpClient.create(server);
			ftpClient.login(user, password.toCharArray());
			if (path.length() != 0)
				ftpClient.changeDirectory(path);
			ftpClient.setBinaryType();
		} catch (FtpProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取得某个目录下的所有文件列表
	 * 
	 */
	public List getFileList(String path) {
		List list = new ArrayList();
		Iterator<FtpDirEntry> ftpFiles = null;
		try {
			System.out.println("------工作目录------"+ftpClient.getWorkingDirectory());
			ftpFiles = ftpClient.listFiles(this.path+path);
			while (ftpFiles.hasNext()) {
				FtpDirEntry ftpDirEntry = ftpFiles.next();
				list.add(ftpDirEntry.getName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * closeServer 断开与ftp服务器的链接
	 * 
	 * @throws java.io.IOException
	 */
	public void closeServer() throws IOException {
		try {
			if (ftpClient != null) {
				ftpClient.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param fileName
	 * @return function:从服务器上读取指定的文件
	 * @throws ParseException
	 * @throws IOException
	 */
	public String readFile(String fileName) throws ParseException {
		StringBuffer resultBuffer = new StringBuffer();
		InputStream in = null;
		logger.info("开始读取绝对路径" + fileName + "文件!");
		try {
			// ftpClient.changeWorkingDirectory(fileName);
			in = ftpClient.getFileStream(this.path+fileName);
		} catch (FileNotFoundException e) {
			logger.error("没有找到" + fileName + "文件");
			e.printStackTrace();
		} catch (SocketException e) {
			logger.error("连接FTP失败.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("文件读取错误。");
			e.printStackTrace();
		} catch (FtpProtocolException e) {
			e.printStackTrace();
		}
		if (in != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String data = null;
			try {
				while ((data = br.readLine()) != null) {
					resultBuffer.append(data + ",");
				}
			} catch (IOException e) {
				logger.error("文件读取错误。");
				e.printStackTrace();
			} finally {
				try {
					ftpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.error("in为空，不能读取。");
		}
		return resultBuffer.toString();
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) {
		FtpUtils ftp = new FtpUtils();
		List list = ftp.getFileList("200/21275/");
		CpResultVisitor cpResultVisitor = new CpResultVisitor();
		if (list.contains("200_21275_21.crc")
				&& list.contains("200_21275_21.txt")) {
			String str = ftp.readFile("200/21275/200_21275_21.txt");
			str.split(",");
			System.out.println(str.split(",").length);
		}
	}
}