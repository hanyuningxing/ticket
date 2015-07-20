package com.cai310.lottery.ticket.protocol.zunao.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.MD5;
import com.google.common.collect.Lists;

public class ZunaoUtil {
//  尊傲测试帐号
    private static final String reUrl="http://121.12.168.124:661/ticketinterface.aspx";
    private static String transcode="1000";
    private static String msg="msg";
    private static String partnerid = "349022";
    private static String key ="123456";
    
	
	
	/**
	 * 发送彩票
	 * @param ticket
	 * @return
	 * @throws DataException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public List<CpResultVisitor> sendTicket(List<Ticket> ticketList) throws DataException, IOException, DocumentException{
		transcode="002" ;
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
		wParamValueSb.append("<msg>"); 
		wParamValueSb.append("<head transcode=\"002\" partnerid=\""+partnerid+"\" version=\"1.0\" time=\""+System.currentTimeMillis()+"\"/>"); 
		wParamValueSb.append("<body>"); 
		Ticket ticketTemp = null;
		Integer ticketsnum = 0;
		Integer totalmoney = 0;
		StringBuffer ticketXML = new StringBuffer();
		for (Ticket ticket : ticketList) {
			try{
				ticketTemp=ticket;
				ticketsnum++;
				totalmoney = totalmoney+ticket.getSchemeCost();
				ticketXML.append(this.getLotCode(ticket));
			}catch(Exception e){
				System.out.print(e);
				continue;
			}
		}
		wParamValueSb.append("<ticketorder lotteryId =\""+getZunaoLotteryId(ticketTemp)+"\" ticketsnum=\""+ticketsnum+"\" totalmoney=\""+totalmoney+"\">"); 
		wParamValueSb.append("<tickets>"); 
		wParamValueSb.append(ticketXML.toString());
		wParamValueSb.append("</tickets>"); 
		wParamValueSb.append("</ticketorder>"); 
		wParamValueSb.append("</body>"); 
	    wParamValueSb.append("</msg>"); 
	    msg = wParamValueSb.toString();
		String postData="transcode="+transcode+"&msg="+msg+"&key="+SecurityUtil.md5((transcode+msg+key).getBytes("UTF-8")).toLowerCase()+"&partnerid="+partnerid;
	    String returnString = Utf8HttpClientUtils(reUrl,postData);
	    List<CpResultVisitor> cpResultVisitorList = Lists.newArrayList();
	    if(StringUtils.isNotBlank(returnString)){
	    	Integer start=returnString.indexOf("msg=")+"msg=".length();
	    	Integer end=returnString.indexOf("&key=");
	    	returnString = returnString.substring(start,end);
			Document doc=DocumentHelper.parseText(returnString);
			Element root = doc.getRootElement();
			Element ticket;
			Element body = root.element("body");
			Element tickets = body.element("tickets");
			for (Iterator i = tickets.elementIterator("ticket"); i.hasNext();) {
					ticket = (Element) i.next();
					CpResultVisitor cpResultVisitor=new CpResultVisitor();
					ticket.accept(cpResultVisitor);
					cpResultVisitorList.add(cpResultVisitor);
			}
	    }
		return cpResultVisitorList;
	}
	public List<QueryPVisitor> confirmTicket(List<Long> ticketList) throws DataException, IOException, DocumentException{
		transcode="003" ;
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
		wParamValueSb.append("<msg>"); 
		wParamValueSb.append("<head transcode=\"003\" partnerid=\""+partnerid+"\" version=\"1.0\" time=\""+System.currentTimeMillis()+"\" />"); 
		wParamValueSb.append("<body>"); 
		for (Long id : ticketList) {
			wParamValueSb.append("<queryticket   palmId=\"\"  ticketId=\""+id+"\"/>"); 
		}
		wParamValueSb.append("</body>"); 
	    wParamValueSb.append("</msg>"); 
	    msg = wParamValueSb.toString();
		String postData="transcode="+transcode+"&msg="+msg+"&key="+MD5.md5(transcode+msg+key).toLowerCase()+"&partnerid="+partnerid;
	    String returnString = Utf8HttpClientUtils(reUrl,postData);

		List<QueryPVisitor> returnTicketList = Lists.newArrayList();
	    if(StringUtils.isNotBlank(returnString)){
	    	Integer start=returnString.indexOf("msg=")+"msg=".length();
	    	Integer end=returnString.indexOf("&key=");
	    	returnString = returnString.substring(start,end);

			Document doc=DocumentHelper.parseText(returnString);
			Element root = doc.getRootElement();
			Element ticket;
			QueryPVisitor queryPVisitor;
			Element body = root.element("body");
			for (Iterator i = body.elementIterator("ticketresult"); i.hasNext();) {
					ticket = (Element) i.next();
					queryPVisitor=new QueryPVisitor();
					ticket.accept(queryPVisitor);
					returnTicketList.add(queryPVisitor);
			}
	    }
		
		return returnTicketList;
	}
	public String buildPeqParamValue(String OrderId){
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("OrderId", ""+OrderId);
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
  	    return "<?xml version=\"1.0\"?>"+rootElement.asXML();
  	}
	public static void main(String[] args) throws DataException, IOException, DocumentException {
		String a = Utf8HttpClientUtils1("http://i.sporttery.cn/odds_calculator/get_odds","i_format=json&poolcode=hhad");
		int a22=1;
	}
	 public static String Utf8HttpClientUtils1(String url,String param){
	     String result="";
	     URL dataUrl=null;
	     HttpURLConnection httpConn =null;
	     PrintWriter out = null;
	     BufferedReader in = null;
	     try {
	    	 	dataUrl = new URL(url);
				httpConn = (HttpURLConnection) dataUrl.openConnection();
				httpConn.setDoOutput(true);
				httpConn.setDoInput(true);
				httpConn.setRequestMethod("GET");  
				httpConn.setRequestProperty("content-type",  "text/plain");
				// 设置连接超时时间3秒
				httpConn.setConnectTimeout(new Integer(10000));
				// 设置接收超时时间3秒
				httpConn.setReadTimeout(new Integer(10000));

				out = new PrintWriter(new OutputStreamWriter(httpConn.getOutputStream(),"utf-8"));
				out.print(param);
				out.flush();
				out.close();
				in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"utf-8"));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
				in.close();

		     }catch (Exception ex) {
		    	  dataUrl=null;
		    	  httpConn =null;
			      out = null;
			      in = null;
		     }finally{
		    	  dataUrl=null;
		    	  httpConn =null;
			      out = null;
			      in = null;
			 }   
		     return result;
	}
	public ZunaoQueryPVisitor getIssue(Lottery lottery,Byte betType) throws DataException, IOException, DocumentException{
		transcode="001" ;
		Ticket ticket = new Ticket();
		ticket.setBetType(betType);
		ticket.setLotteryType(lottery);
		StringBuffer wParamValueSb = new StringBuffer();
//		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
//		wParamValueSb.append("<msg>"); 
//		wParamValueSb.append("<head transcode=\"002\" partnerid=\"349022\" version=\"1.0\" time=\"20110628175541\" />"); 
//		wParamValueSb.append("<body>"); 
//		wParamValueSb.append("<ticketorder   lotteryId =\"SSQ\"  ticketsnum=\"1\"   totalmoney=\"2\">"); 
//		wParamValueSb.append("<tickets>"); 
//		wParamValueSb.append("<ticket ticketId=\"1023620\" betType=\"P1_1\"  issueNumber=\"2011062\" betUnits=\"1\" multiple=\"1\" betMoney =\"8\" isAppend =\"0\">"); 
//		wParamValueSb.append("<betContent>1:[,]/2:[,]</betContent>"); 
//		wParamValueSb.append("</ticket>"); 
//		wParamValueSb.append("</tickets>"); 
//		wParamValueSb.append("</ticketorder>"); 
//		wParamValueSb.append("</body>"); 
//	    wParamValueSb.append("</msg>"); 
		
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		wParamValueSb.append("<msg>");
		wParamValueSb.append("<head transcode=\""+transcode+"\"  partnerid=\""+partnerid+"\" version=\"1.0\" time=\"20110628175541\" />");
		wParamValueSb.append("<body>");
		wParamValueSb.append("<queryissue  lotteryId=\""+getZunaoLotteryId(ticket)+"\"  issueNumber=\"\"  />");
		wParamValueSb.append("</body>");
		wParamValueSb.append("</msg>");
		
		
		msg = wParamValueSb.toString();
		String postData="transcode="+transcode+"&msg="+msg+"&key="+MD5.md5(transcode+msg+key).toLowerCase()+"&partnerid="+partnerid;
	    String returnString = Utf8HttpClientUtils(reUrl,postData);
	    if(StringUtils.isNotBlank(returnString)){
	    	Integer start=returnString.indexOf("msg=")+"msg=".length();
	    	Integer end=returnString.indexOf("&key=");
	    	returnString = returnString.substring(start,end);
	    	Document doc=DocumentHelper.parseText(returnString);
	    	ZunaoQueryPVisitor queryPVisitor=new ZunaoQueryPVisitor();
			doc.accept(queryPVisitor);
			return queryPVisitor;
	    }
		return null;
	}
	 public static String Utf8HttpClientUtils(String url,String param){
	     String result="";
	     URL dataUrl=null;
	     HttpURLConnection httpConn =null;
	     PrintWriter out = null;
	     BufferedReader in = null;
	     try {
	    	 	dataUrl = new URL(url);
				httpConn = (HttpURLConnection) dataUrl.openConnection();
				httpConn.setDoOutput(true);
				httpConn.setDoInput(true);
				httpConn.setRequestMethod("POST");  
				httpConn.setRequestProperty("content-type",  "text/xml");
				// 设置连接超时时间3秒
				httpConn.setConnectTimeout(new Integer(10000));
				// 设置接收超时时间3秒
				httpConn.setReadTimeout(new Integer(10000));

				out = new PrintWriter(new OutputStreamWriter(httpConn.getOutputStream(),"utf-8"));
				out.print(param);
				out.flush();
				out.close();
				in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"utf-8"));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
				in.close();

		     }catch (Exception ex) {
		    	  dataUrl=null;
		    	  httpConn =null;
			      out = null;
			      in = null;
		     }finally{
		    	  dataUrl=null;
		    	  httpConn =null;
			      out = null;
			      in = null;
			 }   
		     return result;
	}
	
//	public CpResultVisitor getIssueAward(Lottery lottery,Byte betType,String issueNumber) throws DataException, IOException, DocumentException{
//		Ticket ticket = new Ticket();
//		ticket.setBetType(betType);
//		ticket.setLotteryType(lottery);
//		String LotID = getZunaoLotteryId(ticket);
//		StringBuffer wParamValueSb = new StringBuffer();
//		wParamValueSb.append("LotID="+LotID.trim());
//		wParamValueSb.append("_LotIssue="+issueNumber.trim());
//		String wParamValue=wParamValueSb.toString();
//		String wMsgIDValue = System.currentTimeMillis()+"";
//		StringBuffer sb = new StringBuffer();
//		String wActionValue = "110";
//		sb.append(reUrl);
//		
//		
//		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
//		ParamMap.put("wAgent", wAgentValue);
//		ParamMap.put("wAction", wActionValue);
//		ParamMap.put("wMsgID", wMsgIDValue);
//		ParamMap.put("wSign", MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
//		ParamMap.put("wParam", wParamValue);
		
		
//		sb.append("?");
//		sb.append(wAgent+"="+wAgentValue);
//		sb.append("&");
//		sb.append(wAction+"="+wActionValue);
//		sb.append("&");
//		sb.append(wMsgID+"="+wMsgIDValue);
//		sb.append("&");
//		sb.append(wSign+"="+MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
//		sb.append("&");
//		sb.append(wParam+"="+wParamValue);
//		String returnString = HttpclientUtil.Utf8HttpClientUtils(sb.toString(), ParamMap);
//		Document doc=DocumentHelper.parseText(returnString);
//		CpResultVisitor cpResultVisitor=new CpResultVisitor();
//		doc.accept(cpResultVisitor);
//		return cpResultVisitor;
//	}
	public String getMixPlayString(Ticket ticket){
		return null;
	}
	public String getLotCode(Ticket ticket) throws DataException{
		return null;
	}
	/*OrderID=D2010128298
	LotID=33
	LotIssue=2010008
	LotMoney=12
	LotMulti=2
	OneMoney=2
	LotCode=1|68,2,9;6|5,1,8
	Attach=投注测试*/
	public String getZunaoLotteryId(Ticket ticket){
		if(null==ticket)return null;
		if(null==ticket.getLotteryType())return null;
		if(Lottery.SSC.equals(ticket.getLotteryType())){
			return "ZQSSC";
		}else if(Lottery.JCZQ.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.jczq.PlayType playType =  com.cai310.lottery.support.jczq.PlayType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("playTypeOrdinal")))];
			com.cai310.lottery.support.jczq.PassType passType =  com.cai310.lottery.support.jczq.PassType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("passTypeOrdinal")))];
			switch (playType) {
			case SPF:
				return "JCBRQSPF";
			case JQS:
				return "JCJQS";
			case BF:
				return "JCBF";
			case BQQ:
				return "JCBQC";
			case MIX:
				return getMixPlayString(ticket);
			default:
				throw new RuntimeException("玩法不正确.");
			}
		}else if(Lottery.JCLQ.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.jclq.PlayType playType =  com.cai310.lottery.support.jclq.PlayType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("playTypeOrdinal")))];
			switch (playType) {
			case SF:
				return "JCSF";
			case RFSF:
				return "JCRFSF";
			case SFC:
				return "JCFC";
			case DXF:
				return "JCDXF";
			case MIX:
				return getMixPlayString(ticket);
			default:
				throw new RuntimeException("玩法不正确.");
			}
		}else if(Lottery.WELFARE3D.equals(ticket.getLotteryType())){
			return "3D";
		}else if(Lottery.DLT.equals(ticket.getLotteryType())){
			return "DLT";
		}else if(Lottery.SSQ.equals(ticket.getLotteryType())){
			return "SSQ";
		}else if(Lottery.SFZC.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.zc.PlayType playType = com.cai310.lottery.support.zc.PlayType.values()[ticket.getBetType()];
			if(playType.equals(com.cai310.lottery.support.zc.PlayType.SFZC14)){
				return "14CSF";
			}else if(playType.equals(com.cai310.lottery.support.zc.PlayType.SFZC9)){
				return "SFR9";
			}
		}else if(Lottery.SCZC.equals(ticket.getLotteryType())){
			return "4CJQ";
		}else if(Lottery.LCZC.equals(ticket.getLotteryType())){
			return "6CBQ";
		}else if(Lottery.SDEL11TO5.equals(ticket.getLotteryType())){
			return "SD11X5";
		}else if(Lottery.EL11TO5.equals(ticket.getLotteryType())){
			return "23009";
		}else if(Lottery.PL.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.pl.PlPlayType playType = com.cai310.lottery.support.pl.PlPlayType.values()[ticket.getBetType()];
			if(playType.equals(com.cai310.lottery.support.pl.PlPlayType.P5Direct)){
				return "PL5";
			}else{
				return "PL3";
			}
		}else if(Lottery.SEVEN.equals(ticket.getLotteryType())){
			return "QLC";
		}else if(Lottery.SEVENSTAR.equals(ticket.getLotteryType())){
			return "QXC";
		}else if(Lottery.DCZC.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.dczc.PlayType playType = com.cai310.lottery.support.dczc.PlayType.values()[ticket.getBetType()];
			if(PlayType.SPF.equals(playType)){
				return "SPF";
			}else if(PlayType.ZJQS.equals(playType)){
				return "JQS";
			}else if(PlayType.BF.equals(playType)){
				return "BF";
			}else if(PlayType.BQQSPF.equals(playType)){
				return "BQC";
			}else if(PlayType.SXDS.equals(playType)){
				return "SXDS";
			}
		}else if(Lottery.TC22TO5.equals(ticket.getLotteryType())){
			return "22X5";
		}
		return null;
	}
	
	protected static Map<String, Object> map;
	public ZunaoUtil(Ticket ticket){
		if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(null!=ticket&&null!=ticket.getContent())map = JsonUtil.getMap4Json(ticket.getContent());
		}
	}
	public ZunaoUtil(){
		
	}
	public static Map<String, Object> getTicketContentMap(Ticket ticket){
		if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(null!=ticket&&null!=ticket.getContent())map = JsonUtil.getMap4Json(ticket.getContent());
		}
		return map;
	}
	protected List<String> formatBetNum(List<String> numList,NumberFormat nf){
		List<String> newNumList = Lists.newArrayList();
		for (String num : numList) {
			newNumList.add(nf.format(Integer.valueOf(num)));
		}
		return newNumList;
	}
	public String getMixPlayString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
