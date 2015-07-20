package com.cai310.lottery.support.jclq;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOdds;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.utils.CombCallBack;
import com.cai310.utils.ExtensionCombCallBack;
import com.cai310.utils.MathUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 彩篮球工具类
 * 
 */

public final class JclqUtil {
	private static final NumberFormat LINEID_NF = new DecimalFormat("000");
	private static final String MATCH_DATE_FORMAT = "yyyyMMdd";

	/**
	 * 获取用二进制位的第几位表示该类型
	 * 
	 * @param playType
	 *            玩法类型
	 * @param passMode
	 *            过关模式
	 * @return 用二进制位的第几位表示该类型
	 */
	public static int getOpenFlagOffset(PlayType playType, PassMode passMode) {
		int len = PassMode.values().length;
		int offset = playType.ordinal() * len + passMode.ordinal();
		return offset;
	}

	/**
	 * 获取表示该类型的值
	 * 
	 * @param playType
	 *            玩法类型
	 * @param passMode
	 *            过关模式
	 * @return 表示该类型的值
	 */
	public static int getOpenFlag(PlayType playType, PassMode passMode) {
		return 0x1 << getOpenFlagOffset(playType, passMode);
	}

	/**
	 * 格式时场次序号
	 * 
	 * @param lineIdOfDay
	 *            场次序号
	 * @return 已格式的场次序号
	 */
	public static String formatLineId(int lineIdOfDay) {
		return LINEID_NF.format(lineIdOfDay);
	}

	/**
	 * 组装场次标识
	 * 
	 * @param matchDate
	 *            比赛场次的日期
	 * @param lineIdOfDay
	 *            场次序号
	 * @return 场次标识
	 */
	public static String buildMatchKey(int matchDate, int lineIdOfDay) {
		return String.format("%s-%s", matchDate, formatLineId(lineIdOfDay));
	}
    public static Integer getMatchWeek(String matchKey){
	    	Calendar cal = Calendar.getInstance();
	    	String[] matchKeyArr  = matchKey.split("-");
	    	return getMatchDayOfWeek(Integer.valueOf(matchKeyArr[0].trim()));
	}
    public static String getLineId(String matchKey){
    	String[] matchKeyArr  = matchKey.split("-");
    	return LINEID_NF.format(Integer.valueOf(matchKeyArr[1]));
    }
	public static Integer getMatchDate(String dayOfWeekStr, Date matchTime) {
		int dayOfWeek = getDayOfWeek(dayOfWeekStr);
		if (dayOfWeek == -1)
			return null;

		DateTime dateTime = new DateTime(matchTime);

		int tryTimes = 3;
		while (dayOfWeek != dateTime.getDayOfWeek() && tryTimes > 0) {
			dateTime = dateTime.plusDays(-1);
			tryTimes--;
		}
		return Integer.valueOf(dateTime.toString(MATCH_DATE_FORMAT));
	}

