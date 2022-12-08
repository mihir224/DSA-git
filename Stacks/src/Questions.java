import java.util.*;
import java.util.Queue;
import java.util.Stack;

public class Questions {
    public static void main(String[] args) {
      int[] nums1={4,1,2};
      int[] nums2={4,3,1,2};
        System.out.println(Arrays.toString(nextGreaterElement(nums1,nums2)));
    }

    //valid parenthesis
    //https://leetcode.com/problems/valid-parentheses/

    public boolean isValid(String s) {
        Stack<Character> st = new Stack<>();
        char[] str = s.toCharArray();
        for (int i = 0; i < str.length; i++) {
            if (str[i] == '(' || str[i] == '{' || str[i] == '[') { //pushing opening brackets to the stack
                st.push(str[i]);
            } else {
                if (st.isEmpty()) { //case when the string starts with an closed bracket
                    return false;
                }
                char ch = st.peek();
                if (str[i] == ')' && ch == '(' || str[i] == '}' && ch == '{' || str[i] == ']' && ch == '[') {
                    //checking if there are matching brackets
                    // in the stack for closed ones
                    st.pop();
                } else {
                    return false;
                }
            }
        }
        return st.isEmpty();
    }

    //simplify path
    //https://leetcode.com/problems/simplify-path/
    public String simplifyPath(String path) {
        Stack<String> st = new Stack<>();
        StringBuilder sb = new StringBuilder();
        String[] str = path.split("/");
        for (String s : str) {
            if (!st.isEmpty() && s.equals("..")) {
                st.pop();
            } else if (!s.equals("") && !s.equals(".") && !s.equals("..")) { //case when the input has '.' , '..' , or
                // '//' (when there is no file between two slashes) in the path
                st.push(s);
            }

        }
        if (st.isEmpty()) {
            return "/";
        }
        while (!st.isEmpty()) { //while the stack is not empty, we insert the top of the stack at the 0th index of the
            // result string and then insert / each time at the 0th index
            sb.insert(0, st.pop()).insert(0, "/");
        }
        return sb.toString();
    }

    //implement stack using queues
    //https://leetcode.com/problems/implement-stack-using-queues/

    class MyStack {
        Queue<Integer> q1=new LinkedList<Integer>();
        Queue<Integer> q2=new LinkedList<Integer>();

        public MyStack() {

        }

        public void push(int x) {
            while(!q1.isEmpty()){
                q2.add(q1.remove()); //emptying q1 into q2
            }
            q1.add(x); //pushing new data into empty q1
            while(!q2.isEmpty()){
                q1.add(q2.remove());
            }
        }

        public int pop() {
            return q1.remove();
        }

        public int top() {
            return q1.peek();
        }

        public boolean empty() {
            return q1.isEmpty();
        }
    }

    //implement queue using stacks
    class MyQueue {
        Stack<Integer> st1=new Stack<>();
        Stack<Integer> st2=new Stack<>();

        public MyQueue() {
        }

        public void push(int x) {
            st1.push(x);
        }

        public int pop() {
            while(!st1.isEmpty()){
                st2.push(st1.pop());
            }
            int ans=st2.pop();
            while(!st2.isEmpty()){ //pushing st2 elements back to st1
                st1.push(st2.pop());
            }
            return ans;
        }

        public int peek() {
            while(!st1.isEmpty()){
                st2.push(st1.pop());
            }
            int ans=st2.peek();
            while(!st2.isEmpty()){ //pushing st2 elements back to st1
                st1.push(st2.pop());
            }
            return ans;
        }

        public boolean empty() {
            return st1.isEmpty();
        }
    }

    //Decode String
    //https://leetcode.com/problems/decode-string/

