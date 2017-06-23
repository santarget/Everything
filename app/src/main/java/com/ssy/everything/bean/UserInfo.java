package com.ssy.everything.bean;

import java.io.Serializable;

/**
 * Created by ssy on 2017/5/19.
 */

public class UserInfo implements Serializable {
    private static final long serialVersionUID = -3662201601613187010L;
    public String userName;
    public String nickName;
    public String phone;
    public boolean gender;//true M,false F
    public int age;
    public String email;

}
