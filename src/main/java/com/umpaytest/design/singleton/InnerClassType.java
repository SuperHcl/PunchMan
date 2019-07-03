package com.umpaytest.design.singleton;

/**
 * @author: Hucl
 * @date: 2019/5/22 16:50
 * @description: 线程安全的单例模式，使用内部类实现. 特点：既能实现延迟加载，又能实现线程安全
 */
public class InnerClassType {

    private InnerClassType(){}

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例没有绑定关系，而且只有被调用到时才会装载(装载过程是由jvm保证线程安全)
     * 从而实现了延迟加载
     */
    private static class SingletonPatternHolder {
        /**
         * 这个模式的优势在于：getInstance方法并没有被同步，并且只是执行一个域的访问，因此延迟初始化并没有增加任何访问成本
         */
        private static final InnerClassType instance = new InnerClassType();
    }

    /**
     * 这个模式的优势在于：getInstance方法并没有被同步，并且只是执行一个域的访问，因此延迟初始化并没有增加任何访问成本
     */
    public static InnerClassType getInstance() {
        return SingletonPatternHolder.instance;
    }

    public void say() {
        System.out.println("inner class type");
    }
}
