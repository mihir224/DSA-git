import java.util.*;

public class Questions {
    //combination sum
    //https://leetcode.com/problems/combination-sum/
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        helper(0, candidates, target, ans, new ArrayList<>());
        return ans;
    }

    public void helper(int i, int[] candidates, int target, List<List<Integer>> ans, List<Integer> list) {
        if (i == candidates.length) {
            if (target == 0) {
                ans.add(new ArrayList<>(list));
            }
            return;
        }
        if (candidates[i] <= target) {
            list.add(candidates[i]);
            helper(i, candidates, target - candidates[i], ans, list);
            list.remove(list.size() - 1);
        }
        helper(i + 1, candidates, target, ans, list);
    }

    //combination sum 2
    //https://leetcode.com/problems/combination-sum-ii/
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<Integer> list = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(candidates);
        helper2(0, candidates, target, list, ans);
        return ans;
    }

    public void helper2(int currentIndex, int[] candidates, int target, List<Integer> list, List<List<Integer>> ans) {
        if (target == 0) {
            ans.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < candidates.length; i++) {
            //case 1
            if (i > currentIndex && candidates[i] == candidates[i - 1]) { //check for duplicates
                continue;
            }
            //case 2
            if (candidates[i] > target) {
                break;
            }
            //case 3 (the one that leads us to possible solution)
            list.add(candidates[i]);
            helper2(i + 1, candidates, target - candidates[i], list, ans);
            list.remove(list.size() - 1);
        }
    }

    //subset sum
    //https://practice.geeksforgeeks.org/problems/subset-sums2234/1
    ArrayList<Integer> subsetSums(ArrayList<Integer> arr, int N) {
        ArrayList<Integer> ans = new ArrayList<>();
        helper3(0, 0, arr, N, ans);
        Collections.sort(ans);
        return ans;
    }

    public void helper3(int i, int sum, ArrayList<Integer> arr, int n, ArrayList<Integer> ans) {
        if (i == n) { //found a sum
            ans.add(sum);
        }
        helper3(i + 1, sum + arr.get(i), arr, n, ans); //picked
        helper3(i + 1, sum, arr, n, ans); //not picked
    }

    //subsets
    //https://leetcode.com/problems/subsets/
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        helper4(0, list, nums, ans);
        return ans;
    }

    public void helper4(int i, List<Integer> list, int[] nums, List<List<Integer>> ans) {
        if (i >= nums.length) {
            ans.add(new ArrayList<>(list));
            return;
        }
        list.add(nums[i]); //pick
        helper4(i + 1, list, nums, ans);
        list.remove(list.size() - 1); //not pick
        helper4(i + 1, list, nums, ans);
    }

    //subsets 2
    //https://leetcode.com/problems/subsets-ii/
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        //inorder to have all the duplicates side by side, we need to sort the given array
        Arrays.sort(nums);
        helper5(0, nums, new ArrayList<>(), ans);
        return ans;
    }

    public void helper5(int currentIndex, int[] nums, List<Integer> list, List<List<Integer>> ans) {
        //we store the subset obtained in each recursive call
        ans.add(new ArrayList<>(list));
        //as soon as current index reaches n, the for loop below will not run and that particular recursion call will just
        //return to where it was called after adding the particular subset stored in the list at that time to the ans
        //thus we don't need to explicitly specify the base condition ie what happens when i>=n
        for (int i = currentIndex; i < nums.length; i++) {
            if (i > currentIndex && nums[i] == nums[i - 1]) { //check for duplicates
                continue;
            }
            list.add(nums[i]);
            helper5(i + 1, nums, list, ans);
            list.remove(list.size() - 1);
        }
    }

    //permutations
    //https://leetcode.com/problems/permutations/
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        boolean[] vis = new boolean[nums.length];
        helper6(new ArrayList<>(), vis, ans, nums);
        return ans;
    }

    public void helper6(List<Integer> list, boolean[] vis, List<List<Integer>> ans, int[] nums) {
        if (list.size() == nums.length) {
            ans.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!vis[i]) {
                list.add(nums[i]);
                vis[i] = true;
                helper6(list, vis, ans, nums);
                list.remove(list.size() - 1);
                vis[i] = false;
            }
        }
    }

    //N-Queens
    //https://leetcode.com/problems/n-queens/
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList<>();
        char[][] chessB = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                chessB[i][j] = '.';
            }
        }
        helperQueen(0, chessB, ans);
        return ans;
    }

    public void helperQueen(int col, char[][] chessB, List<List<String>> ans) {
        if (col == chessB.length) {
            ans.add(convertToListOfStrings(chessB));
            return;
        }
        for (int i = 0; i < chessB.length; i++) {
            if (isPossible(chessB, i, col)) {
                chessB[i][col] = 'Q'; //place queen if possible
                helperQueen(col + 1, chessB, ans);
                chessB[i][col] = '.'; //remove queen after recursion call is over
            }
        }
    }

    public boolean isPossible(char[][] chessB, int row, int col) {
        int rowCopy = row;
        int colCopy = col;
        while (row >= 0 && col >= 0) { //checking upper left diagonal
            if (chessB[row][col] == 'Q') {
                return false;
            }
            row--;
            col--;
        }
        row = rowCopy;
        col = colCopy;
        while (row >= 0 && col >= 0) {
            if (chessB[row][col] == 'Q') { //checking left
                return false;
            }
            col--;
        }
        row = rowCopy;
        col = colCopy;
        while (row < chessB.length && col >= 0) { //checking lower left diagonal
            if (chessB[row][col] == 'Q') {
                return false;
            }
            row++;
            col--;
        }
        return true; //all conditions satisfied
    }

    public List<String> convertToListOfStrings(char[][] chessB) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < chessB.length; i++) {
            String s = new String(chessB[i]);
            list.add(s);
        }
        return list;
    }

    //sudoku solver
    //https://leetcode.com/problems/sudoku-solver/
    public void solveSudoku(char[][] board) {
        helperSudoku(board);
    }

    public boolean helperSudoku(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') { //checking for empty spaces
                    for (char ch = '1'; ch <= '9'; ch++) { //checking whether it's possible to place anyone from 1-9 at the empty place
                        if (isPossibleSudoku(board, i, j, ch)) {
                            board[i][j] = ch;
                            if (helperSudoku(board)) { //checking for further empty spaces
                                return true;
                            } else { //case when putting a specific number at an empty space results in collision while
                                // filling further empty cells, so removing this number from current cell so that we can try
                                // a different number
                                board[i][j] = '.';
                            }
                        }
                    }
                    return false; //case when it wasn't possible to fill an empty space with any  number
                }
            }
        }
        return true; //successfully filled all empty spaces
    }

    public boolean isPossibleSudoku(char[][] board, int row, int col, char ch) {
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == ch) { //check for row
                return false;
            }
            if (board[row][i] == ch) { //check for col
                return false;
            }
            if (board[(3 * (row / 3)) + (i / 3)][(3 * (col / 3)) + (i % 3)] == ch) { //formula to check the complete 3*3 grid.
                return false;
            }
        }
        return true;
    }

    //m-colouring problem
    //https://practice.geeksforgeeks.org/problems/m-coloring-problem-1587115620/1
    public boolean graphColoring(boolean graph[][], int m, int n) {
        //building the graph
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (graph[i][j] == true) {
                    adj.get(i).add(j);
                    adj.get(j).add(i);
                }
            }
        }
        int[] color = new int[n];
        return helper7(0, adj, color, m, n);
    }

    public boolean helper7(int currentNode, List<List<Integer>> adj, int[] color, int m, int n) {
        if (currentNode == n) { //it was possible to colour all nodes
            return true;
        }
        for (int i = 1; i <= m; i++) {
            if (isPossible2(currentNode,adj,i,color)) {
                color[currentNode]=i;
                if(helper7(currentNode+1,adj,color,m,n)){
                    return true;
                }
                color[currentNode]=0; //not possible to color further nodes with this color, thus removing it and trying
                // out other colors
            }
        }
        return false; //not possible to color the graph
    }
    public boolean isPossible2(int node,List<List<Integer>> adj,int clr,int[] color){
        for(int i:adj.get(node)){
            if(color[i]==clr){
                return false;
            }
        }
        return true;
    }

}
