package com.ycn.springcloud.service;

import com.ycn.springcloud.entity.User;

import java.util.List;

/**
 * @author ycn
 * @package com.ycn.springcloud.service
 * @ClassName UserService
 * @Date 2018/7/4 10:58
 */
public interface UserService {

    List<User> getAllUser();

    int addUser(User user);

}
