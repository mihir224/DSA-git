import java.util.*;
import java.util.LinkedList;

public class StackQueues {

    //implement stack using arrays

    public class Stacks {
        private int[] data;
        int ptr = -1;
        private static final int DEFAULT_SIZE = 10;

        public Stacks() {
            this(DEFAULT_SIZE);
        }

        public Stacks(int size) {
            data = new int[size];
        }

        void push(int num) throws Exception {
            if (isFull() == 1) {
                throw new Exception("Stack full");
            }
            ptr++;
            data[ptr] = num;
        }

        int pop() throws Exception {
            if (isEmpty() == 1) {
                throw new Exception("Stack empty");
            }
            int removed = data[ptr];
            ptr--;
            return removed;

        }


        int top() throws Exception {
            if (isEmpty() == 1) {
                throw new Exception("Stack empty");
            }
            return data[ptr];
        }

        int isEmpty() {
            return ptr == -1 ? 1 : 0;
        }

        int isFull() {
            return ptr == data.length - 1 ? 1 : 0;
        }
    }

    //implement stack using queues
    //https://leetcode.com/problems/implement-stack-using-queues/
    //using 2 queues: O(N)time, O(2N)space

    //using single queue: O(N)time, O(N)space
    public class myStack {
        Queue<Integer> q = new LinkedList<>();

        public void push(int x) {
            q.add(x);
            for (int i = 0; i < q.size() - 1; i++) {
                push(q.remove());
            }
        }

        public int pop() {
            return q.remove();
        }

        public int top() {
            return q.peek();
        }

        public boolean empty() {
            return q.isEmpty();
        }
    }

    //implement queue using arrays
    //https://practice.geeksforgeeks.org/problems/implement-queue-using-array/1
    class Q {
        int front, rear, size;
        int arr[] = new int[100005];

        Q() {
            front = 0;
            size = 0;
            rear = 0;
        }

        void push(int x) {
            if (size < arr.length) {
                arr[rear % arr.length] = x;
                size++;
                rear++;
            }
        }

        int pop() {
            if (front != rear) {
                int removed = arr[front];
                front++;
                size--;
                return removed;
            }
            return -1;
        }
    }

    //implement queue using stacks
    //https://leetcode.com/problems/implement-queue-using-stacks/

    //brute - O(N) - pop and peek, O(1) push, O(2N) space
    //for push(), simply push whatever is passed to stack 1. For pop(), empty stack 1 to 2 then remove head of st2, store it
    //then empty s2 to s1 and return head. Same operation for peek()

    //better - O(1) amortised pop and peek, O(1) push, O(2N) space
    //here O(1) amortised means that in almost all cases complexity is O(1) because only in some instances it becomes O(N)
    //we use two stacks - input and output. While popping, we check if st2 is empty. If it is, we simply empty input to
    //output. Otherwise, we simply return output's head. Same goes for peek operations. Push operation will be same

    public class MyQueue {
        Stack<Integer> input = new Stack<>();
        Stack<Integer> output = new Stack<>();
        private int peakElement = -1;

        public void push(int x) {
            if (input.isEmpty())
                peakElement = x; //to keep track of peek element if no pop operation is ever called.
            // This will make sure that we don't empty input to output in the peek() function everytime output is empty
            // making the peek function O(1)
            input.push(x);
        }

        public int pop() {
            if (output.isEmpty()) {
                while (!input.isEmpty()) {
                    output.push(input.pop());
                }
            }
            return output.pop();
        }

        public int peek() {
            return output.isEmpty() ? peakElement : output.peek();
        }

        public boolean empty() {
            return input.isEmpty() && output.isEmpty();
        }
    }

    //valid parenthesis
    //https://leetcode.com/problems/valid-parentheses/

    public boolean isValid(String s) {
        char[] ch = s.toCharArray();
        Stack<Character> st = new Stack<>();
        for (char c : ch) {
            if (c == '{' || c == '(' || c == '[') {
                st.push(c);
            } else { //closing bracket
                if (st.isEmpty()) { //no opening brackets pushed yet, ie the string starts with closing bracket
                    return false;
                }
                char sp = st.peek();
                if (sp == '{' && c == '}' || sp == '(' && c == ')' || sp == '[' && c == ']') {
                    st.pop();
                } else {
                    return false;
                }
            }
        }
        return st.isEmpty();
    }

