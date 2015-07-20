package com.cai310.lottery.support.shrink;

/**
 * 奇偶
 * 
 */
public enum OddEvenType {
	ODD {
		@Override
		public boolean check(int num) {
			return num % 2 != 0;
		}
	},
	EVEN {
		@Override
		public boolean check(int num) {
			return num % 2 == 0;
		}
	};

	public abstract boolean check(int num);
}
