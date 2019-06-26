package com.umpaytest.springProxy.service;

/**
 * @author: Hucl
 * @date: 2019/6/24 19:06
 * @description:
 */
public interface UserManager {

    void addUser(String username, String password);

    void delUser(String username);
}
