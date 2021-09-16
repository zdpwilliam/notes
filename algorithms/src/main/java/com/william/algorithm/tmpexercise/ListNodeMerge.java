package com.william.algorithm.tmpexercise;

/**
 * @Package com.william.algorithm.tmpexercise
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/8/19 08:03
 * @Version V1.0
 */
public class ListNodeMerge {

    public static class ListNode {
        int value;
        ListNode next;

        public ListNode(int value, ListNode next) {
            this.value = value;
            this.next = next;
        }

        public ListNode(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1, new ListNode(3, new ListNode(5, new ListNode(7))));
        ListNode l2 = new ListNode(2, new ListNode(4, new ListNode(6, new ListNode(8))));
        ListNode result = merge(l1, l2);
        do {
            System.out.println(result.value);
            result = result.next;
        }
        while (result != null);
    }

    private static ListNode merge(ListNode l1, ListNode l2) {
        if(l1 == null) {
            return l2;
        }
        if(l2 == null) {
            return l1;
        }
        if(l1.value > l2.value) {
            l2.next = merge(l1, l2.next);
            return l2;
        } else {
            l1.next = merge(l1.next, l2);
            return l1;
        }
    }

}
