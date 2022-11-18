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
     
}

