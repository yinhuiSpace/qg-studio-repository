package com.example.jdbc.pool;

import java.sql.Connection;

/**
 * @author root
 * @description 数据库连接池接口
 * @date 2024/3/17 16:09
 */
public interface IConnectionPool {

    /**
     * 获取连接
     */
    Connection getConnection();


    /**
     * 将连接放回连接池
     */
    void releaseConnection(Connection connection);
}
