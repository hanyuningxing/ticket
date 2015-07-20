package com.cai310.lottery.support;

import org.apache.commons.lang.builder.ToStringBuilder;

public class CapacityChaseBean {

	private String startChasePeriodNum; //开始期号
	
	private String periodSizeOfChase;	//追号期数
	
	private String startMultiple;		//起始倍数

	private String hasInvested;			 //已经投入
	
	private String expectedHit;			//预计命中
	
	
	private String allafterlucre;    //全程收益金额
	
	private String  befortermmember;  // 前几期

	private String beforelc;		  // 前几期金额
	
	private String aferlc;			  //之后累计收益不低于
	
	private String all_lucrep_select; //全程收益率

	private String befortermmemberp;	// 期数
	
	private String before_lcp_select;  // 前几期金额
	
	private String aferlcp_select;     //之后收益率不低于
	
	
	public String getStartChasePeriodNum() {
		return startChasePeriodNum;
	}


	public void setStartChasePeriodNum(String startChasePeriodNum) {
		this.startChasePeriodNum = startChasePeriodNum;
	}


	public String getPeriodSizeOfChase() {
		return periodSizeOfChase;
	}


	public void setPeriodSizeOfChase(String periodSizeOfChase) {
		this.periodSizeOfChase = periodSizeOfChase;
	}


	public String getStartMultiple() {
		return startMultiple;
	}


	public void setStartMultiple(String startMultiple) {
		this.startMultiple = startMultiple;
	}


	public String getHasInvested() {
		return hasInvested;
	}


	public void setHasInvested(String hasInvested) {
		this.hasInvested = hasInvested;
	}


	public String getExpectedHit() {
		return expectedHit;
	}


	public void setExpectedHit(String expectedHit) {
		this.expectedHit = expectedHit;
	}


	public String getAllafterlucre() {
		return allafterlucre;
	}


	public void setAllafterlucre(String allafterlucre) {
		this.allafterlucre = allafterlucre;
	}


	public String getBefortermmember() {
		return befortermmember;
	}


	public void setBefortermmember(String befortermmember) {
		this.befortermmember = befortermmember;
	}


	public String getBeforelc() {
		return beforelc;
	}


	public void setBeforelc(String beforelc) {
		this.beforelc = beforelc;
	}


	public String getAferlc() {
		return aferlc;
	}


	public void setAferlc(String aferlc) {
		this.aferlc = aferlc;
	}


	public String getAll_lucrep_select() {
		return all_lucrep_select;
	}


	public void setAll_lucrep_select(String all_lucrep_select) {
		this.all_lucrep_select = all_lucrep_select;
	}


	public String getBefortermmemberp() {
		return befortermmemberp;
	}


	public void setBefortermmemberp(String befortermmemberp) {
		this.befortermmemberp = befortermmemberp;
	}


	public String getBefore_lcp_select() {
		return before_lcp_select;
	}


	public void setBefore_lcp_select(String before_lcp_select) {
		this.before_lcp_select = before_lcp_select;
	}


	public String getAferlcp_select() {
		return aferlcp_select;
	}


	public void setAferlcp_select(String aferlcp_select) {
		this.aferlcp_select = aferlcp_select;
	}


	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
