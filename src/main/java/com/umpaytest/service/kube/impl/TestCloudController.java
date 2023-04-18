package com.umpaytest.service.kube.impl;

import com.umpaytest.entity.Test;
import com.umpaytest.service.kube.AbstractKubeService;
import com.umpaytest.service.kube.ICloudController;
import org.springframework.stereotype.Service;

/**
 * @author Hu.ChangLiang
 * @date 2022/9/29 16:29
 */
@Service("testCloudController")
public class TestCloudController extends AbstractKubeService<Test> implements ICloudController<Test, Object, Object> {

    @Override
    public Object listEntity(Test test) {
        test.setMethod("listEntity");
        return test;
    }

    @Override
    public String getKubeServiceName(Test param) {
        Object o = listEntity(param);
        return o.toString();
    }
}
