package com.cai310.lottery.ticket.protocol.response.dto;

import org.apache.commons.lang.StringUtils;


/**
 * <p>场次赔率信息类<p>
 * referodds(赔率)之间用"|"分隔，如胜平负：24.40|21.43|1.10<p>
 * bonusnum(比赛结果)结果与最终赔率用"|"分隔，如胜平负：胜|2.946069
 * @author jack
 *
 */
public class SeqOddsInfo {
	
	/**期号*/
	private String issue;
	/**场次号 1-99*/
	private Integer seq;
	/**赔率信息  赔率之间用"|"分隔，如胜平负：24.40|21.43|1.10 */
	private String referodds;
	/**比赛结果 结果与最终赔率用"|"分隔，如胜平负：胜|2.946069 */
	private String bonusnum;
	
	//zhuhui add 2011-04-26
	private Double bonusnumValue;

	public Double getBonusnumValue() {
		if(StringUtils.isNotBlank(this.bonusnum)){
			String[] arr = this.bonusnum.split("\\|");
			if(null!=arr&&arr.length==2){
				return Double.valueOf(arr[1]);
		 	}
		}
		return null;
	}
	
	public void setBonusnumValue(Double bonusnumValue) {
		this.bonusnumValue = bonusnumValue;
	}

	/**
	 * @return the issue
	 */
	public String getIssue() {
		return issue;
	}
	/**
	 * @param issue the issue to set
	 */
	public void setIssue(String issue) {
		this.issue = issue;
	}
	/**
	 * @return the seq
	 */
	public Integer getSeq() {
		return seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	/**
	 * @return the referodds
	 */
	public String getReferodds() {
		return referodds;
	}
	/**
	 * @param referodds the referodds to set
	 */
	public void setReferodds(String referodds) {
		this.referodds = referodds;
	}
	/**
	 * @return the bonusnum
	 */
	public String getBonusnum() {
		return bonusnum;
	}
	/**
	 * @param bonusnum the bonusnum to set
	 */
	public void setBonusnum(String bonusnum) {
		this.bonusnum = bonusnum;
	}

	
}
