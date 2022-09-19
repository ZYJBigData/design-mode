package com.zyj.play.interview.questions.raft.impl;

import com.zyj.play.interview.questions.raft.common.NodeStatus;
import com.zyj.play.interview.questions.raft.common.Peer;
import com.zyj.play.interview.questions.raft.entity.LogEntry;
import com.zyj.play.interview.questions.raft.membership.change.ClusterMembershipChanges;
import com.zyj.play.interview.questions.raft.membership.change.Result;
import com.zyj.play.interview.questions.raft.rpc.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 集群配置变更接口默认实现
 */
public class ClusterMembershipChangesImpl implements ClusterMembershipChanges {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterMembershipChangesImpl.class);


    private final DefaultNode node;

    public ClusterMembershipChangesImpl(DefaultNode node) {
        this.node = node;
    }

    /**
     * 必须是同步的,一次只能添加一个节点
     *
     * @param newPeer
     */
    @Override
    public synchronized Result addPeer(Peer newPeer) {
        // 已经存在
        if (node.peerSet.getPeersWithOutSelf().contains(newPeer)) {
            return new Result();
        }

        node.peerSet.getPeersWithOutSelf().add(newPeer);

        if (node.status == NodeStatus.LEADER) {
            node.nextIndexs.put(newPeer, 0L);
            node.matchIndexs.put(newPeer, 0L);

            for (long i = 0; i < node.logModule.getLastIndex(); i++) {
                LogEntry e = node.logModule.read(i);
                if (e != null) {
                    node.replication(newPeer, e);
                }
            }

            for (Peer ignore : node.peerSet.getPeersWithOutSelf()) {
                // TODO 同步到其他节点.
                Request request = Request.builder()
                        .cmd(Request.CHANGE_CONFIG_ADD)
                        .url(newPeer.getAddr())
                        .obj(newPeer)
                        .build();

                Result result = node.rpcClient.send(request);
                if (result != null && result.getStatus() == Result.Status.SUCCESS.getCode()) {
                    LOGGER.info("replication config success, peer : {}, newServer : {}", newPeer, newPeer);
                } else {
                    LOGGER.warn("replication config fail, peer : {}, newServer : {}", newPeer, newPeer);
                }
            }

        }

        return new Result();
    }


    /**
     * 必须是同步的,一次只能删除一个节点
     *
     * @param oldPeer
     */
    @Override
    public synchronized Result removePeer(Peer oldPeer) {
        node.peerSet.getPeersWithOutSelf().remove(oldPeer);
        node.nextIndexs.remove(oldPeer);
        node.matchIndexs.remove(oldPeer);

        return new Result();
    }
}
