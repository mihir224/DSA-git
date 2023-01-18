import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws Exception{
//        Stack stack =new Stack(5);
//        stack.push(2);
//        stack.push(1);
//        stack.push(5);
//        stack.push(4);
//        stack.push(7);
//
//        stack.display();
//        stack.pop();
//        System.out.println(stack.peek());
        System.out.println(3%5);
//        CircularQueue queue=new CircularQueue(5);
//        queue.add(2);
//        queue.add(3);
//        queue.add(4);
//        queue.add(5);
//        queue.add(6);
//        queue.display();
//        queue.remove();
//        queue.add(9);
//        queue.display(
    }
    //custom stack
    public class Stack{
        protected int[] data;
        private static final int DEFAULT_SIZE=10; //final coz we want it to be const, static coz we want all objects of stack class
        private int ptr=-1;
        public Stack(){
            this(DEFAULT_SIZE);
        }
        public Stack(int size){
            this.data=new int[size];
        }
        public void push(int item) throws Exception{
            if(isFull()){
                throw new Exception("Stack full");
            }
            ptr++;
            data[ptr]=item;
        }
        public int pop() throws Exception{
            if(isEmpty()){
                throw new Exception("Stack Empty");
            }
            int removed=data[ptr];
            ptr--;
            return removed;
        }
        public int peek() throws Exception{
            if(isEmpty()){
                throw new Exception("Stack Empty");
            }
            return data[ptr];
        }
        public boolean isEmpty(){
            return ptr==-1;
        }
        public boolean isFull(){
            return ptr==data.length-1;
        }
    }
    //dynamic stack using arrays
    public class DynamicStackUsingArrays extends Stack{
        public DynamicStackUsingArrays() {
            super();
        }

        public DynamicStackUsingArrays(int size) {
            super(size);
        }

        @Override
        public void push(int item) throws Exception {
            if(isFull()){
                int[] temp=new int[data.length*2];
                for(int i=0;i<data.length;i++){
                    temp[i]=data[i];
                }
                data=temp;
            }
            super.push(item);
        }
    }
    //dynamic stack using linked list
    class DynamicStackUsingLinkedList{
        Node head;
        int size;
        public DynamicStackUsingLinkedList(){
            this.head=null;
            this.size=0;
        }
        public void push(int item){
            Node node=new Node(item);
            node.next=head;
            head=node;
            size++;
        }
        public int peek(){
            if(head==null){
                return -1;
            }
            return head.val;
        }
        public int pop(){
            if(head==null){
                return -1;
            }
            int removed=head.val;
            head=head.next;
            size--;
            return removed;
        }
        public int size(){
            return size;
        }
    }
    class Node{
        int val;
        Node next;
        public Node(){}
        public Node(int val) {
            this.val = val;
        }
        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    //custom queue
    public class Queue {
        protected int[] data;
        private static final int DEFAULT_SIZE = 10; //final coz we want it to be const, static coz we want all objects of stack class
        private int end = 0;

        public Queue() {
            this(DEFAULT_SIZE);
        }

        public Queue(int size) {
            this.data = new int[size];
        }

        public void push(int item) throws Exception {
            if (isFull()) {
                throw new Exception("Queue full");
            }
            data[end] = item;
            end++;
        }

        public int pop() throws Exception {
            if (isEmpty()) {
                throw new Exception("Stack Empty");
            }
            int removed = data[0];
            for (int i = 1; i < data.length; i++) {
                data[i - 1] = data[i]; //shifting each left
            }
            end--;
            return removed;
        }

        public int peek() throws Exception {
            if (isEmpty()) {
                throw new Exception("Stack Empty");
            }
            return data[0];
        }

        public boolean isEmpty() {
            return end == 0;
        }

        public boolean isFull() {
            return end == data.length;
        }
    }
        //circular queue
        public class CircularQueue{
            private static final int DEFAULT_SIZE=10;
            protected int[] data;
            private int front=0;
            private int end=0;
            private int size=0;
            public CircularQueue() {
                this(DEFAULT_SIZE);
            }
            public CircularQueue(int size){
                this.data=new int[size];
            }
            public void push(int item){
                data[end]=item;
                end++;
                end=end%data.length;
                size++;
            }
            public int pop(){
                int removed=data[front];
                front++;
                front=front%data.length;
                size--;
                return removed;
            }
            public int head(){
                return data[front];
            }
            public boolean isEmpty(){
                return size==0;
            }
            public boolean isFull(){
                return size==data.length;
            }
            public void display(){
                int i=front;
                do{
                    System.out.println(data[i]);
                    i++;
                    i=i%data.length;
                }while(i!=end);
            }
        }
        //queue using linked list
        class QueueUsingLinkedlist{
            Node front;
            Node rear;
            int size;

            public QueueUsingLinkedlist( int size) {
                this.front = null;
                this.rear = null;
                this.size = size;
            }
            public void  push(int item){
               Node node =new Node(item);
                rear.next=node;
                rear=node;
            }
            public int dequeue(){
                front=front.next;
            }

        }
        //queues using stacks
        class QueueUsingStacks{
            Stack stack1=new Stack();
            Stack stack2=new Stack();
            public QueueUsingStacks(){}
            public void add(int item) throws Exception {
                stack1.push(item);
            }
            public int remove() throws Exception {
                while(!stack1.isEmpty()){
                    stack2.push(stack1.pop());
                }
                int remove=stack2.pop();
                while(!stack2.isEmpty()){
                    stack1.push(stack2.pop());
                }
                return remove;
            }
        }
        class StackUsingQueues{
            Queue q1=new Queue();
            Queue q2=new Queue();
            public void push(int item) throws Exception {
                while(!q1.isEmpty()) {
                    q2.push(q1.pop());
                }
                q1.push(item);
                while(!q2.isEmpty()) {
                    q1.push(q2.pop());
                }
            }
            public int remove() throws Exception {
                int rem=q1.pop();
                return rem;
            }
        }
        //delete middle
    public void deleteMid(DynamicStackUsingLinkedList stack){
        if(stack.size==0){
            return;
        }
        int mid=stack.size()/2+1;
        solve(stack, mid);
    }
    public void solve(DynamicStackUsingLinkedList stack, int mid) {
        if (mid == 1) {
            stack.pop();
            return;
        }
        int temp = stack.pop();
        solve(stack, mid - 1);
        stack.push(temp);
    }
    //two stacks in an array
    DynamicStackUsingLinkedList stack=new DynamicStackUsingLinkedList();
    DynamicStackUsingLinkedList stack2=new DynamicStackUsingLinkedList();
    int[] arr=new int[10];
    int top1=-1;
    int top2=arr.length;
    public void push1(int item){
        if(top2==top1+1){
            return;
        }
        top1++;
        arr[top1]=item;
    }
    public void push2(int item){
        if(top2==top1+1){
            return;
        }
        top2--;
        arr[top2]=item;
    }
    public int pop1(){
        int rem=arr[top1];
        top1--;
        return rem;
    }
    public int size1(){
        return top1+1;
    }

    public int size2(){
        return arr.length-top2;
    }

   //merge sort
    public int[] mergeSort(int[] arr)
    {
        if(arr.length==1){
            return arr;
        }
        int mid=arr.length/2;
        int[] left= Arrays.copyOfRange(arr, 0, mid);
        int[] right=Arrays.copyOfRange(arr, mid, arr.length);
        return merge(left,right);
    }
    public int[] merge(int[] left, int[] right){
        int[] mergedArray=new int[left.length+right.length];
        int i=0;
        int j=0;
        int k=0;
        while(i< left.length&&j<right.length){
            if(left[i]< right[j]){
                mergedArray[k]= left[i];
                i++;
            }
            else{
                mergedArray[k]= right[j];
                j++;
            }
            k++;
        }
        while(i<left.length){
            mergedArray[k]= left[i];
            i++;
            k++;
        }
        while(i<left.length){
            mergedArray[j]= right[j];
            j++;
            k++;
        }
        return mergedArray;
    }

    //quick sort
    public void QuickSort(int[] arr, int low, int hi){
        if(low>=hi){
            return;
        }
        int start=low;
        int end=hi;
        int mid=start+(end-start)/2;
        int pivot=arr[mid];
        while(start<=end){
            if(arr[start]<pivot){
                start++;
            }
            if(arr[end]<pivot){
                end--;
            }
            if(start<=end){
                swap(end, start);
                start++;
                end--;
            }
        }
        QuickSort(arr, low, end);
        QuickSort(arr, start, hi);

    }



}

