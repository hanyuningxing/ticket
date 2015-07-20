package com.cai310.lottery.support.zc;

import com.cai310.lottery.exception.DataException;

public interface ZcCompoundItem extends Comparable<ZcCompoundItem> {
	public byte toByte();

	public int selectedCount();

	boolean checkPass(char c) throws DataException;

	public String toBetString();
}
