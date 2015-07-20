package com.cai310.lottery.support.jclq;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cai310.lottery.Constant;
import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.support.JcWonMatchItem;
import com.cai310.lottery.support.PrintWonItem;
import com.cai310.lottery.support.PrizeWork;
import com.cai310.lottery.utils.CommonUtil;
import com.google.common.collect.Lists;

public abstract class JclqPrizeWork implements PrizeWork, Serializable {
	private static final long serialVersionUID = -2985375777736937886L;

	/** 各个中奖组合中奖信息的分隔符 */
	public static final String lineSeq = "<br/>";
	/** 乘号字符 */
	public static final char MUL_CHAR = '×';

	public static final NumberFormat MONEY_FORMAT = new DecimalFormat("0.00");
	public static final NumberFormat AWARD_FORMAT = new DecimalFormat("0.00");

	/** 中奖详情 */
	protected StringBuilder wonDetail;

	/** 奖金详情 */
	protected StringBuilder prizeDetail;

	/** 税前总奖金 */
	protected double totalPrize = 0.0d;

	/** 税后总奖金 */
	protected double totalPrizeAfterTax = 0.0d;
	/** 出票回查 */
	protected List<PrintWonItem> printWonItemList=Lists.newArrayList();
	/** 是否中奖 */
	protected boolean won;

	protected final PassMode passMode;
	protected final boolean isSinglePass;
	protected final int multiple;
	protected Map<Integer, List<List<Double>>> wonMap = new TreeMap<Integer, List<List<Double>>>(
			new Comparator<Integer>() {

				public int compare(Integer o1, Integer o2) {
					if (o1 > o2)
						return 1;
					if (o1 < o2)
						return -1;
					else
						return 0;
				}
			});
	protected int refundmentUnits = 0;

	public JclqPrizeWork(final PassMode passMode, final int multiple) {
		super();
		this.passMode = passMode;
		this.isSinglePass = this.passMode == PassMode.SINGLE;
		this.multiple = multiple;
	}

	protected void addTotalPrize(double prize) {
		totalPrize += prize;
	}

	protected void addTotalPrizeAfterTax(double prizeAfterTax) {
		totalPrizeAfterTax += prizeAfterTax;
	}

	protected void handle(PassType passType, List<JcWonMatchItem> combList) {
		int size = 1;
		List<Double> list = new ArrayList<Double>();
		for (JcWonMatchItem wonMatchItem : combList) {
			if (wonMatchItem.isCancel())
				size *= wonMatchItem.getSelectCount();
			else
				list.add(wonMatchItem.getAward());
		}
		if (list.isEmpty()) {
			refundmentUnits += size;
		} else {
			Integer key = list.size();
			List<List<Double>> value = wonMap.get(key);
			if (value == null)
				value = new ArrayList<List<Double>>();
			for (int i = 0; i < size; i++) {
				value.add(list);
			}
			wonMap.put(key, value);
			buildTicketWon(wonMap,0);//只有1张票
		}
	}

	protected void build() {
		buildInit();
		buildRefundment();
		buildWon();
		buildSum();
	}

	protected void buildInit() {
		totalPrize = 0.0d;
		totalPrizeAfterTax = 0.0d;
		wonDetail = new StringBuilder();
		prizeDetail = new StringBuilder();
	}

