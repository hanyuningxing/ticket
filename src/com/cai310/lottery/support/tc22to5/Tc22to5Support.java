package com.cai310.lottery.support.tc22to5;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.exception.DataException;

public class Tc22to5Support {
	
	/** 三等奖奖金 */
	public static final int SecondPrize = 50;
	
	/** 四等奖奖金 */
	public static final int ThirdPrize = 5;


	public static String getPolyBetDescHtml(String betDesc, String result) throws DataException {

		if (StringUtils.isNotBlank(betDesc)) {
			StringBuffer sb = new StringBuffer(50);

			String[] arr = null;
			arr = betDesc.split(";");// 分隔成多个投注内容
			StringBuffer lineSb = new StringBuffer();

			for (String str : arr) {

				if (str.split("\\)").length == 1) {
					// 复式投注内容格式：注数_内容
					if (str.split("_").length != 2) {
						throw new DataException("方案内容格式不正确！");
					}
					lineSb.setLength(0);

					// 内容格式：1,2,3,4,5,6
					String BET = str.split("_")[1];
					String[] bet = BET.split(",");

					if (bet.length < 5) {
						throw new DataException("方案内容格式不正确！");
					}

					for (int i = 0; i < bet.length; i++) {
						if (!(Integer.parseInt(bet[i]) > 0 && Integer.parseInt(bet[i]) < 23)) {
							throw new DataException("方案内容格式不正确！");
						}
					}

					lineSb.append(toHtml(BET, result).replaceAll(",", " "));					
					lineSb.append(";");
					sb.append(lineSb.toString());

				} else if (str.split("\\)").length > 1) {

					// 胆码复式投注内容: 注数_(1,2,3),4,5,6,7
					if (str.split("_").length != 2) {
						throw new DataException("方案内容格式不正确！");
					}

					lineSb.setLength(0);

					String BET = str.split("_")[1];
					String[] betSpecile = BET.split("\\)")[0].split("\\(")[1].split(",");
					String[] betCommon = BET.split("\\)")[1].split(","); // betCommon[0]为空格

					if ((betSpecile.length + betCommon.length) < 5) {
						throw new DataException("方案内容格式不正确！");
					}

					for (int i = 0; i < betSpecile.length; i++) {
						if (!(Integer.parseInt(betSpecile[i]) > 0 && Integer.parseInt(betSpecile[i]) < 22)) {
							throw new DataException("方案内容格式不正确！");
						}
					}

					for (int i = 1; i < betCommon.length; i++) {
						if (!(Integer.parseInt(betCommon[i]) > 0 && Integer.parseInt(betCommon[i]) < 22)) {
							throw new DataException("方案内容格式不正确！");
						}
					}

					lineSb.append(toHtml(BET, result).replaceAll(",", " "));					
					lineSb.append(";");
					sb.append(lineSb.toString());
				}
			}

			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}

		return "";
	}

	public static String getSingleBetDescHtml(String betDesc, String result) throws DataException {
		if (StringUtils.isNotBlank(betDesc)) {
			StringBuffer sb = new StringBuffer();
			String[] arr = null;
			arr = betDesc.split("\r\n");// 分隔成多个投注内容
			StringBuffer lineSb = new StringBuffer();

			for (String str : arr) {

				// 单式投注内容格式：1 2 3 4 5
				if (str.split(" ").length != 5) {
					throw new DataException("方案内容格式不正确！");
				}
				lineSb.setLength(0);

				String[] bet = str.split(" ");
				for (int i = 0; i < bet.length; i++) {
					if (!(Integer.parseInt(bet[i]) > 0 && Integer.parseInt(bet[i]) < 22)) {
						throw new DataException("方案内容格式不正确！");
					}
				}

				lineSb.append(toHtml(str, result));
				lineSb.append(";");
				sb.append(lineSb.toString());
			}
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}

		return "";
	}

	/**
	 * 高亮中奖号码
	 * 
	 * @param bet 号码
	 * @param result 开奖结果
	 */
	public static String toHtml(String bet, String result) {
		if (StringUtils.isNotBlank(result)) {
			StringBuffer sb = new StringBuffer();
			String[] rs = result.split(",");
			sb.append("(");
			for (String r : rs) {
				sb.append(r).append("|");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
			String reg = sb.toString();
			bet = bet.replaceAll(reg, "<span class=\"hitchar\">$1</span>");
		}
		return bet;
	}
}