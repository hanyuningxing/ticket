package com.cai310.lottery.ticket.protocol.bd.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.dom4j.DocumentException;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.lottery.utils.Base64Util;
import com.cai310.lottery.utils.StringUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

public abstract class BeiDanUtil {
	
	//  测试帐号
//    private static final String reUrl="http://101.251.195.152:8001/vespid";
//    private static String userId = "7";
//    private static String Key ="123456";
	
	private static final String reUrl="http://59.151.41.188/vespid";
    private static String userId = "40";
    private static String Key ="dsjghrwh8swkgskiu";
    private static Map<String,String> prizeMap = new HashMap<String,String>();
    private static Map<String,QueryPVisitor> pidMap = new HashMap<String,QueryPVisitor>();

    public abstract String getLotCode(Ticket ticket) throws DataException;
    public abstract Integer getLastMatchNum(Ticket ticket) throws DataException;
	/**
	 * 发送彩票
	 * @param ticket
	 * @return
	 * @throws DataException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public CpResultVisitor sendTicket(Ticket ticket) throws DataException, IOException, DocumentException{
		
		String orderId = ""+ticket.getId();
		String gameId = getBeiDanLotteryId(ticket);
		QueryPVisitor queryPVisitor = new QueryPVisitor();
		if(!pidMap.isEmpty()&&pidMap.containsKey(ticket.getPeriodNumber()+"_"+gameId)){
			queryPVisitor = pidMap.get(ticket.getPeriodNumber()+"_"+gameId);
		}else{
			queryPVisitor = getIssue(gameId);
			if(pidMap.size()>6){
				pidMap.clear();
			}
			if(queryPVisitor.getIsSuccess()){
				pidMap.put(ticket.getPeriodNumber()+"_"+gameId, queryPVisitor);
			}
		}
		CpResultVisitor cpResultVisitor = new CpResultVisitor();
		String drawId ="";
		if(queryPVisitor.getIsSuccess()){
			if(queryPVisitor.getIssueNumber().equals(ticket.getPeriodNumber().substring(1))){
				drawId = queryPVisitor.getIssueId();
			}else{
				queryPVisitor.setStatus("获取期号"+queryPVisitor.getIssueNumber()+"与系统期号"+ticket.getPeriodNumber().substring(1)+"不一致");
			}
		}
		if(StringUtil.isEmpty(drawId)){
			cpResultVisitor.setResult("期号错误："+queryPVisitor.getStatus());
			return cpResultVisitor;
		}
		String money = ""+ticket.getSchemeCost();
		
		
		StringBuffer orderParameter = new StringBuffer();
		
		
		cpResultVisitor.setIssueId(drawId);
		cpResultVisitor.setOrderId(orderId);
		cpResultVisitor.setIsSuccess(false);
		cpResultVisitor.setResult("-1");
		cpResultVisitor.setBetMoney(money);
		
		orderParameter.append(orderId).append("-")
		.append(gameId).append("-")
		.append(ticket.getUnits()).append("-")
		.append(money).append("-")
		.append(getLotCode(ticket)).append("-")
		.append(ticket.getMultiple()).append("-")
		.append(0);
		System.out.println("------------orderParameter-----------"+orderParameter);
		String base64 = Base64Util.encode(ZLibUtils.compress(orderParameter.toString().getBytes()));
		String singStr = userId+"*"+ orderId+"*"+ gameId+"*"+drawId+"*"+ money+"*"+base64+"*"+Key;
		System.out.println("------------singStr-----------"+singStr);
		String sign = SecurityUtil.md5(singStr.getBytes("UTF-8"));
		String postData="userId="+userId+"&orderId="+orderId+"&gameId="+gameId+"&drawId="+drawId
				+"&money="+money+"&orderParameter="+base64+"&MD5="+sign;
		System.out.println("------------postData-----------"+postData);
		String resultStr = Utf8HttpClientUtils(reUrl+"/OrderPurchase",postData);
		System.out.println("------------resultStr-----------"+resultStr);
		String returnString = JsonUtil.getStringValueByJsonStr(resultStr,"code");
		if(null!=returnString&&"0".equals(returnString)){
			cpResultVisitor.setIsSuccess(true);
			cpResultVisitor.setResult(returnString);
		}else{
			String message = JsonUtil.getStringValueByJsonStr(resultStr,"message");
			cpResultVisitor.setResult(returnString+","+message);
		}
		return cpResultVisitor;
	}
	
	
	public CpResultVisitor updateTicket(Ticket ticket) throws DataException, IOException, DocumentException, ParseException{
		
		String issueId = ticket.getIssueId();
		String gameId = getBeiDanLotteryId(ticket);
		if(StringUtil.isEmpty(issueId)){
			String drawNo = ticket.getPeriodNumber().substring(1);
			String drawYear = String.valueOf(DateUtil.getYear(ticket.getCreateTime()));
			issueId = getIssueId(gameId, drawNo, drawYear);
			ticket.setIssueId(issueId);
		}
		Integer matchNum = getLastMatchNum(ticket);
		String folder = "/"+gameId+"/"+issueId+"/";
		String fileName = gameId+"_"+issueId+"_"+matchNum;
		String dirString = folder+fileName+".txt";
		System.out.println(ticket.getOrderNo()+"开奖文件路径-----------------------"+dirString);
		
		CpResultVisitor cpResultVisitor = new CpResultVisitor();
		cpResultVisitor.setOrderId(ticket.getOrderNo());
		
		if(prizeMap.containsKey(dirString)){
			cpResultVisitor.setIsSuccess(true);
			String detailMap = prizeMap.get(dirString);
			if(!StringUtil.isEmpty(detailMap)){
				String[] awards = detailMap.split(",");
				System.out.println(ticket.getOrderNo()+"---"+dirString+"缓存开奖信息-----------------------"+detailMap);
				for (String str : awards) {
					String[] contents = str.split("\\^");
					if(ticket.getOrderNo().equals(contents[1])){
						cpResultVisitor.setDetail(str);
						cpResultVisitor.setPrize(Double.valueOf(contents[3]));
						cpResultVisitor.setTax(Double.valueOf(contents[4]));
						boolean flag = false;
						if("1".equals(contents[5].trim())){
							flag = true;
						}
						cpResultVisitor.setReturnAward(flag);
						cpResultVisitor.setReturnMoney(Double.valueOf(contents[6]));
						return cpResultVisitor;
					}
				}
			}
			return cpResultVisitor;
		}
		FtpUtils ftp = null;
		try {
			ftp = new FtpUtils();
			List list = ftp.getFileList(folder);
			
			if(list.contains(fileName+".crc")&&list.contains(fileName+".txt")){
				cpResultVisitor.setIsSuccess(true);
				String detail = ftp.readFile(dirString);
				if(StringUtil.isEmpty(prizeMap.get(dirString))){
					if(prizeMap.size()>100){
						prizeMap.clear();
					}
					prizeMap.put(dirString, detail);
				}
				if(!StringUtil.isEmpty(detail)){
					String[] awards = detail.split(",");
					System.out.println(ticket.getOrderNo()+"---"+dirString+"开奖信息------------detail-----------"+detail);
					for (String str : awards) {
						String[] contents = str.split("\\^");
						if(ticket.getOrderNo().equals(contents[1])){
							cpResultVisitor.setDetail(str);
							cpResultVisitor.setPrize(Double.valueOf(contents[3]));
							cpResultVisitor.setTax(Double.valueOf(contents[4]));
							boolean flag = false;
							if("1".equals(contents[5].trim())){
								flag = true;
							}
							cpResultVisitor.setReturnAward(flag);
							cpResultVisitor.setReturnMoney(Double.valueOf(contents[6]));
							ftp.closeServer();
							return cpResultVisitor;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ftp.closeServer();
		}
		return cpResultVisitor;
	}
	
	
	public QueryPVisitor confirmTicket(Ticket ticket) throws DataException, IOException, DocumentException{
		String orderId = ""+ticket.getId();
		String singStr = userId+"*"+ orderId+"*"+Key;
		System.out.println("************singStr************"+singStr);
		String sign = SecurityUtil.md5(singStr.getBytes("UTF-8"));
		String postData="userId="+userId+"&orderId="+orderId+"&MD5="+sign;
		System.out.println("************postData************"+postData);
		String result = Utf8HttpClientUtils(reUrl+"/GetOrderResult",postData);
		System.out.println("************result************"+result);
		String code = JsonUtil.getStringValueByJsonStr(result,"code");
		String orderResults = JsonUtil.getStringValueByJsonStr(result,"orderResults");
		
		QueryPVisitor queryPVisitor = new QueryPVisitor();
		if(null!=code&&"0".equals(code)){
			String contents = new String(ZLibUtils.decompress(Base64Util.decode(orderResults)));
			System.out.println("************contents************"+contents);
			String[] mes = contents.split("\\&");
			queryPVisitor.setTicketCode(mes[2]);
			queryPVisitor.setOperateTime(mes[3]);
			queryPVisitor.setStatus(mes[4]);
			queryPVisitor.setOrderId(orderId);
			if(null!=mes[4]&&"0".equals(mes[4].trim())){
				queryPVisitor.setIsSuccess(true);
			}
			queryPVisitor.setResultId(mes[4]);
		}else{
			queryPVisitor.setResultId(code);
			queryPVisitor.setStatus(JsonUtil.getStringValueByJsonStr(result,"message"));
		}
		return queryPVisitor;
	}
	
	public static QueryPVisitor getIssue(String gameId) throws DataException, IOException, DocumentException{
		
		String singStr = gameId+"*"+ userId+"*"+Key;
		System.out.println("########singStr########"+singStr);
		String sign = SecurityUtil.md5(singStr.getBytes("UTF-8"));
		String postData="gameId="+gameId+"&userId="+userId+"&MD5="+sign;
		System.out.println("########postData########"+postData);
		String result = Utf8HttpClientUtils(reUrl+"/GetGameDraw",postData);
		System.out.println("########result########"+result);
		String code = JsonUtil.getStringValueByJsonStr(result,"code");
		String draws = JsonUtil.getStringValueByJsonStr(result,"draws");
		QueryPVisitor queryPVisitor=new QueryPVisitor();
		if("0".equals(code)){
			JSONArray array = JsonUtil.getJSONArray4Json(draws);
			for (Object object : array) {
				JSONObject ob = JSONObject.fromObject(object);
				queryPVisitor.setIssueId(ob.getString("drawId"));
				queryPVisitor.setIssueNumber(ob.getString("drawNo"));
			}
			queryPVisitor.setIsSuccess(true);
		}else{
			queryPVisitor.setStatus(code+","+JsonUtil.getStringValueByJsonStr(result,"message"));
		}
		return queryPVisitor;
	}
	
	public static String getIssueId(String gameId,String drawNo,String drawYear) throws DataException, IOException, DocumentException{
		
		String singStr = drawNo+"*"+drawYear+"*"+ userId+"*"+Key;
		System.out.println("########singStr########"+singStr);
		String sign = SecurityUtil.md5(singStr.getBytes("UTF-8"));
		String postData="drawNo="+drawNo+"&drawYear="+drawYear+"&userId="+userId+"&MD5="+sign;
		System.out.println("########postData########"+postData);
		String result = Utf8HttpClientUtils(reUrl+"/GetDrawID",postData);
		System.out.println("########result########"+result);
		String draws = JsonUtil.getStringValueByJsonStr(result,"draws");
		JSONArray array = JsonUtil.getJSONArray4Json(draws);
		for (Object object : array) {
			JSONObject ob = JSONObject.fromObject(object);
			if(gameId.equals(ob.getString("gameId"))){
				return ob.getString("drawId");
			}
		}
		return "";
	}
	
	public String[] getMatch(String type,String etag,Ticket ticket) throws DataException, IOException, DocumentException{
		ticket.setBetType(Byte.valueOf(type));
		ticket.setLotteryType(Lottery.DCZC);
		String gameId = getBeiDanLotteryId(ticket);
		QueryPVisitor queryPVisitor = getIssue(gameId);
		String drawId ="";
		if(queryPVisitor.getIsSuccess()){
			drawId = queryPVisitor.getIssueId();
			ticket.setPeriodNumber(queryPVisitor.getIssueNumber());
		}
		
		String singStr = drawId+"*0*"+ userId+"*"+Key;
		System.out.println("########singStr########"+singStr);
		String sign = SecurityUtil.md5(singStr.getBytes("UTF-8"));
		String postData="drawId="+drawId+"&timestamp=0&userId="+userId+"&MD5="+sign;
		System.out.println("########postData########"+postData);
		return Utf8HttpClientUtils(reUrl+"/GetMatch",postData,etag);
		
	}
	
	public String getMatchSp(String type) throws DataException, IOException, DocumentException{
		Ticket ticket = new Ticket();
		ticket.setBetType(Byte.valueOf(type));
		ticket.setLotteryType(Lottery.DCZC);
		String gameId = getBeiDanLotteryId(ticket);
		QueryPVisitor queryPVisitor = getIssue(gameId);
		String drawId ="";
		if(queryPVisitor.getIsSuccess()){
			drawId = queryPVisitor.getIssueId();
		}
		String singStr = drawId+"*"+ userId+"*"+Key;
		System.out.println("########singStr########"+singStr);
		String sign = SecurityUtil.md5(singStr.getBytes("UTF-8"));
		String postData="drawId="+drawId+"&userId="+userId+"&MD5="+sign;
		System.out.println("########postData########"+postData);
		return Utf8HttpClientUtils(reUrl+"/GetOdds",postData);
		
	}
	
	
	public static void main(String[] args) throws DataException, IOException, DocumentException {
		StringBuffer orderParameter = new StringBuffer();
//		String orderId = "253742"; String code = "16~20$4^21$4^22$1^23$1^24$4"; String gameId = "200"; String money = "2"; String zhushu = "1"; //胜平负
//		String orderId = "253743"; String code = "4~20$255^21$255^22$255"; String gameId = "230"; String money = "1024"; String zhushu = "512";//进球数
//		String orderId = "253744"; String code = "8~21$15^22$15^23$15^24$15"; String gameId = "210"; String money = "512"; String zhushu = "256";//上下单双过关
//		String orderId = "253745"; String code = "2~22$8^23$65536"; String gameId = "250"; String money = "2"; String zhushu = "1";//比分
//		String orderId = "253746"; String code = "2~21$32^22$288"; String gameId = "240"; String money = "4"; String zhushu = "2";
//		String orderId = "253747"; String code = "2~27$32^28$288"; String gameId = "240"; String money = "4"; String zhushu = "2";
//		QueryPVisitor queryPVisitor = getIssue(gameId);
//		
//		orderParameter.append(orderId+"-"+gameId+"-"+zhushu+"-"+money+"-"+code+"-1-0");
//		String base64 = Base64Util.encode(ZLibUtils.compress(orderParameter.toString().getBytes()));
//		
//		String singStr = "7*"+orderId+"*"+gameId+"*"+queryPVisitor.getIssueId()+"*"+money+"*"+base64+"*"+Key;
//		System.out.println("------------singStr-----------"+singStr);
//		String sign = SecurityUtil.md5(singStr.getBytes("UTF-8"));
//		String postData="userId=7&orderId="+orderId+"&gameId="+gameId+"&drawId="+queryPVisitor.getIssueId()+"&money="+money+"&orderParameter="+base64+"&MD5="+sign;
//		System.out.println("------------postData-----------"+postData);
//		String resultStr = Utf8HttpClientUtils(reUrl+"/OrderPurchase",postData);
//		System.out.println("------------resultStr-----------"+resultStr);
//		String returnString = JsonUtil.getStringValueByJsonStr(resultStr,"code");
		
		
//		String orderId = "253739";
//		String singStr = userId+"*"+orderId+"*"+Key;
//		System.out.println("************singStr************"+singStr);
//		String sign = SecurityUtil.md5(singStr.getBytes("UTF-8"));
//		String postData="userId="+userId+"&orderId="+orderId+"&MD5="+sign;
//		System.out.println("************postData************"+postData);
//		String result = Utf8HttpClientUtils(reUrl+"/GetOrderResult",postData);
//		System.out.println("************result************"+result);
//		String code = JsonUtil.getStringValueByJsonStr(result,"code");
//		String orderResults = JsonUtil.getStringValueByJsonStr(result,"orderResults");
//		
//		QueryPVisitor queryPVisitor = new QueryPVisitor();
//		if(null!=code&&"0".equals(code)){
//			String contents = new String(ZLibUtils.decompress(Base64Util.decode("eJw1yrENgDAMRNFVqFIgWTmfHeNkmyjA/iOAkPjNaz4jWnixk9YnVZaqi+uRko2UWHf6vBxAL4ARKfvXDxK9oRDaqrIqNo1hHHz/B6c0ExY=")));
//			System.out.println("************contents************"+contents);
//			System.out.println(DateUtil.strToDate("2015/12/10 16:32:29", "yyyy/MM/dd hh:mm:ss"));
//			String[] mes = contents.split("\\&");
//			queryPVisitor.setTicketCode(mes[2]);
//			queryPVisitor.setOperateTime(mes[3]);
//			queryPVisitor.setStatus(mes[4]);
//			
//			if(null!=mes[4]&&"0".equals(mes[4].trim())){
//				queryPVisitor.setIsSuccess(true);
//				System.out.println("出票成功");
//			}
//		}
//		Ticket ticket = new Ticket();
//		ticket.setBetType(Byte.valueOf("0"));
//		ticket.setLotteryType(Lottery.DCZC);
//		String gameId = "200";
//		QueryPVisitor queryPVisitor = getIssue(gameId);
//		String drawId ="";
//		if(queryPVisitor.getIsSuccess()){
//			drawId = queryPVisitor.getIssueId();
//		}
//		if(StringUtil.isEmpty(drawId)){
//			//
//		}
//		String singStr = drawId+"*0*"+ userId+"*"+Key;
//		System.out.println("########singStr########"+singStr);
//		String sign = SecurityUtil.md5(singStr.getBytes("UTF-8"));
//		String postData="drawId="+drawId+"&timestamp=0&userId="+userId+"&MD5="+sign;
//		System.out.println("########postData########"+postData);
//		String result = Utf8HttpClientUtils(reUrl+"/GetMatch",postData);
//		System.out.println("########result########"+result);
		System.out.println("160502".substring(1));
	}
	
	 public static String Utf8HttpClientUtils(String url,String param){
		     
		     StringBuffer result=new StringBuffer();
		     BufferedReader reader = null;
		     DefaultHttpClient httpclient = null;
		     HttpPost httppost = null;
		     try {
		    	 BasicHttpParams parms = new BasicHttpParams(); 
		         parms.setParameter("charset", HTTP.UTF_8);
		         parms.setParameter(HttpConnectionParams.SO_TIMEOUT,200000);
			     httpclient = new DefaultHttpClient(parms);  
			     // 目标地址  
			     httppost = new HttpPost(url);
			     httppost.addHeader("charset", HTTP.UTF_8); 
			     // 构造最简单的字符串数据  
			      StringEntity reqEntity = new StringEntity(param,HTTP.UTF_8);  
			     // 设置类型  
			      reqEntity.setContentType("application/x-www-form-urlencoded");
			     // 设置请求的数据  
			      httppost.setEntity(reqEntity);  
			     // 执行  
			      HttpResponse response = httpclient.execute(httppost);  
			      HttpEntity entity = response.getEntity(); 
			     // 显示结果  
			      reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));  
			      String line = null;  
			      while ((line = reader.readLine()) != null) {  
			    	  result.append(line);
			      } 
			      if (entity != null) {  
			        entity.consumeContent();  
			      }
			      reader.close();
		    	  httppost.abort();
		     }catch (Exception ex) {
		    	  reader = null;
				  httpclient = null;
				  httppost = null;
		     }finally{
		    	 reader = null;
				  httpclient = null;
				  httppost = null;
			 }   
		     return result.toString();
	}
	 public static String[] Utf8HttpClientUtils(String url,String param,String etag){
		 
		 StringBuffer result=new StringBuffer();
	     BufferedReader reader = null;
	     DefaultHttpClient httpclient = null;
	     HttpPost httppost = null;
	     String[] contents = new String[3];
		 try {
			 BasicHttpParams parms = new BasicHttpParams(); 
			 parms.setParameter("charset", HTTP.UTF_8);
			 if(!StringUtil.isEmpty(etag)){
				 parms.setParameter("If-None-Match", etag);
			 }
			 parms.setParameter(HttpConnectionParams.SO_TIMEOUT,200000);
			 httpclient = new DefaultHttpClient(parms);  
			 // 目标地址  
		     httppost = new HttpPost(url);
		     httppost.addHeader("charset", HTTP.UTF_8); 
		     // 构造最简单的字符串数据  
		      StringEntity reqEntity = new StringEntity(param,HTTP.UTF_8);  
		     // 设置类型  
		      reqEntity.setContentType("application/x-www-form-urlencoded");
		     // 设置请求的数据  
		      httppost.setEntity(reqEntity);  
		     // 执行  
		      HttpResponse response = httpclient.execute(httppost); 
		      int statusCode = response.getStatusLine().getStatusCode();
		      contents[0] = String.valueOf(statusCode);
				Header[] headers = response.getHeaders("etag");
				if (headers != null && headers.length > 0) {
					for (Header header : headers) {
						contents[1] = header.getValue();
					}
				}
		      HttpEntity entity = response.getEntity(); 
		     // 显示结果  
		      reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));  
		      String line = null;  
		      while ((line = reader.readLine()) != null) {  
		    	  result.append(line);
		      } 
		      if (entity != null) {  
		        entity.consumeContent();  
		      }
		      contents[2] = result.toString();
		      reader.close();
	    	  httppost.abort();
	     }catch (Exception ex) {
	    	  reader = null;
			  httpclient = null;
			  httppost = null;
	     }finally{
	    	 reader = null;
			  httpclient = null;
			  httppost = null;
		 }   
	     return contents;
	 }
	
	public String getMixPlayString(Ticket ticket){
		return null;
	}
	public String getBeiDanLotteryId(Ticket ticket){
		if(null==ticket)return null;
		if(null==ticket.getLotteryType())return null;
		if(Lottery.DCZC.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.dczc.PlayType playType = com.cai310.lottery.support.dczc.PlayType.values()[ticket.getBetType()];
			if(PlayType.SPF.equals(playType)){
				return "200";
			}else if(PlayType.ZJQS.equals(playType)){
				return "230";
			}else if(PlayType.BF.equals(playType)){
				return "250";
			}else if(PlayType.BQQSPF.equals(playType)){
				return "240";
			}else if(PlayType.SXDS.equals(playType)){
				return "210";
			}else{
				return "270";
			}
		}
		return null;
	}
	
	protected static Map<String, Object> map;
	public BeiDanUtil(Ticket ticket){
		if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(null!=ticket&&null!=ticket.getContent())map = JsonUtil.getMap4Json(ticket.getContent());
		}
	}
	public BeiDanUtil(){
		
	}
	public static Map<String, Object> getTicketContentMap(Ticket ticket){
		if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(null!=ticket&&null!=ticket.getContent())map = JsonUtil.getMap4Json(ticket.getContent());
		}
		return map;
	}
	protected List<String> formatBetNum(List<String> numList,NumberFormat nf){
		List<String> newNumList = Lists.newArrayList();
		for (String num : numList) {
			newNumList.add(nf.format(Integer.valueOf(num)));
		}
		return newNumList;
	}
	
}
