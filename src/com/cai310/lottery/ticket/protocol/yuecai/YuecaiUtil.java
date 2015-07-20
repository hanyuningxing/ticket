package com.cai310.lottery.ticket.protocol.yuecai;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpParams;
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

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.MD5;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public abstract class YuecaiUtil {
//  测试帐号
//    private static final String reUrl="http://113.31.87.102:6217/trade.aspx";
//    private static String transcode="101";
//    private static String msg="msg";
//    private static String ChannelID = "52";
//    private static String Key ="123456789";
	
    
    private static final String reUrl="http://121.9.245.212:6217/trade.aspx";
    private static String transcode="101";
    private static String msg="msg";
    private static String ChannelID = "54";
    private static String Key ="QK20130924";
    
    public abstract String BetContent(Ticket ticket) throws DataException;
    public abstract String WareIssue(Ticket ticket) throws DataException;
	/**
	 * 发送彩票
	 * @param ticket
	 * @return
	 * @throws DataException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public List<CpResultVisitor> sendTicket(List<Ticket> ticketList) throws DataException, IOException, DocumentException{
		
//		transcode="101" ;
//		String LotteryID = "5";
//		String WareID = "0";
//		String WareIssue = "30914";
//		String BatchID = ""+System.currentTimeMillis();
//		String AddFlag = "0";
//		Integer BetAmount = 6;
//		StringBuffer content = new StringBuffer();
//        content.append("2~[平]~0/4~[平]~0/6~[胜,平,负]~0$3串1~1~3@15|2~[平]~0/4~[平]~0/6~[胜,平,负]~0$3串1~1~3@16");
        transcode="101" ;
		String LotteryID = null;
		String WareID = "0";
		String WareIssue = null;
		String BatchID = ""+System.currentTimeMillis();
		String AddFlag = "0";
		Integer BetAmount = 0;
		StringBuffer content = new StringBuffer();
		List<CpResultVisitor> cpResultVisitorList = Lists.newArrayList();
		CpResultVisitor cpResultVisitor = null;
        for (Ticket ticket : ticketList) {
        	 cpResultVisitor = new CpResultVisitor();
        	 cpResultVisitor.setOrderId(""+ticket.getId());
        	 cpResultVisitor.setIsSuccess(false);
        	 cpResultVisitor.setResult("-1");
        	 cpResultVisitor.setBetMoney(""+ticket.getSchemeCost());
        	 cpResultVisitorList.add(cpResultVisitor);
    		 LotteryID = getYuecaiLotteryId(ticket);
    		 if(null==WareIssue){
    			 WareIssue = this.WareIssue(ticket);
    		 }
    		 BetAmount = BetAmount+(ticket.getUnits()*ticket.getMultiple());
    		 content.append(BetContent(ticket)).append("|");
		}
        String BetContent = content.delete(content.length()-1, content.length()).toString();
        String RealName = "张三";
        String IDCard = "4321000000";
        String Phone ="13312345678";
        String Sign = SecurityUtil.md5((ChannelID+ LotteryID+ WareID+ BetAmount+ BetContent+Key).getBytes("UTF-8")).toUpperCase();
        String postData="transcode="+transcode+"&ChannelID="+ChannelID+"&LotteryID="+LotteryID+"&WareID="+WareID
        							+"&WareIssue="+WareIssue+"&BatchID="+BatchID+"&AddFlag="+AddFlag
        							+"&BetAmount="+BetAmount+"&BetContent="+BetContent+"&RealName="+RealName
        							+"&IDCard="+IDCard+"&Phone="+Phone+"&Sign="+Sign;
        postData = postData.replaceAll("7\\+","7%2b");
        String returnString = Utf8HttpClientUtils(reUrl,postData);
        List<CpResultVisitor> returnList = Lists.newArrayList();
        if(null!=returnString&&"0".equals(returnString)){
        	for (CpResultVisitor cpResultVisitor2 : cpResultVisitorList) {
        		cpResultVisitor2.setIsSuccess(true);
        		returnList.add(cpResultVisitor2); 
			}
        }else{
        	for (CpResultVisitor cpResultVisitor2 : cpResultVisitorList) {
        		cpResultVisitor2.setResult(returnString);
        		returnList.add(cpResultVisitor2);
			}
        }
    	return returnList;
	}
	public List<QueryPVisitor> confirmTicket(List<Ticket> ticketList) throws DataException, IOException, DocumentException{
		StringBuffer wParamValueSb = new StringBuffer();
		String transcode="205";
		String LotteryID = null;
		for (Ticket ticket : ticketList) {
			LotteryID = getYuecaiLotteryId(ticket);
			wParamValueSb.append(ticket.getId()).append(",");
		}
		String ChannelTicketID = wParamValueSb.delete(wParamValueSb.length()-1, wParamValueSb.length()).toString();
		String Sign = SecurityUtil.md5(ChannelID+ LotteryID+ChannelTicketID+Key).toUpperCase();
		String postData="transcode="+transcode+"&ChannelID="+ChannelID+"&LotteryID="+LotteryID+"&ChannelTicketID="+ChannelTicketID+"&Sign="+Sign;
		String returnString = Utf8HttpClientUtils(reUrl,postData);
		//<?xml version="1.0" encoding="utf-8"?><Tickets><PrintedTicket ChannelID='52' ChannelTicketID='2192' LotteryID='5' PrintTime='' ODDS='' TicketStatus='1' WinAmt='' /></Tickets>
		Document doc = DocumentHelper.parseText(returnString);
		Element root = doc.getRootElement(); 
		List<QueryPVisitor> returnList = Lists.newArrayList();
		for (Iterator i = root.elementIterator("PrintedTicket"); i.hasNext();) {
			QueryPVisitor queryPVisitor = new QueryPVisitor();
			Element element= (Element) i.next();
			String ChannelTicketID2 = element.attributeValue("ChannelTicketID");
			String TicketStatus = element.attributeValue("TicketStatus");
			String PrintTime = element.attributeValue("PrintTime");
			String ODDS = element.attributeValue("ODDS");
			String TicketNo = element.attributeValue("TicketNo");
			queryPVisitor.setTicketCode(TicketNo);
			queryPVisitor.setAwards(ODDS);
			queryPVisitor.setOrderId(ChannelTicketID2);
			queryPVisitor.setStatus(TicketStatus);
			queryPVisitor.setOperateTime(PrintTime);
			queryPVisitor.setResultId(TicketStatus);
			if(null!=TicketStatus&&"2".equals(TicketStatus.trim())){
				queryPVisitor.setIsSuccess(true);
			}
			returnList.add(queryPVisitor);
		}
		return returnList;
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
		String transcode="201";

		String msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><head transcode=\"002\" partnerid=\"349022\" version=\"1.0\" time=\"1337772209062\" /><body><ticketorder   lotteryId =\"SPF\"  ticketsnum=\"1\"   totalmoney=\"2\"><tickets><ticket ticketId=\"325\" betType=\"P2_1\"  issueNumber=\"120515\" betUnits=\"1\" multiple=\"1\" betMoney =\"2\" isAppend =\"0\"><betContent>3:[胜]/4:[平]</betContent></ticket></tickets></ticketorder></body></msg>";

		String key="123456";
		String LotteryID = "8";
		String WareID = "";
		String BetAmount = "";
		String BetContent = "";
		String Sign = SecurityUtil.md5(ChannelID+ LotteryID+Key).toUpperCase();
		//String postData="transcode="+transcode+"&ChannelID="+ChannelID+"&LotteryID="+LotteryID+"&Sign="+Sign;
		String postData = "sign=ecabf1b2b91674e13594c07f43b4c80b&timestamp=2013-09-25%2017:08:03&_input_charset=utf-8&grant_type=authorization_code&sign_type=MD5&service=alipay.open.auth.token.exchange&partner=2088701896023484&code=4fc97f71ca9640b48363f5a7eedccb4e";
		String reUrl="https://mapi.alipay.com/gateway.do";
		
		
	    String returnString = Utf8HttpClientUtils(reUrl,postData);
		
		//String sign = SecurityUtil.md5(ChannelID+ LotteryID+ WareID+ BetAmount+ BetContent+Key);
		
		System.out.println(SecurityUtil.md5(transcode+msg+key));
		
		///System.out.println(SecurityUtil.md5("你好"));
		///System.out.println(MD5.md5("你好"));
		//System.out.print(MD5.md5(transcode+msg+key));
		DczcYuecaiUtil plYuecaiUtil = new DczcYuecaiUtil();
		//plYuecaiUtil.getIssue(Lottery.DCZC,Byte.valueOf("0"));
		plYuecaiUtil.sendTicket(null);
	}
	public QueryPVisitor getIssue(Lottery lottery,Byte betType) throws DataException, IOException, DocumentException{
		String transcode="201";
		Ticket ticket = new Ticket();
		ticket.setBetType(betType);
		ticket.setLotteryType(lottery);
		String LotteryID = getYuecaiLotteryId(ticket);
		String Sign = SecurityUtil.md5(ChannelID+ LotteryID+Key).toUpperCase();
		String postData="transcode="+transcode+"&ChannelID="+ChannelID+"&LotteryID="+LotteryID+"&Sign="+Sign;
		String returnString = Utf8HttpClientUtils(reUrl,postData);
		Document doc = DocumentHelper.parseText(returnString);
		Element root = doc.getRootElement(); 
		List<CpResultVisitor> returnList = Lists.newArrayList();
		//<?xml version="1.0" encoding="utf-8"?><CWares><CWare WareID='13291' LotteryID='8' WareIssue='30913' BeginSellTime='2013/9/22 9:00:00' EndSellTime='2013/9/24 6:00:00' WareState='1' /></CWares>
		
		Element element = (Element)root.element("CWare");
	    String WareIssue = element.attributeValue("WareIssue");
		String	BeginSellTime = element.attributeValue("BeginSellTime");
		String	EndSellTime = element.attributeValue("EndSellTime");
		QueryPVisitor queryPVisitor=new QueryPVisitor();
		queryPVisitor.setIssueNumber("1"+WareIssue);
		return queryPVisitor;
	}
	 public static String Utf8HttpClientUtils(String url,String param){
		     
		     StringBuffer result=new StringBuffer();
		     BufferedReader reader = null;
		     DefaultHttpClient httpclient = null;
		     HttpPost httppost = null;
		     try {
		    	 BasicHttpParams parms = new BasicHttpParams(); 
		         parms.setParameter("charset", HTTP.UTF_8);
		         parms.setParameter(HttpConnectionParams.SO_TIMEOUT,200000);
			     httpclient = new DefaultHttpClient(parms);  
			     // 目标地址  
			     httppost = new HttpPost(url);
			     httppost.addHeader("charset", HTTP.UTF_8); 
			     // 构造最简单的字符串数据  
			      StringEntity reqEntity = new StringEntity(param,HTTP.UTF_8);  
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
		     }catch (Exception ex) {
		    	  reader = null;
				  httpclient = null;
				  httppost = null;
		     }finally{
		    	 reader = null;
				  httpclient = null;
				  httppost = null;
			 }   
		     return result.toString();
	}
	
//	public CpResultVisitor getIssueAward(Lottery lottery,Byte betType,String issueNumber) throws DataException, IOException, DocumentException{
//		Ticket ticket = new Ticket();
//		ticket.setBetType(betType);
//		ticket.setLotteryType(lottery);
//		String LotID = getYuecaiLotteryId(ticket);
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
	public String getYuecaiLotteryId(Ticket ticket){
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
				return "Pl3";
			}
		}else if(Lottery.SEVEN.equals(ticket.getLotteryType())){
			return "QLC";
		}else if(Lottery.SEVENSTAR.equals(ticket.getLotteryType())){
			return "QXC";
		}else if(Lottery.DCZC.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.dczc.PlayType playType = com.cai310.lottery.support.dczc.PlayType.values()[ticket.getBetType()];
			if(PlayType.SPF.equals(playType)){
				return "5";
			}else if(PlayType.ZJQS.equals(playType)){
				return "8";
			}else if(PlayType.BF.equals(playType)){
				return "9";
			}else if(PlayType.BQQSPF.equals(playType)){
				return "7";
			}else if(PlayType.SXDS.equals(playType)){
				return "6";
			}
		}else if(Lottery.TC22TO5.equals(ticket.getLotteryType())){
			return "22X5";
		}
		return null;
	}
	
	protected static Map<String, Object> map;
	public YuecaiUtil(Ticket ticket){
		if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(null!=ticket&&null!=ticket.getContent())map = JsonUtil.getMap4Json(ticket.getContent());
		}
	}
	public YuecaiUtil(){
		
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
	
}
