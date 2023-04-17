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
        // we use tree map in order to sort the items along with their keys. First item is the vertical ie column ie -2,-1,0,1,2 etc and the second item is another treemap in which the first item stores the level and second item is a pq to store all those nodes which occur at the same row and col
        Queue<Triad> q=new LinkedList();
        q.offer(new Triad(root,0,0)); //node,vertical,level
        while(!q.isEmpty()){
            Triad t=q.poll();
            TreeNode node=t.first;
            int vertical=t.second;
            int level=t.third;
            if(node.left!=null){
                q.offer(new Triad(node.left,vertical-1,level+1));
            }
            if(t.first.right!=null){
                q.offer(new Triad(node.right,vertical+1,level+1));
            }
            if(!map.containsKey(vertical)){
                map.put(vertical,new TreeMap<>());
            }
            if(!map.get(vertical).containsKey(level)){
                map.get(vertical).put(level,new PriorityQueue<>());
            }
            map.get(vertical).get(level).add(node.val);
        }
        for(TreeMap<Integer,PriorityQueue<Integer>> mp:map.values()){ //doubt
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


}