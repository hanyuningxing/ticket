package com.cai310.lottery.support.zc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cai310.lottery.exception.DataException;

/**
 * 足彩过关中奖更新
 * 
 */
public class ZcCompoundPassWork {
	private int lost0_count;
	private int lost1_count;
	private int lost2_count;
	private int lost3_count;
	private int passcount=0;
	private List<List<ZcCompoundItem>> betItems_list;
	private int multiple;
	private String result;

	/** 存放中奖的组合的MAP */
	protected Map<String, CombinationBean> combinationMap = new HashMap<String, CombinationBean>();// 存放中奖的组合

	public ZcCompoundPassWork(ZcCompoundItem[] betItems, int multiple, String result) {
		super();
		this.multiple = multiple;
		this.result = result;
		try {
			this.passCount(betItems);
		} catch (DataException e) {
			e.printStackTrace();
		}
	}

	public ZcCompoundPassWork(List<List<ZcCompoundItem>> betItems_list, int multiple, String result) {
		super();
		this.betItems_list = betItems_list;
		this.multiple = multiple;
		this.result = result;
		this.passCountList();
	}

	private void passCountList() {
		ZcCompoundItem[] items = null;
		int wonUnits = 0;// 一等奖注数
		int passcountTemp = 0;
		for (List<ZcCompoundItem> betItems : this.betItems_list) {
			
			items = ZcUtils.getStandardSfItems(betItems);
			try {
				wonUnits = this.passCount(items);
				if(this.passcount>passcountTemp){
					passcountTemp=this.passcount;
				}
			} catch (DataException e) {
				e.printStackTrace();
			}
			if (wonUnits > 0) {// 保存中奖组合
				CombinationBean combBean = new CombinationBean();
				SfzcCompoundItem[] sfItems = new SfzcCompoundItem[items.length];
				for (int i = 0; i < items.length; i++) {
					sfItems[i] = (SfzcCompoundItem) items[i];
				}
				combBean.setItems(sfItems);
				combBean.setWonUnits(wonUnits);
				if(!combinationMap.containsKey(combBean.generateKey())){
					combinationMap.put(combBean.generateKey(), combBean);
				}				
			}

		}
		this.setPasscount(passcountTemp);
	}

	/**
	 * 中奖计算
	 * 
	 * @param betItems
	 * @return 中奖的
	 * @throws DataException
	 */
	public int passCount(ZcCompoundItem[] betItems) throws DataException {
		int lost0 = 1;
		int lost1 = 0;
		int lost2 = 0;
		int lost3 = 0;
		int passcountTemp = 0;
		// boolean finalResult = true;
		int selectMatch = 0;
		for (int i = 0; i < betItems.length; i++) {
			ZcCompoundItem betItem = betItems[i];
			char c = result.charAt(i);
			int selCount = betItem.selectedCount();
			if (selCount > 0)
				selectMatch++;
			if (Character.isDigit(c)) {
				//如果有开奖信息
				if (betItem.checkPass(c)) {
					///如果过关
					if (selCount > 1) {
						//选择
						lost3 = lost3 + lost2 * (selCount - 1);
						lost2 = lost2 + lost1 * (selCount - 1);
						lost1 = lost1 + lost0 * (selCount - 1);
					}
					if (selCount > 0) {
						passcountTemp++;
					}
				} else {
					//没有过关
					if (selCount > 0) {
						lost3 = lost2 * selCount;
						lost2 = lost1 * selCount;
						lost1 = lost0 * selCount;
						lost0 = 0;
					}
				}
			} else {
				if (selCount > 0) {
					lost0 = lost0 * selCount;
					lost1 = lost1 * selCount;
					lost2 = lost2 * selCount;
					lost3 = lost3 * selCount;
					passcountTemp++;
					// finalResult = false;
				}
			}
		}
		this.lost0_count += lost0;
		this.lost1_count += lost1;
		this.lost2_count += lost2;
		this.lost3_count += lost3;
		this.passcount=passcountTemp;
		return lost0;
	}

	public int getLost0_count() {
		return lost0_count * multiple;
	}

	public int getLost1_count() {
		return lost1_count * multiple;
	}

	public int getLost2_count() {
		return lost2_count * multiple;
	}

	public int getLost3_count() {
		return lost3_count * multiple;
	}

	public Map<String, CombinationBean> getCombinationMap() {
		return combinationMap;
	}

	/**
	 * @return the passcount
	 */
	public int getPasscount() {
		return passcount;
	}

	/**
	 * @param passcount the passcount to set
	 */
	public void setPasscount(int passcount) {
		this.passcount = passcount;
	}

}
