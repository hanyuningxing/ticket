package com.cai310.lottery.ticket;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.service.lottery.ticket.TicketEntityManager;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.lottery.ticket.protocol.bd.utils.ZLibUtils;
import com.cai310.lottery.utils.Base64Util;
import com.cai310.spring.SpringContextHolder;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;

public class TicketServlet extends HttpServlet {

	protected String orderId;
	
	protected String orderResults;
	
	
	@Autowired
	private static final long serialVersionUID = 8897416698107668179L;
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void check(HttpServletRequest request) {
		this.orderId=request.getParameter("orderId");
		this.orderResults=request.getParameter("orderResults");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int returnValue = 0;
		try{
			InputStream is = request.getInputStream();
			String text = IOUtils.toString(is);
			System.out.println("----------------receive notice----------------------"+text);
			if(StringUtils.isBlank(text)){
				returnValue = 1;//失败
				logger.error("---------------------接收到的通知信息为空");
			}else{
				orderId = JsonUtil.getStringValueByJsonStr(text,"orderId");
				orderResults = JsonUtil.getStringValueByJsonStr(text,"orderResults");
				TicketEntityManager ticketEntityManager = SpringContextHolder.getBean("ticketEntityManager");
				if(StringUtils.isNotBlank(orderId)){
					Ticket ticket = ticketEntityManager.getTicket(Long.valueOf(orderId));
					System.out.println("orderResults************"+orderResults);
					String contents = new String(ZLibUtils.decompress(Base64Util.decode(orderResults)));
					System.out.println("出票结果通知内容************"+contents);
					String[] mes = contents.split("\\&");
					
					if(null!=mes[4].trim()&&"0".equals(mes[4].trim())){
						logger.debug("{"+ticket.getId()+"}出票成功，返回代码："+mes[4]);
						ticket.setStateCode("1");
						ticket.setTicketSupporter(TicketSupporter.BEIDAN);
						ticket.setStateModifyTime(new Date());
						ticket.setRemoteTicketId(mes[2]);
						ticket.setTicketTime(StringUtils.isBlank(mes[3])?null:DateUtil.strToDate(mes[3], "yyyy/MM/dd hh:mm:ss"));
						ticketEntityManager.saveTicket(ticket);
					}else if(!"1002".equals(mes[4].trim())&&!"1003".equals(mes[4].trim())&&!"1005".equals(mes[4].trim())&&!"1012".equals(mes[4].trim())){
						///撤单
						logger.warn("{"+ticket.getId()+"}出票失败撤单，返回代码："+mes[4].trim());
						ticket.setStateCode("2");
						ticket.setTicketTime(StringUtils.isBlank(mes[3])?null:DateUtil.strToDate(mes[3], "yyyy/MM/dd hh:mm:ss"));
						ticket.setStateModifyTime(new Date());
						ticketEntityManager.saveTicket(ticket);
					}
				}else{
					returnValue = 1;//失败
					logger.error(orderId+"订单为空");
				}
			}
		 }catch(Exception e){
			 returnValue = 1;//失败
			 logger.error(e.getMessage(), e);
		 }
		String jsonString ="{\"code\":"+returnValue+"}";
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		response.getWriter().write(jsonString);
		response.getWriter().flush();
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String is = "253738&ab38f686-5da2-4492-b7b0-18b3303c1287&001851-******-******-382465&2015/12/3 18:06:25&0";
		String base64 = Base64Util.encode(ZLibUtils.compress(is.getBytes()));
		System.out.println(base64);
		String postData = "orderId=1&orderResults="+URLEncoder.encode("eJw1xUEKgDAMBMCveMpBCCa7TRv7m0bx/08QBOcyCA6mrGI+PbvGvaCtndAaZepZpPFy5BAzz3DdP39MtB4C8zgcBzfPaX0ixF63ghNU");
		System.out.println(postData);
//		String result = BeiDanUtil.Utf8HttpClientUtils("http://localhost:8080/ticket/ticketNotice",postData);
		
//		String contents = new String(ZLibUtils.decompress(Base64Util.decode("eJw1xUEKgDAMBMCveMpBCCa7TRv7m0bx/08QBOcyCA6mrGI+PbvGvaCtndAaZepZpPFy5BAzz3DdP39MtB4C8zgcBzfPaX0ixF63ghNU")));
//		System.out.println("出票结果通知内容************"+contents);
	}
	
}
