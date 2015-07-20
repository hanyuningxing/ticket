package com.cai310.utils;
import java.util.Random;
/** 
* 随机数、随即字符串工具 
* User: leizhimin 
* Date: 2008-11-19 9:43:09 
*/ 
public class RandomUtils { 
        public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        public static final String numberChar = "0123456789"; 
        public static final String luckNumberChar = "0268";
        /** 
         * 返回一个定长的随机字符串(只包含大小写字母、数字) 
         * 
         * @param length 随机字符串长度 
         * @return 随机字符串 
         */ 
        public static String generateString(int length) { 
                StringBuffer sb = new StringBuffer(); 
                Random random = new Random(); 
                for (int i = 0; i < length; i++) { 
                        sb.append(allChar.charAt(random.nextInt(allChar.length()))); 
                } 
                return sb.toString(); 
        } 


        /** 
         * 返回一个定长的随机字符串(只包含数字) 
         * 
         * @param length 随机字符串长度 
         * @return 随机字符串 
         */ 
        public static String generateNumString(int length) { 
                StringBuffer sb = new StringBuffer(); 
                Random random = new Random(); 
                for (int i = 0; i < length; i++) { 
                        sb.append(numberChar.charAt(random.nextInt(numberChar.length()))); 
                } 
                return sb.toString(); 
        } 
        
        /** 
         * 返回一个定长的随机纯字母字符串(只包含大小写字母) 
         * 
         * @param length 随机字符串长度 
         * @return 随机字符串 
         */ 
        public static String generateMixString(int length) { 
                StringBuffer sb = new StringBuffer(); 
                Random random = new Random(); 
                for (int i = 0; i < length; i++) { 
                        sb.append(allChar.charAt(random.nextInt(letterChar.length()))); 
                } 
                return sb.toString(); 
        } 

        /** 
         * 返回一个定长的随机纯大写字母字符串(只包含大小写字母) 
         * 
         * @param length 随机字符串长度 
         * @return 随机字符串 
         */ 
        public static String generateLowerString(int length) { 
                return generateMixString(length).toLowerCase(); 
        } 

        /** 
         * 返回一个定长的随机纯小写字母字符串(只包含大小写字母) 
         * 
         * @param length 随机字符串长度 
         * @return 随机字符串 
         */ 
        public static String generateUpperString(int length) { 
                return generateMixString(length).toUpperCase(); 
        } 

        /** 
         * 生成一个定长的纯0字符串 
         * 
         * @param length 字符串长度 
         * @return 纯0字符串 
         */ 
        public static String generateZeroString(int length) { 
                StringBuffer sb = new StringBuffer(); 
                for (int i = 0; i < length; i++) { 
                        sb.append('0'); 
                }  
                return sb.toString(); 
        } 

        /** 
         * 根据数字生成一个定长的字符串，长度不够前面补0 
         * 
         * @param num             数字 
         * @param fixdlenth 字符串长度 
         * @return 定长的字符串 
         */ 
        public static String toFixdLengthString(long num, int fixdlenth) { 
                StringBuffer sb = new StringBuffer(); 
                String strNum = String.valueOf(num); 
                if (fixdlenth - strNum.length() >= 0) { 
                        sb.append(generateZeroString(fixdlenth - strNum.length())); 
                } else { 
                        throw new RuntimeException("将数字" + num + "转化为长度为"+ fixdlenth + "的字符串发生异常！"); 
                } 
                sb.append(strNum); 
                return sb.toString(); 
        } 

        /** 
         * 根据数字生成一个定长的字符串，长度不够前面补0 
         * 
         * @param num             数字 
         * @param fixdlenth 字符串长度 
         * @return 定长的字符串 
         */ 
        public static String toFixdLengthString(int num, int fixdlenth) { 
                StringBuffer sb = new StringBuffer(); 
                String strNum = String.valueOf(num); 
                if (fixdlenth - strNum.length() >= 0) { 
                        sb.append(generateZeroString(fixdlenth - strNum.length())); 
                } else { 
                        throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！"); 
                } 
                sb.append(strNum); 
                return sb.toString(); 
        } 
        
        /** 
         * 返回一个定长的随机字符串(只包含数字) 
         * 
         * @param length 随机字符串长度 
         * @return 随机字符串 
         */ 
        public static String generateLuckNumString(int length) { 
                StringBuffer sb = new StringBuffer(); 
                Random random = new Random(); 
                for (int i = 0; i < length; i++) { 
                        sb.append(luckNumberChar.charAt(random.nextInt(luckNumberChar.length()))); 
                } 
                return sb.toString(); 
        } 
}