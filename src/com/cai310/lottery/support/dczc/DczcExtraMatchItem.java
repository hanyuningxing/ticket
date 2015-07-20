package com.cai310.lottery.support.dczc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.SelectedCount;

public class DczcExtraMatchItem implements Serializable, SelectedCount {
	private static final long serialVersionUID = 2550824314453960163L;

	/** 场次ID */
	private int lineId;

	/** 是否设胆 */
	private boolean dan;

	/** 投注内容 */
	private List<DczcExtraItem> values;

	private boolean cancel;

	private boolean end;

	private Item resultItem;

	private Double resultSp;

	/**
	 * @return {@link #lineId}
	 */
	public int getLineId() {
		return lineId;
	}

	/**
	 * @param lineId the {@link #lineId} to set
	 */
	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	/**
	 * @return {@link #dan}
	 */
	public boolean isDan() {
		return dan;
	}

	/**
	 * @param dan the {@link #dan} to set
	 */
	public void setDan(boolean dan) {
		this.dan = dan;
	}

	/**
	 * @return {@link #values}
	 */
	public List<DczcExtraItem> getValues() {
		return values;
	}

	/**
	 * @param values the {@link #values} to set
	 */
	public void setValues(List<DczcExtraItem> values) {
		this.values = values;
	}

	public List<DczcExtraItem> getCopyValuesOfAscSort() {
		if (values == null)
			return null;

		List<DczcExtraItem> copyList = new ArrayList<DczcExtraItem>(values);

		if (values.isEmpty() | values.size() == 1)
			return copyList;

		Collections.sort(copyList);
		return copyList;
	}

	public DczcExtraItem getMinSpItem() {
		List<DczcExtraItem> copyList = getCopyValuesOfAscSort();
		if (copyList == null || copyList.isEmpty())
			return null;
		return copyList.get(0);
	}

	public DczcExtraItem getMaxSpItem() {
		List<DczcExtraItem> copyList = getCopyValuesOfAscSort();
		if (copyList == null || copyList.isEmpty())
			return null;
		return copyList.get(copyList.size() - 1);
	}

	/**
	 * 获取最小或最大SP值的选项
	 * 
	 * @param min true表示获取最小SP值，false表示获取最大SP值
	 * @return 最小或最大SP值的选项
	 */
	public DczcExtraItem getMinSpOrMaxSpItem(boolean min) {
		return min ? getMinSpItem() : getMaxSpItem();
	}

	public int selectedCount() {
		if (values == null)
			return 0;
		return values.size();
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public Item getResultItem() {
		return resultItem;
	}

	public void setResultItem(Item resultItem) {
		this.resultItem = resultItem;
	}

	public Double getResultSp() {
		return resultSp;
	}

	public void setResultSp(Double resultSp) {
		this.resultSp = resultSp;
	}

	public Boolean isWon() {
		if (isCancel())
			return true;
		if (!isEnd() || getResultItem() == null)
			return null;

		for (DczcExtraItem extraItem : getValues()) {
			if (extraItem.getItem() == getResultItem())
				return true;
		}
		return false;
	}
}
