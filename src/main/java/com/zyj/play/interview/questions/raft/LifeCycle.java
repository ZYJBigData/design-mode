package com.zyj.play.interview.questions.raft;

/**
 * 生命周期
 */
public interface LifeCycle {
    /**
     * 初始化
     * @throws Throwable
     */
    void init() throws Throwable;

    /**
     * 关系资源
     * @throws Throwable
     */
    void close() throws Throwable;
}
