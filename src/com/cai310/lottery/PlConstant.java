package com.cai310.lottery;

public final class PlConstant {
	public static final String KEY = "pl";

	/** p5直选复式内容正则表达式 */
	public static final String P5_DIRECT_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,9}(-\\d{1,2}(,\\d{1,2}){0,9}){4})\\s*$";

	/** p5直选单式内容正则表达式 */
	public static final String P5_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){4})\\s*$";

	/** p3直选复式内容正则表达式 */
	public static final String P3_DIRECT_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,9}(-\\d{1,2}(,\\d{1,2}){0,9}){2})\\s*$";

	/** p3直选单式内容正则表达式 */
	public static final String P3_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){2})\\s*$";

	/** 组选3组选6复式内容正则表达式 */
	public static final String P3_GROUP3_6_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,9})\\s*$";
	/** 直选和值复式内容正则表达式 */
	public static final String P3_DIRECT_SUM_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,27})\\s*$";

	/** 组选和值复式内容正则表达式 */
	public static final String P3_GROUP_SUM_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,25})\\s*$";

	
	/** 包串复式内容正则表达式 */
	public static final String P3_BAOCHUAN_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,9})\\s*$";
	
	/** 跨度复式内容正则表达式 */
	public static final String P3_KUADU_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,9})\\s*$";
	
	
	/** 复式：号码之间的分隔符 */
	public static final String SEPARATOR_FOR_NUMBER = ",";

	/** 复式：直选区间之间的分隔符 */
	public static final String SEPARATOR_FOR_ = "-";

	public static final int[] UNITS_DIRECT_SUM = new int[] { 
		1, //0
        3, //1
        6, //2
        10, //3
        15, //4
        21, //5
        28, //6
        36, //7
        45, //8
        55, //9
        63, //10
        69, //11
        73, //12
        75, //13
        75, //14
        73, //15
        69, //16
        63, //17
        55, //18
        45, //19
        36, //20
        28, //21
        21, //22
        15, //23
        10, //24
        6, //25
        3, //26
        1   //27
	};

	public static final int[] UNITS_GROUP_SUM = new int[] {
			0, //   0
	        1, //   1
	        2, //   2
	        2, //   3
	        4, //   4
	        5, //   5
	        6, //   6
	        8, //   7
	        10, //   8
	        11, //   9
	        13, //  10
	        14, //  11
	        14, //  12
	        15, //  13
	        15, //  14
	        14, //  15
	        14, //  16
	        13, //  17
	        11, //  18
	        10, //  19
	        8, //  20
	        6, //  21
	        5, //  22
	        4, //  23
	        2, //  24
	        2, //  25
	        1   //  26
	};
	public static final Integer[] UNITS_GROUP3_SUM = new Integer[] { 
			0, // 0
			1, // 1 001
			2, // 2 002,011
			1, // 3 003
			3, // 4 004,022,112
			3, // 5 005,122,113
			3, // 6 006,033,114
			4, // 7 007,115,133,223
			5, // 8 008,044,116,224,233
			4, // 9 009,117,144,225
			5, // 10 118,226,244,334,505
			4, // 11 119,155,227,335
			3, // 12 228,255,336
			5, // 13 166,229,337,355,445
			5, // 14 266,338,446,455,707
			3, // 15 339,366,447
			5, // 16 277,448,466,556,808
			5, // 17 188,377,449,557,566
			3, // 18 288,477,558
			5, // 19 199,388,559,577,667
			4, // 20 299,488,668,677,
			3, // 21 399,588,669
			3, // 22 499,688,778
			3, // 23 599,779,788,
			1, // 24 699
			2, // 25 799,889
			1 // 26 899
	};
	
	public static final Integer[] UNITS_P3_KUADU = new Integer[] { 
		10, // 0
		54, // 1 
		96, // 2 
		126, // 3
		144, // 4
		150, // 5 
		144, // 6
		126, // 7
		96, // 8 
		54, // 9 
    };
	public static final Integer[] UNITS_P3_G3_KUADU = new Integer[] { 
		0, // 0
		18, // 1 
		16, // 2 
		14, // 3
		12, // 4
		10, // 5 
		8, // 6
		6, // 7
		4, // 8 
		2, // 9 
    };
	public static final Integer[] UNITS_P3_G6_KUADU = new Integer[] { 
		0, // 0
		0, // 1 
		8, // 2 
		14, // 3
		18, // 4
		20, // 5 
		20, // 6
		18, // 7
		14, // 8 
		8, // 9 
    };
}
