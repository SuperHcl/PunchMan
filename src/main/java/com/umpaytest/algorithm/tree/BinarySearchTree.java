package com.umpaytest.algorithm.tree;


import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author: Hucl
 * @date: 2019/12/13 16:03
 * @description: 二叉查找树
 * 规则：在树中的任何一个节点，其左子树中的每个节点都要小于该节点，
 * 右子树中的每个节点都要大于该节点
 */
public class BinarySearchTree {
    private TreeNode tree;

    /**
     * 查找
     * 如果当前节点的值等于查找的数，直接返回。
     * 小于的话，就遍历当前节点的左子树
     * 大于，查找当前节点的右子树
     *
     * @param searchData search data
     * @return 节点
     */
    public TreeNode find(int searchData) {
        TreeNode p = tree;

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
            tree = new TreeNode(insertedData);
            return;
        }
        TreeNode p = tree;

        while (p != null) {
            // left tree
            if (p.data > insertedData) {
                if (p.leftTree == null) {
                    p.leftTree = new TreeNode(insertedData);
                    return;
                } else {
                    p = p.leftTree;
                }
            } else {
                // <= ,遍历右子树
                if (p.rightTree == null) {
                    p.rightTree = new TreeNode(insertedData);
                    return;
                } else {
                    p = p.rightTree;
                }
            }
        }
    }

    /**
     * 删除指定的数，有三种情况
     * 1. 删除的数据是叶子节点。 只需要将父节点指向该要删除的节点的指针置为null就行了
     * 2. 删除的数据是只有一个子节点（左子节点或右子节点），这种情况下只需要更新父节点中，指向要删除节点的指针
     * 让它指向要删除的子节点就行了
     * 3. 删除的数据有两个子节点。先找到这个节点的右子树中的最小叶子节点，把他替换到要删除的节点上。然后删除
     * 这个最小叶子节点，
     *
     * @param data
     */
    public void delete(int data) {
        TreeNode p = tree;
        // 要删除节点的父节点
        TreeNode pp = null;

        while (p != null && p.data != data) {
            pp = p;
            if (data > p.data)
                p = p.rightTree;
            else
                p = p.leftTree;
        }
        if (p == null) return;
        // 要删除的节点有两个子节点
        if (p.leftTree != null && p.rightTree != null) { // 查找右子树中最小节点
            TreeNode minP = p.rightTree;
            TreeNode minPP = p; // minPP表示minP的父节点
            while (minP.leftTree != null) {
                minPP = minP;
                minP = minP.leftTree;
            }
            p.data = minP.data; // 将minP的数据替换到p中
            p = minP; // 下面就变成了删除minP了
            pp = minPP;
        }

        TreeNode child;
        if (p.leftTree != null) {
            child = p.leftTree;
        } else if (p.rightTree != null) {
            child = p.rightTree;
        } else {
            child = null;
        }
        if (pp == null) tree = child; // 删除的是根节点
        else if (pp.leftTree == p) pp.leftTree = child;
        else pp.rightTree = child;
    }

    /**
     * 查询最大的节点值
     * @return Min Tree Node
     */
    public TreeNode findMax() {
        if (tree == null) return null;
        TreeNode p = tree;

        while (p.rightTree!=null) {
            p = p.rightTree;
        }
        return p;
    }

    /**
     * 查询最小的节点值
     * @return Max Tree Node
     */
    public TreeNode findMin() {
        if (tree == null) return null;
        TreeNode p = tree;
        while (p.leftTree!=null) {
            p = p.leftTree;
        }
        return p;
    }

    /**
     * 创建二叉树
     * @param linkedList {@link LinkedList}
     * @return Tree
     */
    public TreeNode createBinaryTree(LinkedList<Integer> linkedList) {
        TreeNode tree = null;

        if (linkedList == null || linkedList.isEmpty()) return null;
        Integer data = linkedList.removeFirst();
        if (data != null) {
            tree = new TreeNode(data);
            tree.leftTree = createBinaryTree(linkedList);
            tree.rightTree = createBinaryTree(linkedList);
        }
        return tree;
    }

    public static void main(String[] args) {
        BinarySearchTree st = new BinarySearchTree();
        LinkedList<Integer> integers = new LinkedList<>(Arrays.asList(33, 16, 13, null, 15, null, null, 18, 17, null, null,
                25, 19, null, null, 27, null, null, 50, 34, null, null, 58, 51, null, 55, null, null, 66, null, null));
        st.tree = st.createBinaryTree(integers);
//        st.delete(19);
//        System.out.println(st.tree.toString());
        TreeNode maxNode = st.findMax();
        System.out.println(maxNode.data);
        TreeNode minNode = st.findMin();
        System.out.println(minNode.data);

    }

    private static class TreeNode {
        private int data;
        private TreeNode leftTree;
        private TreeNode rightTree;

        public TreeNode(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "data=" + data +
                    ", leftTree=" + leftTree +
                    ", rightTree=" + rightTree +
                    '}';
        }
    }
}
