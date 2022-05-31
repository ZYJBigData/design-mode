package com.zyj.play.zookpeer.common;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZookpeerClient {
    public static String zkServerAddress = "192.168.10.189:2181";
    public static ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3, 5000);

    public static CuratorFramework getClient() {
        return CuratorFrameworkFactory.builder()
                .connectString(zkServerAddress)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
    }
}
