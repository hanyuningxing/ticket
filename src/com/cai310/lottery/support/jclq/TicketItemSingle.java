package com.cai310.lottery.support.jclq;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.exception.DataException;

public class TicketItemSingle implements Serializable {
	private static final long serialVersionUID = -6937176391628989219L;
	public static final String ITEM_AND = ";";
	public static final String SEQ = "_";

	/**
	 * 对应投注内容的下标
	 */
	private int index;

	/**
	 * 过关方式
	 */
	private PassType passType;

	/** 倍数 */
	private int multiple;

	/** 选项内容，以二进制表示一场的选项，多场之间用逗号隔开 */
	private String itemValue;

	public TicketItemSingle(int index, PassType passType, int multiple) {
		this(index, passType, multiple, null);
	}

	public TicketItemSingle(int index, PassType passType, int multiple, String itemValue) {
		this.index = index;
		this.passType = passType;
		this.multiple = multiple;
		this.itemValue = itemValue;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(index).append(SEQ).append(passType.ordinal()).append(SEQ).append(multiple);
		if (StringUtils.isNotBlank(itemValue))
			sb.append(SEQ).append(itemValue);
		return sb.toString();
	}

	public static TicketItemSingle valueOf(String content) throws DataException {
		if (content == null || "".equals(content.trim()))
			throw new DataException("内容不能为空或空字符串.");

		String[] arr = content.split(SEQ);
		if (arr.length < 3 || arr.length > 4)
			throw new DataException("内容格式不对.");
		Integer index = Integer.valueOf(arr[0]);
		Integer ordinal = Integer.valueOf(arr[1]);
		Integer multiple = Integer.valueOf(arr[2]);
		String itemValue = (arr.length == 4) ? arr[3] : null;

		if (StringUtils.isNotBlank(itemValue) && itemValue.indexOf(SEQ) >= 0)
			throw new DataException("选项内容不允许包含字符[" + SEQ + "].");

		if (index == null || ordinal == null || ordinal < 0 || ordinal >= PassType.values().length
				|| multiple == null || multiple <= 0 || multiple > JclqConstant.TICKET_MAX_MULTIPLE)
			throw new DataException("内容不合法.");

		PassType passType = PassType.values()[ordinal];

		return new TicketItemSingle(index, passType, multiple, itemValue);
	}

	

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public PassType getPassType() {
		return passType;
	}

	public void setPassType(PassType passType) {
		this.passType = passType;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	public void putItemValues(List<JclqMatchItem> matchItemList) {
		if(null==matchItemList||matchItemList.isEmpty())return;
		StringBuilder sb = new StringBuilder();
		for (JclqMatchItem jczqMatchItem : matchItemList) {
			sb.append(jczqMatchItem.getValue()+",");
		}
		sb.deleteCharAt(sb.length()-",".length());
		this.setItemValue(sb.toString());
	}
	public int[] getItemValues() {
		if (StringUtils.isBlank(this.getItemValue()))
			return null;
		String[] arr = this.getItemValue().split(",");
		int[] vals = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			vals[i] = Integer.parseInt(arr[i]);
		}
		return vals;
	}
	public String getMatchKeyText(String matchKey) {
		if(StringUtils.isNotBlank(matchKey)&&matchKey.indexOf("-")!=-1){
			String[] arr  = matchKey.split("-");
			return JclqUtil.getMatchKeyText(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
		}else{
			return matchKey;
		}
	}
}