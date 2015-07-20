package com.cai310.lottery.support;

import java.io.Serializable;

public class WinItem implements Serializable {
	private static final long serialVersionUID = -3326404907328176514L;

	private String winName;
	private int winUnit;

	public WinItem(String winName, int winUnit) {
		super();
		this.winName = winName;
		this.winUnit = winUnit;
	}

	public String getWinName() {
		return winName;
	}

	public void setWinName(String winName) {
		this.winName = winName;
	}

	public int getWinUnit() {
		return winUnit;
	}
	
	
	public void setWinUnit(int winUnit) {
		this.winUnit = winUnit;
	}

}
