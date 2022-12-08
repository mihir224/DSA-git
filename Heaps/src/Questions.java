import java.time.temporal.Temporal;
import java.util.*;

public class Questions implements Comparable<Questions> {
    @Override
    public int compareTo(Questions o) {
        return 0;
    }

    public class TreeNode{
        private int val;
        private TreeNode left;
        private TreeNode right;
        private int i;
        private int j;
        public TreeNode(int val, int row, int col){
            this.val=val;
            this.i=row;
            this.j=col;
        }
        public TreeNode(){};
        public TreeNode(int val) {
            this.val = val;
            this.left=this.right=null;
        }
    }
    public static void main(String[] args) {
        int[] a={4,3,2,1};
        int[] b={9,8,7,5};
        System.out.println(Arrays.toString(mergeHeaps1(a,b,a.length,b.length)));

    }
    public static int[] mergeHeaps1(int[] a, int[] b, int n, int m) {
        int[] c=new int[n+m];
        int i=0;
        int j=0;
        int k=0;
        while(i<n){ //case when b is fully traversed and a still has some elements
            c[k]=a[i];
            i++;
            k++;
        }
        while(j<m){
            c[k]=b[j];
            j++;
            k++;
        }
        //converting the merged array into heap

        return c;
    }

    //kth smallest
    //https://practice.geeksforgeeks.org/problems/kth-smallest-element5635/1
    public static int kthSmallest(int[] arr, int l, int r, int k)
    {
        PriorityQueue<Integer> pq=new PriorityQueue<>(Collections.reverseOrder());
        for(int i=l;i<k;i++){
            pq.offer(arr[i]); //pushing first k elements into the pq
        }
        for (int i=k;i<=r;i++){
            if(arr[i]<pq.peek()){
                pq.poll();
                pq.offer(arr[i]);
            }
        }
        int ans=pq.poll();
        return ans;
    }

    //isBinaryTreeHeap
    //https://practice.geeksforgeeks.org/problems/is-binary-tree-heap/1
    public boolean isHeap(TreeNode tree) {
        int nodeCount=totalCount(tree);
        return (isCBT(tree,0,nodeCount)&&isMaxHeap(tree));
    }
    public boolean isCBT(TreeNode root,int index,int nodeCount){
        if(root==null){
            return true;
        }
        if(index>=nodeCount){ //0 based indexing
            return false;
        }
        else{
            boolean left=isCBT(root.left,2*index+1,nodeCount);
            boolean right=isCBT(root.right,2*index+2,nodeCount);
            return left&&right;
        }
    }
    public boolean isMaxHeap(TreeNode root){
        if(root.left==null&&root.right==null){
            return true;
        }
        if(root.right==null){ //only left child
            return root.left.val<root.val;
        }
        else{
            boolean left=isMaxHeap(root.left);
            boolean right=isMaxHeap(root.right);
            return left&&right&&(root.left.val<root.val&&root.right.val<root.val);
        }

    }
    public int totalCount(TreeNode root){
        if(root==null){
            return 0;
        }
        return 1+totalCount(root.left)+totalCount(root.right);
    }

    //merge 2 max heaps
    //https://practice.geeksforgeeks.org/problems/merge-two-binary-max-heap0144/1
    public int[] mergeHeaps(int[] a, int[] b, int n, int m) {
        int[] c=new int[n+m];
        int i=0;
        int j=0;
        int k=0;
        while(i<n&&j<m){
            if(a[i]<b[j]){
                c[k]=a[i];
                i++;
            }
            else{
                c[k]=b[j];
                j++;
            }
            k++;
        }
        while(i<n){ //case when b is fully traversed and a still has some elements
            c[k]=a[i];
            i++;
            k++;
        }
        while(j<m){
            c[k]=b[j];
            j++;
            k++;
        }
        //converting the merged array into heap
        for(int s=((n+m)/2)-1;s>=0;s--){ //0-based indexing
            heapify(c,n+m,s);
        }
        return c;
    }
    public void heapify(int[] arr,int n,int i){
        int largest=i;
        int left=2*i+1; //0-based indexing
        int right=2*i+2;
        if(left<n&&arr[largest]<arr[left]){
            largest=left;
        }
        if(right<n&&arr[largest]<arr[right]){
            largest=right;
        }
        if(largest!=i){ //value of largest has been changed meaning we found a violating child
            int temp=arr[i];
            arr[i]=arr[largest];
            arr[largest]=temp;
            heapify(arr,n,largest); //recursively converting further nodes to heap
        }
    }

