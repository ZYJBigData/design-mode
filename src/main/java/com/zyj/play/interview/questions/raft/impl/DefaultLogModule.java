package com.zyj.play.interview.questions.raft.impl;

import com.alibaba.fastjson.JSON;
import com.zyj.play.interview.questions.raft.LogModule;
import com.zyj.play.interview.questions.raft.entity.LogEntry;
import lombok.extern.slf4j.Slf4j;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.io.File;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
public class DefaultLogModule implements LogModule {
    /** public just for test */
    public String dbDir;
    public String logsDir;

    private RocksDB logDb;

    public final static byte[] LAST_INDEX_KEY = "LAST_INDEX_KEY".getBytes();

    ReentrantLock lock = new ReentrantLock();

    private DefaultLogModule() {
        if (dbDir == null) {
            dbDir = "./rocksDB-raft/" + System.getProperty("serverPort");
        }
        if (logsDir == null) {
            logsDir = dbDir + "/logModule";
        }
        RocksDB.loadLibrary();
        Options options = new Options();
        options.setCreateIfMissing(true);

        File file = new File(logsDir);
        boolean success = false;
        if (!file.exists()) {
            success = file.mkdirs();
        }
        if (success) {
            log.warn("make a new dir : " + logsDir);
        }
        try {
            logDb = RocksDB.open(options, logsDir);
        } catch (RocksDBException e) {
            log.warn(e.getMessage());
        }
    }

    public static DefaultLogModule getInstance() {
        return DefaultLogsLazyHolder.INSTANCE;
    }

    @Override
    public void init() throws Throwable {

    }

    @Override
    public void close() throws Throwable {
        logDb.close();
        log.info("destroy success");
    }

    private static class DefaultLogsLazyHolder {

        private static final DefaultLogModule INSTANCE = new DefaultLogModule();
    }

    /**
     * logEntry 的 index 就是 key. 严格保证递增.
     *
     * @param logEntry
     */
    @Override
    public void write(LogEntry logEntry) {

        boolean success = false;
        try {
            lock.tryLock(3000, MILLISECONDS);
            logEntry.setIndex(getLastIndex() + 1);
            logDb.put(logEntry.getIndex().toString().getBytes(), JSON.toJSONBytes(logEntry));
            success = true;
            log.info("DefaultLogModule write rocksDB success, logEntry info : [{}]", logEntry);
        } catch (RocksDBException | InterruptedException e) {
            log.warn(e.getMessage());
        } finally {
            if (success) {
                updateLastIndex(logEntry.getIndex());
            }
            lock.unlock();
        }
    }


    @Override
    public LogEntry read(Long index) {
        try {
            byte[] result = logDb.get(convert(index));
            if (result == null) {
                return null;
            }
            return JSON.parseObject(result, LogEntry.class);
        } catch (RocksDBException e) {
            log.warn(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void removeOnStartIndex(Long startIndex) {
        boolean success = false;
        int count = 0;
        try {
            lock.tryLock(3000, MILLISECONDS);
            for (long i = startIndex; i <= getLastIndex(); i++) {
                logDb.delete(String.valueOf(i).getBytes());
                ++count;
            }
            success = true;
            log.warn("rocksDB removeOnStartIndex success, count={} startIndex={}, lastIndex={}", count, startIndex, getLastIndex());
        } catch (InterruptedException | RocksDBException e) {
            log.warn(e.getMessage());
        } finally {
            if (success) {
                updateLastIndex(getLastIndex() - count);
            }
            lock.unlock();
        }
    }


    @Override
    public LogEntry getLast() {
        try {
            byte[] result = logDb.get(convert(getLastIndex()));
            if (result == null) {
                return null;
            }
            return JSON.parseObject(result, LogEntry.class);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long getLastIndex() {
        byte[] lastIndex = "-1".getBytes();
        try {
            lastIndex = logDb.get(LAST_INDEX_KEY);
            if (lastIndex == null) {
                lastIndex = "-1".getBytes();
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return Long.valueOf(new String(lastIndex));
    }

    private byte[] convert(Long key) {
        return key.toString().getBytes();
    }

    // on lock
    private void updateLastIndex(Long index) {
        try {
            // overWrite
            logDb.put(LAST_INDEX_KEY, index.toString().getBytes());
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }
}
