public class ReverseList {
    //https://leetcode.com/problems/reverse-linked-list/
    private Node head;
    private Node tail;
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

    //recursive approach
    public void reverseList(Node node){
        if(node==tail){
            head=tail;
            return;
        }
        reverseList(node.next);
        tail.next=node;
        tail=node;
        tail.next=null;
    }

    //iterative approach

    public Node ReverseList(Node head) {
        if(head==null){
            return head;
        }
        Node prev=null;
        Node present=head;
        Node next=present.next;
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
