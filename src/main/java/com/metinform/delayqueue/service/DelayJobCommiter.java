package com.metinform.delayqueue.service;

import com.metinform.delayqueue.job.DelayJob;
import com.metinform.delayqueue.timer.JobTimer;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

public class DelayJobCommiter implements Serializable {

    private static final long serialVersionUID = 5473420647772214501L;

    @Autowired
    private RedissonClient client;

    public void commit(DelayJob job, Long delay, TimeUnit timeUnit) {
        RBlockingQueue blockingQueue = client.getBlockingQueue(JobTimer.jobsTag);
        RDelayedQueue delayedQueue = client.getDelayedQueue(blockingQueue);
        delayedQueue.offer(job, delay, timeUnit);
    }

}