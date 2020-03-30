package com.metinform.delayqueue.job;

import com.metinform.delayqueue.interfaces.JobExecuter;
import java.io.Serializable;
import lombok.Data;

@Data
public class DelayJob<T> implements Serializable {

    private static final long serialVersionUID = 2979243570150666892L;

    private Class<? extends JobExecuter> service;  //具体执行实例实现,必须实现了JobExecuter接口

    private T jobParams;    //job执行参数

    public DelayJob(Class<? extends JobExecuter> service) {
        this.service = service;
    }
}