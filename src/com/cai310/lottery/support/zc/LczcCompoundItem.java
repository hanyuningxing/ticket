package com.cai310.lottery.support.zc;

import com.cai310.lottery.exception.DataException;

public class LczcCompoundItem implements ZcCompoundItem {

	private boolean homeWin;
	private boolean guestWin;
	private boolean draw;

	public LczcCompoundItem() {
	}

	/**
	 * 解析用户输入的"310"格式字符串.
	 * 
	 * @param betString
	 */
	public LczcCompoundItem(String betString) {
		if (betString.indexOf('3') >= 0)
			this.homeWin = true;
		if (betString.indexOf('1') >= 0)
			this.draw = true;
		if (betString.indexOf('0') >= 0)
			this.guestWin = true;
	}

	public LczcCompoundItem(byte bet) {
		if ((bet & 0x1) != 0) {
			homeWin = true;
		}
		if ((bet & 0x1 << 1) != 0) {
			draw = true;
		}
		if ((bet & 0x1 << 2) != 0) {
			guestWin = true;
		}
	}

	public boolean isHomeWin() {
		return homeWin;
	}

	public void setHomeWin(boolean homeWin) {
		this.homeWin = homeWin;
	}

	public boolean isGuestWin() {
		return guestWin;
	}

	public void setGuestWin(boolean guestWin) {
		this.guestWin = guestWin;
	}

	public boolean isDraw() {
		return draw;
	}

	public boolean validateEmpty() {
		return !(this.homeWin || this.guestWin || this.draw);
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public byte toByte() {
		byte ret = 0;
		if (this.isHomeWin())
			ret |= 0x1;
		if (this.isDraw())
			ret |= 0x1 << 1;
		if (this.isGuestWin())
			ret |= 0x1 << 2;
		return ret;
	}

	public int selectedCount() {
		int c = 0;
		if (this.isHomeWin())
			c++;
		if (this.isGuestWin())
			c++;
		if (this.isDraw())
			c++;
		return c;
	}

	/**
	 * 根据投注内容生成易于阅读的“310”格式的字符串
	 * 
	 * @return
	 */
	public String toBetString() {

		if (this.validateEmpty())
			return "*";

		StringBuffer sb = new StringBuffer();
		if (this.isHomeWin())
			sb.append('3');
		if (this.isDraw())
			sb.append('1');
		if (this.isGuestWin())
			sb.append('0');
		return sb.toString();

	}

	public boolean checkPass(char c) throws DataException {
		if (this.validateEmpty())
			return true;
		switch (c) {
		case '0':
			return this.isGuestWin();
		case '1':
			return this.isDraw();
		case '3':
			return this.isHomeWin();
		default:
			throw new DataException("未能匹配到选择项.");
		}
	}

	public int compareTo(ZcCompoundItem arg0) {
		return 0;
	}
}
