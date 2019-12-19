package com.umpaytest.algorithm.tree;

/**
 * @author: Hucl
 * @date: 2019/12/13 16:03
 * @description: 二叉查找树
 * 规则：在树中的任何一个节点，其左子树中的每个节点都要小于该节点，
 * 右子树中的每个节点都要大于该节点
 */
public class BinarySearchTree {
    private Node tree;

    /**
     * 查找
     * 如果当前节点的值等于查找的数，直接返回。
     * 小于的话，就遍历当前节点的左子树
     * 大于，查找当前节点的右子树
     *
     * @param searchData search data
     * @return 节点
     */
    public Node find(int searchData) {
        Node p = tree;

        while (p != null) {
            if (p.data < searchData) {
                p = p.leftTree;
            } else if (p.data > searchData) {
                p = p.rightTree;
            } else {
                return p;
            }
        }
        return null;
    }

    public void insert(int insertedData) {
        if (tree == null) {
            tree = new Node(insertedData);
            return;
        }
        Node p = tree;

        while (p!=null) {
            // left tree
            if (p.data > insertedData) {
                if (p.leftTree == null) {
                    p.leftTree = new Node(insertedData);
                    return;
                } else {
                    p = p.leftTree;
                }
            } else {
                // <= ,遍历右子树
                if (p.rightTree == null) {
                    p.rightTree = new Node(insertedData);
                    return;
                } else {
                    p = p.rightTree;
                }
            }
        }
    }

    static class Node {
        private int data;
        private Node leftTree;
        private Node rightTree;

        public Node(int data) {
            this.data = data;
        }
    }
}
