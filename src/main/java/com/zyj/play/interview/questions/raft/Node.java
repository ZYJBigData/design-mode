package com.zyj.play.interview.questions.raft;

import com.zyj.play.interview.questions.raft.client.ClientKVAck;
import com.zyj.play.interview.questions.raft.client.ClientKVReq;
import com.zyj.play.interview.questions.raft.common.NodeConfig;
import com.zyj.play.interview.questions.raft.entity.AentryParam;
import com.zyj.play.interview.questions.raft.entity.AentryResult;
import com.zyj.play.interview.questions.raft.entity.RvoteParam;
import com.zyj.play.interview.questions.raft.entity.RvoteResult;

/**
 * 节点，raft抽象的机器节点
 */
public interface Node extends LifeCycle {

    /**
     * 设置配置文件.
     *
     * @param config
     */
    void setConfig(NodeConfig config);

    /**
     * 处理请求投票 RPC.
     *
     * @param param
     * @return
     */
    RvoteResult handlerRequestVote(RvoteParam param);

    /**
     * 处理附加日志请求.
     *
     * @param param
     * @return
     */
    AentryResult handlerAppendEntries(AentryParam param);

    /**
     * 处理客户端请求.
     *
     * @param request
     * @return
     */
    ClientKVAck handlerClientRequest(ClientKVReq request);

    /**
     * 转发给 leader 节点.
     * @param request
     * @return
     */
    ClientKVAck redirect(ClientKVReq request);
}
