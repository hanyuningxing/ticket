package com.cai310.utils;

public final class MathUtils {
	/**
	 * 计算组合数
	 * 
	 * @param r 取出元素个数
	 * @param n 可选个数
	 * @return 组合数
	 */
	public static int comp(int r, int n) {
		long C = 1;
		for (int i = n - r + 1; i <= n; i++) {
			C = C * i;
		}
		for (int i = 2; i <= r; i++) {
			C = C / i;
		}
		return (int) C;
	}

	/**
	 * 高效的组合算法
	 * 
	 * @param n 可选的数目
	 * @param m 取的数目
	 * @param callBack 每取出一个组合时的回调函数
	 */
	public static void efficientComb(int n, int m, CombCallBack callBack) {
		if (m > n) {
			return;
		}

		boolean[] bs = new boolean[n];
		for (int i = 0; i < m; i++) {
			bs[i] = true;
		}
		if (m == n) {
			callBack.callback(bs, m);
			return;
		}
		for (int i = m; i < n; i++) {
			bs[i] = false;
		}
		if (m == 0) {
			callBack.callback(bs, m);
			return;
		}

		boolean flag = true;
		boolean tempFlag = false;
		int pos = 0;
		int sum = 0;
		// 首先找到第一个10组合，然后变成01，同时将左边所有的1移动到数组的最左边
		do {
			sum = 0;
			pos = 0;
			tempFlag = true;
			boolean stop = callBack.callback(bs, m);
			if (stop)
				return;

			// 找到第一个10组合，然后变成01
			for (int i = 0; i < n - 1; i++) {
				if (bs[i] == true && bs[i + 1] == false) {
					bs[i] = false;
					bs[i + 1] = true;
					pos = i;
					break;
				}
			}

			// 将左边的1全部移动到数组的最左边
			for (int i = 0; i < pos; i++) {
				if (bs[i] == true) {
					sum++;
				}
			}
			for (int i = 0; i < pos; i++) {
				if (i < sum) {
					bs[i] = true;
				} else {
					bs[i] = false;
				}
			}

			// 检查是否所有的1都移动到了最右边
			for (int i = n - m; i < n; i++) {
				if (bs[i] == false) {
					tempFlag = false;
					break;
				}
			}
			if (tempFlag == false) {
				flag = true;
			} else {
				flag = false;
			}

		} while (flag);
		callBack.callback(bs, m);
	}

	/**
	 * 扩展的组合算法，支持设胆
	 * 
	 * @param needs 所需要的数目
	 * @param danSize 胆码的数目
	 * @param unDanSize 拖码的数目
	 * @param callBack 回调函数
	 * @see com.cai310.utils.ExtensionCombCallBack#run(boolean[], int, boolean[],
	 *      int)
	 */
	public static void efficientCombExtension(final int needs, final int danSize, final int unDanSize,
			final ExtensionCombCallBack callBack) {
		efficientCombExtension(needs, danSize, -1, -1, unDanSize, callBack);
	}

	/**
	 * 扩展的组合算法，支持模糊设胆
	 * 
	 * @param needs 所需要的数目
	 * @param danSize 胆码的数目
	 * @param danMin 模糊设胆，胆至少选几个
	 * @param danMax 模糊设胆，胆至多选几个
	 * @param unDanSize 拖码的数目
	 * @param callBack 回调函数
	 * @see com.cai310.utils.ExtensionCombCallBack#run(boolean[], int, boolean[],
	 *      int)
	 */
	public static void efficientCombExtension(final int needs, final int danSize, int danMin, int danMax,
			final int unDanSize, final ExtensionCombCallBack callBack) {
		// 检查模糊设胆，兼容非模糊设胆
		if (danMin < 0)
			danMin = danSize;
		if (danMax < 0 || danMax > danSize)
			danMax = danSize;

		if (unDanSize + danMax >= needs) {
			for (int danNum = danMin; danNum <= danMax; danNum++) {
				final int alsoNeeds = needs - danNum;
				if (unDanSize >= alsoNeeds) {
					// 先从胆码中选择指定的数目
					efficientComb(danSize, danNum, new CombCallBack() {

						public boolean callback(final boolean[] comb1, final int m1) {
							// 再从拖胆中选取还需要的数目
							efficientComb(unDanSize, alsoNeeds, new CombCallBack() {

								public boolean callback(final boolean[] comb2, int m2) {
									// 已完整拆了一个组合，调用回调函数进行处理，
									return callBack.run(comb1, m1, comb2, m2);
								}

							});

							return false;
						}

					});
				}
			}
		}
	}

}
