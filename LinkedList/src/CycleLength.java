public class CycleLength {
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
    public int cycleLength(Node head){
        Node fast=head;
        Node slow=head;
        while(fast!=null&&fast.next!=null){
            fast=fast.next.next;
            slow=slow.next;
            if(fast==slow){
                int count=0;
                Node temp=slow;
                do{
                    temp=temp.next;
                    count++;
                }while(temp!=slow);
                return count;
            }
        }
        return 0;
    }

}
