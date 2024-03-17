package com.example.jdbc.pool.impl;

import com.example.jdbc.pool.IConnectionPool;
import com.example.jdbc.pool.config.DataSourceConfig;
import com.example.jdbc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @author root
 * @packageName: com.example.jdbc.pool.impl
 * @className: ConnectionPool
 * @description:
 * @date 2024/3/17 16:10
 */
public class ConnectionPool implements IConnectionPool {

    //空闲连接池（直接拿来用）
    private List<Connection> freePool = new ArrayList<>();

    //正在使用的连接池
    private List<Connection> usePool = new ArrayList<>();

    //连接计数器，每创建一个新连接就要记录一次，只有在确保线程安全的情况下，计数器才能正确反映已创建的连接数
    private Integer connCount = 0;

    //配置文件对象
    private DataSourceConfig dataSourceConfig;

    public ConnectionPool(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
        //初始化
        init();
    }

    /**
     * 初始化连接池
     */
    public void init() {
        //创建数据库连接
        for (int i = 0; i < Integer.parseInt(dataSourceConfig.getInitSize()); i++) {

            freePool.add(createConnection());
        }
    }

    /**
     * 创建连接（获取连接不是一个原子性的操作，需要加锁确保线程安全）
     */
    public synchronized Connection createConnection() {

        Connection connection = null;

        //调用驱动类创建数据库连接
        try {
            connection = JDBCUtils.getConnection();
            connCount++;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return connection;

    }

    /**
     * 获取连接（该操作是非原子性的，也会出现线程安全问题，需要加锁，如果不加锁，就可能会出现总连接数超过最大连接数的情况）
     */
    @Override
    public synchronized Connection getConnection() {

        Connection connection = null;

        try {
            //空闲连接池有，就直接拿来用
            if (!freePool.isEmpty()) {
                connection = freePool.get(0);
                //将该链接直接移出空闲池
                freePool.remove(connection);
                //将该链接移入激活池
                usePool.add(connection);

                return connection;
            }

            //没有空闲连接，在不超最大连接数的情况下，创建新连接
            if (connCount < Integer.parseInt(dataSourceConfig.getMaxSize())) {
                return createConnection();
            }

            //达到最大连接数，不能再创建新连接，原地等待，等其他线程归还连接，再唤醒
            this.wait(Long.parseLong(dataSourceConfig.getWaittime()));
            //被唤醒后，继续请求连接
            connection = getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    @Override
    public synchronized void releaseConnection(Connection connection) {

        try {
            //判断连接是否可用
            if (connection == null || connection.isClosed()) {
                return;
            }

            freePool.add(connection);
            //从激活池中移除
            usePool.remove(connection);
            //归还之后马上唤醒等待的线程
            this.notifyAll();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
