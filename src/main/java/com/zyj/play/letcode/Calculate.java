package com.zyj.play.letcode;

import com.zyj.play.letcode.lianbiao.ListNode;
import com.zyj.play.letcode.tree.TreeNode;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

import java.io.IOException;
import java.util.*;

/**
 * @author zhangyingjie
 * //通过两个栈实现一个计算器 逆波兰式方式进行计算会比较简单
 */
public class Calculate {
    public static void main(String[] args) throws IOException {
//        int[] arr = {1, 2, 3, 1};
//        System.out.println(massage(arr));
        test();
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

    public static Character findTheDifference(String s, String t) {
        Map<Character, List<Character>> sMap = new HashMap<>();
        Map<Character, List<Character>> tMap = new HashMap<>();
        getMap(s, t, sMap);
        getMap(t, s, tMap);
        if (sMap.size() == tMap.size()) {
            Iterator<Character> iterator = sMap.keySet().iterator();
            while (iterator.hasNext()) {
                Character next = iterator.next();
                if (tMap.get(next).size() > sMap.get(next).size()) {
                    return next;
                }
            }
        } else {
            tMap.keySet().removeAll(sMap.keySet());
            Iterator<Character> iterator = tMap.keySet().stream().iterator();
            while (iterator.hasNext()) {
                return iterator.next();
            }
        }
        return null;
    }

    public static void getMap(String s, String t, Map<Character, List<Character>> stringListMap) {
        for (int i = 0; i < s.length(); i++) {
            if (stringListMap.containsKey(s.charAt(i))) {
                stringListMap.get(s.charAt(i)).add(s.charAt(i));
            } else {
                List<Character> values = new ArrayList<>();
                values.add(s.charAt(i));
                stringListMap.put(s.charAt(i), values);
            }
        }
    }

    public static TreeNode invertTree(TreeNode node) {
        if (node == null) {
            return null;
        }
        TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
        invertTree(node.left);
        invertTree(node.right);
        return node;
    }

    public static int fib(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    public static int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int prev = 0, curr = 0;
        for (int i = 2; i <= n; i++) {
            int result2 = prev + cost[i - 2];
            int result1 = curr + cost[i - 1];
            int next = Math.min(result1, result2);
            prev = curr;
            curr = next;
        }
        return curr;
    }

    public static int getMaximumGenerated(int n) {
        Integer[] nums = new Integer[n + 1];
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else if (n > 1) {
            nums[0] = 0;
            nums[1] = 1;
            for (int i = 1; i <= n / 2; i++) {
                nums[2 * i] = nums[i];
                if (2 * i == n) {
                    Arrays.sort(nums, Comparator.comparingInt(o -> o));
                    return nums[n];
                }
                nums[(2 * i) + 1] = nums[i] + nums[i + 1];
            }
        }
        Arrays.sort(nums, Comparator.comparingInt(o -> o));
        return nums[n];
    }


    public static int tribonacci(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        Integer[] num = new Integer[n + 1];
        num[0] = 0;
        num[1] = 1;
        num[2] = 1;
        if (n >= 3) {
            for (int i = 0; i < n - 2; i++) {
                num[i + 3] = num[i] + num[i + 1] + num[i + 2];
            }
        }
        return num[n];
    }

    public static int numWays(int n) {
        if (n <= 1) return 1;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        //dp[2] = 2;

        for (int i = 2; i < n + 1; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % 1000000007;
        }
        return dp[n];
    }

    public static int fib_2(int n) {
        int a = 0, b = 1, sum;
        for (int i = 0; i < n; i++) {
            sum = (a + b) % 1000000007;
            a = b;
            b = sum;
        }
        return a;
    }


    public static int ways;
    public int n, k;
    public static List<List<Integer>> edges;

    /**
     * 深度优先搜索
     *
     * @param n        n表示的是到的数
     * @param relation 给数字
     * @param k        可以走几步
     * @return 总共可以采用的方案
     */
    public int numWays(int n, int[][] relation, int k) {
        ways = 0;
        this.n = n;
        this.k = k;
        edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }
        for (int[] edge : relation) {
            int src = edge[0], dst = edge[1];
            edges.get(src).add(dst);
        }
        dfs(0, 0);
        return ways;
    }

    /**
     * @param index 是不是路线要到的树
     * @param steps 走的步数
     */
    public void dfs(int index, int steps) {
        if (steps == k) {
            if (index == n - 1) {
                ways++;
            }
            return;
        }
        List<Integer> list = edges.get(index);
        for (int nextIndex : list) {
            dfs(nextIndex, steps + 1);
        }
    }

    /**
     * 一个数组非相邻两个数字加和最大
     *
     * @param nums
     * @return
     */
    public static int massage(int[] nums) {
        for (int i = 0; i < nums.length; i = i + 2) {

        }
        return 1;
    }

    public static void test() throws IOException {
        HdfsConfiguration entries = new HdfsConfiguration();
        String s = entries.get("fs.defaultFS");
        System.out.println("s==={}" + s);
        FileSystem fs = FileSystem.get(entries);
        System.out.println("fs=====" + fs.getClass().getSimpleName());
        entries.set("fs.defaultFS", "hdfs://yingjiedeMacBook-Prolocal.local:8280");
        FileSystem fs1 = FileSystem.get(entries);
        System.out.println("fs=====" + fs1.getClass().getSimpleName());

        Configuration entries1 = new Configuration();
        String s1 = entries1.get("fs.defaultFS");
        System.out.println("s1==={}" + s1);


        YarnConfiguration yarnConfiguration = new YarnConfiguration();
        String s2 = yarnConfiguration.get("fs.defaultFS");
        System.out.println("s2====={}" + s2);
    }

    
}
