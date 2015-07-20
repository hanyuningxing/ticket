package com.cai310.lottery.utils;

public class CpdyjAwardData {
	/** 方案id,对应printDetail表的id */
	private String schemeId;
	/** 打印总金额 */
	private Double cost;
	/** 总奖金 */
	private Double prize;
	/** 税后奖金 */
	private Double returnPrize;
	/** 是否完成封装 */
	private Boolean packageUp;
	
	private final String awardDataSplitString="_";
	
	public CpdyjAwardData(){
		
	}
	public CpdyjAwardData(String awardData) throws Exception{
		String[] arr=awardData.split(awardDataSplitString);
		if(arr.length!=4)throw new Exception();
		this.setSchemeId(arr[0]);
		this.setCost(Double.valueOf(arr[1]));
		this.setPrize(Double.valueOf(arr[2]));
		this.setReturnPrize(Double.valueOf(arr[3]));
		this.setPackageUp(Boolean.TRUE);
	}
	
	/**
	 * @return the schemeId
	 */
	public String getSchemeId() {
		return schemeId;
	}
	/**
	 * @param schemeId the schemeId to set
	 */
	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
	/**
	 * @return the cost
	 */
	public Double getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(Double cost) {
		this.cost = cost;
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
	/**
	 * @return the packageUp
	 */
	public Boolean getPackageUp() {
		return packageUp;
	}
	/**
	 * @param packageUp the packageUp to set
	 */
	public void setPackageUp(Boolean packageUp) {
		this.packageUp = packageUp;
	}
	
}
