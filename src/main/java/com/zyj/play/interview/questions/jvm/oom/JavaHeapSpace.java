package com.zyj.play.interview.questions.jvm.oom;

import java.util.Random;

/**
 * @author zhangyingjie
 */
public class JavaHeapSpace {
    public static void main(String[] args) {
        String str = "atBS";
        try {
            while (true) {
                str += str + new Random().nextInt(111111) + new Random().nextInt(222222);
                str.intern();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }
}
