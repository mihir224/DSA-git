public class MergeTwoSortedLists {
    //https://leetcode.com/problems/merge-two-sorted-lists/
    private Node head;
    private Node tail;
    private int size;

    public MergeTwoSortedLists() {
        this.size = 0;
    }

    private class Node{
        private int value;
        private Node next;

        public Node() {
        }

        public Node(int value) {
            this.value = value;
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    public Node mergeTwoList(Node head1, Node head2){
        Node head=new Node();
        Node temp=head;
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
