package com.zyj.play.letcode.tree;

/**
 * @author zhangyingjie
 */
public class Tree2str {
    public static void main(String[] args) {
        TreeNode root = new TreeNode();
        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
//        root.left.right = new TreeNode(4);
        String result = tree2str(root);
        System.out.println("result=={}" + result);
    }


    public static String tree2str(TreeNode root) {
        if (root == null) {
            return null;
        }
        return buildTree2str(root);
    }

    public static String buildTree2str(TreeNode root) {
        if (root != null) {
            if (root.left == null && root.right == null) {
                return root.val + "";
            }
            if (root.right == null) {
                return root.val + "(" + buildTree2str(root.left) + ")";
            }
            if (root.left == null) {
                return root.val + "()" + "(" + buildTree2str(root.right) + ")";
            }
            return root.val + "(" + buildTree2str(root.left) + ")" + "(" + buildTree2str(root.right) + ")";
        }
        return "";
    }
}
