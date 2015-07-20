package com.cai310.lottery.ticket.protocol.rlyg;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
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
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

public class RlygUtil {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static final String reUrl="http://116.213.75.179:8080/billservice/sltAPI";
	private static final String realname ="hongbo";
	private static final String username ="hongbo";
	private static final String mobile ="";
	private static final String email ="";
	private static final String prefix ="";
	private static final String cardtype = "1";
	private static final String cardno = "";
	private static final String agentID = "800053";
	private static final String AgentPwd ="s8dugf7r9mqp";
	
	/**
	 * 发送彩票
	 * @param ticket
	 * @return
	 * @throws DataException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public CpResultVisitor sendTicket(List<Ticket> ticketList) throws DataException, IOException, DocumentException{
		StringBuffer bodyValueSb = new StringBuffer();
		String cmd = "2030";
		bodyValueSb.append("<body>"); 
		Ticket ticketTemp = null;
		Long issue = 0L;
		StringBuffer ticketXML = new StringBuffer();
		for (Ticket ticket : ticketList) {
			try{
				ticketTemp=ticket;
				ticketXML.append(this.getLotCode(ticket).get("betValue"));
				if(Long.valueOf(this.getLotCode(ticket).get("issue"))>issue){
					issue = Long.valueOf(this.getLotCode(ticket).get("issue"));
				}
			}catch(Exception e){
				logger.error(e.toString());
				continue;
			}
		}
		if(LotteryCategory.NUMBER.equals(this.getLottery().getCategory())
				||LotteryCategory.ZC.equals(this.getLottery().getCategory())
			         ||LotteryCategory.FREQUENT.equals(this.getLottery().getCategory())){
			cmd = "2001";
		}
		bodyValueSb.append("<order username=\""+username+"\" lotoid=\""+getRlygLotteryId(ticketTemp)+"\" issue=\""+issue+"\" areaid=\"00\" orderno=\""+prefix+ticketTemp.getLotteryType().getKey()+ticketTemp.getId()+"\">"); 
		bodyValueSb.append("<userinfo realname=\""+realname+"\" mobile=\""+mobile+"\" email=\""+email+"\" cardtype=\""+cardtype+"\" cardno=\""+cardno+"\"/>"); 
		bodyValueSb.append(ticketXML.toString());
		bodyValueSb.append("</order>"); 
		bodyValueSb.append("</body>"); 
		
		String md5 = SecurityUtil.md5((agentID+AgentPwd+bodyValueSb.toString()).getBytes("UTF-8")).toLowerCase();
		
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
		wParamValueSb.append("<msg v=\"1.0\" id=\""+System.currentTimeMillis()+"\"><ctrl><agentID>"+agentID+"</agentID><cmd>"+cmd+"</cmd><timestamp>"+System.currentTimeMillis()+"</timestamp><md>"+md5+"</md></ctrl>");
		wParamValueSb.append(bodyValueSb.toString());
		wParamValueSb.append("</msg>");
		
		
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("cmd", cmd);
		ParamMap.put("msg", wParamValueSb.toString());
		String returnString = HttpclientUtil.Utf8HttpClientUtils(reUrl, ParamMap);
	    List<CpResultVisitor> cpResultVisitorList = Lists.newArrayList();
	    if(StringUtils.isNotBlank(returnString)){
			Document doc=DocumentHelper.parseText(returnString);
			Element root = doc.getRootElement();
			Element body = root.element("body");
		    Element response = body.element("response");
			CpResultVisitor cpResultVisitor=new CpResultVisitor();
			response.accept(cpResultVisitor);
		    return cpResultVisitor;
	    }
		return null;
	}
	public Map<String,String> getLotCode(Ticket ticket) throws DataException{
		return null;
	}
	public Lottery getLottery(){
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
	public String getRlygLotteryId(Ticket ticket){
		if(null==ticket)return null;
		if(null==ticket.getLotteryType())return null;
		if(Lottery.JCZQ.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.jczq.PlayType playType =  com.cai310.lottery.support.jczq.PlayType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("playTypeOrdinal")))];
			com.cai310.lottery.support.jczq.PassType passType =  com.cai310.lottery.support.jczq.PassType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("passTypeOrdinal")))];
			switch (playType) {
			case SPF:
				if(passType.equals(com.cai310.lottery.support.jczq.PassType.P1)){
					return "311";
				}else{
					return "301";
				}
			case JQS:
				if(passType.equals(com.cai310.lottery.support.jczq.PassType.P1)){
					return "313";
				}else{
					return "303";
				}
			case BF:
				if(passType.equals(com.cai310.lottery.support.jczq.PassType.P1)){
					return "312";
				}else{
					return "302";
				}
			case BQQ:
				if(passType.equals(com.cai310.lottery.support.jczq.PassType.P1)){
					return "314";
				}else{
					return "304";
				}
			default:
				throw new RuntimeException("玩法不正确.");
			}
		}else if(Lottery.JCLQ.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.jclq.PlayType playType =  com.cai310.lottery.support.jclq.PlayType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("playTypeOrdinal")))];
			com.cai310.lottery.support.jclq.PassType passType =  com.cai310.lottery.support.jclq.PassType.values()[Integer.valueOf(String.valueOf(getTicketContentMap(ticket).get("passTypeOrdinal")))];
			switch (playType) {
			case SF:
				if(passType.equals(com.cai310.lottery.support.jclq.PassType.P1)){
					return "317";
				}else{
					return "307";
				}
			case RFSF:
				if(passType.equals(com.cai310.lottery.support.jclq.PassType.P1)){
					return "316";
				}else{
					return "306";
				}
			case SFC:
				if(passType.equals(com.cai310.lottery.support.jclq.PassType.P1)){
					return "318";
				}else{
					return "308";
				}
			case DXF:
				if(passType.equals(com.cai310.lottery.support.jclq.PassType.P1)){
					return "319";
				}else{
					return "309";
				}
			default:
				throw new RuntimeException("玩法不正确.");
			}
		}else if(Lottery.SSQ.equals(ticket.getLotteryType())){
			return "001";
		}else if(Lottery.DLT.equals(ticket.getLotteryType())){
			DltPlayType dltPlayType = DltPlayType.values()[ticket.getBetType()];
			if(dltPlayType.equals(DltPlayType.Select12to2)){//12选2
				return "114";
			}else{
				return "113";
			}
		}else if(Lottery.WELFARE3D.equals(ticket.getLotteryType())){
			return "002";
		}else if(Lottery.LCZC.equals(ticket.getLotteryType())){
			return "115";
		}else if(Lottery.SCZC.equals(ticket.getLotteryType())){
			return "116";
		}else if(Lottery.PL.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.pl.PlPlayType playType = com.cai310.lottery.support.pl.PlPlayType.values()[ticket.getBetType()];
			if(playType.equals(com.cai310.lottery.support.pl.PlPlayType.P5Direct)){
				return "109";
			}else{
				return "108";
			}
		}else if(Lottery.SFZC.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.zc.PlayType playType = com.cai310.lottery.support.zc.PlayType.values()[ticket.getBetType()];
			if(playType.equals(com.cai310.lottery.support.zc.PlayType.SFZC14)){
				return "117";
			}else if(playType.equals(com.cai310.lottery.support.zc.PlayType.SFZC9)){
				return "118";
			}
		}else if(Lottery.SDEL11TO5.equals(ticket.getLotteryType())){
			return "107";
		}else if(Lottery.SSC.equals(ticket.getLotteryType())){
			return "018";
		}else if(Lottery.SEVEN.equals(ticket.getLotteryType())){
			return "003";
		}
		return null;
	}
	
	protected Map<String, Object> map;
	public RlygUtil(Ticket ticket){
		if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(null!=ticket&&null!=ticket.getContent())map = JsonUtil.getMap4Json(ticket.getContent());
		}
	}
	public RlygUtil(){
		
	}
	public Map<String, Object> getTicketContentMap(Ticket ticket){
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
	public static void main(String[] args) throws ClientProtocolException, IOException, DocumentException {
		Ticket ticket = new Ticket();
		ticket.setLotteryType(Lottery.SEVEN);
		JczqRlygUtil jczqRlygUtil = new JczqRlygUtil();
		jczqRlygUtil.getIssue(Lottery.SEVEN,null);
	}
	public RlygQueryPVisitor getIssue(Lottery lottery,Byte betType) throws ClientProtocolException, IOException, DocumentException {
		Ticket ticket = new Ticket();
		ticket.setLotteryType(lottery);
		ticket.setBetType(betType);
		StringBuffer bodyValueSb = new StringBuffer();
		String cmd = "2000";
		bodyValueSb.append("<body>");
		bodyValueSb.append("<loto lotoid=\""+getRlygLotteryId(ticket)+"\" issue=\"\"/>");
		bodyValueSb.append("</body>"); 
		
		String md5 = SecurityUtil.md5((agentID+AgentPwd+bodyValueSb.toString()).getBytes("UTF-8")).toLowerCase();
		
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
		wParamValueSb.append("<msg v=\"1.0\" id=\""+System.currentTimeMillis()+"\"><ctrl><agentID>"+agentID+"</agentID><cmd>"+cmd+"</cmd><timestamp>"+System.currentTimeMillis()+"</timestamp><md>"+md5+"</md></ctrl>");
		wParamValueSb.append(bodyValueSb.toString());
		wParamValueSb.append("</msg>");
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("cmd", cmd);
		ParamMap.put("msg", wParamValueSb.toString());
		String returnString = HttpclientUtil.Utf8HttpClientUtils(reUrl, ParamMap);
	    if(StringUtils.isNotBlank(returnString)){
			Document doc=DocumentHelper.parseText(returnString);
			Element root = doc.getRootElement();
			Element body = root.element("body");
		    Element response = body.element("response");
		    Element order = response.element("issuequery");
		    RlygQueryPVisitor queryPVisitor=new RlygQueryPVisitor();
		    order.element("issue").accept(queryPVisitor);
			return queryPVisitor;
	    }
		
		return null;
	}
	
	public QueryPVisitor confirmTicket(Ticket ticket) throws ClientProtocolException, IOException, DocumentException {
		StringBuffer bodyValueSb = new StringBuffer();
		String cmd = "2015";
		bodyValueSb.append("<body>");
		bodyValueSb.append("<order merchantid=\""+agentID+"\" orderno=\""+prefix+ticket.getLotteryType().getKey()+ticket.getId()+"\"/>");
		bodyValueSb.append("</body>"); 
		
		String md5 = SecurityUtil.md5((agentID+AgentPwd+bodyValueSb.toString()).getBytes("UTF-8")).toLowerCase();
		
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
		wParamValueSb.append("<msg v=\"1.0\" id=\""+System.currentTimeMillis()+"\"><ctrl><agentID>"+agentID+"</agentID><cmd>"+cmd+"</cmd><timestamp>"+System.currentTimeMillis()+"</timestamp><md>"+md5+"</md></ctrl>");
		wParamValueSb.append(bodyValueSb.toString());
		wParamValueSb.append("</msg>");
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("cmd", cmd);
		ParamMap.put("msg", wParamValueSb.toString());
		String returnString = HttpclientUtil.Utf8HttpClientUtils(reUrl, ParamMap);
	    if(StringUtils.isNotBlank(returnString)){
			Document doc=DocumentHelper.parseText(returnString);
			Element root = doc.getRootElement();
			Element body = root.element("body");
		    Element response = body.element("response");
		    Element order = response.element("order");
		    QueryPVisitor queryPVisitor=new QueryPVisitor();
		    order.element("ticket").accept(queryPVisitor);
			return queryPVisitor;
	    }
		
		return null;
	}
}
