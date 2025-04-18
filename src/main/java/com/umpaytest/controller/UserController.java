package com.umpaytest.controller;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.umpaytest.annotation.Superman;
import com.umpaytest.entity.Student;
import com.umpaytest.entity.User;
import com.umpaytest.firebase.FirebaseContext;
import com.umpaytest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
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

    @PostMapping("/test")
    public Object test(@RequestBody @Valid Student student) {
        return student;
    }

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
    public String doSomething(@RequestParam @Validated Integer number) {
        String token = "dsz7ety7R3KUx3sTJvH9Yq:APA91bFXjdaM59kN4stMqqQqFOLIWbu-Kv3ZmxxZHPwlq8dG0OY3Ost1ZTDrAtmyY3AIFXvEu6PB_JfH1DCdXTVdXRDeOESX95aCt82aaRvhRBvq61BuPlGJsSDvOVY_PyeoQXAZ15PZ";
        String title = "Apple发布会";
        String body = "1块钱一个的iPhone16";

        try {
            // 创建消息
            Notification build = Notification.builder()
                    .setBody(body)
                    .setTitle(title)
                    .build();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("announcementId", "1838115366069501954");
            jsonObject.put("employeeId", "1824701376039485441");

            Message message = Message.builder()
                    .setToken(token) // 设置设备的 registration token
                    .setNotification(build) // 消息内容
                    .putData("type", "1")
                    .putData("action", "open_page")
                    .putData("target_page", "mxos://company/notice/index")
                    .putData("extra_params", jsonObject.toString())
                    .build();

            // 发送消息
            FirebaseMessaging instance = FirebaseContext.getInstance("haiwaiMyMixue");
            String response = instance.send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "doSomething";
    }
}
