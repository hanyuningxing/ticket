package com.cai310.lottery.ticket.dataconver;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.SscConstant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ssc.SscCompoundContent;
import com.cai310.lottery.support.ssc.SscPlayType;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;
import com.cai310.utils.ExtensionCombCallBack;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.MathUtils;

/**
 * 时时彩特殊玩法拆单
 * 
 * @author zhuhui
 * 
 */
public class SSCDataConver {
	static int unitsMoney = 2;
	static int unitsMaxAllFive = 5;
	static int maxUnits = 1;

//	static final String[][] directTwoSum = { {"00"}, {"01","10"}, {"02","20","11"}, { "03","30","12","21"}, {"04","40","13","31","22"}, {"05","50","14","41","23","32"}, { "06","60","15","51","24","42","33" }, {"07","70","16","61","25","52","34","43"}, {"08","80","17","71","26","62","35","53","44"}, { "09","90","18","81","27","72","36","63","45","54" }, {"19","91","28","82","37","73","46","64","55"},
//		{"29","92","38","83","47","74","56","65"}, { "39","93","48","84","57","75","66" }, {"49","94","58","85","67","76"}, {"59","95","68","86","77"}, { "69","96","78","87"}, {"79","97","88"}, {"89","98"}, {"99"} };	
//	
//	static final String[][] groupTwoSum = { {"00"}, {"01"}, {"02","11"}, { "03","21"}, {"04","31","22"}, {"05","14","32"}, { "06","15","24","33" }, {"07","61","25","34"}, {"08","17","26","35","44"}, { "09","18","27","36","45"}, {"19","28","37","46","55"},
//		{"29","38","47","56"}, { "39","48","57","66" }, {"49","58","67"}, {"59","68","77"}, { "69","78"}, {"79","88"}, {"89"}, {"99"} };	
//
//	
	static final String[][] groupSum2Direct3 = { {}, {}, {}, { "1 1 1" }, {}, {}, { "2 2 2" }, {}, {}, { "3 3 3" }, {},
			{}, { "4 4 4" }, {}, {}, { "5 5 5" }, {}, {}, { "6 6 6" }, {}, {}, { "7 7 7" }, {}, {}, { "8 8 8" }, {}, {} };

