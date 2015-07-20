package com.cai310.lottery.service.lottery.ticket.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ticket.LotterySupporter;
import com.cai310.lottery.service.lottery.ticket.LotterySupporterEntityManager;
import com.cai310.lottery.service.lottery.ticket.RemoteDataQuery;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.support.jclq.JclqMatch;
import com.cai310.lottery.support.jczq.JczqMatch;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.lottery.ticket.common.TypeTransaction;
import com.cai310.lottery.ticket.protocol.Protocol;
import com.cai310.lottery.ticket.protocol.response.dto.IssueInfo;
import com.cai310.lottery.ticket.protocol.response.dto.SeqOddsInfo;
import com.cai310.lottery.ticket.protocol.rlyg.RlygQueryPVisitor;
import com.cai310.lottery.ticket.protocol.rlyg.RlygUtil;
import com.cai310.lottery.ticket.protocol.zunao.utils.ZunaoQueryPVisitor;
import com.cai310.lottery.ticket.protocol.zunao.utils.ZunaoUtil;
import com.cai310.lottery.utils.CpdyjIssueVisitor;
import com.cai310.lottery.utils.CpdyjUtil;
import com.cai310.utils.DateUtil;
 
/**
 * 远程数据查询
 * @author jack
 *
 */
@Service("remoteDataQueryService")
public class RemoteDataQueryImpl implements RemoteDataQuery{

protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected LotterySupporterEntityManager lotterySupporterEntityManager;
	
	
	/* (non-Javadoc)
	 * @see com.cai310.lottery.ticket.RemoteDataQueryRMIOfTicket#getIssueInfo(com.cai310.lottery.common.Lottery, java.lang.Byte)
	 */
	@Override
	public IssueInfo getIssueInfo(Lottery lottery,Byte betType){
		LotterySupporter lotterySupporter = lotterySupporterEntityManager.findSupporterByLottery(lottery);
		if(lotterySupporter==null||lotterySupporter.getTicketSupporter()==null){
			throw new RuntimeException(lottery.getLotteryName()+"未设定相应的出票商.");
		}
		try{
			if(lotterySupporter.getTicketSupporter().equals(TicketSupporter.CPDYJ)){
				///大赢家
				if(lottery.equals(Lottery.SFZC)||lottery.equals(Lottery.PL))betType=0;
				CpdyjIssueVisitor cpdyjIssueVisitor =  CpdyjUtil.getIssue(lottery, betType);
				if(null!=cpdyjIssueVisitor&&cpdyjIssueVisitor.getIsSuccess()){
					IssueInfo issueInfo = new IssueInfo();
					issueInfo.setGameIssue(cpdyjIssueVisitor.getIssue());
					issueInfo.setEndTime(DateUtil.strToDate(cpdyjIssueVisitor.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
					issueInfo.setSingleEndTime(DateUtil.strToDate(cpdyjIssueVisitor.getSingleEndTime(),"yyyy-MM-dd HH:mm:ss"));
					issueInfo.setCompoundEndTime(DateUtil.strToDate(cpdyjIssueVisitor.getCompoundEndTime(),"yyyy-MM-dd HH:mm:ss"));
					return issueInfo;
				}
			}else if(lotterySupporter.getTicketSupporter().equals(TicketSupporter.ZUNAO)){
				///尊熬
				if(lottery.equals(Lottery.SFZC)||lottery.equals(Lottery.PL))betType=0;
				ZunaoQueryPVisitor zunaoQueryPVisitor =  new ZunaoUtil().getIssue(lottery, betType);
				if(null!=zunaoQueryPVisitor){
					IssueInfo issueInfo = new IssueInfo();
					issueInfo.setGameIssue(zunaoQueryPVisitor.getIssueNumber());
					issueInfo.setEndTime(DateUtil.strToDate(zunaoQueryPVisitor.getCloseTime(),"yyyy/MM/dd HH:mm:ss"));
					issueInfo.setSingleEndTime(DateUtil.strToDate(zunaoQueryPVisitor.getStopTime(),"yyyy/MM/dd HH:mm:ss"));
					issueInfo.setCompoundEndTime(DateUtil.strToDate(zunaoQueryPVisitor.getStopTime(),"yyyy/MM/dd HH:mm:ss"));
					return issueInfo;
				}
			}else if(lotterySupporter.getTicketSupporter().equals(TicketSupporter.RLYG)){
				///睿郎
				if(lottery.equals(Lottery.SFZC)||lottery.equals(Lottery.PL)||lottery.equals(Lottery.DLT))betType=0;
				RlygQueryPVisitor rlygQueryPVisitor =  new RlygUtil().getIssue(lottery, betType);
				if(null!=rlygQueryPVisitor){
					IssueInfo issueInfo = new IssueInfo();
					issueInfo.setGameIssue(rlygQueryPVisitor.getIssueNumber());
					issueInfo.setEndTime(DateUtils.addMinutes(DateUtil.strToDate(rlygQueryPVisitor.getStopTime(),"yyyyMMddHHmmss"),10));
					issueInfo.setSingleEndTime(DateUtil.strToDate(rlygQueryPVisitor.getStopTime(),"yyyyMMddHHmmss"));
					issueInfo.setCompoundEndTime(DateUtil.strToDate(rlygQueryPVisitor.getStopTime(),"yyyyMMddHHmmss"));
					return issueInfo;
				}
			}	
		}catch (Exception e) {
			logger.error("玩法｛"+lotterySupporter.getLotteryType().getLotteryName()+"｝取期旗号出错"+e.getMessage());
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.cai310.lottery.ticket.RemoteDataQueryRMIOfTicket#getIssueResult(com.cai310.lottery.common.Lottery, java.lang.String)
	 */
	@Override
	public String getIssueResult(Lottery lottery,String issueNumber){
		Protocol protocol = getProtocolOfTicketSupporter(lottery);	
		protocol.setTypeTransaction(TypeTransaction.KJGGCX);
		protocol.setLottery(lottery);
		protocol.setGameIssue(issueNumber);
		String responseMessage = null;
		String resultCode = "";
		try {
			responseMessage = protocol.messageSend();
			resultCode = protocol.parseResponseIssueResult(responseMessage);
		} catch (Exception e) {
			logger.error("期开奖结果获取异常..彩种:"+lottery.getLotteryName()+" 期号:"+issueNumber,e);
		}		
		return resultCode;
	}
	
	/* (non-Javadoc)
	 * @see com.cai310.lottery.ticket.RemoteDataQueryRMIOfTicket#fetchAwardData(com.cai310.lottery.common.Lottery, java.lang.String)
	 */
	@Override
	public String fetchAwardData(Lottery lottery,String issueNumber){
		Protocol protocol = getProtocolOfTicketSupporter(lottery);	
		protocol.setTypeTransaction(TypeTransaction.KJGGCX);
		protocol.setLottery(lottery);
		protocol.setGameIssue(issueNumber);
		String responseMessage = null;
		try {
			responseMessage = protocol.messageSend();
			responseMessage = this.getResponseXML(responseMessage);

		} catch (Exception e) {
			logger.error("期开奖结果获取异常..彩种:"+lottery.getLotteryName()+" 期号:"+issueNumber,e);
		}		
		return responseMessage;
	}
	
	protected String getResponseXML(String responseMessage){
		if(responseMessage==null || "".equals(responseMessage)){
			return "<?xml version=\"1.0\" encoding=\"GBK\"?></message>";
		}
		int flagIndex = responseMessage.indexOf("<");
		if(flagIndex!=-1){
			responseMessage = responseMessage.substring(flagIndex, responseMessage.length());
		}		
		return responseMessage;
	}

	
	/* (non-Javadoc)
	 * @see com.cai310.lottery.ticket.RemoteDataQueryRMIOfTicket#getDczcOdds(com.cai310.lottery.support.dczc.PlayType, java.lang.String)
	 */
	@Override
	public List<SeqOddsInfo> getDczcOdds(PlayType playType,String issueNumber){
		Protocol protocol = getProtocolOfTicketSupporter(Lottery.DCZC);	
		protocol.setTypeTransaction(TypeTransaction.DCPLCX);
		protocol.setLottery(Lottery.DCZC);
		protocol.setBetType((byte)playType.ordinal());
		protocol.setGameIssue(issueNumber);
		String responseMessage = null;
		List<SeqOddsInfo> ls = null;
		try {
			responseMessage = protocol.messageSend();
			logger.debug("赔率response："+responseMessage);
			ls = protocol.parseResponseDczcOdds(responseMessage);
		} catch (Exception e) {
			logger.error("单场获取期赔率异常.. 期号:"+issueNumber,e);
		}		
		return ls;
	}
	
	/**
	 * 根据彩种获取相应的出票方协议
	 * @param lottery
	 * @return
	 */
	private Protocol getProtocolOfTicketSupporter(Lottery lottery){
		Protocol protocol = null;
			LotterySupporter lotterySupporter = lotterySupporterEntityManager.findSupporterByLottery(lottery);
			if(lotterySupporter==null||lotterySupporter.getTicketSupporter()==null){
				throw new RuntimeException(lottery.getLotteryName()+"未设定相应的出票商.");
			}
			TicketSupporter ticketSupporter = lotterySupporter.getTicketSupporter();
			
//			switch(ticketSupporter){
//			case HONGBO:
//				protocol = new ProtocolHongbo();break;
//			case TICAI:
//				protocol = new ProtocolTicai();break;
//			case RLYG:
//				protocol = new ProtocolRlyg();break;
//			case BOZHONG:
//				protocol = new ProtocolBozhong();break;
//			case BOHAN:
//				protocol = new ProtocolBohan();
//				protocol.setIncremenIndex(printInterfaceIndexEntityManager.getBohanIncremenIndex(TicketConstant.BOHAN_INDEX_ID));
//				break;
//			default:
//				throw new RuntimeException(lottery.getLotteryName()+"没有对应的出票商.");
//			}
			return protocol;
		}	
	/* (non-Javadoc)
	 * @see com.cai310.lottery.ticket.RemoteDataQueryRMIOfTicket#getJczqSpByMatch(com.cai310.lottery.entity.lottery.jczq.JczqMatch)
	 */
	@Override
	public JczqMatch getJczqSpByMatch(JczqMatch jczqMatch){
//		ProtocolTicai protocol = new ProtocolTicai();	
//		protocol.setLottery(Lottery.JCZQ);
//		protocol.setBetType(Byte.valueOf("0"));
//		protocol.setTypeTransaction(TypeTransaction.ZZSP_JC);
//		protocol.setLotttime(jczqMatch.getMatchDate()+"");
//		protocol.setTeamId(JczqUtil.getLineId(jczqMatch.getMatchKey()));
//		String responseMessage = null;
//		try {
//			responseMessage = protocol.messageSend();
//		} catch (IOException e) {
//			logger.error("期信息获取异常..彩种:"+Lottery.JCZQ.getLotteryName()+" 球队号:"+jczqMatch.getMatchKey(),e);
//		}
//		String spValue = getXmlElement(responseMessage,"element");
//		if(StringUtils.isNotBlank(spValue)){
//			String GRESULTBONUS = this.getXmlElement(spValue, "GRESULTBONUS");
//			jczqMatch.setSpfResultSp(getSpValue(GRESULTBONUS));
//			String TOTALBONUS = this.getXmlElement(spValue, "TOTALBONUS");
//			jczqMatch.setJqsResultSp(getSpValue(TOTALBONUS));
//			String SCOREBONUS = this.getXmlElement(spValue, "SCOREBONUS");
//			jczqMatch.setBfResultSp(getSpValue(SCOREBONUS));
//			String HRESULTBONUS = this.getXmlElement(spValue, "HRESULTBONUS");
//			jczqMatch.setBqqResultSp(getSpValue(HRESULTBONUS));
//		}
		return jczqMatch;
	}
	/* (non-Javadoc)
	 * @see com.cai310.lottery.ticket.RemoteDataQueryRMIOfTicket#getJclqSpByMatch(com.cai310.lottery.entity.lottery.jclq.JclqMatch)
	 */
	@Override
	public JclqMatch getJclqSpByMatch(JclqMatch jclqMatch){
//		ProtocolTicai protocol = new ProtocolTicai();	
//		protocol.setLottery(Lottery.JCLQ);
//		protocol.setBetType(Byte.valueOf("0"));
//		protocol.setTypeTransaction(TypeTransaction.ZZSP_JC);
//		protocol.setLotttime(jclqMatch.getMatchDate()+"");
//		protocol.setTeamId(JczqUtil.getLineId(jclqMatch.getMatchKey()));
//		String responseMessage = null;
//		try {
//			responseMessage = protocol.messageSend();
//		} catch (IOException e) {
//			logger.error("期信息获取异常..彩种:"+Lottery.JCLQ.getLotteryName()+" 球队号:"+jclqMatch.getMatchKey(),e);
//		}
//		String spValue = getXmlElement(responseMessage,"element");
//		if(StringUtils.isNotBlank(spValue)){
//			String GRESULTBONUS = this.getXmlElement(spValue, "GRESULTBONUS");
//			jclqMatch.setSfResultSp(getSpValue(GRESULTBONUS));
//			String HRESULTBONUS = this.getXmlElement(spValue, "HRESULTBONUS");
//			jclqMatch.setRfsfResultSp(getSpValue(HRESULTBONUS));
//			String SCOREBONUS = this.getXmlElement(spValue, "SCOREBONUS");
//			jclqMatch.setSfcResultSp(getSpValue(SCOREBONUS));
//			String TOTALBONUS = this.getXmlElement(spValue, "TOTALBONUS");
//			jclqMatch.setDxfResultSp(getSpValue(TOTALBONUS));
//		}
		return jclqMatch;
	}
	protected Double getSpValue(String spValue){
		if(StringUtils.isNotBlank(spValue)){
			Double d = Double.valueOf(spValue);
			if(d>0){
				return d/100;
			}
		}
		return 0d;
	}
	/**
	 * 获取元素
	 * @param responseMessage
	 * @param elementFlag
	 * @return
	 */
	protected String getXmlElement(String responseMessage,String elementFlag){
		Pattern pattern=Pattern.compile("<"+elementFlag+">(.+?)</"+elementFlag+">");
		Matcher m=pattern.matcher(responseMessage);
		String returnValue="";
		if(m.find()){
			returnValue = m.group(1);
		}
		return returnValue;
	}
	public static void main(String[] args) {
		RemoteDataQueryImpl  remoteDataQuery  = new RemoteDataQueryImpl();
		JczqMatch jczqMatch = new JczqMatch();
		jczqMatch.setMatchKey("20110720-001");
		jczqMatch.setMatchDate(20110720);
		remoteDataQuery.getJczqSpByMatch(jczqMatch);
	}
}
