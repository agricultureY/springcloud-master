package com.ycn.springcloud.controller;

import com.ycn.springcloud.entity.ResponseWrapper;
import com.ycn.springcloud.entity.User;
import com.ycn.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ycn
 * @package com.ycn.springcloud.controller
 * @ClassName UserController
 * @Date 2018/7/4 11:15
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getAllUser")
    public ResponseEntity<ResponseWrapper> getAllUser() {
        List<User> userList = userService.getAllUser();
        return ResponseEntity.ok(ResponseWrapper.markSuccess(userList));
    }

    @RequestMapping("/addUser")
    public ResponseEntity<ResponseWrapper> addUser(User user) {
        Integer res = userService.addUser(user);
        return ResponseEntity.ok(ResponseWrapper.markSuccess(res));
    }

}
