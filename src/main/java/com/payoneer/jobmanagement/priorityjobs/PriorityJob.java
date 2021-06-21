package com.payoneer.jobmanagement.priorityjobs;

import java.util.UUID;

import com.payoneer.jobmanagement.enums.JobPriority;
import com.payoneer.jobmanagement.enums.JobStatus;
import com.payoneer.jobmanagement.enums.JobType;

public abstract class PriorityJob implements Runnable {
	
	private final String jobId;
	private final JobType jobType;
	private JobPriority jobPriority;
	private JobStatus jobStatus;
	private String errorMessage;

	protected PriorityJob(JobType jobType, JobPriority jobPriority) {
		this.jobId = UUID.randomUUID().toString();
		this.jobType = jobType;
		this.jobPriority = jobPriority;
		this.jobStatus = JobStatus.QUEUED;
	}
	
	public String getJobId() {
		return jobId;
	}

	public JobType getJobType() {
		return jobType;
	}

	public JobPriority getJobPriority() {
		return jobPriority;
	}

	public void setJobPriority(JobPriority jobPriority) {
		this.jobPriority = jobPriority;
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
