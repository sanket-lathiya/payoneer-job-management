package com.payoneer.jobmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.payoneer.jobmanagement.enums.JobPriority;
import com.payoneer.jobmanagement.enums.JobStatus;
import com.payoneer.jobmanagement.priorityjobs.DataLoadPriorityJob;
import com.payoneer.jobmanagement.priorityjobs.IndexingPriorityJob;
import com.payoneer.jobmanagement.priorityjobs.PriorityJob;
import com.payoneer.jobmanagement.priorityjobs.SendEmailPriorityJob;
import com.payoneer.jobmanagement.services.PriorityBasedJobManagerService;

@SpringBootTest
class PriorityBasedJobManagerServiceTests {
	
	@Autowired
	private PriorityBasedJobManagerService service;
	
	@Test
	public void whenMultiplePriorityJobsQueued_thenHighestPriorityJobIsPicked() throws InterruptedException {
		
		System.out.println("***** PriorityBasedJobManagerServiceTests : 1 *****");
		
		PriorityJob job1 = new DataLoadPriorityJob(JobPriority.LOW);		//Priority = 5
	    PriorityJob job2 = new DataLoadPriorityJob(JobPriority.MEDIUM);		//Priority = 3
	    PriorityJob job3 = new DataLoadPriorityJob(JobPriority.HIGH);		//Priority = 1
	    PriorityJob job4 = new IndexingPriorityJob(JobPriority.MEDIUM);		//Priority = 4
	    PriorityJob job5 = new SendEmailPriorityJob(JobPriority.LOW);		//Priority = 6
	    PriorityJob job6 = new SendEmailPriorityJob(JobPriority.HIGH);		//Priority = 2
	    
	    service.scheduleJob(job1);
	    service.scheduleJob(job2);
	    service.scheduleJob(job3);
	    service.scheduleJob(job4);
	    service.scheduleJob(job5);
	    service.scheduleJob(job6);
	    
	    Thread.sleep(10 * 1000);// Waiting for all scheduled job to finish processing. 
		//Adjust sleep time to see output for all scheduled jobs in console. Ideally it should be (Number of jobs + few seconds) for current implementations.
		//While thread is sleeping you can check the job processing logs in the console.
		//If you found that all the scheduled jobs has not processed, please increase thread's sleeping time above.
	    
	    assertEquals(JobStatus.SUCCESS, job1.getJobStatus());
	    assertEquals(JobStatus.SUCCESS, job2.getJobStatus());
	    assertEquals(JobStatus.SUCCESS, job3.getJobStatus());
	    assertEquals(JobStatus.SUCCESS, job4.getJobStatus());
	    assertEquals(JobStatus.SUCCESS, job5.getJobStatus());
	    assertEquals(JobStatus.SUCCESS, job6.getJobStatus());
	}
	
	@Test
	public void whenMultiplePriorityJobsQueued_thenHighestPriorityJobIsPicked1() throws InterruptedException {
		
		System.out.println("***** PriorityBasedJobManagerServiceTests : 2 *****");
		
		PriorityJob job1 = new DataLoadPriorityJob(JobPriority.LOW);		//Priority = 5
	    PriorityJob job2 = new DataLoadPriorityJob(JobPriority.MEDIUM);		//Priority = 3
	    PriorityJob job3 = new DataLoadPriorityJob(JobPriority.HIGH);		//Priority = 1
	    PriorityJob job4 = new IndexingPriorityJob(JobPriority.MEDIUM);		//Priority = 4
	    PriorityJob job5 = new SendEmailPriorityJob(JobPriority.LOW);		//Priority = 6
	    PriorityJob job6 = new SendEmailPriorityJob(JobPriority.HIGH);		//Priority = 2
	    
	    List<PriorityJob> jobs = new ArrayList<>();
	    jobs.add(job1);
	    jobs.add(job2);
	    jobs.add(job3);
	    jobs.add(job4);
	    jobs.add(job5);
	    jobs.add(job6);
	    
	    service.scheduleMutipleJobs(jobs);
	    
	    Thread.sleep(10 * 1000);// Waiting for all scheduled job to finish processing. 
		//Adjust sleep time to see output for all scheduled jobs in console. Ideally it should be (Number of jobs + few seconds) for current implementations.
		//While thread is sleeping you can check the job processing logs in the console.
		//If you found that all the scheduled jobs has not processed, please increase thread's sleeping time above.
	
	    assertEquals(JobStatus.SUCCESS, job1.getJobStatus());
	    assertEquals(JobStatus.SUCCESS, job2.getJobStatus());
	    assertEquals(JobStatus.SUCCESS, job3.getJobStatus());
	    assertEquals(JobStatus.SUCCESS, job4.getJobStatus());
	    assertEquals(JobStatus.SUCCESS, job5.getJobStatus());
	    assertEquals(JobStatus.SUCCESS, job6.getJobStatus());
	}

}
