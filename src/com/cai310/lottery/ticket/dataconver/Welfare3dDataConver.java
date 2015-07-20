package com.cai310.lottery.ticket.dataconver;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.support.welfare3d.Welfare3dCompoundContent;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;
import com.cai310.utils.JsonUtil;

/**
 * 福彩3D特殊玩法拆单
 * @author jack
 *
 */
public class Welfare3dDataConver {
	static int unitsMoney = 2;
	//组选和值对应的组三
	static final String[][] groupSum2Group3 = {{},{"0 0 1"},{"0 0 2","0 1 1"},{"0 0 3"},{"0 0 4","0 2 2","1 1 2"},{"0 0 5","1 2 2","1 1 3"},
			{"0 0 6","0 3 3","1 1 4"},{"0 0 7","1 1 5","1 3 3","2 2 3"},{"0 0 8","0 4 4","1 1 6","2 2 4","2 3 3"},
			{"0 0 9","1 1 7","1 4 4","2 2 5"},{"1 1 8","2 2 6","2 4 4","3 3 4","5 0 5"},{"1 1 9","1 5 5","2 2 7","3 3 5"},
			{"2 2 8","2 5 5","3 3 6","6 0 6"},{"1 6 6","2 2 9","3 3 7","3 5 5","4 4 5"},{"2 6 6","3 3 8","4 4 6","4 5 5","7 0 7"},
			{"1 7 7","3 3 9","3 6 6","4 4 7"},{"2 7 7","4 4 8","4 6 6","5 5 6","8 0 8"},{"1 8 8","3 7 7","4 4 9","5 5 7","5 6 6"},
			{"2 8 8","4 7 7","5 5 8","9 0 9"},{"1 9 9","3 8 8","5 5 9","5 7 7","6 6 7"},{"2 9 9","4 8 8","6 6 8","6 7 7"},
			{"3 9 9","5 8 8","6 6 9"},{"4 9 9","6 8 8","7 7 8"},{"5 9 9","7 7 9","7 8 8"},{"6 9 9"},{"7 9 9","8 8 9"},{"8 9 9"}};
	static final String[][] groupSum2Direct = {{},{},{},{"1 1 1"},{},{},{"2 2 2"},{},{},{"3 3 3"},{},{},{"4 4 4"},{},{},{"5 5 5"},{},{},{"6 6 6"},{},{},{"7 7 7"},{},{},{"8 8 8"},{},{}};
	//组选和值对应的组六
	static final String[][] groupSum2Group6 = {{},{},{},{"0 1 2"},{"0 1 3"},{"0 1 4","0 2 3"},{"0 1 5","0 2 4","1 2 3"},{"0 1 6","0 2 5","0 3 4","1 2 4"},
			{"0 1 7","0 2 6","0 3 5","1 2 5","1 3 4"},{"0 1 8","0 2 7","0 3 6","0 4 5","1 2 6","1 3 5","2 3 4"},{"1 0 9","1 2 7","1 3 6","1 4 5","2 0 8","2 3 5","3 0 7","4 0 6"},
			{"1 2 8","1 3 7","1 4 6","2 0 9","2 3 6","2 4 5","3 0 8","3 4 6","4 0 7","5 0 6"},{"1 2 9","1 3 8","1 4 7","1 5 6","2 3 7","2 4 6","3 0 9","3 4 5","4 0 8","5 0 7"},
			{"1 3 9","1 4 8","1 5 7","2 3 8","2 4 7","2 5 6","3 4 6","4 0 9","5 0 8","6 0 7"},{"1 4 9","1 5 8","1 6 7","2 3 9","2 4 8","2 5 7","3 4 7","3 5 6","5 0 9","6 0 8"},
			{"1 5 9","1 6 8","2 4 9","2 5 8","2 6 7","3 4 8","3 5 7","4 5 6","6 0 9","7 0 8"},{"1 6 9","1 7 8","2 5 9","2 6 8","3 4 9","3 5 8","3 6 7","4 5 7","7 0 9"},
			{"1 7 9","2 6 9","2 7 8","3 5 9","3 6 8","4 5 8","4 6 7","8 0 9"},{"1 8 9","2 7 9","3 6 9","3 7 8","4 5 9","4 6 8","5 6 7"},{"2 8 9","3 7 9","4 6 9","4 7 8","5 6 8"},
			{"3 8 9","4 7 9","5 6 9","5 7 8"},{"4 8 9","5 7 9","6 7 8"},{"5 8 9","6 7 9"},{"6 8 9"},{"7 8 9"},{},{}};

