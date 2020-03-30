package com.metinform.delayqueue.interfaces;

import com.metinform.delayqueue.job.DelayJob;

public interface JobExecuter<T extends DelayJob> {

    public void execute(T job);
}