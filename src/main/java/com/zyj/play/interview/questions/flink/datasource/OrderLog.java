package com.zyj.play.interview.questions.flink.datasource;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyingjie
 */
@Data
public class OrderLog implements Serializable {
    private String orderId;
    private String skuId;
    private String priceType;
    private Long requestTime;
}
