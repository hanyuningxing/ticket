package com.cai310.lottery.ticket.protocol.localnew.utils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONArray;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.Config;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

public abstract class CPUtil {
	protected static Logger logger = LoggerFactory.getLogger(CPUtil.class);
	public static final String SINGLE_REGEX = "^\\s*((?:\\d{1,2}\\D+)+)(\\d{1,2})\\s*$";
	
	protected Map<String, Object> map;
	public CPUtil(Ticket ticket){
		if(null!=ticket&&null!=ticket.getContent())map = JsonUtil.getMap4Json(ticket.getContent());
	}
	public CPUtil(){
	}
	public static void main(String[] args) {
		String key = "902324175232,3261434041";
		;
		System.out.println(SecurityUtil.md5(key).toLowerCase());
    }
	public Map<String, Object> getTicketContentMap(Ticket ticket){
		if(null!=ticket&&null!=ticket.getContent()){
			map = JsonUtil.getMap4Json(ticket.getContent());
		}
		return map;
	}
	public static final String SINGLE_SPACE = "+";
	
	private static final String reUrl=Config.config.getProperty("ticket.internal", "http://localhost/api");//"http://192.168.15.250:81/Api";//dh测试
//	private static final String reUrl="http://127.0.0.1:61288/Api";
//	private static final String reUrl="http://124.128.234.4:13088/Api";
//	private static final String reUrl="http://210.14.139.23:61288/Api ";
	private static final String key="qwerty";
	
	
	public CpResult sendTicket(List<Ticket> ticketList) throws DataException, IOException, DocumentException{
		List<Map<String,String>> list = Lists.newArrayList();
		for (Ticket ticket : ticketList) {
			Map<String,String> paramMap = buildPeqParamValue(ticket);
			list.add(paramMap);
		}
		String parame =JSONArray.fromObject(list).toString();
		//String parame ="{}";
		String version ="1";
		String transCode = "901";
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("TransCode", transCode);
		ParamMap.put("Version", version);
		ParamMap.put("Parame", parame);
		ParamMap.put("Key", SecurityUtil.md5((transCode+version+parame+key).getBytes("UTF-8")).toLowerCase());
		System.out.println("deng------------"+parame);
		//ParamMap.put("ReqKey", SecurityUtil.md5(reqIdValue+msgIdValue+peqParamValue+key).toLowerCase());
		
		//String url = getReUrl(reqIdValue,msgIdValue,peqParamValue);
		//{"Data":[{"Code":101,"TicketId":2784}],"Code":0}
		String returnString = HttpclientUtil.Utf8HttpClientUtils(reUrl, ParamMap);
		Gson gson = new Gson();
		CpResult cpResult = gson.fromJson(returnString, CpResult.class);
		return cpResult;
	}
	
