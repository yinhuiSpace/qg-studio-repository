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
        CRUDUtils<User> crudUtils = new CRUDUtils<>(User.class);

        String sql="insert into user( username, password, phone, email, gender, user_status) VALUES (?,?,?,?,?,?);";

        Object[] params = {"张三11111", "1233211", "18718788888", "zs11111@qq.com", 1, 1};

        System.out.println(crudUtils.insert(sql, params));
    }
}
