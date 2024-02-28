package com.zyj.play.letcode;

import java.util.HashMap;

/**
 * @author zhangyingjie
 */
public class VersionControl {
    private static HashMap<Integer, Integer> map = new HashMap<>();
    private static int[] postorderCopy;

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
}
