import java.util.List;

public class ReorderList {
    private class ListNode{
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
    public void reorderList(ListNode head){
        if(head==null||head.next==null){ //case when list is empty or there is only one element in the list
            return;
        }
        ListNode mid=middleNode(head);
        ListNode headFirst=head;
        ListNode headSecond=reverseList(mid); //reversing the list from mid in order to move backward in fwd direction

        while(headFirst!=null&&headSecond!=null){
            ListNode temp=headFirst.next; //temp ensures that head.next and headSecond.next point to the original nodes before reordering
            headFirst.next=headSecond;
            headFirst=temp;
            temp=headSecond.next;
            headSecond.next=headFirst;
            headSecond=temp;
        }
        if(headFirst!=null) { //to point the last node ie whatever headFirst would be after the reordering to null
            headFirst.next=null;
        }
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
    public ListNode reverseList(ListNode head) {
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
