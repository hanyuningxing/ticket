package com.cai310.lottery.support.jczq;

import java.io.Serializable;
import java.util.List;

import com.cai310.lottery.utils.StringUtil;

/**
 * 竞猜足球单式内容Bean
 * 
 */
public class JczqSingleContent implements Serializable {
	private static final long serialVersionUID = -6747955534455939909L;

	/** 选择的场次 */
	private List<String> matchKeys;
	
	/** 选择的场次内容 */
	private List<JczqMatchItem> items;

	/** 投注内容 (\r\n)分隔*/
	private String content;
	
	/** 倍投与content相对应，优化方案才有(倍数的调整，否则就是方案中的倍投)*/
	private List<Integer> multiples;
	
	/** 是否为优化方案*/
	private boolean optimize;
	
	/** 优化方案混合过关玩法，与场次对应*/
	private List<String> playTypes;
	
	/** 最小奖金预测*/
	private Double bestMinPrize;
	
	/** 最大奖金预测*/
	private Double bestMaxPrize;

	/**
	 * @return {@link #matchKeys}
	 */
	public List<String> getMatchkeys() {
		return matchKeys;
	}

	/**
	 * @param matchKeys the {@link #matchKeys} to set
	 */
	public void setMatchkeys(List<String> matchKeys) {
		this.matchKeys = matchKeys;
	}
	
	

	public List<JczqMatchItem> getItems() {
		return items;
	}

	public void setItems(List<JczqMatchItem> items) {
		this.items = items;
	}

	/**
	 * @return {@link #content}
	 */
	public String getContent() {
		return content;
	}
	
	public String[] converContent2Arr(){
		if(StringUtil.isEmpty(this.content)){
			return null;
		}else{
			return this.getContent().split("\r\n");
		}
	}

	/**
	 * @param content the {@link #content} to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public List<Integer> getMultiples() {
		return multiples;
	}

	public void setMultiples(List<Integer> multiples) {
		this.multiples = multiples;
	}

	public boolean isOptimize() {
		return optimize;
	}

	public void setOptimize(boolean optimize) {
		this.optimize = optimize;
	}

	public List<String> getPlayTypes() {
		return playTypes;
	}

	public void setPlayTypes(List<String> playTypes) {
		this.playTypes = playTypes;
	}

	public Double getBestMinPrize() {
		return bestMinPrize;
	}

	public void setBestMinPrize(Double bestMinPrize) {
		this.bestMinPrize = bestMinPrize;
	}

	public Double getBestMaxPrize() {
		return bestMaxPrize;
	}

	public void setBestMaxPrize(Double bestMaxPrize) {
		this.bestMaxPrize = bestMaxPrize;
	}
	
}
