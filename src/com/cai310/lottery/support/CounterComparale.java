package com.cai310.lottery.support;

import java.util.Comparator;

public class CounterComparale implements Comparator<OneDayCounter>{

	public int compare(OneDayCounter o1, OneDayCounter o2) {
		return (int)(o1.getDay().getTime() - o2.getDay().getTime());
	}

}
