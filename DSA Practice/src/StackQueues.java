import java.util.*;

class StackQueues{

    //implement stack using arrays
    //https://www.codingninjas.com/studio/problems/stack-implementation-using-array_3210209?utm_source=striver&utm_medium=website&utm_campaign=a_zcoursetuf&leftPanelTab=1

    static class st {
        int ptr;
        int[] arr;
        st(int capacity) {
            this.arr=new int[capacity];
            this.ptr=-1;
        }
        public void st(int num) {
            if(isFull()==1){
                return;
            }
            ptr++;
            arr[ptr]=num;
        }
        public int pop() {
            if(isEmpty()==1){
                return -1;
            }
            int del=arr[ptr];
            ptr--;
            return del;
        }
        public int top() {
            if(isEmpty()==1){
                return -1;
            }
            return arr[ptr];
        }
        public int isEmpty() {
            return ptr==-1?1:0;
        }
        public int isFull() {
            return ptr==arr.length-1?1:0;
        }
    }

    //implement queue using arrays
    //https://www.codingninjas.com/studio/problems/implement-queue-using-arrays_8390825?utm_source=striver&utm_medium=website&utm_campaign=a_zcoursetuf&leftPanelTab=1

    class Q {
        int front, rear;
        int []arr;
        int size;

        Q() {
            front = 0;
            rear = 0;
            size=0;
            arr = new int[100001];
        }

        // Enqueue (add) element 'e' at the end of the queue.
        public void enqueue(int e) {
            if(size==arr.length){
                return;
            }
            arr[rear]=e;
            rear++;
            size++;
            rear=rear%arr.length;
        }

        // Dequeue (retrieve) the element from the front of the queue.
        public int dequeue() {
            if(size==0){
                return -1;
            }
            int del=arr[front];
            front++;
            front=front%arr.length;
            size--;
            return del;
        }
    }

    //implement stack using queue
    //https://leetcode.com/problems/implement-stack-using-queues
    //using 2 queues - O(2N - push operation)  time, O(2N) space

    class MyStack {
        Queue<Integer> q1;
        Queue<Integer> q2;
        public MyStack() {
            this.q1=new LinkedList<>();
            this.q2=new LinkedList<>();
        }
        public void push(int x) {
            while(!q1.isEmpty()){
                q2.offer(q1.poll());
            }
            q1.offer(x);
            while(!q2.isEmpty()){
                q1.offer(q2.poll());
            }
        }

        public int pop() {
            return q1.poll();
        }

        public int top() {
            return q1.peek();
        }

        public boolean empty() {
            return q1.isEmpty();
        }
    }

    //using 1 queue - O(N) time and space

    class MyStack1 {
        Queue<Integer> q;
        public MyStack1() {
            this.q=new LinkedList<>();
        }
        public void push(int x) {
            q.offer(x);
            int n=q.size();
            for(int i=0;i<q.size()-1;i++){
                q.offer(q.remove());
            }
        }
        public int pop() {
            return q.poll();
        }

        public int top() {
            return q.peek();
        }

        public boolean empty() {
            return q.isEmpty();
        }
    }

    //implementing queue using stacks
    //https://leetcode.com/problems/implement-queue-using-stacks/

    class MyQueue {
        Stack<Integer> st1;
        Stack<Integer> st2;
        public MyQueue() {
            this.st1=new Stack<>();
            this.st2=new Stack<>();
        }

        public void push(int x) {
            while(!st1.isEmpty()){
                st2.push(st1.pop());
            }
            st1.push(x);
            while(!st2.isEmpty()){
                st1.push(st2.pop());
            }
        }

        public int pop() {
            return st1.pop();
        }

        public int peek() {
            return st1.peek();
        }

        public boolean empty() {
            return st1.isEmpty();
        }
    }

    //approach using O(1) amortised time for push and pop operations
    class MyQueue1 {
        Stack<Integer> input;
        Stack<Integer> output;
        int peakElement; //this will be used to store the top of st1 incase pop was never called and peek() was called making peek() an O(1) operation as we don't have to insert everything from st1 to st2 if st2 is empty
        public MyQueue1() {
            this.input=new Stack<>();
            this.output=new Stack<>();
            this.peakElement=-1;
        }

        public void push(int x) {
            if(input.isEmpty()){
                peakElement=x;
            }
            input.push(x);
        }

