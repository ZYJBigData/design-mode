package com.zyj.play.interview.questions.flink.util;

/**
 * @author yingjiezhang
 */
public class StringBuilderUtils {
    private static final String JOB_ID_START = "(RUNNING)";

    public static void main(String[] args) {
        StringBuilder sb=new StringBuilder();
        sb.append("------------------ Running/Restarting Jobs -------------------").append("\r\n");
        sb.append("26.11.2021 11:32:38 : d0751b7b032718727b5992ab90fb1a15 : CarTopSpeedWindowingExample (RUNNING)").append("\r\n");
        sb.append("--------------------------------------------------------------").append("\r\n");
        System.out.println("value=={}"+sb.toString());

        if (sb.toString().contains(JOB_ID_START)){
            String s = sb.toString().split(" : ")[1];
            System.out.println(s);
        }

    }
}
