package com.zyj.play.letcode.tree;

/**
 * 给定一个不含重复元素的整数数组 nums 。一个以此数组直接递归构建的 最大二叉树 定义如下：
 * <p>
 * 二叉树的根是数组 nums 中的最大元素。
 * 左子树是通过数组中 最大值左边部分 递归构造出的最大二叉树。
 * 右子树是通过数组中 最大值右边部分 递归构造出的最大二叉树。
 * 返回有给定数组 nums 构建的 最大二叉树 。
 *
 * @author zhangyingjie
 */
public class ConstructMaximumBinaryTree {
    public static void main(String[] args) {
        int[] nums = {3, 2, 1, 6, 0, 5};
        int[] nums2 = {3, 2, 1};
        int[] nums3 = {1, 2, 3};
        TreeNode treeNode = constructMaximumBinaryTree(nums3);
        System.out.println(treeNode);
     }
    public static TreeNode constructMaximumBinaryTree(int[] nums) {
        return construct(nums, 0, nums.length);
    }
    public static TreeNode construct(int[] nums, int begin, int size) {
        if (begin == size) {
            return null;
        }
        int maxIndex = max(nums, begin, size);
        TreeNode root = new TreeNode(nums[maxIndex]);
        root.left = construct(nums, begin, maxIndex);
        root.right = construct(nums, maxIndex + 1, size);
        return root;
    }
    //得到最大值的位置
    public static int max(int[] nums, int begin, int size) {
        int maxIndex = begin;
        for (int i = begin; i < size; i++) {
            if (nums[maxIndex] < nums[i]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
