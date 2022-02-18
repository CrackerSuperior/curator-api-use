package com.cracker.curator.api.cache.listenter;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;

public class CuratorCacheListen {

    private final CuratorCache curatorCache;

    public CuratorCacheListen(final CuratorFramework client, final String path) {
        this.curatorCache = CuratorCache.build(client, path, CuratorCache.Options.SINGLE_NODE_CACHE);
    }

    public void addListener(final CuratorCacheListener listener) {
        curatorCache.listenable().addListener(listener);
        curatorCache.start();
    }
}
