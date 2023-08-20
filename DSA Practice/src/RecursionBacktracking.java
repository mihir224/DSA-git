import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class RecursionBacktracking{
    //subset sums
    //https://practice.geeksforgeeks.org/problems/subset-sums2234/1
    //brute - recursion

    //for each element of the array, there are two recursive calls - either we pick the element or we don't pick it. This
    // takes O(2^N) time. Moreover, sorting 2^N subsets takes (2^Nlog2^N) time. Thus overall time complexity is O(2^NlogN)
    // and space is recursive stack space (a recursive call can have a maximum stack space ie of N ie when we reach the base
    // condition, total depth is N) thus O(N) recursive stack space and O(2^N) space to store the subsets ie total space
    // is O(N+2^N)
    ArrayList<Integer> subsetSums(ArrayList<Integer> arr, int N){
        ArrayList<Integer> ans=new ArrayList<>();
        f(0,0,arr,ans,N);
        Collections.sort(ans);
        return ans;
    }
    public void f(int i, int sum, ArrayList<Integer> arr, ArrayList<Integer> ans, int N){
        if(i>=arr.size()){ //return if i==N, ie we have stumbled upon a possible subsequence
            ans.add(sum);
            return;
        }
        f(i+1,sum+arr.get(i),arr,ans,N); //pick current element
        f(i+1,sum,arr,ans,N); //don't pick current
    }


    //subset 2
    //https://leetcode.com/problems/subsets-ii
    //we take the candidate at the ith index(pick) and then remove it after the function call. Then, we move to the next
    // element in the iteration for which recursive call is called for that element (this is equivalent to not picking the
    // prev element and moving forward with the next element) thus for each element we're technically making two recursive
    // calls (first when we pick and second when we don't pick it). Thus time taken by this is 2^N and in every recursive
    // call we're adding the current subset to the ans list. If we assume the avg size of a list is M, then overall time
    // complexity becomes - O(M*2^N + NlogN for sorting the array), and space complexity is - O(2^N for storing all subsets
    // + N ie recursive stack space)

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans=new ArrayList<>();
        Arrays.sort(nums); //necessary to sort the array so that duplicates are placed together
        helper(0,nums,new ArrayList<>(),ans);
        return ans;
    }
    public void helper(int currentIndex, int[] nums,List<Integer> list,List<List<Integer>> ans){
        ans.add(new ArrayList<>(list)); //storing the subsequence passed in each recursive call storing a new object with
        // the copy of the list instead of directly copying the list because list is being passed by reference. Any change
        // to this list in other recursive calls would change it in the ans list also.
        for(int i=currentIndex;i<nums.length;i++){
            if(i>currentIndex&&nums[i]==nums[i-1]){ //checking if the element we're trying to place at the current index
                // of the sub array has already appeared before
                continue;
            }
            list.add(nums[i]); //adding current element at the currentIndex of sub array
            helper(i+1,nums,list,ans);
            list.remove(list.size()-1); //removing the current element to try out other possible combinations
        }
    }

    //combination sum - 1
    //https://leetcode.com/problems/combination-sum/
    // overall we make 2^n recursive calls and for these recursive calls we add the current sequence to the ans list. assuming
    // each valid combination sequence is of size k then the overall time complexity becomes - O(k*2^n) and space taken
    // would be O(k*total subsequences+n)

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans=new ArrayList<>();
        List<Integer> list=new ArrayList<>();
        cs(0,target,candidates,candidates.length,list,ans);
        return ans;
    }
    public void cs(int i, int target,int[] candidates, int n, List<Integer> list, List<List<Integer>> ans){
        if(i==n){
            if(target==0){ //ie current combination sum is equal to target
                ans.add(new ArrayList<>(list));
            }
            return;
        }
        if(candidates[i]<=target){ //current candidate can be used to form a sum equal to target
            list.add(candidates[i]);
            cs(i,target-candidates[i],candidates,n,list,ans); //if current element can be used again, it would be added to the ans from this recursive call
            list.remove(list.size()-1); //to try out other combinations
        }
        cs(i+1,target,candidates,n,list,ans);
    }

    //combination sum 2
    //https://leetcode.com/problems/combination-sum-ii/

    //an element can be taken only once

    //brute - we can use the pick/not pick approach to find all possible subsequences along with a variable sum to track
    // the sum of all the elements of a subsequence such that when we reach the base condition ie i==n meaning that we've
    // traversed the whole array through recursion after picking some elements and not picking others, we basically check
    // if sum till upto this point has become equal to the target sum, if it has then it means that the current subsequence
    // is a valid one, and add it to a set of lists after sorting (so that set can identify duplicates). then we can
    // basically retrieve all the lists from the set and store them in a list of list. we make 2^n recursive calls in total
    // to reach the target from all subsequences (in case each candidate can contribute to the sum) and in each of these calls, we're adding a list of avg size k to the set
    // after sorting. thus overall time complexity is O(2^n*(klok+logk)) and space is O(k*total subsequences+n)

    //optimal - avoiding duplicates on the go (similar approach as subset sum2)

    //O(2^n to find all possible subsets of target * k to insert the list of size k into ans list)
    //O(k*total subsequence + n since in worst case recursion depth can go upto N)
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates); //sorting candidates beforehand so that all the subsequences are also sorted
        List<Integer> list=new ArrayList<>();
        List<List<Integer>> ans=new ArrayList<>();
        cs2(0,target,candidates,list,ans);
        return ans;
    }
    public void cs2(int currentIndex, int target, int[] candidates, List<Integer> list, List<List<Integer>> ans){
        if(target==0){ //here we didn't explicitly check if i has reached n because in case we find an element which is
            // greater than the target, we stop the recursion then and there by breaking out from the for loop. This way,
            // before recursion reaches a depth of n, either we might've gotten a target 0 or we would've stopped further
            // recursive calls because it wouldn't have been possible to pick an element and even if the recursion reaches
            // depth of n, it would only be possible if the target upto that point equated to 0 ie in case the target is
            // the sum of all elements.
            ans.add(new ArrayList<>(list));
            return;
        }
        for(int i=currentIndex;i<candidates.length;i++){
            if(i>currentIndex&&candidates[i]==candidates[i-1]){
                continue;
            }
            if(candidates[i]>target){ //not possible to add current candidates, and thus we can also not add further
                // candidates as the array is sorted
                break;
            }
            list.add(candidates[i]);
            cs2(i+1,target-candidates[i],candidates,list,ans);
            list.remove(list.size()-1);
        }
    }

    //palindrome partitioning
    //https://leetcode.com/problems/palindrome-partitioning/

    //time - O(2^N for generating all possible substrings in case all are valid * k for inserting a valid partitioning into the
    // ans list * n/2 for checking if substring is palindrome ), and space - O(X*K) if x is the number of palindrome
    // partitioned lists of strings and k is the avg size of a list

    public List<List<String>> partition(String s) {
        List<List<String>> ans=new ArrayList<>();
        pp(0,s,new ArrayList<>(),ans);
        return ans;
    }
    public void pp(int currentIndex,String s, List<String> list, List<List<String>> ans){
        if(currentIndex==s.length()){ //partitioning till now is palindromic
            ans.add(new ArrayList<>(list));
            return;
        }
        for(int i=currentIndex;i<s.length();i++){
            String subStr=s.substring(currentIndex,i+1);
            if(isPalindrome(subStr)){ //making partition only if the substring till that partition is a valid
                // palindrome
                list.add(subStr);
                pp(i+1,s,list,ans);
                list.remove(list.size()-1); //to try out partitions at other places
            }
        }
    }
    public boolean isPalindrome(String str){
        int n=str.length();
        int i=0;
        while(i<n/2){
            if(str.charAt(i)!=str.charAt(n-i-1)){
                return false;
            }
            i++;
        }
        return true;
    }

    //kth permutation sequence (mind-trucking ques)
    //https://leetcode.com/problems/permutation-sequence/

    //brute - use recursion to generate all possible permutations, put them in a DS and then sort that DS and return the (k-1)th sequence

    // at any position, we can place any element out of the N elements and for each of these N elements, we make
    // a recursive call in which we try to place the remaining n-1 elements, and then another recursive call inside that
    // where we try to place the remaining n-2 elements. Thus for each of the N elements, we're making N factorial recursive
    // calls. Therefore, total time complexity is - O(N factorial * N)) and space is O(N + N)

    //optimal - we are picking N numbers to place at N positions in our ans and for each number we perform deletions in the
    // DS which takes O(N) time thus total time is O(N2) and space is O(N to store the number of the group numbers+N to
    // store the ans)

    public String getPermutation(int n, int k) {
        int fact=1;
        List<Integer> numbers=new ArrayList<>(); //to store group members
        String ans="";
        k=k-1; //0 based indexing of permutations
        for(int i=1;i<n;i++){ //n-1 factorial
            fact*=i;
            numbers.add(i);
        }
        numbers.add(n);
        while(true){
            ans=ans+numbers.get(k/fact);
            numbers.remove(k/fact);
            if(numbers.size()==0){
                break;
            }
            k=k%fact;
            fact=fact/numbers.size();
        }
        return ans;
    }

    //permutations
    //https://leetcode.com/problems/permutations

    // at any position, we can place any element out of the N elements and for each of these N elements, we make
    // a recursive call in which we try to place the remaining n-1 elements, and then another recursive call inside that
    // where we try to place the remaining n-2 elements. Thus for each of the N elements, we're making N factorial recursive
    // calls. Therefore, total time complexity is - O(N factorial * N)) and space is O(N + N)
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans=new ArrayList<>();
        pmt(new ArrayList<>(), new boolean[nums.length],nums, ans);
        return ans;
    }
    public void pmt(List<Integer> list, boolean[] vis, int[] nums, List<List<Integer>> ans){
        if(list.size()==nums.length){ //ie a valid permutation has been formed
            ans.add(new ArrayList<>(list));
            return;
        }
        for(int i=0;i<nums.length;i++){
            if(!vis[i]){
                vis[i]=true;
                list.add(nums[i]);
                pmt(list,vis,nums,ans);
                list.remove(list.size()-1);
                vis[i]=false;
            }
        }
    }

    //N-queens
    //https://leetcode.com/problems/n-queens/

    //we can place a queen in a col in 
    public List<List<String>> solveNQueens(int n) {
        char[][] chessB=new char[n][n];
        List<List<String>> ans=new ArrayList<>();
        //initialising chessB
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                chessB[i][j]='.';
            }
        }
        helperChess(0,chessB,n,ans);
        return ans;
    }
    public void helperChess(int col,char[][] chessB, int n, List<List<String>> ans){
        if(col==n){
            ans.add(convertToListOfStrings(chessB));
            return;
        }
        for(int i=0;i<n;i++){ //trying to place a queen in every row for each col
            if(isPossible(chessB,i,col)){ //can place queen at the ith row of this col, thus try placing queen in the next col recursively
                chessB[i][col]='Q'; //placing queen at current cell
                helperChess(col+1,chessB,n,ans);
                chessB[i][col]='.'; //removing queen from current cell to try out other combinations
            }
        }
    }
    public boolean isPossible(char[][] chessB, int row, int col){
        int rowCopy=row;
        int colCopy=col;
        while(row>=0&&col>=0){ //check top-left diagonal
            if(chessB[row][col]=='Q'){
                return false;
            }
            row--;
            col--;
        }
        row=rowCopy;
        col=colCopy;
        while(col>=0){ //check left
            if(chessB[row][col]=='Q'){
                return false;
            }
            col--;
        }
        row=rowCopy;
        col=colCopy;
        while(row<chessB.length&&col>=0){ //check bottom-left diagonal
            if(chessB[row][col]=='Q'){
                return false;
            }
            row++;
            col--;
        }
        return true;
    }
    public List<String> convertToListOfStrings(char[][] chessB){
        List<String> list=new ArrayList<>();
        for(int i=0;i<chessB.length;i++){
            String str=new String(chessB[i]);
            list.add(str);
        }
        return list;
    }
    
    //sudoku solver
    //https://leetcode.com/problems/sudoku-solver/

    //for each cell in a board of N2 cells, we can choose from 9 possible numbers. Thus the tc is - O(9^N2) and space is
    // O(1)

    public void solveSudoku(char[][] board) {
        solve(board);
    }
    public boolean solve(char[][] board){
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]=='.'){ //checking for empty spaces
                    for(char ch='1';ch<='9';ch++){
                        if(canPlace(ch,i,j,board)){
                            board[i][j]=ch;
                            if(solve(board)){
                                return true;
                            }
                            else{
                                board[i][j]='.'; //removing current number from the empty space as placing it here would
                                // make it impossible to place other elements at further empty spaces
                            }
                        }
                    }
                    return false; //couldn't place any number in the empty space
                }
            }
        }
        return true;
    }
    public boolean canPlace(char ch, int row, int col, char[][] board){
        for(int i=0;i<9;i++){ //traversing the whole grid
            if(board[row][i]==ch){ //ie element's row already contains this number
                return false;
            }
            if(board[i][col]==ch){ //ie element's col already contains this number
                return false;
            }
            if(board[3*(row/3)+i/3][3*(col/3)+i%3]==ch){ //ie the 3*3 grid within which the element is already contains
                // this number
                return false;
            }
        }
        return true;
    }


    //M-coloring
    //https://practice.geeksforgeeks.org/problems/m-coloring-problem-1587115620/1#

    //time - O(V+E for forming adj list + M^N since we can color each node with any color from 1 to M resulting in about M recursive
    // calls for N nodes), space - O(N) for color array + O(N*K) for adj list assuming each node has K neighbours

    public boolean graphColoring(boolean graph[][], int m, int n) {
        //forming adj list
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<graph.length;i++){
            for(int j=0;j<graph[i].length;j++){
                if(graph[i][j]==true){
                    adj.get(i).add(j);
                    adj.get(j).add(i);
                }
            }
        }
        int[] color=new int[n]; //to store the color of each node
        return mClr(0,color,adj,m,n);
    }
    public boolean mClr(int currentNode, int[] color, ArrayList<ArrayList<Integer>> adj, int m, int n){
        if(currentNode==n){ //reached end, thus we were able to color the graph
            return true;
        }
        for(int i=1;i<=m;i++){ //trying to color the current node from with colors from 1 to M
            if(canColor(currentNode,i,color,adj)){
                color[currentNode]=i;
                if(mClr(currentNode+1,color,adj,m,n)) { //try coloring next nodes recursively
                    return true;
                }
                color[currentNode]=0;   //reaching here would mean that above statement didn't return false and thus we
                // conclude that if we tried coloring current node with this color, we might not be able to color the rest
                // of the nodes, thus we remove this color and try out other possible colors on this node
            }
        }
        return false; //returning false if we weren't able to color the current node with any of the M colors
    }
    public boolean canColor(int currentNode, int clr, int[] color, ArrayList<ArrayList<Integer>> adj){
        for(int i:adj.get(currentNode)){
            if(color[i]==clr){ //ie there's an adj node with same clr that we're trying to color this node with
                return false;
            }
        }
        return true;
    }

    //rat in a maze
    //https://practice.geeksforgeeks.org/problems/rat-in-a-maze-problem/1

    //O(4^(m*n)) time since for each cell we can move in 4 different directions, and O(m*n) space taken by vis array
    public static ArrayList<String> findPath(int[][] m, int n) {
        ArrayList<String> ans=new ArrayList<>();
        boolean[][] vis=new boolean[n][n];
        if(m[0][0]==0||m[n-1][n-1]==0){ //no path exists from source to dst
            ans.add("-1");
            return ans;
        }

        rim(0,0,"",vis,ans,m,n);
        return ans;
    }
    public static void rim(int row, int col, String s, boolean[][] vis,ArrayList<String> ans,int[][] m, int n){
        if(row==n-1&&col==n-1){ //reached dst
            ans.add(s);
            return;
        }
        if(row+1<n&&!vis[row+1][col]&&m[row+1][col]==1){ //moving down
            vis[row][col]=true; //marking visit of current cell
            rim(row+1,col,s+"D",vis,ans,m,n);
            vis[row][col]=false;
        }
        if(col-1>=0&&!vis[row][col-1]&&m[row][col-1]==1){ //moving left
            vis[row][col]=true;
            rim(row,col-1,s+"L",vis,ans,m,n);
            vis[row][col]=false;
        }
        if(col+1<n&&!vis[row][col+1]&&m[row][col+1]==1){ //moving right
            vis[row][col]=true;
            rim(row,col+1,s+"R",vis,ans,m,n);
            vis[row][col]=false;
        }
        if(row-1>=0&&!vis[row-1][col]&&m[row-1][col]==1){ //moving up
            vis[row][col]=true;
            rim(row-1,col,s+"U",vis,ans,m,n);
            vis[row][col]=false;
        }
    }


    //word break
    //https://www.codingninjas.com/codestudio/problems/983635?topList=striver-sde-sheet-problems&utm_source=striver&utm_medium=website

    //we basically try to recursively form valid sentences through all possible combinations of substrings that are present
    // in the dictionary. We start from the first letter (we do index+1 in each call since we try taking the substring in
    // which upperbound is written 1 index further) and try to move till end where in each iteration we check if the substring
    // up till this point is a valid substring ie if it exists in the dictionary. If it does, then we place a gap between
    // this substring and the rest of the substring. now we recursively process the rest of the string(ie after the white
    // space) the same way, passing the combined string to the recursive function. If in any call we see that index>s.length,
    // then that would mean we've completely traversed the string, and thus we add the current string to the ans. Now to
    // try out other possible combinations, after a recursive call is over, we simply re-format the string such that the
    // white space is removed and thus in further iterations of i we can try other valid combinations and do the same for
    // them. for the last word, we don't add a space after it since i has reached s.length. So, when we try to make
    // the recursive call when we've reached the last word, index becomes>s.length() because i was at s.length() since no
    // space was added after the last word

    //in case every substring is a valid substring, the recursive function is called for every index from 0 to n-1 resulting
    // in n recursive calls and in each of these calls we iterate from currentIndex+1 to n thus it takes n-currentIndex
    // time which is equivalent to n. thus time complexity is near about O(N2) and space is O(N) ie recursive stack space

    public static ArrayList<String> wordBreak(String s, ArrayList<String> dictionary) {
        ArrayList<String> ans=new ArrayList<>();
        wB(0,s,ans,dictionary);
        return ans;
    }
    public static void wB(int currentIndex, String s, ArrayList<String> ans, ArrayList<String> dictionary){
        if(currentIndex>s.length()){
            ans.add(s);
            return;
        }
        int temp=s.length();
        for(int i=currentIndex+1;i<=temp;i++){
            String str=s.substring(currentIndex,i);
            if(dictionary.contains(str)){
                if(i!=temp){
                    s=s.substring(0,i) + " " + s.substring(i);
                }
                wB(i+1,s,ans,dictionary);
                if(i!=temp){
                    s=s.substring(0,i)+s.substring(i+1); //i is pointing to white space thus we
                    // exclude it
                }
            }
        }
    }
}