package com.umpaytest.service.test;

/**
 * @author Hu.ChangLiang
 * @date 2022/9/29 16:33
 */

import com.umpaytest.entity.Test;
import com.umpaytest.service.kube.ICloudController;
import com.umpaytest.service.kube.IKubeService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExtendTest {
    /*@Resource
    private ICloudController<Test, Object, Object> testCloudController;*/

    @Autowired
    @Qualifier(value = "testCloudController")
    private IKubeService<Test> testCloudController1;

    @org.junit.Test
    public void test_01() {
        Test test = new Test();
        test.setAttribute("011");
        test.setMethod("test_01");
        /*Object o = testCloudController.listEntity(test);
        System.out.println(o.toString());*/
    }

    @org.junit.Test
    public void test_02() {
        Test test = new Test();
        test.setAttribute("011");
        test.setMethod("test_01");
        String kubeServiceName = testCloudController1.getKubeServiceName(test);
        System.out.println(kubeServiceName);
    }


}
