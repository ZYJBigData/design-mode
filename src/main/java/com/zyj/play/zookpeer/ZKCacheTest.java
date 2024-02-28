package com.zyj.play.zookpeer;

import com.zyj.play.zookpeer.common.Constant;
import com.zyj.play.zookpeer.common.ZookpeerClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.UnhandledErrorListener;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;

import java.nio.charset.StandardCharsets;

/**
 * 三种cache的区别
 * 所有的监听都是相互影响的~
 * <p>
 * 1.无论如何都会收到一个INITIALIZED事件的。
 * <p>
 * 2.无论是TreeCache、PathChildrenCache，所谓的监听都是本地视图和ZooKeeper服务器进行对比。
 * 所以如果ZooKeeper节点不为空的话，才会在缓存开始的时候监听到NODE_ADDED事件，这是因为刚开始本地缓存并没有内容，
 * 然后本地缓存和服务器缓存进行对比，发现ZooKeeper服务器有节点而本地缓存没有，这才将服务器的节点缓存到本地，所以才会触发NODE_ADDED事件
 */
public class ZKCacheTest {
    private static int num = 1;
    private static CuratorFramework client = ZookpeerClient.getClient();


    public static void main(String[] args) throws InterruptedException {
        init();
        treeCache();
//        pathChildrenCache();
//        nodeCache();
    }

    /**
     * 初始化操作，创建父节点
     */
    public static void init() {
        client.start();
        try {

            if (client.checkExists().forPath(Constant.PARENT_PATH) == null) {
                client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(Constant.PARENT_PATH, "This is Parent Data!".getBytes());
            }
            client.create().withMode(CreateMode.PERSISTENT).forPath(Constant.PARENT_PATH + "/c1", "This is C1".getBytes(StandardCharsets.UTF_8));//创建第一个节点。
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(Constant.PARENT_PATH + "/c1/c11", "This is worker1".getBytes(StandardCharsets.UTF_8));
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(Constant.PARENT_PATH + "/c1/c12", "This is worker2".getBytes(StandardCharsets.UTF_8));//创建第一个节点。
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(Constant.PARENT_PATH + "/c1/c13", "This is worker3".getBytes(StandardCharsets.UTF_8));//创建第一个节点。

            client.create().withMode(CreateMode.PERSISTENT).forPath(Constant.PARENT_PATH + "/c2", "This is C2".getBytes(StandardCharsets.UTF_8));//创建第一个节点。
            client.create().withMode(CreateMode.PERSISTENT).forPath(Constant.PARENT_PATH + "/c2/c21", "This is master1.".getBytes(StandardCharsets.UTF_8));//创建第一个节点。


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 监听根节点数据变化
     */
    public static void nodeCache() throws InterruptedException {
        final NodeCache nodeCache = new NodeCache(client, Constant.PARENT_PATH, false);
        try {
            nodeCache.start(true);//true代表缓存当前节点
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (nodeCache.getCurrentData() != null) {//只有start中的设置为true才能够直接得到
            System.out.println(num++ + ".nodeCache-------CurrentNode Data is:" + new String(nodeCache.getCurrentData().getData()) + "\n===========================\n");//输出当前节点的内容
        }
        
        //添加节点数据监听
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            public void nodeChanged() throws Exception {
                System.out.println(num++ + ".nodeCache------节点数据发生了改变，发生的路径为：" + nodeCache.getCurrentData().getPath() + ",节点数据发生了改变 ，新的数据为：" + new String(nodeCache.getCurrentData().getData()) + "\n===========================\n");
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }


    /**
     * 监听子节点变化,跟节点的变化一点也监控不到这里所为的根节点就是/dolphinscheduler_zyj 其的内容变更等等都不能监控到
     */
    public static void pathChildrenCache() throws InterruptedException {
        final PathChildrenCache pathChildrenCache = new PathChildrenCache(client, Constant.PARENT_PATH, true);
        try {
            /**
             * After cache is primed with initial values (in the background) a PathChildrenCacheEvent.Type.INITIALIZED will be posted.
             */
            pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);//启动模式
        } catch (Exception e) {
            e.printStackTrace();
        }
        //添加监听
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println(num++ + ".pathChildrenCache------发生的节点变化类型为：" + pathChildrenCacheEvent.getType() + ",发生变化的节点内容为：" + new String(pathChildrenCacheEvent.getData().getData()) + "\n======================\n");
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 同时监听数据变化和子节点变化。
     */
    public static void treeCache() throws InterruptedException {
        TreeCache treeCache = new TreeCache(client, Constant.PARENT_PATH);

        try {
            treeCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //添加错误监听器
        treeCache.getUnhandledErrorListenable().addListener(new UnhandledErrorListener() {
            public void unhandledError(String s, Throwable throwable) {
                System.out.println(".错误原因：" + throwable.getMessage() + "\n==============\n");
            }
        });

        //节点变化的监听器
        treeCache.getListenable().addListener(new TreeCacheListener() {
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                System.out.println("treeCache ------ Type:" + treeCacheEvent.getType() + ",");
                System.out.println(treeCacheEvent.getData().getPath());

            }
        });

//        //节点变化的监听器
//        treeCache.getListenable().addListener(new TreeCacheListener() {
//            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
//                System.out.println("treeCache ------ Type:" + treeCacheEvent.getType() + ",");
//                System.out.println(treeCacheEvent.getData().getPath());
//
//            }
//        });
        Thread.sleep(Integer.MAX_VALUE);
    }
}