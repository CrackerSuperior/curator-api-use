package com.cracker.curator.api.node;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

public class CuratorNode {

    private final CuratorFramework client;

    public CuratorNode(final CuratorFramework client) {
        this.client = client;
        this.client.start();
    }

    /**
     * Create a node with initial empty content.
     * @param path ZNode path
     * @return context
     * @throws Exception exception
     */
    public String create(final String path) throws Exception {
        return client.create().forPath(path);
    }

    /**
     * Create a node with initialization.
     * @param path ZNode path
     * @param data node data
     * @return context
     * @throws Exception exception
     */
    public String create(final String path, final byte[] data) throws Exception {
        return client.create().forPath(path, data);
    }

    /**
     * Creates a node with the creation mode specified and the content empty.
     * @param path ZNode path
     * @param createMode create mode
     * @return context
     * @throws Exception exception
     */
    public String create(final String path, final CreateMode createMode) throws Exception {
        return client.create().withMode(createMode).forPath(path);
    }

    /**
     * Creates a node with the creation mode specified and the content empty.
     * @param path ZNode path
     * @param data node data
     * @param createMode create mode
     * @return context
     * @throws Exception exception
     */
    public String create(final String path, final byte[] data, final CreateMode createMode) throws Exception {
        return client.create().withMode(createMode).forPath(path, data);
    }

    /**
     * Create a node, attach initialization content, and automatically recursively create the parent node.
     * @param path ZNode path
     * @param data node data
     * @param creatingParentContainersIfNeeded Whether you need to automatically recursively create a parent node
     * @return context
     * @throws Exception exception
     */
    public String create(final String path, final byte[] data, final boolean creatingParentContainersIfNeeded) throws Exception {
        return creatingParentContainersIfNeeded
                ? client.create().creatingParentContainersIfNeeded().forPath(path, data)
                : create(path, data);
    }

    /**
     * Create a node, specify the creation mode, attach initialization content, and automatically recursively create the parent node.
     * @param path ZNode path
     * @param data node data
     * @param createMode create mode
     * @param creatingParentContainersIfNeeded Whether you need to automatically recursively create a parent node
     * @return context
     * @throws Exception exception
     */
    public String create(final String path, final byte[] data, final CreateMode createMode, final boolean creatingParentContainersIfNeeded) throws Exception {
        return creatingParentContainersIfNeeded
                ? client.create().creatingParentContainersIfNeeded().withMode(createMode).forPath(path, data)
                : create(path, data, createMode);
    }
}
