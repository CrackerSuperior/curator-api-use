package com.cracker.curator.api.session;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;

public class CuratorSession {

    /**
     * Initialize the Client.
     * @param connectString zookeeper paths
     * @param sessionTimeoutMs session timeout
     * @param connectionTimeoutMs connection timeout
     * @param retryPolicy retry policy
     * @param namespace If you specify a Zookeeper root path, all the operations based on the client are under the namespace node
     * @return initialize the client
     */
    public CuratorFramework createClient(final String connectString, final int sessionTimeoutMs, final int connectionTimeoutMs, final RetryPolicy retryPolicy, final String namespace) {
        return CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(sessionTimeoutMs)
                .connectionTimeoutMs(connectionTimeoutMs)
                .retryPolicy(retryPolicy)
                .namespace(namespace)
                .build();
    }

    /**
     * Initialize the Client.
     * @param connectString zookeeper connectString:"[path:port]+"
     * @param sessionTimeoutMs session timeout
     * @param connectionTimeoutMs connection timeout
     * @param retryPolicy retry policy
     * @return initialize the client
     */
    public CuratorFramework createClient(final String connectString, final int sessionTimeoutMs, final int connectionTimeoutMs, final RetryPolicy retryPolicy) {
        return CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(sessionTimeoutMs)
                .connectionTimeoutMs(connectionTimeoutMs)
                .retryPolicy(retryPolicy)
                .build();
    }
}
