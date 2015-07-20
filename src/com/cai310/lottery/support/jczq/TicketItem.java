package com.cai310.lottery.support.jczq;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.Item;

public class TicketItem implements Serializable {
	private static final long serialVersionUID = 1824473158539413251L;
	public static final String ITEM_AND = ";";
	public static final String SEQ = "_";

	/**
	 * 以二进制位的形式标记所选择的场次，值为0表示选择原数组所有元素<br/>
	 * 例如：<br/>
	 * 原选择的场次：[10,15,16,18,19]<br/>
	 * matchFlag=5=(2进制)00101，则选择的是原数组的第1和第3个元素，即：[10,16];
	 */
	private Long matchFlag;

	/**
	 * 过关方式
	 */
	private PassType passType;

	/** 倍数 */
	private int multiple;

	/** 选项内容，以二进制表示一场的选项，多场之间用逗号隔开 */
	private String itemValue;

	public TicketItem(Long matchFlag, PassType passType, int multiple) {
		this(matchFlag, passType, multiple, null);
	}

	public TicketItem(Long matchFlag, PassType passType, int multiple, String itemValue) {
		this.matchFlag = matchFlag;
		this.passType = passType;
		this.multiple = multiple;
		this.itemValue = itemValue;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(matchFlag).append(SEQ).append(passType.ordinal()).append(SEQ).append(multiple);
		if (StringUtils.isNotBlank(itemValue))
			sb.append(SEQ).append(itemValue);
		return sb.toString();
	}

	public static TicketItem valueOf(String content) throws DataException {
		if (content == null || "".equals(content.trim()))
			throw new DataException("内容不能为空或空字符串.");

		String[] arr = content.split(SEQ);
		if (arr.length < 3 || arr.length > 4)
			throw new DataException("内容格式不对.");
		Long matchFlag = Long.valueOf(arr[0]);
		Integer ordinal = Integer.valueOf(arr[1]);
		Integer multiple = Integer.valueOf(arr[2]);
		String itemValue = (arr.length == 4) ? arr[3] : null;

		if (StringUtils.isNotBlank(itemValue) && itemValue.indexOf(SEQ) >= 0)
			throw new DataException("选项内容不允许包含字符[" + SEQ + "].");

		if (matchFlag == null || ordinal == null || ordinal < 0 || ordinal >= PassType.values().length
				|| multiple == null || multiple <= 0 || multiple > JczqConstant.TICKET_MAX_MULTIPLE)
			throw new DataException("内容不合法.");

		PassType passType = PassType.values()[ordinal];

		return new TicketItem(matchFlag, passType, multiple, itemValue);
	}

	public Long getMatchFlag() {
		return matchFlag;
	}

	public void setMatchFlag(Long matchFlag) {
		this.matchFlag = matchFlag;
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
	public void putItemValues(List<JczqMatchItem> matchItemList) {
		if(null==matchItemList||matchItemList.isEmpty())return;
		StringBuilder sb = new StringBuilder();
		for (JczqMatchItem jczqMatchItem : matchItemList) {
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
			return JczqUtil.getMatchKeyText(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
		}else{
			return matchKey;
		}
	}
}