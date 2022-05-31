package com.zyj.play.letcode.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhangyingjie
 */
public class TreeNodeTest {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = null;
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);
        List<Integer> list = preorderTraversal(root);
        System.out.println("list===={}" + list);
        HashMap<String, String> map = new HashMap<>();
        
    }

    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList();
        if (root == null) {
            return result;
        }
        preorderTraversalC(root, result);
        return result;
    }

    //前序遍历
    public static void preorderTraversalC(TreeNode root, List<Integer> result) {
        System.out.println("root.val=={}" + root.val);
        result.add(root.val);
        if (root.left != null) {
            preorderTraversalC(root.left,result);
        }
        if (root.right != null) {
            preorderTraversalC(root.right,result);
        }
    }
}
