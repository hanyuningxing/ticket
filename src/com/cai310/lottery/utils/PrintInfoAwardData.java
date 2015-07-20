package com.cai310.lottery.utils;

public class PrintInfoAwardData {
	/** 方案号*/
	private String schemeNum;
	/** 总奖金 */
	private Double prize;
	/** 税后奖金 */
	private Double returnPrize;
	/**
	 * @return the schemeNum
	 */
	public String getSchemeNum() {
		return schemeNum;
	}
	/**
	 * @param schemeNum the schemeNum to set
	 */
	public void setSchemeNum(String schemeNum) {
		this.schemeNum = schemeNum;
	}
	/**
	 * @return the prize
	 */
	public Double getPrize() {
		return prize;
	}
	/**
	 * @param prize the prize to set
	 */
	public void setPrize(Double prize) {
		this.prize = prize;
	}
	public void addPrize(Double prize){
		if(null!=getPrize()){
			this.setPrize(getPrize()+prize);
		}else{
			this.setPrize(prize);
		}
	}
	/**
	 * @return the returnPrize
	 */
	public Double getReturnPrize() {
		return returnPrize;
	}
	/**
	 * @param returnPrize the returnPrize to set
	 */
	public void setReturnPrize(Double returnPrize) {
		this.returnPrize = returnPrize;
	}
	public void addReturnPrize(Double returnPrize){
		if(null!=getReturnPrize()){
			this.setReturnPrize(getReturnPrize()+returnPrize);
		}else{
			this.setReturnPrize(returnPrize);
		}
	}
}
