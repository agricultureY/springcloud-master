package com.ycn.springcloud.dao;

import com.ycn.springcloud.entity.User;

import java.util.List;

/**
 * @author ycn
 * @package com.ycn.springcloud.dao
 * @ClassName UserDao
 * @Date 2018/7/2 16:13
 */
public interface UserDao {
    int addUser(User user);

    List<User> getAllUser();

    int delAllUser();
}
