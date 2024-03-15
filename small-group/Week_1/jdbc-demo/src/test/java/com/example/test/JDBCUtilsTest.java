package com.example.test;

import com.example.jdbc.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

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
     * */
    @Test
    public void testGetConnection(){

        for (int i = 0; i < 10; i++) {
            //由于将数据库连接储存到threadLocal中,所以在同一个线程内的数据库连接对象是同一个
            System.out.println(JDBCUtils.getConnection());
        }
    }
}
