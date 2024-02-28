package com.zyj.play.interview.questions.jvm;

public class StackStructTest {
    private int a = 1;
    private static int c = 3;

    public static void main(String[] args) {
        int b = 2;
    }
    
    public StackStructTest() {
        a = 10;
        int d = 20;
    }
}
