package com.cai310.lottery.ticket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.ticket.protocol.bd.utils.BeiDanUtil;
import com.cai310.lottery.ticket.protocol.bd.utils.DczcBeiDanUtil;
import com.cai310.lottery.ticket.protocol.bd.utils.ZLibUtils;
import com.cai310.lottery.utils.Base64Util;

public class BDMatchSpServlet extends HttpServlet {

	private static final long serialVersionUID = 2933102939331701941L;

	protected String type;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void check(HttpServletRequest request) {
		this.type=request.getParameter("type");
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		check(request);
		String jsonString ="";
		try{
			BeiDanUtil cpUtil = new DczcBeiDanUtil();
			jsonString = cpUtil.getMatchSp(type);
			System.out.println("-----sp---jsonString------"+jsonString);
		 }catch(Exception e){
			 logger.error(e.getMessage(), e);
		 }
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.getWriter().write(jsonString);
		response.getWriter().flush();
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String jsonString ="";
		try{
			BeiDanUtil cpUtil = new DczcBeiDanUtil();
			jsonString = cpUtil.getMatchSp("1");
			System.out.println(jsonString);
		 }catch(Exception e){
			 
		 }
		System.out.println(new String(ZLibUtils.decompress(Base64Util.decode("eJzT0QGDOp3BSQMAclUhaw=="))));
		
	}
	
}
