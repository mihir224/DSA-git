import java.util.Scanner;

public class LinkedList {
    //9920102054 MIHIR SAINI E2
    //Q1
    public static void main(String[] args) {
//        LinkedList list=new LinkedList();
//        Scanner sc=new Scanner(System.in);
//        System.out.println("Enter no. of elements");
//        int n=sc.nextInt();
//        for(int i=0;i<n;i++){
//            list.insertFirst(sc.nextInt());
//        }
//        list.deleteFirst();
//        list.deleteLast();
        System.out.println(12500*Math.pow(1.06,2.5));

    }
    private Node head;
    private Node tail;
    private int size;
    public LinkedList() {
        this.size = 0;
    }
    private class Node{
        private int value;
        private Node next;
        public Node(int value){
            this.value=value;
        }
        public Node(int value, Node next){
            this.value=value;
            this.next=next;
        }

    }

//insertions
    public void insertFirst(int value){ //to insert an element at first position
        Node node=new Node(value);
        node.next=head;
        head=node;
        if(tail==null){ //case when linkedlist is empty
            tail=head;
        }
        size+=1;
    }
    public void insertLast(int value){
        if(tail==null){
            insertFirst(value); //case when linked list is empty
            return;
        }
        Node node=new Node(value);
        tail.next=node;
        tail=node;
        size++;
    }
    public void insert(int value, int index){
        if(index==0){
            insertFirst(value);
            return;
        }
        if(index==size){
            insertLast(value);
            return;
        }
        Node temp=head;
        for(int i=1;i<index-1;i++){
            temp=temp.next; //temp now points to the index position index
         }
        Node newElement=new Node(value, temp.next);
        temp.next=newElement;
        size++;
    }
    //deletions
    public int deleteFirst(){
        int value=head.value; //to store the value that is deleted
        head=head.next; //pointing the head to the next element, this will override previous value to which head was
        //pointing to before in the new function calls
        size--;
        return value;
    }
    public int deleteLast(){
        if(size<=1){
            return deleteFirst();
        }
        Node secondLast=get(size-2); //to retrieve the reference pointer at second last position
        int value=tail.value;
        tail=secondLast;
        tail.next=null; //old value stored as garbage value
        return value;
    }
    public int delete(int index){
        if(index==0){
            return deleteFirst();
        }
        if(index==size-1){
            return deleteLast();
        }
        Node prev=get(index-1); //prev node is the node just before the node at the specified index
        int value=prev.next.value;
        prev.next=prev.next.next; //prev now points to its next to next node
        return value;
    }
    public Node get(int index){
        Node node=head;
        for(int i=0;i<index;i++){
            node=node.next;
        }
        return node;
    }
    public void display(){ //to display items of the linkedList
        Node temp=head;
        while(temp!=null){
            System.out.print(temp.value + "->");
            temp=temp.next;
        }
        System.out.println("END");
    }


}
