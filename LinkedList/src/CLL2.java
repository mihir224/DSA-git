class CLL2{
    //9920102054 MIHIR SAINI E2
    //Q2
    private Node head;
     class Node
    {
        int data;
        Node next;
        Node prev;

        public Node() {
        }

        public Node(int data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
     void insertEnd(int value)
    {
        if (head == null)
        {
            Node new_node = new Node();
            new_node.data = value;
            new_node.next = new_node.prev = new_node;
            head = new_node;
            return;
        }
        Node last = (head).prev;
        Node new_node = new Node();
        new_node.data = value;
        new_node.next = head;
        (head).prev = new_node;
        new_node.prev = last;
        last.next = new_node;
    }
   void display()
    {
        Node temp = head;

        System.out.printf("\nTraversal in " +
                "forward direction \n");
        while (temp.next != head)
        {
            System.out.printf("%d ", temp.data);
            temp = temp.next;
        }
        System.out.printf("%d ", temp.data);

        System.out.printf("\nTraversal in reverse " +
                "direction \n");
        Node last = head.prev;
        temp = last;

        while (temp.prev != last)
        {
            System.out.printf("%d ", temp.data);
            temp = temp.prev;
        }
        System.out.printf("%d ", temp.data);
    }
  void updateNodeValue()
    {
        int sum = findSum(head);
        Node temp = head;
        while (temp.next != head)
        {
            temp.data = sum - temp.data;
            temp = temp.next;
        }
        temp.data = sum - temp.data;
    }

     Node formLinkedList(Node start) {
         insertEnd(4);
         insertEnd(5);
         insertEnd(6);
         insertEnd(7);
         insertEnd(8);
         return start;
     }
    static int findSum(Node head) {
        Node temp = head;
        int sum = 0;
        if (head != null) {
            do {
                temp = temp.next;
                sum += temp.data;
            } while (temp != head);
        }
        return sum;
    }
    public static void main(String args[])
    {
        Node head = null;
        CLL2 cll2=new CLL2();
        head = cll2.formLinkedList(head);
        cll2.display();
        cll2.updateNodeValue();
        cll2.display();
    }
}