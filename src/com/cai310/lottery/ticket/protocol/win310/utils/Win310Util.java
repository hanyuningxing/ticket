package com.cai310.lottery.ticket.protocol.win310.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.MD5;
import com.google.common.collect.Lists;

public abstract class Win310Util {
//	 private static final String reUrl="http://print.wincai.com/receiveTicket/receiveTicket2.jsp";
//	 private static String msg="msg";
//	 private static String partnerid = "179";
//	 private static String key ="7t48g5r9";
	private static final String reUrl = "http://61.143.211.102:18088/receiveTicket/receiveTicket2.jsp";
	private static String msg = "msg";
	private static String partnerid = "182";
	private static String key = "8s5s2s8w3";
	 protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 发送彩票
	 * 
	 * @param ticket
	 * @return
	 * @throws DataException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public List<CpResultVisitor> sendTicket(List<Ticket> ticketList) throws DataException, IOException, DocumentException {
		String transcode = "0001";
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		wParamValueSb.append("<msg>");
		wParamValueSb.append("<head transcode=\"" + transcode + "\" partnerid=\"" + partnerid + "\" version=\"1.0\" time=\"" + System.currentTimeMillis() + "\"/>");
		wParamValueSb.append("<body>");
		Ticket ticketTemp = null;
		Integer ticketsnum = 0;
		Integer totalmoney = 0;
		StringBuffer ticketXML = new StringBuffer();
		for (Ticket ticket : ticketList) {
			try {
				// 判断
				if (!ticket.getLotteryType().getCategory().equals(LotteryCategory.JC)) {
					QueryPVisitor queryPVisitor = this.getIssue(ticket.getLotteryType(), ticket.getBetType(),ticket.getPeriodNumber());
					if (null == queryPVisitor || null == queryPVisitor.getIssueNumber() || !queryPVisitor.getIssueNumber().equalsIgnoreCase(ticket.getPeriodNumber()))
						continue;
					
				}
				ticketTemp = ticket;
				ticketsnum++;
				totalmoney = totalmoney + ticket.getSchemeCost();
				ticketXML.append(this.getLotCode(ticket));
			} catch (Exception e) {
				System.out.print(e);
				continue;
			}
		}
		List<CpResultVisitor> cpResultVisitorList = Lists.newArrayList();
		if (ticketsnum == 0)
			return cpResultVisitorList;
		wParamValueSb.append("<ticketorder platformId =\"" + getWin310LotteryId(ticketTemp) + "\" ticketsnum=\"" + ticketsnum + "\" totalmoney=\"" + totalmoney + "\">");
		wParamValueSb.append("<tickets>");
		wParamValueSb.append(ticketXML.toString());
		wParamValueSb.append("</tickets>");
		wParamValueSb.append("</ticketorder>");
		wParamValueSb.append("</body>");
		wParamValueSb.append("</msg>");
		msg = wParamValueSb.toString();
		String keyValue = SecurityUtil.md5((transcode + msg + key).getBytes("UTF-8")).toLowerCase();
		msg = URLEncoder.encode(msg,"UTF-8");
		String postData = "transcode=" + transcode + "&msg=" + msg + "&key=" + keyValue + "&partnerid=" + partnerid;
			
		String returnString = Utf8HttpClientUtils(reUrl, postData);
		logger.error("热点发送"+postData);
		logger.error("热点返回"+returnString);
		if (StringUtils.isNotBlank(returnString)) {
			Document doc = DocumentHelper.parseText(returnString);
			Element root = doc.getRootElement();
			Element ticket;
			Element body = root.element("body");
			Element ticketorder = body.element("ticketorder");
			Element tickets = ticketorder.element("tickets");
			
			for (Iterator i = tickets.elementIterator("ticket"); i.hasNext();) {
				ticket = (Element) i.next();
				CpResultVisitor cpResultVisitor = new CpResultVisitor();
				ticket.accept(cpResultVisitor);
				cpResultVisitorList.add(cpResultVisitor);
			}
		}
		return cpResultVisitorList;
	}

