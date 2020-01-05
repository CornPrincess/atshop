package com.lee.gmall.cart;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GmallCartServiceApplicationTest {

    @Before
    public void setUp() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        client = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);

        client.start();
    }
    /**
     * 客户端
     */
    private CuratorFramework client = null;

    /**
     * 创建节点
     *
     * @param path       路径
     * @param createMode 节点类型
     * @param data       节点数据
     * @return 是否创建成功
     */
    public boolean crateNode(String path, CreateMode createMode, String data)
    {
        try
        {
            client.create().withMode(createMode).forPath(path, data.getBytes());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Test
    public void main() {

        this.crateNode("/a",CreateMode.EPHEMERAL,"a" );


    }
}