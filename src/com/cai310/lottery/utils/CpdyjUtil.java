package com.cai310.lottery.utils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.utils.MD5;

public class CpdyjUtil {
	private static final String reUrl="http://i.zc310.net:8089/bin/LotSaleHttp.dll";
	private static final String bakReUrl="http://i2.zc310.net:8089/bin/LotSaleHttp.dll";
	private static final String wAgent="wAgent";
	private static final String wAgentValue="832693";
	private static final String wAction="wAction";
	private static final String wActionValue="110";
	private static final String wAwardActionValue="112";
	private static final String wMsgID="wMsgID";
	private static final String wSign="wSign";
	private static final String key="568qsdkisq2123wq";
	private static final String wParam="wParam";
	///获取开奖信息
		public static String getReUrl(String LotIssue,String LotID,Long wMsgIDValue){
			if(StringUtils.isBlank(LotIssue))return null;
			if(StringUtils.isBlank(LotID))return null;
			String wParamValue="LotID="+LotID.trim()+"_"+"LotIssue="+LotIssue.trim();
			StringBuffer sb = new StringBuffer();
			sb.append(reUrl);
			sb.append("?");
			sb.append(wAgent+"="+wAgentValue);
			sb.append("&");
			sb.append(wAction+"="+wActionValue);
			sb.append("&");
			sb.append(wMsgID+"="+wMsgIDValue);
			sb.append("&");
			sb.append(wSign+"="+MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
			sb.append("&");
			sb.append(wParam+"="+wParamValue);
			return sb.toString();
		}
		///获取开奖信息（后备）
		public static String getBakReUrl(String LotIssue,String LotID,Long wMsgIDValue){
			if(StringUtils.isBlank(LotIssue))return null;
			if(StringUtils.isBlank(LotID))return null;
			String wParamValue="LotID="+LotID.trim()+"_"+"LotIssue="+LotIssue.trim();
			StringBuffer sb = new StringBuffer();
			sb.append(bakReUrl);
			sb.append("?");
			sb.append(wAgent+"="+wAgentValue);
			sb.append("&");
			sb.append(wAction+"="+wActionValue);
			sb.append("&");
			sb.append(wMsgID+"="+wMsgIDValue);
			sb.append("&");
			sb.append(wSign+"="+MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
			sb.append("&");
			sb.append(wParam+"="+wParamValue);
			return sb.toString();
		}
		
		///查询中奖信息
		public static String getReAwardUrl(String LotIssue,String LotID,Long wMsgIDValue){
			if(StringUtils.isBlank(LotIssue))return null;
			if(StringUtils.isBlank(LotID))return null;
			String wParamValue="LotID="+LotID.trim()+"_"+"LotIssue="+LotIssue.trim();
			StringBuffer sb = new StringBuffer();
			sb.append(reUrl);
			sb.append("?");
			sb.append(wAgent+"="+wAgentValue);
			sb.append("&");
			sb.append(wAction+"="+wAwardActionValue);
			sb.append("&");
			sb.append(wMsgID+"="+wMsgIDValue);
			sb.append("&");
			sb.append(wSign+"="+MD5.md5(wAgentValue+wAwardActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
			sb.append("&");
			sb.append(wParam+"="+wParamValue);
			return sb.toString();
		}
		///查询中奖信息（后备）
		public static String getBakAwardReUrl(String LotIssue,String LotID,Long wMsgIDValue){
			if(StringUtils.isBlank(LotIssue))return null;
			if(StringUtils.isBlank(LotID))return null;
			String wParamValue="LotID="+LotID.trim()+"_"+"LotIssue="+LotIssue.trim();
			StringBuffer sb = new StringBuffer();
			sb.append(bakReUrl);
			sb.append("?");
			sb.append(wAgent+"="+wAgentValue);
			sb.append("&");
			sb.append(wAction+"="+wAwardActionValue);
			sb.append("&");
			sb.append(wMsgID+"="+wMsgIDValue);
			sb.append("&");
			sb.append(wSign+"="+MD5.md5(wAgentValue+wAwardActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
			sb.append("&");
			sb.append(wParam+"="+wParamValue);
			return sb.toString();
		}
	public static CpdyjIssueVisitor getIssue(Lottery lottery,Byte betType) throws DataException, IOException, DocumentException{
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
		
		String returnString = HttpclientUtil.Utf8HttpClientUtils(sb.toString(), ParamMap);
		Document doc=DocumentHelper.parseText(returnString);
		CpdyjIssueVisitor cpdyjIssueVisitor=new CpdyjIssueVisitor();
		doc.accept(cpdyjIssueVisitor);
		return cpdyjIssueVisitor;
	}
	public static void main(String[] args) throws DataException, IOException, DocumentException {
		CpdyjUtil.getIssue(Lottery.SSQ, null);
	}
//	public static CpResultVisitor getIssueAward(Lottery lottery,Byte betType,String issueNumber) throws DataException, IOException, DocumentException{
//		Ticket ticket = new Ticket();
//		ticket.setBetType(betType);
//		ticket.setLotteryType(lottery);
//		String LotID = getCpdyjLotteryId(ticket);
//		StringBuffer wParamValueSb = new StringBuffer();
//		wParamValueSb.append("LotID="+LotID.trim());
//		wParamValueSb.append("_LotIssue="+issueNumber.trim());
//		String wParamValue=wParamValueSb.toString();
//		String wMsgIDValue = System.currentTimeMillis()+"";
//		StringBuffer sb = new StringBuffer();
//		String wActionValue = "110";
//		sb.append(reUrl);
//		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
//		ParamMap.put("wAgent", wAgentValue);
//		ParamMap.put("wAction", wActionValue);
//		ParamMap.put("wMsgID", wMsgIDValue);
//		ParamMap.put("wSign", MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
//		ParamMap.put("wParam", wParamValue);
//		
//		String returnString = HttpclientUtil.Utf8HttpClientUtils(sb.toString(), ParamMap);
//		Document doc=DocumentHelper.parseText(returnString);
//		CpResultVisitor cpResultVisitor=new CpResultVisitor();
//		doc.accept(cpResultVisitor);
//		return cpResultVisitor;
//	}
	/*OrderID=D2010128298
	LotID=33
	LotIssue=2010008
	LotMoney=12
	LotMulti=2
	OneMoney=2
	LotCode=1|68,2,9;6|5,1,8
	Attach=投注测试*/
	public static String getCpdyjLotteryId(Ticket ticket){
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
	
}
