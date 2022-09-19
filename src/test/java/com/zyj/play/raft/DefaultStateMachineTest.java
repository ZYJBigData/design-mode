package com.zyj.play.raft;

import com.zyj.play.interview.questions.raft.entity.Command;
import com.zyj.play.interview.questions.raft.entity.LogEntry;
import com.zyj.play.interview.questions.raft.impl.DefaultStateMachine;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.rocksdb.RocksDBException;

@Slf4j
public class DefaultStateMachineTest {
    static DefaultStateMachine machine = DefaultStateMachine.getInstance();

    static {
        System.setProperty("serverPort", "8777");
        machine.dbDir = "./rocksDB-raft/" + System.getProperty("serverPort");
        machine.stateMachineDir = machine.dbDir + "/stateMachine";
    }

    @Before
    public void before() {
        machine = DefaultStateMachine.getInstance();
    }

    @Test
    public void apply() {
        LogEntry logEntry = LogEntry.builder().term(1).command(Command.builder().key("hello").value("value1").build()).build();
        log.error("logEntry : ={}", logEntry);
        //将日志应用到状态机上
        machine.apply(logEntry);
        //用key值获取value
        LogEntry hello = machine.get("hello");
        log.error("hello==={}", hello);
    }


    @Test
    public void applyRead() throws RocksDBException {

        System.out.println(machine.get("hello:7"));
    }
}