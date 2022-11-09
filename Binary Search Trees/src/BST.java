import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Stack;

public class BST {
    public class TreeNode {
        private int value;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }

        public TreeNode() {
            this.value = 0;
            this.left = null;
            this.right = null;
        }
    }

    public static void main(String[] args) {

    }

    //search in bst
    //https://leetcode.com/problems/search-in-a-binary-search-tree/
    public TreeNode searchBST(TreeNode root, int val) {
        if (root != null && root.value != val) {
            root = val < root.value ? root.left : root.right;
        }
        return root;
    }

    //ceil in bst
    //https://practice.geeksforgeeks.org/problems/implementing-ceil-in-bst/1
    int findCeil(TreeNode root, int key) {
        if (root == null) return -1;
        int ceil = 0;
        while (root != null) {
            if (root.value == key) {
                ceil = root.value;
            }
            if (root.value < key) {
                root = root.right; //move right if current value < key
            } else {
                ceil = root.value;
                root = root.left; //move left if current value greater than key
            }
        }
        return ceil;
    }

    //floor
    int findFloor(TreeNode root, int key) {
        if (root == null) return -1;
        int floor = 0;
        while (root != null) {
            if (root.value == key) {
                floor = root.value;
            }
            if (root.value < key) {
                floor = root.value;
                root = root.right; //move right if current value < kek
            } else {
                root = root.left; //move left if current value greater than key
            }
        }
        return floor;
    }

    //insert a given node in bst
    //https://leetcode.com/problems/insert-into-a-binary-search-tree/
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val); //if tree is empty, insert at node
        }
        TreeNode node = root;
        while (true) {
            if (val > node.value) { //insert somewhere in right subtree
                if (node.right != null) {
                    node = node.right;
                } else { //right is null
                    node.right = new TreeNode(val);
                    break;
                }
            } else { //insert somewhere in left subtree
                if (node.left != null) {
                    node = node.left;
                } else { //left is null
                    node.left = new TreeNode(val);
                    break;
                }
            }
        }
        return root;
    }

    //delete a node in bst
    //https://leetcode.com/problems/delete-node-in-a-bst/
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.value == key) {
            return helper(root); //case when root is to be deleted
        }
        TreeNode node = root;
        while (node != null) {
            if (key < node.value) { //key exists in left subtree
                if (node.left != null && node.left.value == key) {
                    node.left = helper(node.left);
                    break;
                } else {
                    node = node.left;
                }
            } else { //key exists in right sub tree
                if (node.right != null && node.right.value == key) {
                    node.right = helper(node.right);
                    break;
                } else {
                    node = node.right;
                }
            }
        }
        return root;
    }

    public TreeNode helper(TreeNode node) {
        if (node.left == null) {   //edge cases
            return node.right;
        } else if (node.right == null) {
            return node.left;
        }
        TreeNode rightChild = node.right;
        TreeNode rightOfLeftSubTree = findRightLeaf(node.left);
        rightOfLeftSubTree.right = rightChild;
        return node.left;
    }

    public TreeNode findRightLeaf(TreeNode node) {
        if (node.right == null) {
            return node;
        }
        return findRightLeaf(node.right);
    }

    //kth smallest element in a bst
    //https://leetcode.com/problems/kth-smallest-element-in-a-bst/
    public int ans = 0;
    public int count = 0;

    public int kthSmallest(TreeNode root, int k) {
        count = k;
        helper1(root);
        return ans;
    }

    public void helper1(TreeNode root) {
        if (root.left != null) {
            helper1(root.left);
        }
        count--;
        if (count == 0) {
            ans = root.value;
            return;
        }
        if (root.right != null)
            helper1(root.right);
    }

    //valid bst
    //https://leetcode.com/problems/validate-binary-search-tree/
    public boolean isValidBST(TreeNode root) {
        return helper2(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean helper2(TreeNode root, long min, long max) {
        if (root == null) {  //traversed the whole tree, no one returned false
            return true;
        }
        if (root.value >= max || root.value <= min) {
            return false;
        }
        return helper2(root.left, min, root.value) && helper2(root.right, root.value, max);
    }

    //lowest common ancestor in bst
    //https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        TreeNode node = root;
        if (node.value < p.value && node.value < q.value) { //current node less than p q, move right
            return lowestCommonAncestor(root.right, p, q);
        }
        if (node.value > p.value && node.value > q.value) { //current node greater than p q, move left
            return lowestCommonAncestor(root.left, p, q);
        }
        return root; //path split, thus lca found
    }

    //construct bst from preorder
    //https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
    public TreeNode bstFromPreorder(int[] preorder) {
        return helper3(preorder, Integer.MAX_VALUE, new int[]{0});
    }

    public TreeNode helper3(int[] preorder, int upperBound, int[] i) {
        if (i[0] == preorder.length || preorder[i[0]] > upperBound) { //case when current node exceeds the upperbound
            return null;
        }
        TreeNode root = new TreeNode(preorder[i[0]]);
        i[0]++;
        root.left = helper3(preorder, root.value, i);
        root.right = helper3(preorder, upperBound, i);
        return root;
    }

    //inorder successor in bst
    //https://practice.geeksforgeeks.org/problems/inorder-successor-in-bst/1
    public TreeNode inorderSuccessor(TreeNode root, TreeNode x) {
        TreeNode successor = null;
        while (root != null) {
            if (x.value >= root.value) {
                root = root.right;
            } else {
                successor = root;
                root = root.left;
            }
        }
        return successor;
    }

    class BSTIterator {
        private Stack<TreeNode> stack;
        public BSTIterator(TreeNode root) {
            this.stack=new Stack<TreeNode>();
            pushLeft(root);
        }
        public int next() {
            TreeNode node=stack.pop();
            pushLeft(node.right);
            return node.value;
        }
        public boolean hasNext() {
            return !stack.isEmpty();
        }
        public void pushLeft(TreeNode node){
            while(node!=null){
                stack.push(node);
                node=node.left;
            }
        }
    }

    //two sum bst
    //https://leetcode.com/problems/two-sum-iv-input-is-a-bst/
    class BSTIterator1 {
        private Stack<TreeNode> stack;
        private boolean isReverse;
        public BSTIterator1(TreeNode root, boolean isReverse) {
            this.stack=new Stack<TreeNode>();
            this.isReverse=isReverse;
            push(root);
        }
        public int next() {
            TreeNode node=stack.pop();
            if(isReverse){
                push(node.left);
            }
            else{
                push(node.right);
            }
            return node.value;
        }
        public boolean hasNext() {
            return !stack.isEmpty();
        }
        public void push(TreeNode node){
            while(node!=null){
                stack.push(node);
                if(isReverse){
                    node=node.right;
                }
                else{
                    node=node.left;
                }
            }
        }
    }
    class Solution {
        public boolean findTarget(TreeNode root, int k) {
            BSTIterator1 next=new BSTIterator1(root,false);
            BSTIterator1 prev=new BSTIterator1(root,true);
            if(root==null){
                return false;
            }
            int i=next.next();
            int j=prev.next();
            while(i<j){
                if(i+j==k){
                    return true;
                }
                else if((i+j)<k){
                    i=next.next();
                }
                else{
                    j=prev.next();
                }
            }
            return false;
        }
    }

    //recover bst
    //https://leetcode.com/problems/recover-binary-search-tree/
    TreeNode prev; //to store node prev to current node
    TreeNode first;
    TreeNode mid;
    TreeNode last;
    public void recoverTree(TreeNode root) {
        first=null;
        mid=null;
        last=null;
        prev =new TreeNode(Integer.MIN_VALUE);
        helper4(root);
        if(first!=null&&last!=null){ //swap nodes not adjacent
            int temp=first.value; //java is pass by value, thus we perform swap manually
            first.value=last.value;
            last.value=temp ;
        }
        else if(first!=null&&mid!=null){
            int temp=first.value;
            first.value=mid.value;
            mid.value=temp;
        }
    }
    public void helper4(TreeNode root){  //to simultaneously perform inorder and check each node
        if(root==null){
            return;
        }
        helper4(root.left); //go left
        if(prev!=null&&(root.value< prev.value)){  //first violation
            if(first==null){
                first=prev;
                mid=root;
            }
            else{  //second violation
                last=root;
            }
        }
        prev=root; //mark this root as prev and then go right
        helper4(root.right);
    }

    //size of largest bst in a bt
    //https://practice.geeksforgeeks.org/problems/largest-bst/1
    static class Node{
        public int smallestRight;
        public int largestLeft;
        public int size;
        public Node(int largestLeft, int smallestRight, int size) {
            this.largestLeft = largestLeft;
            this.smallestRight = smallestRight;
            this.size = size;
        }
    }
    static int largestBst(TreeNode root)
    {
        return helper5(root).size;
    }
    static public Node helper5(TreeNode root){
        if(root==null){
            return new Node(Integer.MIN_VALUE,Integer.MAX_VALUE,0);
        }
        Node left=helper5(root.left); //performing post order traversal
        Node right=helper5(root.right);
        if(left.largestLeft<root.value&&root.value<right.smallestRight){ //valid bst
            return new Node(Math.max(root.value,left.largestLeft),Math.min(root.value, right.smallestRight),left.size+right.size+1);
        }
        //otherwise invalid bst, return node with max largest and min smallest
        return new Node(Integer.MAX_VALUE, Integer.MIN_VALUE,Math.max(left.size, right.size));

    }

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq=new PriorityQueue<>();
        for(int i=0;i<k;i++){
            pq.add(nums[i]);
        }
        for(int i=k;i<nums.length;i++){
            if(pq.peek()<nums[i]) {
                pq.poll();
                pq.add(nums[i]);
            }
        }
        return pq.peek();
    }

}


