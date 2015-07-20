package com.cai310.lottery.support.shrink;

/**
 * 旋转矩阵类型
 * 
 */
public enum SpinmatrixType {
	/** 中5保4 */
	ST5_4(5, 4, 7, 18),

	/** 中6保5 */
	ST6_5(6, 5, 7, 18);

	private final int hit;
	private final int keep;
	private final int min;
	private final int max;
	private final String key;
	private final String text;

	private SpinmatrixType(int hit, int keep, int min, int max) {
		this.hit = hit;
		this.keep = keep;
		this.min = min;
		this.max = max;
		this.key = String.format("%s_%s", hit, keep);
		this.text = String.format("中s保%s", hit, keep);
	}

	public int getHit() {
		return hit;
	}

	public int getKeep() {
		return keep;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public String getKey() {
		return key;
	}

	public String getText() {
		return text;
	}

}
