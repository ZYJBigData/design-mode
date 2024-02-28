package com.zyj.play.letcode.lianbiao;

import com.zyj.play.letcode.pojo.ListNode;

/**
 * @author zhangyingjie
 */
public class MiddleNode {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        listNode.next.next.next = new ListNode(4);
        listNode.next.next.next.next = new ListNode(5);
        MiddleNode middleNode = new MiddleNode();
        ListNode listNode1 = middleNode.middleNode(listNode);
        System.out.println("listNode1=={}" + listNode1);
    }

    public ListNode middleNode(ListNode head) {
        ListNode listNode = head;
        int count = 0;
        while (listNode != null) {
            count++;
            listNode = listNode.next;
        }
        int begin = count / 2;
        while (begin > 0) {
            head = head.next;
            begin--;
        }
        return head;
    }
}
