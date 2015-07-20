package com.cai310.lottery.ticket.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cai310.utils.CombCallBack;
import com.cai310.utils.MathUtils;

public class Conver {

	private List<String> codes = null;
	private List<List<String>> resultList = new ArrayList<List<String>>();
	private Set<String> resultSet = new HashSet<String>();
	private static final int NUMSUNIT = 3;
  
	public Set<String> dataConver(List<String> codes) {
		if(codes==null||codes.isEmpty())return null;
		this.codes = codes;
		List<String> resultCode = new ArrayList<String>();		
		PrizeWorkExtensionCombCallBack call = new PrizeWorkExtensionCombCallBack();
		int codesNum = codes.size() > NUMSUNIT ? NUMSUNIT : codes.size();
		StringBuffer sb = null;
		for(int i=0;i<codes.size();i++){
			sb = new StringBuffer();
			for(int j=0;j<NUMSUNIT;j++){
				sb.append(codes.get(i)).append(" ");
			}	
			sb.deleteCharAt(sb.length()-1);
			resultSet.add(sb.toString());
		}
		
		for (int need = 2; need <= codesNum; need++) {
			resultList.clear();
			resultCode.clear();
			MathUtils.efficientComb(codes.size(), need, call);
			String strTemp = null;
			for (List<String> x : resultList) {
				strTemp = new String();
				for (String xx : x) {
					strTemp += xx;
				}
				resultCode.add(strTemp);
			}

			if(need==1){
				
			}else if (need == 2) {
				for (String s : resultCode) {
					for (int j = 0; j < 2; j++) {
						if (j == 0) {
							s += s.charAt(0);
						} else {
							s = s.substring(1, 3);
							s += s.charAt(0);
						}
						char[] c = s.toCharArray();
						zuhe(c, c.length,0);
						for (String str : resultSet) {
							System.out.println(str);
						}
					}
				}
			} else {
				for (String s : resultCode) {
					char[] c = s.toCharArray();
					zuhe(c, c.length, 0);
					for (String str : resultSet) {
						System.out.println(str);
					}
				}
			}

		}
		for (String str : resultSet) {
			System.out.println(str);
		}
		System.out.println("可能的组合数：" + resultSet.size());
		return resultSet;
	}

	class PrizeWorkExtensionCombCallBack implements CombCallBack {
		public boolean callback(boolean[] comb1, int m1) {
			List<String> combList = new ArrayList<String>();
			for (int i = 0; i < comb1.length; i++) {
				if (comb1[i]) {
					combList.add(codes.get(i));
					if (combList.size() == m1)
						break;
				}
			}
			resultList.add(combList);
			return false;
		}
	}

	public static void main(String[] args) {
		List<String> codes = new ArrayList<String>();
		codes.add("0");
		codes.add("1");
		codes.add("2");
		codes.add("3");
		codes.add("4");
		codes.add("5");
		codes.add("6");
		codes.add("7");
		codes.add("8");
		codes.add("9");
		new Conver().dataConver(codes);
	}

	
	StringBuffer resultStr = null;	
	private void zuhe(char[] array, int n, int k) {
		resultStr = new StringBuffer();
		if (n == k) {
			for (char str : array) {
				resultStr.append(str).append(" ");
			}
			resultStr.deleteCharAt(resultStr.length() - 1);
			resultSet.add(resultStr.toString());
		} else {
			for (int i = k; i < n; i++) {
				swap(array, k, i);
				zuhe(array, n, k + 1);
				swap(array, i, k);
			}
		}
	}

	private void swap(char[] a, int x, int y) {
		char temp = a[x];
		a[x] = a[y];
		a[y] = temp;
	}

}
