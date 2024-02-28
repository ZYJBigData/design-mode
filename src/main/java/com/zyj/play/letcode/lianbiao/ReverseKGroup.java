package com.zyj.play.letcode.lianbiao;

import com.zyj.play.letcode.pojo.ListNode;

/**
 * @author zhangyingjie
 */
public class ReverseKGroup {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        ReverseKGroup reverseKGroup = new ReverseKGroup();
        ListNode listNode = reverseKGroup.reverseKGroup(head, 3);
        System.out.println("listNode=={}" + listNode);
    }

    ListNode successor = null;

    public ListNode reverseKGroup(ListNode head, int k) {
        int count = count(head);
        if (count == 1 || k == 1) {
            return head;
        }
        if (count / k < 1) {
            return reverseN(head, count);
        } else {
            int shang = count / k;
            return digui(reverseN(head, k), k + 1, k + k, shang * k, k);
        }
    }

    public ListNode digui(ListNode head, int left, int right, int count, int k) {
        if (right > count) {
            return head;
        }
        digui(reverseBetween(head, left, right), left + k, right + k, count, k);
        return head;
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == 1) {
            return reverseN(head, right);
        }
        head.next = reverseBetween(head.next, left - 1, right - 1);
        return head;
    }

    public ListNode reverseN(ListNode head, int right) {
        if (right == 1) {
            successor = head.next;
            return head;
        }
        ListNode last = reverseN(head.next, right - 1);
        head.next.next = head;
        head.next = successor;
        return last;
    }

    public int count(ListNode head) {
        int count = 0;
        while (head != null) {
            head = head.next;
            count++;
        }
        return count;
    }
}
