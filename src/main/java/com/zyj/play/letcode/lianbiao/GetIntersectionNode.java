package com.zyj.play.letcode.lianbiao;

import java.util.Objects;

/**
 * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
 * 输出：Reference of the node with value = 8
 * 输入解释：相交节点的值为 8 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,0,1,8,4,5]。在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
 * <p>
 * <p>
 * <p>
 * 输入：intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
 * 输出：Reference of the node with value = 2
 * 输入解释：相交节点的值为 2 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [0,9,1,2,4]，链表 B 为 [3,2,4]。在 A 中，相交节点前有 3 个节点；在 B 中，相交节点前有 1 个节点。
 *
 * @author zhangyingjie
 */
public class GetIntersectionNode {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ListNode)) {
                return false;
            }
            ListNode listNode = (ListNode) o;
            return val == listNode.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }
    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        //双向链表解决这个问题
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            System.out.println("pA=={}" + pA);
            System.out.println("pB=={}" + pB);
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        System.out.println("pA=={}" + pA);
        return pA;
    }

    public static void main(String[] args) throws InterruptedException {
        int[] listA = {0, 9, 1, 2, 4};
        int[] listB = {3, 2, 4};
        ListNode headA = new ListNode(listA[0]);
        ListNode headB = new ListNode(listB[0]);
        ListNode tempA;
        tempA = headA;
        ListNode tempB;
        tempB = headB;
        for (int i = 1; i < listA.length; i++) {
            tempA.next = new ListNode(listA[i]);
            tempA = tempA.next;
        }
        for (int j = 1; j < listB.length; j++) {
            tempB.next = new ListNode(listB[j]);
            tempB = tempB.next;
        }
        System.out.println("headA==={}" + headA + ",headB==={}" + headB);
        ListNode result = getIntersectionNode(headA, headB);
        System.out.println("result=={}" + result);
    }
}
