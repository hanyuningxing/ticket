package com.cai310.lottery.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class FetchAwardVisitor extends VisitorSupport{
	private Boolean isSuccess=Boolean.FALSE;
	private Boolean isDataSuccess=Boolean.TRUE;
	private final String xCodeElementName="xCode";
	private final String resultElementName="xValue";
	private final String awardSplitString=",";
	private CpdyjAwardData cpdyjAwardData;
	private List<CpdyjAwardData>  cpdyjFetchDataList;
	public void visit(Attribute node){
		
	}
	public void visit(Element node){
		if(xCodeElementName.equals(node.getName())){
			///是否成功
			if(StringUtils.isNotBlank(node.getText())){
				if(String.valueOf("0").equals(node.getText().trim())){
					///成功
					isSuccess=Boolean.TRUE;
				}
			}
		}
		if(resultElementName.equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				String fetchData = node.getText().trim();
				if(null==cpdyjFetchDataList||cpdyjFetchDataList.isEmpty()){
					cpdyjFetchDataList=new ArrayList<CpdyjAwardData>();
				}
				if(fetchData.indexOf(awardSplitString)!=-1){
					//多个方案
					String[] arr=fetchData.split(awardSplitString);
					for (String awardData : arr) {
						try {
							cpdyjAwardData=new CpdyjAwardData(awardData);
							cpdyjFetchDataList.add(cpdyjAwardData);
						} catch (Exception e) {
							e.printStackTrace();
							isDataSuccess=Boolean.FALSE;
						}
					}
				}else{
					///只有一个方案得奖
					try {
						cpdyjAwardData=new CpdyjAwardData(fetchData);
						cpdyjFetchDataList.add(cpdyjAwardData);
					} catch (Exception e) {
						e.printStackTrace();
						isDataSuccess=Boolean.FALSE;
					}
				}
			}
		}
	}
	
	/**
	 * @return the isSuccess
	 */
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	/**
	 * @return the isDataSuccess
	 */
	public Boolean getIsDataSuccess() {
		return isDataSuccess;
	}
	/**
	 * @param isDataSuccess the isDataSuccess to set
	 */
	public void setIsDataSuccess(Boolean isDataSuccess) {
		this.isDataSuccess = isDataSuccess;
	}
	/**
	 * @return the cpdyjFetchDataList
	 */
	public List<CpdyjAwardData> getCpdyjFetchDataList() {
		return cpdyjFetchDataList;
	}
	/**
	 * @param cpdyjFetchDataList the cpdyjFetchDataList to set
	 */
	public void setCpdyjFetchDataList(List<CpdyjAwardData> cpdyjFetchDataList) {
		this.cpdyjFetchDataList = cpdyjFetchDataList;
	}

}
