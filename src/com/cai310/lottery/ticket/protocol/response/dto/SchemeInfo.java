package com.cai310.lottery.ticket.protocol.response.dto;

import java.util.List;

public class SchemeInfo {
	/**方案编号*/
	private String schemeId;
	/**方案状态*/
	private String status;//0-委托中,1-落地成功,2-落地失败,-1方案不存在
	/**错误代码*/
	private String errorCode;
	/**错误信息*/
	private String errorMsg;
	/**编码后的中心电子票号*/
	private List ticketIds;	
	/**响应代码*/
	private String responseCode;
	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}
	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	/**
	 * @return the schemeId
	 */
	public String getSchemeId() {
		return schemeId;
	}
	/**
	 * @param schemeId the schemeId to set
	 */
	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	/**
	 * @return the ticketId
	 */
	public List getTicketIds() {
		return ticketIds;
	}
	/**
	 * @param ticketId the ticketId to set
	 */
	public void setTicketIds(List ticketIds) {
		this.ticketIds = ticketIds;
	}
	
	
	
}
