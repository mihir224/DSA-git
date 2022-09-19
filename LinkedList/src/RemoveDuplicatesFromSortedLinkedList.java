import java.util.Scanner;

public class RemoveDuplicatesFromSortedLinkedList {
    //https://leetcode.com/problems/remove-duplicates-from-sorted-list/submissions/
    private Node head;
    private Node tail;
    private int size;

    public RemoveDuplicatesFromSortedLinkedList() {
        this.size = 0;
    }

    public static void main(String[] args) {
        RemoveDuplicatesFromSortedLinkedList list=new RemoveDuplicatesFromSortedLinkedList();
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the number of elements");
        int n=sc.nextInt();
        for(int i=0;i<n;i++){
            int element=sc.nextInt();
            list.insertFirst(element);
        }
        list.removeDuplicate(list.head);
        list.display();
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

    public void insertFirst(int value){
        Node node=new Node(value);
        node.next=head;
        head=node;
        if(tail==null){
            tail=head;
        }
        size+=1;
    }
    public Node removeDuplicate(Node head){
        Node temp=head;
        if(head==null){
            return null;
        }
        while(temp.next!=null){
            if(temp.value==temp.next.value){
                temp.next=temp.next.next;
            }
            else{
                temp=temp.next;}
        }
        return head;
    }
    public void display(){
        Node temp=head;
        while(temp!=null){
            System.out.print(temp.value+"->");
            temp=temp.next;
        }
    }
}
