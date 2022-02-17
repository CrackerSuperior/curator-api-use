package com.cracker.curator.api.node;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

@SuppressWarnings("unused")
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

    /**
     * Deleting a node.
     *
     * <p>Note that this method can only remove the leaf node, otherwise an exception will be thrown.
     * @param path ZNode path
     * @return void
     * @throws Exception exception
     */
    public Void delete(final String path) throws Exception {
        return client.delete().forPath(path);
    }

    /**
     * Delete a node and recursively delete all of its children.
     * @param path ZNode path
     * @param deletingChildrenIfNeeded Whether to recursively delete all of its children
     * @return void
     * @throws Exception exception
     */
    @SuppressWarnings("all")
    public Void delete(final String path, final boolean deletingChildrenIfNeeded) throws Exception {
        return deletingChildrenIfNeeded
                ? client.delete().deletingChildrenIfNeeded().forPath(path)
                : delete(path);
    }

    /**
     * Deletes a node to force the specified version to be deleted.
     * @param path ZNode path
     * @param version specify the version
     * @return void
     * @throws Exception exception
     */
    @SuppressWarnings("all")
    public Void delete(final String path, final int version) throws Exception {
        return client.delete().withVersion(version).forPath(path);
    }

    /**
     * A callback function after a node is deleted.
     * @param path ZNode path
     * @param callback callback function
     * @return void
     * @throws Exception exception
     */
    @SuppressWarnings("all")
    public Void delete(final String path, final BackgroundCallback callback) throws Exception {
        return client.delete().inBackground(callback).forPath(path);
    }

    /**
     * Deleting a node is mandatory.
     *
     * <p>A guaranteed() interface is a safeguard, and as long as a client session is valid, a Curator will continue to
     * remove nodes in the background until they are successfully removed.
     * @param path ZNode path
     * @return void
     * @throws Exception exception
     */
    public Void deleteGuaranteed(final String path) throws Exception {
        return client.delete().guaranteed().forPath(path);
    }

    public Void deleteGuaranteed(final String path, final boolean deletingChildrenIfNeeded) throws Exception {
        return deletingChildrenIfNeeded
                ? client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path)
                : deleteGuaranteed(path);
    }

    @SuppressWarnings("all")
    public Void deleteGuaranteed(final String path, final boolean deletingChildrenIfNeeded, final int version) throws Exception {
        return deletingChildrenIfNeeded
                ? client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(version).forPath(path)
                : client.delete().guaranteed().withVersion(version).forPath(path);
    }

    @SuppressWarnings("all")
    public Void deleteGuaranteed(final String path, final BackgroundCallback callback) throws Exception {
        return client.delete().guaranteed().inBackground(callback).forPath(path);
    }

    /**
     * Reads the data content of a node.
     * @param path ZNode path
     * @return node data
     * @throws Exception exception
     */
    public byte[] getData(final String path) throws Exception {
        return client.getData().forPath(path);
    }

    /**
     * The stat for this node is obtained.
     * @param path ZNode path
     * @return The stat for this node is obtained
     * @throws Exception exception
     */
    public Stat getStat(final String path) throws Exception {
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        return stat;
    }

    /**
     * Updates the data content of a node.
     * @param path ZNode path
     * @param data updated data
     * @return The stat for this node is obtained
     * @throws Exception exception
     */
    public Stat setData(final String path, final byte[] data) throws Exception {
        return client.setData().forPath(path, data);
    }

    /**
     * Updates the data content of a node to force the specified version to be updated.
     * @param path ZNode path
     * @param data updated data
     * @param version node version
     * @return The stat for this node is obtained
     * @throws Exception exception
     */
    @SuppressWarnings("all")
    public Stat setData(final String path, final byte[] data, final int version) throws Exception {
        return client.setData().withVersion(version).forPath(path, data);
    }

    /**
     * Check whether the node exists.
     * @param path ZNode path
     * @return Check whether the node exists
     * @throws Exception exception
     */
    public boolean exists(final String path) throws Exception {
        return client.checkExists().forPath(path) != null;
    }
}
