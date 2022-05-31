package com.zyj.play.interview.questions.spark;

import java.io.IOException;

/**
 * @author yingjiezhang
 */
public class TestKill {
    public static void main(String[] args) throws IOException, InterruptedException {
//        String cmd="cmd.exe kill -9 33846";
//        Process process= new ProcessBuilder(cmd).start();
//        List<String> command = new ArrayList<>();
//        //init process builder
//        ProcessBuilder processBuilder = new ProcessBuilder();
//        // setting up a working directory
//        processBuilder.directory(new File("/Users/yingjiezhang/IdeaProjects/design-mode"));
//        // merge error information to standard output stream
//        processBuilder.redirectErrorStream(true);
//        command.add("sh");
//        command.add("test.sh");
//        // setting commands
//        processBuilder.command(command);
//        Process process = processBuilder.start();
//        BufferedReader bufferedReader =
//                new BufferedReader(new InputStreamReader(new SequenceInputStream(process.getInputStream(), process.getErrorStream())));
//        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
//            System.out.println(line);
//        }
//
//        Thread.sleep(20000);
        String test="{" +
                "\"mainArgs\":\"\", " +
                "\"driverMemory\":\"1G\", " +
                "\"executorMemory\":\"2G\", " +
                "\"programType\":\"SCALA\", " +
                "\"mainClass\":\"basicetl.GlobalUserCar\", " +
                "\"driverCores\":\"2\", " +
                "\"deployMode\":\"cluster\", " +
                "\"executorCores\":2, " +
                "\"mainJar\":{\"res\":\"test-1.0-SNAPSHOT.jar\"}, " +
                "\"sparkVersion\":\"SPARK1\", " +
                "\"numExecutors\":\"10\", " +
                "\"localParams\":[], " +
                "\"others\":\"\", " +
                "\"resourceList\":[]" +
                "}";
        System.out.println(test);
    }
}