	public List<QueryPVisitor> confirmTicket(List<Ticket> ticketList,Lottery lottery) throws DataException, IOException, DocumentException {
		String transcode = "0002";
		Ticket ticketTemp = new Ticket();
		StringBuffer orderId = new StringBuffer();
		String issueNumber="";
		for (Ticket ticket : ticketList) {
			orderId.append(ticket.getId()+",");
			ticketTemp=ticket;
			if(!ticket.getLotteryType().getCategory().equals(LotteryCategory.JC)){
				issueNumber=ticket.getPeriodNumber();
			}
		}
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		wParamValueSb.append("<msg>");
		wParamValueSb.append("<head transcode=\"0002\" partnerid=\"" + partnerid + "\" version=\"1.0\" time=\"" + System.currentTimeMillis() + "\" />");
		wParamValueSb.append("<body>");
		wParamValueSb.append("<queryticket issueNumber=\""+issueNumber+"\" platformId=\"" + getWin310LotteryId(ticketTemp) + "\">");
		wParamValueSb.append("<tickets>"+orderId.delete(orderId.length()-1,orderId.length()).toString()+"</tickets>");
		wParamValueSb.append("</queryticket>");
		wParamValueSb.append("</body>");
		wParamValueSb.append("</msg>");
		msg = wParamValueSb.toString();
		String postData = "transcode=" + transcode + "&msg=" + msg + "&key=" + MD5.md5(transcode + msg + key).toLowerCase() + "&partnerid=" + partnerid;
		String returnString = Utf8HttpClientUtils(reUrl, postData);
		logger.error("热点返回"+returnString);
		List<QueryPVisitor> returnTicketList = Lists.newArrayList();
		if (StringUtils.isNotBlank(returnString)) {
			Document doc = DocumentHelper.parseText(returnString);
			Element root = doc.getRootElement();
			Element ticket;
			QueryPVisitor queryPVisitor;
			Element body = root.element("body");
			Element ticketreport = body.element("ticketreport");
			
			for (Iterator i = ticketreport.elementIterator("ticketresult"); i.hasNext();) {
				ticket = (Element) i.next();
				queryPVisitor = new QueryPVisitor();
				ticket.accept(queryPVisitor);
				returnTicketList.add(queryPVisitor);
			}
		}

		return returnTicketList;
	}

	public String buildPeqParamValue(String OrderId) {
		Map<String, String> ParamMap = new LinkedHashMap<String, String>();
		ParamMap.put("OrderId", "" + OrderId);
		Document document = DocumentHelper.createDocument();
		/** 建立XML文档的根 */
		Element rootElement = document.addElement("ReqParam");
		rootElement.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		rootElement.addAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		Element ticketElement = rootElement.addElement("ticket");
		for (String key : ParamMap.keySet()) {
			String value = ParamMap.get(key);
			Element element = ticketElement.addElement(key);
			element.setText(value);
		}
		return "<?xml version=\"1.0\"?>" + rootElement.asXML();
	}

	public static void main(String[] args) throws DataException, IOException, DocumentException {
		String transcode = "0001";
		String msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><head transcode=\"0001\" partnerid=\"179\" version=\"1.0\" time=\"1337772209062\" /><body><ticketorder   lotteryId =\"SPF\"  ticketsnum=\"1\"   totalmoney=\"2\"><tickets><ticket ticketId=\"325\" betType=\"P2_1\"  issueNumber=\"120515\" betUnits=\"1\" multiple=\"1\" betMoney =\"2\" isAppend =\"0\"><betContent>3:[胜]/4:[平]</betContent></ticket></tickets></ticketorder></body></msg>";

		System.out.println(SecurityUtil.md5(transcode + msg + key));
		Map<String, String> ParamMap1 = new LinkedHashMap<String, String>();
		ParamMap1.put("transcode", transcode);
		ParamMap1.put("msg", msg);
		String param = transcode + msg + key;
		String pwd = SecurityUtil.md5(param.getBytes("UTF-8")).toLowerCase().trim();
		ParamMap1.put("key", pwd);
		String returnString = HttpclientUtil.Utf8HttpClientUtils(reUrl, ParamMap1);
		// /System.out.println(SecurityUtil.md5("你好"));
		// /System.out.println(MD5.md5("你好"));
		// System.out.print(MD5.md5(transcode+msg+key));
//		PlWin310Util plWin310Util = new PlWin310Util();
//		plWin310Util.getIssue(Lottery.SDEL11TO5, Byte.valueOf("0"));
	}

