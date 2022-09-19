package com.zyj.play.interview.questions.raft.rpc;

import com.zyj.play.interview.questions.raft.LifeCycle;

/**
 * Rpc Server 接口
 */
public interface RpcService extends LifeCycle {

    /**
     * 处理请求.
     * @param request 请求参数.
     * @return 返回值.
     */
    Response<?> handlerRequest(Request request);
}
