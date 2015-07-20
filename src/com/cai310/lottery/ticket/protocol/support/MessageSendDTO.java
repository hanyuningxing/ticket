package com.cai310.lottery.ticket.protocol.support;

public class MessageSendDTO {
	
	/** 交易通信url */
	private String transationUrl;
	
	/**交易类型key */
	private String typekey;
	
	/**交易类型 */
	private int type;
	
	/** 消息主体key */
	private String messageKey;
	
	/** 消息主体 */
	private String message;
	
	/** message简单发送*/
	private boolean simpleSend;
	
	/** message编码*/
	private String encoding;

	/**
	 * @return the transationUrl
	 */
	public String getTransationUrl() {
		return transationUrl;
	}

	/**
	 * @param transationUrl the transationUrl to set
	 */
	public void setTransationUrl(String transationUrl) {
		this.transationUrl = transationUrl;
	}

	/**
	 * @return the typekey
	 */
	public String getTypekey() {
		return typekey;
	}

	/**
	 * @param typekey the typekey to set
	 */
	public void setTypekey(String typekey) {
		this.typekey = typekey;
	}

	/**
	 * @return the messageKey
	 */
	public String getMessageKey() {
		return messageKey;
	}

	/**
	 * @param messageKey the messageKey to set
	 */
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the simpleSend
	 */
	public boolean isSimpleSend() {
		return simpleSend;
	}

	/**
	 * @param simpleSend the simpleSend to set
	 */
	public void setSimpleSend(boolean simpleSend) {
		this.simpleSend = simpleSend;
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	

}
