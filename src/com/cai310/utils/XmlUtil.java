package com.cai310.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtil {
	// 读取XML中某个节点中所有元素，存到MAP中
	public static Map<String, String> ReadXMLToMap(String xmlString, String execp) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Document document = DocumentHelper.parseText(xmlString);
			List<Element> list = document.selectNodes(execp);
			for (Element o : list) {
				for (Iterator iterator = o.attributeIterator(); iterator.hasNext();) {
					Attribute attribute = (Attribute) iterator.next();
					map.put(attribute.getName(), attribute.getValue());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	// 读取XML中某个节点中值，存到MAP中
	public static Map<String, String> ReadXMLElementToMap(String xmlString, String execp) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Document document = DocumentHelper.parseText(xmlString);
			List<Element> list = document.selectNodes(execp);
			for (Element o : list) {
				for (Iterator iterator = o.elementIterator(); iterator.hasNext();) {
					Element element = (Element) iterator.next();
					map.put(element.getName(), element.getText());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	// 读取XML中某个节点中所有元素，存到List中
	public static List<Map<String, String>> ReadXMLToList(String xmlString, String execp) {
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		try {
			Document document = DocumentHelper.parseText(xmlString);
			List<Element> list = document.selectNodes(execp);
			for (Element o : list) {
				Map<String, String> map = new HashMap<String, String>();
				for (Iterator iterator = o.attributeIterator(); iterator.hasNext();) {
					Attribute attribute = (Attribute) iterator.next();
					map.put(attribute.getName(), attribute.getValue());
				}
				ret.add(map);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	// 读取XML中某个节点下所有元素值，存到List中
	public static List<Map<String, String>> ReadXMLNodeToList(String xmlString, String execp) {
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		try {
			Document document = DocumentHelper.parseText(xmlString);
			List<Element> list = document.selectNodes(execp);
			for (Element o : list) {
				Map<String, String> map = new HashMap<String, String>();
				for (Iterator iterator = o.elementIterator(); iterator.hasNext();) {
					Element element = (Element) iterator.next();
					map.put(element.getName(), element.getText());
				}
				ret.add(map);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	/**
	 * 读取XML下某个节点的所有属性值到map中 格式:"<?xml version=\"1.0\"
	 * encoding=\"UTF-8\"?><root> <MessageInfo shareType=\"1\" salesMode=\"1\"
	 * lotteryType=\"0\" periodNumber=\"\"/> </root>";
	 * 
	 * @param xmlString
	 * @param nodeName
	 * @return
	 */
	public static Map<String, String> readXML2Map(String xmlString, String nodeName) {
		HashMap<String, String> map = new HashMap();
		try {
			Document doc = (Document) DocumentHelper.parseText(xmlString);
			;
			List<Element> elements = doc.selectNodes(nodeName);
			for (Iterator iter = (Iterator) elements.iterator(); iter.hasNext();) {
				Element elemt = (Element) iter.next();
				List attrs = elemt.attributes();
				for (int i = 0; i < attrs.size(); i++) {
					Attribute attr = (Attribute) attrs.get(i);
					map.put(attr.getName(), attr.getText());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 读取XML下某个节点的某个属性值(只能有一个MessageInfo节点) 格式:"<?xml version=\"1.0\"
	 * encoding=\"UTF-8\"?><root> <MessageInfo shareType=\"1\" salesMode=\"1\"
	 * lotteryType=\"0\" periodNumber=\"\"/> </root>";
	 * 
	 * @param xmlString
	 * @param nodeName
	 * @return
	 */
	public static String getProperty(String xmlString, String nodeName, String attrName) {
		Map map = readXML2Map(xmlString, nodeName);
		if (map.containsKey(attrName)) {
			return (String) map.get(attrName);
		}
		return null;
	}

	/**
	 * 读取XML下某个节点的所有属性值到list中 格式:"<?xml version=\"1.0\"
	 * encoding=\"UTF-8\"?><root> <MessageInfo shareType=\"1\" salesMode=\"1\"
	 * lotteryType=\"0\" periodNumber=\"\"/> </root>";
	 * 
	 * @param xmlString
	 * @param nodeName
	 * @return
	 */
	public static List<Map<String, String>> readXMLNode2List(String xmlString, String nodeName) {
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		try {
			Document document = DocumentHelper.parseText(xmlString);
			List<Element> elements = document.selectNodes(nodeName);
			for (Iterator iter = (Iterator) elements.iterator(); iter.hasNext();) {
				Element elemt = (Element) iter.next();
				Map<String, String> map = new HashMap<String, String>();
				List attrs = elemt.attributes();
				for (int i = 0; i < attrs.size(); i++) {
					Attribute attr = (Attribute) attrs.get(i);
					map.put(attr.getName(), attr.getText());
				}
				ret.add(map);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	public static void main(String[] args) {
		// String msg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		// "<msg v=\"1.0\" id=\"000001\"><body><bonusitem>" +
		// "<bonus memberId=\"0001\" username=\"天涯\" ticketId=\"0001\" " +
		// "lotterySortId=\"001\" issueNumberName=\"20100515\" playCode=\"9999\" "
		// +
		// "multiple=\"2\" winAmount=\"320\" winNumber=\"1,1,1,1,1,1\" " +
		// "bonusLevel=\"1\" bonusTime=\"2010-05-15 12:00:00\"></bonus>" +
		// "</bonusitem></body></msg>";

		String msg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<body><errorcode>200</errorcode><account>"
				+ "<cash v=\"现金\"/><card v=\"彩金\" />" + "<ticket v=\"红包\" /><score v=\"积分\" />"
				+ "<drawpwd v=\"y/n\"/></account></body>";

		String msg2 = "<alipay><is_success>T</is_success>" + "<request>"
				+ "<param name=\"service\">single_trade_query</param>"
				+ "<param name=\"partner\">2088501247194221</param>" + "<param name=\"_input_charset\">UTF-8</param>"
				+ "<param name=\"out_trade_no\">2011062100000013</param>" + "</request>" + "<response>"
				+ "<AwardIndex index=1>" + "<body>2011062100000011</body>" + "</AwardIndex>" + "<AwardIndex index=2>"
				+ "<body>2011062100000013</body>" + "</AwardIndex>" + "</response>" + "</alipay>";

		msg = "<?xml version=\"1.0\" encoding=\"utf-8\"?><message><head><messageId>消息流水号</messageId><agentId>商户id </agentId><timestamp>时间戳</timestamp><digest>摘要消息体后的内容</digest></head><body><responseCode>响应代码</responseCode><issue><game>玩法编号</game><gameIssue>奖期号</gameIssue><startTime>奖期开启时间</startTime><endTime>奖期截止时间</endTime><status>状态</status><drawCode>摇奖号码</drawCode><salesMoney>本期销售金额</salesMoney><bonusMoney>本期中奖金额</bonusMoney></issue></body></message>";

		System.out.println(ReadXMLElementToMap(msg2, "/alipay/").get("body"));
		// System.out.println(ReadXMLToMap(msg,"/body/account/cash").get("v"));
		// System.out.println(ReadXMLElementToMap(msg,"/body").get("errorcode"));
		// msg="<body><errorcode>200</errorcode></body>";
		// System.out.println(ReadXMLElementToMap(msg,"body").get("cash"));
		// List<Map<String,String>>
		// list=XmlUtil.ReadXMLToList(msg,"/body/account/cash");
		// //System.out.println(list.get(0).get("v"));
		// for (Map map : list) {
		// //System.out.println(map);
		// for (Object string : map.values()) {
		// System.out.println(string);
		// }
		// }
	}

	/**
	 * 读文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<String> readFile(String filePath) {
		List<String> para = new ArrayList<String>();
		File file = new File(filePath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String temp = null;

			while ((temp = reader.readLine()) != null) {

				para.add(temp);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return para;
	}

	public static String updateChild(String xmlString, String childName, String childValue) {
		Document document = null;
		try {
			
		     document = DocumentHelper.parseText(xmlString);
			List<Element> childelements = document.selectNodes(childName);
			for (Iterator childs = childelements.iterator(); childs.hasNext();) {
				Element everyone = (Element) childs.next();
				everyone.setText(childValue); // 修改该元素值
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document.asXML();
	}

}
