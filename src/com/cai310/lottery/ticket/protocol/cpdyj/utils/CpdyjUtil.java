package com.cai310.lottery.ticket.protocol.cpdyj.utils;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.MD5;
import com.google.common.collect.Lists;

public abstract class CpdyjUtil {
	private static final String reUrl="http://t.zc310.net:8089/bin/LotSaleHttp.dll";
	private static final String bakReUrl="http://i2.zc310.net:8089/bin/LotSaleHttp.dll";
	private static final String wAgent="wAgent";
	private static final String wAgentValue="3821";
	private static final String wAction="wAction";
	private static final String wMsgID="wMsgID";
	private static final String wSign="wSign";
	private static final String key="a8b8c8d8e8f8g8h8";///key这里留空。根据实制填
	private static final String wParam="wParam";
	/**
	 * 发送彩票
	 * @param ticket
	 * @return
	 * @throws DataException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public CpResultVisitor sendTicket(Ticket ticket) throws DataException, IOException, DocumentException{
		if(null==ticket)return null;
		String OrderID = ticket.getId()+"";
		String LotID = getCpdyjLotteryId(ticket);
		String LotIssue = getLotIssue(ticket);
		String LotMoney = ticket.getSchemeCost()+"";
		String LotCode = getLotCode(ticket);
		String LotMulti = ticket.getMultiple()+"";
		String Attach = getAttach(ticket);
		String OneMoney = getOneMoney(ticket);
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("OrderID="+OrderID.trim());
		wParamValueSb.append("_LotID="+LotID.trim());
		wParamValueSb.append("_LotIssue="+LotIssue.trim());
		wParamValueSb.append("_LotMoney="+LotMoney.trim());
		wParamValueSb.append("_LotCode="+LotCode.trim());
		wParamValueSb.append("_LotMulti="+LotMulti.trim());
		wParamValueSb.append("_Attach="+Attach.trim());
		wParamValueSb.append("_OneMoney="+OneMoney.trim());
		String wParamValue=wParamValueSb.toString();
		String wMsgIDValue = ticket.getId()+"";
		StringBuffer sb = new StringBuffer();
		String wActionValue = "101";
		sb.append(reUrl);
		
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("wAgent", wAgentValue);
		ParamMap.put("wAction", wActionValue);
		ParamMap.put("wMsgID", wMsgIDValue);
		ParamMap.put("wSign", MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
		ParamMap.put("wParam", wParamValue);
		
		
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
		String returnString = HttpclientUtil.Utf8HttpClientUtils(sb.toString(), ParamMap);
		Document doc=DocumentHelper.parseText(returnString);
		CpResultVisitor cpResultVisitor=new CpResultVisitor();
		doc.accept(cpResultVisitor);
		return cpResultVisitor;
	}
	public CpResultVisitor getIssue(Lottery lottery,Byte betType) throws DataException, IOException, DocumentException{
		Ticket ticket = new Ticket();
		ticket.setBetType(betType);
		ticket.setLotteryType(lottery);
		String LotID = getCpdyjLotteryId(ticket);
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("LotID="+LotID.trim());
		String wParamValue=wParamValueSb.toString();
		String wMsgIDValue = System.currentTimeMillis()+"";
		StringBuffer sb = new StringBuffer();
		String wActionValue = "104";
		sb.append(reUrl);
		
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("wAgent", wAgentValue);
		ParamMap.put("wAction", wActionValue);
		ParamMap.put("wMsgID", wMsgIDValue);
		ParamMap.put("wSign", MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
		ParamMap.put("wParam", wParamValue);
		
		
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
		String returnString = HttpclientUtil.Utf8HttpClientUtils(sb.toString(), ParamMap);
		Document doc=DocumentHelper.parseText(returnString);
		CpResultVisitor cpResultVisitor=new CpResultVisitor();
		doc.accept(cpResultVisitor);
		return cpResultVisitor;
	}
	
	
	public CpResultVisitor getIssueAward(Lottery lottery,Byte betType,String issueNumber) throws DataException, IOException, DocumentException{
		Ticket ticket = new Ticket();
		ticket.setBetType(betType);
		ticket.setLotteryType(lottery);
		String LotID = getCpdyjLotteryId(ticket);
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("LotID="+LotID.trim());
		wParamValueSb.append("_LotIssue="+issueNumber.trim());
		String wParamValue=wParamValueSb.toString();
		String wMsgIDValue = System.currentTimeMillis()+"";
		StringBuffer sb = new StringBuffer();
		String wActionValue = "110";
		sb.append(reUrl);
		
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("wAgent", wAgentValue);
		ParamMap.put("wAction", wActionValue);
		ParamMap.put("wMsgID", wMsgIDValue);
		ParamMap.put("wSign", MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
		ParamMap.put("wParam", wParamValue);
		
		
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
		String returnString = HttpclientUtil.Utf8HttpClientUtils(sb.toString(), ParamMap);
		Document doc=DocumentHelper.parseText(returnString);
		CpResultVisitor cpResultVisitor=new CpResultVisitor();
		doc.accept(cpResultVisitor);
		return cpResultVisitor;
	}
	
	
	
	
	
	public static void main(String[] args) throws DataException, IOException, DocumentException {
		CpdyjUtil cpdyjUtil = new Weflare3dCpdyjUtil();
		cpdyjUtil.getIssue(Lottery.EL11TO5,Byte.valueOf("0"));
		cpdyjUtil.getIssueAward(Lottery.SSC,Byte.valueOf("0"),"2011033002");
	}
	public QueryPVisitor confirmTicket(Ticket ticket) throws DataException, IOException, DocumentException{
		if(null==ticket)return null;
		String OrderID = ticket.getOrderNo()+"";
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("OrderID="+OrderID.trim());
		String wParamValue=wParamValueSb.toString();
		String wMsgIDValue = System.currentTimeMillis()+"";
		StringBuffer sb = new StringBuffer();
		String wActionValue = "102";
		sb.append(reUrl);
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("wAgent", wAgentValue);
		ParamMap.put("wAction", wActionValue);
		ParamMap.put("wMsgID", wMsgIDValue);
		ParamMap.put("wSign", MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
		ParamMap.put("wParam", wParamValue);
		
		
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
		String returnString = HttpclientUtil.Utf8HttpClientUtils(sb.toString(), ParamMap);
		Document doc=DocumentHelper.parseText(returnString);
		QueryPVisitor queryPVisitor=new QueryPVisitor();
		doc.accept(queryPVisitor);
		return queryPVisitor;
	}
	public abstract String getLotIssue(Ticket ticket);
	public abstract String getLotCode(Ticket ticket) throws DataException;
	public abstract String getAttach(Ticket ticket);
	public abstract String getOneMoney(Ticket ticket) throws DataException;
	/*OrderID=D2010128298
	LotID=33
	LotIssue=2010008
	LotMoney=12
	LotMulti=2
	OneMoney=2
	LotCode=1|68,2,9;6|5,1,8
	Attach=投注测试*/
	public String getCpdyjLotteryId(Ticket ticket){
		if(null==ticket)return null;
		if(null==ticket.getLotteryType())return null;
		if(Lottery.SSC.equals(ticket.getLotteryType())){
			return "10401";
		}
		if(Lottery.JCZQ.equals(ticket.getLotteryType())){
			return "42";
		}else if(Lottery.JCLQ.equals(ticket.getLotteryType())){
			return "43";
		}else if(Lottery.WELFARE3D.equals(ticket.getLotteryType())){
			return "52";
		}else if(Lottery.DLT.equals(ticket.getLotteryType())){
			return "23529";
		}else if(Lottery.SSQ.equals(ticket.getLotteryType())){
			return "51";
		}else if(Lottery.SFZC.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.zc.PlayType playType = com.cai310.lottery.support.zc.PlayType.values()[ticket.getBetType()];
			if(playType.equals(com.cai310.lottery.support.zc.PlayType.SFZC14)){
				return "11";
			}else if(playType.equals(com.cai310.lottery.support.zc.PlayType.SFZC9)){
				return "19";
			}
		}else if(Lottery.SCZC.equals(ticket.getLotteryType())){
			return "18";
		}else if(Lottery.LCZC.equals(ticket.getLotteryType())){
			return "16";
		}else if(Lottery.EL11TO5.equals(ticket.getLotteryType())){
			return "23009";
		}else if(Lottery.PL.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.pl.PlPlayType playType = com.cai310.lottery.support.pl.PlPlayType.values()[ticket.getBetType()];
			if(playType.equals(com.cai310.lottery.support.pl.PlPlayType.P5Direct)){
				return "35";
			}else{
				return "33";
			}
		}else if(Lottery.SEVEN.equals(ticket.getLotteryType())){
			return "23528";
		}else if(Lottery.DCZC.equals(ticket.getLotteryType())){
			return "41";
		}
		return null;
	}
	
	protected Map<String, Object> map;
	public CpdyjUtil(Ticket ticket){
		if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(null!=ticket&&null!=ticket.getContent())map = JsonUtil.getMap4Json(ticket.getContent());
		}
	}
	public CpdyjUtil(){
		
	}
	public Map<String, Object> getTicketContentMap(Ticket ticket){
		if(null!=map)return map;
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
