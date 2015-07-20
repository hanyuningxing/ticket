package com.cai310.lottery.ticket.protocol.win310.utils;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.utils.DateUtil;
import com.google.common.collect.Lists;

public class Win310DczcSpVisitor extends VisitorSupport{
	//<?xml version="1.0" encoding="UTF-8"?>
	//<msg><head transcode="108" partnerid="349022" version="1.0" time="20121113165220" />
	//<body><spInfos lotteryId="SPF" issueNumber="121109">
	//<spInfo matchId="1" matchtime="201211140345" sp="2.12,2.12,17" /><spInfo matchId="2" matchtime="201211140345" sp="0,2,2" /><spInfo matchId="3" matchtime="201211140345" sp="1,0,0" /><spInfo matchId="4" matchtime="201211140345" sp="1,0,0" /><spInfo matchId="5" matchtime="201211140345" sp="0,0,1" /><spInfo matchId="6" matchtime="201211140345" sp="1,0,0" /><spInfo matchId="7" matchtime="201211140400" sp="3.45,3.44,2.37" /><spInfo matchId="8" matchtime="201211140605" sp="2.74,3.18,3.1" /><spInfo matchId="9" matchtime="201211140605" sp="6.57,3.61,1.75" /><spInfo matchId="10" matchtime="201211141800" sp="1.17,6.66,0" /><spInfo matchId="11" matchtime="201211141930" sp="1.57,0,2.75" /><spInfo matchId="12" matchtime="201211141930" sp="6.08,3.7,1.76" /><spInfo matchId="13" matchtime="201211142100" sp="1.38,6.12,8.61" /><spInfo matchId="14" matchtime="201211142245" sp="2.47,3.31,3.39" /><spInfo matchId="15" matchtime="201211142300" sp="0,0,1" /><spInfo matchId="16" matchtime="201211150000" sp="0,0,1" /><spInfo matchId="17" matchtime="201211150030" sp="2,2,0" /><spInfo matchId="18" matchtime="201211150030" sp="2.41,3.49,3.33" /><spInfo matchId="19" matchtime="201211150100" sp="2,2,0" /><spInfo matchId="20" matchtime="201211150100" sp="1.59,6.12,4.78" /><spInfo matchId="21" matchtime="201211150130" sp="1,0,0" /><spInfo matchId="22" matchtime="201211150230" sp="0,2,2" /><spInfo matchId="23" matchtime="201211150300" sp="2.25,2.25,9" /><spInfo matchId="24" matchtime="201211150300" sp="1,0,0" /><spInfo matchId="25" matchtime="201211150300" sp="1.8,2.25,0" /><spInfo matchId="26" matchtime="201211150330" sp="4.88,2.59,2.44" /><spInfo matchId="27" matchtime="201211150330" sp="0,0,1" /><spInfo matchId="28" matchtime="201211150330" sp="3,2.25,4.5" /><spInfo matchId="29" matchtime="201211150330" sp="0,0,1" /><spInfo matchId="30" matchtime="201211150345" sp="8,0,1.14" /><spInfo matchId="31" matchtime="201211150345" sp="0,0,1" /><spInfo matchId="32" matchtime="201211150345" sp="2.32,2.85,4.57" /><spInfo matchId="33" matchtime="201211150345" sp="1,0,0" /><spInfo matchId="34" matchtime="201211150350" sp="4,0,1.33" /><spInfo matchId="35" matchtime="201211150400" sp="1.68,4.82,5.04" /><spInfo matchId="36" matchtime="201211150500" sp="2.66,0,1.6" /><spInfo matchId="37" matchtime="201211150605" sp="2.37,5.6,2.49" /><spInfo matchId="38" matchtime="201211150605" sp="4.79,6.35,1.57" /><spInfo matchId="39" matchtime="201211150605" sp="1.2,0,6" /><spInfo matchId="40" matchtime="201211150605" sp="1,0,0" /><spInfo matchId="41" matchtime="201211150605" sp="1,0,0" /><spInfo matchId="42" matchtime="201211151100" sp="2.66,2.22,5.71" /><spInfo matchId="43" matchtime="201211151900" sp="0,3.25,1.44" /><spInfo matchId="44" matchtime="201211160345" sp="0,0,1" /><spInfo matchId="45" matchtime="201211160400" sp="2.69,4.16,2.57" /><spInfo matchId="46" matchtime="201211160605" sp="2.52,5.81,2.31" /><spInfo matchId="47" matchtime="201211160605" sp="1,0,0" /><spInfo matchId="48" matchtime="201211160605" sp="3.2,3.09,2.73" /><spInfo matchId="49" matchtime="201211160605" sp="1.78,4.37,4.73" /><spInfo matchId="50" matchtime="201211160605" sp="1.5,0,3" /><spInfo matchId="51" matchtime="201211160605" sp="4.71,1.37,16.5" /></spInfos></body></msg>
	private Win310DczcSp win310DczcSp;
	private List<Win310DczcSp> win310DczcSpList;
	public void visit(Attribute node){
		if("matchId".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				win310DczcSp.setMatchId(node.getText().trim());
			}
		}
		if("matchtime".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				win310DczcSp.setMatchtime(node.getText().trim());
			}
		}
		if("sp".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				win310DczcSp.setSp(node.getText().trim());
			}
		}
		if(null!=win310DczcSp&&StringUtils.isNotBlank(win310DczcSp.getSp())){
			win310DczcSpList.add(win310DczcSp);
		}
	}
	public void visit(Element node){
		if("spInfo".equals(node.getName())){
			if(null==win310DczcSpList)win310DczcSpList = Lists.newArrayList();
			win310DczcSp = new Win310DczcSp();
		}
	}
	public Win310DczcSp getWin310DczcSp() {
		return win310DczcSp;
	}
	public void setWin310DczcSp(Win310DczcSp win310DczcSp) {
		this.win310DczcSp = win310DczcSp;
	}
	public List<Win310DczcSp> getWin310DczcSpList() {
		return win310DczcSpList;
	}
	public void setWin310DczcSpList(List<Win310DczcSp> win310DczcSpList) {
		this.win310DczcSpList = win310DczcSpList;
	}
	

}
