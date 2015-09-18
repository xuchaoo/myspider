package dedup;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.dudup.DeDup;
import com.oxf1.spider.dudup.impl.MemoryDedup;
import com.oxf1.spider.request.HttpRequestMethod;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.request.impl.HttpRequest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by cxu on 2015/9/18.
 */
public class MemoryDedupTest {
    private TaskConfig taskConfig;
    private Request rq = new HttpRequest("http://baidu.com", HttpRequestMethod.HTTP_DELETE, null);
    private DeDup dp;

    @BeforeClass
    public void setup()
    {
        taskConfig = new TaskConfig("Task-Id-For-Test", "testTask");
        dp = new MemoryDedup(taskConfig);

        List<Request> req = new ArrayList<Request>();
        req.add(rq);
        req = dp.deDup(req);
        assertEquals(1, req.size());
    }

    @AfterClass
    public void tearDown()
    {
        dp.close();
    }

    @Test
    public void test(){

        Request rq1 = rq;
        Request rq2 = new HttpRequest("url2", HttpRequestMethod.HTTP_DELETE, null);
        Request rq3 = new HttpRequest("url3", HttpRequestMethod.HTTP_DELETE, null);

        DeDup dp = new MemoryDedup(taskConfig);

        List<Request> req = new ArrayList<Request>();
        req.add(rq1);
        req.add(rq2);
        req.add(rq3);

        req = dp.deDup(req);
        assertEquals(2, req.size());
        assertEquals(0, dp.deDup(req).size());
    }
}
