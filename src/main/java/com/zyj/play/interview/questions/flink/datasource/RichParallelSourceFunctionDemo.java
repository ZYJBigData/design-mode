package com.zyj.play.interview.questions.flink.datasource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

/**
 * @author zhangyingjie
 */
public class RichParallelSourceFunctionDemo extends RichParallelSourceFunction<OrderLog> {
    private volatile boolean running = true;
    private OrderLog orderLog = new OrderLog();


    @Override
    public void run(SourceContext<OrderLog> ctx) throws Exception {
        while (running) {
            String orderId = RandomStringUtils.random(17, new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'});
            String skuId = RandomStringUtils.random(7, new char[]{'1', '2', '3', '4', '5', '6', '7'});
            String priceType = RandomStringUtils.random(6, new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'
                    , 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'});
            orderLog.setOrderId(orderId);
            orderLog.setSkuId(skuId);
            orderLog.setPriceType(priceType);
            orderLog.setRequestTime(System.currentTimeMillis());
            ctx.collect(orderLog);
            Thread.sleep(5000);
            orderLog.setRequestTime(System.currentTimeMillis() - 10000);
            ctx.collect(orderLog);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}
