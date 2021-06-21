package com.payoneer.jobmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.payoneer.jobmanagement.delayjobs.DataLoadDelayJob;
import com.payoneer.jobmanagement.delayjobs.DelayJob;
import com.payoneer.jobmanagement.delayjobs.IndexingDelayJob;
import com.payoneer.jobmanagement.delayjobs.SendEmailDelayJob;
import com.payoneer.jobmanagement.enums.JobStatus;
import com.payoneer.jobmanagement.services.DelayBasedJobManagerService;

@SpringBootTest
public class DelayBasedJobManagerServiceTests {
	
	@Autowired
	private DelayBasedJobManagerService service;
	
	@Test
	public void whenMultipleDelayJobsQueued_thenLowestDelayJobIsPicked() throws InterruptedException {
		
		System.out.println("***** DelayBasedJobManagerServiceTests : 1 *****");
		
		//Delay time is in milliseconds.
		DelayJob job1 = new DataLoadDelayJob(1 * 1000); //1 second delay      	//Priority = 1
		DelayJob job2 = new SendEmailDelayJob(51 * 1000); //51 second delay		//Priority = 6
		DelayJob job3 = new IndexingDelayJob(45 * 1000); //45 second delay		//Priority = 5
		DelayJob job4 = new DataLoadDelayJob(32 * 1000); //32 second delay		//Priority = 4
		DelayJob job5 = new IndexingDelayJob(23 * 1000); //23 second delay		//Priority = 3
		DelayJob job6 = new DataLoadDelayJob(11 * 1000); //11 second delay	   	//Priority = 2
	    
		service.scheduleJob(job1);
		service.scheduleJob(job2);
		service.scheduleJob(job3);
		service.scheduleJob(job4);
		service.scheduleJob(job5);
		service.scheduleJob(job6);
		
		Thread.sleep(55 * 1000);// Waiting for all scheduled job to finish processing. 
		//Adjust sleep time to see output for all scheduled jobs in console. Ideally it should be (Max delayTime + few seconds) for current implementations.
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
	public void whenMultipleDelayJobsQueued_thenLowestDelayJobIsPicked1() throws InterruptedException {
		
		System.out.println("***** DelayBasedJobManagerServiceTests : 2 *****");
		
		//Delay time is in milliseconds.
		DelayJob job1 = new DataLoadDelayJob(1 * 1000); //1 second delay      	//Priority = 1
		DelayJob job2 = new SendEmailDelayJob(51 * 1000); //51 second delay		//Priority = 6
		DelayJob job3 = new IndexingDelayJob(45 * 1000); //45 second delay		//Priority = 5
		DelayJob job4 = new DataLoadDelayJob(32 * 1000); //32 second delay		//Priority = 4
		DelayJob job5 = new IndexingDelayJob(23 * 1000); //23 second delay		//Priority = 3
		DelayJob job6 = new DataLoadDelayJob(11 * 1000); //11 second delay	   	//Priority = 2
	    
		List<DelayJob> jobs = new ArrayList<>();
		jobs.add(job1);
		jobs.add(job2);
		jobs.add(job3);
		jobs.add(job4);
		jobs.add(job5);
		jobs.add(job6);
		
		service.scheduleMutipleJobs(jobs);
	    
		Thread.sleep(55 * 1000);// Waiting for all scheduled job to finish processing. 
		//Adjust sleep time to see output for all scheduled jobs in console. Ideally it should be (Max delayTime + few seconds) for current implementations.
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
