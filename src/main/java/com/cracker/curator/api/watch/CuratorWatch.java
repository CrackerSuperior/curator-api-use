package com.cracker.curator.api.watch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;

public class CuratorWatch {

    private final CuratorFramework client;

    public CuratorWatch(final CuratorFramework client) {
        this.client = client;
    }

    public CuratorWatcher normalWatcher(final CuratorWatcher watcher) {
        return watcher;
    }
}
