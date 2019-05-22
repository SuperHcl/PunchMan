package com.umpaytest.algorithm;


/**
 * @author: Hucl
 * @date: 2019/3/26 16:22
 * @description: 链表实现的栈
 */
public class LinkedListStack {
    private Node top = null;

    //入栈
    public void push(int value) {
        Node newNode = new Node(null,value);
        if (top == null) {
            top = newNode;
        } else {
            newNode.next = top;
            top = newNode;
        }
    }

    //出栈
    public int pop() {
        if (top == null) return -1;
        int value = top.data;
        top = top.next;
        return value;
    }

    private void printAll() {
        Node p = top;
        while (p!=null) {
            System.out.print(p.data+" ");
            p = p.next;
        }
        System.out.println();
    }


    private static class Node {
        private Node next;
        private int data;

        public Node(Node next, int data) {
            this.next = next;
            this.data = data;
        }
        public int getData() {
            return data;
        }
    }


    public static void main(String[] args) {
        LinkedListStack linkedListStack = new LinkedListStack();
        linkedListStack.push(1);
        linkedListStack.push(2);
        linkedListStack.push(3);
        linkedListStack.push(4);

        linkedListStack.printAll();

        linkedListStack.pop();
        linkedListStack.pop();
        linkedListStack.printAll();


    }
}
