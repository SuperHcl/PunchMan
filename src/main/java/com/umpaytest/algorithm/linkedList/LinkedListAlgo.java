package com.umpaytest.algorithm.linkedList;

/**
 * @author: Hucl
 * @date: 2019/11/19 09:42
 * @description: 1) 单链表反转
 * 2) 链表中环的检测
 * 3) 两个有序的链表合并
 * 4) 删除链表倒数第n个节点
 * 5) 求链表的中间结点
 */
public class LinkedListAlgo {

    /**
     * 单链表反转
     *
     * @param list 原始链表
     * @return 反转后的链表
     */
    public static SinglyLinkedList.Node reverse(SinglyLinkedList.Node list) {
        SinglyLinkedList.Node curr = list, pre = null;
        while (curr != null) {
            SinglyLinkedList.Node node = curr.next;
            curr.next = pre;
            pre = curr;
            curr = node;
        }
        return pre;
    }

    /**
     * 删除链表倒数第n个节点
     *
     * @param list 链表
     * @param k    倒数第k个
     * @return 删除后的链表
     */
    public static SinglyLinkedList.Node deleteLastKth(SinglyLinkedList.Node list, int k) {
        SinglyLinkedList.Node fast = list;
        int i=1;
        while (fast!=null && i<k) {
            fast = fast.next;
            ++i;
        }
        if (fast == null) return list;

        SinglyLinkedList.Node slow = list;
        SinglyLinkedList.Node prev = null;
        while (fast.next != null) {
            fast = fast.next;
            prev = slow;
            slow = slow.next;
        }

        if (prev == null) {
            list = list.next;
        } else {
            // 在此会修改list中的值
            prev.next = prev.next.next;
        }
        return list;
    }

    public static SinglyLinkedList.Node deleteLastNNode(SinglyLinkedList.Node list, int n) {
        SinglyLinkedList.Node node = list;
        if (node == null) return null;
        int count=0;

        while (node != null) {
            ++count;
            node = node.next;
        }

        int normal = count - n + 1;
        if (normal<=0) return null;
        if (normal==1) return list;

        SinglyLinkedList.Node cur = list;
        int nu = 0;
        while (cur.next!=null) {
            ++nu;
            if (nu == normal-1) {
                cur.next = cur.next.next;
                break;
            } else {
                cur = cur.next;
            }
        }
        return list;

    }

    /**
     * 查找中间结点
     * 思路：快慢指针
     * 快指针每次走两格，
     * 慢指针每次走一格，
     * 当快指针 == null || .next == null 结束循环
     *
     * @param list list
     * @return middle node
     */
    public static SinglyLinkedList.Node findMiddleNode(SinglyLinkedList.Node list) {
        if (list == null) return null;

        SinglyLinkedList.Node fast = list;
        SinglyLinkedList.Node slow = list;

        while (fast!=null && fast.next!=null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }


    public static void main(String[] args) {
        SinglyLinkedList nodeList = new SinglyLinkedList();
        nodeList.insertTail(1);
        nodeList.insertTail(2);
        nodeList.insertTail(3);
        nodeList.insertTail(4);
        nodeList.insertTail(5);

//        SinglyLinkedList.Node reverse = reverse(nodeList.head);
//        nodeList.printAll(reverse);
//        SinglyLinkedList.Node middleNode = findMiddleNode(nodeList.head);
//        nodeList.printAll(middleNode);
//        SinglyLinkedList.Node deletedList = deleteLastKth(nodeList.head, 2);
//        nodeList.printAll(deletedList);
        SinglyLinkedList.Node node = deleteLastNNode(nodeList.head, 1);
        nodeList.printAll(node);
    }


}
