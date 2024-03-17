package com.example.test;

import com.example.jdbc.pool.config.DataSourceConfig;
import com.example.jdbc.pool.impl.ConnectionPool;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * @author root
 * @packageName: com.example.test
 * @className: PoolTest
 * @description: 测试数据库连接池
 * @date 2024/3/17 16:12
 */
public class PoolTest {

    /**
     * 测试获取链接
     */
    @Test
    public void testGetConn() {

        //创建数据库连接池对象
        ConnectionPool pool = new ConnectionPool(new DataSourceConfig());
        //获取连接
        for (int i = 0; i < 6; i++) {
            System.out.println(pool.getConnection());
        }
    }

    /**
     * 测试归还连接
     */
    @Test
    public void testReleaseConn() {
        //创建数据库连接池对象
        ConnectionPool pool = new ConnectionPool(new DataSourceConfig());

        ArrayList<Connection> connections = new ArrayList<>();
        //获取连接
        for (int i = 0; i < 6; i++) {
            connections.add(pool.getConnection());
        }
        //归还连接
        for (int i = 0; i < 6; i++) {
            pool.releaseConnection(connections.get(i));
        }

        System.out.println(pool.getConnection());
    }
}
