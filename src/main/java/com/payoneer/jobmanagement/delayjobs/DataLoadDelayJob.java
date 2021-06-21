package com.payoneer.jobmanagement.delayjobs;

import com.payoneer.jobmanagement.enums.JobStatus;
import com.payoneer.jobmanagement.enums.JobType;
import com.payoneer.jobmanagement.exceptions.DataLoadJobException;

public class DataLoadDelayJob extends DelayJob {

	public DataLoadDelayJob(long delayTime) {
		super(JobType.DATA_LOAD, delayTime);
	}

	@Override
	public void run() {
		try {
			System.out.println(String.format("(ThreadId:JobId:JobType:DelayTime)-->(%s:%s:%s:%s)", Thread.currentThread().getId(), getJobId(), getJobType(), getDelayTime()));
			setJobStatus(JobStatus.RUNNING);
			performJobActions();
			setJobStatus(JobStatus.SUCCESS); 
		} catch (DataLoadJobException e) {
			//exception needs special handling.
			setJobStatus(JobStatus.FAILED);
			setErrorMessage(e.getLocalizedMessage());
			System.out.println(String.format("(ThreadId:JobId:JobType:DelayTime)-->(%s:%s:%s:%s)-->Failed job. Reason: %s", Thread.currentThread().getId(), getJobId(), getJobType(), getDelayTime(), e.getLocalizedMessage()));
		}
	}

	private void performJobActions() throws DataLoadJobException {
		//Perform all the job actions in the single database transaction.
		try {
			System.out.println(String.format("(ThreadId:JobId:JobType:DelayTime)-->(%s:%s:%s:%s)-->ACTION : Data loading started...", Thread.currentThread().getId(), getJobId(), getJobType(), getDelayTime()));
			Thread.sleep(1000); //To simulate actual execution time
			System.out.println(String.format("(ThreadId:JobId:JobType:DelayTime)-->(%s:%s:%s:%s)-->ACTION : Data loading completed...", Thread.currentThread().getId(), getJobId(), getJobType(), getDelayTime()));
		} catch (Exception e) {
			//Rollback database transaction if any.
			throw new DataLoadJobException(e);
		} 
	}

}
