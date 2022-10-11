public class BST {
    public class TreeNode{
        private int value;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
        public TreeNode() {
            this.value=0;
            this.left = null;
            this.right = null;
        }
    }
    public static void main(String[] args) {

    }
    //search in bst
    //https://leetcode.com/problems/search-in-a-binary-search-tree/
    public TreeNode searchBST(TreeNode root, int val) {
        if(root!=null&&root.value!=val) {
            root = val < root.value ? root.left : root.right;
        }
        return root;
    }

    //ceil in bst
    //https://practice.geeksforgeeks.org/problems/implementing-ceil-in-bst/1
    int findCeil(TreeNode root, int key) {
        if (root == null) return -1;
        int ceil=0;
        while(root!=null){
            if(root.value==key){
                ceil=root.value;
            }
            if(root.value<key){
                root=root.right; //move right if current value < key
            }
            else{
                ceil=root.value;
                root=root.left; //move left if current value greater than key
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
        if(root==null){
            return new TreeNode(val); //if tree is empty, insert at node
        }
        TreeNode node=root;
        while(true){
            if(val>node.value){ //insert somewhere in right subtree
                if(node.right!=null){
                    node=node.right;
                }
                else{ //right is null
                    node.right=new TreeNode(val);
                    break;
                }
            }
            else{ //insert somewhere in left subtree
                if(node.left!=null){
                    node=node.left;
                }
                else{ //left is null
                    node.left=new TreeNode(val);
                    break;
                }
            }
        }
    return root;
    }

    //delete a node in bst
    //https://leetcode.com/problems/delete-node-in-a-bst/
    public TreeNode deleteNode(TreeNode root, int key) {
        3
        return root;
    }


}
