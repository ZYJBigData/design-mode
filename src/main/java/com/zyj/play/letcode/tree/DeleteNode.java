package com.zyj.play.letcode.tree;

/**
 * 给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
 * 一般来说，删除节点可分为两个步骤：
 * 首先找到需要删除的节点；
 * 如果找到了，删除它。
 * 说明： 要求算法时间复杂度为 O(h)，h 为树的高度。
 * root = [5,3,6,2,4,null,7]
 * key = 3
 * <p>
 * 5
 * / \
 * 3   6
 * / \   \
 * 2   4   7
 * <p>
 * 一.给定需要删除的节点值是 3，所以我们首先找到 3 这个节点，然后删除它。
 * 一个正确的答案是 [5,4,6,2,null,null,7], 如下图所示。
 * <p>
 * 5
 * / \
 * 4   6
 * /     \
 * 2       7
 * <p>
 * 二。另一个正确答案是 [5,2,6,null,4,null,7]。
 * 5
 * / \
 * 2   6
 * \   \
 * 4   7
 * 注意：所谓的二叉搜索树的规定是： 右子树>根节点>左子树
 *
 * @author zhangyingjie
 * <p>
 * 5
 * / \
 * 3   6
 * / \   \
 * 2   4   7
 */
public class DeleteNode {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(7);

        DeleteNode deleteNode = new DeleteNode();
        TreeNode treeNode = deleteNode.deleteNode(root, 3);
        System.out.println("result=={}" + treeNode);
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.val == key) {
            //删除度为0的数
            if (root.right == null) {
                return root.left;
            }
            if (root.left == null) {
                return root.right;
            }
            TreeNode minNode = getMin(root.right);
            root.val = minNode.val;
            root.right = deleteNode(root.right, minNode.val);
        }
        if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else {
            root.right = deleteNode(root.right, key);
        }
        return root;
    }

    private TreeNode getMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
