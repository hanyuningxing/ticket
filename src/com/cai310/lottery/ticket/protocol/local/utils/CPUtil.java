package com.cai310.lottery.ticket.protocol.local.utils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.MD5;
import com.google.common.collect.Lists;

public abstract class CPUtil {
	protected static Logger logger = LoggerFactory.getLogger(CPUtil.class);
	public static final String SINGLE_REGEX = "^\\s*((?:\\d{1,2}\\D+)+)(\\d{1,2})\\s*$";
	public static void main(String[] args) {
		Pattern patt = Pattern.compile(SINGLE_REGEX);
		Matcher matcher = patt.matcher("01-02|04,02,04,02");
		if (matcher.matches()){
			String unitsStr = matcher.group(1);
			int i = 0;
		}
		String unitsStr = matcher.group(1);
	}
	protected Map<String, Object> map;
	public CPUtil(Ticket ticket){
		if(null!=ticket&&null!=ticket.getContent())map = JsonUtil.getMap4Json(ticket.getContent());
	}
	public CPUtil(){
	}
	public Map<String, Object> getTicketContentMap(Ticket ticket){
		if(null!=ticket&&null!=ticket.getContent()){
			map = JsonUtil.getMap4Json(ticket.getContent());
		}
		return map;
	}
	public static final String SINGLE_SPACE = "+";
	private static final String reUrl="http://127.0.0.1:26828/PrintSystem3Motion.ashx/DealWith";
	private static final String key="123456";
	
	
	public List<CpResultVisitor> sendTicket(List<Ticket> ticketList) throws DataException, IOException, DocumentException{

		Document document = DocumentHelper.createDocument();
	   	/** 建立XML文档的根 */
	   	Element rootElement = document.addElement("ReqParam");
	   	rootElement.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		rootElement.addAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		for (Ticket ticket : ticketList) {
			Map<String,String> paramMap = buildPeqParamValue(ticket);
	   	    Element ticketElement = rootElement.addElement("Ticket");
	   	    for (String key : paramMap.keySet()) {
		   		String value = paramMap.get(key);
		   	    Element element = ticketElement.addElement(key);
		   	    element.setText(value);
			}
		}
	   	String peqParamValue =  "<?xml version=\"1.0\"?>"+rootElement.asXML();
		String msgIdValue = System.currentTimeMillis()+"";
		String reqIdValue = "801";
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("ReqId", reqIdValue);
		ParamMap.put("MsgId", msgIdValue);
		ParamMap.put("ReqParam", peqParamValue);
		ParamMap.put("ReqKey", SecurityUtil.md5((reqIdValue+msgIdValue+peqParamValue+key).getBytes("UTF-8")).toLowerCase());
		
		//ParamMap.put("ReqKey", SecurityUtil.md5(reqIdValue+msgIdValue+peqParamValue+key).toLowerCase());
		
		//String url = getReUrl(reqIdValue,msgIdValue,peqParamValue);
		
		String returnString = HttpclientUtil.Utf8HttpClientUtils(reUrl, ParamMap);
		Document doc = DocumentHelper.parseText(returnString);
		Element root = doc.getRootElement(); 
		List<CpResultVisitor> returnList = Lists.newArrayList();
		for(Iterator i = root.elementIterator("Ticket"); i.hasNext();) {
			String ResultId = null;
			String OrderId = null;
			try {
				Element element = (Element)i.next();
				ResultId = element.selectSingleNode("ResultId").getText();
				OrderId = element.selectSingleNode("OrderId").getText();
				CpResultVisitor cpResultVisitor=new CpResultVisitor();
				///是否成功
				if(StringUtils.isNotBlank(ResultId)){
					if(String.valueOf("0").equals(ResultId.trim())||String.valueOf("2").equals(ResultId.trim())){
						///0成功2重复
						cpResultVisitor.setIsSuccess(Boolean.TRUE);
					}
					cpResultVisitor.setResult(ResultId);
				}
				if(StringUtils.isNotBlank(OrderId)){
					cpResultVisitor.setOrderId(OrderId);
				}
				returnList.add(cpResultVisitor);
			}catch (Exception e) {
				logger.error("发送彩票出现错误[ResultId]"+ResultId+"[OrderId]"+OrderId+e.getMessage());
			    continue;
			}
		}
		return returnList;
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
		String OrderId=""+ticket.getId();
		paramMap.put("OrderId", OrderId);
		////////加入2串1分离
		
		if(ticket.getSponsorId()==406||ticket.getSponsorId()==415||ticket.getSponsorId()==428){
				paramMap.put("SourceId", "8");
		}else if(ticket.getSponsorId()==370){
				paramMap.put("SourceId", "9");
		}else{
				paramMap.put("SourceId", "1");
		}
		paramMap.put("Priority", "100");
		String PlatformId = getCpLotteryId(lottery,betType);
		paramMap.put("PlatformId", PlatformId);
		String BetType = getCpBetType(ticket);
		paramMap.put("BetType", BetType);
		if(ticket.getLotteryType().getCategory().equals(LotteryCategory.FREQUENT)){
			paramMap.put("IssueNumber",getUpdatePeriodNumber(ticket));
		}else{
			paramMap.put("IssueNumber", ticket.getPeriodNumber());
		}
		String EndSaleTime = DateUtil.dateToStr(ticket.getOfficialEndTime(),"yyyy/MM/dd HH:mm:ss");
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
        paramMap.put("SpecialFlag", SpecialFlag);
        String BetContent = getBetContent(ticket);
        paramMap.put("BetContent", BetContent);
        paramMap.put("MatchInfo", putMatchInfo(ticket));
        return paramMap;
//		String localhostString= "<?xml version=\"1.0\"?>"+rootElement.asXML();
//		String zhengque = "<?xml version=\"1.0\"?><ReqParam xmlns:xsi=\" http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\" http://www.w3.org/2001/XMLSchema\"><OrderId>19615648</OrderId><PlatformId>3</PlatformId><BetType>0</BetType><IssueNumber>11055</IssueNumber><EndSaleTime>2011/5/29 23:00:00</EndSaleTime><PlayType>0</PlayType><Multiple>1</Multiple><BetUnits>1</BetUnits><BetCost>2</BetCost><SpecialFlag></SpecialFlag><BetContent>0,3,3,3,0,3,3,3,3,3,3,0,3,3</BetContent></ReqParam>";
//		
//		return "<?xml version=\"1.0\"?><ReqParam xmlns:xsi=\" http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\" http://www.w3.org/2001/XMLSchema\"><OrderId>19615648</OrderId><PlatformId>3</PlatformId><BetType>0</BetType><IssueNumber>11055</IssueNumber><EndSaleTime>2011/5/29 23:00:00</EndSaleTime><PlayType>0</PlayType><Multiple>1</Multiple><BetUnits>1</BetUnits><BetCost>2</BetCost><SpecialFlag></SpecialFlag><BetContent>0,3,3,3,0,3,3,3,3,3,3,0,3,3</BetContent></ReqParam>";
	}
	public static List<QueryPVisitor> confirmTicket(List<Long> ticketList) throws DataException, IOException, DocumentException{
		String msgIdValue = System.currentTimeMillis()+"";
		String reqIdValue = "802";
		StringBuffer sb = new StringBuffer();
		for (Long id : ticketList) {
			sb.append(id+",");
		}
		sb.delete(sb.length()-1, sb.length());
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("ReqId", reqIdValue);
		ParamMap.put("MsgId", msgIdValue);
		ParamMap.put("ReqParam", sb.toString());
		ParamMap.put("ReqKey", MD5.md5(reqIdValue+msgIdValue+sb.toString()+key).toLowerCase());
		
		
		String returnString = HttpclientUtil.Utf8HttpClientUtils(reUrl, ParamMap);
		Document doc=DocumentHelper.parseText(returnString);
		Element root = doc.getRootElement();
		Element ticket;
		QueryPVisitor queryPVisitor;
		List<QueryPVisitor> returnTicketList = Lists.newArrayList();
		for (Iterator i = root.elementIterator("Ticket"); i.hasNext();) {
			ticket = (Element) i.next();
			queryPVisitor=new QueryPVisitor();
			ticket.accept(queryPVisitor);
			returnTicketList.add(queryPVisitor);
		}
		if(returnTicketList.isEmpty()){
			Document document = DocumentHelper.parseText(returnString);
			queryPVisitor=new QueryPVisitor();
			document.accept(queryPVisitor);
			returnTicketList.add(queryPVisitor);
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
