package com.cai310.lottery;

import java.io.File;

public class FetchConstant {
	//双色球 大乐透
	public static final String MATCH_PER = "<div class=\"an_percent\"><span class=\"(.*?)\" style=\"height:(.*?)%;\"></span></div>";
	public static final String MATCH_TOTAL ="<div class=\"total_num\"><span class=\"writing\">(\\d+?)</span></div><span class=\"(.*?)\">(\\d{2})</span><span class=\"curkj_.*?\"></span>";
	public static final String MATCH_CONDTION = "<input.*?name=\"RadioGroup1\".*?value=\"(\\d+)\".*?checked=\"checked\".*?/>.*?</label>.*?<option value=\"\\d+?\" selected=\"selected\">(\\d+?)</option>.*?排序按.*? value=\"(\\d+?)\" selected=\"selected\">(.*?)</option>";	
	
	//福彩3D 数字排列  (直选复式分析  按号码统计)
	public static final String FC3D_MATCH_CONDTION= "<input name=\"RadioGroup1\" .*? value=\"(\\d+)\" .*? checked=\"checked\"\\s?/>.*?</label>.*?<option value=\"\\d+?\" selected=\"selected\">(\\d+?)</option>.*?排序按.*?<option value=\"(\\d+?)\".*?selected=\"selected\">(.*?)</option>";
	//福彩3D 数字排列  (直选复式分析 按号码统计 )                                                                                      
	public static final String FC3D_MATCH_NUM = "<td>(\\d+?)</td>.*?<span class=\"yell_num\">(\\d+?)</span><span class=\"yell_num\">(\\d+?)</span><span class=\"yell_num\">(\\d+?)</span>.*?<span class=\"views\">(\\d+?)</span>.*? style=\"width:(.*?)%;\"></span>";
	//福彩3D 数字排列   (直选复式分析 按位置统计)                                                                                       
	public static final String FC3D_MATCH_P_CONDTION = "<td rowspan=\"2\" class=\"ws\">(.*?)</td><span class=\"blue_per\" style=\"height:(.*?)%;\"></span>.*?<div class=\"total_num\"><span class=\"writing\">(\\d+?)</span></div><span class=\"yell_num\">(\\d+?)</span>";
	public static final String FC3D_MATCH_P_CONDTION_HEIGHT="<div class=\"an_percent\"><span class=\"blue_per\" style=\"height:(.*?)%;\"></span></div>";
	public static final String FC3D_MATCH_P_CONDTION_NUM="<div class=\"total_num\"><span class=\"writing\">(\\d+?)</span></div><span class=\"yell_num\">(\\d+?)</span>";
	public static final String FC3D_MATCH_P_CONDTION_HEAD = "<input.*?name=\"RadioGroup1\".*?value=\"(\\d+)\".*?checked=\"checked\".*?/>.*?</label>.*?<option value=\"\\d+?\" selected=\"selected\">(\\d+?)</option>.*?排序按.*? value=\"(\\d+?)\".*?selected=\"selected\">(.*?)</option>"; 

	//福彩3D 数字排列  (组选复式分析  按单注号码)
	public static final String FC3D_MATCH_C_CONDTION = "<input.*?name=\"RadioGroup2\" value=\"(.*?)\".*?checked=\"checked\"/>.*?<input name=\"RadioGroup3\".*?value=\"(.*?)\" checked=\"checked\" />.*?<option value=\"(\\d+?)\" selected=\"selected\">\\d+?</option>.*?排序按.*?<option value=\"(\\d+?)\".*selected=\"selected\">.*?</option>";
	//福彩3D 数字排列  (组选复式分析   按单注号码)
	public static final String FC3D_MATCH_C_NUM = "<span class=\"yell_num\">(\\d+?)</span><span class=\"yell_num\">(\\d+?)</span><span class=\"yell_num\">(\\d+?)</span>.*?<span class=\"views\">(\\d+?)</span>.*? style=\"width:(.*?)%;\"></span>";
	
