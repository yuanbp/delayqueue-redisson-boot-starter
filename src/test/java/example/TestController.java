package example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chieftain
 * @date 2020/3/30 15:42
 */
@RestController
@RequestMapping("/delay")
public class TestController {

    @Autowired
    private TestJobExecuter testExcuteJob;

    @GetMapping("/test")
    public void test() {
        testExcuteJob.initTest();
    }
}
