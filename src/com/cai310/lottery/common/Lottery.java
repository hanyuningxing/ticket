package com.cai310.lottery.common;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.AhKuai3Constant;
import com.cai310.lottery.Constant;
import com.cai310.lottery.DczcConstant;
import com.cai310.lottery.DltConstant;
import com.cai310.lottery.El11to5Constant;
import com.cai310.lottery.GdEl11to5Constant;
import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.KlpkConstant;
import com.cai310.lottery.KlsfConstant;
import com.cai310.lottery.LczcConstant;
import com.cai310.lottery.PlConstant;
import com.cai310.lottery.QyhConstant;
import com.cai310.lottery.SczcConstant;
import com.cai310.lottery.SdEl11to5Constant;
import com.cai310.lottery.SevenConstant;
import com.cai310.lottery.SevenstarConstant;
import com.cai310.lottery.SfzcConstant;
import com.cai310.lottery.SscConstant;
import com.cai310.lottery.SslConstant;
import com.cai310.lottery.SsqConstant;
import com.cai310.lottery.Tc22to5Constant;
import com.cai310.lottery.Welfare36to7Constant;
import com.cai310.lottery.Welfare3dConstant;
import com.cai310.lottery.XjEl11to5Constant;

/**
 * 彩票类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum Lottery {
	/*
	 * ----------------------- 彩票类型----------------------- 加彩票类型的时候，一定要往后面加
	 * 。不能中间插队
	 */
	/** 双色球 */
	SSQ(SsqConstant.KEY, "双色球", "SSQ", LotteryCategory.NUMBER),//0

	/** 快乐十分 */
	KLSF(KlsfConstant.KEY, "快乐十分", "KLS", LotteryCategory.FREQUENT),//1

	/** 11选5 */
	EL11TO5(El11to5Constant.KEY, "11选5", "ELT", LotteryCategory.FREQUENT),//2

	/** 时时彩 */
	SSC(SscConstant.KEY, "时时彩", "SSC", LotteryCategory.FREQUENT),//3

	/** 时时乐 */
	SSL(SslConstant.KEY, "时时乐", "SSL", LotteryCategory.FREQUENT),//4

	/** 3D */
	WELFARE3D(Welfare3dConstant.KEY, "福彩3D", "WSD", LotteryCategory.NUMBER),//5

	/** PL */
	PL(PlConstant.KEY, "排列3/5", "TPL", LotteryCategory.NUMBER),//6
	
	

	/** DCZC */
	DCZC(DczcConstant.KEY, "北京单场", "BDC", LotteryCategory.DCZC),//7

	
	SEVEN(SevenConstant.KEY, "七乐彩", "QLC", LotteryCategory.NUMBER),//8

	/** 胜负足彩-- 14场、任选九场（playType） */
	SFZC(SfzcConstant.KEY, "胜负彩", "SZC", LotteryCategory.ZC),//9

	/** 六场半全场 */
	LCZC(LczcConstant.KEY, "六场半全场", "BCB", LotteryCategory.ZC),//10

	/** 四场进球 */
	SCZC(SczcConstant.KEY, "进球彩 ", "JQC", LotteryCategory.ZC),//11

	/** 36选7 */
	WELFARE36To7(Welfare36to7Constant.KEY, "好彩1", "WFC", LotteryCategory.NUMBER),//12

	/** 超级大乐透 */
	DLT(DltConstant.KEY, "大乐透", "DLT", LotteryCategory.NUMBER),//13

	/** 山东11选5 */
	SDEL11TO5(SdEl11to5Constant.KEY, "山东11选5", "SDELT", LotteryCategory.FREQUENT),//14

	/** 山东群英会 */
	QYH(QyhConstant.KEY, "群英会", "QYH", LotteryCategory.FREQUENT),//15
	
	/** 体彩22选5 */
	TC22TO5(Tc22to5Constant.KEY, "22选5", "EEXW", LotteryCategory.NUMBER),//16
	
	/** 竞彩足球 */
	JCZQ(JczqConstant.KEY, "竞彩足球", "JCZ", LotteryCategory.JC),//17

	/** 竞彩篮球 */
	JCLQ(JclqConstant.KEY, "竞彩篮球", "JCL", LotteryCategory.JC),//18
	
	GDEL11TO5(GdEl11to5Constant.KEY, "广东11选5", "GDELT", LotteryCategory.FREQUENT),//19
	
	SEVENSTAR(SevenstarConstant.KEY, "七星彩", "QXC", LotteryCategory.NUMBER),//20

	KLPK(KlpkConstant.KEY ,"快乐扑克3", "KLPK", LotteryCategory.FREQUENT),//21
	
	AHKUAI3(AhKuai3Constant.KEY,"安徽快3","AHK3",LotteryCategory.FREQUENT),//22

	XJEL11TO5(XjEl11to5Constant.KEY,"新疆11选5","XJELT",LotteryCategory.FREQUENT);//23
	/* -----------------------添加一个彩种要在LotteryUtil.getWebLotteryList加上----------------------- */
	
	/* ----------------------- 属性----------------------- */

	private final String key;

	/** 彩种简称 */
	private final String simpleName;
	/** 彩种名称 */
	private final String lotteryName;
	/** 彩种方案号前缀 */
	private final String schemeNumberPrefix;
	/**
	 * 彩种所属类别
	 * 
	 * @see com.cai310.lottery.common.LotteryCategory
	 */
	private final LotteryCategory category;

	private final String schemeNumberRegex;

	/* ----------------------- 构造函数 ----------------------- */

	/**
	 * @param key {@link #key}
	 * @param lotteryName {@link #lotteryName}
	 * @param schemeNumberPrefix {@link #schemeNumberPrefix}
	 * @param category {@link #category}
	 */
	private Lottery(String key, String lotteryName, String schemeNumberPrefix, LotteryCategory category) {
		this(key, lotteryName, lotteryName, schemeNumberPrefix, category);
	}

	/**
	 * @param key {@link #key}
	 * @param simpleName {@link #simpleName}
	 * @param lotteryName {@link #lotteryName}
	 * @param schemeNumberPrefix {@link #schemeNumberPrefix}
	 * @param category {@link #category}
	 */
	private Lottery(String key, String simpleName, String lotteryName, String schemeNumberPrefix,
			LotteryCategory category) {
		this.key = key;
		this.simpleName = simpleName;
		this.lotteryName = lotteryName;
		this.category = category;
		if (schemeNumberPrefix != null)
			this.schemeNumberPrefix = schemeNumberPrefix;
		else
			this.schemeNumberPrefix = this.name();
		this.schemeNumberRegex = String.format("^%s[0-9]{%s}$", this.schemeNumberPrefix, SCHEME_ID_FORMART.length());
	}

	/* ----------------------- getter method ----------------------- */

	/**
	 * @return {@link #key}
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 注释见{@link #simpleName}
	 */
	public String getSimpleName() {
		return simpleName;
	}

	/**
	 * @return {@link #lotteryName}
	 */
	public String getLotteryName() {
		return lotteryName;
	}

	/**
	 * @return {@link #schemeNumberPrefix}
	 */
	public String getSchemeNumberPrefix() {
		return schemeNumberPrefix;
	}

	/**
	 * @return {@link #category}
	 */
	public LotteryCategory getCategory() {
		return category;
	}

	/**
	 * 从方案号中获取方案ID
	 * 
	 * @param schemeNumber 方案号
	 * @return 方案ID
	 */
	public Long getSchemeId(String schemeNumber) {
		if (StringUtils.isBlank(schemeNumber))
			return null;
		if (schemeNumber.matches(this.schemeNumberRegex))
			return Long.valueOf(schemeNumber.replaceAll(this.schemeNumberPrefix, ""));
		return null;
	}

	/**
	 * 根据方案ID获取该彩种的方案号
	 * 
	 * @param schemeId 方案ID
	 * @return 方案号
	 */
	public String getSchemeNumber(Long schemeId) {
		return this.schemeNumberPrefix + SCHEME_ID_NF.format(schemeId);
	}

	/* ----------------------- other method ----------------------- */

	/**
	 * 检查彩种是否属于某一类型的彩票
	 * 
	 * @param categoryOrdinal 类型序号
	 * @return 是否属于某一类型的彩票
	 * @see com.cai310.lottery.common.LotteryCategory
	 */
	public boolean checkCategory(int categoryOrdinal) {
		return this.category.equals(LotteryCategory.values()[categoryOrdinal]);
	}

	/**
	 * 根据方案号前缀判断属于哪个彩票类型
	 * 
	 * @param prefix 方案号前缀
	 * @return 彩票类型
	 */
	public static Lottery valueOfPrefix(String prefix) {
		if (StringUtils.isNotBlank(prefix)) {
			prefix = prefix.trim();
			for (Lottery l : Lottery.values()) {
				if (l.getSchemeNumberPrefix().equalsIgnoreCase(prefix))
					return l;
			}
		}
		return null;
	}

	/**
	 * 根据方案号判断属于哪个彩票类型
	 * 
	 * @param schemeNumber 方案号
	 * @return 彩票类型
	 */
	public static Lottery valueOfSchemeNumber(String schemeNumber) {
		if (StringUtils.isNotBlank(schemeNumber)) {
			schemeNumber = schemeNumber.trim();
			for (Lottery l : Lottery.values()) {
				if (schemeNumber.matches(l.schemeNumberRegex))
					return l;
			}
		}
		return null;
	}

	/**
	 * 是否高频彩种
	 * 
	 * @param lottery
	 * @return
	 */
	public static boolean isKeno(Lottery lottery) {
		switch (lottery) {
		case EL11TO5:
		case SDEL11TO5:
		case QYH:
		case KLSF:
		case SSC:
		case SSL:
		case GDEL11TO5:
		case AHKUAI3:
		case KLPK:
		case XJEL11TO5:	
			return Boolean.TRUE;
		default:
			return Boolean.FALSE;
		}
	}

	private static final String SCHEME_ID_FORMART = "0000000000";
	private static final NumberFormat SCHEME_ID_NF = new DecimalFormat(SCHEME_ID_FORMART);
	
	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

}
