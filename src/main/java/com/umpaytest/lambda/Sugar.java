package com.umpaytest.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author: Hucl
 * @date: 2019/6/27 09:44
 * @description:
 */
public class Sugar {
    public static void main(String[] args) {
        Person p0 = new Person("p0");
        Person p1 = new Person("p1");
        Person p2 = new Person("p2");
        List<Person> list = Arrays.asList(p0,p1,p2);

        // sayHello()为静态方法，主要因为使用了传入的参数 Person p. p.name
        list.forEach(Person::sayHello);
        list.forEach(Person::total);

        // sayGoodbye() 不是静态方法，所有它是用类变量来做输出，this.name
        // total() sayGoodbyeto()都一样
        list.forEach(Person::sayGoodbye);
        list.forEach(p0::sayGoodbyeTo);
        /*
         * 总结：只要使用了方法中传递的参数（对象）中的属性，就必须为static方法，就如sayHello()方法
         * 没使用的就不用为static
         */

        Person p3 = Person.create("p3", Person::new);
        p3.sayGoodbye();
    }

}

class Person {
    private static int num = 0;

    private String name;

    public Person(){}   //必须给出一个无参构造函数才能使用‘构造引用’

    public Person(String name) {
        this.name = name;
        Person.num++;
    }
    // 针对list.forEach(Consumer) 做1~3，一直forEach的动作是每次想consumer传递一个list中的对象
    // 1.静态 带参 Person::sayHello
    static void sayHello(Person p) {
        System.out.println(p.name + " hello");
    }
    void total() {
        System.out.println("Sum: " + Person.num);
    }
    // 2.2 实例方法(非静态) 不带参 Person::sayGoodbye
    public void sayGoodbye(){
        System.out.println(this.name + " Say Goodbye!");
    }
    // 3 实例方法(非静态) 带参 p0::sayGoodbyeTo
    public void sayGoodbyeTo(Person p) {
        System.out.println(this.name + " Say Goodbye To " + p.name);
    }
    // 4.引用构造方法
    public static Person create(String name, Supplier<Person> supplier){
        Person p = supplier.get();
        p.name = name;
        return p;
    }

}
