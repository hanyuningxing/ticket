package com.cai310.lottery.support.jczq;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang3.time.DateUtils;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.support.Item;
import com.cai310.utils.DateUtil;

public class JczqMatch extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = -7664713483528947069L;
 
	public static final String TABLE_NAME = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "JCZQ_MATCH";

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;

	/** 版本号,用于实现乐观锁 */
	private Integer version;

	/** 期编号 */
	private Long periodId;

	/** 期号 */
	private String periodNumber;

	/** 场次标识 */
	private String matchKey;

	/** 场次归属的日期 */
	private Integer matchDate;

	/** 一天里场次的序号 */
	private Integer lineId;

	/** 联赛名称 */
	private String gameName;

	/** 联赛颜色 */
	private String gameColor;

	/** 比赛时间 */
	private Date matchTime;

	/** 比赛是否已经结束 */
	private boolean ended;

	/** 比赛是否取消 */
	private boolean cancel;

	/** 主队名称 */
	private String homeTeamName;

	/** 客队名称 */
	private String guestTeamName;

	/** 主队粤语名称 */
	private String homeTeamGuangdongName;

	/** 客队粤语名称 */
	private String guestTeamGuangdongName;

	/** 让球 */
	private Byte handicap;

	/** 主队半场进球数 */
	private Integer halfHomeScore;

	/** 客队半场进球数 */
	private Integer halfGuestScore;

	/** 主队全场进球数 */
	private Integer fullHomeScore;

	/** 客队全场进球数 */
	private Integer fullGuestScore;

	/** 胜平负 */
	private Double spfResultSp;
	private Double jqsResultSp;
	private Double bfResultSp;
	private Double bqqResultSp;

	/**
	 * 表示场次对哪些玩法和哪些过关类型才开发
	 * <p>
	 * 以二进制位表示，一种玩法占用两位，其中一位表示单关，一位表示过关
	 * </p>
	 */
	private Integer openFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the {@link #version}
	 */
	@Version
	@Column(nullable = false)
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the {@link #version} to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return the {@link #matchKey}
	 */
	@Column(nullable = false, length = 12)
	public String getMatchKey() {
		return matchKey;
	}

	/**
	 * @param matchKey the {@link #matchKey} to set
	 */
	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}

	/**
	 * @return the {@link #matchDate}
	 */
	@Column(nullable = false)
	public Integer getMatchDate() {
		return matchDate;
	}

	/**
	 * @param matchDate the {@link #matchDate} to set
	 */
	public void setMatchDate(Integer matchDate) {
		this.matchDate = matchDate;
	}

	/**
	 * @return the {@link #lineId}
	 */
	@Column(nullable = false)
	public Integer getLineId() {
		return lineId;
	}

	/**
	 * @param lineId the {@link #lineId} to set
	 */
	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	/**
	 * @return {@link #periodId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getPeriodId() {
		return periodId;
	}

	/**
	 * @param periodId the {@link #periodId} to set
	 */
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	/**
	 * @return {@link #periodNumber}
	 */
	@Column(nullable = false, updatable = false, length = 20)
	public String getPeriodNumber() {
		return periodNumber;
	}

	/**
	 * @param periodNumber the {@link #periodNumber} to set
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	/**
	 * @return {@link #gameName}
	 */
	@Column(length = 20)
	public String getGameName() {
		return gameName;
	}

	/**
	 * @param gameName the {@link #gameName} to set
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	/**
	 * @return {@link #gameColor}
	 */
	@Column(length = 7)
	public String getGameColor() {
		return gameColor;
	}

	/**
	 * @param gameColor the {@link #gameColor} to set
	 */
	public void setGameColor(String gameColor) {
		this.gameColor = gameColor;
	}

	/**
	 * @return {@link #matchTime}
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getMatchTime() {
		return matchTime;
	}

	/**
	 * @param matchTime the {@link #matchTime} to set
	 */
	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	/**
	 * @return {@link #ended}
	 */
	@Column(nullable = false)
	public boolean isEnded() {
		return ended;
	}

	/**
	 * @param ended the {@link #ended} to set
	 */
	public void setEnded(boolean ended) {
		this.ended = ended;
	}

	/**
	 * @return {@link #cancel}
	 */
	@Column(nullable = false)
	public boolean isCancel() {
		return cancel;
	}

	/**
	 * @param cancel the {@link #cancel} to set
	 */
	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	/**
	 * @return {@link #homeTeamName}
	 */
	@Column(nullable = false, length = 20)
	public String getHomeTeamName() {
		return homeTeamName;
	}

	/**
	 * @param homeTeamName the {@link #homeTeamName} to set
	 */
	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	/**
	 * @return {@link #guestTeamName}
	 */
	@Column(nullable = false, length = 20)
	public String getGuestTeamName() {
		return guestTeamName;
	}

	/**
	 * @param guestTeamName the {@link #guestTeamName} to set
	 */
	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}

	/**
	 * @return {@link #homeTeamGuangdongName}
	 */
	@Column(length = 20)
	public String getHomeTeamGuangdongName() {
		return homeTeamGuangdongName;
	}

	/**
	 * @param homeTeamGuangdongName the {@link #homeTeamGuangdongName} to set
	 */
	public void setHomeTeamGuangdongName(String homeTeamGuangdongName) {
		this.homeTeamGuangdongName = homeTeamGuangdongName;
	}

	/**
	 * @return {@link #guestTeamGuangdongName}
	 */
	@Column(length = 20)
	public String getGuestTeamGuangdongName() {
		return guestTeamGuangdongName;
	}

	/**
	 * @param guestTeamGuangdongName the {@link #guestTeamGuangdongName} to set
	 */
	public void setGuestTeamGuangdongName(String guestTeamGuangdongName) {
		this.guestTeamGuangdongName = guestTeamGuangdongName;
	}

	/**
	 * @return {@link #halfHomeScore}
	 */
	@Column
	public Integer getHalfHomeScore() {
		return halfHomeScore;
	}

	/**
	 * @param halfHomeScore the {@link #halfHomeScore} to set
	 */
	public void setHalfHomeScore(Integer halfHomeScore) {
		this.halfHomeScore = halfHomeScore;
	}

	/**
	 * @return {@link #halfGuestScore}
	 */
	@Column
	public Integer getHalfGuestScore() {
		return halfGuestScore;
	}

	/**
	 * @param halfGuestScore the {@link #halfGuestScore} to set
	 */
	public void setHalfGuestScore(Integer halfGuestScore) {
		this.halfGuestScore = halfGuestScore;
	}

	/**
	 * @return {@link #fullHomeScore}
	 */
	@Column
	public Integer getFullHomeScore() {
		return fullHomeScore;
	}

	/**
	 * @param fullHomeScore the {@link #fullHomeScore} to set
	 */
	public void setFullHomeScore(Integer fullHomeScore) {
		this.fullHomeScore = fullHomeScore;
	}

	/**
	 * @return {@link #fullGuestScore}
	 */
	@Column
	public Integer getFullGuestScore() {
		return fullGuestScore;
	}

	/**
	 * @param fullGuestScore the {@link #fullGuestScore} to set
	 */
	public void setFullGuestScore(Integer fullGuestScore) {
		this.fullGuestScore = fullGuestScore;
	}

	/**
	 * @return {@link #handicap}
	 */
	@Column
	public Byte getHandicap() {
		return handicap;
	}

	/**
	 * @param handicap the {@link #handicap} to set
	 */
	public void setHandicap(Byte handicap) {
		this.handicap = handicap;
	}

	/**
	 * @return the {@link #openFlag}
	 */
	@Column(nullable = false)
	public Integer getOpenFlag() {
		return openFlag;
	}

	/**
	 * @param openFlag the {@link #openFlag} to set
	 */
	public void setOpenFlag(Integer openFlag) {
		this.openFlag = openFlag;
	}

	@Column
	public Double getSpfResultSp() {
		return spfResultSp;
	}

	public void setSpfResultSp(Double spfResultSp) {
		this.spfResultSp = spfResultSp;
	}

	@Column
	public Double getJqsResultSp() {
		return jqsResultSp;
	}

	public void setJqsResultSp(Double jqsResultSp) {
		this.jqsResultSp = jqsResultSp;
	}

	@Column
	public Double getBfResultSp() {
		return bfResultSp;
	}

	public void setBfResultSp(Double bfResultSp) {
		this.bfResultSp = bfResultSp;
	}

	@Column
	public Double getBqqResultSp() {
		return bqqResultSp;
	}

	public void setBqqResultSp(Double bqqResultSp) {
		this.bqqResultSp = bqqResultSp;
	}

	@Transient
	public String getMatchKeyText() {
		if (this.matchDate != null && this.lineId != null)
			return JczqUtil.getMatchKeyText(matchDate, lineId);
		else
			return null;
	}

	@Transient
	public boolean isOpen(PlayType playType, PassMode passMode) {
		if (this.getOpenFlag() == null)
			return false;
		int v = JczqUtil.getOpenFlag(playType, passMode);
		return (this.getOpenFlag() & v) > 0;
	}
	
	@Transient
	public boolean isNotDisplay(PlayType playType, PassMode passMode) {
		if (!isOpen(playType, passMode)||isStop()||this.cancel) {
			return true;
		}
		return false;
	}
	
	@Transient
	public boolean isStop() {
		if(null==this.getMatchDate())return true;
		boolean isSaleEnded = this.isEnded() || this.isCancel();
		if(isSaleEnded){
			return true;
		}
		Date endStarDate = null;///截至时间 
		Date endEndDate = null;///开售时间（截至后）
		Date weekDate = DateUtil.strToDate(""+this.getMatchDate(),"yyyyMMdd");
		int week = JczqUtil.getMatchDayOfWeek(this.getMatchDate());
		////取得星期
		if(week==6||week==7){
			//周末
			weekDate = DateUtils.addDays(weekDate, 1);///把截至时间调整到次日
			endStarDate = DateUtil.strToDate(DateUtil.dateToStr(weekDate,"yyyy-MM-dd")+" "+JczqConstant.WEEKEND_EDN_TIME);
			endEndDate = DateUtil.strToDate(DateUtil.dateToStr(weekDate,"yyyy-MM-dd")+" 10:00");
		}else{
			endStarDate = DateUtil.strToDate(DateUtil.dateToStr(weekDate,"yyyy-MM-dd")+" "+JczqConstant.UNWEEKEND_EDN_TIME);
			weekDate = DateUtils.addDays(weekDate, 1);///把截至开始时间调整到次日
			endEndDate = DateUtil.strToDate(DateUtil.dateToStr(weekDate,"yyyy-MM-dd")+" 10:00");
		}
		Date now = new Date();
		if(now.after(endStarDate)&&now.before(endEndDate)){
			////如果现在是截至时间
			if(this.getMatchTime().after(endStarDate)&&this.getMatchTime().before(endEndDate)){
				////把在这个时间段的比赛截至--因为查询的时候有查询提前时间，所以这里无需加入提前时间。
				return true;
			}
		}
		if(now.after(this.getOfficialEndTime()))return true;
		return false;
		
	}
	@Transient
	public Date getOfficialEndTime() {
		if(null==this.getMatchDate())return null;
		if(null==this.getMatchTime())return null;
		Date endStarDate = null;///截至时间 
		Date endEndDate = null;///开售时间（截至后）
		Date weekDate = DateUtil.strToDate(""+this.getMatchDate(),"yyyyMMdd");
		int week = JczqUtil.getMatchDayOfWeek(this.getMatchDate());
		////取得星期
		if(week==6||week==7){
			//周末
			weekDate = DateUtils.addDays(weekDate, 1);///把截至时间调整到次日
			endStarDate = DateUtil.strToDate(DateUtil.dateToStr(weekDate,"yyyy-MM-dd")+" "+JczqConstant.WEEKEND_EDN_TIME);
			endEndDate = DateUtil.strToDate(DateUtil.dateToStr(weekDate,"yyyy-MM-dd")+" 10:00");
		}else{
			endStarDate = DateUtil.strToDate(DateUtil.dateToStr(weekDate,"yyyy-MM-dd")+" "+JczqConstant.UNWEEKEND_EDN_TIME);
			weekDate = DateUtils.addDays(weekDate, 1);///把截至开始时间调整到次日
			endEndDate = DateUtil.strToDate(DateUtil.dateToStr(weekDate,"yyyy-MM-dd")+" 10:00");
		}
		if(this.getMatchTime().after(endStarDate)&&this.getMatchTime().before(endEndDate)){
			////把在这个时间段的比赛截至--因为查询的时候有查询提前时间，所以这里无需加入提前时间。
			return endStarDate;
		}
		return this.getMatchTime();
		
	}
	@Transient
	public boolean isSaleEnded(int aheadEnd) {
		boolean isSaleEnded = this.isEnded() || this.isCancel();
		if (!isSaleEnded && this.getMatchTime() != null) {
			return System.currentTimeMillis() + aheadEnd * 60 * 1000 > this.getMatchTime().getTime();
		} else {
			return true;
		}
	}

	@Transient
	public Double getResultSp(PlayType playType) {
		if (this.isCancel())
			return 1.0;
		if (!this.isEnded())
			return null;

		switch (playType) {
		case SPF:
			return getSpfResultSp();
		case JQS:
			return getJqsResultSp();
		case BF:
			return getBfResultSp();
		case BQQ:
			return getBqqResultSp();
		default:
			throw new RuntimeException("玩法不正确.");
		}
	}

	@Transient
	public Item getResult(PlayType playType) {
		switch (playType) {
		case SPF:
			return getSpfResult();
		case JQS:
			return getJqsResult();
		case BF:
			return getBfResult();
		case BQQ:
			return getBqqResult();
		default:
			throw new RuntimeException("玩法不正确.");
		}
	}

	@Transient
	public ItemSPF getSpfResult() {
		if (this.isCancel())
			return null;
		if (!this.isEnded())
			return null;
		if (this.getFullHomeScore() == null || this.getFullGuestScore() == null)
			return null;

		int handicap = (null == getHandicap()) ? 0 : getHandicap();
		int fullGuestScoreOfHandicap = getFullGuestScore() - handicap;
		if (getFullHomeScore() > fullGuestScoreOfHandicap)
			return ItemSPF.WIN;
		else if (getFullHomeScore().equals(fullGuestScoreOfHandicap))
			return ItemSPF.DRAW;
		else
			return ItemSPF.LOSE;
	}

	@Transient
	public ItemJQS getJqsResult() {
		if (this.isCancel())
			return null;
		if (!this.isEnded())
			return null;
		if (this.getFullHomeScore() == null || this.getFullGuestScore() == null)
			return null;

		int totalScore = this.getFullHomeScore() + this.getFullGuestScore();
		if (totalScore < 0)
			throw new RuntimeException("数据异常:总进球数小于零");

		switch (totalScore) {
		case 0:
			return ItemJQS.S0;
		case 1:
			return ItemJQS.S1;
		case 2:
			return ItemJQS.S2;
		case 3:
			return ItemJQS.S3;
		case 4:
			return ItemJQS.S4;
		case 5:
			return ItemJQS.S5;
		case 6:
			return ItemJQS.S6;
		case 7:
			return ItemJQS.S7;
		default:
			return ItemJQS.S7;
		}
	}

	@Transient
	public ItemBF getBfResult() {
		if (this.isCancel())
			return null;
		if (!this.isEnded())
			return null;
		if (this.getFullHomeScore() == null || this.getFullGuestScore() == null)
			return null;

		if (getFullHomeScore() == 1 && getFullGuestScore() == 0) {
			return ItemBF.WIN10;
		} else if (getFullHomeScore() == 2 && getFullGuestScore() == 0) {
			return ItemBF.WIN20;
		} else if (getFullHomeScore() == 2 && getFullGuestScore() == 1) {
			return ItemBF.WIN21;
		} else if (getFullHomeScore() == 3 && getFullGuestScore() == 0) {
			return ItemBF.WIN30;
		} else if (getFullHomeScore() == 3 && getFullGuestScore() == 1) {
			return ItemBF.WIN31;
		} else if (getFullHomeScore() == 3 && getFullGuestScore() == 2) {
			return ItemBF.WIN32;
		} else if (getFullHomeScore() == 4 && getFullGuestScore() == 0) {
			return ItemBF.WIN40;
		} else if (getFullHomeScore() == 4 && getFullGuestScore() == 1) {
			return ItemBF.WIN41;
		} else if (getFullHomeScore() == 4 && getFullGuestScore() == 2) {
			return ItemBF.WIN42;
		} else if (getFullHomeScore() == 5 && getFullGuestScore() == 0) {
			return ItemBF.WIN50;
		} else if (getFullHomeScore() == 5 && getFullGuestScore() == 1) {
			return ItemBF.WIN51;
		} else if (getFullHomeScore() == 5 && getFullGuestScore() == 2) {
			return ItemBF.WIN52;
		} else if (getFullHomeScore() > getFullGuestScore()) {
			return ItemBF.WIN_OTHER;
		} else if (getFullHomeScore() == 0 && getFullGuestScore() == 0) {
			return ItemBF.DRAW00;
		} else if (getFullHomeScore() == 1 && getFullGuestScore() == 1) {
			return ItemBF.DRAW11;
		} else if (getFullHomeScore() == 2 && getFullGuestScore() == 2) {
			return ItemBF.DRAW22;
		} else if (getFullHomeScore() == 3 && getFullGuestScore() == 3) {
			return ItemBF.DRAW33;
		} else if (getFullHomeScore().equals(getFullGuestScore())) {
			return ItemBF.DRAW_OTHER;
		} else if (getFullHomeScore() == 0 && getFullGuestScore() == 1) {
			return ItemBF.LOSE01;
		} else if (getFullHomeScore() == 0 && getFullGuestScore() == 2) {
			return ItemBF.LOSE02;
		} else if (getFullHomeScore() == 0 && getFullGuestScore() == 3) {
			return ItemBF.LOSE03;
		} else if (getFullHomeScore() == 1 && getFullGuestScore() == 2) {
			return ItemBF.LOSE12;
		} else if (getFullHomeScore() == 1 && getFullGuestScore() == 3) {
			return ItemBF.LOSE13;
		} else if (getFullHomeScore() == 2 && getFullGuestScore() == 3) {
			return ItemBF.LOSE23;
		} else if (getFullHomeScore() == 0 && getFullGuestScore() == 4) {
			return ItemBF.LOSE04;
		} else if (getFullHomeScore() == 1 && getFullGuestScore() == 4) {
			return ItemBF.LOSE14;
		} else if (getFullHomeScore() == 2 && getFullGuestScore() == 4) {
			return ItemBF.LOSE24;
		} else if (getFullHomeScore() == 0 && getFullGuestScore() == 5) {
			return ItemBF.LOSE05;
		} else if (getFullHomeScore() == 1 && getFullGuestScore() == 5) {
			return ItemBF.LOSE15;
		} else if (getFullHomeScore() == 2 && getFullGuestScore() == 5) {
			return ItemBF.LOSE25;
		} else if (getFullHomeScore() < getFullGuestScore()) {
			return ItemBF.LOSE_OTHER;
		} else {
			throw new RuntimeException("数据异常.");
		}
	}

	@Transient
	public ItemBQQ getBqqResult() {
		if (this.isCancel())
			return null;
		if (!this.isEnded())
			return null;
		if (this.getFullHomeScore() == null || this.getFullGuestScore() == null)
			return null;
		if (this.getHalfHomeScore() == null || this.getHalfGuestScore() == null)
			return null;

		if (getHalfHomeScore() > getHalfGuestScore()) {
			if (getFullHomeScore() > getFullGuestScore())
				return ItemBQQ.WIN_WIN; // 胜胜
			if (getFullHomeScore().equals(getFullGuestScore()))
				return ItemBQQ.WIN_DRAW; // 胜平
			else
				return ItemBQQ.WIN_LOSE; // 胜负
		} else if (getHalfHomeScore().equals(getHalfGuestScore())) {
			if (getFullHomeScore() > getFullGuestScore())
				return ItemBQQ.DRAW_WIN; // 平胜
			if (getFullHomeScore().equals(getFullGuestScore()))
				return ItemBQQ.DRAW_DRAW; // 平平
			else
				return ItemBQQ.DRAW_LOSE; // 平负
		} else {
			if (getFullHomeScore() > getFullGuestScore())
				return ItemBQQ.LOSE_WIN; // 负胜
			if (getFullHomeScore().equals(getFullGuestScore()))
				return ItemBQQ.LOSE_DRAW; // 负平
			else
				return ItemBQQ.LOSE_LOSE; // 负负
		}
	}
}
