package com.ssy.everything.bean;

import java.io.Serializable;

/**
 * Created by ssy on 2017/5/19.
 */

public class UserInfo implements Serializable {
    private static final long serialVersionUID = -3662201601613187010L;
    String userName;
    String nickName;
    String phone;
    boolean gender;//true M,false F
    int age;
    String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
