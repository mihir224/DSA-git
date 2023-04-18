import com.sun.source.tree.Tree;

import java.util.*;
import java.util.LinkedList;
import java.util.concurrent.TransferQueue;

class BinaryTree {
    TreeNode root;
    public BinaryTree(){
        this.root=null;
    }
    class TreeNode{
        TreeNode left;
        TreeNode right;
        int val;
        public TreeNode(int val){
            this.val=val;
            this.left=this.right=null;
        }
    }
    //inorder traversal - left,root,right
    //https://leetcode.com/problems/binary-tree-inorder-traversal/
    public List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> st=new Stack<>();
        List<Integer> list=new ArrayList<>();
        TreeNode node=root;
        while(true){
            if(node!=null){ //going extreme left
                st.push(node);
                node=node.left;
            }
            else{
                if(st.isEmpty()){
                    break;
                }
                node=st.pop();
                list.add(node.val);
                node=node.right;
            }
        }
        return list;
    }

    //preorder - root,left,right
    //https://leetcode.com/problems/binary-tree-preorder-traversal
    public List<Integer> preorderTraversal(TreeNode root) {
        if(root==null){
            return new ArrayList<>();
        }
        Stack<TreeNode> st=new Stack<>();
        List<Integer> list=new ArrayList<>();
        st.push(root);
        while(!st.isEmpty()){
            int n=st.size();
            for(int i=0;i<n;i++) {
                TreeNode node = st.pop();
                if (node.right != null) {
                    st.push(node.right);
                }
                if (node.left != null) {
                    st.push(node.left);
                }
                list.add(node.val);
            }
        }
        return list;
    }

    //postorder - left,right,root
    //https://leetcode.com/problems/binary-tree-postorder-traversal/

    //using a single stack
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list=new ArrayList<>();
        Stack<TreeNode> st=new Stack<>();
        TreeNode node=root;
        while(node!=null||!st.isEmpty()){
            if(node!=null){
                st.push(node);
                node=node.left;
            }
            else{
                TreeNode temp=st.peek().right;
                if(temp==null){ //top of stack has no right
                    temp=st.pop();
                    list.add(temp.val);
                    while(!st.isEmpty()&&st.peek().right==temp){ //ie temp is the right of stack's top
                        temp=st.pop();
                        list.add(temp.val);
                    }
                }
                else
                    node=temp;
            }
        }
        return list;
    }

    //morris traversal - allows us to do traversals w/o using stacks, queues and thus reducing the space from O(N) to O(1)
    //https://leetcode.com/problems/binary-tree-inorder-traversal/
    //tc - amortised O(N)

    //inorder
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> list=new ArrayList<>();
        TreeNode current=root;
        while(current!=null){
            if(current.left==null){
                list.add(current.val);
                current=current.right;
            }
            else{
                TreeNode prev=current.left;
                while(prev.right!=null&&prev.right!=current){ //moving to the right most node of the left subtree
                    prev=prev.right;
                }
                if(prev.right==null){ //making thread b4 moving left
                    prev.right=current;
                    current=current.left; //moving left after establishing thread
                }
                else{ //thread already exists ie we came back to root
                    prev.right=null;
                    list.add(current.val);
                    current=current.right;
                }
            }
        }
        return list;
    }

    //preorder
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> list=new ArrayList<>();
        TreeNode current=root;
        while(current!=null){
            if(current.left==null){
                list.add(current.val);
                current=current.right;
            }
            else{
                TreeNode prev=current.left;
                while(prev.right!=null&&prev.right!=current){ //moving to the right most node of the left subtree
                    prev=prev.right;
                }
                if(prev.right==null){ //making thread b4 moving left
                    prev.right=current;
                    list.add(current.val);
                    current=current.left; //moving left after establishing thread
                }
                else{ //thread already exists ie we came back to root
                    prev.right=null;
                    current=current.right;
                }
            }
        }
        return list;
    }

    //left view
    //https://practice.geeksforgeeks.org/problems/left-view-of-binary-tree/1
    ArrayList<Integer> leftView(TreeNode root)
    {
        ArrayList<Integer> ans=new ArrayList<>();
        helper(root,0,ans);
        return ans;
    }
    public void helper(TreeNode root, int level, ArrayList<Integer> list){
        if(root==null){
            return;
        }
        if(level==list.size()){ //first occurence of a node at a level
            list.add(root.val);
        }
        helper(root.left,level+1,list);
        helper(root.right,level+1,list);
    }

    //bottom view
    //https://practice.geeksforgeeks.org/problems/bottom-view-of-binary-tree/1
    public ArrayList <Integer> bottomView(TreeNode root)
    {
        Map<Integer,Integer> map=new TreeMap<>();
        ArrayList<Integer> list=new ArrayList<>();
        Queue<Pair> q=new LinkedList<>();
        q.add(new Pair(root,0));
        while(!q.isEmpty()){
            Pair p=q.poll();
            map.put(p.second,p.first.val);
            if(p.first.left!=null){
                q.add(new Pair(p.first.left,p.second-1));
            }
            if(p.first.right!=null){
                q.add(new Pair(p.first.right,p.second+1));
            }
        }
        for(int i: map.keySet()){
            list.add(map.get(i));
        }
        return list;
    }

    //top view
    //https://practice.geeksforgeeks.org/problems/top-view-of-binary-tree/1
    static ArrayList<Integer> topView(TreeNode root)
    {
        Map<Integer,Integer> map=new TreeMap<>(); // we use a tree map because it sorts the elements wrt their keys and thus it will help us to obtain the ans
        Queue<Pair> q=new LinkedList<>();
        q.add(new Pair(root,0));//first is node, second is its line number
        while(!q.isEmpty()){
            Pair p=q.poll();
            if(!map.containsKey(p.second)){
                map.put(p.second,p.first.val);//first is line number, second is the node
            }
            if(p.first.left!=null){
                q.add(new Pair(p.first.left, p.second-1));
            }
            if(p.first.right!=null){
                q.add(new Pair(p.first.right,p.second+1));
            }
        }
        ArrayList<Integer> list=new ArrayList<>();
        for(int i:map.keySet()){
            list.add(map.get(i));
        }
        return list;
    }

    //preorder,postorder,inorder in one solution
    //https://www.codingninjas.com/codestudio/problems/981269?topList=striver-sde-sheet-problems&utm_source=striver&utm_medium=website

    //intuition is that we update the numbers of each element of the stack in such a away that the node with num1 will always belong to preorder, node with num2 will always belong to inorder and node with num 3 will always belong to postorder. This is because as we move through the preorder, the number we push back into the stack belongs to the inorder and the number that we push back to the stack from inorder belongs to the post order.
    static class Pair{
        TreeNode first;
        int second;
        public Pair(TreeNode first, int second){
            this.first=first;
            this.second=second;
        }
    }
    public static List<List<Integer>> getTreeTraversal(TreeNode root) {
        if(root==null){
            return new ArrayList<>();
        }
        Stack<Pair> stack=new Stack<>();
        stack.push(new Pair(root,1));

        List<Integer> preorder=new ArrayList<>();
        List<Integer> inorder=new ArrayList<>();
        List<Integer> postorder=new ArrayList<>();
        List<List<Integer>> ans=new ArrayList<>();
        while(!stack.isEmpty()){
            Pair p=stack.pop();
            if(p.second==1){
                preorder.add(p.first.val);
                p.second++;
                stack.push(p);
                if(p.first.left!=null){
                    stack.push(new Pair(p.first.left,1));
                }
            }
            else if(p.second==2){
                inorder.add(p.first.val);
                p.second++;
                stack.push(p);
                if(p.first.right!=null){
                    stack.push(new Pair(p.first.right,1));
                }
            }
            else{
                postorder.add(p.first.val);
            }
        }
        ans.add(inorder);
        ans.add(preorder);
        ans.add(postorder);
        return ans;
    }

    //vertical order traversal
    //https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/
    //O(N for traversal*logN for PQ)

    //We traverse the tree (using any traversal - I've used level order). In the queue for each node we store a traid of
    //  node itself, its vertical number ie -2,-1,0,1,2 etc, and its level. To store the vertical order traversal, we use
    //  a treemap with an Integer key and another treemap as its value. The key of this map is the vertical number and the
    //  inner tree map holds two things - the level as key and a Priority Queue as value. This priority queue will make
    //  sure that the node at the same vertical and same level are aligned in a sorted manner in the vertical order traversal.
    //  For each vertical we have different levels(as keys of the inner tree map) and for each level we mark different
    //  nodes in the PQ. // we use tree map in order to sort the items along with their keys. First item is the vertical ie column ie
    // -2,-1,0,1,2 etc and the second item is another treemap in which the first item stores the level and second
    // item is a pq to store all those nodes which occur at the same row and col. Intially we insert the root with
    // vertical and level 0 into the queue. Then we iterate over the queue in a level order fashion. In each iteration,
    // we check if the map contains the vertical of the respective node that we just pulled out from the queue and if it doesn't,
    // add it to the map with a new TreeMap as its value. Then we check if the level of the respective node at its vertical
    // exists inside the treemap of that vertical or not. If it doesn't we add it to the vertical's treemap with a new
    // Priority Queue as its value. Then we simply go left (add to q node.left, vertical-1, level+1) and then right
    // (add to q node.right, vertical+1, level+1) if possible. This is repeated till the q becomes empty ie we've traversed all nodes.
    // Then, we simply iterate over the different tree maps of all the verticals inside the main tree map. In each iteration
    // we add a new list to the list of lists storing the vertical order traversal for each vertical. Inside each inner treemap,
    // we iterate over the priority queue on each level and add empty it into the last list that was added in the list of lists.
    class Triad{
        TreeNode first;
        int second;
        int third;
        public Triad(TreeNode first,int second,int third){
            this.first=first;
            this.second=second; //col ie vertical
            this.third=third; //row ie level
        }
    }
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> ans=new ArrayList<>();
        TreeMap<Integer,TreeMap<Integer,PriorityQueue<Integer>>> map=new TreeMap<>();  //vertical,map<level,pq>

        Queue<Triad> q=new LinkedList();
        q.offer(new Triad(root,0,0)); //node,vertical,level
        while(!q.isEmpty()){
            Triad t=q.poll();
            TreeNode node=t.first;
            int vertical=t.second;
            int level=t.third;
            if(!map.containsKey(vertical)){
                map.put(vertical,new TreeMap<>());
            }
            if(!map.get(vertical).containsKey(level)){
                map.get(vertical).put(level,new PriorityQueue<>());
            }
            if(node.left!=null){
                q.offer(new Triad(node.left,vertical-1,level+1));
            }
            if(t.first.right!=null){
                q.offer(new Triad(node.right,vertical+1,level+1));
            }
            map.get(vertical).get(level).add(node.val);
        }
        for(TreeMap<Integer,PriorityQueue<Integer>> mp:map.values()){
            ans.add(new ArrayList<>());
            for(PriorityQueue<Integer> pq:mp.values()){
                while(!pq.isEmpty()){
                    ans.get(ans.size()-1).add(pq.poll()); //ans.size()-1 because in each iteration we are adding a new list and thus to access that list we do ans.size()-1 since it is the last list that was added
                }
            }
        }
        return ans;
     }

    //root to node path
    //https://www.interviewbit.com/problems/path-to-given-node/
    //O(N) space and time as we're traversing in N nodes (case when distance between root and node is N)

    public int[] solve(TreeNode A, int B) {
        ArrayList<Integer> list=new ArrayList<>();
        helper2(A,B,list);
        int[] ans=new int[list.size()];
        for(int i=0;i<list.size();i++){
            ans[i]=list.get(i);
        }
        return ans;
    }
    public boolean helper2(TreeNode node,int target,ArrayList<Integer> ans){
        if(node==null){
            return false;
        }
        ans.add(node.val);
        if(node.val==target) {
            return true;
        }
        if(helper2(node.left,target,ans)||helper2(node.right,target,ans)){
            return true;
        }
        ans.remove(ans.size()-1); //to try out other possibilities
        return false;
    }


    //max width of the binary tree
    //https://leetcode.com/problems/maximum-width-of-binary-tree/
    //O(N)time, since level order traversal O(N)space, since we use a queue DS
    public int widthOfBinaryTree(TreeNode root) {
        if(root==null){
            return 0;
        }
        Queue<Pair> q=new LinkedList<>();
        int ans=0;
        q.offer(new Pair(root,0));
        while(!q.isEmpty()){
            int n=q.size();
            int min=q.peek().second; //min index at each level (can be different in cases where the first node is null)
            int first=0;
            int last=0;
            for(int i=0;i<n;i++){
                Pair p=q.poll();
                int currentIndex=p.second-min;
                TreeNode node=p.first;
                if(i==0){
                    first=currentIndex;
                }
                if(i==n-1){
                    last=currentIndex;
                }
                if(node.left!=null){
                    q.offer(new Pair(node.left,2*currentIndex+1));
                }
                if(node.right!=null){
                    q.offer(new Pair(node.right,2*currentIndex+2));
                }
            }
            ans=Math.max(ans,last-first+1);
        }
        return ans;
    }

    //height of binary tree
    //https://leetcode.com/problems/maximum-depth-of-binary-tree/
    //O(N) time, O(N) auxiliary stack space
    public int maxDepth(TreeNode root) {
        if(root==null){
            return 0;
        }
        return 1+Math.max(maxDepth(root.left),maxDepth(root.right));
    }

    //level order traversal
    //https://leetcode.com/problems/binary-tree-level-order-traversal/
    //O(N)
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans=new ArrayList<>();
        Queue<TreeNode> q=new LinkedList<>();
        q.add(root);
        if(root==null){
            return ans;
        }
        while(!q.isEmpty()){
            int n=q.size();
            List<Integer> list=new ArrayList<>();
            for(int i=0;i<n;i++){
                TreeNode node=q.poll();
                list.add(node.val);
                if(node.left!=null){
                    q.add(node.left);
                }
                if(node.right!=null){
                    q.add(node.right);
                }
            }
            ans.add(list);
        }
        return ans;
    }

    //diameter of a binary tree
    //https://leetcode.com/problems/diameter-of-binary-tree/
    //O(N)
    public int diameterOfBinaryTree(TreeNode root) {
        int[] diameter=new int[1]; //we use an array coz java does not support pass by reference and simply passing the
        // variable diameter would directly copy its value and not change it in further iterations
        findHeight(root,diameter);
        return diameter[0];
    }
    public int findHeight(TreeNode node, int[] max){
        if(root==null){
            return 0;
        }
        int lh=findHeight(node.left,max);
        int rh=findHeight(node.right,max);
        max[0]=Math.max(max[0],lh+rh);
        return 1+Math.max(lh,rh);
    }

    //balanced binary tree
    //https://leetcode.com/problems/balanced-binary-tree/
    //O(N)
    public boolean isBalanced(TreeNode root) {
        return height(root)==-1?false:true;
    }
    public int height(TreeNode root){
        if(root==null){
            return 0;
        }
        int lh=height(root.left);
        int rh=height(root.right);
        if(lh==-1||rh==-1||Math.abs(lh-rh)>1){
            return -1;
        }
        return 1+Math.max(lh,rh);
    }

    //lowest common ancestor
    //https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree
    //Brute takes O(2N)
    //O(N) time and space
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null||root==p||root==q){
            return root;
        }
        TreeNode left=lowestCommonAncestor(root.left,p,q);
        TreeNode right=lowestCommonAncestor(root.right,p,q);
        if(left==null){
            return right;
        }
        if(right==null){
            return left;
        }
        return root; //both left and right not null meaning that we reached both p and q from current root
    }

    //same tree
    //https://leetcode.com/problems/same-tree/
    //O(N+M) - traversing n nodes of and m nodes of q
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null||q==null){
            return p==q;
        }
        return p.val==q.val&&isSameTree(p.left,q.left)&&isSameTree(p.right,q.right);
    }

    //zig zag traversal
    //https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans=new ArrayList<>();
        List<Integer> list=new ArrayList<>();
        if(root==null){
            return ans;
        }
        boolean flag=false;
        Queue<TreeNode> q=new LinkedList<>();
        q.offer(root);
        while(!q.isEmpty()){
            int n=q.size();
            for(int i=0;i<n;i++){
                TreeNode node=q.poll();
                if(node.left!=null){
                    q.add(node.left);
                }
                if(node.right!=null){
                    q.offer(node.right);
                }
                if(!flag){
                    list.add(node.val);
                }
                else{
                    list.add(0,node.val);
                }
                flag=!flag;
            }
            ans.add(list);
        }
        return ans;
    }

    //boundary traversal
    //https://practice.geeksforgeeks.org/problems/boundary-traversal-of-binary-tree/1
    //O(3N - left boundary, leaves, right boundary)
    ArrayList <Integer> boundary(TreeNode root)
    {
        ArrayList<Integer> ans=new ArrayList<>();
        if(!isLeaf(root)){
            ans.add(root.val);
        }
        addLeftBoundary(root,ans);
        addLeaves(root,ans);
        addRightBoundary(root,ans);
        return ans;
    }
    public void addLeftBoundary(TreeNode root, ArrayList<Integer> ans){
        TreeNode temp=root.left;
        while(temp!=null){
            if(!isLeaf(temp)){
                ans.add(temp.val);
            }
            if(temp.left!=null){
                temp=temp.left;
            }
            else{
                temp=temp.right; //moving right when we can no longer move left
            }
        }
    }
    public void addLeaves(TreeNode root, ArrayList<Integer> ans){
        if(isLeaf(root)){
            ans.add(root.val);
        }
        if(root.left!=null){
            addLeaves(root.left,ans);
        }
        if(root.right!=null){
            addLeaves(root.right,ans);
        }
    }
    public void addRightBoundary(TreeNode root,ArrayList<Integer> ans){
        TreeNode temp=root.right;
        ArrayList<Integer> t=new ArrayList<>();
        while(temp!=null){
            if(!isLeaf(temp)){
                t.add(temp.val);
            }
            if(temp.right!=null){
                temp=temp.right;
            }
            else{
                temp=temp.left; //moving left when we can no longer go right
            }
        }
        for(int i=t.size()-1;i>=0;i--){
            ans.add(t.get(i));
        }
    }
    public boolean isLeaf(TreeNode root){
        return root.left==null&&root.right==null;
    }
}