package com.umpaytest.algorithm;

/**
 * @author: Hucl
 * @date: 2019/3/25 17:55
 * @description: 基于数组实现的顺序栈
 */
public class ArrayStack {
    private int size;       //栈的大小
    private int count;      //栈中元素的个数
    private int[] data;     //数组

    public ArrayStack(int n) {
        if (n<=0) n = 10;
        data = new int[n];
        size = n;
        count = 0;
    }


    /**
     * 入栈
     */
    public boolean push(int value) {
        if (count==size) return false;
        data[count] = value;
        ++count;
        return true;
    }

    /**
     * 出栈
     */
    public int pop() {
        if (count == 0) return -1;
        int tmp = data[count-1];
        --count;
        return tmp;
    }


    public static void main(String[] args) {

    }

}
