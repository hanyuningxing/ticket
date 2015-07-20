package com.cai310.lottery.support.jczq;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cai310.lottery.common.SchemePrintState;


public class JczqContentBean{
	private Integer index;
	private Integer cost;
	private Integer units;
	/** 倍数 */
	private Integer multiple;
	private String schemeNum;
	/** 方案出票状态 */
	private SchemePrintState schemePrintState;
	private String content;
	/** 投注内容 */
	private List<JczqMatchItem> correctList;

	/** 过关方式 */
	private List<PassType> passTypes;

	/** 方案类型 */
	private SchemeType schemeType;
	
    private PlayType playType;
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public List<JczqMatchItem> getCorrectList() {
		return correctList;
	}

	public void setCorrectList(List<JczqMatchItem> correctList) {
		this.correctList = correctList;
	}

	public List<PassType> getPassTypes() {
		return passTypes;
	}

	public void setPassTypes(List<PassType> passTypes) {
		this.passTypes = passTypes;
	}

	public SchemeType getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(SchemeType schemeType) {
		this.schemeType = schemeType;
	}

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getSchemeNum() {
		return schemeNum;
	}

	public void setSchemeNum(String schemeNum) {
		this.schemeNum = schemeNum;
	}

	public SchemePrintState getSchemePrintState() {
		return schemePrintState;
	}

	public void setSchemePrintState(SchemePrintState schemePrintState) {
		this.schemePrintState = schemePrintState;
	}
}
