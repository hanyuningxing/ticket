package com.cai310.lottery.service.info;

import com.cai310.lottery.common.TaskState;
import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.info.TaskInfoData;

/**
 *  线程实体管理接口
 * 
 */
@SuppressWarnings("unchecked") 
public interface TaskInfoDataEntityManager {
	TaskInfoData getTaskInfoDataByTaskType(TaskType taskType);
	TaskInfoData updateTaskInfoData(TaskState taskState,TaskType taskType,String runString);
}
