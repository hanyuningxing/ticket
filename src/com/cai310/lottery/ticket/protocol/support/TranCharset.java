package com.cai310.lottery.ticket.protocol.support;

public class TranCharset {    
	   
    private static final String PRE_FIX_UTF = "&#x";    
    private static final String POS_FIX_UTF = ";";    
   
    public TranCharset() {    
     }    
   
    /** 
      * Translate    charset    encoding    to    unicode 
      * 
      * @param sTemp charset    encoding    is    gb2312 
      * @return charset    encoding    is    unicode 
      */   
    public static String XmlFormalize(String sTemp) {    
         StringBuffer sb = new StringBuffer();    
   
        if (sTemp == null || sTemp.equals("")) {    
            return "";    
         }    
         String s = TranCharset.TranEncodeTOGB(sTemp);    
        for (int i = 0; i < s.length(); i++) {    
            char cChar = s.charAt(i);    
            if (TranCharset.isGB2312(cChar)) {    
                 sb.append(PRE_FIX_UTF);    
                 sb.append(Integer.toHexString(cChar));    
                 sb.append(POS_FIX_UTF);    
             } else {    
                switch ((int) cChar) {    
                    case 32:    
                         sb.append("&#32;");    
                        break;    
                    case 34:    
                         sb.append("&quot;");    
                        break;    
                    case 38:    
                         sb.append("&amp;");    
                        break;    
                    case 60:    
                         sb.append("&lt;");    
                        break;    
                    case 62:    
                         sb.append("&gt;");    
                        break;    
                    default:    
                         sb.append(cChar);    
                 }    
             }    
         }    
        return sb.toString();    
     }    
   
    /** 
      * 将字符串编码格式转成GB2312 
      * 
      * @param str 
      * @return 
      */   
    public static String TranEncodeTOGB(String str) {    
        try {    
             String strEncode = TranCharset.getEncoding(str);    
             String temp = new String(str.getBytes(strEncode), "GB2312");    
            return temp;    
         } catch (java.io.IOException ex) {    
   
            return null;    
         }    
     }    
    
    public static String TranEncodeTOGBK(String str) {    
        try {    
             String strEncode = TranCharset.getEncoding(str);    
             String temp = new String(str.getBytes(strEncode), "GBK");    
            return temp;    
         } catch (java.io.IOException ex) {    
   
            return null;    
         }    
     }   
   
    /** 
      * 判断输入字符是否为gb2312的编码格式 
      * 
      * @param c 输入字符 
      * @return 如果是gb2312返回真，否则返回假 
      */   
    public static boolean isGB2312(char c) {    
         Character ch = new Character(c);    
         String sCh = ch.toString();    
        try {    
            byte[]    bb = sCh.getBytes("gb2312");    
            if (bb.length > 1) {    
                return true;    
             }    
         } catch (java.io.UnsupportedEncodingException ex) {    
            return false;    
         }    
        return false;    
     }    
   
    /** 
      * 判断字符串的编码 
      * 
      * @param str 
      * @return 
      */   
    public static String getEncoding(String str) {    
         String encode = "GB2312";    
        try {    
            if (str.equals(new String(str.getBytes(encode), encode))) {    
                 String s = encode;    
                return s;    
             }    
         } catch (Exception exception) {    
         }    
         encode = "ISO-8859-1";    
        try {    
            if (str.equals(new String(str.getBytes(encode), encode))) {    
                 String s1 = encode;    
                return s1;    
             }    
         } catch (Exception exception1) {    
         }    
         encode = "UTF-8";    
        try {    
            if (str.equals(new String(str.getBytes(encode), encode))) {    
                 String s2 = encode;    
                return s2;    
             }    
         } catch (Exception exception2) {    
         }    
         encode = "GBK";    
        try {    
            if (str.equals(new String(str.getBytes(encode), encode))) {    
                 String s3 = encode;    
                return s3;    
             }    
         } catch (Exception exception3) {    
         }    
        return "";    
     }    
    
    public static void main(String [] ar)throws Exception{
    	String s ="中国人";
    	s = new String(s.getBytes("GBK"),"UTF-8");
    	
    	System.out.println(TranCharset.getEncoding(s));
    }
}   

