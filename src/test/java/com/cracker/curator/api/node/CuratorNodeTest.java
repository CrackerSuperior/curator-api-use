package com.cracker.curator.api.node;

import com.cracker.curator.api.session.CuratorSession;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

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
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorSession curatorSession = new CuratorSession();
        CuratorFramework client = curatorSession.createClient(connectString, 5000, 5000, retryPolicy, "test");
        CuratorNode curatorNode = new CuratorNode(client);
        try {
            System.out.println(curatorNode.create(path));
            System.out.println(curatorNode.create(path2, "init".getBytes()));
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
