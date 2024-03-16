package com.example.jdbc.utils;

import com.example.jdbc.utils.resultlMapper.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author root
 * @packageName: com.example.jdbc.utils
 * @className: CRUDUtils
 * @description: 封装基础的增删改查
 * @date 2024/3/15 22:44
 */
public class CRUDUtils<T> {

    //实体类字节码对象
    private Class<T> entityClazz;

    public CRUDUtils(Class<T> entityClazz) {
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

    /**
    * 更新一条数据
    * */
    public int update(String sql, Object... params) {

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

    /**
    * 根据索引删除数据
    * */
    public int deleteById(String sql, int id) {

        //数据库连接
        Connection connection = null;

        //预编译sql语句
        PreparedStatement preparedStatement = null;

        try {
            //从数据库连接池中获得数据库连接
            connection = JDBCUtils.getConnection();
            //获得sql预编译对象
            preparedStatement = connection.prepareStatement(sql);
            //将传入的参数依次补充到预编译sql语句中
            preparedStatement.setInt(1, id);

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


    /**
    * 根据索引查找数据
    * */
    public T selectById(String sql, int id) {
        //数据库连接
        Connection connection = null;

        //预编译sql语句
        PreparedStatement preparedStatement = null;


        try {
            //从数据库连接池中获得数据库连接
            connection = JDBCUtils.getConnection();
            //获得sql预编译对象
            preparedStatement = connection.prepareStatement(sql);
            //补充参数
            preparedStatement.setObject(1, id);

            //依据tcp协议发送请求，执行sql语句，并接收返回结果
            ResultSet resultSet = preparedStatement.executeQuery();

            //创建用于resultSet解析的对象
            RowMapper<T> rowMapper = new RowMapper<>(entityClazz,resultSet);
            try {
                //解析resultSet，并且封装成列表
                T entity = rowMapper.rowMap();
                return entity;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //必须要归还数据库连接回连接池
            JDBCUtils.closeAll(connection, preparedStatement, null);
        }

        return null;
    }

    /**
    * 根据条件批量查询数据
    * */
    public List<T> selectList(String sql, List<Object> params) {

        //数据库连接
        Connection connection = null;

        //预编译sql语句
        PreparedStatement preparedStatement = null;

        try {
            //从数据库连接池中获得数据库连接
            connection = JDBCUtils.getConnection();
            //获得sql预编译对象
            preparedStatement = connection.prepareStatement(sql);

            //补充参数
            if (params!=null&&params.size()>0){
                //循环拼接
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i+1,params.get(i));
                }
            }

            //依据tcp协议发送请求，执行sql语句，并接收返回结果
            ResultSet resultSet = preparedStatement.executeQuery();

            //创建用于resultSet解析的对象
            RowMapper<T> rowMapper = new RowMapper<>(entityClazz,resultSet);
            //解析resultSet，并且封装成列表
            List<T> list = rowMapper.rowListMap();

            //返回结果
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //必须要归还数据库连接回连接池
            JDBCUtils.closeAll(connection, preparedStatement, null);
        }

        return null;
    }
}
