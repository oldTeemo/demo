package com.taojingwei.demo.service.impl;

import com.taojingwei.demo.dao.UserDao;
import com.taojingwei.demo.pojo.User;
import com.taojingwei.demo.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServicdImpl implements IUserService{

    @Autowired
    private UserDao userDao;

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }
    
}
