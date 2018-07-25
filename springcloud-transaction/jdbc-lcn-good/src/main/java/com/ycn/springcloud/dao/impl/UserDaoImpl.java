package com.ycn.springcloud.dao.impl;

import com.ycn.springcloud.dao.UserDao;
import com.ycn.springcloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ycn
 * @package com.ycn.springcloud.dao.impl
 * @ClassName UserDaoImpl
 * @Date 2018/7/2 16:15
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int addUser(User user) {
        String sql = "insert into user (name, sex, age) values ('"+user.getName()+"', '"+user.getSex()+"', '"+user.getAge()+"')";
        return jdbcTemplate.update(sql);
    }

    @Override
    public List<User> getAllUser() {
        String sql = "select * from user";
        return jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSex(resultSet.getString("sex"));
                user.setAge(resultSet.getInt("age"));
                return user;
            }
        });
    }

    @Override
    public int delAllUser() {
        String sql = "delete from user";
        return jdbcTemplate.update(sql);
    }
}
