public class CLL {
    //9920102054 MIHIR SAINI E2
    //Q1
        private Node head;
        private Node tail;
        private int size;

        public CLL() {
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

    public static void main(String[] args) {
        CLL cll=new CLL();
        cll.insertAfterTail(5);
        cll.insertAfterTail(6);
        cll.insertAfterTail(7);
        cll.insertAfterTail(8);
        cll.insertAfterTail(9);
        cll.insertAfterTail(10);
        cll.insertAfterTail(11);
        cll.search(9);
    }
    public void search(int element) {
        Node current = head;
        int i = 1;
        boolean flag = false;
        if(head == null) {
            System.out.println("List is empty");
        }
        else {
            do{
                if(current.value ==  element) {
                    flag = true;
                    break;
                }
                current = current.next;
                i++;
            }while(current != head);
            if(flag)
                System.out.println("Element is present in the list at the position : " + i);
            else
                System.out.println("Element is not present in the list");
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
            if(temp.value==value){
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
                while(temp!=head);
                System.out.println("END");
            }
        }


}
