package com.ycn.springcloud.service.impl;

import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import com.ycn.springcloud.client.ProductClient;
import com.ycn.springcloud.entity.User;
import com.ycn.springcloud.mapper.UserMapper;
import com.ycn.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ycn
 * @package com.ycn.springcloud.service.impl
 * @ClassName UserServiceImpl
 * @Date 2018/7/4 10:59
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    @TxTransaction(isStart = true)
    public int addUser(User user) {
        int i = (int) productClient.addUser(user).getBody().getRes();
        int j = userMapper.addUser(user.getName(), user.getSex(), user.getAge());
        int t = 1 / 0;
        return i + j;
    }
}
