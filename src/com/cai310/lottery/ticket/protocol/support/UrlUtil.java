package com.cai310.lottery.ticket.protocol.support;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.utils.HttpClientUtil;

public class UrlUtil {
	protected static Logger logger = LoggerFactory.getLogger(UrlUtil.class);
	public static String getDecoder(String string,String coding){
		String ret="";
		try {
			ret=URLDecoder.decode(string, coding);
		} catch (UnsupportedEncodingException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public static String getEncoder(String string,String coding){
		String ret="";
		try { 
			ret=URLEncoder.encode(string, coding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public static String httpClientUtilsRlyg(MessageSendDTO mDTO)throws ClientProtocolException, IOException  {
		String encoding = mDTO.getEncoding();
		URL url = null;
		org.apache.commons.httpclient.HttpClient httpClient;
		PostMethod postMethod = null;

		
		try{
			httpClient = new org.apache.commons.httpclient.HttpClient();
			HttpClientParams p = new HttpClientParams();
			p.setIntParameter(HttpConnectionParams.SO_TIMEOUT,10000); //超时设置
			p.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);//连接超时 
			p.setSoTimeout(30000);
			httpClient.setParams(p);
			
			postMethod = new PostMethod(mDTO.getTransationUrl());
			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		    UrlUtil.logger.info("出票接口发送数据RLYG:"+mDTO.getMessage());
			// HTTP参数
			org.apache.commons.httpclient.NameValuePair[] postData = new org.apache.commons.httpclient.NameValuePair[2];
			postData[0] = new org.apache.commons.httpclient.NameValuePair("cmd", mDTO.getType()+"");
			postData[1] = new org.apache.commons.httpclient.NameValuePair("msg",TranCharset.TranEncodeTOGBK(mDTO.getMessage()));
			postMethod.addParameters(postData);
			httpClient.executeMethod(postMethod);
			String reStr = postMethod.getResponseBodyAsString();
			UrlUtil.logger.info("出票接口回传数据RLYG:"+reStr);
			return reStr;

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			postMethod.releaseConnection();
		}    
		return "";
    
	}
	
	public static String httpClientUtils(MessageSendDTO mDTO)throws ClientProtocolException, IOException  {
		String encoding = mDTO.getEncoding();
		HttpClient httpclient = HttpClientUtil.getHttpClient();
        HttpPost httppost = new HttpPost(mDTO.getTransationUrl());
        httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT,10000); //超时设置
        httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);//连接超时 
        if(mDTO.isSimpleSend()){
        	 StringEntity myEntity = new StringEntity(mDTO.getMessage(),encoding);
     		 UrlUtil.logger.info("出票接口发送数据:"+mDTO.getMessage());
        	 httppost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8"); 
        	 httppost.setEntity(myEntity);
        }else{
        	List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair(mDTO.getTypekey(), String.valueOf(mDTO.getType())));
            UrlUtil.logger.info("出票接口发送数据:"+mDTO.getMessage());
            formparams.add(new BasicNameValuePair(mDTO.getMessageKey(), mDTO.getMessage()));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,encoding);
            entity.setContentType("application/x-www-form-urlencoded;charset=utf-8");
            httppost.setEntity(entity);
        }
        StringBuffer returnInfo = new StringBuffer();
        HttpEntity resEntity = null;
        try {
        	HttpResponse response = httpclient.execute(httppost);
            resEntity = response.getEntity();
            InputStreamReader reader = new InputStreamReader(resEntity.getContent(),encoding);            
            char[] buff = new char[1024];
            int length = 0;
            try {
            	while ((length = reader.read(buff)) != -1) {
                	returnInfo.append(new String(buff, 0, length));
                }
            } catch (IOException e) {
            	logger.error("出票商返回信息IO流读取异常",e);
            } finally {
                try {
                	reader.close();
                } catch (IOException e) {
                	logger.error("出票商返回信息IO流关闭异常",e);
                }
            } 
        } catch (ClientProtocolException e)  {
        	logger.error("建立出票商服务连接协议异常",e);
        } catch (IOException e) {
        	logger.error("出票商通信连接异常，连接地址："+mDTO.getTransationUrl(),e);
        } finally{
        	if(resEntity!=null)resEntity.consumeContent();
        	httppost.abort();
        }
        UrlUtil.logger.info("出票接口回传数据:"+returnInfo.toString());
		return returnInfo.toString();
    }
	
	
	