	/**
	 * 拆单操作
	 * @param printData
	 * @param createTime
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static List<TicketDTO> converData(PrintInterfaceDTO printData, Date createTime) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		TicketDTO ticketNew = null;
		List<String> group3s = new ArrayList<String>();//组三
		List<String> directs = new ArrayList<String>();//直选（豹子）
		List<String> group6s = new ArrayList<String>();//组六
		String[] contents = JsonUtil.getStringArray4Json(printData.getContent());
		Welfare3dCompoundContent welfare3dCompoundContent = null;
		if(printData.getBetType()==Welfare3dPlayType.GroupSum.ordinal()){//组选和值需要区分组选3与组选6				
			List<String> groupSums = null;			
			for(String content:contents){
				welfare3dCompoundContent = JsonUtil.getObject4JsonString(content, Welfare3dCompoundContent.class);
				groupSums = welfare3dCompoundContent.getGroupSumList();					
				for(String groupsum:groupSums){
					int intGroupSum = Integer.valueOf(groupsum).intValue();
					for(int i=1;i<=26;i++){
						if(i==intGroupSum){
							directs.addAll(Arrays.asList(groupSum2Direct[intGroupSum]));
							group3s.addAll(Arrays.asList(groupSum2Group3[intGroupSum]));
							group6s.addAll(Arrays.asList(groupSum2Group6[intGroupSum]));
							break;
						}
					}						
				}
			}
			//保存直选
			if(!directs.isEmpty()){
				ticketNew = createTicketDTONew(printData,createTime,directs,Welfare3dPlayType.Direct);
				resultList.add(ticketNew);
			}		
			//保存组三
			if(!group3s.isEmpty()){
				ticketNew = createTicketDTONew(printData,createTime,group3s,Welfare3dPlayType.Group3);
				resultList.add(ticketNew);
			}			
			//保存组六
			if(!group6s.isEmpty()){
				ticketNew = createTicketDTONew(printData,createTime,group6s,Welfare3dPlayType.Group6);
				resultList.add(ticketNew);
			}
		}else if(printData.getBetType()==Welfare3dPlayType.DirectKuadu.ordinal()){//直选跨度改为单式直选
			List<String> directKuadus = null;//直选跨度号
			int big = 0;//最大数
			int small = 0;//最小数
			int kuaduNum = 0;//跨度号
			List<String> contentList = new ArrayList<String>();
			for(String content:contents){
				welfare3dCompoundContent = JsonUtil.getObject4JsonString(content, Welfare3dCompoundContent.class);
				directKuadus = welfare3dCompoundContent.getDirectKuaduList();
				for(String kd:directKuadus){
					kuaduNum = Integer.valueOf(kd);
					for(int i=0;i<=9;i++){
						for(int j=0;j<=9;j++){
							for(int k=0;k<=9;k++){
								big = Math.max(Math.max(i, j), k);
								small = Math.min(Math.min(i, j), k);
								if(big-small==kuaduNum){
									contentList.add(i+Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER+j+Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER+k);
								}
							}
						}
					}
				}
			}
			ticketNew = createTicketDTONew(printData,createTime,contentList,Welfare3dPlayType.Direct);
			resultList.add(ticketNew);			
		}else if(printData.getBetType()==Welfare3dPlayType.Group3Kuadu.ordinal()){//组三跨度改为单式组三
			List<String> g3Kuadus = null;//组三跨度号
			int big = 0;
			int small = 0;
			List<Integer> nums = null;
			List<String> contentList = new ArrayList<String>();
			Set<String> resultSet = new HashSet<String>();
			int kuaduNum = 0;//跨度号
			for(String content:contents){
				welfare3dCompoundContent = JsonUtil.getObject4JsonString(content, Welfare3dCompoundContent.class);
				g3Kuadus = welfare3dCompoundContent.getG3KuaduList();
				for(String kd:g3Kuadus){
					kuaduNum = Integer.valueOf(kd);
					for(int i=0;i<=9;i++){
						for(int j=0;j<=9;j++){
							for(int k=0;k<=9;k++){
								big = Math.max(Math.max(i, j), k);
								small = Math.min(Math.min(i, j), k);
								if(big-small==kuaduNum &&(i==j||j==k||i==k)){
									nums = new ArrayList<Integer>();
									nums.add(i);
									nums.add(j);
									nums.add(k);
									Collections.sort(nums);
									resultSet.add(nums.get(0)+Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER+nums.get(1)+Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER+nums.get(2));
								}
							}
						}
					}
				}
			}			
			contentList.addAll(resultSet);
			ticketNew = createTicketDTONew(printData,createTime,contentList,Welfare3dPlayType.Group3);
			resultList.add(ticketNew);	
		}else if(printData.getBetType()==Welfare3dPlayType.Group6Kuadu.ordinal()){//组六跨度改为单式组六
			List<String> g6Kuadus = null;//组六跨度号
			int big = 0;
			int small = 0;
			List<Integer> nums = null;
			List<String> contentList = new ArrayList<String>();
			Set<String> resultSet = new HashSet<String>();
			int kuaduNum = 0;//跨度号
			for(String content:contents){
				welfare3dCompoundContent = JsonUtil.getObject4JsonString(content, Welfare3dCompoundContent.class);
				g6Kuadus = welfare3dCompoundContent.getG6KuaduList();
				for(String kd:g6Kuadus){
					kuaduNum = Integer.valueOf(kd);
					for(int i=0;i<=9;i++){
						for(int j=0;j<=9;j++){
							for(int k=0;k<=9;k++){
								big = Math.max(Math.max(i, j), k);
								small = Math.min(Math.min(i, j), k);
								if(big-small==kuaduNum &&(i!=j&&j!=k&&i!=k)){
									nums = new ArrayList<Integer>();
									nums.add(i);
									nums.add(j);
									nums.add(k);
									Collections.sort(nums);
									resultSet.add(nums.get(0)+Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER+nums.get(1)+Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER+nums.get(2));
								}
							}
						}
					}
				}
			}			
			contentList.addAll(resultSet);
			ticketNew = createTicketDTONew(printData,createTime,contentList,Welfare3dPlayType.Group6);
			resultList.add(ticketNew);	
		}
		return resultList;
	}
	
	/**
	 * 
	 * @param printData
	 * @param createTime
	 * @param contentList
	 * @param playType
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private static TicketDTO createTicketDTONew(PrintInterfaceDTO printData, Date createTime, List<String> contentList,Welfare3dPlayType playType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		TicketDTO ticketNew = new TicketDTO();
		PropertyUtils.copyProperties(ticketNew, printData);
		ticketNew.setId(null);
		ticketNew.setPrintinterfaceId(printData.getId());
		String content = StringUtils.join(contentList, Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
		ticketNew.setContent(content);
		int units = contentList.size();
		ticketNew.setUnits(units);
		ticketNew.setSchemeCost(units*unitsMoney*ticketNew.getMultiple());
		ticketNew.setCreateTime(createTime);
		ticketNew.setMode(SalesMode.SINGLE);
		ticketNew.setBetType((byte)playType.ordinal());
		return ticketNew;
	}
}