	protected void buildRefundment() {
		if (refundmentUnits > 0) {
			wonDetail.append("退款:").append(refundmentUnits).append("注").append(lineSeq);

			double prize = refundmentUnits * 2;
			addTotalPrize(prize);
			addTotalPrizeAfterTax(prize);
			prizeDetail.append(refundmentUnits).append(MUL_CHAR).append(2).append("(退款)=")
					.append(MONEY_FORMAT.format(prize)).append("元").append(lineSeq);
		}
	}
	protected void buildTicketWon(Map<Integer, List<List<Double>>> wonMap,Integer index) {
		if (!wonMap.isEmpty()) {
					/** 税前总奖金 */
					double totalPrize = 0.0d;
					/** 税后总奖金 */
					double totalPrizeAfterTax = 0.0d;
					/** 奖金详情 */
					StringBuilder prizeDetail = new StringBuilder();
					StringBuilder wonDetail = new StringBuilder();
					StringBuilder line = new StringBuilder();
					for (Map.Entry<Integer, List<List<Double>>> entry : wonMap.entrySet()) {
					int pass = entry.getKey();
					String passText = getPassText(pass);
					wonDetail.append(passText).append(":").append(entry.getValue().size()).append("注").append(lineSeq);
	
					for (List<Double> list : entry.getValue()) {
						line.setLength(0);
						line.append(passText).append(":");
						double prize = 1d;
						for (double award : list) {
							if (award < 0)
								throw new RuntimeException("数据异常：奖金值小于0.");
	
							prize *= award;
							line.append(AWARD_FORMAT.format(award)).append(MUL_CHAR);
						}
	
						StringBuilder tempSb = new StringBuilder();
						if (isSinglePass) {
							prize = CommonUtil.roundPrize(prize);
							line.deleteCharAt(line.length() - 1);// 删除最后一个乘号
							line.append('=').append(MONEY_FORMAT.format(prize)).append("元");
						} else {
							prize *= 2d;
							prize = CommonUtil.roundPrize(prize);
							line.append(2);
							line.append('=').append(MONEY_FORMAT.format(prize)).append("元");
							if (pass >= 2) {
								Integer maxPrize = JclqConstant.getMaxPrize(pass);
								if (maxPrize != null && prize > maxPrize) {
									prize = maxPrize;
									tempSb.append(pass).append("场过关投注，单注最高奖金限额").append(maxPrize).append("元");
								}
							}
						}
	
						if (prize < 2.0d) {// 补足2元
							prize = 2.0d;
							line.append("(补足2元)");
						}
	
						double prizeAfterTax = CommonUtil.roundPrize(Constant.countPrizeAfterTax(prize));
						if (prizeAfterTax < prize) {
							if (tempSb.length() > 0)
								tempSb.append("，");
							tempSb.append("税后").append(MONEY_FORMAT.format(prizeAfterTax)).append("元");
						}
						if (tempSb.length() > 0)
							line.append('(').append(tempSb).append(')');
	
						addTotalPrize(prize);
						addTotalPrizeAfterTax(prizeAfterTax);
						prizeDetail.append(line).append(lineSeq);
					}
				}
				PrintWonItem printWonItem = new PrintWonItem();
				printWonItem.setIndex(index);
				printWonItem.setTotalPrize(totalPrize);
				printWonItem.setTotalPrizeAfterTax(totalPrizeAfterTax);
				printWonItem.setWonDetail(wonDetail.toString());
				printWonItemList.add(printWonItem);
		}
	}
	protected void buildWon() {
		if (!wonMap.isEmpty()) {
			StringBuilder line = new StringBuilder();
			for (Map.Entry<Integer, List<List<Double>>> entry : wonMap.entrySet()) {
				int pass = entry.getKey();
				String passText = getPassText(pass);
				wonDetail.append(passText).append(":").append(entry.getValue().size()).append("注").append(lineSeq);

				for (List<Double> list : entry.getValue()) {
					line.setLength(0);
					line.append(passText).append(":");
					double prize = 1d;
					for (double award : list) {
						if (award < 0)
							throw new RuntimeException("数据异常：奖金值小于0.");

						prize *= award;
						line.append(AWARD_FORMAT.format(award)).append(MUL_CHAR);
					}

					StringBuilder tempSb = new StringBuilder();
					if (isSinglePass) {
						prize = CommonUtil.roundPrize(prize);
						line.deleteCharAt(line.length() - 1);// 删除最后一个乘号
						line.append('=').append(MONEY_FORMAT.format(prize)).append("元");
					} else {
						prize *= 2d;
						prize = CommonUtil.roundPrize(prize);
						line.append(2);
						line.append('=').append(MONEY_FORMAT.format(prize)).append("元");
						if (pass >= 2) {
							Integer maxPrize = JclqConstant.getMaxPrize(pass);
							if (maxPrize != null && prize > maxPrize) {
								prize = maxPrize;
								tempSb.append(pass).append("场过关投注，单注最高奖金限额").append(maxPrize).append("元");
							}
						}
					}

					if (prize < 2.0d) {// 补足2元
						prize = 2.0d;
						line.append("(补足2元)");
					}

					double prizeAfterTax = CommonUtil.roundPrize(Constant.countPrizeAfterTax(prize));
					if (prizeAfterTax < prize) {
						if (tempSb.length() > 0)
							tempSb.append("，");
						tempSb.append("税后").append(MONEY_FORMAT.format(prizeAfterTax)).append("元");
					}
					if (tempSb.length() > 0)
						line.append('(').append(tempSb).append(')');

					addTotalPrize(prize);
					addTotalPrizeAfterTax(prizeAfterTax);
					prizeDetail.append(line).append(lineSeq);
				}
			}
		}
	}

	protected void buildSum() {
		prizeDetail.append("合计：").append(MONEY_FORMAT.format(this.totalPrizeAfterTax)).append("元").append(MUL_CHAR)
				.append(this.multiple);
		this.totalPrize *= this.multiple;
		this.totalPrizeAfterTax *= this.multiple;
		prizeDetail.append('=').append(MONEY_FORMAT.format(this.totalPrizeAfterTax)).append("元").append(lineSeq);
	}

	protected String getPassText(int pass) {
		if (pass == 1)
			return "单关";
		else
			return pass + "关";
	}

	/**
	 * @return the {@link #wonDetail}
	 */
	public String getWonDetail() {
		if (this.isWon())
			return wonDetail.toString();
		return null;
	}

	/**
	 * @return the {@link #prizeDetail}
	 */
	public String getPrizeDetail() {
		if (this.isWon()) {
			String str = prizeDetail.toString();
			if (str.matches("(.*)" + lineSeq))
				str = str.substring(0, str.length() - lineSeq.length());// 去除最后一个换行
			return str;
		}
		return null;
	}

	/**
	 * @return the {@link #totalPrize}
	 */
	public double getTotalPrize() {
		return totalPrize;
	}

	/**
	 * @return the {@link #totalPrizeAfterTax}
	 */
	public double getTotalPrizeAfterTax() {
		return totalPrizeAfterTax;
	}

	/**
	 * @return the {@link #won}
	 */
	public boolean isWon() {
		return won;
	}

	/**
	 * @return the {@link #multiple}
	 */
	public int getMultiple() {
		return multiple;
	}

	/**
	 * @return the {@link #wonMap}
	 */
	public Map<Integer, List<List<Double>>> getWonMap() {
		return wonMap;
	}

	/**
	 * @return the {@link #refundmentUnits}
	 */
	public int getRefundmentUnits() {
		return refundmentUnits;
	}

	public List<PrintWonItem> getPrintWonItemList() {
		return printWonItemList;
	}

	public void setPrintWonItemList(List<PrintWonItem> printWonItemList) {
		this.printWonItemList = printWonItemList;
	}
}