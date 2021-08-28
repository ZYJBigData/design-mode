package com.zyj.play.letcode.tree;

/**
 * 给定一个二叉树，检查它是否是镜像对称的。
 * <p>
 * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
 * <p>
 * 1
 * / \
 * 2   2
 * / \ / \
 * 3  4 4  3
 * <p>
 * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
 * <p>
 * 1
 * / \
 * 2   2
 * \   \
 * 3    3
 *
 * @author zhangyingjie
 */
public class IsSymmetric {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);

        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);

        boolean result = isSymmetric(root);
        System.out.println("result==={}" + result);

    }

    public static boolean isSymmetric(TreeNode root) {
        return preTree(root, root);
    }

    public static boolean preTree(TreeNode tree1, TreeNode tree2) {

        if (tree1 == null && tree2 == null) {
            return true;
        }

        if (tree1 == null || tree2 == null) {
            return false;
        }

        if (tree1.val != tree2.val) {
            return false;
        }

        return preTree(tree1.left, tree2.right) && preTree(tree1.right, tree2.left);
    }
}
