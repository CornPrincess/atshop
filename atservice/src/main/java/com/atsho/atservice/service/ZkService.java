package com.atsho.atservice.service;

import org.apache.zookeeper.*;

import java.io.IOException;

public class ZkService {

    private static final String connectString = "mini1:2181,mini2:2181,mini3:2181";
    public static final int sessionTimeout=2000;
    private ZooKeeper zkClient=null;

    public void init(){
        try {
            zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {

                    System.out.println(watchedEvent.getType()+"==="+watchedEvent.getPath());
                }
            });

            //数据的增删改查
            String nodeCreate = zkClient.create("/eclipse", "hello".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public void getConnect(){
        
    }

    public static void main(String[] args) {

    }

}