	/**
	 * 
	 * @param lottery
	 * @param playType
	 * @return
	 * @throws DataException 
	 */
	public Map<String,String> buildPeqParamValue(Ticket ticket) throws DataException{
		Lottery lottery = ticket.getLotteryType();
		Byte betType = ticket.getBetType();
		Map<String,String> paramMap=new LinkedHashMap<String,String>();
		String TicketId=""+ticket.getId();
		paramMap.put("TicketId", TicketId);
		
		//区分票源
		if (2==ticket.getSponsorId()){//cpdyj
			paramMap.put("SourceId", "1");
		}
		else if (1==ticket.getSponsorId()){//test
			paramMap.put("SourceId", "2");
		}else {
			paramMap.put("SourceId", String.valueOf(ticket.getSponsorId()));
		}
			
		paramMap.put("Priority", "100");
		String PlatformId = getCpLotteryId(lottery,betType);
		paramMap.put("PlatformId", PlatformId);
		String BetType = getCpBetType(ticket);
		paramMap.put("BetType", BetType);
		if(ticket.getLotteryType().getCategory().equals(LotteryCategory.FREQUENT)||ticket.getLotteryType().getCategory().equals(LotteryCategory.JC)){
			paramMap.put("IssueNumber",getUpdatePeriodNumber(ticket));
		}else{
			paramMap.put("IssueNumber", ticket.getPeriodNumber());
		}
//		String StartSaleTime = DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
		
		paramMap.put("StartSaleTime", "");
		String EndSaleTime = DateUtil.dateToStr(ticket.getOfficialEndTime(),"yyyy-MM-dd HH:mm:ss");
		paramMap.put("EndSaleTime", EndSaleTime);
		String PlayType = getCpPlayType(ticket);
		paramMap.put("PlayType", PlayType);
		String Multiple = ""+ticket.getMultiple();
		paramMap.put("Multiple", Multiple);
		String BetUnits = ""+ticket.getUnits();
		paramMap.put("BetUnits", BetUnits);
		String BetCost = ""+ticket.getSchemeCost();
		paramMap.put("BetCost", BetCost);
        String SpecialFlag = getSpecialFlag(ticket);
        paramMap.put("BetMode", SpecialFlag);
        String BetContent = getBetContent(ticket);
        paramMap.put("BetContent", BetContent);
        return paramMap;
//		String localnewhostString= "<?xml version=\"1.0\"?>"+rootElement.asXML();
//		String zhengque = "<?xml version=\"1.0\"?><ReqParam xmlns:xsi=\" http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\" http://www.w3.org/2001/XMLSchema\"><OrderId>19615648</OrderId><PlatformId>3</PlatformId><BetType>0</BetType><IssueNumber>11055</IssueNumber><EndSaleTime>2011/5/29 23:00:00</EndSaleTime><PlayType>0</PlayType><Multiple>1</Multiple><BetUnits>1</BetUnits><BetCost>2</BetCost><SpecialFlag></SpecialFlag><BetContent>0,3,3,3,0,3,3,3,3,3,3,0,3,3</BetContent></ReqParam>";
//		
//		return "<?xml version=\"1.0\"?><ReqParam xmlns:xsi=\" http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\" http://www.w3.org/2001/XMLSchema\"><OrderId>19615648</OrderId><PlatformId>3</PlatformId><BetType>0</BetType><IssueNumber>11055</IssueNumber><EndSaleTime>2011/5/29 23:00:00</EndSaleTime><PlayType>0</PlayType><Multiple>1</Multiple><BetUnits>1</BetUnits><BetCost>2</BetCost><SpecialFlag></SpecialFlag><BetContent>0,3,3,3,0,3,3,3,3,3,3,0,3,3</BetContent></ReqParam>";
	}
	//{"Data":[
	//		{"TicketState":1,"TicketCode":"Null","Odds":"
	//										{\"Items\":[{\"Choices\":[{\"Value\":0,\"Odds\":1.68}],\"Line\":\"20140418001\",\"HomeTeam\":\"20140418-001\",\"GuestTeam\":\"20140418-001\",\"Rq\":null,\"Zf\":null},{\"Choices\":[{\"Value\":2,\"Odds\":2.38}],\"Line\":\"20140418002\",\"HomeTeam\":\"20140418-002\",\"GuestTeam\":\"20140418-002\",\"Rq\":null,\"Zf\":null}]}"
	//											,"OperateTime":"2014-04-18 11:20:10","Code":0,"TicketId":2784}
	//],"Code":0}
	public static QueryPVisitor confirmTicket(List<Long> ticketList) throws DataException, IOException, DocumentException{
		StringBuffer sb = new StringBuffer();
		for (Long id : ticketList) {
			sb.append(id+",");
		}
		sb.delete(sb.length()-1, sb.length());
		String parame =sb.toString();
		//String parame ="{}";
		String version ="1";
		String transCode = "902";
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("TransCode", transCode);
		ParamMap.put("Version", version);
		ParamMap.put("Parame", parame);
		ParamMap.put("Key", SecurityUtil.md5((transCode+version+parame+key).getBytes("UTF-8")).toLowerCase());
		
		
		String returnString = HttpclientUtil.Utf8HttpClientUtils(reUrl, ParamMap);
		Gson gson = new Gson();
		QueryPVisitor queryPVisitor = gson.fromJson(returnString, QueryPVisitor.class);
		return queryPVisitor;
	}
	
	public static CpResult updatePrizeTicket(List<Ticket> ticketList)throws DataException, IOException, DocumentException{
		List<Map<String,String>> list = Lists.newArrayList();
		for (Ticket ticket : ticketList) {
			Map<String,String> paramMap=new LinkedHashMap<String,String>();
			String TicketId=""+ticket.getId();
			String TicketPrize=ticket.getTotalPrizeAfterTax().toString();
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
			String OperateTime = sdFormat.format(ticket.getSendTime());
			paramMap.put("TicketId", TicketId);
			paramMap.put("Prize", TicketPrize);
			paramMap.put("PrizeDescription", "");
			paramMap.put("OperateTime", OperateTime);
			list.add(paramMap);
		}
		String parame =JSONArray.fromObject(list).toString();
		String version ="1";
		String transCode = "904";
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("TransCode", transCode);
		ParamMap.put("Version", version);
		ParamMap.put("Parame", parame);
		ParamMap.put("Key", SecurityUtil.md5((transCode+version+parame+key).getBytes("UTF-8")).toLowerCase());
		String returnString = HttpclientUtil.Utf8HttpClientUtils(reUrl, ParamMap);
		Gson gson = new Gson();
		CpResult cpResult = gson.fromJson(returnString, CpResult.class);
		return cpResult;
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
	/**
	 *  
	 * @param lottery
	 * @param playType
	 * @return
	 */
	public String putMatchInfo(Ticket ticket){
		return "";
	}
	public abstract String getCpLotteryId(Lottery lottery,Byte betType);
	public abstract String getUpdatePeriodNumber(Ticket ticket);
	public abstract String getCpBetType(Ticket ticket) throws DataException;
	public abstract String getCpPlayType(Ticket ticket);
	public abstract String getBetContent(Ticket ticket) throws DataException;
	public abstract String getSpecialFlag(Ticket ticket);
	protected List<String> formatBetNum(List<String> numList,NumberFormat nf){
		List<String> newNumList = Lists.newArrayList();
		for (String num : numList) {
			newNumList.add(nf.format(Integer.valueOf(num)));
		}
		return newNumList;
	}

	protected static final NumberFormat TWO_NF = new DecimalFormat("00");
	
}
