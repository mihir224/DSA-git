public class LinkedListCycle2 {
    //https://leetcode.com/problems/linked-list-cycle-ii/
private class ListNode {
    private int value;
    private ListNode next;

    public ListNode() {
    }

    public ListNode(int value) {
        this.value = value;
    }

    public ListNode(int value, ListNode next) {
        this.value = value;
        this.next = next;
    }
}
    public ListNode detectCycle(ListNode head) {
        int length=cycleLength(head);
        if(length==0){
            return null;
        }
        ListNode first=head;
        ListNode second=head;
        while(length>0){
            second=second.next;
            length--;
        }
        while(first!=second){
            first=first.next;
            second=second.next;
        }
        return first;
    }

    public int cycleLength(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                int count = 0;
                ListNode temp = slow;
                do {
                    temp = temp.next;
                    count++;
                } while (temp != slow);
                return count;
            }
        }
        return 0;
    }
}