	/**
	 * Get the day of week field value.
	 * <p>
	 * The values for the day of week are defined in
	 * {@link org.joda.time.DateTimeConstants}.
	 * 
	 * @param dayOfWeekStr
	 *            一、二、三、四、五、六、日
	 * @return the day of week
	 */
	public static int getDayOfWeek(String dayOfWeekStr) {
		if (StringUtils.isNotBlank(dayOfWeekStr)) {
			if ("一".equals(dayOfWeekStr)) {
				return DateTimeConstants.MONDAY;
			} else if ("二".equals(dayOfWeekStr)) {
				return DateTimeConstants.TUESDAY;
			} else if ("三".equals(dayOfWeekStr)) {
				return DateTimeConstants.WEDNESDAY;
			} else if ("四".equals(dayOfWeekStr)) {
				return DateTimeConstants.THURSDAY;
			} else if ("五".equals(dayOfWeekStr)) {
				return DateTimeConstants.FRIDAY;
			} else if ("六".equals(dayOfWeekStr)) {
				return DateTimeConstants.SATURDAY;
			} else if ("日".equals(dayOfWeekStr)) {
				return DateTimeConstants.SUNDAY;
			}
		}
		return -1;
	}
	public static int getMatchDayOfWeek(int matchDate) {
		DateTime dateTime = getDateTime(matchDate);
		String  dayOfWeekStr = dateTime.toString("E", Locale.SIMPLIFIED_CHINESE).replaceAll("星期", "");
		
		if (StringUtils.isNotBlank(dayOfWeekStr)) {
			if ("一".equals(dayOfWeekStr)) {
				return DateTimeConstants.MONDAY;
			} else if ("二".equals(dayOfWeekStr)) {
				return DateTimeConstants.TUESDAY;
			} else if ("三".equals(dayOfWeekStr)) {
				return DateTimeConstants.WEDNESDAY;
			} else if ("四".equals(dayOfWeekStr)) {
				return DateTimeConstants.THURSDAY;
			} else if ("五".equals(dayOfWeekStr)) {
				return DateTimeConstants.FRIDAY;
			} else if ("六".equals(dayOfWeekStr)) {
				return DateTimeConstants.SATURDAY;
			} else if ("日".equals(dayOfWeekStr)) {
				return DateTimeConstants.SUNDAY;
			}
		}
		return -1;
	}
	public static String getDayOfWeekStr(int matchDate) {
		DateTime dateTime = getDateTime(matchDate);
		return dateTime.toString("E", Locale.SIMPLIFIED_CHINESE).replaceAll("星期", "周");
	}

	public static DateTime getDateTime(int matchDate) {
		int year = matchDate / 10000;
		int temp = matchDate % 10000;
		int month = temp / 100;
		int day = temp % 100;
		return new DateTime(year, month, day, 0, 0, 0, 0);
	}

	public static String getMatchKeyText(int matchDate, int lineId) {
		return String.format("%s%s", getDayOfWeekStr(matchDate), formatLineId(lineId));
	}

	/**
	 * 拆分倍数
	 * 
	 * @param multiple
	 *            原始倍数
	 * @return 拆分后的倍数集合
	 */
	public static List<Integer> splitMultiple(int betUnits, int multiple) {
		if (betUnits <= 0 || multiple <= 0)
			return null;
		int betCostPerMul = betUnits * 2;
		int ticketMaxMultiple = JclqConstant.TICKET_MAX_BETCOST / betCostPerMul;
		if (ticketMaxMultiple > JclqConstant.TICKET_MAX_MULTIPLE) {
			ticketMaxMultiple = JclqConstant.TICKET_MAX_MULTIPLE;
		}
		List<Integer> list = Lists.newArrayList();
		while (multiple > ticketMaxMultiple) {
			list.add(ticketMaxMultiple);
			multiple -= ticketMaxMultiple;
		}
		if (multiple > 0)
			list.add(multiple);
		return list;
	}

	/**
	 * 多选过关拆票
	 * 
	 * @param size
	 *            选择场次
	 * @param pass
	 *            过关数
	 * @param call
	 *            回调函数
	 */
	public static void split(final int size, final int pass, final int maxMatchSize, final SplitTicketCallBack call) {
		final int maxSame = pass - 1;
		List<PassType> passTypeList = PassType.findPassTypes(pass, maxMatchSize);
		Collections.sort(passTypeList, new Comparator<PassType>() {

			public int compare(PassType o1, PassType o2) {
				if (o1.getMatchCount() > o2.getMatchCount())
					return 1;
				else if (o1.getMatchCount() < o2.getMatchCount())
					return -1;
				return 0;
			}
		});
		final Set<boolean[]> set = Sets.newHashSet();
		for (int i = passTypeList.size() - 1; i > -1; i--) {
			final PassType passType = passTypeList.get(i);
			MathUtils.efficientComb(size, passType.getMatchCount(), new CombCallBack() {

				public boolean check(boolean[] newComb) {
					for (boolean[] comb : set) {
						int same = countSame(newComb, comb);
						if (same > maxSame)
							return false;
					}
					return true;
				}

				public int countSame(boolean[] comb1, boolean[] comb2) {
					int count = 0;
					for (int i = 0; i < comb1.length; i++) {
						if (comb1[i] && comb2[i])
							count++;
					}
					return count;
				}

				public boolean callback(final boolean[] comb, final int m) {
					if (check(comb)) {
						set.add(comb.clone());
						call.callback(comb, m, passType);
					}
					return false;
				}

			});
		}
	}
	
	

