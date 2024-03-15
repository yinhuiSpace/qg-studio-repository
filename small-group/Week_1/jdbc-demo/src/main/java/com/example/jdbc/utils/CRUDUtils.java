package com.example.jdbc.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author root
 * @packageName: com.example.jdbc.utils
 * @className: CRUDUtils
 * @description: 封装基础的增删改查
 * @date 2024/3/15 22:44
 */
public class CRUDUtils<T> {

    //实体类字节码对象
    private Class<?> entityClazz;

    public CRUDUtils(Class<?> entityClazz) {
        this.entityClazz = entityClazz;
    }

    /**
     * 插入一条数据
     */
    public int insert(String sql, Object... params) {

        //数据库连接
        Connection connection = null;

        //预编译sql语句
        PreparedStatement preparedStatement = null;

        try {
            //从数据库连接池中获得数据库连接
            connection = JDBCUtils.getConnection();
            //获得sql预编译对象
            preparedStatement = connection.prepareStatement(sql);

            //循环补充参数
            for (int i = 0; i < params.length; i++) {
                //将传入的参数依次补充到预编译sql语句中
                preparedStatement.setObject(i + 1, params[i]);
            }

            //依据tcp协议发送请求，执行sql语句，并接收返回结果
            int result = preparedStatement.executeUpdate();
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //必须要归还数据库连接回连接池
            JDBCUtils.closeAll(connection, preparedStatement, null);
        }

        return 0;
    }
}
