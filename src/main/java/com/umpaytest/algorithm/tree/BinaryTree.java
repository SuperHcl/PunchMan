package com.umpaytest.algorithm.tree;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author: Hucl
 * @date: 2019/12/16 10:14
 * @description: 二叉树
 */
public class BinaryTree {
    public static TreeNode createBinaryTree(LinkedList<Integer> linkedList) {
        TreeNode tree = null;

        if (linkedList == null || linkedList.isEmpty()) return null;
        Integer data = linkedList.removeFirst();
        if (data != null) {
            tree = new TreeNode(data);
            tree.leftChild = createBinaryTree(linkedList);
            tree.rightChild = createBinaryTree(linkedList);
        }
        return tree;
    }

    /**
     * 前序遍历 递归实现
     * 根 左 右
     *
     * @param node 二叉树
     */
    public static void preOrderTraversal(TreeNode node) {
        if (node == null) return;
        System.out.println(node.data);
        preOrderTraversal(node.leftChild);
        preOrderTraversal(node.rightChild);
    }

    /**
     * 中序遍历 递归实现
     * 左 根 右
     *
     * @param node 二叉树
     */
    public static void inOrderTraversal(TreeNode node) {
        if (node == null) return;
        inOrderTraversal(node.leftChild);
        System.out.println(node.data);
        inOrderTraversal(node.rightChild);
    }

    /**
     * 后序遍历 递归实现
     * 左 右 根
     *
     * @param node 二叉树
     */
    public static void postOrderTraversal(TreeNode node) {
        if (node == null) return;
        postOrderTraversal(node.leftChild);
        postOrderTraversal(node.rightChild);
        System.out.println(node.data);
    }

    /**
     * 前序遍历 栈实现
     * 根 左 右
     * @param node 二叉树
     */
    public static void preOrderTraversalWithStack(TreeNode node) {
        TreeNode p = node;
        Stack<TreeNode> stack = new Stack<>();
        while (p != null || !stack.isEmpty()) {
            while (p != null) {
                System.out.println(p.data);
                stack.push(p);
                p = p.leftChild;
            }
            if (!stack.isEmpty()) {
                p = stack.pop();
                p = p.rightChild;
            }
        }
    }

    /**
     * 中序遍历 左 中 右
     * 栈实现
     * @param node 二叉树根节点
     */
    public static void inOrderTraversalWithStack(TreeNode node) {
        TreeNode p = node;
        Stack<TreeNode> stack = new Stack<>();
        while (p != null || !stack.isEmpty()) {
            while (p != null) {
                stack.push(p);
                p = p.leftChild;
            }
            if (!stack.isEmpty()) {
                p = stack.pop();
                System.out.println(p.data);
                p = p.rightChild;
            }
        }
    }

    /**
     * 后序遍历 左 右 中
     * 栈实现
     * @param root 二叉树根节点
     */
    public static void postOrderTraversalWithStack(TreeNode root) {

        TreeNode cur, pre = null;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.empty()) {
            cur = stack.peek();
            if ((cur.leftChild == null && cur.rightChild == null) || (pre != null && (pre == cur.leftChild || pre == cur.rightChild))) {
                System.out.print(cur.data + "->");
                stack.pop();
                pre = cur;
            } else {
                if (cur.rightChild != null)
                    stack.push(cur.rightChild);
                if (cur.leftChild != null)
                    stack.push(cur.leftChild);
            }
        }
    }

    /**
     * 广度优先遍历，按层遍历,从左到右
     *
     * @param root 二叉树
     */
    public static void levelOrderTraversal(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.println(node.data);
            if (node.leftChild != null) queue.offer(node.leftChild);
            if (node.rightChild != null) queue.offer(node.rightChild);
        }

    }

    public static void main(String[] args) {
        LinkedList<Integer> nodes = new LinkedList<>(Arrays.asList(3, 2, 9, null, null, 10, null, null, 8, null, 4));

        TreeNode binaryTree = createBinaryTree(nodes);
//        System.out.println("\n\n-----------前序遍历------------");
//        preOrderTraversal(binaryTree);
//        System.out.println("\n\n-----------中序遍历------------");
//        inOrderTraversal(binaryTree);
//        System.out.println("\n\n-----------后序遍历------------");
//        postOrderTraversal(binaryTree);
//        preOrderTraversalWithStack(binaryTree);
//        levelOrderTraversal(binaryTree);
//        inOrderTraversalWithStack(binaryTree);
        postOrderTraversalWithStack(binaryTree);

//        String a = "heloowosubgs";
//        System.out.println(a.indexOf("loo"));
    }


    private static class TreeNode {
        int data;
        TreeNode leftChild;
        TreeNode rightChild;

        TreeNode(int data) {
            this.data = data;
        }
    }
}
