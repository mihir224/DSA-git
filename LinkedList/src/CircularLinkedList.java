public class CircularLinkedList {
    private Node head;
    private Node tail;
    private int size;

    public CircularLinkedList() {
        this.size = 0;
    }

    private class Node{
       private int value;
       private Node next;

        public Node(int value) {
            this.value = value;
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }
    public void insertAfterTail(int value){
        Node node =new Node(value);
        if(head==null){
            head=node;
            tail=node;
            return;
        }
        tail.next=node;
        node.next=head;
        tail=node;
    }
    public void delete(int value){
        Node temp=head;
        if(head==null){
            return;
        }
        if(temp.value==value){ //case when the node we wish to remove is the one to which head is pointing to
            head=head.next;
            tail.next=head;
            return;
        }
        do{
            if(temp.next.value==value){
                temp.next=temp.next.next;
                break;
            }
            temp=temp.next;
        }while(temp!=head);
    }
    public void display(){
        Node temp=head;
        if(head!=null){
            do{
                System.out.print(temp.value+"->");
                temp=temp.next;
            }
            while(temp!=head); //since the list is circular and eventually the temp will reach the head again
            System.out.println("END");
        }
    }

}
