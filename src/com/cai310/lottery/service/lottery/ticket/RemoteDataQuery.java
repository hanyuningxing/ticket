package com.cai310.lottery.service.lottery.ticket;

import java.util.List;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.support.jclq.JclqMatch;
import com.cai310.lottery.support.jczq.JczqMatch;
import com.cai310.lottery.ticket.protocol.response.dto.IssueInfo;
import com.cai310.lottery.ticket.protocol.response.dto.SeqOddsInfo;

public interface RemoteDataQuery {

	/**
	 * 获取出票方期信息
	 * @param lottery
	 * @param issueNumber
	 * @return List<IssueInfo> 期数据列表
	 */
	public IssueInfo getIssueInfo(Lottery lottery, Byte betType);

	/**
	 * 获取开奖公告
	 * @param lottery
	 * @param issueNumber
	 * @return 开奖结果 "0102030405"
	 */
	public String getIssueResult(Lottery lottery, String issueNumber);

	/**
	 * 获取开奖公告
	 * @param lottery
	 * @param issueNumber
	 * @return 
	 */
	public String fetchAwardData(Lottery lottery, String issueNumber);

	/**
	 * 获取单场足彩赔率
	 * @param lottery
	 * @param playType
	 * @param issueNumber
	 * @return 
	 */
	public List<SeqOddsInfo> getDczcOdds(PlayType playType, String issueNumber);

	/**
	 * 获取最终sp
	 * @param lottery
	 * @param issueNumber
	 * @return List<IssueInfo> 期数据列表
	 * <?xml version="1.0" encoding="UTF-8" ?><caipiaotv><ctrl><errorcode>0</errorcode><command>50203</command><userid>2011040800000000000000305230</userid><key>38B0F0D90B3EB98E5A926D0C3FDB8550</key></ctrl>
	 * <list>
	 *          <element>
	 *                <LOTTTIME>20110720</LOTTTIME>
	 *                <GAMERESULT>3</GAMERESULT>
	 *                <TOTALPOINS>5</TOTALPOINS>
	 *                <HRESULTBONUS>568</HRESULTBONUS>
	 *                <LOTTERYTYPE>JCFB</LOTTERYTYPE>
	 *                <SCORE>32</SCORE>
	 *                <SCOREBONUS>4942</SCOREBONUS>
	 *                <TOTALBONUS>2774</TOTALBONUS>
	 *                <GRESULTBONUS>288</GRESULTBONUS>
	 *                <TEAMID>001</TEAMID>
	 *                <WEEKID>3</WEEKID>
	 *                <HRESULT>33</HRESULT>
	 *             </element>
	 *    </list></caipiaotv>
	 */
	public JczqMatch getJczqSpByMatch(JczqMatch jczqMatch);

	public JclqMatch getJclqSpByMatch(JclqMatch jclqMatch);

}