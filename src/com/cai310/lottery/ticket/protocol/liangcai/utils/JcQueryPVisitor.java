package com.cai310.lottery.ticket.protocol.liangcai.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import sun.misc.BASE64Decoder; 
/**
 * <?xml version="1.0" encoding="gb2312"?>
<ActionResult>
<xMsgID>1367563288910</xMsgID>
<xCode>0</xCode>
<xMessage>成功</xMessage>
<xSign>04ecd0f5800bea20c87df13d6b49280c</xSign>
<xValue><Project id="144hongbo" chipmul="1" chipcount="1" chipmoney="2">
			<bill id="20130521245398694995" billtime="2013-05-03 14:25:26" issue="20130503" playid="SPF" maxbonu="54.90" ispass="1" passway="3*1" multi="1">
				<match id="130503011">3=3.450</match>
				<match id="130503012">1=3.650</match>
				<match id="130503013">0=2.180</match>
			</bill>
	    </Project>
</xValue>
</ActionResult>
 * @author mac
 *
 */
public class JcQueryPVisitor extends QueryPVisitor{
	@Override
	public void visit(Element node){
		if("xCode".equals(node.getName())){
			///是否成功
			if(StringUtils.isNotBlank(node.getText())){
				if(String.valueOf("0").equals(node.getText().trim())){
					///成功
					isSuccess=Boolean.TRUE;
				}
			}
		}
		if("xValue".equals(node.getName())){
			if(StringUtils.isNotBlank(node.asXML())){
				try {
					Document doc=DocumentHelper.parseText(node.asXML());
					Element root = doc.getRootElement();
					Element project = root.element("Project");
					String orderId = project.attributeValue("id");
					String betCost = project.attributeValue("chipmoney");
					this.orderId = orderId.trim();
					this.betCost = betCost.trim();
					Element bill = project.element("bill");
					String billId = bill.attributeValue("id");
					String multi = bill.attributeValue("multi");
					String winCost = bill.attributeValue("maxbonu");
					String playid = bill.attributeValue("playid");
					String passway = bill.attributeValue("passway");
					this.winCost = winCost.trim();
					StringBuffer sb = new StringBuffer();
					//20024733484880001427_6.19_1_RFSF|130502301=-1.5_0(1.820),130502302=-1.5_3(1.700)|2*1
					for (Iterator i = bill.elementIterator("match"); i.hasNext();) {
						if("HH".equals(playid.trim())){

							Element matchElement = (Element) i.next();
							String matchId = matchElement.attributeValue("id");
							String playType = matchElement.attributeValue("playid");
							String odds = matchElement.getText().replaceAll("=", "(");
							String[] oddArr = odds.split("\\|");
							StringBuffer odd_sb = new StringBuffer();
							for (String odd : oddArr) {
								odd_sb.append(odd).append(")").append("/");
							}
							odd_sb = odd_sb.delete(odd_sb.length()-1, odd_sb.length());
							sb.append(playType).append(">").append(matchId+"=").append(odd_sb).append(",");
						}else{
							Element matchElement = (Element) i.next();
							String matchId = matchElement.attributeValue("id");
							String odds = matchElement.getText().replaceAll("=", "(");
							String[] oddArr = odds.split("\\|");
							StringBuffer odd_sb = new StringBuffer();
							for (String odd : oddArr) {
								odd_sb.append(odd).append(")").append("/");
							}
							odd_sb = odd_sb.delete(odd_sb.length()-1, odd_sb.length());
							sb.append(matchId+"=").append(odd_sb).append(",");
						}
					}
					sb=sb.delete(sb.length()-1, sb.length());
					String odds = billId+"_"+winCost+"_"+multi+"_"+playid+"|"+sb.toString()+"|"+passway;
					this.awards =  odds.trim();
				} catch (DocumentException e) {
					System.out.print("出票赔率返回失败"+e.getMessage());
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
