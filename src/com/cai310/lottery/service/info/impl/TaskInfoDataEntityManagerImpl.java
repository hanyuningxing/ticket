package com.cai310.lottery.service.info.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.TaskState;
import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.dao.info.TaskInfoDataDao;
import com.cai310.lottery.entity.info.TaskInfoData;
import com.cai310.lottery.service.info.TaskInfoDataEntityManager;

@Service("taskInfoDataEntityManagerImpl")
@Transactional
public class TaskInfoDataEntityManagerImpl implements TaskInfoDataEntityManager {

	@Autowired
	protected TaskInfoDataDao taskInfoDataDao;

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public TaskInfoData getTaskInfoDataByTaskType(TaskType taskType) {
		return taskInfoDataDao.findUniqueBy("taskType", taskType);
	}
	
	public TaskInfoData updateTaskInfoData(TaskState taskState,TaskType taskType,String runString) {
		TaskInfoData taskInfoData=getTaskInfoDataByTaskType(taskType);
		if(null==taskInfoData){
			taskInfoData=new TaskInfoData();
			taskInfoData.setRunTime(Long.valueOf(0));
			taskInfoData.setTaskType(taskType);
			taskInfoData.setTaskState(TaskState.RUN);
		}
		if(TaskState.EXCEPTION.equals(taskInfoData.getTaskState())){
			taskInfoData.setRunTime(Long.valueOf(0));
		}else if(TaskState.RUN.equals(taskInfoData.getTaskState())){
			taskInfoData.setRunTime(taskInfoData.getRunTime()+1);
		}else if(TaskState.STOP.equals(taskInfoData.getTaskState())){
			taskInfoData.setRunTime(taskInfoData.getRunTime());
		}
		if(taskInfoData.getRunTime()>100000){
			taskInfoData.setRunTime(Long.valueOf(0));
			taskInfoData.setTaskState(TaskState.RESET);
		}else{
			taskInfoData.setTaskState(taskState);
		}
		if(runString.length()>300){
			runString=runString.substring(0,250);
		}
		taskInfoData.setRunString(runString);
		taskInfoData.setLastModifyTime(new Date());
		return taskInfoDataDao.save(taskInfoData);
	}
}
