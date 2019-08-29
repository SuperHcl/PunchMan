package com.umpaytest.algorithm.lru;

/**
 * @author: Hucl
 * @date: 2019/8/29 09:54
 * @description:
 */
public class LRUClient {

    public static void main(String[] args) {
        LeastRecentlyUsedLinkedList<String> lru = new LeastRecentlyUsedLinkedList<>();

        lru.insert("1");
        lru.insert("2");
        lru.insert("1");

        lru.insert("4");

        lru.insert("5");

        lru.printAll();

    }
}
