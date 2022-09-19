public class oddEvenList {
    private ListNode head;
    private ListNode tail;
    private int size;

    public oddEvenList() {
        this.size = 0;
    }

    private class ListNode {
        private int value;
        private ListNode next;

        public ListNode(int value) {
            this.value = value;
        }

        public ListNode(int value, ListNode next) {
            this.value = value;
            this.next = next;
        }
    }

    //https://leetcode.com/problems/odd-even-linked-list/
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode odd = head;
        ListNode even = head.next;
        ListNode oddHead = head;
        ListNode evenHead = head.next;
        while (even != null && even.next != null) {
            odd.next = odd.next.next;
            even.next = even.next.next;
            odd = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }
}
//        int count=0;
//        while(end!=null){
//            end=end.next;
//            count++;
//        }
//        if(count%2==0){
//            count=(count/2)+1;
//        }
//        else{
//            count=count/2;
//        }
//        while(count) {
//            end.next = temp.next;
//            temp.next = temp.next.next;
//            end.next.next = null;
//            temp = temp.next;
//            end = end.next;
//        }

