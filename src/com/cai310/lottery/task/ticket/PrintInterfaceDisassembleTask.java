package com.cai310.lottery.task.ticket;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.TaskState;
import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.ticket.LotterySupporter;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.service.info.TaskInfoDataEntityManager;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.ticket.LotterySupporterEntityManager;
import com.cai310.lottery.service.lottery.ticket.PrintInterfaceIndexEntityManager;
import com.cai310.lottery.service.lottery.ticket.TicketEntityManager;
import com.cai310.lottery.ticket.disassemble.AbstractDisassemble;
import com.cai310.lottery.ticket.disassemble.DczcDisassemble;
import com.cai310.lottery.ticket.disassemble.DltDisassemble;
import com.cai310.lottery.ticket.disassemble.El11to5Disassemble;
import com.cai310.lottery.ticket.disassemble.JclqDisassemble;
import com.cai310.lottery.ticket.disassemble.JczqDisassemble;
import com.cai310.lottery.ticket.disassemble.KlpkDisassemble;
import com.cai310.lottery.ticket.disassemble.LczcDisassemble;
import com.cai310.lottery.ticket.disassemble.PlDisassemble;
import com.cai310.lottery.ticket.disassemble.QyhDisassemble;
import com.cai310.lottery.ticket.disassemble.SczcDisassemble;
import com.cai310.lottery.ticket.disassemble.SdEl11to5Disassemble;
import com.cai310.lottery.ticket.disassemble.SevenDisassemble;
import com.cai310.lottery.ticket.disassemble.SevenstarDisassemble;
import com.cai310.lottery.ticket.disassemble.SfzcDisassemble;
import com.cai310.lottery.ticket.disassemble.SscDisassemble;
import com.cai310.lottery.ticket.disassemble.SsqDisassemble;
import com.cai310.lottery.ticket.disassemble.Tc22to5Disassemble;
import com.cai310.lottery.ticket.disassemble.Welfare3DDisassemble;
import com.cai310.lottery.ticket.disassemble.XjEl11to5Disassemble;
import com.cai310.lottery.ticket.dto.PrintInterfaceDTO;
import com.cai310.lottery.ticket.dto.TicketDTO;
import com.google.common.collect.Lists;

public class PrintInterfaceDisassembleTask {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected TaskInfoDataEntityManager taskInfoDataEntityManager;

	@Autowired
	protected PrintInterfaceIndexEntityManager printInterfaceIndexEntityManager;

	@Autowired
	protected PrintEntityManager printEntityManager;

	@Autowired
	protected TicketEntityManager ticketEntityManager;
	
	@Autowired
	protected LotterySupporterEntityManager lotterySupporterEntityManager;
	

	

	private List<TicketDTO> ticketList = null;
	
