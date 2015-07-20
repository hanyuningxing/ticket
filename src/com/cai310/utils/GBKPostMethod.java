package com.cai310.utils;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.cai310.lottery.ticket.protocol.local.utils.CPUtil;


  
//Inner class for UTF-8 support  
public class GBKPostMethod extends PostMethod{  
    public GBKPostMethod(String url){  
        super(url);  
    }  
    @Override  
    public String getRequestCharSet() {  
        return "GBK";  
    }  
    
}  