    //next greater element-ii (O(n)time,space)
    //https://leetcode.com/problems/next-greater-element-ii/
    public int[] nextGreaterElements(int[] nums) {
        Stack<Integer> st = new Stack<>();
        int[] nge = new int[nums.length];
        int n = nums.length;
        for (int i = 2 * n - 1; i >= 0; i--) {
            while (!st.isEmpty() && nums[i % n] >= st.peek()) {
                st.pop();
            }
            if (i < n) {
                if (!st.isEmpty()) {
                    nge[i] = st.peek();
                } else {
                    nge[i] = -1;
                }
            }
            st.push(nums[i % n]);
        }
        return nge;
    }

    //sort a stack
    //https://www.codingninjas.com/codestudio/problems/sort-a-stack_985275
    //(very good question and really important to dry run) - make sure to write approach in notes along with dry run
    //We implement two functions: sort() and insert(). sort() will return the sorted stack. It is a recursive function in which
    //we first remove the top of the stack and then recursively do this till only one element is remaining in the stack
    //When that happens we return the stack. This is out bc for sort(). At the end of each call we call the insert() function
    // and pass in the current stack along with the current top so as to insert the current top accordingly in the sorted stack
    //Then we simply return whatever insert() returns

    public static void sortStack(Stack<Integer> stack) {
        sortedStack(stack);
    }

    public static Stack<Integer> sortedStack(Stack<Integer> st) {
        if (st.size() == 1) {
            return st;
        }
        int topEl = st.pop();
        sortedStack(st);
        return insert(st, topEl);
    }

    public static Stack<Integer> insert(Stack<Integer> st, int topEl) {
        if (st.size() == 0 || st.peek() <= topEl) {
            st.push(topEl); //current topEl already greater than top of stack
            return st;
        }
        int top = st.pop();
        insert(st, topEl);
        st.push(top);
        return st;
    }

