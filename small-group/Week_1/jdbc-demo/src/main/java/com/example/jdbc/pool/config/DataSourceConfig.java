package com.example.jdbc.pool.config;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

/**
 * @author root
 * @packageName: com.example.jdbc.pool.config
 * @className: DataSourceConfig
 * @description:
 * @date 2024/3/17 16:10
 */
public class DataSourceConfig {

    //数据库连接配置
    private String driver;

    private String url;

    private String username;

    private String password;

    //数据库连接池配置
    private String initSize = "3";

    private String maxSize = "6";

    private String health = "true";

    private String delay = "1000";

    private String period = "1000";

    private String timeout = "5000";

    private String waittime = "1000";


    /**
     * 使用构造方法创建对象的同时，为字段赋值
     */
    public DataSourceConfig() {
        try {
            //通过io流加载配置文件，将配置项及其参数封装成一个对象
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));

            //通过反射赋值
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String fieldName = entry.getKey().toString();
                String fieldVal = entry.getValue().toString();

                //通过反射获取对应字段
                Field field = this.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(this, fieldVal);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInitSize() {
        return initSize;
    }

    public void setInitSize(String initSize) {
        this.initSize = initSize;
    }

    public String getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getWaittime() {
        return waittime;
    }

    public void setWaittime(String waittime) {
        this.waittime = waittime;
    }
}
