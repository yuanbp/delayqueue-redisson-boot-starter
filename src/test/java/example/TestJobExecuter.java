package example;

import com.metinform.delayqueue.interfaces.JobExecuter;
import com.metinform.delayqueue.service.DelayJobCommiter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chieftain
 * @date 2020/3/30 14:43
 */

@Service
public class TestJobExecuter implements JobExecuter<MapDelayJob> {

    @Autowired
    private DelayJobCommiter delayJobCommiter;

    /**
     * 到期执行的逻辑
     * @param job
     */
    @Override
    public void execute(MapDelayJob job) {
        System.out.println(job.getJobParams().toString());
    }

    /**
     * 模拟创建一个延时任务,15秒后到期
     */
    @PostConstruct
    public void initTest() {
        MapDelayJob job = new MapDelayJob(this);
        Map<String, String> map = new HashMap<>();
        map.put("testk", "testv");
        job.setJobParams(map);
        delayJobCommiter.commit(job, 15L, TimeUnit.SECONDS);
    }
}
