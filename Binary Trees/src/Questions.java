import com.sun.source.tree.Tree;

import java.util.*;

public class Questions {
    TreeNode prev=null;
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

        public TreeNode() {
        }

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
    //https://www.interviewbit.com/problems/path-to-given-node/#_=_
    private boolean getPath(TreeNode root, ArrayList<Integer> ans, int target){
        if(root==null){
            return false;
        }
        ans.add(root.value);
        if(root.value==target){
            return true;
        }
        if (getPath(root.left,ans,target)||getPath(root.right,ans,target)){ //if anyone returns true, we've found a path
            return true;
        }
        ans.remove(ans.size()-1);
        return false;
    }
    public int[] solve(TreeNode root, int target){
        ArrayList<Integer> ansList=new ArrayList<>();
        getPath(root,ansList,target);
        int[] ans=new int[ansList.size()];
        if(root==null){
            return ans;
        }
        for (int i=0;i<ans.length;i++){
            ans[i]=ansList.get(i);
        }
        return ans;
    }

    //lowest common ancestor
    //https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root==null||root==p||root==q){
            return root;
        }
        TreeNode left=lowestCommonAncestor(root.left, p, q);
        TreeNode right=lowestCommonAncestor(root.right, p, q);
        if(left==null){ //take right if left is returning null even if right is also null
            return right;
        }
        else if(right==null){ //take left if right is returning null even if left is also null
            return left;
        }
        else{ //both left and right are not null
            return root;
        }
    }

    //max width of a binary tree
    //https://leetcode.com/problems/maximum-width-of-binary-tree/

    class Pair2{
        private TreeNode node;
        private int index;
        public Pair2() {
        }
        public Pair2(TreeNode node, int index) {
            this.node = node;
            this.index = index;
        }
    }
    public int widthOfBinaryTree(TreeNode root) {
        if (root==null){
            return 0;
        }
        int ans=0;
        Queue<Pair2> q=new LinkedList<>();
        q.offer(new Pair2(root, 0));
        while(!q.isEmpty()){
            int n=q.size();
            int first=0;
            int last=0;
            int min=q.peek().index; //min index of each level since queue is fifo
            for(int i=0;i<n;i++){
                int currentIndex=q.peek().index-min; //to avoid overflow
                TreeNode node=q.poll().node;
                if(i==0){
                    first=currentIndex; //storing the first node index at each level
                }
                if(i==n-1){
                    last=currentIndex; //storing the last node index at each level
                }
                if(node.left!=null){
                    q.offer(new Pair2(node.left, currentIndex*2+1));
                }
                if(node.right!=null){
                    q.offer(new Pair2(node.right,currentIndex*2+2));
                }
            }
            ans=Math.max(ans, last-first+1); //max width among all levels
        }
        return ans;
    }

    //convert to children sum tree
    public void toChildrenSum(TreeNode root){
        if(root==null){
            return;
        }
        int initialSum=0;
        if(root.left!=null){
            initialSum+=root.left.value;
        }
        if(root.right!=null){
            initialSum+=root.right.value;
        }
        if(initialSum>=root.value){
            root.value=initialSum;
        }
        else{
            if(root.left!=null){
                root.left.value=root.value;
            }
            if(root.right!=null){
                root.right.value=root.value;
            }
        }
        toChildrenSum(root.left);
        toChildrenSum(root.right);
        int sum=0;
        if(root.left!=null){
            sum+=root.left.value;
        }
        if(root.right!=null){
            sum+=root.right.value;
        }
        if(root.left!=null||root.right!=null){
            root.value=sum;
        }
    }

    //all nodes distance k
    //https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
    private void markParents(TreeNode root,Map<TreeNode,TreeNode> parentMap){
        Queue<TreeNode> q=new LinkedList<>();
        q.offer(root);
        while(!q.isEmpty()){
            TreeNode node=q.poll();
            if(node.left!=null){
                parentMap.put(node.left, node);
                q.offer(node.left);
            }
            if(node.right!=null){
                parentMap.put(node.right, node);
                q.offer(node.right);
            }
        }
    }

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        Map<TreeNode,TreeNode> parentMap=new HashMap<>();
        markParents(root, parentMap);
        Queue<TreeNode> q=new LinkedList<>();
        List<Integer> ans=new ArrayList<>();
        Map<TreeNode, Boolean> visitedNode=new HashMap<>();
        q.offer(target);
        visitedNode.put(target,true);

        int distance=0;
        while(!q.isEmpty()){
            int n=q.size();
            if(distance==k){
                break;
            }
            distance++;
            for(int i=0;i<n;i++){
                TreeNode node=q.poll();
                if(node.left!=null&&visitedNode.get(node.left)==null){ //moving radially downwards. Also, when a boolean
                    //value is null it is neither true nor false
                    q.offer(node.left);
                    visitedNode.put(node.left,true);
                }
                if(node.right!=null&&visitedNode.get(node.right)==null){ //moving radially downwards
                    q.offer(node.right);
                    visitedNode.put(node.right,true);
                }
                if(parentMap.get(node)!=null&&visitedNode.get(parentMap.get(node))==null){ //moving radially upwards if parent exists
                    q.offer(parentMap.get(node));
                    visitedNode.put(parentMap.get(node),true);
                }
            }
        }
        while (!q.isEmpty()){
            for(int i=0;i<q.size();i++){
                ans.add(i,q.poll().value);
            }
        }
        return ans;
    }
    //min time taken to burn the binary tree
    //https://practice.geeksforgeeks.org/problems/burning-tree/1

    private static TreeNode markParents1(TreeNode root, Map<TreeNode, TreeNode> parentMap, int target){
        Queue<TreeNode> q=new LinkedList<>();
        q.offer(root);
        TreeNode foundNode=null;
        while(!q.isEmpty()){
            TreeNode node=q.poll();
            if(node.value==target){
                foundNode=node;
            }
            if(node.left!=null){
                parentMap.put(node.left, node);
                q.offer(node.left);
            }
            if(node.right!=null){
                parentMap.put(node.right, node);
                q.offer(node.right);
            }
        }
        return foundNode;
    }
    public static int findMaxDistance(Map<TreeNode, TreeNode> parentMap, TreeNode targetNode){
        Queue<TreeNode> q=new LinkedList<>();
        Map<TreeNode, Boolean> visitedNode=new HashMap<>();
        q.offer(targetNode);
        visitedNode.put(targetNode,true);
        int distance=0;
        while(!q.isEmpty()){
            int n=q.size();
            int flag=0; //to check if the current node is capable of burning any other nodes
            for(int i=0;i<n;i++){
                TreeNode node=q.poll();
                if(node.left!=null&&visitedNode.get(node.left)==null){ //moving radially downwards. Also, when a boolean
                    //value is null it is neither true nor false
                    flag=1;
                    q.offer(node.left);
                    visitedNode.put(node.left,true);
                }
                if(node.right!=null&&visitedNode.get(node.right)==null){ //moving radially downwards
                    flag=1;
                    q.offer(node.right);
                    visitedNode.put(node.right,true);
                }
                if(parentMap.get(node)!=null&&visitedNode.get(parentMap.get(node))==null){ //moving radially upwards if parent exists
                    flag=1;
                    q.offer(parentMap.get(node));
                    visitedNode.put(parentMap.get(node),true);
                }
            }
            if(flag==1){
                distance++;
            }
        }
        return distance;
    }
    public static int minTime(TreeNode root, int target)
    {
        Map<TreeNode,TreeNode> parentMap=new HashMap<>();
        TreeNode targetNode=markParents1(root, parentMap,target);
        int time=findMaxDistance(parentMap, targetNode);
        return time;
    }

    //count complete binary tree nodes
    //https://leetcode.com/problems/count-complete-tree-nodes/
    public int countNodes(TreeNode root) {
        if(root==null){
            return 0;
        }
        int lh=findLeftHeight(root);
        int rh=findRightHeight(root);
        if(lh==rh){
            return (int)Math.pow(2,lh)-1;
        }
        return 1+countNodes(root.left)+countNodes(root.right);
    }
    public int findLeftHeight(TreeNode root){
        int count=0;
        while(root!=null){
            count++;
            root=root.left;
        }
        return count;
    }
    public int findRightHeight(TreeNode root){
        int count=0;
        while(root!=null){
            count++;
            root=root.right;
        }
        return count;
    }

    //construct a binary tree from pre-order and inorder traversal
    //https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inOrderMap=new HashMap<>();
        for(int i=0;i<inorder.length;i++){
            inOrderMap.put(inorder[i],i);
        }
        TreeNode root=buildTree1(preorder, 0, preorder.length-1, inorder, 0,inorder.length-1, inOrderMap);
        return root;
    }
    public TreeNode buildTree1(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd, Map<Integer, Integer> inOrderMap){
        if(preStart>preEnd||inStart>inEnd){
            return null;
        }
        TreeNode root=new TreeNode(preorder[preStart]);
        int inRootIndex=inOrderMap.get(root.value);
        int x=inRootIndex-inStart;
        root.left=buildTree1(preorder, preStart+1, preStart+x, inorder, inStart, inRootIndex-1, inOrderMap);
        root.right=buildTree1(preorder, preStart+x+1, preEnd, inorder, inRootIndex+1, inEnd, inOrderMap);
        return root;
    }

    //construct binary tree from in order and post-order traversal
    //https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
    public TreeNode buildTree3(int[] inorder, int[] postorder) {
        Map<Integer, Integer> inOrderMap=new HashMap<>();
        for(int i=0;i<inorder.length;i++){
            inOrderMap.put(inorder[i],i);
        }
        TreeNode root=buildTree4(postorder, 0, postorder.length-1, inorder, 0,inorder.length-1, inOrderMap);
        return root;
    }
    public TreeNode buildTree4(int[] postorder, int postStart, int postEnd, int[] inorder, int inStart, int inEnd, Map<Integer, Integer> inOrderMap){
        if(postStart>postEnd||inStart>inEnd){
            return null;
        }
        TreeNode root=new TreeNode(postorder[postEnd]);
        int inRootIndex=inOrderMap.get(root.value);
        int x=inRootIndex-inStart;
        root.left=buildTree4(postorder, postStart, postStart+x, inorder, inStart, inRootIndex-1, inOrderMap);
        root.right=buildTree4(postorder, postStart+x+1, postEnd, inorder, inRootIndex+1, inEnd, inOrderMap);
        return root;
    }

    //serialize deserialize
    //https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
    public String serialize(TreeNode root) {
        Queue<TreeNode> q=new LinkedList<>();
        StringBuilder ans=new StringBuilder();
        q.offer(root);
        while(!q.isEmpty()){
            TreeNode node=q.poll();
            if(node==null){
                ans.append("NULL ");
                continue;
            }
            ans.append(node.value + " ");
            q.add(root.left);
            q.add(root.right);
        }
        return ans.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) { //we apply the reverse logic of what we applied in serialize
        if(data==""){
            return null;
        }
        Queue<TreeNode> q=new LinkedList<>();
        String[] strArray=data.split(" ");
        TreeNode root=new TreeNode(Integer.parseInt(strArray[0])); //we know for sure that root can never be null
        q.add(root);
        for(int i=1;i<strArray.length;i++){
            TreeNode node=q.poll();
            if(!strArray[i].equals("NULL")){
                TreeNode left=new TreeNode(Integer.parseInt(strArray[i])); { //forming the left of root node
                    node.left=left;
                    q.add(left);
                }
            }
            if(!strArray[++i].equals("NULL")){ //if the next of left ie right is not null we attach it to the tree
                TreeNode right=new TreeNode(Integer.parseInt(strArray[i])); { //forming the left of root node
                    node.right=right;
                    q.add(right);
                }

            }
        }
        return root;
    }

    //morris traversal
    //inorder
    public ArrayList<Integer> morrisInOrder(TreeNode root){
        ArrayList<Integer> list=new ArrayList<>();
        TreeNode node=root;
        while(node!=null){
            if(node.left==null){
                list.add(node.value);
                node=node.right;
            }
            else{
                TreeNode prev=node.left; //left subtree
                if(prev.right!=null&&prev.right!=node){ //check if thread already exists and if right is null
                    prev=prev.right;
                }
                else{
                    if(prev.right==null){ //reached right most node of the left subtree, thus creating a thread and moving left
                        prev.right=node;
                        node=node.left;
                    }
                    else{ //thread already exists, thus remove it, print node and move right
                        prev.right=null;
                        list.add(node.value);
                        node=node.right;
                    }
                }
            }
        }
        return list;
    }

    //flatten a binary tree to linked list
    //https://leetcode.com/problems/flatten-binary-tree-to-linked-list/submissions/

    //recursive
    public void flatten(TreeNode root) {
        if(root==null){
            return;
        }
        flatten(root.right);
        flatten(root.left);
        root.right=prev;
        root.left=null;
        prev=root;
    }

    //using stacks
    public void flatten2(TreeNode root) {
        if(root==null){
            return;
        }
        Stack<TreeNode> stack=new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode node=stack.pop();
            if(node.right!=null){
                stack.push(node.right);
            }
            if(node.left!=null){
                stack.push(node.left);
            }
            node.right=stack.peek();
            node.left=null;
        }
    }
}





