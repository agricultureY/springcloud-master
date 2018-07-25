package com.ycn.springcloud.service.impl;

import com.codingapi.tx.annotation.TxTransaction;
import com.ycn.springcloud.dao.UserDao;
import com.ycn.springcloud.entity.User;
import com.ycn.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ycn
 * @package com.ycn.springcloud.service.impl
 * @ClassName UserServiceImpl
 * @Date 2018/7/2 17:14
 */
@Service
@Transactional//开启本地事务
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @TxTransaction//LCN事务
    public int addUser(User user) {
//        System.out.println(1/0);
        return userDao.addUser(user);
    }

    @Override
    public int addUserNotTransaction(User user) {
        return userDao.addUser(user);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

}
