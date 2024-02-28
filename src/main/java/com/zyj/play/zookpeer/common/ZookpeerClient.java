package com.zyj.play.zookpeer.common;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZookpeerClient {
    public static String zkServerAddress = "10.0.60.142:2181";
    ExponentialBackoffRetry exponentialBackoffRetry = new ExponentialBackoffRetry(1000, Integer.MAX_VALUE);
    public static ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3, 5000);
//    RetryOneTime retryOneTime = new RetryOneTime(1);
//    RetryNTimes nTime = new RetryNTimes(2, 2);
//    RetryUtilElapsed retryUtilElapsed = new RetryUtilElapsed();

    public static CuratorFramework getClient() {
        return CuratorFrameworkFactory.builder()
//                .ensembleProvider(new FixedEnsembleProvider("127.0.0.1"))
                .connectString(zkServerAddress)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
    }

    public static void main(String[] args) throws InterruptedException {
//        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
//        int result = Integer.parseInt(runtimeMXBean.getName().split("@")[0]);
//        System.out.println("result===={}" + result);
//        System.out.println("runtimeMXBean.getName()=={}" + runtimeMXBean.getName());
//        Thread.sleep(12000);
//        SystemInfo SI = new SystemInfo();
//        HardwareAbstractionLayer hal = SI.getHardware();
//        System.out.println("hal system==" + SI.getOperatingSystem());
//        GlobalMemory memory = hal.getMemory();
//        System.out.println("memory available==" + memory.getAvailable() / 1024.0 / 1024 / 1024 + "   swapUsed===" + memory.getSwapUsed() / 1024.0 / 1024 / 1024);
//        double availablePhysicalMemorySize = (memory.getAvailable() + memory.getSwapUsed()) / 1024.0 / 1024 / 1024;
//        System.out.println("availablePhysicalMemorySize==" + availablePhysicalMemorySize);
//        DecimalFormat df = new DecimalFormat("0.00");
//        df.setRoundingMode(RoundingMode.HALF_UP);
//        double value = Double.parseDouble(df.format(availablePhysicalMemorySize));
//        System.out.println("value===" + value);
//
//
//        double loadAverage = hal.getProcessor().getSystemLoadAverage();
//        df.setRoundingMode(RoundingMode.HALF_UP);
//        double v = Double.parseDouble(df.format(loadAverage));
//        System.out.println("cpu load==" + v);

//        System.out.println( (int) Math.floor((1.1 * 1 + 1.111 * 2 + 1.12345 * 7)));
//        DoubleNode doubleNode = new DoubleNode(1.0);
//        System.out.println(doubleNode.asText());
    }
}
