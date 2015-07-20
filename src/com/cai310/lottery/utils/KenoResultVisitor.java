package com.cai310.lottery.utils;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class KenoResultVisitor extends VisitorSupport{
	private String result;
	private String issueNum;
	private Boolean isSuccess=Boolean.FALSE;
	private final String xCodeElementName="xCode";
	private final String resultElementName="xValue";
	private final String iussueAndResultSplitString="_";
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
				String iussueAndResult = node.getText().trim();
				if(iussueAndResult.indexOf(iussueAndResultSplitString)!=-1){
					String[] arr=iussueAndResult.split(iussueAndResultSplitString);
					if(arr.length!=2){
						result=null;
						issueNum=null;
					}else{
						this.setIssueNum(arr[0]);
						this.setResult(arr[1]);
						isSuccess=Boolean.TRUE;
					}
				}else{
					//开奖格式出错
					result=null;
					issueNum=null;
				}
			}else{
				result=null;
				issueNum=null;
			}
		}
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the issueNum
	 */
	public String getIssueNum() {
		return issueNum;
	}
	/**
	 * @param issueNum the issueNum to set
	 */
	public void setIssueNum(String issueNum) {
		this.issueNum = issueNum;
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

}
