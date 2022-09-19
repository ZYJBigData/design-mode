package com.zyj.play.interview.questions.raft.rpc;

import com.alipay.remoting.exception.RemotingException;
import com.zyj.play.interview.questions.raft.exception.RaftRemotingException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class DefaultRpcClient implements RpcClient {

    private final static com.alipay.remoting.rpc.RpcClient CLIENT = new com.alipay.remoting.rpc.RpcClient();

    @Override
    public <R> R send(Request request) {
        return send(request, (int) TimeUnit.SECONDS.toMillis(30));
    }

    @Override
    public <R> R send(Request request, int timeout) {
        Response<R> result;
        try {
            result = (Response<R>) CLIENT.invokeSync(request.getUrl(), request, timeout);
            return result.getResult();
        } catch (RemotingException e) {
            throw new RaftRemotingException("rpc RaftRemotingException ", e);
        } catch (InterruptedException e) {
            throw new RaftRemotingException("rpc InterruptedException ", e);
            // ignore
        }
    }

    @Override
    public void init() {
        CLIENT.init();
    }

    @Override
    public void close() {
        CLIENT.shutdown();
        log.info("destroy success");
    }
}