package com.example.backend.dao;

import com.example.backend.pojo.po.User;

import java.util.List;

/**
 * @author root
 * @packageName: com.example.backend.dao
 * @className: UserDAO
 * @description:
 * @date 2024/4/3 8:47
 */
public interface UserDAO {

    List<User> selectAll();

}
