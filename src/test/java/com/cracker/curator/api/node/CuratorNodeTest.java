package com.cracker.curator.api.node;

import com.cracker.curator.api.session.CuratorSession;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class CuratorNodeTest {

    @Test
    public void create() {
        String connectString = "localhost:2181";
        String path = "/firstNode";
        String path2 = "/secondNode";
        String path3 = "/thirdNode/bbb";
        String path4 = "/fourthNode";
        String path5 = "/fifthNode";
        String path6 = "/sixthNode/ccc";
        String path7 = "/Node1";
        String path8 = "/Node2";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorSession curatorSession = new CuratorSession();
        CuratorFramework client = curatorSession.createClient(connectString, 5000, 5000, retryPolicy, "test");
        CuratorNode curatorNode = new CuratorNode(client);
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
        String connectString = "localhost:2181";
        String path = "/firstNode";
        String path2 = "/secondNode";
        String path3 = "/thirdNode/bbb";
        String path4 = "/fourthNode";
        String path5 = "/fifthNode";
        String path6 = "/sixthNode/ccc";
        String path7 = "/Node1";
        String path8 = "/Node2";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorSession curatorSession = new CuratorSession();
        CuratorFramework client = curatorSession.createClient(connectString, 5000, 5000, retryPolicy, "test");
        CuratorNode curatorNode = new CuratorNode(client);
        try{
            curatorNode.delete(path);
            curatorNode.delete(path3, true);
            curatorNode.delete(path4, 1);
            curatorNode.deleteGuaranteed(path2);
            curatorNode.deleteGuaranteed(path6);
            curatorNode.deleteGuaranteed(path5, true, 1);
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
        String connectString = "localhost:2181";
        String path1 = "/MyFirstZNode/MyFirstSubNode";
        String path2 = "/MyFirstZNode/MySecondSubNode";
        try {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorSession curatorSession = new CuratorSession();
            CuratorFramework client = curatorSession.createClient(connectString, 5000, 5000, retryPolicy);
            CuratorNode curatorNode = new CuratorNode(client);
            System.out.println(new String(curatorNode.getData(path1), StandardCharsets.UTF_8));
            System.out.println(curatorNode.getStat(path2).toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void setData() {
        String connectString = "localhost:2181";
        String path1 = "/MyFirstZNode/MyFirstSubNode";
        try {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorSession curatorSession = new CuratorSession();
            CuratorFramework client = curatorSession.createClient(connectString, 5000, 5000, retryPolicy);
            CuratorNode curatorNode = new CuratorNode(client);
            //curatorNode.setData(path1, "updated success!!!".getBytes());
            curatorNode.setData(path1, "updated success!!!!!!!!".getBytes(), 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
