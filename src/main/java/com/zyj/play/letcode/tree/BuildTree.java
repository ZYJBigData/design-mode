package com.zyj.play.letcode.tree;

import java.util.HashMap;

/**
 * 给定一棵树的前序遍历 preorder 与中序遍历  inorder。请构造二叉树并返回其根节点
 * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * Output: [3,9,20,null,null,15,7]
 *
 * @author zhangyingjie
 */
public class BuildTree {
    public static void main(String[] args) {
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
//        int[] preorder1 = {1, 2, 3};
//        int[] inorder1 = {1, 2, 3};
        TreeNode treeNode = buildTree(preorder, inorder);
//        TreeNode treeNode = buildTree(preorder1, inorder1);
        System.out.println("treeNode=={}" + treeNode);
    }

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTreeHelper(preorder, 0, preorder.length, inorder, 0, inorder.length, map);
    }

    private static TreeNode buildTreeHelper(int[] preorder, int p_start, int p_end, int[] inorder, int i_start, int i_end,
                                     HashMap<Integer, Integer> map) {
        if (p_start == p_end) {
            return null;
        }
        int root_val = preorder[p_start];
        TreeNode root = new TreeNode(root_val);
        int i_root_index = map.get(root_val);
        int leftNum = i_root_index - i_start;
        root.left = buildTreeHelper(preorder, p_start + 1, p_start + leftNum + 1, inorder, i_start, i_root_index, map);
        root.right = buildTreeHelper(preorder, p_start + leftNum + 1, p_end, inorder, i_root_index + 1, i_end, map);
        return root;
    }
}
