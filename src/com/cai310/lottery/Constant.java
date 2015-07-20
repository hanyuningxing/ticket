package com.cai310.lottery;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.cai310.lottery.common.CostMinUnits;

public final class Constant {
	public static final String  ENROLLPOINT = "2";

	/** 外部系统支付宝Id */
	public static final String EXOSYSTEM_ID = "100002";
	
	/** 外部系统支付宝名称  */
	public static final String EXOSYSTEMNAME = "支付宝";
	
	/** 外部系统银联DNAId */
	public static final String EXOSYSTEM_EASYLINK_ID = "100001";
	
	/** 外部系统银联DNA名称  */
	public static final String EXOSYSTEM_EASYLINK_NAME = "银联DNA";
	
	
	/** 彩票系统数据表名称前缀 */
	public static final String LOTTERY_TABLE_PREFIX = "LOTTERY_";

	/** 用于生成主键的数据表的名称 */
	public static final String GENERATOR_TABLE = LOTTERY_TABLE_PREFIX+"ID_GENERATOR";

	public static final NumberFormat MONEY_FORMAT = new DecimalFormat("0.0000");

	/** 发起人至少认购百分比 */
	public static final float SPONSOR_MIN_SUBSCRIPTION_PERCENT = 0.05f;

	/** 佣金率的上限 */
	public static final float COMMISSION_MAX_RATE = 0.05f;

	/** 认购或保底金额的最小单位 */
	public static final CostMinUnits COST_MIN_UNITS = CostMinUnits.YUAN;

	/** 追号期数上限 */
	public static final int CHASE_MAX_PERIOD_SIZE = 1000;

	/** 倍数上限 */
	public static final int MAX_MULTIPLE = 1000;

	/** 注数上限 */
	public static final int MAX_UNITS = 10000;

	/** 单式注数上限 */
	public static final int MAX_SINGLE_UNITS = 1000000;

	/** 网站保底的用户编号 */
	public static final long SITE_BAODI_USER_ID = 1;//

	/** 不能自行撤单的最小百分比 --大于50%的单不能自行撤 */
	public static final int MIN_PROGRESS_RATE = 50;

	public static final byte ORDER_PRIORITY_DEFAULT = 50;
	public static final byte ORDER_PRIORITY_TOP = 100;
	public static final byte ORDER_PRIORITY_BOTTOM = 1;

	/** 网站保底率*/
	public static float SITE_BAODI_RATE = 0.1f;

	/** 默认的枚举类型的SQL Type */
	public static final String ENUM_DEFAULT_SQL_TYPE = "" + Types.TINYINT;

	/**过关统计 缓存key 彩种_彩期_PASSCOUNT' */
	public static final String PASSCOUNT_KEY = "{Lottery}_{PeriodNumber}_PASSCOUNT";

	public static final String WON_LINE_TMPLATE = "{PRIZENAME}：{WINUNITS}注;";
	public static final String PRIZE_LINE_TMPLATE = "<li>{PRIZENAME}：{WINUNITS}注 单注奖金：{UNIT_PRIZE}元 单注税后：{UNIT_PRIZE_TAXED}元</li>";
	public static final String PRIZE_FOOTER_TMPLATE = "<BR><BR>总奖金：{TOTAL_PRIZE}元<BR>税后奖金：{AFTER_TAX_PRIZE}元<BR>";
	public static final String PRIZE_CUT_TEMPLATE = "<BR><STRONG>奖金分配：</STRONG><BR>合买方案发起人佣金（{ORGANIGER_DEDUCT_RATE}）：{ORGANIGER_DEDUCT}元<BR>剩余用户分配奖金:{TOTAL_RETURN}元<BR>";

	public static final NumberFormat numFmt = new DecimalFormat("0.00");
	public static final MathContext prizeMathContext = new MathContext(10);
	public static final MathContext priceMathContext = new MathContext(10);

	public static final String USER_PWD_SALT = "G2dU#3q&6jW";
	public static final String LOGIN_CAPTCHA = "LOGIN_CAPTCHA";

	public static final String BUNDLE_KEY = "ApplicationResources";

