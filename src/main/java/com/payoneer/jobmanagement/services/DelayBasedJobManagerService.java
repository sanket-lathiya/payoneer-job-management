package com.payoneer.jobmanagement.services;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.payoneer.jobmanagement.delayjobs.DelayJob;

@Service
public class DelayBasedJobManagerService {
	
	@Value("${POOL_SIZE}")
	private int poolSize;

	private ExecutorService delayJobPoolExecutor;
	private ExecutorService delayJobScheduler = Executors.newSingleThreadExecutor();
	private BlockingQueue<DelayJob> delayQueue;

	@PostConstruct
	private void startService() {

		delayJobPoolExecutor = Executors.newFixedThreadPool(poolSize);
		delayQueue = new DelayQueue<DelayJob>();

		delayJobScheduler.execute(() -> {
			while(true) {
				try {
					delayJobPoolExecutor.execute(delayQueue.take());
				}catch (InterruptedException e) {
					// exception needs special handling
					break;
				}
			}
		});
	}

	public void scheduleJob(DelayJob job) {
		delayQueue.add(job);
	}
	
	public void scheduleMutipleJobs(List<DelayJob> jobs) {
		delayQueue.addAll(jobs);
	}

	@PreDestroy
	private void cleanUp() {
		delayJobPoolExecutor.shutdown();
		delayJobScheduler.shutdown();
		delayQueue = null;
	}
}