	//福彩3D 数字排列 (组选复式分析   按单个号码)
	public static final String FC3D_MATCH_C_SM_CONDTION = "<span class=\"yell_num\">(\\d+?)</span>.*?<span class=\"views\">(\\d+?)</span>.*? style=\"width:(.*?)%;\"></span>";
	//福彩3D 组选复式分析 按单个号码
	public static final String FC3D_MATCH_C_SM_HEAD="<input.*?name=\"RadioGroup2\" value=\"(.*?)\".*?checked=\"checked\"/>.*?<input.*?name=\"RadioGroup3\" value=\"(.*?)\".*?checked=\"checked\"/>.*?<option value=\"(\\d+?)\" selected=\"selected\">\\d+?</option>.*?<option value=\"(\\d+?)\" selected=\"selected\">.*?</option>";
	//数字排列  (直选复式分析  按号码统计)
	public static final String SZPL_MATCH_C_SM_CONDTION = "<input name=\"RadioGroup1\".*?value=\"(\\d+?)\".*?checked=\"checked\".*?/>.*?<option value=\"(\\d+?)\" selected=\"selected\">\\d+?</option>.*?排序按.*? <option value=\"(\\d+?)\".*?selected=\"selected\">.*?</option>";
	
	//双色球 当期投注号码的IP sort=1&expect=11004&fxtype=2
	public static final String SSQ_CUR_URL = "http://zx.500wan.com/ssq/fsfx.php";
	public static final String SSQ_CUR_SORT_XTYPE_1="http://zx.500wan.com/ssq/fsfx.php?sort=1&fxtype=2";
	public static final String SSQ_CUR_SORT_XTYPE_2="http://zx.500wan.com/ssq/fsfx.php?sort=1&fxtype=3";
	
	public static final String SSQ_CUR_PATH_1 =File.separator+"xml"+File.separator+"ssq"+File.separator+"hmtj"+File.separator;
	public static final String SSQ_CUR_PATH_2 = File.separator+"xml"+File.separator+"ssq"+File.separator+"dmtj"+File.separator;
	public static final String SSQ_CUR_PATH_3 = File.separator+"xml"+File.separator+"ssq"+File.separator+"shtj"+File.separator;
	//大乐透 当期投注号码的IP ?fxtype=2&expect=11031&sort=2
	public static final String DLT_CUR_URL = "http://zx.500wan.com/dlt/fsfx.php";
	public static final String DLT_CUR_SORT_XTYPE_1="http://zx.500wan.com/dlt/fsfx.php?sort=1&fxtype=2";
	
	public static final String DLT_CUR_PATH_1 = File.separator+"xml"+File.separator+"dlt"+File.separator+"hmtj"+File.separator;
	public static final String DLT_CUR_PATH_2 = File.separator+"xml"+File.separator+"dlt"+File.separator+"dmtj"+File.separator;

	//福彩3D 直选复式分析 按号码统计 http://zx.500wan.com/sd/fsfx.php?fxtype=1
	public static final String FC3D_NUM_URL = "http://zx.500wan.com/sd/fsfx.php?fxtype=1";
	public static final String FC3D_NUM_URL_1 = "http://zx.500wan.com/sd/fsfx.php";
	//福彩3D 直选复式分析 按位置统计
	public static final String FC3D_POS_URL = "http://zx.500wan.com/sd/fsfx.php?fxtype=2";
	public static final String FC3D_POS_URL_1 = "http://zx.500wan.com/sd/fsfx.php";
	//福彩3D 组选复式分析 按单注号码
	public static final String FC3D_GROUP_DZ_URL = "http://zx.500wan.com/sd/fsfx_zx.php?fxtype=2";
	public static final String FC3D_GROUP_DZ_URL_2 = "http://zx.500wan.com/sd/fsfx_zx.php?fxtype=2&zxtype=1";
	public static final String FC3D_GROUP_DZ_URL_3 = "http://zx.500wan.com/sd/fsfx_zx.php?fxtype=2&zxtype=2";
	
	public static final String FC3D_GROUP_DG_URL = "http://zx.500wan.com/sd/fsfx_zx.php?fxtype=1";
	public static final String FC3D_GROUP_DG_URL_2 = "http://zx.500wan.com/sd/fsfx_zx.php?fxtype=1&zxtype=1";
	public static final String FC3D_GROUP_DG_URL_3 = "http://zx.500wan.com/sd/fsfx_zx.php?fxtype=1&zxtype=2";

