package com.umpaytest.service.test;

/**
 * @author Hu.ChangLiang
 * @date 2022/9/29 16:33
 */

import com.umpaytest.entity.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExtendTest {


    @org.junit.Test
    public void test_01() {
        Test test = new Test();
        test.setAttribute("011");
        test.setMethod("test_01");
        /*Object o = testCloudController.listEntity(test);
        System.out.println(o.toString());*/
    }


}
