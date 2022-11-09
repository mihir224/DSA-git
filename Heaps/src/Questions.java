import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class Questions {
    public class TreeNode{
        private int val;
        private TreeNode left;
        private TreeNode right;
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

}
