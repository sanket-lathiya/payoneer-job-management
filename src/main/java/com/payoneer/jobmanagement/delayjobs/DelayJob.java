package com.payoneer.jobmanagement.delayjobs;

import java.util.UUID;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.payoneer.jobmanagement.enums.JobStatus;
import com.payoneer.jobmanagement.enums.JobType;

public abstract class DelayJob implements Delayed, Runnable {
	
	private final String jobId;
	private final JobType jobType;
	private long delayTime; //It is a delay time in milliseconds. Job will be executed at currentTime + delayTime.
	private JobStatus jobStatus;
	private String errorMessage;

	protected DelayJob(JobType jobType, long delayTime) {
		this.jobId = UUID.randomUUID().toString();
		this.jobType = jobType;
		this.delayTime = System.currentTimeMillis() + delayTime;
		this.jobStatus = JobStatus.QUEUED;
	}
	
	/**
	 * This method will be called by DelayQueue to determine the job priority based on lowest delay.
	 * The job having lowest delay will have highest execution priority.
	 */
	@Override 
	public long getDelay(TimeUnit unit){
        long diff = delayTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

	/**
	 * This method is used to order the jobs based on delay time set on it. 
	 * Lowest delay job will have highest priority.
	 */
    @Override 
    public int compareTo(Delayed obj) {
        if (this.delayTime < ((DelayJob)obj).delayTime) {
            return -1;
        }
        if (this.delayTime > ((DelayJob)obj).delayTime) {
            return 1;
        }
        return 0;
    }
    
    public String getJobId() {
		return jobId;
	}

    public JobType getJobType() {
		return jobType;
	}

    public long getDelayTime() {
		return delayTime;
	}

    public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}

    public JobStatus getJobStatus() {
		return jobStatus;
	}

    public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}

    public String getErrorMessage() {
		return errorMessage;
	}

    public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
