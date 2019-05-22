package com.umpaytest.service.impl;

import com.umpaytest.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author: Hucl
 * @date: 2019/4/28 18:07
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String doSomething(String name) {
        return "come on boy,just do some real things!"+name;
    }
}
