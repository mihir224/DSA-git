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



}