package com.metinform.delayqueue;

import com.metinform.delayqueue.service.DelayJobCommiter;
import com.metinform.delayqueue.timer.JobTimer;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chieftain
 * @date 2020/3/30 14:14
 */
@Configuration
@ConditionalOnClass({SpringBootApplication.class, RedissonClient.class})
public class DelayQueueAutoConfiguration {

    @Bean
    public JobTimer jobTimer() {
        return new JobTimer();
    }

    @Bean
    public DelayJobCommiter delayJobService() {
        return new DelayJobCommiter();
    }
}