    public static String decodeString(String s) {
        Stack<Integer> numStack=new Stack<>();
        Stack<StringBuilder> strStack=new Stack<>();
        StringBuilder sb = new StringBuilder();
        int num = 0;
        for(char ch:s.toCharArray()){
            if (Character.isDigit(ch)){
                num=num*10+ch-'0'; //converting string to int if it is a digit
            }
            else if(ch=='['){
                strStack.push(sb); //adding previous string for eg aaa to the stack
                sb=new StringBuilder(); //re initialising the string after storing its previous value in stack
                numStack.push(num); //pushing the new value of num to the numStack
                num=0; //re initialising num to 0
            }

            else if(ch==']'){
                StringBuilder temp=sb;
                sb=strStack.pop(); //retrieving the string value from stack when ']' is encountered
                int count=numStack.pop(); //retrieving the count value from numStack
                while(count>0){
                    sb.append(temp); //appending all
                    count--;
                }
            }
            else{
                sb.append(ch); //case when the character is an alphabet
            }
        }
        return sb.toString();
    }
    public static void deleteMid(Stack<Integer> stack, int size){
        if(size==0){ //stack is empty
            return;
        }
        int k=(size/2)+1; //middle element position
        solve(stack,k);
    }
    public static void solve(Stack<Integer> stack, int k){
        if(k==1){     //when the middle element has become top
            stack.pop();
            return;
        }
        int temp=stack.peek();
        stack.pop();
        solve(stack,k-1);
        stack.push(temp); //pushing the previous top elements back into the stack
    }

    //implement two stacks in an array
    //https://practice.geeksforgeeks.org/problems/implement-two-stacks-in-an-array/1
    class TwoStack
    {

        int size;
        int top1,top2;
        int arr[] = new int[100];

        TwoStack()
        {
            size = 100;
            top1 = -1;
            top2 = size;
        }
    }
    class Stacks
    {

        //Function to push an integer into the stack1.
        void push1(int x, TwoStack sq)
        {
            if(sq.top2==sq.top1+1){
                return;
            }
            else{
                sq.top1++;
                sq.arr[sq.top1]=x;
            }}

        //Function to push an integer into the stack2.
        void push2(int x, TwoStack sq)
        {
            if(sq.top2==sq.top1+1){
                return;
            }else{
                sq.top2--;
                sq.arr[sq.top2]=x;
            }}

        //Function to remove an element from top of the stack1.
        int pop1(TwoStack sq)
        {
            if(sq.top1<0){
                return -1;
            }
            else{
                int val=sq.arr[sq.top1];
                sq.top1--;
                return val;
            }

        }

        //Function to remove an element from top of the stack2.
        int pop2(TwoStack sq)
        {
            if(sq.top2>=sq.arr.length){
                return -1;
            }
            else{
                int val=sq.arr[sq.top2];
                sq.top2++;
                return val;
            }
        }
    }

    //reverse a string using stack
    //https://practice.geeksforgeeks.org/problems/reverse-a-string-using-stack/1

