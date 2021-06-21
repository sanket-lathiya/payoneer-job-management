package com.payoneer.jobmanagement.services;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.payoneer.jobmanagement.priorityjobs.PriorityJob;

@Service
public class PriorityBasedJobManagerService {

	@Value("${POOL_SIZE}")
	private int poolSize;
	
	@Value("${INITIAL_QUEUE_SIZE}")
	private int initialQueueSize;

	private ExecutorService priorityJobPoolExecutor;
	private ExecutorService priorityJobScheduler = Executors.newSingleThreadExecutor();
	private PriorityBlockingQueue<PriorityJob> priorityQueue;

	@PostConstruct
	private void startService() {
		priorityJobPoolExecutor = Executors.newFixedThreadPool(poolSize);
		priorityQueue = new PriorityBlockingQueue<PriorityJob>(initialQueueSize, Comparator.comparing(PriorityJob::getJobPriority));

		priorityJobScheduler.execute(() -> {
			while(true) {
				try {
					priorityJobPoolExecutor.execute(priorityQueue.take());
				}catch (InterruptedException e) {
					//exception needs special handling
					break;
				}
			}
		});
	}

	public void scheduleJob(PriorityJob job) {
		priorityQueue.add(job);
	}
	
	public void scheduleMutipleJobs(List<PriorityJob> jobs) {
		priorityQueue.addAll(jobs);
	}

	@PreDestroy
	private void cleanUp() {
		priorityJobPoolExecutor.shutdown();
		priorityJobScheduler.shutdown();
		priorityQueue = null;
	}
}
