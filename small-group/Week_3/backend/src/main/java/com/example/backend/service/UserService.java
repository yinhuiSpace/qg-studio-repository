package com.example.backend.service;

import com.example.backend.pojo.po.User;

import java.util.List;

/**
 * @author root
 * @packageName: com.example.backend.service
 * @className: UserService
 * @description:
 * @date 2024/4/3 8:48
 */
public interface UserService {

    List<User> select();
}
