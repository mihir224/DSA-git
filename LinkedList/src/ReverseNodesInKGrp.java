public class ReverseNodesInKGrp {
    //https://leetcode.com/problems/reverse-nodes-in-k-group/
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
    public ListNode reverseKGroup(ListNode head, int k){
        if(k<1||head==null){
            return head;
        }
        ListNode prev=null;
        ListNode current=head;
        int length=getLength(head);
        int count=length/k;
         while(count>0){
             ListNode temp=prev;
             ListNode newLast=current;
             ListNode next=current.next;
             for(int i=0;current!=null&&i<k;i++){
                 current.next=prev;
                 prev=current;
                 current=next;
                 if(next!=null){
                     next=next.next;
                 } //by the end of this loop prev is at the head as present=null
             }
             if(temp!=null){ //connecting the remaining nodes after reversal
                 temp.next=prev;
             }
             else{
                 head=prev;
             }
             newLast.next=current;
             prev=newLast;
             count--;
         }
        return head;
    }
    public int getLength(ListNode head){
        ListNode temp=head;
        int length=1;
        while(temp!=null){
            temp=temp.next;
            length++;
        }
        return length;
    }
}
