package com.umpaytest.controller;

import com.umpaytest.annotation.Superman;
import com.umpaytest.entity.User;
import com.umpaytest.service.UserService;
import com.umpaytest.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author: Hucl
 * @date: 2019/3/26 10:08
 * @description:
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userServiceImpl;
    @Resource
    private UserService UserServiceImpl1;

//    @Autowired
//    public UserController(UserServiceImpl userService) {
//        this.userService = userService;
//    }


    @ApiOperation("创建用户")
    @PostMapping("/users")
    public User create(@RequestBody @Valid User user) {
        return user;
    }

    @ApiOperation("用户详情")
    @GetMapping("/users/{id}")
    public User findById(@PathVariable Integer id) {
        User user = new User();
        user.setName("JJ Lin").setAge(21).setAddress("Beijing").setEmail("157@163.com").setId(id);
        return user;
    }


    @GetMapping("/listPage")
    public User listPage(@Valid User user) {
        System.out.println(user.toString());
        return user;
    }

    @DeleteMapping("/listPage/post")
    public User listPagePost(@RequestBody @Validated User user) {
        System.out.println(user.toString());
        return user;
    }

    @ApiIgnore
    @DeleteMapping("/users/{id}")
    @Superman
    public String deleteById(@PathVariable Long id) {
        return "delete user : " + id;
    }


    @GetMapping("/doSomething")
    public String doSomething(@RequestParam(value = "type", defaultValue = "1") Integer type) {
        String s = UserServiceImpl1.doSomething("ahahs" + type);
        System.out.println(s);

        return userServiceImpl.doSomething(" Jack" + type);
    }
}
