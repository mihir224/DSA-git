import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] arr={2,1,4,2};
//        int n=findPairs(arr,13);
    }
    abstract class depth{
        abstract int tp();
        abstract int cs();
    }
    static class ListNode{
        int val;
        ListNode next;
        public ListNode(){

        }
        public ListNode(int val){
            this.val=val;

        }

    }
    static class LinkedList{
        ListNode head;
        ListNode tail;
        int size;
        public LinkedList(){
            this.head=this.tail=new ListNode();
            this.size=0;
        }
        public void insert(ListNode node){
            tail.next=node;
            tail=tail.next;
            this.size++;
        }
    }

}