	public static String getDomain(HttpServletRequest request) {
		try {

			String url = request.getHeader("Referer");
			url = url.replaceAll("http://", "");

			int index = url.indexOf("/");

			if (index != -1)
				url = url.substring(0, index);
			return "http://"+url;
		} catch (Exception e) {
			return null;
		}
	}
	
	 public static void main(String[] args) throws IOException {
		    MessageSendDTO dto = new MessageSendDTO();
			dto.setSimpleSend(true);
			dto.setTypekey("cmd");
			dto.setType(50203);
			dto.setMessageKey("msg");
			dto.setEncoding("GB2312");
			dto.setTransationUrl("http://t.zc310.net:8089/bin/LotSaleHttp.dll");
			//获取相应信息内容
			//开奖号码
			//String sign =  SecurityUtil.md5("382111012345678912345678900LotID=10401_LotIssue=101012064a8b8c8d8e8f8g8h8").toLowerCase();
			//String message = "wAgent=3821&wAction=110&wMsgID=12345678912345678900&wSign="+sign+"&wParam=LotID=10401_LotIssue=101012064" ;
			
			String sign = SecurityUtil.md5("3821 101 4444 OrderID=D2010128298_LotID=33_LotIssue=2010008_LotMoney=12_LotMulti=2_OneMoney=2_LotCode=1|68,2,9;6|5,1,8_Attach=投注测试a8b8c8d8e8f8g8h8").toLowerCase();
			String message ="wAgent=3821&wAction=110&wMsgID=3821201105231306161722369&wSign=f6680a580e04ae4128f86a071ebf8c69&wParam=LotID=10401_LotIssue=101012064" ;
			//String LotIssue,String LotID,Long wMsgIDValue,
			//OrderID=D2010128298_LotID=33_LotIssue=2010008_LotMoney=12_LotMulti=2_OneMoney=2_LotCode=1|68,2,9;6|5,1,8_Attach=投注测试
			dto.setMessage(message);
			System.out.println(UrlUtil.httpClientUtils(dto));
			
		/*	MessageSendDTO md = new MessageSendDTO();
			String content = "<?xml version=\"1.0\" encoding=\"utf-8\"?><msg v=\"1.0\" id=\"1298357230941\"><ctrl><agentID>800010</agentID><cmd>2006</cmd><timestamp>20110222144710</timestamp><md>583f7bceff2f0417217b42b0477b0424</md></ctrl><body><order username=\"\" lotoid=\"203\" issue=\"0004419\" areaid=\"00\" orderno=\"1298357230941\"><userinfo realname=\"cai310.com\" mobile=\"13811010058\" email=\"\" cardtype=\"1\" cardno=\"370781198904104029\"/><ticket seq=\"1\">00-31-1:[0]/2:[0]/3:[0]/4:[0]/5:[0]-1-2</ticket></order></body></msg>";
			md.setMessage(content);
			md.setTransationUrl("http://119.57.74.210:7070/billservice/sltAPI");
			md.setTypekey("cmd");
			md.setType(2006);
			md.setMessageKey("msg");
			md.setSimpleSend(false);
			System.out.println(UrlUtil.httpClientUtils(md));
			
			md = new MessageSendDTO();
			content = "<?xml version=\"1.0\" encoding=\"GBK\"?><message version=\"1.0\" id=\"22011022400007457\"><header><messengerID>2</messengerID><timestamp>20110224113833</timestamp><transactionType>103</transactionType><digest>D72EB1C0083DC69E7D9451C30F38A8A3</digest></header><body><lotteryRequest><ticket id=\"3306822\" playType=\"7115\" money=\"2.00\" amount=\"1\"><issue number=\"2011022411\" gameName=\"SDQYH\" /><userProfile userName=\"cai310.com\" cardType=\"1\" mail=\"15668368@qq.com\" cardNumber=\"370781198904104029\" mobile=\"13501098899\" realName=\"cai310.com\" bonusPhone=\"13501098899\" /><anteCode>01</anteCode></ticket></lotteryRequest></body></message>";
			md.setMessage(content);
			md.setTransationUrl("http://www.175cp.com/Agent/ElectronTicket/Receive.aspx");
			md.setTypekey("cmd");
			md.setType(2006);
			md.setMessageKey("msg");
			md.setSimpleSend(false);
			System.out.println(UrlUtil.httpClientUtils(md));*/
			
			
	 } 
}
