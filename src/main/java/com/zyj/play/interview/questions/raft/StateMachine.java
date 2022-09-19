package com.zyj.play.interview.questions.raft;

import com.zyj.play.interview.questions.raft.entity.LogEntry;

/**
 * 状态机接口
 */
public interface StateMachine extends LifeCycle {
    /**
     * 将数据应用到状态机上
     *
     * @param logEntry
     */
    void apply(LogEntry logEntry);

    LogEntry get(String key);

    String getString(String key);

    void setString(String key, String value);

    void delString(String... key);

}
