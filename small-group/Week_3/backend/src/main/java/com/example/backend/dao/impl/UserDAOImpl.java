package com.example.backend.dao.impl;

import com.example.backend.dao.UserDAO;
import com.example.backend.pojo.po.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author root
 * @packageName: com.example.backend.dao
 * @className: UserDAOImpl
 * @description:
 * @date 2024/4/3 8:48
 */
public class UserDAOImpl implements UserDAO {

    /**
    * 查询数据
    * */
    @Override
    public List<User> selectAll() {

        //为方便起见，这里使用假数据
        ArrayList<User> userArrayList = new ArrayList<>();

        userArrayList.add(new User(1,"张三","18718766666","123@qq.com",1,1));
        userArrayList.add(new User(2,"李四","18718766666","123@qq.com",2,1));
        userArrayList.add(new User(3,"王五","18718766666","123@qq.com",1,1));
        userArrayList.add(new User(4,"赵六","18718766666","123@qq.com",2,0));
        userArrayList.add(new User(5,"张三丰","18718766666","123@qq.com",1,1));
        userArrayList.add(new User(6,"韦小宝","18718766666","123@qq.com",2,1));

        return userArrayList;
    }
}
