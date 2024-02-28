package com.zyj.play.letcode.lianbiao;

import com.zyj.play.letcode.pojo.ListNode;

/**
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 * 进阶：你能尝试使用一趟扫描实现吗？
 *
 * @author zhangyingjie
 */
public class RemoveNthFromEnd {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        listNode.next.next.next = new ListNode(4);
        listNode.next.next.next.next = new ListNode(5);
        RemoveNthFromEnd removeNthFromEnd = new RemoveNthFromEnd();
        //删除倒数第二个，所以这里删除的是4
        ListNode listNode1 = removeNthFromEnd.removeNthFromEnd(listNode, 2);
        System.out.println("listNode1==={}" + listNode1);
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        int count = 0;
        ListNode listNode = head;
        ListNode tail = head;
        while (listNode != null) {
            count++;
            listNode = listNode.next;
        }
        int begin = count - n - 1;
        while (begin > 0 && tail != null) {
            tail = tail.next;
            begin--;
        }
        if (begin == -1 && tail != null) {
            tail = tail.next;
            return tail;
        } else {
            if (tail != null) {
                tail.next = tail.next.next;
            }
        }
        return head;
    }
}
