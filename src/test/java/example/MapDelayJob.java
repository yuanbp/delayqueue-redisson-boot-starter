package example;

import com.metinform.delayqueue.job.DelayJob;
import java.util.Map;

/**
 * @author chieftain
 * @date 2020/3/30 14:44
 */
public class MapDelayJob extends DelayJob<Map<String, String>> {

    public MapDelayJob(TestJobExecuter testExcuteJob) {
        super(testExcuteJob.getClass());
    }
}
