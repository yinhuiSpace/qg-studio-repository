package com.example.backend.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.backend.pojo.po.User;
import com.example.backend.result.Result;
import com.example.backend.service.UserService;
import com.example.backend.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author root
 * @packageName: com.example.backend.controller
 * @className: UserServlet
 * @description:
 * @date 2024/4/3 8:58
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //执行业务操作
        List<User> userList = userService.select();


        //返回结果
        resp.setContentType("application/json;charset=utf-8");
        //通过设置响应头解决跨域问题
        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setHeader("Access-Control-Allow-Methods","GET,POST");

        PrintWriter out = resp.getWriter();

        out.println(JSONObject.toJSONString(Result.success(userList)));
    }
}
