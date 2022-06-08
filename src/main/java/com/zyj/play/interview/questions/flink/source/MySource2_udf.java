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
public class MySource2_udf extends RichParallelSourceFunction<LearnPojo> implements BaseSource {

    private boolean sourceSwitch = true;
    private final AtomicLong count = new AtomicLong(0);
    private boolean switchPrintLog = true;

    public MySource2_udf(boolean switchPrintLog) {
        System.out.println("create-source2");
        this.switchPrintLog = switchPrintLog;
    }

    @Override
    public void run(SourceContext<LearnPojo> sourceContext) {

        while (sourceSwitch) {
            long i = count.addAndGet(1L);
//            LearnPojo build = LearnPojo.builder(i,"pojo_"+(i % 3))
            LearnPojo build = LearnPojo.builder(i, "source_1")
                    .time(baseTime + i * 1000)
                    .value(1L)
                    .index(i)
                    .build();
            sourceContext.collect(build);
            if (switchPrintLog) {
                log.info(build.toString());
            }
            try {
                Thread.sleep(1000L);
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
