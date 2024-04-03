package com.example.backend.pojo.po;

import java.util.Objects;

/**
 * @author root
 * @packageName: com.example.backend.pojo.po
 * @className: User
 * @description:
 * @date 2024/4/3 8:45
 */
public class User {

    /**
     * 用户表索引
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱号码
     */
    private String email;

    /**
     * 1是男，2是女
     */
    private Integer gender;

    /**
     * 用户状态：1代表可用，0代表禁用
     */
    private Integer userStatus;


    public User() {
    }

    public User(Integer userId, String username,
                String phone, String email,
                Integer gender, Integer userStatus) {
        this.userId = userId;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.userStatus = userStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", userStatus=" + userStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(username, user.username) && Objects.equals(phone, user.phone) && Objects.equals(email, user.email) && Objects.equals(gender, user.gender) && Objects.equals(userStatus, user.userStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, phone, email, gender, userStatus);
    }
}
