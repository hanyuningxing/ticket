package com.cai310.lottery.support.ssq;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.exception.DataException;

public class SsqSupport {
	/** 三等奖奖金 */
	public static final int ThirdPrize = 3000;

	/** 四等奖奖金 */
	public static final int FourthPrize = 200;

	/** 五等奖奖金 */
	public static final int FifthPrize = 10;

	/** 六等奖奖金 */
	public static final int SixthPrize = 5;

	public static String getPolyBetDescHtml(String betDesc, String result) throws DataException {

		if (StringUtils.isNotBlank(betDesc)) {
			StringBuffer sb = new StringBuffer(50);

			String[] arr = null;
			arr = betDesc.split(";");// 分隔成多个投注内容
			StringBuffer lineSb = new StringBuffer(50);

			for (String str : arr) {

				if (str.split("\\)").length == 1) {
					// 复式投注内容格式：注数_内容
					if (str.split("_").length != 2) {
						throw new DataException("方案内容格式不正确！");
					}
					lineSb.setLength(0);

					// 内容格式：1,2,3,4,5,6 7
					String RED = str.split("_")[1].split(" ")[0];
					String BLUE = str.split("_")[1].split(" ")[1];
					String[] red = RED.split(",");
					String[] blue = BLUE.split(",");

					if (red.length < 6 || blue.length < 1) {
						throw new DataException("方案内容格式不正确！");
					}

					for (int i = 0; i < red.length; i++) {
						if (!(Integer.parseInt(red[i]) > 0 && Integer.parseInt(red[i]) < 34)) {
							throw new DataException("方案内容格式不正确！");
						}
					}

					lineSb.append(toHtml(RED, result, 0).replaceAll(",", " "));
					lineSb.append("∣");

					for (int i = 0; i < blue.length; i++) {
						if (!(Integer.parseInt(blue[i]) > 0 && Integer.parseInt(blue[i]) < 17)) {
							throw new DataException("方案内容格式不正确！");
						}
					}

					lineSb.append(toHtml(BLUE, result, 1));
					lineSb.append(";");
					sb.append(lineSb.toString());

				} else if (str.split("\\)").length > 1) {

					// 胆码复式投注内容: 注数_(1,2,3),4,5,6,7 8,9
					if (str.split("_").length != 2) {
						throw new DataException("方案内容格式不正确！");
					}

					lineSb.setLength(0);

					String RED = str.split("_")[1].split(" ")[0];
					String BLUE = str.split("_")[1].split(" ")[1];
					String[] redSpecile = RED.split("\\)")[0].split("\\(")[1].split(",");
					String[] redCommon = RED.split("\\)")[1].split(","); // redCommon[0]为空格
					String[] blue = BLUE.split(",");

					if ((redSpecile.length + redCommon.length) < 7) {
						throw new DataException("方案内容格式不正确！");
					}

					for (int i = 0; i < redSpecile.length; i++) {
						if (!(Integer.parseInt(redSpecile[i]) > 0 && Integer.parseInt(redSpecile[i]) < 34)) {
							throw new DataException("方案内容格式不正确！");
						}
					}

					for (int i = 1; i < redCommon.length; i++) {
						if (!(Integer.parseInt(redCommon[i]) > 0 && Integer.parseInt(redCommon[i]) < 34)) {
							throw new DataException("方案内容格式不正确！");
						}
					}

					lineSb.append(toHtml(RED, result, 0).replaceAll(",", " "));
					lineSb.append("∣");

					for (int i = 0; i < blue.length; i++) {
						if (!(Integer.parseInt(blue[i]) > 0 && Integer.parseInt(blue[i]) < 17)) {
							throw new DataException("方案内容格式不正确！");
						}
					}

					lineSb.append(toHtml(BLUE, result, 1));
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
			StringBuffer sb = new StringBuffer(50);
			String[] arr = null;
			arr = betDesc.split("\r\n");// 分隔成多个投注内容
			StringBuffer lineSb = new StringBuffer(50);

			for (String str : arr) {

				// 单式投注内容格式：1 2 3 4 5 6 7
				if (str.split(" ").length != 7) {
					throw new DataException("方案内容格式不正确！");
				}
				lineSb.setLength(0);

				String[] Ball = str.split(" ");
				String[] red = new String[6];
				String redStr = "";
				for (int i = 0; i < Ball.length - 1; i++) {
					red[i] = Ball[i];
					redStr += Ball[i] + " ";
				}
				String blue = Ball[6];

				for (int i = 0; i < red.length; i++) {
					if (!(Integer.parseInt(red[i]) > 0 && Integer.parseInt(red[i]) < 34)) {
						throw new DataException("方案内容格式不正确！");
					}
				}

				lineSb.append(toHtml(redStr, result, 0));
				lineSb.append("∣");

				if (!(Integer.parseInt(blue) > 0 && Integer.parseInt(blue) < 17)) {
					throw new DataException("方案内容格式不正确！");
				}

				lineSb.append(toHtml(blue, result, 1));
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
	 * @param color 0 : red; 1 : blue
	 */
	public static String toHtml(String bet, String result, int color) {

		String betContent = bet;

		if (StringUtils.isNotBlank(result)) {
			StringBuffer sb = new StringBuffer(15);
			String[] rs = result.split(" ")[0].split(",");
			String bs[] = result.split(" ")[1].split(",");

			// 组装红球正则表达式
			sb.append("(");
			if (color == 0) {
				for (String r : rs) {
					sb.append(r).append("|");
				}

				sb.deleteCharAt(sb.length() - 1);
				sb.append(")");
				// 红球正则表达式
				String reg = sb.toString(); // 正则表达式

				// 匹配中奖红球
				betContent = bet.replaceAll(reg, "<span class=\"hitchar\">$1</span>");

			} else if (color == 1) {
				// 组装蓝球正则表达式
				for (String b : bs) {
					sb.append(b).append("|");
				}

				sb.deleteCharAt(sb.length() - 1);
				sb.append(")");
				// 蓝球正则表达式
				String reg = sb.toString();

				StringBuffer blueSb = new StringBuffer(50);
				// 匹配中奖蓝球
				betContent = bet.replaceAll(reg, "<span class=\"hitchar\">$1</span>");
				String[] temp = betContent.split(",");

				for (int i = 0; i < temp.length; i++) {
					if (temp[i].length() < 4) {
						temp[i] = "<span class=\"f_00f\">" + temp[i] + "</span>";
					}
					blueSb.append(temp[i]).append(" ");
				}

				blueSb.deleteCharAt(blueSb.length() - 1);
				betContent = blueSb.toString();
			}
		} else if (color == 1) {
			String[] temp = betContent.split(",");
			StringBuffer blueSb = new StringBuffer(50);

			for (int i = 0; i < temp.length; i++) {
				temp[i] = "<span class=\"f_00f\">" + temp[i] + "</span>";
				blueSb.append(temp[i]).append(" ");
			}

			blueSb.deleteCharAt(blueSb.length() - 1);
			betContent = blueSb.toString();
		}
		return betContent;
	}
}