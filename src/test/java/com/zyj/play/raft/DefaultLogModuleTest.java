package com.zyj.play.raft;

import com.zyj.play.interview.questions.raft.entity.Command;
import com.zyj.play.interview.questions.raft.entity.LogEntry;
import com.zyj.play.interview.questions.raft.impl.DefaultLogModule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultLogModuleTest {

    static DefaultLogModule defaultLogs = DefaultLogModule.getInstance();

    static {
        System.setProperty("serverPort", "8779");
        defaultLogs.dbDir = "./rocksDB-raft/" + System.getProperty("serverPort");
        defaultLogs.logsDir = defaultLogs.dbDir + "/logModule";
    }

    @Before
    public void setUp() throws Exception {
        System.setProperty("serverPort", "8777");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void write() {
        LogEntry entry = LogEntry.builder().
                term(1).
                command(Command.builder().key("hello").value("world").build()).
                build();
        defaultLogs.write(entry);

        Assert.assertEquals(entry, defaultLogs.read(entry.getIndex()));
    }

    @Test
    public void read() {
        System.out.println(defaultLogs.getLastIndex());
    }

    @Test
    public void remove() {
        defaultLogs.removeOnStartIndex(3L);
    }

    @Test
    public void getLast() {

    }

    @Test
    public void getLastIndex() {
    }

    @Test
    public void getDbDir() {
    }

    @Test
    public void getLogsDir() {
    }

    @Test
    public void setDbDir() {
    }
}