	public static Map<String, Map<String, Double>> getPrintAwardMap(JSONArray jsonArr) {
		if (jsonArr == null)
			return null;
		Map<String, Map<String, Double>> map = new HashMap<String, Map<String, Double>>();
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);
			JSONArray options = jsonObj.getJSONArray("options");
			Map<String, Double> awardMap = new HashMap<String, Double>();
			for (int j = 0; j < options.size(); j++) {
				JSONObject option = options.getJSONObject(j);
				String value = option.getString("value");
				Double award = option.getDouble("award");
				awardMap.put(value, award);
			}
			if (jsonObj.has(JclqConstant.REFERENCE_VALUE_KEY))
				awardMap.put(JclqConstant.REFERENCE_VALUE_KEY, jsonObj.getDouble(JclqConstant.REFERENCE_VALUE_KEY));
			String matchKey = jsonObj.getString("matchKey");
			map.put(matchKey, awardMap);
		}
		return map;
	}

	/**
	 * 单关拆分
	 * 
	 * @param callback
	 *            拆分回调函数
	 * @param totalMultiple
	 *            总倍数
	 * @param matchItemList
	 *            投注选项
	 */
	public static void singleSplit(final TicketSplitCallback callback, final int totalMultiple,
			List<JclqMatchItem> matchItemList) {
		final PassType passType = PassType.P1;
		int m = JclqConstant.TICKET_SINGLE_MAX_MATCH_SIZE;
		int l = matchItemList.size();
		for (int f = 0, t = (f + m > l) ? l : f + m; f < l; f += m, t = (t + m > l) ? l : t + m) {
			List<JclqMatchItem> list = matchItemList.subList(f, t);
			splitWithMultiple(callback, totalMultiple, passType, list);
		}
	}

	/**
	 * 没有设胆的多选过关拆分
	 * 
	 * @param callback
	 *            拆分回调函数
	 * @param totalMultiple
	 *            总倍数
	 * @param playTypeMaxMatchSize
	 *            玩法允许的最大场次数目
	 * @param passTypes
	 *            过关类型
	 * @param matchItemList
	 *            投注选项
	 */
	public static void undanMultiplePassSplit(final TicketSplitCallback callback, final int totalMultiple,
			final int playTypeMaxMatchSize, final List<PassType> passTypes, final List<JclqMatchItem> matchItemList) {
		SplitTicketCallBack call = new SplitTicketCallBack() {

			@Override
			public void callback(boolean[] comb, int m, PassType passType) {
				List<JclqMatchItem> combList = Lists.newArrayList();
				for (int i = 0; i < comb.length; i++) {
					if (comb[i]) {
						combList.add(matchItemList.get(i));
						if (combList.size() == m)
							break;
					}
				}
				splitWithMultiple(callback, totalMultiple, passType, combList);
			}
		};
		int matchSize = matchItemList.size();
		for (PassType passType : passTypes) {
			int pass = passType.getPassMatchs()[0];
			JclqUtil.split(matchSize, pass, playTypeMaxMatchSize, call);
		}
	}
	public static Map<String, Map<String, Double>> getPrintAwardMap(List<JcMatchOdds> list) {
		if (null==list||list.isEmpty())
			return null;
		
		Map<String, Map<String, Double>> map = new HashMap<String, Map<String, Double>>();
		
		
		for (int i = 0; i < list.size(); i++) {
			JcMatchOdds jcMatchOdds = list.get(i);
			Map<String, Double> awardMap = jcMatchOdds.getOptions();
			String matchKey = jcMatchOdds.getMatchKey();
			map.put(matchKey, awardMap);
		}
		return map;
	}
	/**
	 * 
	 * @param ticketItem  出票已经拆单
	 * @param allList 总选择的列表
	 * @return 通过出票拆单标志返回选择的列表;
	 */
	public static List<JclqMatchItem> getSelectByTicketItem(TicketItem ticketItem, List<JclqMatchItem> matchItemList) {
		boolean isAll = ticketItem.getMatchFlag() == 0;
		List<JclqMatchItem> itemList = new ArrayList<JclqMatchItem>();
		for (int p = 0; p < matchItemList.size(); p++) {
			if (isAll || (ticketItem.getMatchFlag() & (0x1l << p)) > 0) {
				JclqMatchItem matchItem = matchItemList.get(p);
				itemList.add(matchItem);
			}
		}
		return itemList;
	}
	/**
     * @param ticketContent
     * @return 出票列表
     * @throws DataException
     */
	public static List<TicketItem> getTicketList(String ticketContent) throws DataException {
		List<TicketItem> list = Lists.newLinkedList();
		String[] arr = ticketContent.trim().split(TicketItem.ITEM_AND);
		for (String content : arr) {
			list.add(TicketItem.valueOf(content));
		}
		return list;
	}
	
	/**
     * @param ticketContent
     * @return 出票列表(单式 第一个参数为投注项的下标)
     * @throws DataException
     */
	public static List<TicketItemSingle> getSingleTicketList(String ticketContent) throws DataException {
		List<TicketItemSingle> list = Lists.newLinkedList();
		String[] arr = ticketContent.trim().split(TicketItem.ITEM_AND);
		for (String content : arr) {
			list.add(TicketItemSingle.valueOf(content));
		}
		return list;
	}
	/**
	 * 设胆的多选过关拆分
	 * 
	 * @param callback
	 *            拆分回调函数
	 * @param totalMultiple
	 *            总倍数
	 * @param passTypes
	 *            过关类型
	 * @param danList
	 *            设胆投注选项
	 * @param undanList
	 *            没有设胆的投注选项
	 * @param danMitHit
	 *            胆码最小命中数
	 * @param danMaxHit
	 *            胆码最大命中数
	 */
	public static void danMultiplePassSplit(final TicketSplitCallback callback, final int totalMultiple,
			final List<PassType> passTypes, final List<JclqMatchItem> danList, final List<JclqMatchItem> undanList,
			int danMitHit, int danMaxHit) {
		int danSize = danList.size();
		int undanSize = undanList.size();
		for (final PassType passType : passTypes) {
			MathUtils.efficientCombExtension(passType.getMatchCount(), danSize, danMitHit, danMaxHit, undanSize,
					new ExtensionCombCallBack() {

						public boolean run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
							List<JclqMatchItem> combList = Lists.newArrayList();
							for (int i = 0; i < comb1.length; i++) {
								if (comb1[i])
									combList.add(danList.get(i));
							}
							for (int i = 0; i < comb2.length; i++) {
								if (comb2[i])
									combList.add(undanList.get(i));
							}

							splitWithMultiple(callback, totalMultiple, passType, combList);
							return false;
						}
					});
		}
	}

	/**
	 * 根据倍数拆分
	 * 
	 * @param callback
	 *            拆分回调函数
	 * @param totalMultiple
	 *            总倍数
	 * @param passType
	 *            过关类型
	 * @param matchItemList
	 *            投注选项
	 */
	public static void splitWithMultiple(final TicketSplitCallback callback, final int totalMultiple,
			final PassType passType, List<JclqMatchItem> matchItemList) {
		final int units = UnitsCountUtils.countUnits(Lists.newArrayList(matchItemList), passType.getPassMatchs());
		final List<Integer> multipleList = JclqUtil.splitMultiple(units, totalMultiple);
		for (int multiple : multipleList) {
			callback.handle(matchItemList, passType, multiple);
		}
	}

	public static long chg2flag(List<JclqMatchItem> subList, List<JclqMatchItem> allList) {
		long flag = 0;
		for (JclqMatchItem item : subList) {
			int p = allList.indexOf(item);
			flag |= 0x1 << p;
		}
		return flag;
	}
	public static String getAwardValueBySpText(String sp,PlayType playType){
		String value = null;
		if(StringUtils.isNotBlank(sp)){
			if(sp.indexOf("^")!=-1){
				String[] arr = sp.split("\\^");
				String  betValue = arr[0].trim();
				switch (playType) {
				case SF:
					if("1".equals(betValue)){
						value = ItemSF.WIN.getValue();
					}else if("2".equals(betValue)){
						value = ItemSF.LOSE.getValue();
					}
					break;
				case RFSF:
					if("1".equals(betValue)){
						value = ItemRFSF.SF_WIN.getValue();
					}else if("2".equals(betValue)){
						value = ItemRFSF.SF_LOSE.getValue();
					}
					break;
				case SFC:
					if("01".equals(betValue)){
						value = ItemSFC.HOME1_5.getValue();
					}else if("02".equals(betValue)){
						value = ItemSFC.HOME6_10.getValue();
					}else if("03".equals(betValue)){
						value = ItemSFC.HOME11_15.getValue();
					}else if("04".equals(betValue)){
						value = ItemSFC.HOME16_20.getValue();
					}else if("05".equals(betValue)){
						value = ItemSFC.HOME21_25.getValue();
					}else if("06".equals(betValue)){
						value = ItemSFC.HOME26.getValue();
					}else if("11".equals(betValue)){
						value = ItemSFC.GUEST1_5.getValue();
					}else if("12".equals(betValue)){
						value = ItemSFC.GUEST6_10.getValue();
					}else if("13".equals(betValue)){
						value = ItemSFC.GUEST11_15.getValue();
					}else if("14".equals(betValue)){
						value = ItemSFC.GUEST16_20.getValue();
					}else if("15".equals(betValue)){
						value = ItemSFC.GUEST21_25.getValue();
					}else if("16".equals(betValue)){
						value = ItemSFC.GUEST26.getValue();
					}
					break;
				case DXF:
					
					if("1".equals(betValue)){
						value = ItemDXF.LARGE.getValue();
					}else if("2".equals(betValue)){
						value = ItemDXF.LITTLE.getValue();
					}
					break;
				default:
					throw new RuntimeException("玩法不正确.");
				}
			}
		}
		return value;
	}
	public static JcMatchOddsList bulidPrintAwardMap(Map<String,Map<String, Double>> map) {
		if (map == null)
			return null;
		JcMatchOddsList jcMatchOddsList = new JcMatchOddsList();
		Map<String, Double> awardMap ;
		JcMatchOdds jcMatchOdds ;
		List<JcMatchOdds> list = Lists.newArrayList();
		for (String matchKey : map.keySet()) {
			jcMatchOdds = new JcMatchOdds();
			jcMatchOdds.setMatchKey(matchKey);
			awardMap = map.get(matchKey);
			jcMatchOdds.setOptions(awardMap);
 			list.add(jcMatchOdds);
		}
		jcMatchOddsList.setJcMatchOdds(list);
		return jcMatchOddsList;
		
	}
	public static Double getAwardBySpText(String sp,PlayType playType){
		Double award = null;
		if(StringUtils.isNotBlank(sp)){
			if(sp.indexOf("^")!=-1){
				String[] arr = sp.split("\\^");
				award = Double.valueOf(arr[1]);
			}
		}
		return award;
	}
}
