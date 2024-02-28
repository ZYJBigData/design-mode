package com.zyj.play.letcode.lianbiao;

import com.zyj.play.letcode.pojo.ListNode;

/**
 * 存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除所有重复的元素，使每个元素 只出现一次 。
 * 返回同样按升序排列的结果链表。
 *
 * @author zhangyingjie
 */
public class DeleteDuplicates {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(3);
        DeleteDuplicates deleteDuplicates = new DeleteDuplicates();
        ListNode listNode = deleteDuplicates.deleteDuplicates(head);
        System.out.println("delete result={}" + listNode);
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head==null || head.next==null){
            return head;
        }
        ListNode flow = head;
        ListNode fast = head.next;
        while (fast != null) {
            if (fast.val == flow.val) {
                fast = fast.next;
                continue;
            }
            flow.next = fast;
            flow = fast;
        }
        flow.next=fast;
        return head;
    }
}
