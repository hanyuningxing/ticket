package com.cai310.lottery.utils;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class CpdyjIssueVisitor extends VisitorSupport{
	private String issue;
	private String singleEndTime;
	private String compoundEndTime;
	private String endTime;
	private Boolean isSuccess=Boolean.FALSE;
	public void visit(Attribute node){
		
	}
	public void visit(Element node){
		if("xCode".equals(node.getName())){
			///是否成功
			if(StringUtils.isNotBlank(node.getText())){
				if(String.valueOf("0").equals(node.getText().trim())){
					///成功
					isSuccess=Boolean.TRUE;
				}
			}
		}
		if("xValue".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				String issueData = node.getText().trim();
				String[] issueDataArr = issueData.split("_");
				issue = issueDataArr[0];
				singleEndTime = issueDataArr[1];
				compoundEndTime = issueDataArr[2];
				endTime = issueDataArr[3];
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
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getSingleEndTime() {
		return singleEndTime;
	}
	public void setSingleEndTime(String singleEndTime) {
		this.singleEndTime = singleEndTime;
	}
	public String getCompoundEndTime() {
		return compoundEndTime;
	}
	public void setCompoundEndTime(String compoundEndTime) {
		this.compoundEndTime = compoundEndTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
