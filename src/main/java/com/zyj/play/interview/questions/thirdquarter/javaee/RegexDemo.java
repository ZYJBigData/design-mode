package com.zyj.play.interview.questions.thirdquarter.javaee;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author zhangyingjie
 */
public class RegexDemo {
    public static void main(String[] args){
        String content = "I am noob " +
                "from runoob.com.";

        String pattern = "\\*runoob.*";
        try {
            Pattern xx = Pattern.compile(pattern);
        } catch(PatternSyntaxException e){
            System.out.println("PatternSyntaxException: ");
            System.out.println("Description: "+ e.getDescription());
            System.out.println("Index: "+ e.getIndex());
            System.out.println("Message: "+ e.getMessage());
            System.out.println("Pattern: "+ e.getPattern());
        }
    }
}
