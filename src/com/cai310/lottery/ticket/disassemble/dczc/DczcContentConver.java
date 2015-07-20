package com.cai310.lottery.ticket.disassemble.dczc;

import java.util.List;

import com.cai310.lottery.support.dczc.DczcMatchItem;
import com.cai310.lottery.support.dczc.ItemBF;
import com.cai310.lottery.support.dczc.ItemBQQSPF;
import com.cai310.lottery.support.dczc.ItemSPF;
import com.cai310.lottery.support.dczc.ItemSXDS;
import com.cai310.lottery.support.dczc.ItemZJQS;
import com.cai310.lottery.support.dczc.PlayType;

public class DczcContentConver {
	/**
	 * 复式：将场次内容转为发送格式：5:[胜,平,负]/49:[胜,负]
	 * @param itemList
	 * @param playType
	 * @return
	 */ 
	public static String itemsToStrCompound(List<DczcMatchItem> itemList,PlayType playType){
		int itemValue = 0;//场次选择值
		StringBuffer resultStr = new StringBuffer();
		for(DczcMatchItem item:itemList){
			resultStr.append(item.getLineId()+1).append(":").append("[");
			itemValue = item.getValue();
			switch (playType) {
			case SPF:
				for (ItemSPF type : ItemSPF.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						resultStr.append(type.getText()).append(",");
					}
				}
				resultStr.deleteCharAt(resultStr.length()-1);
				break;
			case ZJQS:
				for (ItemZJQS type : ItemZJQS.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						resultStr.append(type.getText()).append(",");
					}
				}
				resultStr.deleteCharAt(resultStr.length()-1);
				break;
			case SXDS:
				for (ItemSXDS type : ItemSXDS.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						resultStr.append(reText(type.getText(),"+")).append(",");
					}
				}
				resultStr.deleteCharAt(resultStr.length()-1);
				break;
			case BF:
				for (ItemBF type : ItemBF.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						resultStr.append(type.getText()).append(",");
					}
				}
				resultStr.deleteCharAt(resultStr.length()-1);
				break;
			case BQQSPF:
				for (ItemBQQSPF type : ItemBQQSPF.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						resultStr.append(reText(type.getText(),"-")).append(",");
					}
				}
				resultStr.deleteCharAt(resultStr.length()-1);
				break;
			default:
				throw new RuntimeException("玩法不正确.");
			}
			resultStr.append("]/");
		}
		resultStr.deleteCharAt(resultStr.length()-1);
		return resultStr.toString();
	}
	
	/**
	 * 单式：将场次内容转为发送格式：5:[胜]/49:[胜];5:[负]/49:[胜]........
	 * @param itemList
	 * @param playType
	 * @return
	 */
	public static String itemsToStrSimple(List<String> contents,List<String> lineIds,PlayType playType){
		StringBuffer resultStr = new StringBuffer();
		String value = null;
		String text = null;
		
		for(String content:contents){
			String doubleStr = "";
			int lineIdIndex = 0;
			for(int i=0;i<content.length();i++){					
				switch (playType) {
				case SPF:
					value = String.valueOf(content.charAt(i));
					if("#".equals(value))continue;
					resultStr.append(Integer.valueOf(lineIds.get(i))+1).append(":").append("[");
					text = ItemSPF.valueOfValue(value).getText();
					resultStr.append(text).append("]/");
					break;
				case ZJQS:
					value = String.valueOf(content.charAt(i));
					if("#".equals(value))continue;
					resultStr.append(Integer.valueOf(lineIds.get(i))+1).append(":").append("[");
					text = ItemZJQS.valueOfValue(value).getText();
					resultStr.append(text).append("]/");
					break;
				case SXDS:
					value = String.valueOf(content.charAt(i));
					if("#".equals(value))continue;
					resultStr.append(Integer.valueOf(lineIds.get(i))+1).append(":").append("[");
					text = ItemSXDS.valueOfValue(value).getText();
					text = reText(text,"+");
					resultStr.append(text).append("]/");
					break;
				case BF:
					value = String.valueOf(content.charAt(i));
					if("#".equals(value))continue;
					doubleStr = doubleStr.concat(value);
					if(doubleStr.length()==2){						
						resultStr.append(Integer.valueOf(lineIds.get(lineIdIndex))+1).append(":").append("[");
						text = ItemBF.valueOfValue(doubleStr).getText();
						resultStr.append(text).append("]/");
						doubleStr="";
						lineIdIndex++;
					}
					break;
				case BQQSPF:
					value = String.valueOf(content.charAt(i));
					if("#".equals(value))continue;
					doubleStr = doubleStr.concat(value);
					if(doubleStr.length()==2){
						resultStr.append(Integer.valueOf(lineIds.get(lineIdIndex))+1).append(":").append("[");
						text = ItemBQQSPF.valueOfValue(doubleStr).getText();
						text = reText(text,"-");
						resultStr.append(text).append("]/");
						doubleStr="";
						lineIdIndex++;
					}					
					break;
				default:
					throw new RuntimeException("玩法不正确.");
				}			
			}
			resultStr.deleteCharAt(resultStr.length()-1).append(";");
		}
		resultStr.deleteCharAt(resultStr.length()-1);
		return resultStr.toString();
	}
	
	/**
	 * 
	 * @param text
	 * @param insertCode
	 * @return
	 */
	private static String reText(String text,String insertCode){
		if(text.length()==2){
			return text.charAt(0)+insertCode+text.charAt(1);
		}else{
			return text;
		}
	}
	
	
}