	/** 开始收税的最小奖金 */
	public static final double TAX_MIN_PRIZE = 10000.0d;

	/** 税率 */
	public static final double TAX_RATE = 0.2d;
	
	/** 提现金额控制率  充值金额不到消费的DRAWING_COST_CONTROLLER%,不能提现 */
	public static final double DRAWING_COST_CONTROLLER= 0.6d;

	/** 数字彩单式：数据库号码之间的分隔符 */
	public static final String SINGLE_SEPARATOR_FOR_NUMBER = " ";
	
	
	/** 数字彩单式：拆票号码之间的分隔符 */
	public static final String TICKET_SINGLE_SEPARATOR_FOR_NUMBER = " ";
	
	/** 数字彩单式：拆票注数之间的分隔符 */
	public static final String TICKET_SINGLE_SEPARATOR_FOR_UNIT = ",";

	/** 开奖号码之间的分隔符 */
	public static final String RESULT_SEPARATOR_FOR_NUMBER = ",";
	/** 每个方案复式显示内容的分隔的HTML */
	public static final String SCHEME_SEPARATOR_HTML = "<br/>";
	
	public static final String DEFAULT_ENCODE = "UTF-8";

	/** 系统路径,监听器设值 **/
	public static String ROOTPATH = "";

	/** 系统上下文 **/
	public static String BASEPATH;

	/** 高频彩停止追号锁定 **/
	public static String KENOSTOPCHASE = "KENOSTOPCHASE";

	/** 停止追号锁定 **/
	public static String STOPCHASE = "STOPCHASE";

	/** Ips锁定 **/
	public static String IPSPASS = "IPSPASS";
	
	public static String Epay = "Epay";

	/**易联支付锁定*/
	public static String EASYLINK = "EasyLink";
	
	/** 退款锁定 **/
	public static String DRAWING = "DRAWING";
	
	
	/** 外部接票锁定 **/
	public static String THENLOCK = "THENLOCK";
	
	
	/** 退款锁定 **/
	public static String OPRMONEY = "OPRMONEY";
	
	public static String WELLETIDRECORDKEY = "walletierecord.walletierecordno";
	
	public static String COMMTRACE = "commtrace.commtraceno";
	
	public static String  CUSTOMORDERNO  = "comm.ali.commtrace.customorderno";
	
	public static String  OUTWARDTRACE = "comm.el.commtrace.outwardtrace";
	
	public static String USERID = "euser.id";
	
	public static String ACCOUTID = "walletsubaccount.id";
	
	public static String VISITLOGID = "visitlog.id";
	
	public static final String MESSAGECOADID = "messagecoad.id";
	
	/** 频道公告持久化缓存key **/
	public static final String channelNoticeNewsCacheKey = "Channel.Notice.News.Cache";
	/** 短信通知持久化缓存key **/
	public static final String messageNotice = "Message.Notice";
	
	
	public static final BigDecimal FiveHundreds  = BigDecimal.valueOf(500);
	public static final BigDecimal  OneThousands  = BigDecimal.valueOf(1000);
	public static final BigDecimal  TwoThousands  = BigDecimal.valueOf(2000);
	public static final BigDecimal  OneMillion  = BigDecimal.valueOf(10000);//1万
	public static final BigDecimal  FiveMillion  = BigDecimal.valueOf(50000);//5万
	public static final BigDecimal  FiftyMillion  = BigDecimal.valueOf(500000);//50万
	
	
	/**
	 * 计算税后奖金
	 * 
	 * @param prize 税前奖金
	 * @return 税后奖金
	 */
	public static double countPrizeAfterTax(double prize) {
		if (prize >= TAX_MIN_PRIZE)
			return prize * (1 - TAX_RATE);
		else
			return prize;
	}

	public static String getOrderPriorityText(byte orderPriority) {
		switch (orderPriority) {
		case Constant.ORDER_PRIORITY_BOTTOM:
			return "置底";
		case Constant.ORDER_PRIORITY_TOP:
			return "置顶";
		case Constant.ORDER_PRIORITY_DEFAULT:
			return "默认";
		default:
			return "未知类型";
		}
	}
}
