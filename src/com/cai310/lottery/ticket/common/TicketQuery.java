package com.cai310.lottery.ticket.common;

import java.util.Date;

import com.cai310.lottery.common.Lottery;
import com.cai310.utils.DateUtil;

public class TicketQuery {
	
	private Lottery lotteryType;
	private String issueNumber;
	private Byte betType;
	private Date endtime;//期销售截止时间
	private Date updatetime;//更新时间

	/**
	 * @return the lotteryType
	 */
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType
	 *            the lotteryType to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	/**
	 * @return the issueNumber
	 */
	public String getIssueNumber() {
		return issueNumber;
	}

	/**
	 * @param issueNumber
	 *            the issueNumber to set
	 */
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	/**
	 * @return the betType
	 */
	public Byte getBetType() {
		return betType;
	}

	/**
	 * @param betType the betType to set
	 */
	public void setBetType(Byte betType) {
		this.betType = betType;
	}

	/**
	 * @param overtime the overtime to set
	 */
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	/**
	 * @param updatetime the updatetime to set
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	/**
	 * 是否需要更新出票方期信息
	 * @return
	 */
	public boolean isNeedUpdate(){
		int distanceMinute = Lottery.isKeno(lotteryType)?1:30;//更新间隔
		return DateUtil.getdecDateOfMinute(new Date(),distanceMinute).compareTo(updatetime)>0;
	}

	/**
	 * 出票方提供的彩种期是否过期
	 * @return boolean
	 */
	public boolean isOvertime(){
		if(null==endtime) return true;
		if(lotteryType.equals(Lottery.JCZQ)||lotteryType.equals(Lottery.JCLQ))return false;///竞彩永不过期
		return new Date().compareTo(endtime)>0;
	}

	/* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((issueNumber == null) ? 0 : issueNumber.hashCode());
		result = prime * result
				+ ((lotteryType == null) ? 0 : lotteryType.hashCode());
		return result;
	}

	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TicketQuery other = (TicketQuery) obj;
		if (issueNumber == null) {
			if (other.issueNumber != null)
				return false;
		} else if (!issueNumber.equals(other.issueNumber))
			return false;
		if (lotteryType != other.lotteryType)
			return false;
		return true;
	}

	/**
	 * @return the updatetime
	 */
	public Date getUpdatetime() {
		return updatetime;
	}
}