	// 组选和值对应的组三
	static final String[][] groupSum2Group3 = { {}, { "0 0 1" }, { "0 0 2", "0 1 1" }, { "0 0 3" },
			{ "0 0 4", "0 2 2", "1 1 2" }, { "0 0 5", "1 2 2", "1 1 3" }, { "0 0 6", "0 3 3", "1 1 4" },
			{ "0 0 7", "1 1 5", "1 3 3", "2 2 3" }, { "0 0 8", "0 4 4", "1 1 6", "2 2 4", "2 3 3" },
			{ "0 0 9", "1 1 7", "1 4 4", "2 2 5" }, { "1 1 8", "2 2 6", "2 4 4", "3 3 4", "5 0 5" },
			{ "1 1 9", "1 5 5", "2 2 7", "3 3 5" }, { "2 2 8", "2 5 5", "3 3 6", "6 0 6" },
			{ "1 6 6", "2 2 9", "3 3 7", "3 5 5", "4 4 5" }, { "2 6 6", "3 3 8", "4 4 6", "4 5 5", "7 0 7" },
			{ "1 7 7", "3 3 9", "3 6 6", "4 4 7" }, { "2 7 7", "4 4 8", "4 6 6", "5 5 6", "8 0 8" },
			{ "1 8 8", "3 7 7", "4 4 9", "5 5 7", "5 6 6" }, { "2 8 8", "4 7 7", "5 5 8", "9 0 9" },
			{ "1 9 9", "3 8 8", "5 5 9", "5 7 7", "6 6 7" }, { "2 9 9", "4 8 8", "6 6 8", "6 7 7" },
			{ "3 9 9", "5 8 8", "6 6 9" }, { "4 9 9", "6 8 8", "7 7 8" }, { "5 9 9", "7 7 9", "7 8 8" }, { "6 9 9" },
			{ "7 9 9", "8 8 9" }, { "8 9 9" } };
	// 组选和值对应的组六
	static final String[][] groupSum2Group6 = { {}, {}, {}, { "0 1 2" }, { "0 1 3" }, { "0 1 4", "0 2 3" },
			{ "0 1 5", "0 2 4", "1 2 3" }, { "0 1 6", "0 2 5", "0 3 4", "1 2 4" },
			{ "0 1 7", "0 2 6", "0 3 5", "1 2 5", "1 3 4" },
			{ "0 1 8", "0 2 7", "0 3 6", "0 4 5", "1 2 6", "1 3 5", "2 3 4" },
			{ "1 0 9", "1 2 7", "1 3 6", "1 4 5", "2 0 8", "2 3 5", "3 0 7", "4 0 6" },
			{ "1 2 8", "1 3 7", "1 4 6", "2 0 9", "2 3 6", "2 4 5", "3 0 8", "3 4 6", "4 0 7", "5 0 6" },
			{ "1 2 9", "1 3 8", "1 4 7", "1 5 6", "2 3 7", "2 4 6", "3 0 9", "3 4 5", "4 0 8", "5 0 7" },
			{ "1 3 9", "1 4 8", "1 5 7", "2 3 8", "2 4 7", "2 5 6", "3 4 6", "4 0 9", "5 0 8", "6 0 7" },
			{ "1 4 9", "1 5 8", "1 6 7", "2 3 9", "2 4 8", "2 5 7", "3 4 7", "3 5 6", "5 0 9", "6 0 8" },
			{ "1 5 9", "1 6 8", "2 4 9", "2 5 8", "2 6 7", "3 4 8", "3 5 7", "4 5 6", "6 0 9", "7 0 8" },
			{ "1 6 9", "1 7 8", "2 5 9", "2 6 8", "3 4 9", "3 5 8", "3 6 7", "4 5 7", "7 0 9" },
			{ "1 7 9", "2 6 9", "2 7 8", "3 5 9", "3 6 8", "4 5 8", "4 6 7", "8 0 9" },
			{ "1 8 9", "2 7 9", "3 6 9", "3 7 8", "4 5 9", "4 6 8", "5 6 7" },
			{ "2 8 9", "3 7 9", "4 6 9", "4 7 8", "5 6 8" }, { "3 8 9", "4 7 9", "5 6 9", "5 7 8" },
			{ "4 8 9", "5 7 9", "6 7 8" }, { "5 8 9", "6 7 9" }, { "6 8 9" }, { "7 8 9" }, {}, {} };

	/**
	 * 拆单操作
	 * 
	 * @param printData
	 * @param createTime
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws DataException
	 */
	public static List<TicketDTO> converData(PrintInterfaceDTO printData, Date createTime) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		TicketDTO ticketNew = null;
		List<String> list = new ArrayList<String>();
		List<String> direct3s = new ArrayList<String>();// 直三
		List<String> group3s = new ArrayList<String>();// 组三
		List<String> group6s = new ArrayList<String>();// 组六
		String[] contents = JsonUtil.getStringArray4Json(printData.getContent());
		SscCompoundContent compoundContent = null;
		
		//三星组选和值 拆成 组三+组六+三星单式直选
		if (printData.getBetType() == SscPlayType.GroupThreeSum.ordinal()) {
			List<String> groupSums = null;
			for (String content : contents) {
				compoundContent = JsonUtil.getObject4JsonString(content, SscCompoundContent.class);
				groupSums = compoundContent.getGroupSumList();
				for (String groupsum : groupSums) {
					int intGroupSum = Integer.valueOf(groupsum).intValue();
					if (intGroupSum >= 0 && intGroupSum < 28) {
						direct3s.addAll(Arrays.asList(groupSum2Direct3[intGroupSum]));
						group3s.addAll(Arrays.asList(groupSum2Group3[intGroupSum]));
						group6s.addAll(Arrays.asList(groupSum2Group6[intGroupSum]));
					}
				}
			}
			// 保存直三
			if (!direct3s.isEmpty()) {
				ticketNew = createTicketDTONew(printData, createTime, direct3s, SscPlayType.DirectThree);
				if (ticketNew.getUnits() > maxUnits) {
					resultList.addAll(doSingle(ticketNew));
				} else {
					resultList.add(ticketNew);
				}
			}

			// 保存组三
			if (!group3s.isEmpty()) {
				ticketNew = createTicketDTONew(printData, createTime, group3s, SscPlayType.ThreeGroup3);
				if (ticketNew.getUnits() > maxUnits) {
					resultList.addAll(doSingle(ticketNew));
				} else {
					resultList.add(ticketNew);
				}
			}

			// 保存组六
			if (!group6s.isEmpty()) {
				ticketNew = createTicketDTONew(printData, createTime, group6s, SscPlayType.ThreeGroup6);
				if (ticketNew.getUnits() > maxUnits) {
					resultList.addAll(doSingle(ticketNew));
				} else {
					resultList.add(ticketNew);
				}

			}
		} 
		
