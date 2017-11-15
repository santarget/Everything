package com.ssy.everything.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by ssy on 2017/5/19.
 */
@Entity
public class UserInfo implements Serializable {
    @Transient
    private static final long serialVersionUID = -3662201601613187010L;

    @Id
    public String userName;
    public String nickName;
    public String phone;
    public boolean gender;//true M,false F
    public int age;
    public String email;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getGender() {
        return this.gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Generated(hash = 190429860)
    public UserInfo(String userName, String nickName, String phone, boolean gender,
                    int age, String email) {
        this.userName = userName;
        this.nickName = nickName;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

}
