package com.ycn.springcloud.service;

import com.ycn.springcloud.entity.User;

import java.util.List;

/**
 * @author ycn
 * @package com.ycn.springcloud.service
 * @ClassName UserService
 * @Date 2018/7/2 17:12
 */
public interface UserService {

    int addUser(User user);

    int addUserNotTransaction(User user);

    List<User> getAllUser();
}
