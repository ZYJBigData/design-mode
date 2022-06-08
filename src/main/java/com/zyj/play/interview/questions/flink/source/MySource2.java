package com.zyj.play.interview.questions.flink.source;

import com.zyj.play.interview.questions.flink.source.infra.LearnPojo;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lyy
 * 固定速率数据源 , 可多并行度
 */
@Slf4j
@NoArgsConstructor
public class MySource2 extends RichParallelSourceFunction<LearnPojo> implements BaseSource {

    private boolean sourceSwitch = true;
    private final AtomicLong count = new AtomicLong(0);
    private boolean switchPrintLog = true;
    //private long baseTime = 1630425600_000L;
    //private long baseTime = 1630000000_000L;
    private String name = "pojo";
    private long time = 1000L;

    public MySource2(boolean switchPrintLog) {
        System.out.println("create-source2");
        this.switchPrintLog = switchPrintLog;
    }

    public MySource2(boolean switchPrintLog, String name, Long time) {
        System.out.println("create-source2");
        this.switchPrintLog = switchPrintLog;
        this.name = name;
        this.time = time;
    }

    public MySource2(boolean switchPrintLog, String name) {
        System.out.println("create-source2");
        this.switchPrintLog = switchPrintLog;
        this.name = name;
    }

    @Override
    public void run(SourceContext<LearnPojo> sourceContext) {
        while (sourceSwitch) {
            long i = count.addAndGet(1L);
            LearnPojo build = LearnPojo.builder(i, "source_2")
                    .time(baseTime +i * 6000)
//                    .time(baseTime)
                    .value(1L)
                    .index(i)
                    .build();
            sourceContext.collect(build);
            if (switchPrintLog) {
                log.info(build.toString());
            }
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    long time1 = baseTime;

    public long getTime(long i) {
        if (i % 10 == 0) {
            time1 = time1 + i * 100 * 1000;
        } else {
            time1 = time1 + i * 1000;
        }
        return time1;
    }


    @Override
    public void cancel() {
        this.sourceSwitch = false;
    }
}


