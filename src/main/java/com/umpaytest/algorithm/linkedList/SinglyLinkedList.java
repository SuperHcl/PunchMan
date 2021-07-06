package com.umpaytest.algorithm.linkedList;


/**
 * @author: Hucl
 * @date: 2019/4/28 16:26
 * @description: 单链表
 */
public class SinglyLinkedList {
    public Node head = null;


    public void insertAfter(int value) {
        Node newNode = new Node(value, null);
        if (head == null) {
            head = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
    }

    /**
     * 顺序插入
     * @param value insert value
     */
    public void insertTail(int value) {
        Node newNode = new Node(value, null);
        if (head == null) {
            head = newNode;
        } else {
            Node q = head;
            while (q.next!=null) {
                q = q.next;
            }
            q.next = newNode;

        }
    }

    public void printAll() {
        Node p = head;
        while (p != null) {
            System.out.print(p.data + "->");
            p = p.next;
        }
        System.out.println();
    }

    public static class Node {
        private int data;

        public Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public int getData() {
            return data;
        }
    }

    public static void main(String[] args) {
        SinglyLinkedList linkedList = new SinglyLinkedList();
        for (int i = 1; i <= 5; i++) {
            linkedList.insertTail(i);
        }

        linkedList.printAll();
    }
}

