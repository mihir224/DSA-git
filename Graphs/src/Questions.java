import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Questions {
    public static void main(String[] args) {

    }

    //number of provinces
    //https://leetcode.com/problems/number-of-provinces/
    public int findCircleNum(int[][] isConnected) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        boolean[] vis=new boolean[isConnected.length];
        int count=0;
        for(int i=0;i<isConnected.length;i++){
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < isConnected.length; i++) { //converting adj matrix to adj list
            for (int j = 0; j < isConnected[0].length; j++) {
                if (isConnected[i][j] == 1 && i != j) {
                    adj.get(i).add(j);
                    adj.get(j).add(i);
                }
            }
        }
        for(int i=0;i<isConnected.length;i++){
            if(!vis[i]){
                count++;
                dfs(i,adj,vis);
            }
        }
        return count;
    }
    public void dfs(int node, ArrayList<ArrayList<Integer>> adj, boolean[] vis){
        vis[node]=true;
        for(int i: adj.get(node)){
            if(!vis[i]){
                dfs(i,adj,vis);
            }
        }
    }

    //number of islands
    //https://practice.geeksforgeeks.org/problems/find-the-number-of-islands/1
    class Pair{
        int first;
        int second;
        public Pair(int first,int second){
            this.first=first;
            this.second=second;
        }
    }
    public int numIslands(char[][] grid) {
        int n=grid.length;
        int m=grid[0].length;
         boolean[][] vis=new boolean[n][m];
         int count=0;
         for(int i=0;i<n;i++){
             for(int j=0;j<m;j++){
                 if(!vis[i][j]&&grid[i][j]=='1'){ //not visited and equal to 1 ie new island
                     count++;
                     bfs(i,j,vis,grid);
                 }
             }
         }
         return count;
    }
    public void bfs(int row, int col,boolean[][] vis, char[][] grid){
        vis[row][col]=true; //mark visit of current node
        Queue<Pair> q=new LinkedList<>();
        q.add(new Pair(row,col)); //pushing current node to the queue
        int n=grid.length;
        int m=grid[0].length;
        while(!q.isEmpty()){
            Pair p=q.poll();
            int i=p.first;
            int j=p.second;
            //find neighbours (checking all 8 directions of current node) and mark visit
            for(int delRow=-1;delRow<=1;delRow++){
                for(int delCol=-1;delCol<=1;delCol++){
                    int neighRow=i+delRow;
                    int neighCol=j+delCol;
                    if(neighRow>=0&&neighRow<n&& //to check for valid rows
                            neighCol>=0&&neighCol<m&& //to check for valid cols
                            grid[neighRow][neighCol]=='1' //to make sure that current node is a land
                            &&!vis[neighRow][neighCol]){ //to make sure current node has not already been visited\
                        vis[neighRow][neighCol]=true;
                        q.add(new Pair(neighRow,neighCol)); //adding neighbours (lands) having value 1
                    }
                }
            }
        }
    }

    //flood fill
    //https://leetcode.com/problems/flood-fill/
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int initialColor=image[sr][sc];
        int[][] copy=image;
        int[] delRow={-1,0,1,0}; //possible values of row and col of all neighbours (in order)
        int[] delCol={0,1,0,-1};
        dfs1(sr,sc,image,copy,color,initialColor,delRow,delCol);
        return copy;
    }
    public void dfs1(int sr, int sc, int[][] image, int[][] copy, int newColor, int initialColor, int[] delRow, int[] delCol){
        copy[sr][sc]=newColor; //coloring starting pixel with new color
        int n=image.length;
        int m=image[0].length;
        //checking for neighbours (4 directional)
        for(int i=0;i<4;i++){
            int neighRow=sr+delRow[i];
            int neighCol=sc+delCol[i];
            if(neighRow>=0&&neighRow<n&& //checking validity of rows and cols
                    neighCol>=0&&neighCol<m
                    &&image[neighRow][neighCol]==initialColor //checking for same color neighbour
                    &&copy[neighRow][neighCol]!=newColor){  //checking if the neighbour has already been replaced with new color
                dfs1(neighRow,neighCol,image,copy,newColor,initialColor,delRow,delCol);
            }
        }
    }

    //rotten oranges
    //https://leetcode.com/problems/rotting-oranges/
    public class triad{
        int row;
        int col;
        int time;

        public triad(int row, int col, int time) {
            this.row = row;
            this.col = col;
            this.time = time;
        }
    }
    public int orangesRotting(int[][] grid) {
        int n=grid.length;
        int m=grid[0].length;
        int[][] vis=new int[n][m];
        Queue<triad> q=new LinkedList<>();
        int[] delRow={-1,0,1,0};
        int[] delCol={0,1,0,-1};
        int timeCount=0;
        int freshCount=0; //to keep track of the fresh oranges, in case some are unreachable
        int count=0; //to keep track of every fresh orange that has been rotten-ed
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==2){
                    q.add(new triad(i,j,0)); //add initial rotten to queue
                    vis[i][j]=2; //mark visit
                }
                else if(grid[i][j]==1){ //counting total fresh nodes initially present in the given matrix
                    freshCount++;
                }
            }
        }
        while(!q.isEmpty()){
            int row=q.peek().row;
            int col=q.peek().col;
            int time=q.peek().time;
            timeCount=Math.max(timeCount,time);
            q.poll();
            for(int i=0;i<4;i++){ //checking for valid neighbours (4 directional)
                int neighRow=row+delRow[i];
                int neighCol=col+delCol[i];
                if(neighRow>=0&&neighRow<n&&neighCol>=0&&neighCol<m&&vis[neighRow][neighCol]!=2&&grid[neighRow][neighCol]==1){
                    q.add(new triad(neighRow,neighCol,time+1));
                    vis[neighRow][neighCol]=2;
                    count++;
                }
            }
        }
        if(count!=freshCount){ //some fresh oranges were unreachable
            return -1;
        }
        return timeCount;
    }

    //Cycle Detection
    //https://practice.geeksforgeeks.org/problems/detect-cycle-in-an-undirected-graph/1

    //Using bfs
    public boolean isCycle(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] vis=new boolean[V]; //visited array
        for(int i=0;i<V;i++){
            if(!vis[i]){
                if(checkCycle(i,adj,vis)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean checkCycle(int sn, ArrayList<ArrayList<Integer>> adj, boolean[] vis){
        vis[sn]=true;
        Queue<Pair> q=new LinkedList<>();
        q.add(new Pair(sn,-1)); //the starting node is coming from nowhere, thus its source is -1
        while(!q.isEmpty()){
            Pair p=q.poll(); //popping front of queue
            int node=p.first;
            int parent=p.second;
            for(int i: adj.get(node)){ //neighbours of current node
                if(!vis[i]){
                    vis[i]=true;
                    q.add(new Pair(i,node));
                }
                else if(parent!=i){ //case we have a node which has been visited and is not a parent
                    return true; //cycle exists
                }
            }
        }
        return false;
    }

    //using dfs
    public boolean isCycle1(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] vis = new boolean[V];
        for (int i = 0; i < V; i++) {
            if (!vis[i]) {
                if (dfs2(i, -1, vis, adj)) { //since the graph can have multiple components, calling dfs for each
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dfs2(int node, int parent, boolean[] vis, ArrayList<ArrayList<Integer>> adj) {
        vis[node] = true;
        for (int i : adj.get(node)) {
            if (!vis[i]) {
                if (dfs2(i, node, vis, adj)) { //return true if any dfs returns true
                    return true;
                }
            }
            else if (parent != i) { //when a neighbour other than parent has already been visited
                return true;
            }
        }
        return false;
    }

}

