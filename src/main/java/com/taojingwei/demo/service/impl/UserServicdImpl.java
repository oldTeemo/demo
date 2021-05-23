package com.taojingwei.demo.service.impl;

import com.taojingwei.demo.config.DataSource;
import com.taojingwei.demo.dao.demo1.UserDao;
import com.taojingwei.demo.dao.demo2.TeacherDao;
import com.taojingwei.demo.pojo.User;
import com.taojingwei.demo.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServicdImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    @Qualifier("datasourceTransactionManager1")
    DataSourceTransactionManager dataSourceTransactionManager1;

    @Autowired
    @Qualifier("datasourceTransactionManager2")
    DataSourceTransactionManager dataSourceTransactionManager2;
    @Autowired
    TransactionDefinition transactionDefinition;

    @Override
    @DataSource({"datasourceTransactionManager1","datasourceTransactionManager2"})
    public void addUser(User user) {
        teacherDao.addUser(user);
        int x = 1/0;
        userDao.addUser(user);
    }

}
