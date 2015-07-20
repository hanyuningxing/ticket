package com.cai310.lottery.support.zc;

import com.cai310.lottery.exception.DataException;

public class SfzcCompoundItem implements ZcCompoundItem {

	private int lineId;
	/** 场次ID */
	private boolean homeWin;
	private boolean guestWin;
	private boolean draw;
	private boolean shedan;

	public SfzcCompoundItem() {
	}

	public SfzcCompoundItem(int lineId) {
		this.lineId = lineId;
	}

	/**
	 * 解析用户输入的"310"格式字符串.
	 * 
	 * @param betString
	 */
	public SfzcCompoundItem(String betString, int lineId) {
		this.lineId = lineId;
		if (betString.indexOf('4') >= 0)
			this.shedan = true;
		if (betString.indexOf('3') >= 0)
			this.homeWin = true;
		if (betString.indexOf('1') >= 0)
			this.draw = true;
		if (betString.indexOf('0') >= 0)
			this.guestWin = true;
	}

	public SfzcCompoundItem(byte bet, int lineId) {
		this.lineId = lineId;
		if ((bet & 0x1) != 0) {
			homeWin = true;
		}
		if ((bet & 0x1 << 1) != 0) {
			draw = true;
		}
		if ((bet & 0x1 << 2) != 0) {
			guestWin = true;
		}
		if ((bet & 0x1 << 3) != 0) {
			shedan = true;
		}
	}

	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
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

	public boolean isShedan() {
		return shedan;
	}

	public void setShedan(boolean shedan) {
		this.shedan = shedan;
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
		if (this.isShedan())
			ret |= 0x1 << 3;
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
			return String.valueOf(ZcUtils.getSfzc9NoSelectedCode());

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
		case '4':
			return this.isShedan();
		default:
			throw new DataException("未能匹配到选择项.");
		}
	}

	public int compareTo(ZcCompoundItem z) {
		SfzcCompoundItem s = null;
		if (z instanceof SfzcCompoundItem)
			s = (SfzcCompoundItem) z;
		if (this.lineId > s.lineId)
			return 1;
		else if (this.lineId < s.lineId)
			return -1;
		else
			return 0;
	}
}
