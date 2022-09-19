package com.zyj.play.interview.questions.raft.rpc;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.RpcServer;
import com.zyj.play.interview.questions.raft.client.ClientKVReq;
import com.zyj.play.interview.questions.raft.common.Peer;
import com.zyj.play.interview.questions.raft.entity.AentryParam;
import com.zyj.play.interview.questions.raft.entity.RvoteParam;
import com.zyj.play.interview.questions.raft.impl.DefaultNode;
import com.zyj.play.interview.questions.raft.membership.change.ClusterMembershipChanges;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultRpcServiceImpl implements RpcService {

    private final DefaultNode node;

    private final RpcServer rpcServer;

    public DefaultRpcServiceImpl(int port, DefaultNode node) {
        rpcServer = new RpcServer(port, false, false);
        rpcServer.registerUserProcessor(new RaftUserProcessor<Request>() {

            @Override
            public Object handleRequest(BizContext bizCtx, Request request) {
                return handlerRequest(request);
            }
        });

        this.node = node;
    }


    @Override
    public Response<?> handlerRequest(Request request) {
        //请求投票
        if (request.getCmd() == Request.R_VOTE) {
            return new Response<>(node.handlerRequestVote((RvoteParam) request.getObj()));
            //附加日志
        } else if (request.getCmd() == Request.A_ENTRIES) {
            return new Response<>(node.handlerAppendEntries((AentryParam) request.getObj()));
            //客户端请求
        } else if (request.getCmd() == Request.CLIENT_REQ) {
            return new Response<>(node.handlerClientRequest((ClientKVReq) request.getObj()));
            //配置变更
        } else if (request.getCmd() == Request.CHANGE_CONFIG_REMOVE) {
            return new Response<>(((ClusterMembershipChanges) node).removePeer((Peer) request.getObj()));
            //配置变更
        } else if (request.getCmd() == Request.CHANGE_CONFIG_ADD) {
            return new Response<>(((ClusterMembershipChanges) node).addPeer((Peer) request.getObj()));
        }
        return null;
    }


    @Override
    public void init() {
        rpcServer.start();
    }

    @Override
    public void close() {
        rpcServer.stop();
        log.info("destroy success");
    }
}
