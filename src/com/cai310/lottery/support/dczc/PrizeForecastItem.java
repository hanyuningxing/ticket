package com.cai310.lottery.support.dczc;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.cai310.lottery.DczcConstant;
import com.cai310.utils.ExtensionCombCallBack;
import com.cai310.utils.MathUtils;

public class PrizeForecastItem implements Serializable {
	private static final long serialVersionUID = 6051191640635454000L;

	public static final NumberFormat SP_FORMAT = new DecimalFormat("0.00");
	public static final NumberFormat CANCEL_SP_FORMAT = new DecimalFormat("0");
	public static final NumberFormat RETURN_RATE_FORMAT = new DecimalFormat("0%");
	public static final NumberFormat MONEY_FORMAT = new DecimalFormat("0.00");
	public static final char SIGN_MULTIPLE = '×';
	public static final int UNITS_MONEY = 2;

	private final List<PrizeForecastDetailItem> detailList;
	private final int matchSize;
	private final Double totalPrize;

	private final List<DczcExtraMatchItem> danlist;
	private final List<DczcExtraMatchItem> undanlist;

	public PrizeForecastItem(Integer multiple, final List<DczcExtraMatchItem> list, Integer danMinHit,
			Integer danMaxHit, final List<Integer> passMatchList, final boolean min) {
		super();
		this.matchSize = list.size();

		this.danlist = new ArrayList<DczcExtraMatchItem>();
		this.undanlist = new ArrayList<DczcExtraMatchItem>();
		for (DczcExtraMatchItem item : list) {
			if (item.isDan())
				this.danlist.add(item);
			else
				this.undanlist.add(item);
		}

		if (danMinHit == null || danMinHit > this.danlist.size())
			danMinHit = this.danlist.size();

		if (danMaxHit == null || danMaxHit > this.danlist.size() || danMaxHit < danMinHit)
			danMaxHit = this.danlist.size();

		List<PrizeForecastDetailItem> detailList = new ArrayList<PrizeForecastDetailItem>();
		double totalPrize = 0;
		for (Integer pass : passMatchList) {
			if (pass <= this.matchSize) {
				ExtendCombCallBack call = new ExtendCombCallBack(min, multiple);
				MathUtils.efficientCombExtension(pass, this.danlist.size(), danMinHit, danMaxHit,
						this.undanlist.size(), call);
				PrizeForecastDetailItem detailItem = call.buildResult();
				detailList.add(detailItem);
				if (detailItem.getTotalPrize() != null)
					totalPrize += detailItem.getTotalPrize();
			} else {
				break;
			}
		}
		this.totalPrize = totalPrize;
		this.detailList = detailList;
	}

	public List<PrizeForecastDetailItem> getDetailList() {
		return detailList;
	}

	public int getMatchSize() {
		return matchSize;
	}

	public Double getTotalPrize() {
		return totalPrize;
	}

	private class ExtendCombCallBack implements ExtensionCombCallBack {
		private List<List<DczcExtraMatchItem>> detailList = new ArrayList<List<DczcExtraMatchItem>>();
		private double totalPrize = 0.0d;
		private StringBuilder detail = new StringBuilder();

		private final boolean min;
		private final Integer multiple;

		public ExtendCombCallBack(boolean min, Integer multiple) {
			super();
			this.min = min;
			this.multiple = multiple;
		}

		public PrizeForecastDetailItem buildResult() {
			return new PrizeForecastDetailItem(multiple, totalPrize, detailList, detail.toString());
		}

		public boolean run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
			int n = m1 + m2;
			List<DczcExtraMatchItem> combList = new ArrayList<DczcExtraMatchItem>();
			double linePrize = 1;
			StringBuilder line = new StringBuilder();
			String passText = DczcConstant.PASS_TEXT[n - 1];
			line.append('[').append(passText).append(']');
			for (int i = 0; i < comb1.length; i++) {
				if (comb1[i]) {
					DczcExtraMatchItem item = danlist.get(i);
					combList.add(item);

					Double sp;
					if (item.isCancel()) {
						sp = item.selectedCount() * DczcConstant.CANCEL_MATCH_RESULT_SP;
						StringBuilder cancelSpText = new StringBuilder();
						for (int j = 0; j < item.selectedCount(); j++) {
							cancelSpText.append(CANCEL_SP_FORMAT.format(DczcConstant.CANCEL_MATCH_RESULT_SP)).append('+');
						}
						cancelSpText.deleteCharAt(cancelSpText.length() - 1);// 删除最后一个'+'
						line.append('(').append(cancelSpText).append(')').append(SIGN_MULTIPLE);
					} else if (item.isEnd() && item.getResultItem() != null && item.getResultSp() != null) {
						if (!item.isWon())
							return false;

						sp = item.getResultSp();
						line.append(SP_FORMAT.format(sp)).append(SIGN_MULTIPLE);
					} else {
						sp = item.getMinSpOrMaxSpItem(min).getSp();
						if (sp == null)
							sp = 0.0d;

						line.append(SP_FORMAT.format(sp)).append(SIGN_MULTIPLE);
					}
					linePrize *= sp;

					if (combList.size() == m1)
						break;
				}
			}
			for (int i = 0; i < comb2.length; i++) {
				if (comb2[i]) {
					DczcExtraMatchItem item = undanlist.get(i);
					combList.add(item);

					Double sp;
					if (item.isCancel()) {
						sp = item.selectedCount() * DczcConstant.CANCEL_MATCH_RESULT_SP;
						StringBuilder cancelSpText = new StringBuilder();
						for (int j = 0; j < item.selectedCount(); j++) {
							cancelSpText.append(CANCEL_SP_FORMAT.format(DczcConstant.CANCEL_MATCH_RESULT_SP)).append('+');
						}
						cancelSpText.deleteCharAt(cancelSpText.length() - 1);// 删除最后一个'+'
						line.append('(').append(cancelSpText).append(')').append(SIGN_MULTIPLE);
					} else if (item.isEnd() && item.getResultItem() != null && item.getResultSp() != null) {
						if (!item.isWon())
							return false;

						sp = item.getResultSp();
						line.append(SP_FORMAT.format(sp)).append(SIGN_MULTIPLE);
					} else {
						sp = item.getMinSpOrMaxSpItem(min).getSp();
						if (sp == null)
							sp = 0.0d;

						line.append(SP_FORMAT.format(sp)).append(SIGN_MULTIPLE);
					}
					linePrize *= sp;

					if (combList.size() == n)
						break;
				}
			}
			if (n == 1 && combList.get(0).isCancel()) {
				linePrize *= multiple * UNITS_MONEY;
				line.append(multiple).append(SIGN_MULTIPLE).append(UNITS_MONEY).append("(退款)=").append(
						MONEY_FORMAT.format(linePrize));
			} else {
				linePrize *= multiple * UNITS_MONEY * DczcConstant.PRIZE_RETURN_RATE;
				line.append(multiple).append(SIGN_MULTIPLE).append(UNITS_MONEY).append(SIGN_MULTIPLE).append(
						RETURN_RATE_FORMAT.format(DczcConstant.PRIZE_RETURN_RATE)).append('=').append(
						MONEY_FORMAT.format(linePrize));
			}
			if (linePrize < 2.0d && n == 1) {
				linePrize = 2.0d;
				line.append("(补足2元)");
			}
			if (detail.length() > 0)
				detail.append("\r\n");
			detail.append(line);
			totalPrize += linePrize;
			detailList.add(combList);

			return false;
		}
	}
}
