package com.zyj.play.zookpeer.common;

import org.apache.curator.framework.CuratorFramework;

public class UpdateData {

    public static void main(String[] args) {
        CuratorFramework client = ZookpeerClient.getClient();
        client.start();
        updateData(client);
    }

    /**
     * 创建节点、修改数据、删除节点等操作，用来给其他的监听器测试使用
     */
    public static void updateData(CuratorFramework client) {
        try {
            client.delete().forPath(Constant.PARENT_PATH + "/c1/c11");//删除一个master节点。


//            client.create().withMode(CreateMode.EPHEMERAL).forPath(Constant.PARENT_PATH + "/c3", "This is C3.".getBytes(StandardCharsets.UTF_8));//创建第一个节点。
//            client.setData().forPath(Constant.PARENT_PATH + "/c2", "This is New C2.".getBytes());//修改子节点数据。
//            client.delete().forPath(Constant.PARENT_PATH + "/c2");//删除一个节点。
//            client.setData().forPath(Constant.PARENT_PATH,"This is root Data!".getBytes(StandardCharsets.UTF_8)); //修改父节点的数据。
//            client.delete().deletingChildrenIfNeeded().forPath(Constant.PARENT_PATH);//将父节点下所有内容删除
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
