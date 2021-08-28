package com.zyj.play.letcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyingjie
 */
public class CopyRandomList {

    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }

    public static void main(String[] args) {
        //构建一个新的测试链表出来
        int[] listA = {7, 13, 11, 10, 1};
        Node node = new Node(listA[0]);
        Node temp = node;
        temp.random = null;
        //先是构建next链表
        for (int i = 1; i < listA.length; i++) {
            temp.next = new Node(listA[i]);
            temp = temp.next;
        }
        //再构建random构成的链表
        node.next.random = node;
        node.next.next.random = node.next.next.next.next;
        node.next.next.next.random = node.next.next;
        node.next.next.next.next.random = node;
        Node nodeCopy = copyRandomList(node);
        System.out.println("nodeCopy=={}" + nodeCopy);
        System.out.println(nodeCopy.random);
        System.out.println(nodeCopy.next.random.val);
        System.out.println(nodeCopy.next.next.random.val);
        System.out.println(nodeCopy.next.next.next.random.val);
        System.out.println(nodeCopy.next.next.next.next.random.val);
    }
    static Map<Node, Node> cachedNode = new HashMap<Node, Node>();

    public static Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        if (!cachedNode.containsKey(head)) {
            Node headNew = new Node(head.val);
            cachedNode.put(head, headNew);
            headNew.next = copyRandomList(head.next);
            headNew.random = copyRandomList(head.random);
        }
        return cachedNode.get(head);
    }
}
