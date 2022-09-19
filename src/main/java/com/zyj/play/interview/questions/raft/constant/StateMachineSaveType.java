package com.zyj.play.interview.questions.raft.constant;

import com.zyj.play.interview.questions.raft.StateMachine;
import com.zyj.play.interview.questions.raft.impl.DefaultStateMachine;
import com.zyj.play.interview.questions.raft.impl.RedisStateMachine;

public enum StateMachineSaveType {
    /** sy */
    REDIS("redis", "redis存储", RedisStateMachine.getInstance()),
    ROCKS_DB("RocksDB", "RocksDB本地存储", DefaultStateMachine.getInstance())
    ;

    public StateMachine getStateMachine() {
        return this.stateMachine;
    }

    private String typeName;

    private String desc;

    private StateMachine stateMachine;

    StateMachineSaveType(String typeName, String desc, StateMachine stateMachine) {
        this.typeName = typeName;
        this.desc = desc;
        this.stateMachine = stateMachine;
    }

}