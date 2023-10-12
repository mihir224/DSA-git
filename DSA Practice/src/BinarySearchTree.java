import java.util.*;

class BinarySearchTree{
    TreeNode root;
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode next;
        public TreeNode(){}
        public TreeNode(int val){
            this.val=val;
            this.left=this.right=this.next=null;
        }
    }

    //populate next right pointers of tree
    //https://leetcode.com/problems/populating-next-right-pointers-in-each-node/

    //we simply follow a preorder traversal where we check if the root is null we return it. Now since the given tree is
    // a perfect BT, each node must be having 2 children except the leaves. Now if the current root is not null, we check
    // if its left child exists or not and if does, then we assign its left child's next to the node's right. Now since
    // the left child is !null we surely know that there will also be a right child, since given tree is perfect BT.
    // Thus, within the same condition, we check if the root's next is not null. If it isn't then we simply assign its
    // left to right child's next.  We then do this recursively for left and right subtrees.
    public TreeNode connect(TreeNode root) {
        if(root==null){
            return null;
        }
        if(root.left!=null){ //we're certain right also exists since this is a pbt
            root.left.next=root.right;
            if(root.next!=null){ //we check this condition inside the previous condition because if there was no left, then we know for sure there wouldn't be any right and the statement below would've given null pointer exception
                root.right.next=root.next.left;
            }
        }
        connect(root.left);
        connect(root.right);
        return root;
    }

    //search BST
    //https://leetcode.com/problems/search-in-a-binary-search-tree/
    public TreeNode searchBST(TreeNode root, int val) {
        if(root==null||root.val==val){
            return root;
        }
        return root.val<val?searchBST(root.right,val):searchBST(root.left,val);
    }

    //convert sorted array to height balanced bst
    //https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree

    //the reason why we took mid as the root is so that the elements could be evenly distributed to the left and right
    // subtrees, making sure that each node is height balanced. We could form a bst by taking any other node as the
    // root but in that case the resultant tree wouldn't have been height balanced. this will make sure that the tree
    // is a valid bst as well as height balanced
    public TreeNode sortedArrayToBST(int[] nums) {
        return helper(0,nums.length-1,nums);
    }
    public TreeNode helper(int start,int end, int[] nums){
        if(start>end){
            return null;
        }
        int mid=start+(end-start)/2;
        TreeNode root=new TreeNode(nums[mid]);
        root.left=helper(start,mid-1,nums);
        root.right=helper(mid+1,end,nums);
        return root;
    }

    //construct bst from preorder
    //https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/

    //brute - first element is root, thus we can attach remaining elements to the root accordingly. basically for each
    // node, we find the index of the first el greater than the current node. this way, all of the el before this el would
    // be smaller than the current node and will thus belong to the left sub tree. moreover every el on its right including
    // this el would be larger than the current node and will thus belong to the right sub tree. thus we can recursively
    // do this for every node to construct our tree. now in the worst case (skew tree) for eg: {3,3,3,3} here for N nodes
    // there will be N levels, thus the tc would be O(N) recursive * O(N) for finding the greater el of each node
    // thus O(N2) wc time

    public TreeNode bstFromPreord1er(int[] preorder) {
        return help2er(0,preorder.length-1,preorder);
    }
    public TreeNode help2er(int start, int end, int[] preorder){
        if(start>end){
            return null;
        }
        TreeNode root=new TreeNode(preorder[start]);
        int i=0;
        for(i=start;i<preorder.length;i++){
            if(preorder[i]>preorder[start]){
                break;
            }
        }
        root.left=helper(start+1,i-1,preorder);
        root.right=helper(i,end,preorder);
        return root;
    }

    //better - find inorder, use the technique used in bt to find bst (O(NlogN+N))
    public TreeNode bstFromPreorde2r(int[] preorder) {
        int n=preorder.length;
        int[] inorder=Arrays.copyOfRange(preorder,0,n);
        Arrays.sort(inorder);
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<n;i++){
            map.put(inorder[i],i);
        }
        return helper(preorder,0,n-1,inorder,0,n-1,map);
    }
    public TreeNode helper(int[] preorder,int preStart,int preEnd, int[] inorder, int inStart, int inEnd,HashMap<Integer,Integer> map){
        if(preStart>preEnd){
            return null;
        }
        TreeNode root=new TreeNode(preorder[preStart]);
        int inRootIndex=map.get(root.val);
        int x=inRootIndex-inStart;
        root.left=helper(preorder,preStart+1,preStart+x,inorder,inStart,inRootIndex-1,map);
        root.right=helper(preorder,preStart+x+1,preEnd,inorder,inRootIndex+1,inEnd,map);
        return root;
    }

    //optimal - using upperbound technique O(N)
    public TreeNode bstFromPreorder(int[] preorder) {
        return bp(Integer.MAX_VALUE,preorder,new int[]{0});
    }
    public TreeNode bp(int upperbound,int[] preorder,int[] index){
        if(index[0]==preorder.length||preorder[index[0]]>upperbound){
            return null;
        }
        TreeNode root=new TreeNode(preorder[index[0]]);
        index[0]++;
        root.left=bp(root.val,preorder,index);
        root.right=bp(upperbound,preorder,index);
        return root;
    }

    //validate bst
    //https://leetcode.com/problems/validate-binary-search-tree

    //better to ask the interviewer if every node is distinct or there can be duplicates. if there are duplicates, the
    // condition of bst is modified to l<=n<r. in this question, there are only distinct elements, thus we use the
    // conventional formula for bst which is non inclusive: l<n<r
    public boolean isValidBST(TreeNode root) {
        return isValid(root,Long.MIN_VALUE,Long.MAX_VALUE);
    }
    public boolean isValid(TreeNode root,long lowerbound,long upperbound){
        if(root==null){
            return true;
        }
        if(root.val<=lowerbound||root.val>=upperbound){
            return false;
        }
        return isValid(root.left,lowerbound,root.val)&&isValid(root.right,root.val,upperbound);
    }

    //lca of bst
    //https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null){
            return null;
        }
        if(p.val<root.val&&q.val<root.val){ //both p, q lie in the left subtree
            return lowestCommonAncestor(root.left,p,q);
        }
        else if(p.val>root.val&&q.val>root.val){
            return lowestCommonAncestor(root.right,p,q); //both pq lie in the right subtree
        }
        return root; //path split here, thus this is the lca
    }

    //inorder successor of a node in bst

    //brute - (O(N) for storing inorder + O(Logbase2N) for applying binary search to find predecessor and successor
    //better - (O(N) - performing inorder traversal and returning the first val greater than given node (morris traversal) ie space is O(1))
    //optimal - (O(height of tree)time, O(1)space)
    //https://practice.geeksforgeeks.org/problems/predecessor-and-successor/1

    public static void findPredecessor(TreeNode root, int key){
        TreeNode pre=null; //predecessor
        while(root!=null){
            if(root.val>=key){
                root=root.left;
            }else{
                pre=root;
                root=root.right;
            }
        }
    }
    public static void findSuccessor(TreeNode root, int key){
        TreeNode suc=null; //successor
        while(root!=null){
            if(root.val<=key){
                root=root.right;
            }else{
                suc=root;
                root=root.left;
            }
        }
    }

    //floor of bst
    //https://practice.geeksforgeeks.org/problems/floor-in-bst/1
    public static int floor(TreeNode root, int x) {
        int[] f=new int[1];
        f[0]=-1;
        fl(root,x,f);
        return f[0];
    }
    public static void fl(TreeNode root, int x, int[] f){
        if(root==null){
            return;
        }
        if(root.val<=x){
            f[0]=root.val;
            fl(root.right,x,f);
        }else{
            fl(root.left,x,f);
        }
    }

    //ceil of bst
    //https://practice.geeksforgeeks.org/problems/implementing-ceil-in-bst/1

    int findCeil(TreeNode root, int key) {
        if (root == null) return -1;
        int ceil=-1;
        while(root!=null){
            if(root.val>=key){
                ceil=root.val;
                root=root.left;
            }
            else{
                root=root.right;
            }
        }
        return ceil;
    }

    //find kth smallest element in a bst
    //https://leetcode.com/problems/kth-smallest-element-in-a-bst/

    //moving in inorder fashion and keeping track of the kth el, returning it as soon as k reaches the given val

    public int kthSmallest(TreeNode root, int k) {
        int[] count=new int[1];
        count[0]=1;
        int[] ans=new int[1];
        helperKsmall(root,k,count,ans);
        return ans[0];
    }
    public void helperKsmall(TreeNode root, int k, int[] count, int[] ans) {
        if (root == null) {
            return;
        }
        helperKsmall(root.left, k, count, ans);
        if (count[0] == k) {
            ans[0] = root.val;
        }
        count[0]++;
        helperKsmall(root.right, k, count, ans);
    }

    //alt approach
    int k=0;
    public int kthSmal1est(TreeNode root, int k) {
        return helper(root,k);
    }
    public int helper(TreeNode root, int k){
        if(root==null){
            return 0;
        }
        int left=helper(root.left,k);
        if(left!=0){
            return left;
        }
        this.k++;
        if(this.k==k){
            return root.val;
        }
        int right=helper(root.right,k);
        return right;
    }

    //kth largest element in bst
    //https://practice.geeksforgeeks.org/problems/kth-largest-element-in-bst/1

    public int kthLargest(TreeNode root,int k)
    {
        int[] ans=new int[1];
        int[] count=new int[1];
        count[0]=1;
        helperKlarge(root,k,count,ans);
        return ans[0];
    }
    public void helperKlarge(TreeNode root,int k, int[] count, int[] ans){
        if(root==null){
            return;
        }
        helperKlarge(root.right,k,count,ans);
        if(count[0]==k){
            ans[0]=root.val;
        }
        count[0]++;
        helperKlarge(root.left,k,count,ans);
    }

    //bst iterator
    //https://leetcode.com/problems/binary-search-tree-iterator/
    class BSTIterator {
        private Stack<TreeNode> st;
        public BSTIterator(TreeNode root) {
            this.st=new Stack<>();
            push(root);
        }
        public int next() {
            TreeNode node=st.pop();
            push(node.right);
            return node.val;
        }
        public boolean hasNext() {
            return !st.isEmpty();
        }
        public void push(TreeNode root){
            while(root!=null){
                st.push(root);
                root=root.left;
            }
        }
    }

    //two sum bst
    //https://leetcode.com/problems/two-sum-iv-input-is-a-bst/

    //brute - find inorder, apply two pointer O(2N) time, O(N) space

    //optimal - using modified bst iterator(inorder + reverse inorder) O(N) time, O(2H) space, wc O(N) both

    class BSTIterator2{
        Stack<TreeNode> st;
        boolean flag;
        public BSTIterator2(TreeNode root, boolean flag){
            this.st=new Stack<>();
            this.flag=flag;
            push(root);
        }
        public int next(){
            TreeNode node=st.pop();
            push(node.right); //left sub tree was taken care of, this the root which is being taken care of currently and thus now we take care of the right sub tree
            return node.val;
        }
        public int prev(){
            TreeNode node=st.pop();
            push(node.left); //following reverse inorder and doing exact opposite of what we did in next
            return node.val;
        }
        public void push(TreeNode root){
            if(flag){ //reverse inorder
                while(root!=null){
                    st.push(root);
                    root=root.right;
                }
            }
            else{ //inorder
                while(root!=null){
                    st.push(root);
                    root=root.left;
                }
            }
        }
    }

    public boolean findTarget(TreeNode root, int k) {
        BSTIterator2 obj1=new BSTIterator2(root,false);
        BSTIterator2 obj2=new BSTIterator2(root,true);
        int i=obj1.next();
        int j=obj2.prev();
        //since we're iterating over the inorder from front and back both, so in the worst case either i won't move and
        // j will reach index 0 or j won't move and i will reach index n-1. in both the cases i==j and the loop will break.
        // thus we don't need to explicitly check if the next or prev ptr have gone out of bounds. as soon as i>=j,
        // execution will stop
        while(i<j){
            if(i+j==k){
                return true;
            }
            if(i+j<k){
                i=obj1.next();
            }
            else{
                j=obj2.prev();
            }
        }
        return false;
    }

    //find the max sum bst in a bt
    //https://leetcode.com/problems/maximum-sum-bst-in-binary-tree

    //brute - check validity of each node, and if valid, find sum of all nodes
    public int maxSumB1ST(TreeNode root) {
        int[] max={0};
        helper(root,max);
        return max[0];
    }
    public void helper(TreeNode root, int[] max){
        if(root==null){
            return;
        }
        if(isValid(root,Integer.MIN_VALUE,Integer.MAX_VALUE)){
            int[] sum=new int[1];
            findSum(root,sum);
            max[0]=Math.max(max[0],sum[0]);
        }
        helper(root.left,max);
        helper(root.right,max);
    }
    public boolean isValid(TreeNode root, int lb, int ub){
        if(root==null){
            return true;
        }
        if(root.val>=ub||root.val<=lb){
            return false;
        }
        return isValid(root.left,lb,root.val)&&isValid(root.right,root.val,ub);
    }
    public void findSum(TreeNode root, int[] sum){
        if(root==null){
            return;
        }
        sum[0]+=root.val;
        findSum(root.left,sum);
        findSum(root.right,sum);
    }

    //optimal

    //Optimal - O(N)time - postorder, O(1) space (if we don't consider recursive stack space)
    //we know that for a tree to be a bst, it's root's value should be greater than the largest element on the left subtree
    // and, it should be smaller than the smallest element on the right subtree. Thus, we start from the bottom and for each node,
    // we store the greatest element on its left, the smallest element on its right and the max sum till that node and if the node
    // satisfies the above condition,we save that node's largest value on the left as the min of (smallest val on left, node's val)
    // and its smallest value on right as max of (largest val on right, node's val). If it isn't a bst, we keep track of the max sum
    // val up till now and set that node's greatest on left as int max and that node's smallest on right as int min so that there's
    // no comparison further. If a root's left and right are null, we set its greatest and smallest to node.val
    // DRY RUN FOR BETTER UNDERSTANDING.- really imp
    int max=0;
    class Node{
        int size;
        int largest;
        int smallest;
        public Node(int size, int largest, int smallest){
            this.size=size;
            this.largest=largest;
            this.smallest=smallest;
        }
    }
    public int maxSumBST(TreeNode root) {
        helperMSBST(root);
        return max;
    }
    public Node helperMSBST(TreeNode root){
        if(root==null){
            return new Node(0,Integer.MIN_VALUE,Integer.MAX_VALUE);
        }
        Node left=helperMSBST(root.left); //following postorder traversal
        Node right=helperMSBST(root.right);
        if(root.val>left.largest&&root.val<right.smallest){ //valid bst
            max=Math.max(max,root.val+left.size+right.size); //comparing current bst's size with current max
            return new Node(root.val+left.size+right.size,Math.max(root.val,right.largest),Math.min(root.val,left.smallest));
            //for largest and smallest we take max and min and not root.left.smallest and root.right.largest directly
            // because we have to account for leaf nodes as well where largest on the right would give us a very small
            // val (since that node will be null) and smallest on the left will give us a very large value
        }
        return new Node(0,Integer.MAX_VALUE,Integer.MIN_VALUE); //not a bst, thus returning such extreme values for
        // largest and smallest so that the parent is also deemed not a bst. since it is not a bst, we mark it's
        // size as zero because its parent will also never be a bst and we don't have to use its size as it doesn't concern us
    }

    //binary tree misc

    //flatten bt to LL
    //https://leetcode.com/problems/flatten-binary-tree-to-linked-list/

    //brute - discussed in bt solns
    //optimal - modified Morris traversal
    public void flatten(TreeNode root) {
        if(root==null){
            return;
        }
        while(root!=null){
            if(root.left!=null){
                TreeNode node=root.left;
                while(node.right!=null){
                    node=node.right;
                }
                node.right=root.right;
                root.right=root.left;
                root.left=null;
            }
            root=root.right;
        }
    }

    //find median from data stream
    //https://leetcode.com/problems/find-median-from-data-stream/

    class MedianFinder {
        PriorityQueue<Integer> maxHeap;
        PriorityQueue<Integer> minHeap;
        public MedianFinder() {
            this.maxHeap=new PriorityQueue<>(Collections.reverseOrder());
            this.minHeap=new PriorityQueue<>();
        }
        public void addNum(int num) {
            if(!maxHeap.isEmpty()&&maxHeap.peek()>num){
                maxHeap.offer(num);
            }
            else{
                minHeap.offer(num);
            }
            if(maxHeap.size()>minHeap.size()+1){
                minHeap.offer(maxHeap.poll());
            }
            if(minHeap.size()>maxHeap.size()){
                maxHeap.offer(minHeap.poll());
            }
        }
        public double findMedian() {
            if(minHeap.size()==maxHeap.size()){
                return (maxHeap.peek()+minHeap.peek())/2.0; //even number of elements
            }
            return maxHeap.peek(); //odd number of elements
        }
    }

    //kth largest element in a stream of data
    //https://leetcode.com/problems/kth-largest-element-in-a-stream/

    class KthLargest {
        PriorityQueue<Integer> minHeap;
        int k;
        public KthLargest(int k, int[] nums) {
            this.minHeap=new PriorityQueue<>();
            this.k=k;
            for(int i:nums){
                minHeap.offer(i);
                if(minHeap.size()>k){
                    minHeap.poll();
                }
            }
        }
        public int add(int val) {
            minHeap.offer(val);
            if(minHeap.size()>k){
                minHeap.poll();
            }
            return minHeap.peek();
        }
    }

    //distinct numbers in window
    //https://www.interviewbit.com/problems/distinct-numbers-in-window/

    public ArrayList<Integer> dNums(ArrayList<Integer> A, int B) {
        int i=0;
        int j=0;
        ArrayList<Integer> list=new ArrayList<>();
        HashMap<Integer,Integer> map=new HashMap<>();
        int n=A.size();
        while(j<n){
            map.put(A.get(j),map.getOrDefault(A.get(j),0)+1);
            if(j-i+1<B){
                j++;
            }
            else if(j-i+1==B){
                list.add(map.size());
                map.put(A.get(i),map.get(A.get(i))-1);
                if(map.get(A.get(i))==0){
                    map.remove(A.get(i));
                }
                i++;
                j++;
            }
        }
        return list;
    }


    //kth largest element in an array
    //https://leetcode.com/problems/kth-largest-element-in-an-array/

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap=new PriorityQueue<>();
        for(int i:nums){
            minHeap.offer(i);
            if(minHeap.size()>k){
                minHeap.poll();
            }
        }
        return minHeap.peek();
    }

}
