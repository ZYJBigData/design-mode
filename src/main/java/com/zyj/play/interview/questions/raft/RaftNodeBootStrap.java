package com.zyj.play.interview.questions.raft;

import com.zyj.play.interview.questions.raft.common.NodeConfig;
import com.zyj.play.interview.questions.raft.constant.StateMachineSaveType;
import com.zyj.play.interview.questions.raft.impl.DefaultNode;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * -DserverPort=8775
 * -DserverPort=8776
 * -DserverPort=8777
 * -DserverPort=8778
 * -DserverPort=8779
 */
@Slf4j
public class RaftNodeBootStrap {

    public static void main(String[] args) throws Throwable {
        boot();
    }

    public static void boot() throws Throwable {
        String[] peerAddr = {"localhost:8775", "localhost:8776", "localhost:8777", "localhost:8778", "localhost:8779"};

        NodeConfig config = new NodeConfig();

        // 自身节点
        config.setSelfPort(Integer.parseInt(System.getProperty("serverPort", "8779")));

        // 其他节点地址
        config.setPeerAddrs(Arrays.asList(peerAddr));
        config.setStateMachineSaveType(StateMachineSaveType.ROCKS_DB);

        Node node = DefaultNode.getInstance();
        node.setConfig(config);

        node.init();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            synchronized (node) {
                node.notifyAll();
            }
        }));

        log.info("gracefully wait");

        synchronized (node) {
            node.wait();
        }

        log.info("gracefully stop");
        node.close();
    }

}