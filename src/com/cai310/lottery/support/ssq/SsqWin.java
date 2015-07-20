package com.cai310.lottery.support.ssq;


public class SsqWin {

	public static SsqWinUnit calcWinUnit(String result, String betUnits, int multiple, boolean isPoly) {

		int T = 0; // 中间变量 计算中奖注数使用

		int redHit = 0; // 红球命中
		int redUnhit = 0; // 红球未命中

		int redSpecileHit = 0; // 胆码红球命中
		int redSpecileUnHit = 0;// 胆码红球未命中
		int redCommonHit = 0; // 拖码红球命中
		int redCommonUnhit = 0; // 拖码红球未命中

		int blueHit = 0; // 蓝球命中
		int blueUnhit = 0; // 蓝球未命中

		String[] temp = result.split(" ");

		// result 中的红蓝球
		String[] red_result = temp[0].split(",");
		String[] blue_result = temp[1].split(",");

		// 拆分每注的红蓝球
		String[] bet_temp = betUnits.split(" ");

		SsqWinUnit winUnit = new SsqWinUnit();

		/* 判断红球是否含有胆码 */
		if (bet_temp[0].split("\\)").length == 1) {

			String[] redball = null;
			String[] blueball = null;

			if (isPoly) {
				// 无胆码复式红蓝球
				redball = bet_temp[0].split(",");
				blueball = bet_temp[1].split(",");
			} else {
				// 单式红蓝球
				redball = new String[6];
				for (int i = 0; i < redball.length; i++) {
					redball[i] = bet_temp[i];
				}
				blueball = new String[] { bet_temp[6] };
			}

			// 计算红球命中个数
			for (int i = 0; i < redball.length; i++) {
				for (int j = 0; j < red_result.length; j++) {

					if (redball[i].equals(red_result[j])) {
						redHit++;
					}
				}
			}

			// 计算红球未命中个数
			redUnhit = redball.length - redHit;

			// 计算蓝球命中个数
			for (int i = 0; i < blueball.length; i++) {
				for (int j = 0; j < blue_result.length; j++) {
					if (blueball[i].equals(blue_result[j])) {
						blueHit++;
					}
				}
			}

			// 计算蓝球未命中个数
			blueUnhit = blueball.length - blueHit;

			// 一等奖 6+1
			winUnit.setFirstWinUnits(multiple * SsqUtils.calcWinUnits(6, 1, redHit, redUnhit, blueHit, blueUnhit));

			// 二等奖 6+0
			winUnit.setSecondWinUnits(multiple * SsqUtils.calcWinUnits(6, 0, redHit, redUnhit, blueHit, blueUnhit));

			// 三等奖 5+1
			winUnit.setThirdWinUnits(multiple * SsqUtils.calcWinUnits(5, 1, redHit, redUnhit, blueHit, blueUnhit));

			// 四等奖 5+0
			winUnit.setFourthWinUnits(multiple * SsqUtils.calcWinUnits(5, 0, redHit, redUnhit, blueHit, blueUnhit));

			T = winUnit.getFourthWinUnits().intValue();

			// 四等奖 4+1
			winUnit.setFourthWinUnits(multiple * SsqUtils.calcWinUnits(4, 1, redHit, redUnhit, blueHit, blueUnhit) + T);

			T = 0;

			// 五等奖 4+0
			winUnit.setFifthWinUnits(multiple * SsqUtils.calcWinUnits(4, 0, redHit, redUnhit, blueHit, blueUnhit));

			T = winUnit.getFifthWinUnits().intValue();

			// 五等奖 3+1
			winUnit.setFifthWinUnits(multiple * SsqUtils.calcWinUnits(3, 1, redHit, redUnhit, blueHit, blueUnhit) + T);

			T = 0;

			// 六等奖 2+1
			winUnit.setSixthWinUnits(multiple * SsqUtils.calcWinUnits(2, 1, redHit, redUnhit, blueHit, blueUnhit));

			T = winUnit.getSixthWinUnits().intValue();

			// 六等奖 1+1
			winUnit.setSixthWinUnits(multiple * SsqUtils.calcWinUnits(1, 1, redHit, redUnhit, blueHit, blueUnhit) + T);

			T = winUnit.getSixthWinUnits().intValue();

			// 六等奖 0+1
			winUnit.setSixthWinUnits(multiple * SsqUtils.calcWinUnits(0, 1, redHit, redUnhit, blueHit, blueUnhit) + T);

		} else if (bet_temp[0].split("\\)").length > 1) {

			// 胆码格式： (1,2),3,4,5,6,7 8,9

			int P = 0; // 中间变量 计算中奖注数使用

			String[] redSpecileball = bet_temp[0].split("\\)")[0].split("\\(")[1].split(",");
			String[] redCommon_temp = bet_temp[0].split("\\)")[1].split(","); // 第一个元素为"";
			String[] redCommon = new String[redCommon_temp.length - 1];
			for (int i = 1; i < redCommon_temp.length; i++) {
				redCommon[i - 1] = redCommon_temp[i];
			}
			String[] blueball = bet_temp[1].split(",");

			// 计算胆码红球命中个数
			for (int i = 0; i < redSpecileball.length; i++) {
				for (int j = 0; j < red_result.length; j++) {

					if (redSpecileball[i].equals(red_result[j]))
						redSpecileHit++;
				}
			}

			// 计算胆码红球未命中个数
			redSpecileUnHit = redSpecileball.length - redSpecileHit;

			for (int i = 0; i < redCommon.length; i++) {
				for (int j = 0; j < red_result.length; j++) {

					if (redCommon[i].equals(red_result[j]))
						redCommonHit++;
				}
			}

			// 计算拖码红球未命中个数
			redCommonUnhit = redCommon.length - redCommonHit;

			// 计算蓝球命中个数
			for (int i = 0; i < blueball.length; i++) {
				for (int j = 0; j < blue_result.length; j++) {

					if (blueball[i].equals(blue_result[j]))
						blueHit++;

				}
			}

			// 计算蓝球未命中个数
			blueUnhit = blueball.length - blueHit;

			// 一等奖 6+1
			if (redSpecileHit <= 6) {
				winUnit.setFirstWinUnits(multiple
						* SsqUtils.calcWinUnits(6, 1, redSpecileHit, redSpecileUnHit, redCommonHit, redCommonUnhit,
								blueHit, blueUnhit));
			} else {
				winUnit.setFirstWinUnits(0);
			}

			// 二等奖 6+0
			if (redSpecileHit <= 6) {
				winUnit.setSecondWinUnits(multiple
						* SsqUtils.calcWinUnits(6, 0, redSpecileHit, redSpecileUnHit, redCommonHit, redCommonUnhit,
								blueHit, blueUnhit));
			} else {
				winUnit.setSecondWinUnits(0);
			}

			// 三等奖 5+1
			if (redSpecileHit <= 5) {
				winUnit.setThirdWinUnits(multiple
						* SsqUtils.calcWinUnits(5, 1, redSpecileHit, redSpecileUnHit, redCommonHit, redCommonUnhit,
								blueHit, blueUnhit));
			} else {
				winUnit.setThirdWinUnits(0);
			}

			// 四等奖 5+0
			if (redSpecileHit <= 5) {
				winUnit.setFourthWinUnits(multiple
						* SsqUtils.calcWinUnits(5, 0, redSpecileHit, redSpecileUnHit, redCommonHit, redCommonUnhit,
								blueHit, blueUnhit));

				P = winUnit.getFourthWinUnits().intValue();
			} else {
				winUnit.setFourthWinUnits(0);
			}

			// 四等奖 4+1
			if (redSpecileHit <= 4) {
				winUnit.setFourthWinUnits(multiple
						* SsqUtils.calcWinUnits(4, 1, redSpecileHit, redSpecileUnHit, redCommonHit, redCommonUnhit,
								blueHit, blueUnhit) + P);
			} else {
				winUnit.setFourthWinUnits(P);
			}

			P = 0;

			// 五等奖 4+0
			if (redSpecileHit <= 4) {
				winUnit.setFifthWinUnits(multiple
						* SsqUtils.calcWinUnits(4, 0, redSpecileHit, redSpecileUnHit, redCommonHit, redCommonUnhit,
								blueHit, blueUnhit));

				P = winUnit.getFifthWinUnits().intValue();
			} else {
				winUnit.setFifthWinUnits(0);
			}

			// 五等奖 3+1
			if (redSpecileHit <= 3) {
				winUnit.setFifthWinUnits(multiple
						* SsqUtils.calcWinUnits(3, 1, redSpecileHit, redSpecileUnHit, redCommonHit, redCommonUnhit,
								blueHit, blueUnhit) + P);
			} else {
				winUnit.setFifthWinUnits(P);
			}

			P = 0;

			// 六等奖 2+1
			if (redSpecileHit <= 2) {
				winUnit.setSixthWinUnits(multiple
						* SsqUtils.calcWinUnits(2, 1, redSpecileHit, redSpecileUnHit, redCommonHit, redCommonUnhit,
								blueHit, blueUnhit));

				P = winUnit.getSixthWinUnits().intValue();
			} else {
				winUnit.setSixthWinUnits(0);
			}

			// 六等奖 1+1
			if (redSpecileHit <= 1) {
				winUnit.setSixthWinUnits(multiple
						* SsqUtils.calcWinUnits(1, 1, redSpecileHit, redSpecileUnHit, redCommonHit, redCommonUnhit,
								blueHit, blueUnhit) + P);

				P = winUnit.getSixthWinUnits().intValue();
			} else {
				winUnit.setSixthWinUnits(P);
			}

			// 六等奖 0+1
			if (redSpecileHit <= 0) {
				winUnit.setSixthWinUnits(multiple
						* SsqUtils.calcWinUnits(0, 1, redSpecileHit, redSpecileUnHit, redCommonHit, redCommonUnhit,
								blueHit, blueUnhit) + P);
			} else {
				winUnit.setSixthWinUnits(P);
			}
		}

		return winUnit;
	}

	public static void main(String[] args) {
		System.out.println(calcWinUnit("02,13,21,28,29,31 09", "01 09 14 28 29 31 09", 2, false));

		// System.out.println(calcWinUnit("01,05,15,17,27,29 02","(01,05,15,17,27),04,07,29 02",1,true));
		//
		// System.out.println(calcWinUnit("01,05,15,17,27,29 02","(07,09,27,28,29),03,12,16,24 09",1,true));

	}
}
