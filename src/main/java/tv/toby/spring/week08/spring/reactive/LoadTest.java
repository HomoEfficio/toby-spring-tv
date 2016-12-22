package tv.toby.spring.week08.spring.reactive;

import com.oracle.tools.packager.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author homo.efficio@gmail.com
 *         create on 2016-12-17.
 */
@Slf4j
public class LoadTest {

    static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService es = Executors.newFixedThreadPool(100);

        RestTemplate rt = new RestTemplate();
//        String url = "http://localhost:8080/blocking";
        String url = "http://localhost:8080/asyncContext";
//        String url = "http://localhost:8080/callable";
//        String url = "http://localhost:8080/dr";

        StopWatch mainSW = new StopWatch();

        mainSW.start();

        for (int i = 0 ; i < 100 ; i++) {
            es.execute(() -> {
                int idx = atomicInteger.addAndGet(1);
                log.info("Thread {}", idx);

                StopWatch sw = new StopWatch();
                sw.start();
                rt.getForObject(url, String.class);
                sw.stop();
                log.info("Elapsed: {} -> {}", idx, sw.getTotalTimeSeconds());
            });
        }

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);

        mainSW.stop();

        log.info("Total Elapsed: {}", mainSW.getTotalTimeSeconds());
    }
}
