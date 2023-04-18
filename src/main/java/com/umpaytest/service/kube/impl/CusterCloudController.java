package com.umpaytest.service.kube.impl;

import com.umpaytest.entity.User;
import com.umpaytest.service.kube.AbstractKubeService;
import com.umpaytest.service.kube.ICloudController;
import org.springframework.stereotype.Service;

/**
 * @author Hu.ChangLiang
 * @date 2022/9/29 16:26
 */
@Service("custerCloudController")
public class CusterCloudController extends AbstractKubeService<User> implements
        ICloudController<User, Object, Object> {

    @Override
    public String getKubeServiceName(User param) {
        return param.getName();
    }

    @Override
    public Object listEntity(User user) {
        user.setMoney(1000000);
        return user;
    }
}
