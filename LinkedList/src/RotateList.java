public class RotateList {
    //https://leetcode.com/problems/rotate-list/
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
    public ListNode rotateRight(ListNode head, int k){
       if(head==null||head.next==null||k<=0){
           return head;
       }
       ListNode last=head;
       int length=1;
       while(last.next!=null){ //to find the last node
           last=last.next;
           length++;
       }
       last.next=head; //connecting the last node to the original head
       int rotations=k%length;
       ListNode newLast=head;
       for(int i=0;i<length-rotations-1;i++){ //to find the new last node
            newLast=newLast.next;
       }
       head=newLast.next;
       newLast.next=null;
       return head;
    }
}
