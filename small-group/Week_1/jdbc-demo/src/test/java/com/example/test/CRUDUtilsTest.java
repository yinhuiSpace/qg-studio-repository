package com.example.test;

import com.example.jdbc.utils.CRUDUtils;
import org.junit.jupiter.api.Test;

/**
 * @author root
 * @packageName: com.example.test
 * @className: CRUDUtils
 * @description: 测试增删改查工具类
 * @date 2024/3/15 22:48
 */
public class CRUDUtilsTest {

    /**
    * 测试增加数据
    * */
    @Test
    public void testInsert(){
        //创建对象
        CRUDUtils<User> crudUtils = new CRUDUtils<>(User.class);

        String sql="insert into user( username, password, phone, email, gender, user_status) VALUES (?,?,?,?,?,?);";

        //指定预编译sql语句以及参数
        Object[] params = {"张三11111", "1233211", "18718788888", "zs11111@qq.com", 1, 1};

        System.out.println(crudUtils.insert(sql, params));
    }

    /**
    * 测试更新数据
    * */
    @Test
    public void testUpdate(){
        //创建对象
        CRUDUtils<User> crudUtils = new CRUDUtils<>(User.class);

        String sql="update user set username=? where user_id=?;";

        //指定预编译sql语句以及参数
        Object[] params = {"张三1111111",3};

        System.out.println(crudUtils.update(sql, params));
    }

    /**
    * 测试根据索引删除数据
    * */
    @Test
    public void testDeleteById(){
        //创建对象
        CRUDUtils<User> crudUtils = new CRUDUtils<>(User.class);

        String sql="delete from user where user_id=?;";

        System.out.println(crudUtils.deleteById(sql, 5));
    }

    /**
    * 测试根据主键查找
    * */
    @Test
    public void testSelectById(){
        //创建对象
        CRUDUtils<User> crudUtils = new CRUDUtils<>(User.class);

        String sql="select * from user where user_id=?;";

        System.out.println(crudUtils.selectById(sql, 3));
    }
}
