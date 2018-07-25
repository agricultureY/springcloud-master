package com.ycn.springcloud.entity;

import java.io.Serializable;

/**
 * @author ycn
 * @package com.ycn.springcloud.entuty
 * @ClassName User
 * @Date 2018/7/2 16:09
 */
public class User implements Serializable {

    private static final long serialVersionUID = -6397435951625623173L;

    private Integer id;

    private String name;

    private String sex;

    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User() {
    }
}
