package com.example.jdbc.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author root
 * @packageName: com.example.jdbc.utils
 * @className: JDBCUtils
 * @description:
 * @date 2024/3/15 21:25
 */
public class JDBCUtils {
    //用于封装配置文件中的配置项
    private static final Properties PROPERTIES = new Properties();

    //本地线程对象，用于同一个线程内共享数据库连接，线程封闭
    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    /**
     * 读取配置文件中的配置信息
     * */
    static {

        try {
            //加载配置文件
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            InputStream propertiesInputstream = classLoader.getResourceAsStream("db.properties");

            PROPERTIES.load(propertiesInputstream);

            //将驱动类的字节码文件加载到内存中
            Class.forName(PROPERTIES.getProperty("driver"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过数据库驱动类创建数据库连接对象
     */
    public static Connection getConnection() {

        try {
            //使用ThreadLocal的目的是保证同一线程使用同一个数据库连接，便于事务控制

            //1.首先看本地线程内有没有
            Connection connection = threadLocal.get();

            //2.本地线程内没有，就通过驱动类获取一个连接，再将连接放到threadLocal中
            if (connection == null) {
                //通过驱动类新建数据库连接
                connection = DriverManager.getConnection(PROPERTIES.getProperty("url"), PROPERTIES.getProperty("username"), PROPERTIES.getProperty("password"));
                //将新连接存到当前线程
                threadLocal.set(connection);
            }

            return connection;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 开启事务
     */
    public static void begin() {

        try {
            //关闭自动提交
            Connection connection = getConnection();

            connection.setAutoCommit(false);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 所有sql成功，手动提交
     */
    public static void commit() {

        Connection connection = null;

        try {
            //手动提交
            connection = getConnection();

            connection.commit();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 有sql执行异常，全部sql操作回滚
     */
    public static void rollback() {

        Connection connection = null;

        try {
            //有sql执行异常，回滚所有sql操作
            connection = getConnection();
            connection.rollback();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 关闭连接，释放资源
     */
    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {

        try {
            //需要遵循先开后关的原则
            //注意：只有对象不为空时，才执行关闭
            //关闭结果集对象
            if (resultSet != null) {
                resultSet.close();
            }

            //关闭sql语句对象
            if (statement != null) {
                statement.close();
            }

            //数据库连接对象
            if (connection != null) {
                connection.close();

                //销毁封闭线程对象
                threadLocal.remove();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
