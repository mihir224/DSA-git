public class ListNode {
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

    static Node middleNode(Node head){
        Node fast=head;
        Node slow=head;
        while(fast!=null&&fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        } //at the end the fast pointer reaches the end of the node and slow is still at middle
        return slow;
    }


}

