package com.atsho.atservice.service;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class ZkServiceTest {
//    private static final String connectString = "localhost:2181";
    private static final String connectString = "mini1:2181";
    public static final int sessionTimeout=2000;
    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType()+"==="+watchedEvent.getPath());
                try {
                    zkClient.getChildren("/", true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void testCreate() throws KeeperException, InterruptedException {
        String nodeCreate = zkClient.create("/eclipse2", "hello".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(nodeCreate);
    }
    @Test
    public void testExist() throws KeeperException, InterruptedException {
        Stat stat = zkClient.exists("/eclipse2", false);
        System.out.println(stat);
    }
    @Test
    public void getChildren() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/", true);
        children.forEach(s -> System.out.println(s));
        Thread.sleep(Long.MAX_VALUE);
    }

}