package com.cai310.lottery.common;

public enum CounterType {

	ISSUE("期"),DAY("天"),MONTH("月"),YEAR("年");
	
	private String counterName;
	
	private CounterType(String counterName){
		this.counterName = counterName;
	}

	public String getCounterName() {
		return counterName;
	}
	
	public byte getTypeValue(){
		return (byte) this.ordinal();
	}
}