		//三星直选和值
		else if (printData.getBetType() == SscPlayType.DirectThreeSum.ordinal()) {
			for (String content : contents) {
				compoundContent = JsonUtil.getObject4JsonString(content, SscCompoundContent.class);
				for (String directSum : compoundContent.getDirectSumList()) {
					int inSum = Integer.valueOf(directSum).intValue();
					if (inSum >= 0 && inSum < 28) {
						ticketNew = createTicketDTONew(printData,SscConstant.UNITS_DIRECT_THREE_SUM[inSum] , createTime,
								String.valueOf(inSum), SalesMode.SINGLE);
						resultList.add(ticketNew);
					}
				}
			}
		}
		
		//两星直选和值
		else if (printData.getBetType() == SscPlayType.DirectTwoSum.ordinal()) {
			for (String content : contents) {
				compoundContent = JsonUtil.getObject4JsonString(content, SscCompoundContent.class);
				for (String directSum : compoundContent.getDirectSumList()) {
					int inSum = Integer.valueOf(directSum).intValue();
					if (inSum >= 0 && inSum < 28) {
						ticketNew = createTicketDTONew(printData,SscConstant.UNITS_DIRECT_TWO_SUM[inSum] , createTime,
								String.valueOf(inSum), SalesMode.SINGLE);
						resultList.add(ticketNew);
					}
				}
			}
		}
		//两星组选和值
		else if (printData.getBetType() == SscPlayType.GroupTwoSum.ordinal()) {
			for (String content : contents) {
				compoundContent = JsonUtil.getObject4JsonString(content, SscCompoundContent.class);
				for (String groupSum : compoundContent.getGroupSumList()) {
					int inSum = Integer.valueOf(groupSum).intValue();
					if (inSum >= 0 && inSum < 19) {
						ticketNew = createTicketDTONew(printData, SscConstant.UNITS_GROUP_TWO_SUM[inSum], createTime,
								String.valueOf(inSum), SalesMode.SINGLE);
						resultList.add(ticketNew);
					}
				}
			}
		} 
		//一星复选拆为单式 
		else if (printData.getBetType() == SscPlayType.DirectOne.ordinal()) {
			for (String content : contents) {
				compoundContent = JsonUtil.getObject4JsonString(content, SscCompoundContent.class);
				for (String area5 : compoundContent.getArea5List()) {
					list.add(area5);
				}
			}
			if (list.size() > maxUnits) {
				resultList.addAll(singleMaxUnits(printData, createTime, list, SscPlayType.DirectOne));
			} else {
				ticketNew = createTicketDTONew(printData, createTime, list, SscPlayType.DirectOne);
				resultList.add(ticketNew);
			}
		}
		//五星通选拆为单式
		else if (printData.getBetType() == SscPlayType.AllFive.ordinal()) {
			for (String content : contents) {
				compoundContent = JsonUtil.getObject4JsonString(content, SscCompoundContent.class);
				for (String area1 : compoundContent.getArea1List()) {
					for (String area2 : compoundContent.getArea2List()) {
						for (String area3 : compoundContent.getArea3List()) {
							for (String area4 : compoundContent.getArea4List()) {
								for (String area5 : compoundContent.getArea5List()) {
									ticketNew = createTicketDTONew(printData, 1, createTime, area1 + Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER
											+ area2 + Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER + area3 + Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER + area4
											+ Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER + area5, SalesMode.SINGLE);
									resultList.add(ticketNew);
								}
							}
						}
					}
				}
			}
		} 
		//大小双单拆为单式
		else if (printData.getBetType() == SscPlayType.BigSmallDoubleSingle.ordinal()) {
			for (String content : contents) {
				compoundContent = JsonUtil.getObject4JsonString(content, SscCompoundContent.class);
				for (String area4 : compoundContent.getArea4List()) {
					for (String area5 : compoundContent.getArea5List()) {
						list.add(area4 + Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER + area5);
					}
				}
			}
			
			ticketNew = createTicketDTONew(printData, createTime, list, SscPlayType.BigSmallDoubleSingle);
			if (ticketNew.getUnits() > maxUnits) {
				resultList.addAll(doSingle(ticketNew));
			} else {
				resultList.add(ticketNew);
			}
		} 
		