	public static final String FC3D_GROUP_DZ_URL_1 = "http://zx.500wan.com/sd/fsfx_zx.php";
	public static final String FC3D_GROUP_DG_URL_1 = "http://zx.500wan.com/sd/fsfx_zx.php";

	
	public static final String FC3D_NUM_PATH_1 = File.separator+"xml"+File.separator+"fc3d"+File.separator+"zhixuan"+File.separator+"hmtj"+File.separator;
	public static final String FC3D_NUM_PATH_2 = File.separator+"xml"+File.separator+"fc3d"+File.separator+"zhixuan"+File.separator+"wztj"+File.separator;
	public static final String FC3D_GROUP_PATH_1 =File.separator+"xml"+File.separator+"fc3d"+File.separator+"zuxuan"+File.separator+"dz"+File.separator;
	public static final String FC3D_GROUP_PATH_2 = File.separator+"xml"+File.separator+"fc3d"+File.separator+"zuxuan"+File.separator+"dg"+File.separator;
	//public static final String FC3D_GROUP_PATH_3 = "D:\\fc3d\\组选复式分析\\按单个号码\\";
	
	//数字排列  直选复式分析 按号码统计 http://zx.500wan.com/sd/fsfx.php?fxtype=1
	public static final String SZPL_NUM_URL = "http://zx.500wan.com/szpl/fsfx.php?fxtype=1";
	public static final String SZPL_NUM_URL_1 = "http://zx.500wan.com/szpl/fsfx.php";
	//数字排列  直选复式分析 按位置统计
	public static final String SZPL_POS_URL = "http://zx.500wan.com/szpl/fsfx.php?fxtype=2";
	public static final String SZPL_POS_URL_1 = "http://zx.500wan.com/szpl/fsfx.php";
	
	//数字排列  组选复式分析 按单注号码
	public static final String SZPL_GROUP_DZ_URL = "http://zx.500wan.com/szpl/fsfx_zx.php?fxtype=2";
	public static final String SZPL_GROUP_DZ_URL_1 = "http://zx.500wan.com/szpl/fsfx_zx.php";
	
	public static final String SZPL_GROUP_DZ_URL_2 = "http://zx.500wan.com/szpl/fsfx_zx.php?fxtype=2&zxtype=1";
	public static final String SZPL_GROUP_DZ_URL_3 = "http://zx.500wan.com/szpl/fsfx_zx.php?fxtype=2&zxtype=2";

	//数字排列  组选复式分析 按单个号码
	public static final String SZPL_GROUP_DG_URL = "http://zx.500wan.com/szpl/fsfx_zx.php?fxtype=1";
	
	public static final String SZPL_GROUP_DG_URL_2 = "http://zx.500wan.com/szpl/fsfx_zx.php?fxtype=1&zxtype=1";
	public static final String SZPL_GROUP_DG_URL_3 = "http://zx.500wan.com/szpl/fsfx_zx.php?fxtype=1&zxtype=2";

	//数字排列  组选复式分析 按单个号码
	public static final String SZPL_MATCH_C_SM_HEAD="<input.*?name=\"RadioGroup2\" value=\"(.*?)\".*?checked=\"checked\"/>.*?<input.*?name=\"RadioGroup3\" value=\"(.*?)\".*?checked=\"checked\"/>.*?<option value=\"(\\d+?)\" selected=\"selected\">\\d+?</option>.*?<option value=\"(\\d+?)\" selected=\"selected\">.*?</option>";
	public static final String SZPL_MATCH_C_DG_HEAD="<input.*?name=\"RadioGroup2\" value=\"(.*?)\".*?checked=\"checked\".*?/>.*? name=\"RadioGroup3\" value=\"(.*?)\".*?checked=\"checked\"/>.*?<option value=\"(\\d+?)\" selected=\"selected\">\\d+?</option>.*?排序按.*?<option value=\"(\\d+?)\" selected=\"selected\">.*?</option>";
	public static final String SZPL_MATCH_C_DZ_HEAD="<input .*? name=\"RadioGroup2\" value=\"(.*?)\" .*? checked=\"checked\".*?/>.*?<input name=\"RadioGroup3\" .*? value=\"(.*?)\" checked=\"checked\" />.*?<option value=\"(\\d+?)\" selected=\"selected\">\\d+?</option>.*?排序按.*?<option value=\"(\\d+?)\" selected=\"selected\">.*?</option>";
	
