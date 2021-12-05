package com.zyj.play.interview.questions.flink.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yingjiezhang
 */
public class ListUtils {
    private static final String JOB_ID_START = "(RUNNING)";
    private static final Logger logger= LoggerFactory.getLogger(ListUtils.class);
    private static final String flinkClusterAddress="10.0.90.81:8081";

    //1、交集
    public static void main(String[] args) {
        ListUtils.killStandaloneJob();
    }
    public static List<String> killStandaloneJob() {
        List<String> jobIds = new ArrayList<>();
        try {
            String cmd = String.format("flink list -m %s -r", flinkClusterAddress);
            logger.info("standalone,list all running  application={} ", cmd);
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor(20, TimeUnit.SECONDS);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                logger.info("line : {}", line);
                if (line.contains(JOB_ID_START)) {
                    String jobId = line.split(" : ")[1];
                    jobIds.add(jobId);
                    logger.info(" running job id : {}", jobId);
                }
            }
            logger.info("running job ids  : {} ", jobIds);
            bufferedReader.close();
            List<String> standaloneAppIds = Collections.singletonList("944a0ef44d27421ee50d80f1b58458d5");
            logger.info("get appId from log : {}", standaloneAppIds);
            List<String> jobIdsLast = new ArrayList<>(org.apache.commons.collections4.CollectionUtils.intersection(standaloneAppIds, jobIds));
            logger.info("really need stop job : {}", jobIdsLast);
            for (String jobId : jobIdsLast) {
                String killCmd = String.format("flink cancel -m %s  %s", flinkClusterAddress, jobId);
                logger.info("standalone, kill application cmd ={}", killCmd);
                Process killProcess = Runtime.getRuntime().exec(killCmd);
                process.waitFor(20, TimeUnit.SECONDS);
                BufferedReader errorInput = new BufferedReader(new InputStreamReader(killProcess.getErrorStream()));
                while ((line = errorInput.readLine()) != null) {
                    logger.error(line);
                }
                errorInput.close();
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return jobIds;
    }
}
