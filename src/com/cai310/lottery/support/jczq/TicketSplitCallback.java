package com.cai310.lottery.support.jczq;

import java.util.List;


public interface TicketSplitCallback {

	void handle(List<JczqMatchItem> matchItemList, PassType passType, int multiple);
}
