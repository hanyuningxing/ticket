package com.cai310.lottery.utils;

import java.text.ParseException;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public final class PlayerHttpUtil {
	protected static final Logger logger = LoggerFactory.getLogger(PlayerHttpUtil.class);

	/**
	 * 读取远程页面的数据
	 * 
	 * @param url 远程页面URL
	 * @param encode 编码
	 * @return 远程页面的数据
	 */
	public static String getRemoteStr(String url, String encode) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpUriRequest httpReq = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(httpReq, responseHandler);
		} catch (Exception e) {
			logger.error("读取远程页面数据失败.URL="+url, e);
		}
		return null;
	}
	public static void main(String[] args) throws ParseException {
		for (int i = 1; i < 10; i++) {
			String content = getRemoteStr("http://pesdb.net/pes2014/index.php?sort=galactico&order=d&page="+i,"utf-8");
			TableTag tableTag = getTableTag(content,"utf-8");
			parser(tableTag);
		}
		int i = 0;
	}
	protected static TableTag getTableTag(String html, String charset) {
		Parser parser = Parser.createParser(html, charset);
		NodeFilter tableFilter = new NodeClassFilter(TableTag.class);
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			return null;
		}

		for (int i = 0; i <= nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof TableTag) {
				TableTag tag = (TableTag) nodeList.elementAt(i);
				if ("players".equals(tag.getAttribute("class"))) {
					return tag;
				}
			}
		}

		return null;
	}
	protected static List<Long> parser(TableTag tag) throws ParseException {
		TableRow[] rows = tag.getRows();
		int i = 0;
		List<Long> ids = Lists.newArrayList();
		for (TableRow row : rows) {
			TableColumn[] columns = row.getColumns();
			i++;
			if(i==1)continue;
			if(null!=columns){
				LinkTag linkTag = (org.htmlparser.tags.LinkTag) columns[0].getChild(0);
				String id = linkTag.getLink().split("id=")[1];
				ids.add(Long.valueOf(id));
				int t = 0;
			}
	//		
			int t = 0;
		}
		return null;
	}
}
