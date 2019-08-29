package com.umpaytest.algorithm.lru;


/**
 * @author: Hucl
 * @date: 2019/8/28 16:01
 * @description: 最近最少使用算法(链表实现)
 */
public class LeastRecentlyUsedLinkedList<T> {
    // 头结点
    private Node<T> top = null;
    // 容器大小
    private static final int size = 4;
    // 当前容器中的元素个数
    private int count = 0;

    public boolean insert(T data) {
        // 若缓存中有data
        Node<T> node = query(data);
        if (node != null) {
            // 操作链表，把之放到首位
            // 查询当前值的上一个节点
            Node<T> prevNode = queryPreviousNode(data);
            if (prevNode == null) {
                return false;
            } else {
                // 删除链表中的当前元素
                delete(prevNode);
                // 重新插入到链表中
                insert(node);
            }
        } else {
            // 进行插入操作，判断缓存是否已满，若满 删除最后一位，不满直接插入
            Node<T> newNode = new Node<>(null, data);
            insert(newNode);
        }
        return true;
    }

    public void delete(Node<T> prevNode) {
        prevNode.setNext(prevNode.getNext().getNext());
        --count;
    }

    public void deleteLast() {
        Node<T> p = top;
        // 空链表直接返回
        if (p.getNext() == null) {
            return;
        }
        // 倒数第二个结点
        while (p.getNext().getNext() != null) {
            p = p.getNext();
        }
        p.setNext(null);
        count--;
    }

    private void insert(Node<T> p) {
        // 如果缓存已满，删除最后一位
        if (size == count) {
            deleteLast();
        }

        Node<T> newNode = new Node<>(null, p.data);
        if (top == null) {
            top = newNode;
        } else {
            newNode.setNext(top);
            top = newNode;
        }
        ++count;
    }

    private Node<T> query(T data) {
        Node<T> p = top;
        if (p == null) return null;

        while (p != null) {
            if (p.getData().equals(data)) {
                return p;
            }
            p = p.getNext();
        }
        return null;
    }


    // 查询当前节点的上个节点
    private Node<T> queryPreviousNode(T data) {
        Node<T> p = top;
        if (p == null) return null;
        if (p.getNext() == null) return p;
        while (p.getNext() != null) {
            // 查询头节点
            // 如果当前节点的下一个节点的data值 == data, 返回当前节点
            if (p.getNext().getData().equals(data)) {
                return p;
            }
            p = p.getNext();
        }
        return null;
    }

    public void printAll() {
        Node<T> node = top;
        while (node != null) {
            System.out.print(node.getData() + " ");
            node = node.getNext();
        }
        System.out.println();
    }


    private static class Node<T> {
        private Node<T> next; // 指向的下个对象
        private T data; // 数据

        private Node(Node<T> next, T data) {
            this.next = next;
            this.data = data;
        }

        public Node<T> getNext() {
            return next;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}