	public QueryPVisitor getIssue(Lottery lottery, Byte betType,String issueNumber) throws DataException, IOException, DocumentException {
		String transcode = "0000";
		Ticket ticket = new Ticket();
		ticket.setBetType(betType);
		ticket.setLotteryType(lottery);
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		wParamValueSb.append("<msg>");
		wParamValueSb.append("<head transcode=\"" + transcode + "\"  partnerid=\"" + partnerid + "\" version=\"1.0\" time=\"" + System.currentTimeMillis() + "\" />");
		wParamValueSb.append("<body>");
		wParamValueSb.append("<queryissue  platformId=\"" + getWin310LotteryId(ticket) + "\"  issueNumber=\"\"  />");
		wParamValueSb.append("</body>");
		wParamValueSb.append("</msg>");

		msg = wParamValueSb.toString();
		String postData = "transcode=" + transcode + "&msg=" + msg + "&key=" + SecurityUtil.md5((transcode + msg + key).getBytes("UTF-8")).toLowerCase() + "&partnerid=" + partnerid;
		String returnString = Utf8HttpClientUtils(reUrl, postData);
		if (StringUtils.isNotBlank(returnString)) {
			Integer start = returnString.indexOf("<m");
			returnString = returnString.substring(start);
			Document doc = DocumentHelper.parseText(returnString);
			QueryPVisitor queryPVisitor = new QueryPVisitor();
			doc.accept(queryPVisitor);
			return queryPVisitor;
		}
		
		return null;
	}

	public static String Utf8HttpClientUtils(String url, String param) {

		StringBuffer result = new StringBuffer();
		BufferedReader reader = null;
		DefaultHttpClient httpclient = null;
		HttpPost httppost = null;
		try {
			BasicHttpParams parms = new BasicHttpParams();
			parms.setParameter("charset", HTTP.UTF_8);
			parms.setParameter(HttpConnectionParams.SO_TIMEOUT, 200000);
			httpclient = new DefaultHttpClient(parms);
			// 目标地址
			httppost = new HttpPost(url);
			httppost.addHeader("charset", HTTP.UTF_8);
			// 构造最简单的字符串数据
			StringEntity reqEntity = new StringEntity(param, HTTP.UTF_8);
			// 设置类型
			reqEntity.setContentType("application/x-www-form-urlencoded");
			// 设置请求的数据
			httppost.setEntity(reqEntity);
			// 执行
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			// 显示结果
			reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			if (entity != null) {
				entity.consumeContent();
			}
			reader.close();
			httppost.abort();
		} catch (Exception ex) {
			reader = null;
			httpclient = null;
			httppost = null;
		} finally {
			reader = null;
			httpclient = null;
			httppost = null;
		}
		return result.toString();
	}

	// public CpResultVisitor getIssueAward(Lottery lottery,Byte betType,String
	// issueNumber) throws DataException, IOException, DocumentException{
	// Ticket ticket = new Ticket();
	// ticket.setBetType(betType);
	// ticket.setLotteryType(lottery);
	// String LotID = getWin310LotteryId(ticket);
	// StringBuffer wParamValueSb = new StringBuffer();
	// wParamValueSb.append("LotID="+LotID.trim());
	// wParamValueSb.append("_LotIssue="+issueNumber.trim());
	// String wParamValue=wParamValueSb.toString();
	// String wMsgIDValue = System.currentTimeMillis()+"";
	// StringBuffer sb = new StringBuffer();
	// String wActionValue = "110";
	// sb.append(reUrl);
	//
	//
	// Map<String,String> ParamMap=new LinkedHashMap<String,String>();
	// ParamMap.put("wAgent", wAgentValue);
	// ParamMap.put("wAction", wActionValue);
	// ParamMap.put("wMsgID", wMsgIDValue);
	// ParamMap.put("wSign",
	// MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
	// ParamMap.put("wParam", wParamValue);

	// sb.append("?");
	// sb.append(wAgent+"="+wAgentValue);
	// sb.append("&");
	// sb.append(wAction+"="+wActionValue);
	// sb.append("&");
	// sb.append(wMsgID+"="+wMsgIDValue);
	// sb.append("&");
	// sb.append(wSign+"="+MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
	// sb.append("&");
	// sb.append(wParam+"="+wParamValue);
	// String returnString = HttpclientUtil.Utf8HttpClientUtils(sb.toString(),
	// ParamMap);
	// Document doc=DocumentHelper.parseText(returnString);
	// CpResultVisitor cpResultVisitor=new CpResultVisitor();
	// doc.accept(cpResultVisitor);
	// return cpResultVisitor;
	// }
	public String getMixPlayString(Ticket ticket) {
		return null;
	}

