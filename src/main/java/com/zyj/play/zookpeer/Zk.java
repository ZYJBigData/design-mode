package com.zyj.play.zookpeer;

import com.zyj.play.zookpeer.common.ZookpeerClient;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;

public class Zk {
    public static void main(String[] args) throws Exception {
        CuratorFramework zkClient = ZookpeerClient.getClient();
        zkClient.start();
        List<String> list = zkClient.getChildren().forPath("/dolphinscheduler/nodes/master");
        String data = new String(zkClient.getData().forPath("/dolphinscheduler/nodes/master/10.0.90.79:5678")).split(",")[6];
        System.out.println(data);
        zkClient.close();
    }
}
