package com.example.backend.service.impl;

import com.example.backend.dao.UserDAO;
import com.example.backend.dao.impl.UserDAOImpl;
import com.example.backend.pojo.po.User;
import com.example.backend.service.UserService;

import java.util.List;

/**
 * @author root
 * @packageName: com.example.backend.service.impl
 * @className: UserServiceImpl
 * @description:
 * @date 2024/4/3 8:49
 */
public class UserServiceImpl implements UserService {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public List<User> select() {

        //通过dao层查找数据
        return userDAO.selectAll();
    }
}
