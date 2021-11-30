package com.zyj.play.letcode.lianbiao;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhangyingjie
 */
@Data
@ToString
public class ListNode {
    public int val;
    public ListNode next;
    ListNode() {

    }
    public ListNode(int val) {
        this.val = val;
    }
    ListNode(int val, ListNode next) {
        this.val = val; this.next = next;
    }
}
