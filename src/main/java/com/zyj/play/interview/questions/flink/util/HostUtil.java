package com.zyj.play.interview.questions.flink.util;

import java.io.File;
import java.net.UnknownHostException;

public class HostUtil {
    public static void main(String[] args) throws UnknownHostException {
      File f = new File("/tmp/dolphinscheduler/exec/process/1/14/94/176/standalone.list");
      if (!f.exists()){
          System.out.println("*********");
      }
    }
}
