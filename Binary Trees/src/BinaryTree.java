import com.sun.source.tree.Tree;
import org.w3c.dom.Node;

import java.security.KeyPair;
import java.util.*;


public class BinaryTree {
    TreeNode root;
    public BinaryTree() {
        root = null;
    }
    class TreeNode {
        private int value;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int value) {
            this.value = value;
            left = null;
            right = null;
        }
    }
    //DFS
    //pre-order traversal
    public void preOrder(TreeNode node){
        if(node==null){
            return;
        }
        System.out.print(node.value);
        preOrder(node.left);
        preOrder(node.right);
    }
    //iterative
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list=new LinkedList<>();
        Stack<TreeNode> stack=new Stack<>();
        if(root==null){
            return list;
        }
        stack.push(root);
        while(!stack.isEmpty()){
            int n=stack.size();
            for(int i=0;i<n;i++){
                TreeNode node=stack.pop();
                if(node.right!=null){
                    stack.push(node.right);
                }
                if(node.left!=null){
                    stack.push(node.left);
                }
                list.add(node.value);
            }
        }
        return list;
    }

    //in-order traversal
    public void inOrder(TreeNode node){
        if(node==null) {
            return;
        }
        inOrder(node.left);
        System.out.print(node.value);
        inOrder(node.right);
    }
    //iterative
    public List<Integer> inorderTraversal(TreeNode root) {
        //dry run for better understanding
        List<Integer> list=new LinkedList<>();
        Stack<TreeNode> stack=new Stack<>();
        while(true){
            TreeNode node=root;
            if(node!=null){
                stack.push(node);
                node=node.left; //moving towards left till node is null
            }
            else{ //case when node is null
                if(stack.isEmpty()){ //all nodes traversed
                    break;
                }
                node=stack.pop();
                list.add(node.value);
                node=node.right;
            }
        }
        return list;
    }


    //post-order traversal
    public void postOrder(TreeNode node){
        if(node==null){
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.value);
    }
    //iterative
    //using 2 stacks
    public List<Integer> postorderTraversal1(TreeNode root) {
        Stack<TreeNode> st1=new Stack<>();
        Stack<TreeNode> st2=new Stack<>();
        List<Integer> list=new LinkedList<>();
        if(root==null){
            return list;
        }
        st1.push(root);
        while(!st1.isEmpty()){
            st2.push(st1.pop());
            if(st2.peek().left!=null) {
                st1.push(st2.peek().left);
            }
            if(st2.peek().right!=null){
                st1.push(st2.peek().right);
            }
        }
        while(!st2.isEmpty()){
            for(int i=0;i<st2.size();i++){
                list.add(st2.pop().value);
            }
        }
        return list;
    }

    //using 1 stack
    public List<Integer> postorderTraversal(TreeNode root) {
        Stack<TreeNode> st1=new Stack<>();
        List<Integer> list=new LinkedList<>();
        TreeNode temp=root;
        TreeNode current=root;
        while(current!=null||!st1.isEmpty()){
            if(current!=null){
                st1.push(current);
                current=current.left; //move left till current is null
            }
            else{
                temp=st1.peek().right;
                if(temp==null) { //right is null
                    temp=st1.pop();
                    list.add(temp.value);
                    while(!st1.isEmpty()&&temp==st1.peek().right){ //to go back if the current temp has a root
                        temp=st1.pop();
                        list.add(temp.value);
                    }

                }
                else{
                    current=temp; //right exists, assign it to current
                }
            }
        }

        return list;
    }

    //all traversals in one traversal


    //BFS
    //level order traversal
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> q=new LinkedList<>();
        List<List<Integer>> levelWiseList=new LinkedList<>();   //list storing level wise traversals
        if(root==null){
            return levelWiseList;
        }
        q.offer(root);
        while(!q.isEmpty()){
            List<Integer> list=new LinkedList<>();
            int n=q.size();
            for(int i=0;i<n;i++){
                TreeNode node=q.poll();
                if(node.left!=null){
                    q.add(node.left);
                }
                if(node.right!=null){
                    q.add(node.right);
                }
                list.add(node.value);
            }
            levelWiseList.add(list);
        }
        return levelWiseList;
    }
}
