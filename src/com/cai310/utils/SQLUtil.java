package com.cai310.utils;

public class SQLUtil {
	public final static String DISTINCT_REGEX = "DISTINCT\\s+([A-Z0-9]+.){0,1}[A-Z0-9]+";

	public static String getCountHql(String hql) {
		String countHql;

		// 去掉排序语句
		int index = hql.toUpperCase().indexOf(" ORDER ");
		if (index != -1)
			countHql = hql.substring(0, index);
		else
			countHql = hql;

		String upperCountHql = countHql.toUpperCase();
		index = upperCountHql.indexOf("SELECT");
		if (index != -1) {
			index = upperCountHql.indexOf("DISTINCT");
			if (index != -1) {
				String[] arr = upperCountHql.split(DISTINCT_REGEX);
				if (arr.length == 2) {
					countHql = new StringBuffer("select count(").append(
							countHql.substring(arr[0].length(), countHql.length() - arr[1].length())).append(") ")
							.append(countHql.substring(upperCountHql.indexOf("FROM"), countHql.length())).toString();
				} else {
					countHql = "select count(*) "
							+ countHql.substring(upperCountHql.indexOf("FROM"), countHql.length());
				}
			} else {
				countHql = "select count(*) " + countHql.substring(upperCountHql.indexOf("FROM"), countHql.length());
			}
		} else {
			countHql = "select count(*) " + countHql;
		}
		return countHql;
	}
}
