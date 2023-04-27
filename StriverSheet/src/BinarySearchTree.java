import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

class BinarySearchTree{
    TreeNode root;
    public BinarySearchTree(){
        this.root=null;
    }
    class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;
        public TreeNode(){

        }
        public TreeNode(int val){
            this.val=val;
            this.left=this.right=null;
        }
    }

    //search in a BST
    //https://leetcode.com/problems/search-in-a-binary-search-tree/
    public TreeNode searchBST(TreeNode root, int val) {
        while(root!=null&&root.val!=val){ //if <val search in right else search in left
            root=root.val>val?root.left:root.right;
        }
        return root;
    }

    //floor in BST
    //https://practice.geeksforgeeks.org/problems/floor-in-bst/1
    public static int floor(TreeNode root, int val) {
        if(root==null){
            return -1;
        }
        int floor=-1;
        while(root!=null){
            if(root.val>val){
                root=root.left;
            }
            else{
                floor=root.val;
                root=root.right;
            }
        }
        return floor;
    }

    //ceil of BST
    //https://practice.geeksforgeeks.org/problems/implementing-ceil-in-bst/1
    int findCeil(TreeNode root, int val) {
        if (root == null) return -1;
        int ceil=-1;
        while(root!=null){
            if(root.val<val){
                root=root.right;
            }
            else{
                ceil=root.val;
                root=root.left;
            }
        }
        return ceil;
    }

    //kth smallest element in a bst
    //https://leetcode.com/problems/kth-smallest-element-in-a-bst/
    public int kthSmallest(TreeNode root, int k) {
        int count[]={k};
        int ans[]=new int[1];
        //since java doesn't have pass by reference
        helper(root,count,ans);
        return ans[0];
    }
    public void helper(TreeNode root, int[] count, int[] ans){
        if(root==null){
            return;
        }
        helper(root.left,count,ans);
        count[0]--;
        if(count[0]==0){
            ans[0]=root.val;
        }
        helper(root.right,count,ans);
    }

    //kth largest element in bst
    //https://practice.geeksforgeeks.org/problems/kth-largest-element-in-bst/1
    public int kthLargest(TreeNode root,int k)
    {
        int[] count={k};
        int[] ans=new int[1];
        helper2(root,count,ans);
        return ans[0];
    }
    public void helper2(TreeNode root, int[] count, int[] ans){
        if(root==null){
            return;
        }
        helper2(root.right,count,ans);
        count[0]--;
        if(count[0]==0){
            ans[0]=root.val;
        }
        helper2(root.left,count,ans);
    }

    //given an array, we have to convert it to a height balanced binary search tree. A ht balanced bt is the one in which
    //depth of the two subtrees of every node never differ by more than 1
    //https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/

    //approach - to make the tree height balanced, we have to take the middle element of the given sorted array and assign
    // it as root and then we recursively apply binary search to for further subtrees

    //O(logBASE2N) time and space - since we're applying recursive binary search  thus for logbase2N comparisons stack space can be logbase2N
    //imp - dry run to understand better
    public TreeNode sortedArrayToBST(int[] nums) {
        return helper3(0,nums.length-1,nums);
    }
    public TreeNode helper3(int start,int end,int[] nums){
        if(start>end){
            return null;
        }
        int mid=start+(end-start)/2;
        TreeNode root=new TreeNode(nums[mid]);
        root.left=helper3(start,mid-1,nums);
        root.right=helper3(mid+1,end,nums);
        return root;
    }

    //construct bst from preorder
    //https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/

    //O(3N - since we visit a particular node 3 times)=O(N)

    public TreeNode bstFromPreorder(int[] preorder) {
        return helper4(preorder,new int[]{0},Integer.MAX_VALUE);
    }
    public TreeNode helper4(int[] preorder, int[] i,int upperBound){
        if(i[0]==preorder.length||preorder[i[0]]>upperBound){
            return null;
        }
        TreeNode root=new TreeNode(preorder[i[0]]);
        i[0]++;
        root.left=helper4(preorder,i,root.val);
        root.right=helper4(preorder,i,upperBound);
        return root;
    }

    //validate bst
    //https://leetcode.com/problems/validate-binary-search-tree/
    public boolean isValidBST(TreeNode root) {
        return isValid(root,Long.MIN_VALUE,Long.MAX_VALUE);
    }
    public boolean isValid(TreeNode root, long lowerBound, long upperBound){
        if(root==null){ //traversed whole tree, no one returned false
            return true;
        }
        if(root.val>=upperBound||root.val<=lowerBound){
            return false;
        }
        return isValid(root.left,lowerBound,root.val)&&isValid(root.right,root.val,upperBound);
    }

    //lca of a BST
    //https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/

    //O(Height of tree)tc, O(N)sc-stack space for recursion
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null){
            return null;
        }
        if(root.val>p.val&&root.val>q.val){
            return lowestCommonAncestor(root.left,p,q);
        }
        if(root.val<p.val&&root.val<q.val){
            return lowestCommonAncestor(root.right,p,q);
        }
        return root; //point of intersection
    }

    //inorder successor/predecessor of BST
    //https://practice.geeksforgeeks.org/problems/predecessor-and-successor/1

    //brute - (O(N) for storing inorder + O(Logbase2N) for applying binary search to find predecessor and successor
    //better - (O(N) - performing inorder traversal and returning the first val greater than given node (morris traversal) ie space is O(1))
    //optimal - (O(height of tree)time, O(1)space)
    public void findPreSuc(TreeNode root, TreeNode p, TreeNode s, int key)
    {
        p=findP(root,key);
        s=findS(root,key);
    }
    public TreeNode findS(TreeNode root,int key){
        TreeNode successor=null;
        while(root!=null){
            if(root.val<key){
                root=root.right;
            }
            else{
                successor=root;
                root=root.left;
            }
        }
        return successor;
    }
    public TreeNode findP(TreeNode root,int key){
        TreeNode predecessor=null;
        while(root!=null){
            if(root.val>key){
                root=root.left;
            }
            else{
                predecessor=root;
                root=root.right;
            }
        }
        return predecessor;
    }

    //BST iterator
    //https://leetcode.com/problems/binary-search-tree-iterator/

    //brute - we store the inorder traversal of the given BST and maintain a ptr over it which returns the current node
    // and moves to the next node whenever next() is called. When that ptr points to null we hasNext() returns false
    //O(1) time and O(N) space. But there might be a possibility that we aren't allowed to store the inorder.

    //optimal - O(1) time, O(ht of tree) space

    class BSTIterator {
        Stack<TreeNode> st;
        public BSTIterator(TreeNode root) {
            this.st=new Stack<>();
            pushLeft(root);
        }

        public int next() {
            TreeNode node=st.pop();
            pushLeft(node.right);
            return node.val;
        }

        public boolean hasNext() {
            return !st.isEmpty();
        }
        public void pushLeft(TreeNode node){
            while(node!=null){
                st.push(node);
                node=node.left;
            }
        }
    }

    //Two sum BST
    //https://leetcode.com/problems/two-sum-iv-input-is-a-bst/

    //brute - store inorder to find the elements of bst in a sorted fashion and then apply simple two pointer approach as
    // we did in the original two sum problem
    //O(N)time and space - to traverse the inorder and to store the inorder

    //optimal - use BSTIterator() to obtain the next() function along with a new function prev, which returns the first
    // element of the reverse inorder ie last element of inorder. We can use these functions to obtain next and prev pointers
    // which we can use similarly as we did in two pointer
    //O(N) time and O(ht of tree) space
    public boolean findTarget(TreeNode root, int k) {
        BSTIterator2 prev=new BSTIterator2(root,true);
        BSTIterator2 next=new BSTIterator2(root,false);
        int i=next.next();
        int j=prev.next();
        while(i<j){
            if(i+j==k){
                return true;
            }
            if(i+j<k){
                i= next.next();
            }
            else{
                j= prev.next();
            }
        }
        return false;
    }
    class BSTIterator2 {
        Stack<TreeNode> st;
        boolean isReverse;
        public BSTIterator2(TreeNode root,boolean isReverse) {
            this.st=new Stack<>();
            this.isReverse=isReverse;
            push(root);
        }

        public int next() {
            TreeNode node=st.pop();
            if(isReverse){
                push(node.left);
            }
            else{
                push(node.right);
            }
            return node.val;
        }

        public boolean hasNext() {
            return !st.isEmpty();
        }
        public void push(TreeNode node){
            while(node!=null){
                st.push(node);
                if(isReverse){
                    node=node.right;
                }
                else {
                    node = node.left;
                }
            }
        }
    }

    //size of largest bst in a binary tree
    //https://www.codingninjas.com/codestudio/problems/893103?topList=striver-sde-sheet-problems&utm_source=striver&utm_medium=website&leftPanelTab=0

    //Brute force - We use the valid bst function() to check whether a node is a valid bst or not. If it is, we first
    // move left, and again check and if the current node is a valid bst, we traverse all the nodes of the tree with
    // current node taken as its root and find their sum. Then we move right of the previous current node and do the same
    // thing. We store the maximum sum and then return it at the end.
    //O(N*N)tc - validate bst takes O(N) time and finding the sum of all nodes of the tree where the nth node is taken as root

    //Optimal - O(N)time - preorder, O(1) space (if we don't consider recursive stack space)
    //we know that for a tree to be a bst, it's root's value should be greater than the largest element on the left subtree
    // and, it should be smaller than the smallest element on left subtree. Thus, we start from the bottom and for each node,
    // we store the greatest element on its left, the smallest element on its right and the max sum till that node and if the node
    // satisfies the above condition,we save that node's largest value on the left as the min of (smallest val on left, node's val)
    // and its smallest value on right as max of (largest val on right, node's val). If it isn't a bst, we keep track of the max sum
    // val up till now and set that node's greatest on left as int max and that node's smallest on right as int min so that there's
    // no comparison further. If a root's left and right are null, we set its greatest and smallest to node.val
    // DRY RUN FOR BETTER UNDERSTANDING.- really imp

    public class Solution {
         class Node{
            int size;
            int largest;
            int smallest;
            public Node(int size,int largest, int smallest){
                this.size=size;
                this.largest=largest;
                this.smallest=smallest;
            }
        }
        public  Node helpThis(TreeNode root){
            if(root==null){
                return new Node(0,Integer.MIN_VALUE,Integer.MAX_VALUE);
            }
            Node left=helpThis(root.left);
            Node right=helpThis(root.right);
            if(root.val>left.largest&&root.val<right.smallest){ //valid bst
                return new Node(left.size+right.size+1,Math.max(right.largest,root.val),Math.min(left.smallest,root.val));
            }
            return new Node(Math.max(left.size,right.size),Integer.MAX_VALUE,Integer.MIN_VALUE); //not a bst
        }
        public  int largestBST(TreeNode root) {
            return helpThis(root).size;
        }
    }

    //leetcode soln
    //here we declare a global variable max to store max sum because there might be a possibility that there might be
    // negative values in the tree which might the final max sum in further recursive calls and, thus we use this variable
    // to store the max value out of all the values encountered for max sum
    class Solutions {
        int max=0;
        class Node{
            int size;
            int largest;
            int smallest;
            public Node(int size,int largest, int smallest){
                this.size=size;
                this.largest=largest;
                this.smallest=smallest;
            }
        }
        public  Node helpThis(TreeNode root){
            if(root==null){
                return new Node(0,Integer.MIN_VALUE,Integer.MAX_VALUE);
            }
            Node left=helpThis(root.left);
            Node right=helpThis(root.right);
            if(root.val>left.largest&&root.val<right.smallest){ //valid bst
                max=Math.max(max,root.val+left.size+right.size);
                return new Node(root.val+left.size+right.size,Math.max(right.largest,root.val),Math.min(left.smallest,root.val));
            }
            return new Node(Math.max(left.size,right.size),Integer.MAX_VALUE,Integer.MIN_VALUE); //not a bst
        }
        public  int maxSumBST(TreeNode root) {
            helpThis(root);
            return max;
        }
    }

    //Serialise/De-serialise binary tree
    //https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
    //O(N)time and space - level order traversal and strong nodes in queue
    public class Codec {
        public String serialize(TreeNode root) {
            StringBuilder sb=new StringBuilder();
            if(root==null){
                return "";
            }
            Queue<TreeNode> q=new LinkedList<>();
            q.add(root);
            while(!q.isEmpty()){
                TreeNode node=q.poll();
                if(node==null){
                    sb.append("null ");
                    continue;
                }
                sb.append(node.val+" ");
                q.add(node.left);
                q.add(node.right);
            }
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if(data==""){
                return null;
            }
            String[] st=data.split(" ");
            TreeNode root=new TreeNode(Integer.parseInt(st[0]));
            Queue<TreeNode> q=new LinkedList<>();
            q.add(root);
            for(int i=1;i<st.length;i++){
                TreeNode node=q.poll();
                if(!st[i].equals("null")){
                    node.left=new TreeNode(Integer.parseInt(st[i]));
                    q.add(node.left);
                }
                if(!st[++i].equals("null")){
                    node.right=new TreeNode(Integer.parseInt(st[i]));
                    q.add(node.right);
                }
            }
            return root;
        }
    }

    //flatten binary tree to linked list
    //https://leetcode.com/problems/flatten-binary-tree-to-linked-list/
    TreeNode prev=null;
    public void flatten(TreeNode root) {
        if(root==null){
            return;
        }
        flatten(root.right);
        flatten(root.left);
        //root is now leaf node
        root.right=prev;
        root.left=null;
        prev=root;
    }







}