    public String reverse(String s){
        Stack<Character> stack=new Stack<>();
        StringBuilder sb=new StringBuilder();
        for(char ch: s.toCharArray()){
            stack.push(ch);
        }
        while(!stack.isEmpty()){
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    //reverse string leetcode
    //https://leetcode.com/problems/reverse-string/
    //9920102054 MIHIR SAINI E2
    //Q1
    public void reverseString(char[] s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length; i++) {
            stack.push(s[i]);
        }
        for (int i = 0; i < s.length; i++) {
            if (!stack.isEmpty()) {
                s[i] = stack.pop();
            } else {
                return;
            }
        }
    }

    //infix to postfix
    //https://www.geeksforgeeks.org/stack-set-2-infix-to-postfix/
    //9920102054 MIHIR SAINI E2
    //Q2
    public String infixToPostfix(String str){
        String ans="";
        Deque<Character> stack=new ArrayDeque<>();
        for(int i=0;i<str.length();i++){
            char ch=str.charAt(i);
            if(ch=='('){
                stack.push(ch);
            }
            else if(Character.isLetterOrDigit(ch)){
                ans+=ch;
            }
            else if(ch==')'){
                while(!stack.isEmpty()&&stack.peek()!='('){ //popping from the stack till '(' is encountered
                    ans+=stack.pop();
                }
                stack.pop();
            }
            else{ //operator encountered
                while(!stack.isEmpty()&&precedence(stack.peek())>=precedence(ch)){
                    ans+=stack.pop();
                }
                stack.push(ch);
            }
        }
        while(stack.isEmpty()){
            if(stack.peek()=='(') {//edge case for when a ( is extra in the expression
                return "invalid";
            }
            ans+=stack.pop();
        }
        return ans;

    }
    public int precedence(char ch){
        switch(ch){
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

    //postfix evaluation
    //https://practice.geeksforgeeks.org/problems/evaluation-of-postfix-expression1735/1

    public static int evaluatePostFix(String s){
        Stack<Integer> stack=new Stack<>();
        char[] str=s.toCharArray();
        for(int i=0;i<str.length;i++){
            if(Character.isDigit(str[i])){
                stack.push(str[i]-'0');
            }
            else {
                int num2=stack.pop();
                int num1=stack.pop();
                switch (str[i]){
                    case '+':
                        stack.push(num1+num2);
                        break;
                    case '-':
                        stack.push(num1-num2);
                        break;
                    case '*':
                        stack.push(num1*num2);
                        break;
                    case '/':
                        stack.push(num1/num2);
                        break;
                }
            }
        }

        return stack.pop();
    }


    //postfix leetcode
    //https://leetcode.com/problems/evaluate-reverse-polish-notation/

    public static int evalRPN(String[] str){
        Stack<Integer> stack=new Stack<>();
        if(str.length==0){
            return 0;
        }
        for(int i=0;i<str.length;i++){
            String s=str[i];
            char ch=s.charAt(0);
            if(ch=='-'&&s.length()>1){
                ch=s.charAt(1);
            }
            if(Character.isDigit(ch)){
                stack.push(Integer.parseInt(s));
            }
            else if(s.length()==1) {
                int num2=stack.pop();
                int num1=stack.pop();
                switch (ch){
                    case '+':
                        stack.push(num1+num2);
                        break;
                    case '-':
                        stack.push(num1-num2);
                        break;
                    case '*':
                        stack.push(num1*num2);
                        break;
                    case '/':
                        stack.push(num1/num2);
                        break;
                }
            }

        }
        return stack.pop();
    }

    //Length of longest valid substring
    //https://practice.geeksforgeeks.org/problems/valid-substring0624/0

    public int findMaxLen(String s) {
        Stack<Integer> stack=new Stack<>();
        stack.push(-1);
        char[] str=s.toCharArray();
        int res=0;
        int current=0;
       for(int i=0;i<str.length;i++){
           if(str[i]=='('){
               stack.push(i);
           }
           else{
                   stack.pop();
               if(!stack.isEmpty()){
               current=i-stack.peek();
               if(current>res){
                   res=current;
               }
               }
               else{
                   stack.push(i);
               }

           }
       }
       return res;
    }

    //Remove invalid parenthesis
    //https://leetcode.com/problems/remove-invalid-parentheses/

    public List<String> removeInvalidParentheses(String s) {
        int minRemoval=returnMin(s);
        List<String> list=new ArrayList<>();
        solution(s,minRemoval,list);
        return list;
    }

    public void solution(String s, int minRemovalAllowed,List<String> list){
        if(minRemovalAllowed==0){
            if(isValid(s)){
                if(!list.contains(s)){ //to avoid repetition of a string in the list
                    list.add(s);
                }
            }
            return;
        }
        for(int i=0;i<s.length();i++){
            String left=s.substring(0,i);
            String right=s.substring(i+1); //the character between left and right will be removed
            solution(s,minRemovalAllowed-1,list); //reducing min removals allowed
        }
    }
    public int returnMin(String s){
        Stack<Character> stack=new Stack<>(); //this stack will contain all the invalid parenthesis
        Queue<Integer> q=new LinkedList<>();
        for(int i=0;i<s.length();i++){
            char ch=s.charAt(i);
            if(ch=='('){
                stack.push(ch);
            }
            else if(ch==')'){
                if(!stack.isEmpty()){
                    if(stack.peek()=='('){
                        stack.pop();
                    }
                    else{
                        stack.push(ch);
                    }
                }
                else{
                    stack.push(ch);
                }
            }
        }
        return stack.size(); //returning the min number of removals to make string valid
    }

    //implement n-stacks in an array
    //https://www.codingninjas.com/codestudio/problems/n-stacks-in-an-array_1164271?leftPanelTab=0

    public class NStack {
        int[] arr;
        int[] top;
        int[] next;
        int N;
        int freeSpot;

        public NStack(int N, int S) {
            this.N=N;
            this.arr=new int[S];
            this.top=new int[this.N]; //top stores the index of the top of each stack
            this.next=new int[S];
            this.freeSpot=0; //initially, the spot at 0th index is free
            for(int i=0;i<this.N;i++){
                top[i]=-1; //initialising heads of all the stacks to be -1 as all stacks are empty initially
            }
            for(int i=0;i<S-1;i++){
                next[i]=i+1;
            }
            next[S-1]=-1; //last element of next[]
        }

        public boolean push(int x, int m) {
            if(freeSpot==-1){ //stack overflow, no free spots left
                return false;
            }
            //finding index
            int index=freeSpot;
            //update freeSpot
            freeSpot=next[index];
            //insert in array
            arr[index]=x;
            //update next
            next[index]=top[m-1]; //returns the top of (m-1)th element
            //update top
            top[m-1]=index;
            return true;

        }

        public int pop(int m) {
            // Write your code here.
            if(top[m-1]==-1){ //underflow, if the given stack is empty
                return -1;
            }
            int index=top[m-1];
            top[m-1]=next[index];
            next[index]=freeSpot;
            freeSpot=index;
            return arr[index];
        }
    }

    //Next greater element
    //https://leetcode.com/problems/next-greater-element-i/
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        HashMap<Integer,Integer> map=new HashMap<>();
        int[] ans = new int[nums1.length];
        int[] nge=nge(nums2);
        for(int i=0;i<nums2.length;i++){
            map.put(nums2[i],nge[i]);
        }
        for(int i=0;i<nums1.length;i++){
            ans[i]=map.get(nums1[i]);
        }
        return ans;
    }

    public static int[] nge(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int[] nge = new int[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            while(!stack.isEmpty()&&nums[i]>=stack.peek()){
                stack.pop();
            }
            if(!stack.isEmpty()){
                nge[i]=stack.peek();
            }
            else{
                nge[i]=-1;
            }
            stack.push(nums[i]);
        }
        return nge;
    }

    //Next greater element 2 (circular)
    //https://leetcode.com/problems/next-greater-element-ii/
    public int[] nextGreaterElements(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int n =nums.length;
        int[] nge = new int[n];
        for (int i = 2*n - 1; i >= 0; i--) {
            while(!stack.isEmpty()&&nums[i%n]>=stack.peek()){
                stack.pop();
            }
            if(i<n){
            if(!stack.isEmpty()){
                nge[i%n]=stack.peek();
            }
            else{
                nge[i%n]=-1;
            }
            }
            stack.push(nums[i%n]);
        }
        return nge;
    }

    //Celebrity Problem
    //https://practice.geeksforgeeks.org/problems/the-celebrity-problem/1
    int celebrity(int M[][], int n)
    {
        Stack<Integer> stack=new Stack<>();
        for(int i=0;i<M.length;i++){
            stack.push(i);
        }
        while(stack.size()>=2){
            int j=stack.pop();
            int i=stack.pop();
            if(M[i][j]==1){ //i knows j, thus i not a celebrity
                stack.push(j);
            }else{
                stack.push(i); //i does not know j, j is not a celebrity
            }
        }
        int potentialCeleb=stack.pop(); //last element remaining in stack
        for(int i=0;i<M.length;i++){
            if(i!=potentialCeleb){
                if(M[i][potentialCeleb]==0||M[potentialCeleb][i]==1){ //if i doesn't know potential celeb or potential
                    // celeb knows someone
                    return -1;
                }
            }
        }
        return potentialCeleb;
    }

    //largest rectangle area in histogram
    //https://leetcode.com/problems/largest-rectangle-in-histogram/
    public int largestRectangleArea(int[] heights) {
        int max=0;
        int ps[]=pse(heights);
        int ns[]=nse(heights);
        for(int i=0;i<heights.length;i++){
            int current=(ns[i]-ps[i]-1)*heights[i];
            max=Math.max(current,max);
        }
        return max;
    }
    public int[] pse(int[] nums){
        Stack<Integer> stack = new Stack<>();
        int[] pse = new int[nums.length];
        for (int i = 0; i <nums.length; i++) {
            while(!stack.isEmpty()&&nums[i]<=nums[stack.peek()]){
                stack.pop();
            }
            if(!stack.isEmpty()){
                pse[i]=stack.peek();
            }
            else{
                pse[i]=-1;
            }
            stack.push(i);
        }
        return pse;
    }
    public int[] nse(int[] nums){
        Stack<Integer> stack = new Stack<>();
        int[] nse = new int[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] >= nums[stack.peek()]) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                nse[i] = stack.peek();
            } else {
                nse[i] = nums.length; //as the area cannot be negative, when there is no next smaller element, we return index as n
            }
            stack.push(i);
        }
        return nse;
    }

    //maximum rectangle/sub-matrix with all 1s
    //https://practice.geeksforgeeks.org/problems/max-rectangle/1
    public int maximalRectangle(int M[][], int n, int m) {
        int[] currentRow=M[0]; //first row
        int maxAns=largestRectangleArea(currentRow);
        for(int i=1;i<M.length;i++) { //starting from second row
            for (int j = 0; j < M[0].length; j++) {
                if (M[i][j] == 1) {
                    currentRow[j]++;
                } else {
                    currentRow[j] = 0;
                }
            }
            int currentAns = largestRectangleArea(currentRow);
            maxAns = Math.max(currentAns, maxAns);
        }
        return maxAns;
    }

    //sliding window maximum
    //https://leetcode.com/problems/sliding-window-maximum/

    //brute force
    public int[] maxSlidingWindow1(int[] nums, int k) {
        int[] max=new int[nums.length-k+1];
        for(int i=0;i<=nums.length-k;i++){
            max[i]=nums[i];
            for(int j=1;j<k;j++){
                if(nums[i+j]>max[i]){
                    max[i]=nums[i+j];
                }
            }
        }
        return max;
    }

    //
    public int[] maxSlidingWindow(int[] nums, int k) {
        //for every element, the previous smaller elements are useless so we remove them from the queue
//        Deque<Integer> q=new ArrayDeque<>();
//        int[] max=new int[nums.length-k+1];
//        int mx=0;
//        for(int i=0;i<k;i++){ //managing first window
//            while(!q.isEmpty()&&nums[i]>=nums[q.peekLast()]){
//                q.removeLast();
//            }
//            q.addLast(i);
//        }
//        for(int i=0;i<nums.length;i++){
//            max[mx]=nums[q.peek()];
//            mx++;
//            while(!q.isEmpty()&&q.peek()<=i-k){ //removing elements which are out of window
//                q.removeFirst();
//            }
//            while(!q.isEmpty()&&nums[i]>=nums[q.peekLast()]){
//                q.removeLast();
//            }
//            q.addLast(i);
//        }
//
//        return max;

        Stack<Integer> stack = new Stack<>();
        int[] nge = new int[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            while(!stack.isEmpty()&&nums[i]>=stack.peek()){
                stack.pop();
            }
            if(!stack.isEmpty()){
                nge[i]=stack.peek();
            }
            else{
                nge[i]=-1;
            }
            stack.push(nums[i]);
        }
        return nge;

    }

    //interleave the first of queue with second half
    public void interLeaveQueue(Queue<Integer> q){
        Stack<Integer> stack=new Stack<>();
        if(q.size()%2!=0){ //case when queue doesn't have even no. of elements
            return;
        }
        int half=q.size()/2;
        for(int i=0; i<half;i++){ //pushing 1st half of the queue to the stack
            stack.push(q.poll());
        }
        while(!stack.isEmpty()){ //adding stack elements to the queue
            q.add(stack.pop());
        }
        for(int i=0;i<half;i++){
            q.add(q.poll()); //deque and enqueue simultaneously
        }
        for(int i=0;i<half;i++){
            stack.push(q.poll()); //again pushing 1st half of queue to the stack
        }
        while(!stack.isEmpty()){ //interleave stack and queue elements
            q.add(stack.pop());
            q.add(q.poll());
        }
    }

}