        public int pop() {
            if(output.isEmpty()){
                while(!input.isEmpty()){
                    output.push(input.pop());
                }
            }
            return output.pop();
        }

        public int peek() {
            return output.isEmpty()?peakElement:output.peek();
        }

        public boolean empty() {
            return input.isEmpty()&&output.isEmpty();
        }
    }


    //valid parenthesis
    //https://leetcode.com/problems/valid-parentheses

    public boolean isValid(String s) {
        Stack<Character> st=new Stack<>();
        for(char ch:s.toCharArray()){
            if(ch=='('||ch=='{'||ch=='['){
                st.push(ch);
            }
            else{
                if(st.isEmpty()){
                    return false;
                }
                if((st.peek()=='('&&ch==')')||
                        (st.peek()=='{'&&ch=='}')||
                        (st.peek()=='['&&ch==']')){
                    st.pop();
                }
                else{
                    return false;
                }
            }
        }
        return st.isEmpty();
    }

    //next greater element
    //https://leetcode.com/problems/next-greater-element-i

    //brute - for each element, check on its right for nge
    //O(N2) time

    //optimal - using stacks
    //O(N+M) time and space
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] nge=new int[nums1.length];
        int n=nums2.length;
        HashMap<Integer,Integer> map=new HashMap<>();
        Stack<Integer> st=new Stack<>();
        for(int i=n-1;i>=0;i--){
            while(!st.isEmpty()&&st.peek()<nums2[i]){
                st.pop();
            }
            if(st.isEmpty()){
                map.put(nums2[i],-1);
            }
            else{
                map.put(nums2[i],st.peek());
            }
            st.push(nums2[i]);
        }
        for(int i=0;i<nums1.length;i++){
            nge[i]=map.get(nums1[i]);
        }
        return nge;
    }

    //sort a stack
    //https://www.codingninjas.com/studio/problems/sort-a-stack_985275?topList=striver-sde-sheet-problems&utm_source=striver&utm_medium=website&leftPanelTab=1
    //around O(N2) time
    public static void sortStack(Stack<Integer> stack) {
        if(stack.size()==1){
            return;
        }
        int top=stack.pop();
        sortStack(stack);
        insert(top,stack);
    }
    public static void insert(int top, Stack<Integer> st){
        if(st.isEmpty()||top>=st.peek()){
            st.push(top);
            return;
        }
        int currentTop=st.pop();
        insert(top,st);
        st.push(currentTop);
    }

    //prev smaller element
    //https://www.interviewbit.com/problems/nearest-smaller-element/

    public int[] prevSmaller(int[] nums) {
        int n=nums.length;
        int[] pse=new int[n];
        Stack<Integer> st=new Stack<>();
        for(int i=0;i<n;i++){
            while(!st.isEmpty()&&st.peek()>=nums[i]){
                st.pop();
            }
            if(st.isEmpty()){
                pse[i]=-1;
            }
            else{
                pse[i]=st.peek();
            }
            st.push(nums[i]);
        }
        return pse;
    }

    //largest rectangle in histogram
    //https://leetcode.com/problems/largest-rectangle-in-histogram

    //brute - for each element, find smaller on el before and after
    //O(N2) time

    //optimal - using nse and pse arrays
    //O(N) for main func, O(2N) for calc pse and nse, O(2N) space for storing nse and pse

    public int largestRectangleArea(int[] heights) {
        int[] pse=prevSmalle1r(heights);
        int[] nse=nextSmaller(heights);
        int ans=0;
        int width=1;
        for(int i=0;i<heights.length;i++){
            int area=heights[i]*(nse[i]-pse[i]-1);
            ans=Math.max(ans,area);
        }
        return ans;
    }
    public int[] prevSmalle1r(int[] nums) {
        int n=nums.length;
        int[] pse=new int[n];
        Stack<Integer> st=new Stack<>();
        for(int i=0;i<n;i++){
            while(!st.isEmpty()&&nums[st.peek()]>=nums[i]){
                st.pop();
            }
            if(st.isEmpty()){
                pse[i]=-1;
            }
            else{
                pse[i]=st.peek();
            }
            st.push(i);
        }
        return pse;
    }
    public int[] nextSmaller(int[] heights)
    {
        int n=heights.length;
        int[] nse=new int[n];
        Stack<Integer> st=new Stack<>();
        for(int i=n-1;i>=0;i--){
            while(!st.isEmpty()&&heights[st.peek()]>=heights[i]){
                st.pop();
            }
            if(st.isEmpty()){
                nse[i]=n;
            }
            else{
                nse[i]=st.peek();
            }
            st.push(i);
        }
        return nse;
    }

    //sliding window maximum
    //https://leetcode.com/problems/sliding-window-maximum
    //brute - for each element find the max in window of size k
    //O(N*K) time

    public int[] maxSlidingWindow1(int[] nums, int k) {
        int n=nums.length-k+1;
        int[] ans=new int[n];
        for(int i=0;i<n;i++){
            int max=Integer.MIN_VALUE;
            for(int j=i;j<=i+k-1;j++){
                max=Math.max(nums[j],max);
            }
            ans[i]=max;
        }
        return ans;
    }

    //optimal - using deque. basically we iterate over each window and in each iteration we make sure that deque holds
    // elements in descending order so that for each window we can obtain its max as the front of the deque. now the
    // intuition behind doing this is that for each element that we're at, every element that came before it which was
    // <= that element cannot be the max element in the window containing our element. so without a doubt they'll be
    // discarded and this will ensure that the max in the current window always stays at the top of the deque because
    // further elements which are smaller than the top will never be able to remove it for the current window. since we
    // always remove the out of bound element, we make sure that the deque at all times contains elements of the current
    // window only so that elements of that window are compared with each other only

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n=nums.length;
        int[] arr=new int[n-k+1];
        Deque<Integer> d=new ArrayDeque<>();
        for(int i=0;i<n;i++){
            if(d.peek()==i-k){
                d.poll();
            }
            while(!d.isEmpty()&&d.peekLast()<=nums[i]){
                d.pollLast();
            }
            d.offer(i);
            if(i>=i-k){
                arr[i-k]=d.peek();
            }
        }
        return arr;
    }

    //min stack

    // just store min yet along with whatever trying to push in stack. at any point of time, min would be in the top's min
    // O(1) time, O(2N) space - since storing pairs

    class MinStack {
        Stack<Pair> st;
        class Pair{
            int first;
            int second;
            public Pair(int first,int second){
                this.first=first;
                this.second=second;
            }
        }
        public MinStack() {
            this.st=new Stack<>();
        }

        public void push(int val) {
            if(st.isEmpty()||val<st.peek().second){
                st.push(new Pair(val,val));
            }
            else {
                st.push(new Pair(val,st.peek().second));
            }
        }

        public void pop() {
            st.pop();
        }

        public int top() {
            return st.peek().first;
        }

        public int getMin() {
            return st.peek().second;
        }
    }

    //space optimized
    //O(1) time, O(N) space
    //the whole reason for using modified val for new min is because through this modified val we'll be able to obtain
    // the prev min in case the current min has been removed from the stack

    class MinStack1 {
        Stack<Long> st;
        Long min; //to store min till now

        public MinStack1() {
            this.st=new Stack<>();
            this.min=Long.MAX_VALUE;
        }

        public void push(int val) {
            Long v=Long.valueOf(val);
            if(v<min){
                long modifiedVal=(2*v)-min;
                min=v;
                st.push(modifiedVal);
            }else{
                st.push(v);
            }
        }

        public void pop() {
            if(st.peek()<min){ //ie we have to change min to its prev val
                min=(2*min)-st.peek();
            }
            st.pop();
        }

        public int top() {
            if(st.peek()<min){
                return min.intValue();
            }
            return st.peek().intValue();
        }

        public int getMin() {
            return min.intValue();
        }
    }


    //optimised - whenever we find a min val, we store its modified version in the stack instead of original

    //rotten oranges
    //https://leetcode.com/problems/rotting-oranges
    class Triad{
        int row;
        int col;
        int time;
        public Triad(int row,int col,int time){
            this.row=row;
            this.col=col;
            this.time=time;
        }
    }
    public int orangesRotting(int[][] grid) {
        int countFresh=0;
        int count=0;
        Queue<Triad> q=new LinkedList<>();
        boolean[][] vis=new boolean[grid.length][grid[0].length];
        int ans=0;
        int[] delRow={-1,0,1,0};
        int[] delCol={0,1,0,-1};
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j]==1){
                    countFresh++;
                }
                if(grid[i][j]==2){
                    q.offer(new Triad(i,j,0));
                }
            }
        }
        while(!q.isEmpty()){
            Triad t=q.poll();
            int row=t.row;
            int col=t.col;
            int time=t.time;
            ans=Math.max(ans,time);
            for(int i=0;i<4;i++){
                int nrow=row+delRow[i];
                int ncol=col+delCol[i];
                if(nrow<grid.length&&nrow>=0
                        &&ncol<grid[0].length&&ncol>=0
                        &&grid[nrow][ncol]==1
                        &&!vis[nrow][ncol]){
                    q.offer(new Triad(nrow,ncol,time+1));
                    vis[nrow][ncol]=true;
                    count++;
                }
            }
        }
        if(count!=countFresh){
            return -1;
        }
        return ans;
    }

    //stock span
    //https://leetcode.com/problems/online-stock-span

    //for each element, we check pge. till the pge of an element, every day would be <=current element making the days consecutive.
    // this way we can easily calculate span for each price
    //O(N)time and O(1) space

    class StockSpanner {
        class Pair{
            int first;
            int second;
            public Pair(int first,int second){
                this.first=first;
                this.second=second;
            }
        }
        Stack<Pair> st;
        int index; //to keep track of current index
        public StockSpanner() {
            this.st=new Stack<>();
            this.index=-1;
        }
        public int next(int price) {
            while(!st.isEmpty()&&st.peek().first<=price){
                st.pop();
            }
            index++;
            if(!st.isEmpty()){ //pge exists, return index-pge
                int pge=st.peek().second;
                st.push(new Pair(price,index));
                return index-pge;
            }
            //pge doesn't exist, return entire length till this index
            st.push(new Pair(price,index));
            return index+1;
        }
    }

    //max of min of every window size
    //https://practice.geeksforgeeks.org/problems/maximum-of-minimum-for-every-window-size3453/1

    //brute - for each element iterating left and right to find its pse and nse
    //O(N2) time

    //optimal
    //O(4N) time, O(3N) space

    class Solution
    {
        //Function to find maximum of minimums of every window size.
         int[] maxOfMin(int[] arr, int n)
        {
            int[] nse=nextSmaller(arr,n);
            int[] pse=prevSmaller(arr,n);
            int[] ans=new int[n];
            for(int i=0;i<n;i++){
                int len=nse[i]-pse[i]-1;
                ans[len-1]=Math.max(ans[len-1],arr[i]);
            }
            for(int i=n-2;i>=0;i--){
                if(ans[i]<ans[i+1]){
                    ans[i]=ans[i+1];
                }
            }
            return ans;
        }
         int[] nextSmaller(int[] arr,int n){
            Stack<Integer> st=new Stack<>();
            int[] nse=new int[n];
            for(int i=n-1;i>=0;i--){
                while(!st.isEmpty()&&arr[st.peek()]>=arr[i]){
                    st.pop();
                }
                if(st.isEmpty()){
                    nse[i]=n;
                }
                else{
                    nse[i]=st.peek();
                }
                st.push(i);
            }
            return nse;
        }
         int[] prevSmaller(int[] arr, int n){
            Stack<Integer> st=new Stack<>();
            int[] pse=new int[n];
            for(int i=0;i<n;i++){
                while(!st.isEmpty()&&arr[st.peek()]>=arr[i]){
                    st.pop();
                }
                if(st.isEmpty()){
                    pse[i]=-1;
                }
                else{
                    pse[i]=st.peek();
                }
                st.push(i);
            }
            return pse;
        }

    }

    //celebrity problem
    //https://practice.geeksforgeeks.org/problems/the-celebrity-problem/1

    //brute - for each element, check if their entire row is 0 ie they don't know anyone. In case we find a potentialCeleb,
    // we check if the celeb is known by anyone or not. This will take O(N2) time

    //optimal - using stack
    //O(N) time and space
    int celebrity(int M[][], int n)
    {
        Stack<Integer> st=new Stack<>();
        for(int i=0;i<n;i++){
            st.push(i);
        }
        while(st.size()>1){
            int i=st.pop();
            int j=st.pop();
            if(M[i][j]==1){
                st.push(j);
            }
            else{
                st.push(i);
            }
        }
        int potentialCeleb=st.pop();
        for(int i=0;i<n;i++){
            if(i!=potentialCeleb){
                if(M[i][potentialCeleb]==0||M[potentialCeleb][i]==1){
                    return -1;
                }
            }
        }
        return potentialCeleb;
    }

}