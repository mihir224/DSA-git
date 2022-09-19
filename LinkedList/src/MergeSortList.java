public class MergeSortList {
    //https://leetcode.com/problems/sort-list/
    public static void main(String[] args) {

    }
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
        public ListNode sortList(ListNode head){
            if(head==null||head.next==null){ //case when the list is empty or there is only a single item in the list
                return head;
            }
            ListNode mid=getMid(head);
            ListNode left=sortList(head);
            ListNode right=sortList(mid);

            return merge(left,right);
        }
        ListNode getMid(ListNode head) {
            ListNode midPrev = null;
            while (head != null && head.next != null) {
                midPrev = (midPrev == null) ? head : midPrev.next;
                head = head.next.next;
            }
            ListNode mid = midPrev.next;
            midPrev.next = null; //the list is broken into two parts here: head till midPrev and mid till end
            return mid;
        }

        public ListNode merge(ListNode head1, ListNode head2){
            ListNode head=new ListNode();
            ListNode temp=head;
            while(head1!=null&&head2!=null){
                if(head1.value<head2.value){
                    temp.next=head1;
                    head1=head1.next;
                }
                else{
                    temp.next=head2;
                    head2=head2.next;
                }
                temp=temp.next;
            }
            temp.next=(head1!=null)?head1:head2;
            return head.next;
        }


}
}
