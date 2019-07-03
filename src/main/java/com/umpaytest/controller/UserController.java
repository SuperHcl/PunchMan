package com.umpaytest.controller;

import com.umpaytest.annotation.Superman;
import com.umpaytest.entity.User;
import com.umpaytest.service.UserService;
import com.umpaytest.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/3/26 10:08
 * @description:
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService2;

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

    @ApiOperation("用户列表")
    @GetMapping("/users")
    @Superman
    public List<User> list(@ApiParam("查看第几页") @RequestParam int pageIndex,
                           @ApiParam("每页多少条") @RequestParam int size) {
        List<User> result = new ArrayList<>(2);
        result.add(new User(1,"aaa",52,"广州","aaa@qq.com"));
        result.add(new User(2,"bbb",23,"上海","bbb.@umfintech.com"));
        result.add(new User(3,"ccc",30,"深圳","ccc@qq.com"));
        result.add(new User(4,"ddd",43,"郑州","ddd@qq.com"));
        return result;
    }

    @ApiIgnore
    @DeleteMapping("/users/{id}")
    @Superman
    public String deleteById(@PathVariable Long id) {
        return "delete user : " + id;
    }


    @GetMapping("/doSomething")
    public String doSomething() {
        return userService2.doSomething(" Jack");
    }
}
