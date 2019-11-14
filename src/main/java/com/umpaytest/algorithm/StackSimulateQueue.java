package com.umpaytest.algorithm;

import java.util.Stack;

/**
 * @author: Hucl
 * @date: 2019/11/14 10:15
 * @description: 需求：
 * 用两个栈模拟队列的入队和出队
 */
public class StackSimulateQueue {
    private static Stack<Integer> stack1 = new Stack<>();
    private static Stack<Integer> stack2 = new Stack<>();

    public static void main(String[] args) {

//        String s = "We Are Happy".replaceAll(" ", "%20");
//        System.out.println(s);
//        ArrayList<Integer> intList = new ArrayList<>();
//        intList.add(0, 1);
//        intList.add(0, 2);
//        intList.add(0, 3);
//        intList.add(0, 4);
//        intList.add(0, 5);
//        intList.add(0, 6);
//        System.out.println(intList);
        push(1);
        push(2);
        push(3);
        System.out.println(pop());
        System.out.println(pop());
        push(4);
        push(5);
        System.out.println(pop());
        System.out.println(pop());
        System.out.println(pop());
    }

    public static void push(int node) {
//        int size = stack2.size();
//        if (size != 0) {
//            for (int i = 0; i < size; i++) {
//                stack1.push(stack2.pop());
//            }
//        }
        stack1.push(node);
    }

    public static int pop() {
        if (stack2.size() == 0) {
            int size = stack1.size();
            for (int i = 0; i < size; i++) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }


}