	public abstract String getLotCode(Ticket ticket) throws DataException;

	/*
	 * OrderID=D2010128298 LotID=33 LotIssue=2010008 LotMoney=12 LotMulti=2
	 * OneMoney=2 LotCode=1|68,2,9;6|5,1,8 Attach=投注测试
	 */
	public String getWin310LotteryId(Ticket ticket) {
		if (null == ticket)
			return null;
		if (null == ticket.getLotteryType())
			return null;
		if (Lottery.SSC.equals(ticket.getLotteryType())) {
			return "ZQSSC";
		} else if (Lottery.JCZQ.equals(ticket.getLotteryType())) {
			return "30";
		} else if (Lottery.JCLQ.equals(ticket.getLotteryType())) {
			return "91";
		} else if (Lottery.WELFARE3D.equals(ticket.getLotteryType())) {
			return "3D";
		} else if (Lottery.DLT.equals(ticket.getLotteryType())) {
			return "DLT";
		} else if (Lottery.SSQ.equals(ticket.getLotteryType())) {
			return "SSQ";
		} else if (Lottery.SFZC.equals(ticket.getLotteryType())) {
			com.cai310.lottery.support.zc.PlayType playType = com.cai310.lottery.support.zc.PlayType.values()[ticket.getBetType()];
			if (playType.equals(com.cai310.lottery.support.zc.PlayType.SFZC14)) {
				return "1";
			} else if (playType.equals(com.cai310.lottery.support.zc.PlayType.SFZC9)) {
				return "3";
			}
		} else if (Lottery.SCZC.equals(ticket.getLotteryType())) {
			return "56";
		} else if (Lottery.LCZC.equals(ticket.getLotteryType())) {
			return "50";
		} else if (Lottery.SDEL11TO5.equals(ticket.getLotteryType())) {
			return "SD11X5";
		} else if (Lottery.EL11TO5.equals(ticket.getLotteryType())) {
			return "23009";
		} else if (Lottery.PL.equals(ticket.getLotteryType())) {
			com.cai310.lottery.support.pl.PlPlayType playType = com.cai310.lottery.support.pl.PlPlayType.values()[ticket.getBetType()];
			if (playType.equals(com.cai310.lottery.support.pl.PlPlayType.P5Direct)) {
				return "PL5";
			} else {
				return "Pl3";
			}
		} else if (Lottery.SEVEN.equals(ticket.getLotteryType())) {
			return "QLC";
		} else if (Lottery.SEVENSTAR.equals(ticket.getLotteryType())) {
			return "QXC";
		} else if (Lottery.DCZC.equals(ticket.getLotteryType())) {
			com.cai310.lottery.support.dczc.PlayType playType = com.cai310.lottery.support.dczc.PlayType.values()[ticket.getBetType()];
			if (PlayType.SPF.equals(playType)) {
				return "20";
			} else if (PlayType.ZJQS.equals(playType)) {
				return "22";
			} else if (PlayType.BF.equals(playType)) {
				return "26";
			} else if (PlayType.BQQSPF.equals(playType)) {
				return "28";
			} else if (PlayType.SXDS.equals(playType)) {
				return "24";
			}
		} else if (Lottery.TC22TO5.equals(ticket.getLotteryType())) {
			return "22X5";
		}
		return null;
	}

	protected static Map<String, Object> map;

	public Win310Util(Ticket ticket) {
		if (SalesMode.COMPOUND.equals(ticket.getMode())) {
			if (null != ticket && null != ticket.getContent())
				map = JsonUtil.getMap4Json(ticket.getContent());
		}
	}

	public Win310Util() {

	}

	public static Map<String, Object> getTicketContentMap(Ticket ticket) {
			if (null != ticket && null != ticket.getContent())
				map = JsonUtil.getMap4Json(ticket.getContent());
			return map;
	}

	protected List<String> formatBetNum(List<String> numList, NumberFormat nf) {
		List<String> newNumList = Lists.newArrayList();
		for (String num : numList) {
			newNumList.add(nf.format(Integer.valueOf(num)));
		}
		return newNumList;
	}

	public String getMixPlayString() {
		return null;
	}

}