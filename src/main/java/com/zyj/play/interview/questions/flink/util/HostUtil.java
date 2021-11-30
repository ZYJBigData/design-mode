package com.zyj.play.interview.questions.flink.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostUtil {
    public static void main(String[] args) throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        System.out.println(hostAddress);
    }
}
