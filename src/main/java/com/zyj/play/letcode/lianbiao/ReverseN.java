package com.zyj.play.letcode.lianbiao;

/**
 * 旋转指定数字的的链表长度
 *
 * @author zhangyingjie
 */
public class ReverseN {
    ListNode successor = null; // 后驱节点
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        ReverseN reverseN = new ReverseN();
        ListNode listNode = reverseN.reverseN(head, 2);
        System.out.println("listNode=={}" + listNode);
    }
    // 反转以 head 为起点的 n 个节点，返回新的头结点
    ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            // 记录第 n + 1 个节点
            successor = head.next;
            return head;
        }
        // 以 head.next 为起点，需要反转前 n - 1 个节点
        ListNode last = reverseN(head.next, n - 1);

        head.next.next = head;
        // 让反转之后的 head 节点和后面的节点连起来
        head.next = successor;
        return last;
    }
}
