package com.cai310.lottery.ticket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.ticket.protocol.bd.utils.BeiDanUtil;
import com.cai310.lottery.ticket.protocol.bd.utils.DczcBeiDanUtil;

public class BDMatchServlet extends HttpServlet {

	private static final long serialVersionUID = 2933102939331701941L;

	protected String type;
	
	protected String etag;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void check(HttpServletRequest request) {
		this.type=request.getParameter("type");
		this.etag=request.getParameter("etag");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		check(request);
		String[] contents = new String[3];
		String statusCode =null;
		Ticket ticket = new Ticket();
		try{
			BeiDanUtil cpUtil = new DczcBeiDanUtil();
			contents = cpUtil.getMatch(type, etag,ticket);
			statusCode = contents[0];
			System.out.println("------------statusCode-----------"+statusCode+"-------etag-------"+contents[1]+"--periodNumber--"+ticket.getPeriodNumber());
		 }catch(Exception e){
			 logger.error(e.getMessage(), e);
		 }
		String jsonString =contents[2];
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("etag", contents[1]);
		response.setDateHeader("Expires", 0);
		response.setStatus(statusCode==null?404:Integer.valueOf(statusCode));
		response.getWriter().write("{ periodNumber:"+ticket.getPeriodNumber()+",match:"+jsonString+"}");
		response.getWriter().flush();
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String[] contents = new String[3];
		try{
			BeiDanUtil cpUtil = new DczcBeiDanUtil();
			Ticket ticket = new Ticket();
			contents = cpUtil.getMatch("0", "",ticket);
			String statusCode = contents[0];
			System.out.println("------------statusCode-----------"+statusCode);
		 }catch(Exception e){
		 }
		String jsonString =contents[2];
		System.out.println(jsonString);
	}
	
}
