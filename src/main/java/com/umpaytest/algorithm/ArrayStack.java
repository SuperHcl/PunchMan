package com.umpaytest.algorithm;

/**
 * @author: Hucl
 * @date: 2019/3/25 17:55
 * @description: 基于数组实现的顺序栈
 */
public class ArrayStack {
    private int size;       //栈的大小
    private int count;      //栈中元素的个数
    private String[] data;     //数组

    public ArrayStack(int n) {
        if (n<=0) n = 10;
        data = new String[n];
        size = n;
        count = 0;
    }


    /**
     * 入栈
     */
    public boolean push(String value) {
        if (count==size) return false;
        data[count] = value;
        ++count;
        return true;
    }

    /**
     * 出栈
     */
    public String pop() {
        if (count == 0) return "-1";
        String tmp = data[count-1];
        --count;
        data[count] = null;
        return tmp;
    }

    public void printAll() {
        for (String datum : data) {
            System.out.print(datum + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(5);
        arrayStack.push("1");
        arrayStack.push("2");
        arrayStack.push("3");
        arrayStack.push("4");

        arrayStack.printAll();

        System.out.println(arrayStack.pop());
        arrayStack.printAll();
    }

}
