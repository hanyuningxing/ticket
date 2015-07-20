package com.cai310.lottery.support.zc;

import com.cai310.lottery.exception.DataException;

public class SczcCompoundItem implements ZcCompoundItem {

	private boolean nonGoal;
	private boolean oneGoal;
	private boolean twoGoal;
	private boolean threeGoal;

	public SczcCompoundItem() {
	}
	/**
	 * 解析用户输入的"0123"格式字符串.
	 * 
	 * @param betString
	 */
	public SczcCompoundItem(String betString) {
		if (betString.indexOf('0') >= 0)
			this.nonGoal = true;
		if (betString.indexOf('1') >= 0)
			this.oneGoal = true;
		if (betString.indexOf('2') >= 0)
			this.twoGoal = true;
		if (betString.indexOf('3') >= 0)
			this.threeGoal = true;
	}
	public SczcCompoundItem(byte bet) {
		if ((bet & 0x1) != 0) {
			nonGoal = true;
		}
		if ((bet & 0x1 << 1) != 0) {
			oneGoal = true;
		}
		if ((bet & 0x1 << 2) != 0) {
			twoGoal = true;
		}
		if ((bet & 0x1 << 3) != 0) {
			threeGoal = true;
		}
	}

	public boolean isNonGoal() {
		return nonGoal;
	}

	public void setNonGoal(boolean nonGoal) {
		this.nonGoal = nonGoal;
	}

	public boolean isOneGoal() {
		return oneGoal;
	}

	public void setOneGoal(boolean oneGoal) {
		this.oneGoal = oneGoal;
	}

	public boolean isThreeGoal() {
		return threeGoal;
	}

	public void setThreeGoal(boolean threeGoal) {
		this.threeGoal = threeGoal;
	}

	public boolean isTwoGoal() {
		return twoGoal;
	}

	public void setTwoGoal(boolean twoGoal) {
		this.twoGoal = twoGoal;
	}

	public byte toByte() {
		byte ret = 0;
		if (this.isNonGoal())
			ret |= 0x1;
		if (this.isOneGoal())
			ret |= 0x1 << 1;
		if (this.isTwoGoal())
			ret |= 0x1 << 2;
		if (this.isThreeGoal())
			ret |= 0x1 << 3;
		return ret;
	}

	public int selectedCount() {
		int c = 0;
		if (this.isNonGoal())
			c++;
		if (this.isOneGoal())
			c++;
		if (this.isTwoGoal())
			c++;
		if (this.isThreeGoal())
			c++;
		return c;
	}

	public boolean checkPass(char c) throws DataException {
		switch (c) {
		case '0':
			return this.isNonGoal();
		case '1':
			return this.isOneGoal();
		case '2':
			return this.isTwoGoal();
		case '3':
			return this.isThreeGoal();
		default:
			throw new DataException("未能匹配到选择项.");
		}
	}

	private static String[] betstring = new String[] { "", // 0
			"0", // 1
			"1", // 2
			"10",// 3
			"2", // 4
			"20",// 5
			"21", // 6
			"210",// 7
			"3",// 8
			"30", // 9
			"31", // 10
			"310", // 11
			"32", // 12
			"320", // 13
			"321", // 14
			"3210" // 15
	};

	public String toBetString() {
		return betstring[this.toByte()];
	}

	public int compareTo(ZcCompoundItem arg0) {
		return 0;
	}
}
