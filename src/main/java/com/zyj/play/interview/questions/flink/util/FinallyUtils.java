package com.zyj.play.interview.questions.flink.util;

public class FinallyUtils {
    public static void main(String[] args) {
        String count ="1";
        try {
            System.out.println("The count is " + Integer.parseInt(count));
        } catch (NumberFormatException e) {
            System.out.println("No count");
        } finally {
            System.out.println("In finally");
        }
    }
}
