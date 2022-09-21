public class Questions {
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


}

