package com.cai310.lottery.ticket.protocol.localnew.utils;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.MD5;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

public class FetchDataThread implements Runnable {
	//private static final String reUrl="http://www.552cai.com/ticket.jsp";
	//private static final String reUrl="http://localnewhost:8080/ticket.jsp";
	//private static final String reUrl="http://219.134.129.169:8090/ticket.jsp";
	//private static final String reUrl="http://61.147.79.116:7089/ticket.jsp";
	//private static final String reUrl="http://localnewhost:8080/ticket";
	private static final String reUrl="http://218.28.137.234:61288/Api";
//	private static final String reUrl="http://www.qiu310.com/ticket";
//	private static final String reUrl="http://42.159.133.140:61288/Api";
//	private static final String reUrl="http://124.128.234.4:13088/Api";
	//private static final String Uid="250";
	//private static final String key="E404953F7A6DE2C6315F737C5878D094";
	//private static final String Uid="1";
	//private static final String key="E10ADC3949BA59ABBE56E057F20F883E";
	//private static final String Uid="504";
	//private static final String key="2573F076B19986CAFAC1BBE81C87E48D";
//	private static final String Uid="233";
//    private static final String key="E10ADC3949BA59ABBE56E057F20F883E";
    private static final String key="qwerty";
	private static final String password="2bfcca30dd7a0b6857da4b9e2c1ddf2c";
    public void run() {
    	try{
   	    Long time =System.currentTimeMillis();
    	    Document document = DocumentHelper.createDocument();
    	   	/** 建立XML文档的根 */
    	   	Element rootElement = document.addElement("ReqParam");
    	   	rootElement.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
    		rootElement.addAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
//    	    List<String> ticket = Lists.newArrayList();
//    		for (long i = time; i < time+1; i++) {
//        		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
//        		ParamMap.put("mode", "0");
//        		ParamMap.put("periodNumber", "13180");
//        		ParamMap.put("cost", "2");
//        		ParamMap.put("multiple", "1");
//        		ParamMap.put("units", "1");
//        		ParamMap.put("playType", "0");
//        		ParamMap.put("type", "0");
//        		ParamMap.put("passType", "0");
//        		String value = "{\"danMinHit\":1,\"items\":[{\"dan\":false,\"matchKey\":\"0\",\"value\":3}," +
//        													"{\"dan\":false,\"matchKey\":\"2\",\"value\":3}," +
//        													"{\"dan\":false,\"matchKey\":\"3\",\"value\":3}," +
//									        				"{\"dan\":false,\"matchKey\":\"4\",\"value\":0}," +
//									        				"{\"dan\":false,\"matchKey\":\"5\",\"value\":3}," +
//									        				"{\"dan\":false,\"matchKey\":\"6\",\"value\":3}," +
//									        				"{\"dan\":true,\"matchKey\":\"7\",\"value\":1}," +
//									        				"{\"dan\":false,\"matchKey\":\"8\",\"value\":0}," +
//									        				"{\"dan\":false,\"matchKey\":\"10\",\"value\":3}]}";
//        		String value = "{\"danMinHit\":-1,\"danMaxHit\":-1,\"items\":[{\"dan\":false,\"lineId\":\"74\",\"value\":3}," +
//									"{\"dan\":false,\"lineId\":\"75\",\"value\":3}+" +
//									"{\"dan\":false,\"lineId\":\"76\",\"value\":3}]}";
//        		ParamMap.put("value", "*,1,3,0,*,0,*,1,1,*,*,1,1,1");
//        		ParamMap.put("value", "3,1,3,3,3,3,3,3,3,3,3,3,3,3");
        		
//        		String value = "{\"items\":[{\"matchKey\":\"20131024-301\",\"value\":\"home1-5\"},{\"matchKey\":\"20131024-302\",\"value\":\"home11-15\"}]}";
//         		ParamMap.put("value", "1");
//        		ParamMap.put("userId", Uid);
//        		ParamMap.put("userPwd", password);
//        		ParamMap.put("value", "3");
//        		ParamMap.put("userId", "134");
//        		ParamMap.put("userPwd", "2a90ac99f393436c3a581f83fe90e57b");
//        		
//        		ParamMap.put("orderId", "ee");
//        		ParamMap.put("outOrderNumber", "86941");
//        		ParamMap.put("periodSizeOfChase", "2");
//        		ParamMap.put("totalCostOfChase", "8");
//        		ParamMap.put("wonStopOfChase", "false");
        		
        		
        		
        		
        		
        		
//        		ParamMap.put("shareType", "0");
//        		ParamMap.put("baodiCost", "1");
//        		ParamMap.put("subscriptionCost", "1");
//        		ParamMap.put("commissionRate", "1");
//        		ParamMap.put("minSubscriptionCost", "1");
//        		ParamMap.put("secretType", "");
//        		ticket.add(JsonUtil.getJsonString4JavaPOJO(ParamMap));
//    		}
//    		Map<String,Object> ParamMap=new LinkedHashMap<String,Object>();
//    		ParamMap.put("userId", "134");
//    		ParamMap.put("userPwd", "E10ADC3949BA59ABBE56E057F20F883E");
//    		ParamMap.put("wLotteryId", Lottery.DCZC.ordinal());
//    		ParamMap.put("id", "20");
//    		ParamMap.put("ticket", ticket);
//    		ParamMap.put("id", "20"); 
//    		ParamMap.put("userName", "childs312"); 
//    		ParamMap.put("start", "0");   		
//    		ParamMap.put("count", "100");
//    		ParamMap.put("periodNumber", "140405");
//    		ParamMap.put("playType", "0");
//    		ParamMap.put("type", "0");
//    		Map<String,String> ParamMap1=new LinkedHashMap<String,String>();
//    		ParamMap.put("userWay", "APHONE");
//    		ParamMap.put("mid", "");
//    		ParamMap.put("playType", "0");
//    		ParamMap.put("type", "0");
//    		ParamMap.put("passType", "1");
//    		ParamMap.put("periodNumber", "121117");
//    		ParamMap.put("wLotteryId", "");
////    		ParamMap.put("report", "好");
//  		    ParamMap.put("userName", "jp1888");
//    		ParamMap.put("userPwd", SecurityUtil.md5("111111").toUpperCase().trim());
//    		ParamMap.put("mobile", "11111111111");
//			ParamMap.put("realName", "ALIPAYPHONE");
//			ParamMap.put("idcard", "342423197401265877");
////    		String wAction  = "215";
//    		ParamMap.put("oldPwd", SecurityUtil.md5("123456").toUpperCase().trim());
//    		ParamMap.put("newPwd", SecurityUtil.md5("1234").toUpperCase().trim());
//    		String wAction  = "101";
//    		ParamMap.put("model", "model");
//    		ParamMap.put("sdk", "sdk");
//    		ParamMap.put("number", "number");
//    		ParamMap.put("imei", "imei");
//    		ParamMap.put("imsi", "imsi");
//    		ParamMap.put("mid", "1");
    		
//    		ParamMap.put("update", "1");
//    		ParamMap.put("id", "1");
//     		ParamMap.put("amount", "1");
//    		ParamMap.put("userWay", "IPHONE");
//    		ParamMap.put("userId", Uid);
//    		ParamMap.put("userPwd", password);
//    		ParamMap.put("isSend", "false");
//    		ParamMap.put("userId", 83);
//    		ParamMap.put("userPwd", "f0298a3a13d1ddb82a38fe108ead91f8");
//    		ParamMap.put("userId", 14);
//    		ParamMap.put("userPwd", "481003544d98e630e7e0eb1a739806e5");
//    		ParamMap.put("payWay", "YINLIAN_PHONE");
//  		ParamMap.put("won", "true");
//    		ParamMap.put("userName", "childs");
//    		ParamMap.put("userPwd", SecurityUtil.md5("123456").toUpperCase().trim());
//    		ParamMap.put("mobile", "11111111111");
//    		ParamMap.put("messageType", "CHECKPHONE");
//    		ParamMap.put("randomWord", "444267");
//    		ParamMap.put("winningUpdateStatus", "NONE");
//   		ParamMap.put("orderType", "1");
//    		ParamMap.put("playTypeOrdinal", "0");
//    		ParamMap.put("schemeId", "25");
//    		ParamMap.put("subscriptionCost", "1");
//    		ParamMap.put("orderId", "127A810684A52C531F689758F10D6A69");
    		
    		List<Map<String,String>> list = Lists.newArrayList();
    		Map<String,String> paramMap=new LinkedHashMap<String,String>();
    		paramMap.put("TicketId", "1000000");//
//    		paramMap.put("SourceId", "1");
//    		paramMap.put("Priority", "1");
//    		paramMap.put("PlatformId", "10572");
//    		paramMap.put("BetType", "0");//
//    		paramMap.put("IssueNumber", "2014072412");//2014072236
//    		paramMap.put("StartSaleTime", DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
//    		paramMap.put("EndSaleTime", "2014-07-24 10:50:00");
//    		paramMap.put("PlayType", "0");//
//    		paramMap.put("Multiple", "1");//
//    		paramMap.put("BetUnits", "11");//
//    		paramMap.put("BetCost", "22");
//            paramMap.put("BetMode", "");
//            paramMap.put("BetContent", "01,02,03,04,05,06,07,08,09,10,11");
    		list.add(paramMap);
    		String betValue =JSONArray.fromObject(list).toString();
//    		String wAction  = "104";
    		String version="1";
    		String transCode = "902";
    		Map<String,String> ParamMap1=new LinkedHashMap<String,String>();
//    		String wAgent = Uid;
//    		String betValue = JsonUtil.getJsonString4JavaPOJO(ParamMap);
//    		ParamMap1.put("wAction", wAction);
    		ParamMap1.put("transCode", transCode);
    		ParamMap1.put("version", version);
//    		ParamMap1.put("wParam", betValue);
    		ParamMap1.put("parame", "13");
//    		ParamMap1.put("wAgent", wAgent);
//    		String param = wAction+betValue+wAgent+key;
//    		String pwd  = SecurityUtil.md5(param.getBytes("UTF-8")).toUpperCase().trim();
//    		ParamMap1.put("wSign", pwd);
    		ParamMap1.put("Key", SecurityUtil.md5((transCode+version+"13"+key).getBytes("UTF-8")).toLowerCase());
    		String returnString = HttpclientUtil.Utf8HttpClientUtils(reUrl,ParamMap1);
//    		Map<String, Object> _map = JsonUtil.getMap4Json(returnString);
//    		JSONObject jsonObj = (JSONObject) _map.get("scheme");
    		Gson gson = new Gson();
    		CpResult cpResult = gson.fromJson(returnString, CpResult.class);
    		System.out.print(returnString);

    		
    		int i = 0;
   		}catch(Exception e){
   			
   		}
    }
    public static void main(String[] args) throws ClientProtocolException, IOException, DocumentException {
		 AsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("FetchDataThread");
		 FetchDataThread fetchDataThread = new FetchDataThread();
	     executor.execute(fetchDataThread, 0L);
		int i=0;
	}
    
}