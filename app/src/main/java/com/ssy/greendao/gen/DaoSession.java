package com.ssy.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.ssy.everything.bean.GankBean;
import com.ssy.everything.bean.NewsInfo;
import com.ssy.everything.bean.UserInfo;

import com.ssy.greendao.gen.GankBeanDao;
import com.ssy.greendao.gen.NewsInfoDao;
import com.ssy.greendao.gen.UserInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig gankBeanDaoConfig;
    private final DaoConfig newsInfoDaoConfig;
    private final DaoConfig userInfoDaoConfig;

    private final GankBeanDao gankBeanDao;
    private final NewsInfoDao newsInfoDao;
    private final UserInfoDao userInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        gankBeanDaoConfig = daoConfigMap.get(GankBeanDao.class).clone();
        gankBeanDaoConfig.initIdentityScope(type);

        newsInfoDaoConfig = daoConfigMap.get(NewsInfoDao.class).clone();
        newsInfoDaoConfig.initIdentityScope(type);

        userInfoDaoConfig = daoConfigMap.get(UserInfoDao.class).clone();
        userInfoDaoConfig.initIdentityScope(type);

        gankBeanDao = new GankBeanDao(gankBeanDaoConfig, this);
        newsInfoDao = new NewsInfoDao(newsInfoDaoConfig, this);
        userInfoDao = new UserInfoDao(userInfoDaoConfig, this);

        registerDao(GankBean.class, gankBeanDao);
        registerDao(NewsInfo.class, newsInfoDao);
        registerDao(UserInfo.class, userInfoDao);
    }
    
    public void clear() {
        gankBeanDaoConfig.getIdentityScope().clear();
        newsInfoDaoConfig.getIdentityScope().clear();
        userInfoDaoConfig.getIdentityScope().clear();
    }

    public GankBeanDao getGankBeanDao() {
        return gankBeanDao;
    }

    public NewsInfoDao getNewsInfoDao() {
        return newsInfoDao;
    }

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

}
