package com.umpaytest.test;

import com.umpaytest.entity.Test;

/**
 * @author: Hucl
 * @date: 2019/4/28 17:43
 * @description: 针对对象饮用的问题
 */
public class ObjectReferenceTest {
    private Test test = new Test("attribute","method");

    public void change() {
        Test t = test;

        t.setAttribute("a");
        t.setMethod("m");
    }

    public static void main(String[] args) {
        ObjectReferenceTest referenceTest = new ObjectReferenceTest();
        System.out.println(referenceTest.test.toString());

        referenceTest.change();

        System.out.println("after change() --"+referenceTest.test.toString());
    }
}
