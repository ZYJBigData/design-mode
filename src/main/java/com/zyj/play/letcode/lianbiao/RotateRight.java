package com.zyj.play.letcode.lianbiao;

import com.zyj.play.letcode.pojo.ListNode;

/**
 * 给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。
 *
 * @author zhangyingjie
 */
public class RotateRight {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        RotateRight rotateRight = new RotateRight();
        ListNode listNode = rotateRight.rotateRight(head, 2);
        //45123;
        System.out.println("result===" + listNode);
    }

//    public ListNode rotateRight(ListNode head, int k) {
//        if (head == null || head.next == null) {
//            return head;
//        }
//        ListNode headCopy=head;
//        LinkedList<Integer> list = new LinkedList<>();
//        while (headCopy != null) {
//            list.add(headCopy.val);
//            headCopy = headCopy.next;
//        }
//        int yushu = k % list.size();
//        if (yushu==0){
//            return head;
//        }
//        for (int i = 0; i < yushu; i++) {
//            list.addFirst(list.removeLast());
//        }
//        return digui(new ListNode(list.removeFirst()), list);
//    }
//
//    public ListNode digui(ListNode head, LinkedList<Integer> list) {
//        if (list.size() > 0) {
//            head.next = new ListNode(list.removeFirst());
//            digui(head.next, list);
//        }
//        return head;
//    }


    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return head;
        }

        // 计算链表中节点个数
        int len = calculateLen(head);
        k = k%len;

        // 慢指针初始指向头节点
        ListNode slow = head;
        // 快指针初始指向头节点
        ListNode fast = head;

        // 快指针先向前移动k步
        for(int i = 0; i < k; i++) {
            fast= fast.next;
        }

        // 快慢指针同时向前移动，直到快指针指向的节点的
        // 下一个节点为null
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // 快指针此时在链表末尾
        // 然后其指向的节点的后继指针指向头节点
        // 这时链表首尾相连成环
        fast.next = head;
        // 新的头节点是慢指针所指节点的下一个节点
        head = slow.next;
        // 慢指针所指节点的的后继指针指向null
        // 断开环
        slow.next = null;
        return head;
    }

    private int calculateLen(ListNode head){
        int len = 0;
        while (head!=null) {
            head = head.next;
            len++;
        }
        return len;
    }
}
