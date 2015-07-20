package com.cai310.lottery.support.jclq;

import java.util.List;


public interface TicketSplitCallback {

	void handle(List<JclqMatchItem> matchItemList, PassType passType, int multiple);
}
