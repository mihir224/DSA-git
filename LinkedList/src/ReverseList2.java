public class ReverseList2  {
    //https://leetcode.com/problems/reverse-linked-list-ii/
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

    public ListNode reverseBetween(ListNode head, int left, int right){
        if(left==right) {  //case when the range which we want to reverse has only one element
            return head;
        }
        ListNode prev=null;
        ListNode current=head;


        for(int i=0;current!=null&&i<left-1;i++)//after this loop current will be the first node between left and right
            //and prev will be just before current
        {
            prev=current;
            current=current.next;
        }
        ListNode temp=prev; //refer to explanation in notes to understand better
        ListNode newEnd=current;


        //reversing
        ListNode next=current.next;
        int n=right-left+1; //length of the list between left and right
        for(int i=0;current!=null&&i<n;i++){ //this loop iterates till the length of the list between left and right
            current.next=prev;
            prev=current;
            current=next;
            if(next!=null){
                next=next.next;
            }
        }
        //connecting the nodes after reversal
        if(temp!=null){
            temp.next=prev;
        }
        else{
            head=prev;
        }
        newEnd.next=current;
        return head;
    }
}
