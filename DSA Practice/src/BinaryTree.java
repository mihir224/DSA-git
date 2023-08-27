import java.util.*;

class BinaryTree{
    TreeNode root;
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

    //traversals
    //inorder
    //https://leetcode.com/problems/binary-tree-inorder-traversal/

    //recursive
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list=new ArrayList<>();
        helperInorder(root,list);
        return list;
    }
    public void helperInorder(TreeNode root, List<Integer> list){
        if(root==null){
            return;
        }
        helperInorder(root.left,list);
        list.add(root.val);
        helperInorder(root.right,list);
    }

    //iterative
    public List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> ans=new ArrayList<>();
        Stack<TreeNode> st=new Stack<>();
        TreeNode temp=root;
        while(true){
            if(temp!=null){
                st.push(temp);
                temp=temp.left;
            }else{
                if(st.isEmpty()){
                    break;
                }
                TreeNode node=st.pop();
                temp=node.right;
                ans.add(node.val);
            }
        }
        return ans;
    }

    //preorder
    //https://leetcode.com/problems/binary-tree-preorder-traversal/

    //recursive
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans=new ArrayList<>();
        helperPreorder(root,ans);
        return ans;
    }
    public void helperPreorder(TreeNode root, List<Integer> list){
        if(root==null){
            return;
        }
        list.add(root.val);
        helperPreorder(root.left,list);
        helperPreorder(root.right,list);
    }

    //iterative
    public List<Integer> preorderTraversal1(TreeNode root) {
        List<Integer> ans=new ArrayList<>();
        Stack<TreeNode> st=new Stack<>();
        if(root==null){
            return new ArrayList<>();
        }
        st.add(root);
        while(!st.isEmpty()){
            TreeNode temp=st.pop();
            ans.add(temp.val);
            if(temp.right!=null){
                st.push(temp.right);
            }
            if(temp.left!=null){
                st.push(temp.left);
            }
        }
        return ans;
    }

    //post order
    //https://leetcode.com/problems/binary-tree-postorder-traversal/

    //recursive
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list=new ArrayList<>();
        helperPostorder(root,list);
        return list;
    }
    public void helperPostorder(TreeNode root, List<Integer> list){
        if(root==null){
            return;
        }
        helperPostorder(root.left,list);
        helperPostorder(root.right,list);
        list.add(root.val);
    }

    //iterative (2 stacks)
    public List<Integer> postorderTraversal1(TreeNode root) {
        if(root==null){
            return new ArrayList<>();
        }
        List<Integer> list=new ArrayList<>();
        Stack<TreeNode> st1=new Stack<>();
        Stack<TreeNode> st2=new Stack<>();
        st1.push(root);
        while(!st1.isEmpty()){
            st2.push(st1.pop());
            if(st2.peek().left!=null){
                st1.push(st2.peek().left);
            }
            if(st2.peek().right!=null){
                st1.push(st2.peek().right);
            }
        }
        int n=st2.size();
        for(int i=0;i<n;i++){
            list.add(st2.pop().val);
        }

        return list;
    }

    //iterative using 1 stack
    public List<Integer> postorderTraversal2(TreeNode root) {
        if(root==null){
            return new ArrayList<>();
        }
        Stack<TreeNode> st=new Stack<>();
        List<Integer> list=new ArrayList<>();
        TreeNode current=root;
        while(current!=null||!st.isEmpty()){
            if(current!=null){ //moving left until we can't
                st.push(current);
                current=current.left;
            }
            else{
                TreeNode temp=st.peek().right;
                if(temp==null){ //right doesn't exist, we're at a root
                    temp=st.pop();
                    list.add(temp.val);
                    while(!st.isEmpty()&&temp==st.peek().right){ //moving to root if currently we're the right child
                        temp=st.pop();
                        list.add(temp.val);
                    }
                }
                else{
                    current=temp; //right exists thus we move right
                }
            }
        }
        return list;
    }

    //inorder,preorder and post order in one traversal (DRY RUN RECOMMENDED)
    //https://www.codingninjas.com/studio/problems/981269?topList=striver-sde-sheet-problems&utm_source=striver&utm_medium=website

    //we're picking elements in such a way that for each traversal, the stack is being ordered in the correct order
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
        List<Integer> inorder=new ArrayList<>();
        List<Integer> preorder=new ArrayList<>();
        List<Integer> postorder=new ArrayList<>();
        List<List<Integer>> ans=new ArrayList<>();
        Stack<Pair> st=new Stack<>();
        st.push(new Pair(root,1));
        while(!st.isEmpty()){
            Pair p=st.pop();
            if(p.second==1){ //pre order
                preorder.add(p.first.val);
                p.second++;
                st.push(p); //setting up the right ordering for inorder in stack.
                //if we treat this current node as root, then for preorder, we're supposed to go left
                if(p.first.left!=null){
                    st.push(new Pair(p.first.left,1));
                }
            }
            else if(p.second==2){ //inorder
                inorder.add(p.first.val);
                p.second++;
                st.push(p); //setting up the right ordering for post order in stack
                //if we treat this current node as root, then for inorder, we're supposed to go right
                if(p.first.right!=null){
                    st.push(new Pair(p.first.right,1));
                }
            }
            else { //num==3, ie post order
                postorder.add(p.first.val);
            }
        }
        ans.add(inorder);
        ans.add(preorder);
        ans.add(postorder);
        return ans;
    }

    //level order
    //https://leetcode.com/problems/binary-tree-level-order-traversal/
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans=new ArrayList<>();
        Queue<TreeNode> q=new LinkedList<>();
        q.offer(root);
        while(!q.isEmpty()){
            int n=q.size();
            List<Integer> list=new ArrayList<>();
            for(int i=0;i<n;i++){
                TreeNode node=q.poll();
                list.add(node.val);
                if(node.left!=null){
                    q.offer(node.left);
                }
                if(node.right!=null){
                    q.offer(node.right);
                }
            }
            ans.add(list);
        }
        return ans;
    }

    //morris traversal - inorder
    //O(N)time, O(1)space
    //dry run recommended
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> ans=new ArrayList<>();
        TreeNode node=root;
        while(node!=null){
            if(node.left==null){
                ans.add(node.val);
                node=node.right; //moving right after printing node (in case of the last node of the left subtree, it's left
                // would be null so this condition would be triggered and when we try moving right, it would take us to the
                // root of the tree to which our left subtree is attached to)
            }
            else{
                TreeNode temp=node.left; //this is a temp node which is used to check whether last node of left subtree is
                // connected to the root or not
                while(temp.right!=null&&temp.right!=node){ //moving to the last node of left subtree, and checking if thread exists
                    temp=temp.right;
                }
                if(temp.right==null){ //thread doesn't exist, thus forming it and then moving current node to left
                    temp.right=node;
                    node=node.left;
                }
                else{ //thread already exists, thus removing it, printing root and moving right
                    temp.right=null;
                    ans.add(node.val);
                    node=node.right;
                }
            }
        }
        return ans;
    }

    //morris traversal - preorder
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> ans=new ArrayList<>();
        TreeNode node=root;
        while(node!=null){
            if(node.left==null){
                ans.add(node.val); //adding current root to ans
                node=node.right;
            }
            else{ //can go left
                TreeNode temp=node.left;
                while(temp.right!=null&&temp.right!=node){ //moving to the last node of left subtree, and checking if
                    // thread already exists
                    temp=temp.right;
                }
                if(temp.right==null){ //thread doesn't exist, thus forming it and then moving current node to left
                    temp.right=node;
                    ans.add(node.val); //adding root before moving left
                    node=node.left;
                }
                else{ //thread already exists, thus removing it, printing root and moving right
                    temp.right=null;
                    node=node.right;
                }
            }
        }
        return ans;
    }

    //left view of binary tree
    //https://practice.geeksforgeeks.org/problems/left-view-of-binary-tree/1
    ArrayList<Integer> leftView(TreeNode root)
    {
        ArrayList<Integer> list=new ArrayList<>();
        helperLV(root,list,0);
        return list;
    }
    public void helperLV(TreeNode root,ArrayList<Integer> list,int level){
        if(root==null){
            return;
        }
        if(list.size()==level){
            list.add(root.val);
        }
        helperLV(root.left,list,level+1);
        helperLV(root.right,list,level+1);
    }

    //bfs
    //storing each node with its level
    ArrayList<Integer> leftVieqww(TreeNode root)
    {
        ArrayList<Integer> ans=new ArrayList<>();
        Queue<PairLV> q=new LinkedList<>();
        if(root==null){
            return new ArrayList<>();
        }
        q.add(new PairLV(root,0));
        while(!q.isEmpty()){
            PairLV p=q.poll();
            if(p.level==ans.size()){
                ans.add(p.node.val);
            }
            if(p.node.left!=null){
                q.add(new PairLV(p.node.left,p.level+1));
            }
            if(p.node.right!=null){
                q.add(new PairLV(p.node.right,p.level+1));
            }
        }

        return ans;
    }
    class PairLV{
        TreeNode node;
        int level;
        public PairLV(TreeNode node, int level){
            this.node=node;
            this.level=level;
        }

    }

    //bottom view of binary tree
    //https://practice.geeksforgeeks.org/problems/bottom-view-of-binary-tree/1
    public ArrayList <Integer> bottomView(TreeNode root)
    {
        TreeMap<Integer,TreeNode> map=new TreeMap<>();
        ArrayList<Integer> ans=new ArrayList<>();
        Queue<Pair> q=new LinkedList<>();
        q.offer(new Pair(root,0));
        while(!q.isEmpty()){
            Pair p=q.poll();
            TreeNode node=p.first;
            int line=p.second;
            if(node.left!=null){
                q.offer(new Pair(node.left,line-1));
            }
            if(node.right!=null){
                q.offer(new Pair(node.right,line+1));
            }
            map.put(line,node);
        }
        for(int key:map.keySet()){
            ans.add(map.get(key).val);
        }
        return ans;
    }

    //top view
    //https://practice.geeksforgeeks.org/problems/top-view-of-binary-tree/1
    public ArrayList <Integer> topView(TreeNode root)
    {
        TreeMap<Integer,TreeNode> map=new TreeMap<>();
        ArrayList<Integer> ans=new ArrayList<>();
        Queue<Pair> q=new LinkedList<>();
        q.offer(new Pair(root,0));
        while(!q.isEmpty()){
            Pair p=q.poll();
            TreeNode node=p.first;
            int line=p.second;
            if(node.left!=null){
                q.offer(new Pair(node.left,line-1));
            }
            if(node.right!=null){
                q.offer(new Pair(node.right,line+1));
            }
            if(!map.containsKey(line)){
                map.put(line,node);
            }
        }
        for(int key:map.keySet()){
            ans.add(map.get(key).val);
        }
        return ans;
    }

    //vertical order traversal
    //https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/

    //solved through level order traversal

    //O(N*logN*logN*logN) tc, O(N) sc
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> ans=new ArrayList<>();
        TreeMap<Integer,TreeMap<Integer,PriorityQueue>> map=new TreeMap<>(); //vertical,level,map. pq makes sure that
        // overlapping nodes (ie nodes at the same level) are added in a sorted manner for each level. we could've used a
        // tree set here but that would've discarded duplicate elements

        Queue<Triad> q=new LinkedList<>();
        q.offer(new Triad(root,0,0));
        while(!q.isEmpty()){
            Triad t=q.poll();
            TreeNode node=t.node;
            int vertical=t.vertical;
            int level=t.level;
            if(node.left!=null){
                q.offer(new Triad(node.left,vertical-1,level+1));
            }
            if(node.right!=null){
                q.offer(new Triad(node.right,vertical+1,level+1));
            }
            if(!map.containsKey(vertical)){ //vertical doesn't exist in the map, thus creating a new entry for it
                map.put(vertical,new TreeMap<>());
            }
            if(!map.get(vertical).containsKey(level)){ //level does not exist in the vertical's entry
                map.get(vertical).put(level,new PriorityQueue());
            }
            map.get(vertical).get(level).add(node.val);
        }
        //retrieving nodes level wise for each vertical (iterating over each vertical level wise) - bit tricky
        for(TreeMap<Integer,PriorityQueue> levelMap:map.values()){
            List<Integer> list=new ArrayList<>();
            for(PriorityQueue<Integer> pq:levelMap.values()){
                while(!pq.isEmpty()){
                    list.add(pq.poll());
                }
            }
            ans.add(list);
        }
        return ans;
    }
    class Triad{
        TreeNode node;
        int vertical;
        int level;
        public Triad(TreeNode node, int vertical, int level){
            this.node=node;
            this.vertical=vertical;
            this.level=level;
        }
    }

    //path from root to node
    //https://www.interviewbit.com/problems/path-to-given-node/
    public ArrayList<Integer> solve(TreeNode root, int target) {
        if(root==null) {
            return new ArrayList<>();
        }
        ArrayList<Integer> ans=new ArrayList<>();
        helperPFRN(root,ans,target);
        return ans;
    }
    public boolean helperPFRN(TreeNode root, ArrayList<Integer> ans, int target){
        if(root==null){ //reached end, couldn't find target
            return false;
        }
        //add current root to path
        ans.add(root.val);
        if(root.val==target){ //root is the target
            return true;
        }
        if(helperPFRN(root.left,ans,target)||helperPFRN(root.right,ans,target)){ //recursively checking left and right
            // subtrees for path to target
            return true;
        }
        //if we reached here, then it would mean there was no way we could reach target after current node & thus we remove
        // it to try out other nodes that might lead us to the path
        ans.remove(ans.size()-1);
        return false;
    }

    //max width of a binary tree (good question)
    //https://leetcode.com/problems/maximum-width-of-binary-tree/

    //we'll be traversing in level order
    //note - null nodes between end nodes count. null nodes at the end of a level do not count in the width of that level
    public int widthOfBinaryTree(TreeNode root) {
        Queue<Pair> q=new LinkedList<>();
        q.offer(new Pair(root,0));
        int width=Integer.MIN_VALUE;
        while(!q.isEmpty()){
            int n=q.size();
            int min=q.peek().second; //this min can vary, might not always be 1 (in case 1st node of a level is null)
            int first=0;
            int last=0;
            for(int i=0;i<n;i++){
                Pair p=q.poll();
                TreeNode node=p.first;
                int index=p.second;
                int currentIndex=index-min;
                if(i==0){
                    first=currentIndex;
                }
                if(i==n-1){
                    last=currentIndex;
                }
                if(node.left!=null){
                    q.offer(new Pair(node.left,2*(currentIndex)+1)); //to find children of current node, we pass on the current node's 0 based index
                }
                if(node.right!=null){
                    q.offer(new Pair(node.right,2*(currentIndex)+2));
                }
            }
            width=Math.max(width,last-first+1);
        }
        return width;
    }

    //height of a binary tree (max depth)
    //https://leetcode.com/problems/maximum-depth-of-binary-tree/

    public int maxDepth(TreeNode root) {
        if(root==null){
            return 0;
        }
        return 1+Math.max(maxDepth(root.left),maxDepth(root.right));
    }

    //diameter of a binary tree
    //https://leetcode.com/problems/diameter-of-binary-tree

    //brute force - O(N2)
    public int diameterOfBinaryTree(TreeNode root) {
        if(root==null){
            return 0;
        }
        int[] max=new int[1];
        helperDBT(root,max);
        return max[0];
    }
    public void helperDBT(TreeNode root, int[] max){
        if(root==null){
            return;
        }
        int lh=maxDepth(root.left);
        int rh=maxDepth(root.right);
        max[0]=Math.max(max[0],lh+rh);
        helperDBT(root.left,max);
        helperDBT(root.right,max);
    }

    //optimal - O(N) doing everything within the height function itself
    public int diameterOfBinaryTree1(TreeNode root) {
        if(root==null){
            return 0;
        }
        int[] max=new int[1];
        height(root,max);
        return max[0];
    }
    public int height(TreeNode root, int[] max){
        if(root==null){
            return 0;
        }
        int lh=height(root.left,max);
        int rh=height(root.right,max);
        max[0]=Math.max(max[0],lh+rh);
        return 1+Math.max(lh,rh);
    }

    //balanced binary tree
    //https://leetcode.com/problems/balanced-binary-tree/

    //brute - calculate left and right height for each node recursively and if at any moment their abs difference>1,
    // return false
    //O(N2)
    public boolean isBalanced(TreeNode root) {
        if(root==null){
            return true;
        }
        int lh=maxDepth(root.left);
        int rh=maxDepth(root.right);
        if(Math.abs(lh-rh)>1){
            return false;
        }
        return isBalanced(root.left)&&isBalanced(root.right);
    }

    //optimised - doing everything while calculating the height
    //O(N2)
    public boolean isBalanced1(TreeNode root) {
        if(root==null){
            return true;
        }
        return balancedHeight(root)==-1?false:true;
    }
    public int balancedHeight(TreeNode root){
        if(root==null){
            return 0;
        }
        int lh=balancedHeight(root.left);
        if(lh==-1){ // left sub tree not balanced
            return -1;
        }
        int rh=balancedHeight(root.right);
        if(rh==-1){ // right sub tree not balanced
            return -1;
        }
        if(Math.abs(lh-rh)>1){ // current tree not blanaced
            return -1;
        }
        return 1+Math.max(lh,rh);
    }

    //lca in a binary tree
    //https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null||root==p||root==q){
            return root;
        }
        TreeNode left=lowestCommonAncestor(root.left,p,q);
        TreeNode right=lowestCommonAncestor(root.right,p,q);
        if(left==null){
            return right; //because right might have one of p or q
        }
        if(right==null){
            return left; //left might have one of p or q
        }
        return root; //left and right both are not null, ie this is a possible ans
    }

    //check identical trees
    //https://leetcode.com/problems/same-tree/
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null||q==null){
            return p==q; //this will return false if either p or q don't reach null at the same time, meaning there's a
            // mismatch
        }
        return p.val==q.val&&isSameTree(p.left,q.left)&&isSameTree(p.right,q.right); //preorder
    }

    //zigzag level order traversal
    //https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if(root==null){
            return new ArrayList<>();
        }
        Queue<TreeNode> q=new LinkedList<>();
        List<List<Integer>> ans=new ArrayList<>();
        boolean flag=false;
        q.offer(root);
        while(!q.isEmpty()){
            int n=q.size();
            List<Integer> list=new ArrayList<>();
            for(int i=0;i<n;i++){
                TreeNode node=q.poll();
                if(node.left!=null){
                    q.offer(node.left);
                }
                if(node.right!=null){
                    q.offer(node.right);
                }
                if(flag){
                    list.add(0,node.val);
                }else{
                    list.add(node.val);
                }
            }
            flag=!flag;
            ans.add(list);
        }
        return ans;
    }

    //boundary traversal
    //https://practice.geeksforgeeks.org/problems/boundary-traversal-of-binary-tree/1
    ArrayList<Integer> boundary(TreeNode root)
    {
        if(root==null){
            return new ArrayList<>();
        }
        ArrayList<Integer> ans=new ArrayList<>();
        if(!isLeaf(root)){
            ans.add(root.val);
        }
        addLeftBoundary(root,ans);
        addLeafBoundary(root,ans);
        addRightBoundary(root,ans);
        return ans;
    }
    public boolean isLeaf(TreeNode root){
        return root.left==null&&root.right==null;
    }
    public void addLeftBoundary(TreeNode root, ArrayList<Integer> ans){
        TreeNode temp=root.left;
        while(temp!=null){
            if(!isLeaf(temp)){ //root is not leaf
                ans.add(temp.val);
            }
            if(temp.left!=null){
                temp=temp.left;
            }
            else {
                temp=temp.right;
            }
        }
    }
    public void addLeafBoundary(TreeNode root, ArrayList<Integer> ans){
        if(isLeaf(root)){ //root is leaf
            ans.add(root.val);
            return;
        }
        //root is not leaf, thus checking if it has a left or a right
        if(root.left!=null){
            addLeafBoundary(root.left,ans);
        }
        if(root.right!=null){
            addLeafBoundary(root.right,ans);
        }
    }
    public void addRightBoundary(TreeNode root, ArrayList<Integer> ans){
        TreeNode temp=root.right;
        List<Integer> list=new ArrayList<>(); //to temporarily store right booundary so that we can reverse it and then
        // add it to the ans
        while(temp!=null){
            if(!isLeaf(temp)){
                list.add(temp.val);
            }
            if(temp.right!=null){ //preferring right first because if for a node in the right boundary there were two child,
                // the right one would be the one that would be counted in the boundary and the left one would be only counted
                // if there was no right child
                temp=temp.right;
            }
            else{
                temp=temp.left;
            }
        }
        for(int i=list.size()-1;i>=0;i--){
            ans.add(list.get(i));
        }
    }

    //max path sum
    //https://leetcode.com/problems/binary-tree-maximum-path-sum/

    //in a path, every node appears only once

    public int maxPathSum(TreeNode root) {
        int[] max=new int[1];
        max[0]=Integer.MIN_VALUE;
        helperMPS(root,max);
        return max[0];
    }
    public int helperMPS(TreeNode root,int[] max){
        if(root==null){
            return 0;
        }
        int leftSum=Math.max(0,helperMPS(root.left,max)); //this will make sure that if any of the right or left calls return a negative sum, then we don't take them in our path and make them 0 because they will only reduce the overall sum if taken
        int rightSum=Math.max(0,helperMPS(root.right,max));
        max[0]=Math.max(max[0],root.val+leftSum+rightSum); //comparing the max path sum for current node with max overall
        return root.val+Math.max(leftSum,rightSum); //for the parent node, we choose the best path ie the one that gives
        // us the max sum from current node
    }

    //construct binary tree from inorder and preorder
    //https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<inorder.length;i++){
            map.put(inorder[i],i);
        }
        return treeBuilder(preorder,0,preorder.length-1,inorder,0,inorder.length-1,map);
    }
    public TreeNode treeBuilder(int[] preorder, int preStart,int preEnd,int[] inorder,int inStart,int inEnd,HashMap<Integer,Integer> inorderMap){
        //we can omit the inEnd from this function as it is not needed. when preStart goes out of bounds, inStart will
        // also be out of bounds
        if(preStart>preEnd||inStart>inEnd){ //reached null
            return null;
        }
        TreeNode root=new TreeNode(preorder[preStart]);
        int rootIndex=inorderMap.get(preorder[preStart]);
        int x=rootIndex-inStart; ////number of elements belonging to left subtree, these help us to partition preorder array
        root.left=treeBuilder(preorder,preStart+1,preStart+x,inorder,inStart,rootIndex-1,inorderMap);
        root.right=treeBuilder(preorder,preStart+x+1,preEnd,inorder,rootIndex+1,inEnd,inorderMap);
        return root;
    }

    //construct binary tree from inorder and postorder
    ///https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
    public TreeNode buildTree1(int[] inorder, int[] postorder) {
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<inorder.length;i++){
            map.put(inorder[i],i);
        }
        return treeBuilder1(postorder,0,postorder.length-1,inorder,0,inorder.length-1,map);
    }
    public TreeNode treeBuilder1(int[] postorder,int postStart,int postEnd,int[] inorder,int inStart,int inEnd,HashMap<Integer,Integer> map){
        if(postStart>postEnd||inStart>inEnd){ //reached null
            return null;
        }
        int rootVal=postorder[postEnd];
        int rootIndex=map.get(rootVal);
        int x=rootIndex-inStart;
        TreeNode root=new TreeNode(rootVal);
        root.left=treeBuilder1(postorder,postStart,postStart+x-1,inorder,inStart,rootIndex-1,map);
        root.right=treeBuilder1(postorder,postStart+x,postEnd-1,inorder,rootIndex+1,inEnd,map);
        return root;
    }

    //symmetric tree (check if bt is a mirror of itself around the root)
    //https://leetcode.com/problems/symmetric-tree/
    //can use preorder in left subtree and reverse left order in right subtree to compare nodes and return true if
    // there's no mismatch
    public boolean isSymmetric(TreeNode root) {

        if(root==null){
            return false;
        }
        return isMirror(root.left,root.right);
    }
    public boolean isMirror(TreeNode left,TreeNode right){
        if(left==null||right==null){
            return left==right; //if both reach null together, this will return true
        }
        return left.val==right.val&&isMirror(left.left,right.right)&&isMirror(left.right,right.left);
    }

    //flatten binary tree to linked list
    //https://leetcode.com/problems/flatten-binary-tree-to-linked-list/

    //recursive approach: reverse post order (right left root) - track prev node, attach it to the right of current
    //O(N) time and space
    TreeNode prev=null;
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

    //iterative approach (same complexity as recursive) - the final linked-list nodes appear to be in preorder thus we try
    // to traverse the tree in preorder fashion doing the necessary bits to attach the parent's right to the valid children
    public void flatten1(TreeNode root) {
        if(root==null){
            return;
        }
        Stack<TreeNode> st=new Stack<>();
        st.add(root);
        while(!st.isEmpty()){
            TreeNode node=st.pop();
            if(node.right!=null){
                st.add(node.right);
            }
            if(node.left!=null){
                st.add(node.left);
            }
            if(!st.isEmpty()){
                node.right=st.peek();
            }
            node.left=null;
        }
    }

    //space optimised approach (modified morris traversal) - takes O(N) time but O(1) space through morris traversal,
    // for every node, we'll try to connect (thread) the last node of left subtree to the main root's right
    public void flatten3(TreeNode root) {
        if(root==null){
            return;
        }
        while(root!=null){
            if(root.left!=null){
                TreeNode prev=root.left;
                while(prev.right!=null){
                    prev=prev.right;
                }
                //reached last node of left subtree
                prev.right=root.right; //connecting last of left subtree to the first node of right subtree
                root.right=root.left; // attaching left of main root to its right and making left null
                root.left=null;
            }
            root=root.right;
        }
    }

    //convert bt into its mirror image
    //https://practice.geeksforgeeks.org/problems/mirror-tree/1
    //traversing the tree in preorder
    void mirror(TreeNode root) {
        if(root==null){
            return;
        }
        //swapping left and right child of current node
        TreeNode temp=root.left;
        root.left=root.right;
        root.right=temp;
        mirror(root.left); //recursively swapping child nodes
        mirror(root.right);
    }

    //modify tree to follow children sum property
    //https://www.codingninjas.com/studio/problems/childrensumproperty_790723?topList=striver-sde-sheet-problems&utm_source=striver&utm_medium=website

    public static void changeTree(TreeNode root) {
        if(root==null){
            return;
        }
        int currentSum=0;
        if(root.left!=null){
            currentSum+=root.left.val;
        }
        if(root.right!=null){
            currentSum+=root.right.val;
        }
        if(currentSum>=root.val){
            root.val=currentSum;
        }
        else{
            if(root.left!=null){
                root.left.val=root.val;
            }
            if(root.right!=null){
                root.right.val=root.val;
            }
        }
        changeTree(root.left);
        changeTree(root.right);
        int sum=0;
        if(root.left!=null){
            sum+=root.left.val;
        }
        if(root.right!=null){
            sum+=root.right.val;
        }
        if(root.left!=null||root.right!=null){ //assigning the sum to current only if it its not a leaf node
            root.val=sum;
        }
    }

    //serialize-deserialize binary
    //https://leetcode.com/problems/serialize-and-deserialize-binary-tree/

    //using level order traversal as the string
    public String serialize(TreeNode root) {
        if(root==null){
            return "";
        }
        StringBuilder sb=new StringBuilder(); //using sb coz it is much faster than normal string
        Queue<TreeNode> q=new LinkedList<>();
        q.offer(root);
        while(!q.isEmpty()){
            TreeNode node=q.poll();
            if(node==null){
                sb.append("null ");
                continue;
            }
            sb.append(node.val + " ");
            q.offer(node.left);
            q.offer(node.right);
        }
        return sb.toString();
    }

    public TreeNode deserialize(String data) {
        if(data==""){
            return null;
        }
        Queue<TreeNode> q=new LinkedList<>();
        String[] st=data.split(" ");
        TreeNode root=new TreeNode(Integer.parseInt(st[0]));
        q.offer(root);
        for(int i=1;i< st.length;i++){
            TreeNode node=q.poll();
            if(!st[i].equals("null")){
                TreeNode left=new TreeNode(Integer.parseInt(st[i]));
                node.left=left;
                q.offer(left);
            }
            if(!st[++i].equals("null")){
                TreeNode right=new TreeNode(Integer.parseInt(st[i]));
                node.right=right;
                q.offer(right);
            }
        }
        return root;
    }

}