public class DoublyLL {
    private Node head;
    private Node tail;
    private int size;
    public DoublyLL(){this.size=0;}
    private class Node{
        int value;
        Node next;
        Node prev;

        public Node(int value) {
            this.value = value;
        }

        public Node(int value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
    public void insertAtFirst(int value){
        Node node=new Node(value);
        node.next=head;
        node.prev=null;
        if(head!=null){ //handling null pointer exception
            head.prev=node;
        }
        head=node;
    }
    public void insertAtLast(int value){
        Node node=new Node(value);
        node.next=null;
        Node temp=head;
        if(head==null) //case when list is empty
        {
            node.prev=null;
            head=node;
        }
        while(temp.next!=null){ //traversing the list till the last node which points to null
            temp=temp.next;
        }
        temp.next=node;
        node.prev=temp;
    }

    public void insertAfterNode(int after, int value){
        Node p=find(after);
        Node node=new Node(value);
        node.next=p.next;
        p.next=node;
        node.prev=p;
        if(node.next!=null){
            node.next.prev=node;
        }
    }
    public Node find(int value){
        Node node=head;
        while(node!=null){
            if(node.value==value){
                return node;
            }
            node=node.next;
        }
        return null;
    }

    public void display(){
        Node temp=head;
        Node last=null;
        while(temp!=null){
            System.out.print(temp.value + "->");
            last=temp; //this will assign it the last node
            temp=temp.next;
        }
        System.out.println("END");
        System.out.println();
        while(last!=null){
            System.out.print(last.value + "->" );
            last=last.prev;
        }
        System.out.println("START");
    }

}