    //nearest smallest element (index should be smaller than i)
    //https://www.interviewbit.com/problems/nearest-smaller-element/
    public int[] prevSmaller(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int[] pse = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[i] <= stack.peek()) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                pse[i] = stack.peek();
            } else {
                pse[i] = -1;
            }
            stack.push(nums[i]);
        }

        return pse;
    }

    //celebrity problem
    //https://practice.geeksforgeeks.org/problems/the-celebrity-problem/1
    //O(N) solution
    int celebrity(int M[][], int n) {
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < n; i++) {
            st.push(i);
        }
        while (st.size() >= 2) {
            int b = st.pop();
            int a = st.pop();
            if (M[a][b] == 1) {
                st.push(b); //a knows b
            } else {
                st.push(a); //a doesn't know b
            }
        }
        int potentialCeleb = st.pop();
        for (int i = 0; i < n; i++) {
            if (i != potentialCeleb) {
                if (M[i][potentialCeleb] == 0 || M[potentialCeleb][i] != 0) {
                    return -1;
                }
            }
        }
        return potentialCeleb;
    }

    //largest rectangle in a histogram
    //
    //brute force - O(N2)time, O(1)space - find left and right extremes for each index and simultaneously calculate the area using
    //(arr[right]-arr[left]-1) * arr[i]
    public int largestRectangleArea(int[] heights) {
        int area = 0;
        for (int i = 0; i < heights.length; i++) {
            int left = i;
            int right = i;
            while (left >= 0 && heights[left] >= heights[i]) {
                left--;
            }
            while (right <= heights.length - 1 && heights[right] >= heights[i]) {
                right++;
            }
            area = Math.max(area, (right - left - 1) * heights[i]);
            if (heights.length == 1) {
                area = heights[i];
            }
        }
        return area;
    }

    //optimal - O(N)time, O(3N)space
    //we simply optimize the brute force by using the next smaller and previous smaller functions to store the nse and pse for each element
    //and access them later to find the area for each index. Here instead of storing pse and nse's we store their indices
    public int largestRectangleArea2(int[] heights) {
        int area = 0;
        int n = heights.length;
        int[] nse = nextSmallerIndex(heights);
        int[] pse = prevSmallerIndex(heights);
        for (int i = 0; i < heights.length; i++) {
            area = Math.max(area, (nse[i] - pse[i] - 1) * heights[i]);
        }
        return area;
    }

    public int[] nextSmallerIndex(int[] heights) {
        Stack<Integer> st = new Stack<>();
        int[] nse = new int[heights.length];
        for (int i = heights.length - 1; i >= 0; i--) {
            while (!st.isEmpty() && heights[i] <= heights[st.peek()]) {
                st.pop();
            }
            if (!st.isEmpty()) {
                nse[i] = st.peek();
            } else {
                nse[i] = heights.length;
            }
            st.push(i);
        }
        return nse;
    }

    public int[] prevSmallerIndex(int[] heights) {
        Stack<Integer> st = new Stack<>();
        int[] pse = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            while (!st.isEmpty() && heights[i] <= heights[st.peek()]) {
                st.pop();
            }
            if (!st.isEmpty()) {
                pse[i] = st.peek();
            } else {
                pse[i] = -1;
            }
            st.push(i);
        }
        return pse;
    }

    //rotting oranges
    //https://leetcode.com/problems/rotting-oranges/
    class triad {
        int first;
        int second;
        int third;

        public triad(int first, int second, int third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }
    }

    public int orangesRotting(int[][] grid) {
        Queue<triad> q = new LinkedList<>();
        int n = grid.length;
        int m = grid[0].length;
        int freshOranges = 0; //to keep track of all fresh oranges in case some were unreachable
        int count = 0; //to keep track of every fresh orange that has been rotten-ed
        int time = 0; //to keep track of min time
        int[] delRow = {-1, 0, 1, 0};
        int[] delCol = {0, 1, 0, -1};
        int[][] vis = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 2) {
                    q.add(new triad(i, j, 0));
                    vis[i][j] = 2;
                } else if (grid[i][j] == 1) {
                    freshOranges++;
                }
            }
        }
        while (!q.isEmpty()) {
            triad t = q.poll();
            int row = t.first;
            int col = t.second;
            time = Math.max(time, t.third);
            for (int i = 0; i < 4; i++) {
                int nrow = row + delRow[i];
                int ncol = col + delCol[i];
                if (nrow >= 0 && nrow < n &&
                        ncol >= 0 && ncol < m &&
                        vis[nrow][ncol] != 2 &&
                        grid[nrow][ncol] == 1) {
                    q.add(new triad(nrow, ncol, t.third + 1));
                    vis[nrow][ncol] = 2;
                    count++;
                }
            }
        }
        if (count != freshOranges) { //some fresh oranges were unreachable
            return -1;
        }
        return time;
    }

    //sliding window maximum
    //https://leetcode.com/problems/sliding-window-maximum/

    //brute (O(N*K)time) - traverse the list from start till (n-k)th and in each iteration traverse each kth set of elements
    // and find max among them

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length - k + 1;
        int[] mx = new int[n];
        for (int i = 0; i < n; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i; j <= i + k - 1; j++) { //iterating every set of k
                max = Math.max(max, nums[j]);
            }
            mx[i] = max;
        }
        return mx;
    }


    //optimal - (O(N)+O(N))time because we are iterating for each element in the array and in each iteration we remove elements<=arr[i]
    // and out of boundary elements from the deque which can be a total of O(N) amortised. O(K)space because deque at max can store only k elements.
    // We use a deque to store indices of elements of each set of k size in a decreasing order. While traversing the array
    //we check from the last of the deque if the current element is greater than the element stored in the deque. If it is,
    // we simply remove the deque element as it is of no use since we are storing elements in decreasing order.
    // Then if further elements are less than the deque element(checking from last), we simply add them to the
    // deque (maintaining a decreasing order). If at any point of time the deque has an element(checking from front) out of boundary ie out
    // of the k limit for a particular set, we remove it from deque. At the end of each iteration, the first element of the
    //deque would be storing the max element for that particular set of k size

    //intuition - for any element arr[i], we know with certainty that if any element el that appeared before arr[i] is
    // such that el<=arr[i], then that element can never be the maximum element for that sub-array of k size

    //we start taking elements when i becomes >= k-1
    //to keep track of whether an element from deque is out of bounds, we check if that element's index is == i-k meaning that it is the element
    // just before the starting element of a set

    //DRY RUN!!
    public int[] maxSlidingWindow2(int[] nums, int k) {
        int n= nums.length;
        int[] ans=new int[n-k+1];
        int max=0;
        Deque<Integer> deque=new ArrayDeque<>();
        for(int i=0;i<n;i++){
            if(!deque.isEmpty()&&deque.peek()==i-k){ //out of boundary element
                deque.poll();
            }
            while(!deque.isEmpty()&&nums[deque.peekLast()]<=nums[i]){  //element smaller than current element
                deque.pollLast();
            }
            deque.offer(i);
            if(i>=k-1){ //completed first set, now start taking max elements for each set
                ans[max]=nums[deque.peek()];
                max++;
            }
        }
        return ans;
    }

    //implement minimum stack
    //https://leetcode.com/problems/min-stack/

    //brute - We store pairs in stack with the first element being the whatever that has been pushed into the stack and
    // the second element being the min element among all. For get min, we simply return st.peek().second.
    // Rest all operations would be same
    // O(1)tc, O(2N)sc since 2N elements are being stored in stack as pairs.

    class MinStack {
        class Pair{
            int first;
            int second;
            public Pair(int first, int second){
                this.first=first;
                this.second=second;
            }
        }
        Stack<Pair> st;
        public MinStack() {
            st=new Stack<>();
        }

        public void push(int val) {
            int min;
            if(st.isEmpty()){
                min=val;
            }else{
                min=Math.min(st.peek().second,val);
            }
            st.push(new Pair(val,min));
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

    //optimal - (O(1)tc,O(N)sc)
    //we maintain a min variable to store the min value among all items of the stack
    //we modify the push and pop operations such that while pushing we check if the val to be pushed is smaller than min.
    // If it is, we push 2*val-min ie a modified val into the stack and update min. To obtain top we check if the stack's
    // top is smaller than min which would imply that the top is a modified value which was obtained using current min.
    // Thus, we return the current min, else we return top.
    // Intuition behind knowing that st.top() is modified if it is < min:
    // We pushed a val which was < min ie val<min ie val-min<0 ie 2*val-min<val ie the modified val will always be smaller than the val which later on becomes minimum
    //for pop() we again check if the top is smaller than min and in that case we have to reset the current min to the previous min, and
    // we do that by setting min to 2*min-st.top()
    //Intuition: st.top() in this case is nothing but the modified val ie 2*currentMin-prevMin thus the whole expression becomes
    //2*min-2*currentMin+prevMin = prevMin
    class MinStack2 {
        Stack<Long> st; //modified val can exceed int range
        Long min;
        public MinStack2() {
            this.st=new Stack<>();
            this.min=Long.MAX_VALUE;
        }

        public void push(int val) {
            Long v=Long.valueOf(val);
            if(st.isEmpty()){
                min=v;
                st.push(v);
            }
           else{
               if(v<min){
                   Long modVal=2*v-min; //pushing modified val
                   st.push(modVal);
                   min=v;
               }
               else{
                   st.push(v);
               }
            }

        }
        public void pop() {
            if(st.isEmpty()){
                return;
            }
            if(st.peek()<min){
                min=2*min-st.peek(); //rolling min back to prev min val
            }
            st.pop();

        }
        public int top() {
            if(st.isEmpty()){
                return -1;
            }
            if(st.peek()<min){
                return min.intValue();
            }
            return st.peek().intValue();
        }

        public int getMin() {
            return min.intValue();
        }
    }
}