	public static final String SZPL_NUM_PATH_1 = File.separator+"xml"+File.separator+"szpl"+File.separator+"zhixuan"+File.separator+"hmtj"+File.separator;
	public static final String SZPL_NUM_PATH_2 = File.separator+"xml"+File.separator+"szpl"+File.separator+"zhixuan"+File.separator+"wztj"+File.separator;
	public static final String SZPL_GROUP_PATH_1 = File.separator+"xml"+File.separator+"szpl"+File.separator+"zuxuan"+File.separator+"dz"+File.separator;
	public static final String SZPL_GROUP_PATH_2 = File.separator+"xml"+File.separator+"szpl"+File.separator+"zuxuan"+File.separator+"dg"+File.separator;
	//public static final String SZPL_GROUP_PATH_3 = "D:\\szpl\\组选复式分析\\按单个号码\\";
	
	
	
	//以下对应方法FetchController
	public static final String SSQ_MATCH_CONDTION = "<option value=\"(\\d+)\".*?>(\\d+)</option>";	

	//最新开奖公告最新中奖信息XML存放路径
	public static final String LAST_RESULT = File.separator+"xml"+File.separator+"kjxx"+File.separator;

	//单场足彩析亚欧
	public static final String DCZC_XYO = "<tr id=\"match.*?\".*?<td>(.*?)</td>.*?<td.*?<a style=\"color:#000000;\" .*? id=\"home(.*?)\">(.*?)</div></a></td>.*? id=\"guset(.*?)\">(.*?)</div>.*?" +
	"<a href=\"javascript:Showeurop(.*?)\".*?" +
	"<a href=\"javascript:Showeurop(.*?)\".*?" +
	"<a href=\"javascript:Showeurop(.*?)\".*?" +
	"<a href=\"javascript:ShowAnalyse(.*?);\".*?</td>";
	//胜负彩
	public static final String SFCURL="http://odds.cai310.com/index.php?controller=sfcodds&action=index&tp=1";
	//进球彩
	public static final String JQCURL="http://odds.cai310.com/index.php?controller=jqodds&action=index&tp=3";
	//半全场
	public static final String BQCURL="http://odds.cai310.com/index.php?controller=bqodds&action=index&tp=4";

	public static final String XYO_URL = "http://odds.cai310.com/index.php?controller=bdodds&action=index&tp=5";
	public static final String DCZC_CUR_PATH = File.separator+"xml"+File.separator+"dczc"+File.separator;
	public static final String OYP_URL = "http://www.cailele.com/lottery/dcspf/";
	public static final String OYP = "<tr id=\"race.*?<td class=\"bd_r\" .*? class=\"f11\">(.*?)</span>.*?<td class=\"bg01 bd_r\">.*?<span id=\"az_.*?_h\" class=\"f11\" attr=\"h\" .*?>(.*?)</span>.*? id=\"ylb_.*? class=\"f11\".*?>(.*?)</span>" +
	".*?<span id=\"bz_.*?_s\" class=\"f11\" attr=\"s\" .*?>(.*?)</span>.*?<span id=\"ylb_.*?\" class=\"f11\".*?>(.*?)</span>.*?class=\"f1102\" attr=\"r\".*?>(.*?)</span>" +
	".*?<span id=\"bz_.*?_p\" class=\"f11\" attr=\"p\" .*?>(.*?)</span>.*?<span id=\"ylb_.*?_bz_1\" class=\"f11\".*?>(.*?)</span>.*?<span id=\"az_.*?_a\" class=\"f11\" attr=\"a\" .*?>(.*?)</span>" +
	".*?<span id=\"ylb_.*?_az_1\" class=\"f11\".*?>(.*?)</span>.*?<span id=\"bz_.*?_f\" class=\"f11\" attr=\"f\" .*?>(.*?)</span>.*?<span id=\"ylb_.*?_bz_2\" class=\"f11\".*?>(.*?)</span>" +
	".*?</tr>"; 
	public static final String CUR_PERIOD = "<td.*?<option selected=\"selected\" value=\"(.*?)\">.*?</td>";
	public static final String CURPERIOD_URL = "http://www.cai310.com/ch/dczc.htm";
	//过滤的最大条目
	public static final Integer MAX_FILTER=1000000;
}
