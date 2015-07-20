package com.cai310.lottery.ticket.dataconver;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.support.zc.LczcCompoundItem;
import com.cai310.lottery.support.zc.ZcUtils;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;

/**
 * 体彩六场半全场足彩特殊玩法拆单
 * @author jack
 *
 */
public class LczcDataConver {
	static int unitsMoney = 2;
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
		List<TicketDTO> resultList = new ArrayList<TicketDTO>();
		TicketDTO ticketNew = new TicketDTO();
		PropertyUtils.copyProperties(ticketNew, printData);		
		String[] itemStrs = printData.getContent().split(String.valueOf(ZcUtils.getContentSpiltCode()));
		LczcCompoundItem item;
		StringBuffer betContent = new StringBuffer();
		for (int i = 0; i < itemStrs.length; i++) {
			item = new LczcCompoundItem(Byte.valueOf(itemStrs[i]).byteValue());
			betContent.append(item.toBetString()).append(String.valueOf(ZcUtils.getContentSpiltCode()));
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
