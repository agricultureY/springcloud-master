package com.ycn.springcloud.service.impl;

import com.codingapi.tx.annotation.ITxTransaction;
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
public class UserServiceImpl implements UserService, ITxTransaction {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user.getName(), user.getSex(), user.getAge());
    }
}
