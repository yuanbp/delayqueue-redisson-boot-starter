package com.metinform.delayqueue.timer;

import com.metinform.delayqueue.interfaces.JobExecuter;
import com.metinform.delayqueue.job.DelayJob;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@Slf4j
public class JobTimer {

    public static final String jobsTag = "customer_jobtimer_jobs";
    @Autowired
    private RedissonClient client;

    @Autowired
    private ApplicationContext context;

    private NamedThreadFactory namedThreadFactory = new NamedThreadFactory("delay-thread");
    ExecutorService executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2,
            200,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024),
            namedThreadFactory,
            new ThreadPoolExecutor.AbortPolicy());

    @PostConstruct
    public void startJobTimer() {
        RBlockingQueue<DelayJob> blockingQueue = client.getBlockingQueue(jobsTag);
        //由于此线程需要常驻，可以新建线程，不用交给线程池管理
        //noinspection AlibabaAvoidManuallyCreateThread
        new Thread(() -> {
            while (true) {
                try {
                    DelayJob job = blockingQueue.take();
                    executorService.execute(new ExecutorTask(context, job));
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    try {
                        TimeUnit.SECONDS.sleep(60);
                    } catch (Exception ignoreEx) {
                    }
                }
            }
        }).start();
    }

    public static class ExecutorTask implements Runnable {

        private ApplicationContext context;

        private DelayJob delayJob;

        public ExecutorTask(ApplicationContext context, DelayJob delayJob) {
            this.context = context;
            this.delayJob = delayJob;
        }

        @Override
        public void run() {
            JobExecuter service = (JobExecuter) context.getBean(delayJob.getService());
            service.execute(delayJob);
        }
    }
}