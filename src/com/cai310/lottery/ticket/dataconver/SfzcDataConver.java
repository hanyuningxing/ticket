package com.cai310.lottery.ticket.dataconver;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.lottery.support.zc.SchemeConverWork;
import com.cai310.lottery.support.zc.SfzcCompoundItem;
import com.cai310.lottery.support.zc.ZcCompoundItem;
import com.cai310.lottery.support.zc.ZcUtils;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;

/**
 * 体彩胜负足彩特殊玩法拆单
 * @author jack
 *
 */
public class SfzcDataConver {
	static int unitsMoney = 2;
	static int maxUnits = 5;
	private static String CONTENTCONNECTCODE = "-";
	/**
	 * 拆单操作
	 * @param printData
	 * @param createTime 
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public List<TicketDTO> converData(PrintInterfaceDTO printData, Date createTime) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		int betType = printData.getBetType();
		PlayType playType= PlayType.values()[betType];
	    switch(playType){
	    case SFZC14:
	    	return this.doCompound(printData, createTime);
	    case SFZC9:
	    	return this.doCompound9(printData, createTime);
	    default:
			throw new RuntimeException("足彩胜负玩法方式不合法.");
		}
	}
	
	/**
	 * 14场复式
	 */
	protected List<TicketDTO> doCompound(PrintInterfaceDTO printData, Date createTime)throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		TicketDTO ticketNew = new TicketDTO();
		PropertyUtils.copyProperties(ticketNew, printData);		
		String[] itemStrs = printData.getContent().split(String.valueOf(ZcUtils.getContentSpiltCode()));
		SfzcCompoundItem item;
		StringBuffer betContent = new StringBuffer();
		for (int i = 0; i < itemStrs.length; i++) {
			item = new SfzcCompoundItem(Byte.valueOf(itemStrs[i]).byteValue(), i);
			betContent.append(item.toBetString()).append(CONTENTCONNECTCODE);
		}
		betContent.deleteCharAt(betContent.length()-1);
		String resultContent = betContent.toString();
		ticketNew.setId(null);
		ticketNew.setPrintinterfaceId(printData.getId());
		ticketNew.setContent(resultContent);
		ticketNew.setCreateTime(createTime);		
		resultList.add(ticketNew);
		return resultList;
	}	
	
	/**
	 * 任选9场复式 
	 * 格式：4;1-9-9-10-2-2-10-2-2-2-2-0-0-0
	 * @param printData
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	protected List<TicketDTO> doCompound9(PrintInterfaceDTO printData, Date createTime) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		//胆码与方案内容分割符
		String danMinHitSpiltCode = String.valueOf(ZcUtils.getDanMinHitContentSpiltCode());
		
		//分解胆码和投注内容
		String[] contents = printData.getContent().split(danMinHitSpiltCode);		
		int danMinHit = Integer.valueOf(contents[0]).intValue();
		String[] itemStrs = contents[1].split(String.valueOf(ZcUtils.getContentSpiltCode()));
		
		//抽取胆码与非胆码投注项
		SfzcCompoundItem[] items = new SfzcCompoundItem[itemStrs.length];
		for (int i = 0; i < itemStrs.length; i++) {
			items[i] = new SfzcCompoundItem(Byte.valueOf(itemStrs[i]).byteValue(), i);
		}
		List<SfzcCompoundItem> danList = new ArrayList<SfzcCompoundItem>();
		List<SfzcCompoundItem> unDanList = new ArrayList<SfzcCompoundItem>();
		for (SfzcCompoundItem item : items) {
			if (item.selectedCount() > 0) {
				if (item.isShedan()) {
					danList.add(item);
				} else {
					unDanList.add(item);
				}
			}
		}
		
		//计算去除胆码，拆解为标准格式
		SchemeConverWork<SfzcCompoundItem> work = new SchemeConverWork<SfzcCompoundItem>(
				ZcUtils.SFZC9_MATCH_MINSELECT_COUNT, danList, unDanList, danMinHit, -1);
		
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();//转换后最终返回的实体列表
		TicketDTO ticketNew = null;//转化后的单个方案实体
		ZcCompoundItem[] itemStandard = null;//标准格式
		StringBuffer content=null;//转换后的单注叠加内容
		String resultContent=null;//最终的转换内容
		int units=0;//方案拆解后的每项注数
		String bet = null;
		for (List<SfzcCompoundItem> comItems : work.getResultList()) {
			itemStandard = ZcUtils.getStandardSfItems(comItems);//转为14场标准格式
			ticketNew = new TicketDTO();
			PropertyUtils.copyProperties(ticketNew, printData);
			content=new StringBuffer();
			for(ZcCompoundItem item:itemStandard){
				bet = item.toBetString();
				content.append(bet).append(CONTENTCONNECTCODE);
			}
			resultContent = content.deleteCharAt(content.length()-1).toString();
			units = this.countCompoundUnits(resultContent.toString());//计算注数
		    ticketNew.setId(null);
			ticketNew.setPrintinterfaceId(printData.getId());
			ticketNew.setContent(resultContent);//设置内容
			ticketNew.setUnits(units);//设置注数
			ticketNew.setSchemeCost(unitsMoney*units*ticketNew.getMultiple());//设置金额
			ticketNew.setCreateTime(createTime);
			ticketNew.setMode(SalesMode.COMPOUND);
			resultList.add(ticketNew);//加入最终列表
		}
		return resultList;
	}
	
	/**
	 * 复式注数计算
	 * @param content
	 * @return
	 */
	private int countCompoundUnits(String content){
		int units = 1;
		String[] itemStrs = content.split(CONTENTCONNECTCODE);
		for(String itemStr:itemStrs){
			units = units * itemStr.length();
		}
		return units;
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> singleList = new ArrayList<Integer>();
		singleList.add(1);
		singleList.add(2);
		singleList.add(3);
		singleList.add(4);
		singleList.add(5);
		singleList.add(6);
		singleList.add(7);

		int maxUnits = 5;
		int multiples = singleList.size()/maxUnits;
		List<Integer> subs = null;
        for(int i=0;i<multiples;i++){
        	subs = singleList.subList(i*maxUnits, (i+1)*maxUnits);
        	for(Integer code : subs){
        		System.out.print(code);
        	}
        	System.out.println();
        }
        subs = singleList.subList(multiples*maxUnits, singleList.size());
        for(Integer code : subs){
    		System.out.print(code);
    	}
    	System.out.println();
	}

}