    //min cost of ropes
    //https://practice.geeksforgeeks.org/problems/minimum-cost-of-ropes-1587115620/1
    long minCost(long arr[], int n)
    {
        PriorityQueue<Long> pq=new PriorityQueue<Long>();
        for(long i:arr){
            pq.offer(i);
        }
        long ans=0;
        while(n>1){
            long a=pq.poll();
            long b=pq.poll();
            long sum=a+b;
            ans+=sum;
        }
        return ans;
    }

    //convert bst to min heap where left child is always smaller than right child
    //https://www.geeksforgeeks.org/convert-bst-min-heap/
    private int index;
    public void inorder(TreeNode root, ArrayList<Integer> arr){
        if(root==null){
            return;
        }
        inorder(root.left,arr);
        arr.add(root.val);
        inorder(root.right,arr);
    }
    public void preorder(TreeNode root, ArrayList<Integer> arr){
        if(root==null){
            return ;
        }
        root.val= arr.get(index++); //this will change the root in each iteration to the corresponding inorder element of the given bst
        preorder(root.left,arr);
        preorder(root.right,arr);
    }
    public void convertToMinHeap(TreeNode root){
        this.index=0;
        ArrayList<Integer> arr=new ArrayList<>();
        inorder(root,arr); //traversing inorder and storing it in arr
        preorder(root,arr);
    }

    //kth largest sum in a contigious sub array
    //https://practice.geeksforgeeks.org/problems/k-th-largest-sum-contiguous-subarray/1
    public static int kthLargest(int N, int K, int[] Arr) {
        ArrayList<Integer> list=new ArrayList<>();

        for(int i=0;i<N;i++){
            int sum=0;
            for(int j=i;j<N;j++){ //for traversing sub array in each traversal
                sum+=Arr[j];
                list.add(sum);
            }
        }
        int[] arr=new int[list.size()];
        for(int i=0;i<list.size();i++){
            arr[i]=list.get(i);
        }
        Arrays.sort(arr);
        return arr[arr.length-K];
    }

    public static int kthLargest1(int N, int K, int[] Arr) {
        PriorityQueue<Integer> pq=new PriorityQueue<>();
        for(int i=0;i<N;i++) {
            int sum = 0;
            for (int j = i; j < N; j++) { //for traversing sub array in each traversal
                sum += Arr[j];
                if (pq.size() < K) {
                    pq.offer(sum);
                } else {
                    if (sum > pq.peek()) {
                        pq.poll();
                        pq.offer(sum);
                    }
                }
            }
        }
        return pq.peek();
    }

    //merge k sorted arrays
    //https://www.interviewbit.com/problems/merge-k-sorted-arrays/
    //brute force: merge all arrays into one, sort that array and return

    //optimised (using min heap)
    class NodeComparator implements Comparator<TreeNode> {
        public int compare(TreeNode s1, TreeNode s2) {
            return 0;
        }
    }
    public ArrayList<Integer> solve(ArrayList<ArrayList<Integer>> A) {
        PriorityQueue<TreeNode> pq=new PriorityQueue<>(new NodeComparator());
        ArrayList<Integer> ans=new ArrayList<>();
        for(int i=0;i<A.size();i++){
            TreeNode node=new TreeNode(A.get(i).get(0),i,0);
            pq.offer(node);
        }
        while(pq.size()>0){
            TreeNode temp=pq.poll();
            ans.add(temp.val);
            int i=temp.i;
            int j=temp.j;
            if((j+1)<A.get(i).size()){ //inserting element next of the smallest element in all sub arrays
                TreeNode next=new TreeNode(A.get(i).get(j+1),i,j+1);
                pq.offer(next);
            }
        }
        return ans;
    }

    //smallest range in a sorted list
    public int[] smallestRange(List<List<Integer>> nums) {
        int min=Integer.MAX_VALUE;
        int max=Integer.MIN_VALUE;
        PriorityQueue<TreeNode> pq=new PriorityQueue<>();
        for (int i=0;i<nums.size();i++){ //pushing 1st element of all the lists into the min heap
            int element=nums.get(i).get(0);
            min=Math.min(min,element);  //tracking max and min
            max=Math.max(max,element);
            pq.offer(new TreeNode(element,i,0));
        }
        int ansStart=min;
        int ansEnd=max;
        while (pq.isEmpty()) {
            TreeNode temp=pq.poll(); //min node
            min=temp.val;
            if(max-min<ansEnd-ansStart){ //update ansEnd and ansStart
                ansEnd=max;
                ansStart=min;
            }
            if(temp.j<nums.get(0).size()){ //next of min exists
                //update max
                max=Math.max(max,nums.get(temp.i).get(temp.j+1)); //moving to element next of min
                pq.offer(new TreeNode(nums.get(temp.i).get(temp.j+1),temp.i,temp.j+1));

            }
            else {
                break;
            }
        }

        return new int[]{ansStart,ansEnd};
    }










}
