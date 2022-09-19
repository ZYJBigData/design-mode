package com.zyj.play.interview.questions.raft.client;

import com.zyj.play.interview.questions.raft.current.SleepHelper;
import com.zyj.play.interview.questions.raft.entity.LogEntry;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 莫那·鲁道
 */
@Slf4j
public class RaftClient1 {

    public static void main(String[] args) throws Throwable {

        RaftClientRPC rpc = new RaftClientRPC();

        for (int i = 0; i < 3; i++) {
            try {
                String key = "hello:" + i + "\n";
                String value = "world:" + i + "\n";

                String putResult = rpc.put(key, value);

                log.error("key = {}, value = {}, put response : {}", key, value, putResult);

                SleepHelper.sleep(1000);
//
                LogEntry logEntry = rpc.get(key);

                log.error("key = {}, value = {}, get response : {}", key, value, logEntry);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            SleepHelper.sleep(1000);
        }

        RaftClientRPC.CLIENT.close();
    }
}
