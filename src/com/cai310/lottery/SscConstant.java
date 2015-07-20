package com.cai310.lottery;

public class SscConstant {
	public static final String KEY = "ssc";
	
	public static final String SEPARATOR_FOR_NUMBER = ",";
	/** 复式：直选区间之间的分隔符 */
	public static final String SEPARATOR_FOR_ = "-";
	/** 胆拖码之间的分隔符 */
	public static final String SEPARATOR_DAN_FOR_NUMBER = ";";
	/**含有胆码的格式**/
	public static final String GENERAL_COMPOUND_DAN_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,9}"+SEPARATOR_DAN_FOR_NUMBER+"\\d{1,2}(,\\d{1,2}){0,9})\\s*$";

	

	/** 五星直选复式内容正则表达式 */
	public static final String DirectFive_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,9}(-\\d{1,2}(,\\d{1,2}){0,9}){4})\\s*$";

	/** 三星直选复式内容正则表达式 */
	public static final String DirectThree_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,9}(-\\d{1,2}(,\\d{1,2}){0,9}){2})\\s*$";

	/** 二星直选复式内容正则表达式 */
	public static final String DirectTwo_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,9}(-\\d{1,2}(,\\d{1,2}){0,9}){1})\\s*$";

	/** 一星直选复式内容正则表达式 */
	public static final String DirectOne_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,9}(-\\d{1,2}(,\\d{1,2}){0,9}){0})\\s*$";
	
	/** 五星值选单式内容正则表达式 */
	public static final String DirectFive_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){4})\\s*$";
	/** 三星值选单式内容正则表达式 */
	public static final String DirectThree_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){2})\\s*$";
	/** 二星值选单式内容正则表达式 */
	public static final String DirectTwo_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){1})\\s*$";
	/** 一星值选单式内容正则表达式 */
	public static final String DirectOne_SINGLE_REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){0})\\s*$";
	
	
	
	/** 组选3组选6复式内容正则表达式 */
	public static final String Three_GROUP3_6_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,9})\\s*$";
	
	
	/** 三星直选 组选 和值复式内容正则表达式 */
	public static final String Three_DIRECT_GROUP_SUM_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,27})\\s*$";


	/** 二星直选 组选 和值复式内容正则表达式 */
	public static final String Two_DIRECT_GROUP_SUM_COMPOUND_REGEX = "^\\s*(\\d{1,5}):(\\d{1,2}(,\\d{1,2}){0,18})\\s*$";



	
	public static final int[] UNITS_DIRECT_THREE_SUM = new int[] { 
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

	public static final int[] UNITS_GROUP_THREE_SUM = new int[] {
		 1, //0
         1, //1
         2, //2
         3, //3
         4, //4
         5, //5
         7, //6
         8, //7
         10, //8
         12, //9
         13, //10
         14, //11
         15, //12
         15, //13
         15, //14
         15, //15
         14, //16
         13, //17
         12, //18
         10, //19
         8,//20
         7,//21
         5,//22
         4,//23
         3,//24
         2,//25
         1,//26
         1  //27
	};

	
	
	
	public static final int[] UNITS_DIRECT_TWO_SUM = new int[] { 
		 1, //0
         2, //1
         3, //2
         4, //3
         5, //4
         6, //5
         7, //6
         8, //7
         9, //8
         10, //9
         9, //10
         8, //11
         7, //12
         6, //13
         5, //14
         4, //15
         3, //16
         2, //17
         1, //18
	};

	public static final int[] UNITS_GROUP_TWO_SUM = new int[] {
		 1, //0
         1, //1
         2, //2
         2, //3
         3, //4
         3, //5
         4, //6
         4, //7
         5, //8
         5, //9
         5, //10
         4, //11
         4, //12
         3, //13
         3, //14
         2, //15
         2, //16
         1, //17
         1, //18
	};
	
	public static final Integer[] UNITS_GROUP_TWO = new Integer[] { 
		0, // 0
		0, // 1 
		1, // 2 
		3, // 3
		6, // 4
		10, // 5 
		15, // 6
		21, // 7
    };
	public static final Integer[] UNITS_GROUP_THREE = new Integer[] { 
		0, // 0
		0, // 1
		0, // 2 
		6, // 3
		24, // 4
		60, // 5 
		120, // 6
		210, // 7
		336, // 8
		504, // 9
		720, // 10
    };
	
	
}
