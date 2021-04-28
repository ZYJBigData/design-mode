package com.zyj.play.interview.questions.thirdquarter.javaee;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author zhangyingjie
 */
public class RegexDemo1 {
    public static void main(String[] args) {

        // Test Case 1:
        String str1 = "*";
        isValidIdentifier(str1);

        // Test Case 2:
        String str2 = "*dd";
        isValidIdentifier(str2);
//
//        // Test Case 3:
//        String str3 = "1geeks$";
//        isValidIdentifier(str3);
    }

    // Function to validate the identifier.
    public static void isValidIdentifier(String identifier)
    {
        try {
            Pattern p = Pattern.compile(identifier);
        }catch (PatternSyntaxException e){
            System.out.println("PatternSyntaxException: ");
            System.out.println("Description: "+ e.getDescription());
            System.out.println("Index: "+ e.getIndex());
            System.out.println("Message: "+ e.getMessage());
            System.out.println("Pattern: "+ e.getPattern());
        }
    }
}
