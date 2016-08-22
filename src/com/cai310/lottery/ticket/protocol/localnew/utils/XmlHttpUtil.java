package com.cai310.lottery.ticket.protocol.localnew.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class XmlHttpUtil {
	
	public static String url2String(String url, String data, String encoding, int timeout) throws Exception {
	    URL murl = new URL(url);
	    HttpURLConnection con = (HttpURLConnection)murl.openConnection();
	    con.setRequestProperty("accept", "*/*");
	    con.setRequestProperty("connection", "Keep-Alive");
	    con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
	    con.setRequestProperty("Accept-Charset", encoding);
	    con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
	    con.setRequestMethod("GET");
	    con.setConnectTimeout(1000 * timeout);
	    con.setReadTimeout(1000 * timeout);

	    DataOutputStream posts = null;
	    if (data != null && !data.equals("")) {
	      con.setDoInput(true);
	      con.setDoOutput(true);
	      con.setAllowUserInteraction(false);

	      posts = new DataOutputStream(con.getOutputStream());
	      //posts.writeUTF(data);
	      posts.write(data.getBytes(encoding));
	      //posts.writeBytes(data);
	      posts.flush();
	    }

	    ByteArrayOutputStream bout = new ByteArrayOutputStream();
	    InputStream in = con.getInputStream();
	    byte[] buf = new byte[4096];
	    int bytesRead;
	    while ((bytesRead = in.read(buf)) != -1) {
	      bout.write(buf, 0, bytesRead);
	    }

	    if (posts != null) {
	      posts.close();
	      posts = null;
	    }

	    in.close();
	    in = null;

	    con.disconnect();
	    con = null;

	    String s = "";
	    if (encoding != null && !encoding.equals(""))
	      s = new String(bout.toByteArray(), encoding);
	    else {
	      s = new String(bout.toByteArray());
	    }

	    bout.close();
	    bout = null;

	    return s.trim();
	  }
	
	public static Element url2Xml(String url, String data, String encoding, int timeout) throws Exception {
	    String s = url2String(url, data, encoding, timeout);
	    SAXBuilder builder = new SAXBuilder(false);
	    Reader rin = new StringReader(s.trim());
	    Document doc = builder.build(rin);
	    return doc.getRootElement();
	  }
	
	public static Element url2Xml(String url, String data, int timeout) throws Exception {
	    String s = sendPost(url, data, timeout);
	    SAXBuilder builder = new SAXBuilder(false);
	    Reader rin = new StringReader(s.trim());
	    Document doc = builder.build(rin);
	    return doc.getRootElement();
	  }
	
	public static String sendPost(String url, String params, int timeout) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setConnectTimeout(1000 * timeout);
    	    conn.setReadTimeout(1000 * timeout);
 
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(params);
            // flush输出流的缓冲
            out.flush();
 
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
	
	public static void main(String[] args){
		try {
			//XmlHttpUtil.url2String("http://127.0.0.1:8080/rs.aspx", "msg=111", "UTF-8", 10);
			XmlHttpUtil.sendPost("http://127.0.0.1:8080/rs.aspx","msg=111",10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
