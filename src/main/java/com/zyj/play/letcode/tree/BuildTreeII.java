package com.zyj.play.letcode.tree;

import java.util.HashMap;

/**
 * 根据一棵树的中序遍历与后序遍历构造二叉树。
 * 返回如下的二叉树：
 * <p>
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 *
 * @author zhangyingjie
 */
public class BuildTreeII {
    private static HashMap<Integer, Integer> map = new HashMap<>();
    private static int[] postorderCopy;

    public static void main(String[] args) {
        int[] inorder = {9, 3, 15, 20, 7}; //中序遍历
        int[] postorder = {9, 15, 7, 20, 3}; //后续遍历
        TreeNode treeNode = buildTree(inorder, postorder);
        System.out.println("treeNode=={}" + treeNode);

    }

    public static TreeNode buildTree(int[] inorder, int[] postorder) {
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        postorderCopy = postorder;
        return buildTreeHelper(0, inorder.length - 1, 0, postorder.length - 1);
    }

    public static TreeNode buildTreeHelper(int iLeft, int iRight, int pLeft,
                                           int pRight) {
        if(iRight < iLeft || pRight < pLeft){
            return null;
        }
        TreeNode root = new TreeNode(postorderCopy[pRight]);
        int inorderRootIndex = map.get(postorderCopy[pRight]);
        //左子树的长度
        int leftTreeSize = inorderRootIndex - iLeft;
        root.left = buildTreeHelper(iLeft, inorderRootIndex - 1, pLeft, pLeft + leftTreeSize - 1);
        root.right = buildTreeHelper(inorderRootIndex + 1, iRight, pLeft + leftTreeSize, pRight - 1);
        return root;
    }
}
