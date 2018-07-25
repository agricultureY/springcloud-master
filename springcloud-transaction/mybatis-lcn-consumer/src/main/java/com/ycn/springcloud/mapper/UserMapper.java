package com.ycn.springcloud.mapper;

import com.ycn.springcloud.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ycn
 * @package com.ycn.springcloud.mapper
 * @ClassName UserMapper
 * @Date 2018/7/4 10:49
 */
@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> getAllUser();

    @Insert("insert into user (name, sex, age) values (#{name}, #{sex}, #{age})")
    int addUser(@Param("name") String name, @Param("sex") String sex, @Param("age") Integer age);
}
