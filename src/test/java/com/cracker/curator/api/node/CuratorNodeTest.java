package com.cracker.curator.api.node;

import com.cracker.curator.api.cache.listenter.CuratorCacheListen;
import com.cracker.curator.api.session.CuratorSession;
import com.cracker.curator.api.watch.CuratorWatch;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class CuratorNodeTest {

    CuratorFramework client;

    CuratorNode curatorNode;

    private void init(String namespace) {
        String connectString = "localhost:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorSession curatorSession = new CuratorSession();
        client = curatorSession.createClient(connectString, 5000, 5000, retryPolicy, namespace);
        curatorNode = new CuratorNode(client);
    }

    private void init() {
        String connectString = "localhost:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorSession curatorSession = new CuratorSession();
        client = curatorSession.createClient(connectString, 5000, 5000, retryPolicy);
        curatorNode = new CuratorNode(client);
    }

    private void close() {
        client.close();
    }

    @Test
    public void create() {
        String path = "/firstNode";
        String path2 = "/secondNode";
        String path3 = "/thirdNode/bbb";
        String path4 = "/fourthNode";
        String path5 = "/fifthNode";
        String path6 = "/sixthNode/ccc";
        String path7 = "/Node1";
        String path8 = "/Node2";
        init("test");
        try {
            System.out.println(curatorNode.create(path));
            System.out.println(curatorNode.create(path2, "init".getBytes()));
            System.out.println(curatorNode.create(path7, "Node7".getBytes()));
            System.out.println(curatorNode.create(path8, "Node8".getBytes()));
            System.out.println(curatorNode.create(path3, "path3".getBytes(), true));
            System.out.println(curatorNode.create(path4, CreateMode.PERSISTENT));
            System.out.println(curatorNode.create(path5, "path5".getBytes(), CreateMode.PERSISTENT));
            System.out.println(curatorNode.create(path6, "path6".getBytes(), CreateMode.PERSISTENT, true));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void delete() {
        String path = "/firstNode";
        String path2 = "/secondNode";
        String path3 = "/thirdNode/bbb";
        String path4 = "/fourthNode";
        String path5 = "/fifthNode";
        String path6 = "/sixthNode/ccc";
        String path7 = "/Node1";
        String path8 = "/Node2";
        init("test");
        try{
            curatorNode.delete(path);
            curatorNode.delete(path3, true);
            curatorNode.delete(path4, 0);
            curatorNode.deleteGuaranteed(path2);
            curatorNode.deleteGuaranteed(path6);
            curatorNode.deleteGuaranteed(path5, true, 0);
            curatorNode.delete(path7, (client1, event) -> {
                System.out.println("我被删除了~");
                System.out.println(event);
            });
            curatorNode.deleteGuaranteed(path8, (client1, event) -> {
                System.out.println("我被强制删除了~");
                System.out.println(event);
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getData() {
        String path1 = "/MyFirstZNode/MyFirstSubNode";
        String path2 = "/MyFirstZNode/MySecondSubNode";
        init();
        try {
            System.out.println(new String(curatorNode.getData(path1), StandardCharsets.UTF_8));
            System.out.println(curatorNode.getStat(path2).toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void setData() {
        String path1 = "/MyFirstZNode/MyFirstSubNode";
        init();
        try {
            //curatorNode.setData(path1, "updated success!!!".getBytes());
            curatorNode.setData(path1, "updated success!!!!!!!!".getBytes(), 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void exists() {
        String path1 = "/MyFirstZNode/MyFirstSubNode";
        init();
        try {
            System.out.println(curatorNode.exists(path1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getChildren() {
        String path = "/test";
        init();
        try {
            curatorNode.getChildren(path).forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * It only responds once.
     */
    @Test
    public void normalWatcher1() {
        init();
        CuratorWatch watch = new CuratorWatch(client);
        CuratorWatcher watcher = watch.normalWatcher(watchedEvent -> {
            System.out.println("Listen for an event: "+watchedEvent.toString());
        });
        try {
            String path = curatorNode.create("/listener","I'Listener".getBytes());
            String data = new String(client.getData().usingWatcher(watcher).forPath(path));
            System.out.println(path + " Value of a node: " + data);
            //first updated
            curatorNode.setData(path, " change listener ".getBytes());
            //second updated
            curatorNode.setData(path, " change listener! ".getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Event listeners are persistent.
     */
    @Test
    public void normalWatcher2() {
        init();
        CuratorWatch watch = new CuratorWatch(client);
        CuratorWatcher watcher = watch.normalWatcher(new CuratorWatcher() {
            @Override
            public void process(WatchedEvent watchedEvent) throws Exception {
                System.out.println("Listen for an event: "+ watchedEvent.toString());
                client.checkExists().usingWatcher(this).forPath("/listener");
            }
        });
        try {
            String path = curatorNode.create("/listener","I'Listener".getBytes());
            String data = new String(client.getData().usingWatcher(watcher).forPath(path));
            System.out.println(path + " Value of a node: " + data);
            //first updated
            curatorNode.setData(path, " change listener ".getBytes());
            //The thread must be put to sleep for a while, otherwise the listener will not have time to respond
            Thread.sleep(3000);
            //second updated
            curatorNode.setData(path, " change listener! ".getBytes());
            //System.in.read();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void curatorCacheListen() {
        init();
        String path = "/addListener";
        CountDownLatch countDownLatch = new CountDownLatch(3);
        CuratorCacheListen curatorCacheListen = new CuratorCacheListen(client, path);
        curatorCacheListen.addListener(CuratorCacheListener
                .builder().forAll((type, oldNode, newNode) -> {
                    System.out.println("event type: " + type + "\n\rold node" + oldNode + "\n\rnew node" + newNode);
//                .builder().forChanges((oldNode, newNode) -> {
//                    System.out.println("\n\rold node" + oldNode + "\n\rnew node" + newNode);
                }).forInitialized(() -> {
                    System.out.println("initialize");
                }).build());
        try {
            //for (int i = 0; i < 100; i++) {
                curatorNode.create(path);
                countDownLatch.countDown();
                //Thread.sleep(1);
                //curatorNode.setData(path, ("Hello" + i).getBytes());
            curatorNode.setData(path, "Hello".getBytes());
                countDownLatch.countDown();
                curatorNode.delete(path);
                countDownLatch.countDown();
                countDownLatch.await();
            //}
            //System.in.read();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
