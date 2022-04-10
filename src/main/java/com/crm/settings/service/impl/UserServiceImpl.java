package com.crm.settings.service.impl;

import com.crm.exception.LoginException;
import com.crm.settings.dao.UserDao;
import com.crm.settings.domain.User;
import com.crm.settings.service.UserService;
import com.crm.utils.DateTimeUtil;
import com.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = null;
        try{
            user = userDao.login(map);
        }catch (Exception e){

        }

        if(user==null){

            throw new LoginException("账号或密码错误");

        }

        //失效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime)<0){
            throw new LoginException("账号已失效");
        }

        String lockState = user.getLockState();
        if("0".equals(lockState)) {
            throw new LoginException("帐号已锁定");
        }

        String allowIps = user.getAllowIps();
        if(!allowIps.contains(ip)){
            throw new LoginException("ip地址受限");
        }



        return user;
    }

    @Override
    public List<User> getUserList() {

        List<User> uList = userDao.getUserList();

        return uList;
    }


}

