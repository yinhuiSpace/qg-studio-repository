package com.example.test;

import com.example.jdbc.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author root
 * @packageName: com.example.test
 * @className: JDBCUtilsTest
 * @description:
 * @date 2024/3/15 21:26
 */
public class JDBCUtilsTest {

    /**
     * 测试jdbcUtils 获取数据库连接方法
     */
    @Test
    public void testGetConnection() {

        for (int i = 0; i < 10; i++) {
            //由于将数据库连接储存到threadLocal中,所以在同一个线程内的数据库连接对象是同一个
            System.out.println(JDBCUtils.getConnection());
        }
    }

    /**
     * 测试jdbcUtils 事务控制
     */
    @Test
    public void testTransaction() {

        Connection connection = null;

        try {
            //开启事务
            JDBCUtils.begin();
            //执行sql语句
            connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update user set username=? where user_id=?;");

            //占位符映射参数
            preparedStatement.setObject(1, "王六");
            preparedStatement.setObject(2, 3);

            System.out.println(preparedStatement.executeUpdate());

            //模拟发生异常
            int result = 3 / 0;

            JDBCUtils.commit();

        } catch (Exception e) {
            e.printStackTrace();

            //发生异常时，将所有操作回滚
            JDBCUtils.rollback();
        } finally {
            JDBCUtils.closeAll(connection, null, null);
        }
    }


}
