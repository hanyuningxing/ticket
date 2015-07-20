package com.cai310.lottery.support;

public  class PrintWonItem{
	/** 彩票号*/                          
	private Integer index;                
                                        
	/** 中奖金额*/                          
	private Double totalPrize;        
	
	/** 税后金额*/                          
	private Double totalPrizeAfterTax;
	                                    
	/** 中奖详情*/                          
	private String wonDetail;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}



	public String getWonDetail() {
		return wonDetail;
	}

	public void setWonDetail(String wonDetail) {
		this.wonDetail = wonDetail;
	}

	public Double getTotalPrizeAfterTax() {
		return totalPrizeAfterTax;
	}

	public void setTotalPrizeAfterTax(Double totalPrizeAfterTax) {
		this.totalPrizeAfterTax = totalPrizeAfterTax;
	}

	public Double getTotalPrize() {
		return totalPrize;
	}

	public void setTotalPrize(Double totalPrize) {
		this.totalPrize = totalPrize;
	}           
	
}
