package com.zyj.play.interview.questions.raft;

import com.zyj.play.interview.questions.raft.entity.LogEntry;

/**
 * 日志模块接口
 */
public interface LogModule extends  LifeCycle{
    void write(LogEntry logEntry);
    
    LogEntry read(Long index);

    void removeOnStartIndex(Long startIndex);

    LogEntry getLast();

    Long getLastIndex();
}
