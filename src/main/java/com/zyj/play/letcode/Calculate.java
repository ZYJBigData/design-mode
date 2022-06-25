package com.zyj.play.letcode;

import com.zyj.play.letcode.lianbiao.ListNode;
import com.zyj.play.letcode.tree.TreeNode;

import java.util.*;

/**
 * @author zhangyingjie
 * //通过两个栈实现一个计算器 逆波兰式方式进行计算会比较简单
 */
public class Calculate {
    public static void main(String[] args) {
//        String s = "1 + 1";
//        String s1 = " 2-1 + 2 ";
//        String s2 = "(1+(4+5+2)-3)+(6+8)";
//        String s3 = "-(3+(4+5))";
//        int result = calculate(s3);
//        boolean palindrome = isPalindrome(12);
//        int i = climbStairs(6);
//        System.out.println(i);
//        System.out.println(palindrome);
//        System.out.println(result);
//        TreeNode treeNode = new TreeNode(3);
//        treeNode.left = new TreeNode(9);
//        treeNode.right = new TreeNode(20);
//        treeNode.right.left = new TreeNode(15);
//        treeNode.right.right = new TreeNode(7);
//        boolean balanced = isBalanced(treeNode);
//        System.out.println(balanced);
        //正确答案是 9 15 7 20 3
//        List<Integer> list = postorderTraversal(treeNode);
//        System.out.println(list);
//        boolean valid = isValid("()");
//        System.out.println(valid);
//[1,2,6,3,4,5,6]
//        ListNode listNode = new ListNode();
//        listNode.next = new ListNode(7);
//        listNode.next.next = new ListNode(7);
//        listNode.next.next.next = new ListNode(7);
//        listNode.next.next.next.next = new ListNode(7);
//        ListNode listNode1 = removeElements(listNode, 7);
//        System.out.println(listNode1);
//        listNode.next = new ListNode(1);
//        listNode.next.next = new ListNode(2);
//        listNode.next.next.next = new ListNode(6);
//        listNode.next.next.next.next = new ListNode(3);
//        listNode.next.next.next.next.next = new ListNode(4);
//        listNode.next.next.next.next.next.next = new ListNode(5);
//        listNode.next.next.next.next.next.next.next = new ListNode(6);
//        ListNode listNode1 = removeElements(listNode, 6);
//        System.out.println(listNode1);
//        int[] array = new int[]{-10, -3, 0, 5, 9};
//        System.out.println(sortedArrayToBST(array));
        System.out.println(isIsomorphic("badc", "baba"));
//        System.out.println(isIsomorphic("egg", "add"));
    }

    public static int calculate(String s) {
        Deque<Integer> ops = new LinkedList<>();
        ops.push(1);
        int sign = 1;
        int ret = 0;
        int n = s.length();
        int i = 0;
        while (i < n) {
            if (s.charAt(i) == ' ') {
                i++;
            } else if (s.charAt(i) == '+') {
                sign = ops.peek();
                i++;
            } else if (s.charAt(i) == '-') {
                sign = -ops.peek();
                i++;
            } else if (s.charAt(i) == '(') {
                ops.push(sign);
                i++;
            } else if (s.charAt(i) == ')') {
                ops.pop();
                i++;
            } else {
                long num = 0;
                while (i < n && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + s.charAt(i) - '0';
                    i++;
                }
                ret += sign * num;
            }
        }
        return ret;
    }

    public static boolean isPalindrome(int x) {
        String xStr = x + "";
        int left = 0;
        int right = xStr.length() - 1;
        while (right - left >= 1) {
            if (xStr.charAt(left) == xStr.charAt(right)) {
                right--;
                left++;
            } else {
                return false;
            }
        }
        return true;
    }

    public static int climbStairs(int n) {
        //这里大小根据自己需要，或者使用 List 也可以
        int[] dp = new int[100000];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; ++i) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }


    public int singleNumber(int[] nums) {
        int single = 0;
        for (int num : nums) {
            single ^= num;
        }
        return single;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode first = headA.next;
        ListNode second = headB.next;
        while (first != second) {
            first = first == null ? headB : first.next;
            second = second == null ? headA : second.next;
        }
        return first;
    }

    public static boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        } else {
            return Math.abs(height(root.left) - height(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);
        }
    }

    public static int height(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return Math.max(height(root.left), height(root.right)) + 1;
        }
    }

    public static List<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        traverse(root, result);
        return result;
    }

    public static void traverse(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        traverse(root.left, result);
        traverse(root.right, result);
        result.add(root.val);
    }

    public static boolean isValid(String s) {
        int length = s.length();
        if (length % 2 == 1) {
            return false;
        }
        Map<Character, Character> map = Collections.unmodifiableMap(new HashMap<Character, Character>() {
            {
                put(')', '(');
                put(']', '[');
                put('}', '{');
            }
        });
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (map.containsKey(ch)) {
                if (stack.isEmpty() || stack.peek() != map.get(ch)) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(ch);
            }
        }
        return stack.isEmpty();
    }

    public static ListNode removeElements(ListNode head, int val) {
        ListNode first = new ListNode(0);
        first.next = head;
        ListNode temp = first;
        while (temp.next != null) {
            if (temp.next.val == val) {
                temp.next = temp.next.next;
            } else {
                temp = temp.next;
            }
        }
        return head;
    }


    public static TreeNode sortedArrayToBST(int[] nums) {
        return buildTreeNode(nums, 0, nums.length - 1);
    }

    public static TreeNode buildTreeNode(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int midIndex = (left + right + 1) / 2;
        TreeNode treeNode = new TreeNode(nums[midIndex]);
        treeNode.left = buildTreeNode(nums, left, midIndex - 1);
        treeNode.right = buildTreeNode(nums, midIndex + 1, right);
        return treeNode;
    }

    public static boolean isIsomorphic(String s, String t) {
        Map<Character, Character> st = new HashMap<>();
        Map<Character, Character> ts = new HashMap<>();
        if (s.length() != t.length()) {
            return false;
        }
        return (check(st, s, t) && check(ts, t, s));
    }

    public static boolean check(Map<Character, Character> st, String s, String t) {
        for (int i = 0; i < s.length(); i++) {
            if (st.containsKey(s.charAt(i))) {
                if (!st.get(s.charAt(i)).equals(t.charAt(i))) {
                    return false;
                }
            } else {
                st.put(s.charAt(i), t.charAt(i));
            }
        }
        return true;
    }
}
