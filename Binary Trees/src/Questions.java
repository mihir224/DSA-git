import java.lang.reflect.Array;
import java.util.*;


public class Questions {
    class Pair{
        private TreeNode node;
        private int line;
        public Pair() {
        }

        public Pair(TreeNode node, int line) {
            this.node = node;
            this.line = line;
        }
    }
    class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        public TreeNode(int value) {
            this.value = value;
            left = null;
            right = null;
        }
    }


    //maximum depth (height of a BT)
    //https://leetcode.com/problems/maximum-depth-of-binary-tree/
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = maxDepth(root.left);
        int rightHeight = maxDepth(root.right);
        return 1 + Math.max(leftHeight, rightHeight);
    }

    //balanced binary tree
    //https://leetcode.com/problems/balanced-binary-tree/

    //naive
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        int leftHeight = maxDepth(root.left); //these two take O(n) complexity and thus in the optimised approach we omit them
        int rightHeight = maxDepth(root.right);
        if ((Math.abs(leftHeight - rightHeight)) > 1) {
            return false;
        }
        boolean left = isBalanced(root.left);
        boolean right = isBalanced(root.right);
        if (!left || !right) {
            return false;
        }
        return true;
    }

    //optimised
    public int maxDepth1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = maxDepth1(root.left);
        if (leftHeight == -1) {
            return -1;
        }
        int rightHeight = maxDepth1(root.right);
        if (rightHeight == -1) {
            return -1;
        }
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return 1 + Math.max(leftHeight, rightHeight);
    }

    public boolean isBalanced1(TreeNode root) {
        return maxDepth1(root) != -1;
    }

    //diameter of a binary tree
    //https://leetcode.com/problems/diameter-of-binary-tree/

    //naive
    public int diameterOfBinaryTree1(TreeNode node, int max) {
        if (node == null) {
            return 0;
        }
        int lh = maxDepth(node.left);
        int rh = maxDepth(node.right);
        max = Math.max(max, lh + rh);
        int l = diameterOfBinaryTree1(node.left, max);
        int r = diameterOfBinaryTree1(node.right, max);
        return Math.max(max, l + r);
    }

    //optimised (preferred for leetcode)
    public int diameterOfBinaryTree(TreeNode root) {
        int[] diameter = new int[1];
        height(root, diameter);
        return diameter[0];
    }

    public int height(TreeNode node, int[] diameter) {
        if (node == null) {
            return 0;
        }
        int lh = height(node.left, diameter);
        int rh = height(node.right, diameter);
        diameter[0] = Math.max(diameter[0], lh + rh);
        return 1 + Math.max(lh, rh);
    }

    //maximum path sum
    //https://leetcode.com/problems/same-tree/
    public int maxPathSum(TreeNode root) {
        int maxValue[] = new int[1];
        maxValue[0] = Integer.MIN_VALUE;
        maxPathDown(root, maxValue);
        return maxValue[0];
    }

    public int maxPathDown(TreeNode root, int[] maxValue) {
        if (root == null) {
            return 0;
        }
        int leftSum = Math.max(0, maxPathDown(root.left, maxValue)); //ensures we get 0 if no. is negative
        int rightSum = Math.max(0, maxPathDown(root.right, maxValue));
        maxValue[0]=Math.max(maxValue[0],leftSum+rightSum+root.value);
        return Math.max(leftSum,rightSum)+root.value; //makes sure that the path we follow has the max sum
    }

    //same tree
    //https://leetcode.com/problems/same-tree/
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null||q==null){ //base condition
            return p==q;
        }
        return p.value==q.value&&isSameTree(p.left,q.left)&&isSameTree(p.right,q.right);
    }

    //zig-zag traversal
    //https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        Queue<TreeNode> q=new LinkedList<>();
        List<List<Integer>> ans=new ArrayList<>();   //list storing level wise traversals
        boolean flag=true;
        if(root==null){
            return ans;
        }
        q.offer(root);
        while(!q.isEmpty()){
            List<Integer> list=new ArrayList<>();
            int n=q.size();
            for(int i=0;i<n;i++){
                TreeNode node=q.poll();
                if(node.left!=null){
                    q.offer(node.left);
                }
                if(node.right!=null){
                    q.offer(node.right);
                }
                if(flag){
                    list.add(node.value); //this will add all the nodes at the end of the list
                }
                else{
                    list.add(0, node.value); //this will add all the nodes in reverse
                }
            }
            flag=!flag;
            ans.add(list);
        }
        return ans;
    }

    //boundary traversal
    //https://practice.geeksforgeeks.org/problems/boundary-traversal-of-binary-tree/1
    public ArrayList<Integer> boundary(TreeNode root)
    {
        ArrayList<Integer> ans=new ArrayList<>();
        if(!isLeaf(root)){
            ans.add(root.value);
        }
        addLeftBoundary(root, ans);
        addLeaves(root, ans);
        addRightBoundary(root,ans);
        return ans;
    }
    public boolean isLeaf(TreeNode node){
        return (node.left==null)&&(node.right==null);
    }
    public void addLeftBoundary(TreeNode root, ArrayList<Integer> ans){
        TreeNode temp=root.left;
        while(temp!=null){
            if(!isLeaf(temp)){
                ans.add(temp.value);
            }
            if(temp.left!=null){ //moving left till we reach null
                temp=temp.left;
            }
            else{
                temp=temp.right; //moving right when we can no longer go left
            }
        }
    }

    public void addLeaves(TreeNode root, ArrayList<Integer> ans){
        if(isLeaf(root)){
            ans.add(root.value);
            return;
        }
        if(root.left!=null){
            addLeaves(root.left, ans);
        }
        if(root.right!=null){
            addLeaves(root.right, ans);
        }
    }

    public void addRightBoundary(TreeNode root, ArrayList<Integer> ans){
        TreeNode temp=root.right;
        ArrayList<Integer> tempo=new ArrayList<>(); //to store the right boundary in a temp list so that we can reverse it later
        while(temp!=null){
            if(!isLeaf(temp)){
                tempo.add(temp.value);
            }
            if(temp.right!=null){ //moving right till we reach null
                temp=temp.right;
            }
            else{
                temp=temp.left; //moving left when we can no longer go right
            }
        }
        for(int i=tempo.size()-1;i>=0;i--){ //reversing the right boundary
            ans.add(tempo.get(i));
        }
    }

    //top view of binary tree
    //https://practice.geeksforgeeks.org/problems/top-view-of-binary-tree/1
    public ArrayList<Integer> topView(TreeNode root)
    {
        ArrayList<Integer> ans=new ArrayList<>();
        if(root==null){
            return ans;
        }
        Queue<Pair> q=new LinkedList<>(); //queue will store the references of the pair class
        q.add(new Pair(root, 0));
        Map<Integer, Integer> map=new TreeMap<>();
        while(!q.isEmpty()){
            Pair pair=q.poll();
            if(pair.node.left!=null){
                q.add(new Pair(pair.node.left,pair.line-1));
            }
            if(pair.node.right!=null){
                q.add(new Pair(pair.node.right,pair.line+1));
            }
            if(!map.containsKey(pair.line)){
                map.put(pair.line, pair.node.value);
            }
        }
        for(int key:map.keySet()){
            ans.add(map.get(key));
        }
        return ans;
    }

    //bottom view of binary tree
    //https://practice.geeksforgeeks.org/problems/bottom-view-of-binary-tree/1
    public ArrayList <Integer> bottomView(TreeNode root)
    {
        ArrayList<Integer> ans=new ArrayList<>();
        if(root==null){
            return ans;
        }
        Queue<Pair> q=new LinkedList<>(); //queue will store the references of the pair class
        q.add(new Pair(root, 0));
        Map<Integer, Integer> map=new TreeMap<>();
        while(!q.isEmpty()){
            Pair pair=q.poll();
            if(pair.node.left!=null){
                q.add(new Pair(pair.node.left,pair.line-1));
            }
            if(pair.node.right!=null){
                q.add(new Pair(pair.node.right,pair.line+1));
            }
                map.put(pair.line, pair.node.value);
        }
        for(int key:map.keySet()){
            ans.add(map.get(key));
        }
        return ans;
    }

    //right view of binary tree
    //https://practice.geeksforgeeks.org/problems/right-view-of-binary-tree/1
    ArrayList<Integer> rightView(TreeNode root) {
        ArrayList<Integer> ans=new ArrayList<>();
        rightSideView(root, ans, 0);
        return ans;
    }
    public void rightSideView(TreeNode root, ArrayList<Integer> ans, int level){
        if(root==null){
            return;
        }
        if(level==ans.size()){
            ans.add(root.value);
        }
        rightSideView(root.right, ans, level+1); //reverse pre order traversal
        rightSideView(root.left, ans, level+1);
    }

    //left view of binary tree
    //https://practice.geeksforgeeks.org/problems/left-view-of-binary-tree/1
    ArrayList<Integer> leftView(TreeNode node) {
        ArrayList<Integer> ans=new ArrayList<>();
        leftSideView(node, ans, 0);
        return ans;
    }
    public void leftSideView(TreeNode node, ArrayList<Integer> ans, int level){
        if(node==null){
            return;
        }
        if(level==ans.size()){
            ans.add(node.value);
        }
        leftSideView(node.left, ans, level+1); //pre order traversal
        leftSideView(node.right, ans, level+1);
    }
    
    //symmetrical binary tree
    //https://leetcode.com/problems/symmetric-tree/
    public boolean isSymmetric(TreeNode root) {
        return root==null||helper(root.left, root.right);
    }
    public boolean helper(TreeNode leftSubRoot, TreeNode rightSubRoot){
        if(leftSubRoot==null||rightSubRoot==null){
            return leftSubRoot==rightSubRoot;
        }
        if(leftSubRoot.value!=rightSubRoot.value){
            return false;
        }
        //checking each left node of left subtree with each right node of right subtree and vice versa
        return helper(leftSubRoot.left, rightSubRoot.right)&&helper(leftSubRoot.right, rightSubRoot.left);
    }

    //root to node path
    //
    private boolean getPath(TreeNode root, ArrayList<Integer> ans, int target){
        if(root==null){
            return false;
        }
        ans.add(root.value);
        if(root.value==target){
            return true;
        }
        if (getPath(root.left,ans,target)||getPath(root.right,ans,target)){
            return true;
        }
        ans.remove(ans.size()-1);
        return false;
    }
    public ArrayList<Integer> pathToNode(TreeNode root, int target){
        ArrayList<Integer> ans=new ArrayList<>();
        if(root==null){
            return ans;
        }
        getPath(root,ans,target);
        return ans;
    }

}

