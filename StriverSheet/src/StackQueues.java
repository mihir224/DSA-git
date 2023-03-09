import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class StackQueues {
    Queue<Integer> q=new LinkedList<>();


    //implement stack using queues
    //https://leetcode.com/problems/implement-stack-using-queues/
    //using 2 queues: O(N)time, O(2N)space

    //using single queue: O(N)time, O(N)space
    public class myStack {
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
    //implement queue using stacks
    //https://leetcode.com/problems/implement-queue-using-stacks/

    //brute - O(N) - pop and peek, O(1) push, O(2N) space
    //for push(), simply push whatever is passed to stack 1. For pop(), empty stack 1 to 2 then remove head of st2, store it
    //then empty s2 to s1 and return head. Same operation for peek()

    //better - O(1) amortised pop and peek, O(1) push, O(2N) space
    //here O(1) amortised means that in almost all cases complexity is O(1) because only in some instances it becomes O(N)
    //we use two stacks - input and output. While popping, we check if st2 is empty. If it is, we simply empty input to
    //output. Otherwise, we simply return output's head. Same goes for peek operations. Push operation will be same

    public class MyQueue{
        Stack<Integer> input=new Stack<>();
        Stack<Integer> output=new Stack<>();
        private int peakElement=-1;

        public void push(int x) {
            if(input.isEmpty())
                peakElement=x; //to keep track of peek element if no pop operation is ever called.
            // This will make sure that we don't empty input to output in the peek() function everytime output is empty
            // making the peek function O(1)
            input.push(x);
        }

        public int pop() {
            if(output.isEmpty()){
                while(!input.isEmpty()) {
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
    //https://leetcode.com/problems/valid-parentheses/

    public boolean isValid(String s) {
        char[] ch=s.toCharArray();
        Stack<Character> st=new Stack<>();
        for(char c:ch){
            if(c=='{'||c=='('||c=='['){
                st.push(c);
            }
            else{ //closing bracket
                if(st.isEmpty()) { //no opening brackets pushed yet, ie the string starts with closing bracket
                    return false;
                }
                char sp=st.peek();
                if(sp=='{'&&c=='}'||sp=='('&&c==')'||sp=='['&&c==']'){
                    st.pop();
                }
                else{
                    return false;
                }
            }
        }
        return st.isEmpty();
    }

    //next greater element-ii
    //https://leetcode.com/problems/next-greater-element-ii/
    public int[] nextGreaterElements(int[] nums) {
        Stack<Integer> st=new Stack<>();
        int[] nge=new int[nums.length];
        int n=nums.length;
        for(int i=2*n-1;i>=0;i--){
            while(!st.isEmpty()&&nums[i%n]>=st.peek()){
                st.pop();
            }
            if(i<n) {
                if (!st.isEmpty()) {
                    nge[i] = st.peek();
                }
                else{
                    nge[i]=-1;
                }
            }
            st.push(nums[i%n]);
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
    public static Stack<Integer> sortedStack(Stack<Integer> st){
        if(st.size()==1){
            return st;
        }
        int topEl=st.pop();
        sortedStack(st);
        return insert(st,topEl);
    }
    public static Stack<Integer> insert(Stack<Integer> st,int topEl){
        if(st.size()==0||st.peek()<=topEl) {
            st.push(topEl); //current topEl already greater than top of stack
            return st;
        }
        int top=st.pop();
        insert(st,topEl);
        st.push(top);
        return st;
    }
}