		//两星组选
		else if (printData.getBetType() == SscPlayType.GroupTwo.ordinal()) {
			for (String content : contents) {
				compoundContent = JsonUtil.getObject4JsonString(content, SscCompoundContent.class);
				// 胆拖
				if (null != compoundContent.getBetDanList() && compoundContent.getBetDanList().size() > 0) {
					SscDanTuoDataConver sscDanTuoDataConver = new SscDanTuoDataConver(SscPlayType.values()[printData.getBetType()].getLineLimit(),
							compoundContent.getBetDanList(), compoundContent.getGroupTwoList());
					List<List<String>> results = sscDanTuoDataConver.getResultList();
					List listcode = new ArrayList();

					for (List<String> code : results) {
						listcode.add(StringUtils.join(code, Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
					}
					ticketNew = createTicketDTONew(printData, createTime, listcode,
							SscPlayType.values()[printData.getBetType()]);
					resultList.add(ticketNew);

				} else {
					if(compoundContent.getUnits().intValue()==1) {
						//单式
						List codeList = new ArrayList();
						codeList.add(StringUtils.join(compoundContent.getGroupTwoList(), Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
						ticketNew = createTicketDTONew(printData, compoundContent.getUnits(), createTime, codeList,
								SalesMode.SINGLE);
						resultList.add(ticketNew);
					}else {
						SscDanTuoDataConver sscDanTuoDataConver = new SscDanTuoDataConver(SscPlayType.values()[printData.getBetType()].getLineLimit(),
								compoundContent.getBetDanList(), compoundContent.getGroupTwoList());
						List<List<String>> results = sscDanTuoDataConver.getResultList();
						List listcode = new ArrayList();

						for (List<String> code : results) {
							listcode.add(StringUtils.join(code, Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
						}
						ticketNew = createTicketDTONew(printData, createTime, listcode,
								SscPlayType.values()[printData.getBetType()]);
						resultList.add(ticketNew);
					}
				}
			}
		}

		
		
		else if (printData.getBetType() == SscPlayType.ThreeGroup3.ordinal()) {
			for (String content : contents) {
				compoundContent = JsonUtil.getObject4JsonString(content, SscCompoundContent.class);
				// 胆拖
				if (null != compoundContent.getBetDanList() && compoundContent.getBetDanList().size() > 0) {
					SscDanTuoDataConver sscDanTuoDataConver = new SscDanTuoDataConver(3,
							compoundContent.getBetDanList(), compoundContent.getGroup3List());
					List<List<String>> results = sscDanTuoDataConver.getResultList();
					List listcode = new ArrayList();
					for (List<String> code : results) {
						listcode.add(StringUtils.join(code, Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
					}
					ticketNew = createTicketDTONew(printData, createTime, listcode,
							SscPlayType.values()[printData.getBetType()]);
					resultList.add(ticketNew);

				} else {
					if(compoundContent.getUnits()==1) {
						//单式
						List codeList = new ArrayList();
						codeList.add(StringUtils.join(compoundContent.getGroup3List(), Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
						ticketNew = createTicketDTONew(printData, compoundContent.getUnits(), createTime, codeList,
								SalesMode.SINGLE);
						resultList.add(ticketNew);
					}else {
						SscDanTuoDataConver sscDanTuoDataConver = new SscDanTuoDataConver(3,
								compoundContent.getBetDanList(), compoundContent.getGroup3List());
						List<List<String>> results = sscDanTuoDataConver.getResultList();
						List listcode = new ArrayList();
						for (List<String> code : results) {
							listcode.add(StringUtils.join(code, Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
						}
						ticketNew = createTicketDTONew(printData, createTime, listcode,
								SscPlayType.values()[printData.getBetType()]);
						resultList.add(ticketNew);
					}
				}
			}
		}

		else if (printData.getBetType() == SscPlayType.ThreeGroup6.ordinal()) {
			for (String content : contents) {
				compoundContent = JsonUtil.getObject4JsonString(content, SscCompoundContent.class);
				// 胆拖
				if (null != compoundContent.getBetDanList() && compoundContent.getBetDanList().size() > 0) {
					SscDanTuoDataConver sscDanTuoDataConver = new SscDanTuoDataConver(
							SscPlayType.values()[printData.getBetType()].getLineLimit(),
							compoundContent.getBetDanList(), compoundContent.getGroup6List());
					List<List<String>> results = sscDanTuoDataConver.getResultList();
					List listcode = new ArrayList();
					for (List<String> code : results) {
						listcode.add(StringUtils.join(code, Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
					}
					ticketNew = createTicketDTONew(printData, createTime, listcode,
							SscPlayType.values()[printData.getBetType()]);
					if (ticketNew.getUnits() > maxUnits) {
						resultList.addAll(doSingle(ticketNew));
					} else {
						resultList.add(ticketNew);
					}

				} else {
					if(compoundContent.getUnits()==1) {
						//单式
						List codeList = new ArrayList();
						codeList.add(StringUtils.join(compoundContent.getGroup6List(), Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
						ticketNew = createTicketDTONew(printData, compoundContent.getUnits(), createTime, codeList,
								SalesMode.SINGLE);
						resultList.add(ticketNew);
					}else {
						SscDanTuoDataConver sscDanTuoDataConver = new SscDanTuoDataConver(3,
								compoundContent.getBetDanList(), compoundContent.getGroup3List());
						List<List<String>> results = sscDanTuoDataConver.getResultList();
						List listcode = new ArrayList();
						for (List<String> code : results) {
							listcode.add(StringUtils.join(code, Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
						}
						ticketNew = createTicketDTONew(printData, createTime, listcode,
								SscPlayType.values()[printData.getBetType()]);
						resultList.add(ticketNew);
					}
				}
			}
		}
		return resultList;
	}

	/**
	 *三星组选和值拆票
	 * @param printData
	 * @param createTime
	 * @param contentList
	 * @param playType
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private static TicketDTO createTicketDTONew(PrintInterfaceDTO printData, Date createTime, List<String> contentList,
			SscPlayType playType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String content = StringUtils.join(contentList, Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
		int units = contentList.size() ;
		TicketDTO ticketNew = new TicketDTO();
		PropertyUtils.copyProperties(ticketNew, printData);
		ticketNew.setId(null);
		ticketNew.setPrintinterfaceId(printData.getId());
		ticketNew.setContent(content);
		ticketNew.setUnits(units);
		ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
		ticketNew.setCreateTime(createTime);
		ticketNew.setMode(SalesMode.SINGLE);
		ticketNew.setBetType(playType.getBetTypeValue());
		return ticketNew;
		
	}

	// 各种和值 一票一号处理
	private static TicketDTO createTicketDTONew(PrintInterfaceDTO printData, int units, Date createTime,
			List<String> contentList, SalesMode salesMode) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		String content = StringUtils.join(contentList, Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
		return createTicketDTONew(printData,units,createTime, content,salesMode);
	}

	private static TicketDTO createTicketDTONew(PrintInterfaceDTO printData, int units, Date createTime, String content,
			SalesMode salesMode) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		TicketDTO ticketNew = new TicketDTO();
		PropertyUtils.copyProperties(ticketNew, printData);
		ticketNew.setId(null);
		ticketNew.setPrintinterfaceId(printData.getId());
		ticketNew.setContent(content);
		ticketNew.setUnits(units);
		ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
		ticketNew.setCreateTime(createTime);
		ticketNew.setMode(salesMode);
		ticketNew.setBetType(printData.getBetType());
		return ticketNew;
	}

	// 打印实体 根据最大注数拆弹
	private static List<TicketDTO> singleMaxUnits(PrintInterfaceDTO printData, Date createTime, List<String> contentList,
			SscPlayType playType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();// 转换后最终返回的实体列表
		TicketDTO ticketNew = null;// 转化后的单个方案实体
		if (printData.getUnits() > 0) {
			int multiples = printData.getUnits()/maxUnits;
			List<String> subs = null;
			for (int i = 0; i < multiples; i++) {
				subs = contentList.subList(i * maxUnits, (i + 1) * maxUnits);
				ticketNew = new TicketDTO();
				PropertyUtils.copyProperties(ticketNew, printData);
				ticketNew.setId(null);
				ticketNew.setPrintinterfaceId(printData.getId());
				String singleContent = StringUtils.join(subs, Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				ticketNew.setContent(singleContent);
				ticketNew.setUnits(maxUnits);
				ticketNew.setSchemeCost(maxUnits * unitsMoney * ticketNew.getMultiple());
				ticketNew.setCreateTime(createTime);
				ticketNew.setMode(SalesMode.SINGLE);
				resultList.add(ticketNew);
			}
			if (multiples * maxUnits < printData.getUnits()) {
				subs = contentList.subList(multiples * maxUnits, printData.getUnits());
				ticketNew = new TicketDTO();
				PropertyUtils.copyProperties(ticketNew, printData);
				ticketNew.setId(null);
				ticketNew.setPrintinterfaceId(printData.getId());
				String singleContent = StringUtils.join(subs, Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				ticketNew.setContent(singleContent);
				ticketNew.setUnits(subs.size());
				ticketNew.setSchemeCost(subs.size() * unitsMoney * ticketNew.getMultiple());
				ticketNew.setCreateTime(createTime);
				ticketNew.setMode(SalesMode.SINGLE);
				resultList.add(ticketNew);
			}
		}
		return resultList;
	}

	// 出票实体 根据最大注数拆弹
	public static List<TicketDTO> doSingle(TicketDTO ticket) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		TicketDTO ticketNew = null;
		if (ticket.getUnits() > maxUnits) {
			String[] arr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			int units = 0;
			List<String> contentList = new ArrayList<String>();
			for (String line : arr) {
				contentList.add(line);
				units++;
				if (units == maxUnits) {
					ticketNew = new TicketDTO();
					PropertyUtils.copyProperties(ticketNew, ticket);
					ticketNew.setId(null);
					ticketNew.setPrintinterfaceId(ticket.getPrintinterfaceId());
					ticketNew.setCreateTime(ticket.getCreateTime());
					ticketNew.setUnits(units);
					ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
					ticketNew.setContent(StringUtils.join(contentList, "\r\n"));
					resultList.add(ticketNew);
					units = 0;// 计数复位
					contentList.clear();// 重置
				}
			}
			if (units > 0) {
				ticketNew = new TicketDTO();
				PropertyUtils.copyProperties(ticketNew, ticket);
				ticketNew.setId(null);
				ticketNew.setPrintinterfaceId(ticket.getPrintinterfaceId());
				ticketNew.setCreateTime(ticket.getCreateTime());
				ticketNew.setUnits(units);
				ticketNew.setSchemeCost(units * unitsMoney * ticketNew.getMultiple());
				ticketNew.setContent(StringUtils.join(contentList, "\r\n"));
				resultList.add(ticketNew);
			}
		} else {
			resultList.add(ticket);
		}
		return resultList;
	}

	public static void main(String args[]) {
		List<String> betDanList = new ArrayList();
		betDanList.add("1");

		List<String> betList = new ArrayList();
		betList.add("4");
		betList.add("5");
		betList.add("6");
		betList.add("7");

		SscDanTuoDataConver bb = new SscDanTuoDataConver(2, betDanList, betList);
		List<List<String>> resultList = bb.getResultList();
		for (List<String> aa : resultList) {
			for (String vv : aa) {
				System.out.print(vv + ",");
			}
			System.out.println();
		}

	}
}
