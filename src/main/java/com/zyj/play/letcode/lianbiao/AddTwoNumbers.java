package com.zyj.play.letcode.lianbiao;

import java.util.LinkedList;

/**
 * @author zhangyingjie
 */
public class AddTwoNumbers {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(2);
        listNode.next = new ListNode(4);
        listNode.next.next = new ListNode(3);

        ListNode listNode1 = new ListNode(5);
        listNode1.next = new ListNode(6);
        listNode1.next.next = new ListNode(4);

//        ListNode listNode = new ListNode(9);
//        listNode.next = new ListNode(9);
//        listNode.next.next = new ListNode(9);
//        listNode.next.next.next = new ListNode(9);
//        listNode.next.next.next.next = new ListNode(9);
//        listNode.next.next.next.next.next = new ListNode(9);
//        listNode.next.next.next.next.next.next = new ListNode(9);
//
//        ListNode listNode1 = new ListNode(9);
//        listNode1.next = new ListNode(9);
//        listNode1.next.next = new ListNode(9);
//        listNode1.next.next.next = new ListNode(9);
//        ListNode listNode = new ListNode(3);
//        listNode.next = new ListNode(7);
//        ListNode listNode1 = new ListNode(9);
//        listNode1.next = new ListNode(2);
        AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
        ListNode sumNode = addTwoNumbers.addTwoNumbers(listNode, listNode1);
        System.out.println("sumNode=={}" + sumNode);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int l1Count = count(l1);
        int l2Count = count(l2);
        if (l1Count >= l2Count) {
            return traverse(l1, l2);
        } else {
            return traverse(l2, l1);
        }
    }

    public ListNode traverse(ListNode l1, ListNode l2) {
        ListNode sumNode = new ListNode();
        LinkedList<Integer> list = new LinkedList<>();
        ListNode head;
        head = sumNode;
        int quotient = 0;
        int val;
        while (l1 != null) {
            if (l2 != null) {
                val = l1.val + l2.val;
                l2 = l2.next;
            } else {
                if (list.size() != 0 && list.getFirst() == 1) {
                    val = l1.val + list.removeFirst();
                } else {
                    val = l1.val;
                }
            }
            if (val >= 10) {
                val = val % 10;
                quotient = 1;
            }
            if (list.size() != 0 && list.getFirst() == 1) {
                Integer integer = list.removeFirst();
                head.val = val + integer;
            } else {
                head.val = val;
            }
            if (head.val >= 10) {
                head.val = head.val % 10;
                quotient = 1;
            }
            if (quotient == 1) {
                list.add(quotient);
                quotient = 0;
            }
            if (l1.next != null) {
                head = head.next = new ListNode();
            }
            if (l1.next == null && list.size() != 0) {
                head = head.next = new ListNode(list.removeFirst());
            }
            l1 = l1.next;
        }
        return sumNode;
    }

    public int count(ListNode head) {
        int count = 0;
        ListNode listNode = head;
        while (listNode != null) {
            count++;
            listNode = listNode.next;
        }
        return count;
    }
}
