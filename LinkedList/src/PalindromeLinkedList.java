import java.util.List;
//https://leetcode.com/problems/palindrome-linked-list/
public class PalindromeLinkedList {
    private class ListNode{
        private int val;
        private ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
public boolean isPalindrome(ListNode head){
        ListNode mid=middleNode(head);
        ListNode midHead=ReverseList(mid);
        ListNode rereverse=midHead;
        while(head!=null&&midHead!=null){ //loop will end as soon as the list from middle is completely traversed
            if(head.val!=midHead.val){ //break the loop
                break;
            }
            head=head.next;
            midHead=midHead.next;
        }
        ReverseList(rereverse); //in case we wish to retain the original list
        return (head==null)||(midHead==null); //returns true if any one of the lists is completely traversed
    }
    public ListNode middleNode(ListNode head){
        ListNode fast=head;
        ListNode slow=head;
        while(fast!=null&&fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        } //at the end the fast pointer reaches the end of the node and slow is still at middle
        return slow;
    }
    public ListNode ReverseList(ListNode head) {
        if(head==null){
            return head;
        }
        ListNode prev=null;
        ListNode present=head;
        ListNode next=present.next;
        while(present!=null){
            present.next=prev;
            prev=present;
            present=next;
            if(next!=null){
                next=next.next;
            } //by the end of this loop prev is at the head as present=null
        }
        return prev;
    }
}