	public synchronized void runTask() throws Exception {
		logger.error("出票接口表拆单任务执行...");
			List<PrintInterface> printInterfaceList = printEntityManager.findUnDisassemblePrintInterface();
			for (PrintInterface printData : printInterfaceList) {
				try {
					logger.error(printData.getId()+"拆票开始");
						AbstractDisassemble disassemble = null;
						if(printData.getLotteryType()==Lottery.SFZC){//胜负足彩
							disassemble = new SfzcDisassemble();
						}else if(printData.getLotteryType()==Lottery.LCZC){//六场半全足彩
							disassemble = new LczcDisassemble();
						}else if(printData.getLotteryType()==Lottery.SCZC){//四场进球足彩
							disassemble = new SczcDisassemble();
						}else if (printData.getLotteryType() == Lottery.JCZQ) {// 竞彩足球复式拆单
							disassemble = new JczqDisassemble();
						}else if (printData.getLotteryType() == Lottery.JCLQ) {// 竞彩篮球复式拆单
							disassemble = new JclqDisassemble();
						}else if(printData.getLotteryType()==Lottery.EL11TO5){//
							disassemble = new El11to5Disassemble();
						}else if (printData.getLotteryType() == Lottery.DCZC) {// 
							disassemble = new DczcDisassemble();
						}else if (printData.getLotteryType() == Lottery.QYH) {// 
							disassemble = new QyhDisassemble();
						}else if (printData.getLotteryType() == Lottery.SSC) {// 
							disassemble = new SscDisassemble();
						}else if (printData.getLotteryType() == Lottery.WELFARE3D) {// 
							disassemble = new Welfare3DDisassemble();
						}else if (printData.getLotteryType() == Lottery.PL) {// 
							disassemble = new PlDisassemble();
						}else if (printData.getLotteryType() == Lottery.DLT) {// 
							disassemble = new DltDisassemble();
						}else if (printData.getLotteryType() == Lottery.SSQ) {// 
							disassemble = new SsqDisassemble();
						}else if (printData.getLotteryType() == Lottery.SEVEN) {// 
							disassemble = new SevenDisassemble();
						}else if (printData.getLotteryType() == Lottery.SDEL11TO5) {// 
							disassemble = new SdEl11to5Disassemble();
						}else if (printData.getLotteryType() == Lottery.SEVENSTAR) {// 
							disassemble = new SevenstarDisassemble();
						}else if (printData.getLotteryType() == Lottery.TC22TO5) {// 
							disassemble = new Tc22to5Disassemble();
						}else if(printData.getLotteryType()==Lottery.KLPK){
							disassemble=new KlpkDisassemble();
						}else if(printData.getLotteryType()==Lottery.XJEL11TO5){
							disassemble=new XjEl11to5Disassemble();
						}else{
							logger.error("找不到彩种所对应的出票");
							continue;
						}
						PrintInterfaceDTO printDataDTO = new PrintInterfaceDTO();
						BeanUtils.copyProperties(printDataDTO, printData);
						ticketList = disassemble.disassembleData(printDataDTO);
					int totalMoney = 0;
					//加入校验金额
					for (TicketDTO ticketDTO : ticketList) {
						totalMoney += ticketDTO.getSchemeCost();
					}
					if(totalMoney != printData.getSchemeCost()){
						logger.error("拆票金额出错："+printData.getSchemeNumber()+"原始："+printData.getSchemeCost()+"拆票："+totalMoney);
						throw new RuntimeException("拆票金额出错.");
					}
					//出票这里是单独的线程，故可以保存对象不冲突
					ticketEntityManager.saveTickets(printData, buildTicket(ticketList), null);
					logger.info(printData.getId() + "保存成功");
				} catch (Exception e) {
					logger.error("接口表序列：拆票 操作出现错误！",e);
					taskInfoDataEntityManager.updateTaskInfoData(TaskState.EXCEPTION, TaskType.DISASSEMBLE, TaskType.DISASSEMBLE.getTypeName()+"出现错误：错误信息"+e.getMessage());
				    continue;
				}
			}
			String num = "";
			if(printInterfaceList!=null){
				num = printInterfaceList.size()+"";
			}
			logger.error("出票接口表拆单任务结束..."+num);
			taskInfoDataEntityManager.updateTaskInfoData(TaskState.RUN, TaskType.DISASSEMBLE, TaskType.DISASSEMBLE.getTypeName()+"正常");
		
	}
	private List<Ticket> buildTicket(List<TicketDTO> ticketDTOList) throws IllegalAccessException, InvocationTargetException{
        List<Ticket> ticketList = Lists.newArrayList();
        Ticket ticket = null;
		for (TicketDTO ticketDTO : ticketDTOList) {
			ticket = new Ticket();
			ticket.setBetType(ticketDTO.getBetType());
			ticket.setContent(ticketDTO.getContent());
			ticket.setCreateTime(ticketDTO.getCreateTime());
			ticket.setLotteryType(ticketDTO.getLotteryType());
			ticket.setMode(ticketDTO.getMode());
			ticket.setMultiple(ticketDTO.getMultiple());
			ticket.setOfficialEndTime(ticketDTO.getOfficialEndTime());
			ticket.setPeriodNumber(ticketDTO.getPeriodNumber());
			ticket.setPrintinterfaceId(ticketDTO.getPrintinterfaceId());
			ticket.setSchemeCost(ticketDTO.getSchemeCost());
			ticket.setSchemeNumber(ticketDTO.getSchemeNumber());
			ticket.setSponsorId(ticketDTO.getSponsorId());
			ticket.setTicketIndex(ticketDTO.getTicketIndex());
			ticket.setUnits(ticketDTO.getUnits());
			ticketList.add(ticket);
		} 
		return ticketList;
	}
}
