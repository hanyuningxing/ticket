package com.cai310.lottery.ticket.protocol.liangcai.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import sun.misc.BASE64Decoder; 
public class QueryPVisitor extends VisitorSupport{
	 String orderId;
	 String betCost;
	 String winCost;
	 String awards;
	 Boolean isSuccess=Boolean.FALSE;
	public void visit(Attribute node){
		
	}
	public void visit(Element node){
		if("xCode".equals(node.getName())){
			///是否成功
			if(StringUtils.isNotBlank(node.getText())){
				if(String.valueOf("1").equals(node.getText().trim())){
					///成功
					isSuccess=Boolean.TRUE;
				}
			}
		}
		if("xValue".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				String[] arr = node.getText().split("_");
				this.orderId = arr[0].trim();
				this.betCost = arr[1].trim();
				this.winCost = arr[2].trim();
				if(arr.length==4){
					
					this.awards =  arr[3].trim();
					BASE64Decoder decoder = new BASE64Decoder(); 
					try {
						byte[] b =decoder.decodeBuffer(this.awards);
						b = decompress(b);
						this.awards = new String(b);
					} catch (IOException e) {
						System.out.println("sp解析异常"+e.getMessage());
					}
				}
			}
		}
	}
	/**  
     * 解压缩  
     *   
     * @param data  
     *            待压缩的数据  
     * @return byte[] 解压缩后的数据  
     */  
    public static byte[] decompress(byte[] data) {   
        byte[] output = new byte[0];   
  
        Inflater decompresser = new Inflater();   
        decompresser.reset();   
        decompresser.setInput(data);   
  
        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);   
        try {   
            byte[] buf = new byte[1024];   
            while (!decompresser.finished()) {   
                int i = decompresser.inflate(buf);   
                o.write(buf, 0, i);   
            }   
            output = o.toByteArray();   
        } catch (Exception e) {   
            output = data;   
            e.printStackTrace();   
        } finally {   
            try {   
                o.close();   
            } catch (IOException e) {   
                e.printStackTrace();   
            }   
        }   
  
        decompresser.end();   
        return output;   
    }   
  
    /**  
     * 解压缩  
     *   
     * @param is  
     *            输入流  
     * @return byte[] 解压缩后的数据  
     */  
    public static byte[] decompress(InputStream is) {   
        InflaterInputStream iis = new InflaterInputStream(is);   
        ByteArrayOutputStream o = new ByteArrayOutputStream(1024);   
        try {   
            int i = 1024;   
            byte[] buf = new byte[i];   
  
            while ((i = iis.read(buf, 0, i)) > 0) {   
                o.write(buf, 0, i);   
            }   
  
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
        return o.toByteArray();   
    }   
	/**
	 * @return the isSuccess
	 */
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBetCost() {
		return betCost;
	}
	public void setBetCost(String betCost) {
		this.betCost = betCost;
	}
	public String getWinCost() {
		return winCost;
	}
	public void setWinCost(String winCost) {
		this.winCost = winCost;
	}
	public String getAwards() {
		return awards;
	}
	public void setAwards(String awards) {
		this.awards = awards;
	}

}
