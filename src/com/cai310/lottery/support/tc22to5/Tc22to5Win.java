package com.cai310.lottery.support.tc22to5;


public class Tc22to5Win {

	public static Tc22to5WinUnit calcWinUnit(String result, String betUnits, int multiple, boolean isPoly) {

		int betHit = 0; // 球命中
		int betUnhit = 0; // 球未命中

		int betSpecileHit = 0; // 胆码命中
		int betSpecileUnHit = 0;// 胆码未命中
		int betCommonHit = 0; // 拖码红球命中
		int betCommonUnhit = 0; // 拖码红球未命中

		String[] results = result.split(",");

		Tc22to5WinUnit winUnit = new Tc22to5WinUnit();

		/* 判断是否含有胆码 */
		if (betUnits.split("\\)").length == 1) {
			String[] bets = null;
			if (isPoly) {
				bets = betUnits.split(",");
			} else {
				bets = betUnits.split(" ");
			}

			for (int i = 0; i < bets.length; i++) {
				for (int j = 0; j < results.length; j++) {
					if (bets[i].equals(results[j])) {
						betHit++;
					}
				}
			}

			// 计算未命中个数
			betUnhit = bets.length - betHit;			

			// 一等奖 5
			winUnit.setFirstWinUnits(multiple * Tc22to5Utils.calcWinUnits(5, betHit, betUnhit));

			// 二等奖 4
			winUnit.setSecondWinUnits(multiple * Tc22to5Utils.calcWinUnits(4, betHit, betUnhit));

			// 三等奖 3
			winUnit.setThirdWinUnits(multiple * Tc22to5Utils.calcWinUnits(3, betHit, betUnhit));

		} else if (betUnits.split("\\)").length > 1) {

			// 胆码格式： (1,2),3,4,5,6,7 8,9

			String[] betSpecileball = betUnits.split("\\)")[0].split("\\(")[1].split(",");
			String[] betCommon_temp = betUnits.split("\\)")[1].split(","); // 第一个元素为"";
			String[] betCommon = new String[betCommon_temp.length - 1];
			for (int i = 1; i < betCommon_temp.length; i++) {
				betCommon[i - 1] = betCommon_temp[i];
			}

			// 计算胆码球命中个数
			for (int i = 0; i < betSpecileball.length; i++) {
				for (int j = 0; j < results.length; j++) {
					if (betSpecileball[i].equals(results[j]))
						betSpecileHit++;
				}
			}

			// 计算胆码球未命中个数
			betSpecileUnHit = betSpecileball.length - betSpecileHit;

			for (int i = 0; i < betCommon.length; i++) {
				for (int j = 0; j < results.length; j++) {
					if (betCommon[i].equals(results[j]))
						betCommonHit++;
				}
			}

			// 计算拖码球未命中个数
			betCommonUnhit = betCommon.length - betCommonHit;


			// 一等奖 
			if (betSpecileHit <= 5) {
				winUnit.setFirstWinUnits(multiple
						* Tc22to5Utils.calcWinUnits(5, betSpecileHit, betSpecileUnHit, betCommonHit, betCommonUnhit));
			} else {
				winUnit.setFirstWinUnits(0);
			}

			// 二等奖 
			if (betSpecileHit <= 4) {
				winUnit.setSecondWinUnits(multiple
						* Tc22to5Utils.calcWinUnits(4, betSpecileHit, betSpecileUnHit, betCommonHit, betCommonUnhit));
			} else {
				winUnit.setSecondWinUnits(0);
			}

			// 三等奖 
			if (betSpecileHit <= 3) {
				winUnit.setThirdWinUnits(multiple
						* Tc22to5Utils.calcWinUnits(3, betSpecileHit, betSpecileUnHit, betCommonHit, betCommonUnhit));
			} else {
				winUnit.setThirdWinUnits(0);
			}

		}

		return winUnit;
	}

	public static void main(String[] args) {
		System.out.println(calcWinUnit("02,13,14,15,16", "01 02 13 14 15", 2, false));

		System.out.println(calcWinUnit("02,13,14,15,16","(02,13),14,15,16,17",1,true));
		
		System.out.println(calcWinUnit("01,05,15,17,18","01,05,15,17,18,03,12,16,24",1,true));

	}
}
