package com.zyj.play.interview.questions.raft.common;

import com.zyj.play.interview.questions.raft.constant.StateMachineSaveType;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class NodeConfig {

    /** 自身 selfPort */
    public int selfPort;

    /** 所有节点地址. */
    public List<String> peerAddrs;
    /**
     *  状态快照存储类型
     */
    public StateMachineSaveType stateMachineSaveType;
}