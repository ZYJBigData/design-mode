package com.zyj.play.interview.questions.raft.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 请求投票RPC参数
 */
@Getter
@Setter
@Builder
@Data
public class RvoteParam {
    /**
     * 候选人的任期号
     */
    private long term;

    /** 被请求者 ID(ip:selfPort) */
    private String serverId;

    /** 请求选票的候选人的 Id(ip:selfPort) */
    private String candidateId;

    /** 候选人的最后日志条目的索引值 */
    private long lastLogIndex;

    /** 候选人最后日志条目的任期号  */
    private long lastLogTerm;
}
