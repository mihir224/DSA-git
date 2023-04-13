import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
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

}