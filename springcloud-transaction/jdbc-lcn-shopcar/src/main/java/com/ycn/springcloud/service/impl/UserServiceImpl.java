package com.ycn.springcloud.service.impl;

import com.codingapi.tx.annotation.TxTransaction;
import com.ycn.springcloud.client.IntegralClient;
import com.ycn.springcloud.client.SettlementClient;
import com.ycn.springcloud.dao.UserDao;
import com.ycn.springcloud.entity.ResponseWrapper;
import com.ycn.springcloud.entity.User;
import com.ycn.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private SettlementClient settlementClient;
    @Autowired
    private IntegralClient integralClient;

    @Override
    @TxTransaction(isStart = true)//LCN事务
    public int addUser(User user) {
        int i = userDao.addUser(user);
        int j = (int) settlementClient.addUser(user).getBody().getRes();
        int l = (int) integralClient.addUser(user).getBody().getRes();
//        int m = 1 / 0;
        return i + j + l;
    }

    @Override
    public int addUserNotTransaction(User user) {
        int i = userDao.addUser(user);
        int j = (int) settlementClient.addUser(user).getBody().getRes();
        int l = (int) integralClient.addUser(user).getBody().getRes();
        int m = 1 / 0;
        return i + j + l;
    }

    @Override
    public List<User> getAllUser() {
        ResponseEntity<ResponseWrapper> response = settlementClient.getAllUser();
        return (List<User>) response.getBody().getRes();
    }

    @Override
    @TxTransaction
    public int delAllUser() {
        return userDao.delAllUser();
    }

}
