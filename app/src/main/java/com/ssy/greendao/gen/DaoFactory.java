package com.ssy.greendao.gen;

import com.ssy.everything.EverythingApplication;

/**
 * @author ssy
 * @date 2017/11/15
 */
public class DaoFactory {
    private static UserInfoDao userInfoDao;
    private static NewsInfoDao newsInfoDao;
    private static GankBeanDao gankBeanDao;

    public static UserInfoDao getUserInfoDao() {
        if (userInfoDao == null) {
            synchronized (DaoFactory.class) {
                if (userInfoDao == null) {
                    userInfoDao = EverythingApplication.getInstance().getDaoSession().getUserInfoDao();
                }
            }
        }
        return userInfoDao;
    }

    public static NewsInfoDao getNewsInfoDao() {
        if (newsInfoDao == null) {
            synchronized (DaoFactory.class) {
                if (newsInfoDao == null) {
                    newsInfoDao = EverythingApplication.getInstance().getDaoSession().getNewsInfoDao();
                }
            }
        }
        return newsInfoDao;
    }

    public static GankBeanDao getGankBeanDao() {
        if (gankBeanDao == null) {
            synchronized (DaoFactory.class) {
                if (gankBeanDao == null) {
                    gankBeanDao = EverythingApplication.getInstance().getDaoSession().getGankBeanDao();
                }
            }
        }
        return gankBeanDao;
    }
}

