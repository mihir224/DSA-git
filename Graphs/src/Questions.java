import java.lang.reflect.Array;
import java.util.*;

public class Questions {
    public static void main(String[] args) {

    }

    //number of provinces
    //https://leetcode.com/problems/number-of-provinces/
    public int findCircleNum(int[][] isConnected) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        boolean[] vis = new boolean[isConnected.length];
        int count = 0;
        for (int i = 0; i < isConnected.length; i++) {
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
        for (int i = 0; i < isConnected.length; i++) {
            if (!vis[i]) {
                count++;
                dfs(i, adj, vis);
            }
        }
        return count;
    }

    public void dfs(int node, ArrayList<ArrayList<Integer>> adj, boolean[] vis) {
        vis[node] = true;
        for (int i : adj.get(node)) {
            if (!vis[i]) {
                dfs(i, adj, vis);
            }
        }
    }

    //number of islands
    //https://practice.geeksforgeeks.org/problems/find-the-number-of-islands/1
    class Pair {
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        boolean[][] vis = new boolean[n][m];
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!vis[i][j] && grid[i][j] == '1') { //not visited and equal to 1 ie new island
                    count++;
                    bfs(i, j, vis, grid);
                }
            }
        }
        return count;
    }

    public void bfs(int row, int col, boolean[][] vis, char[][] grid) {
        vis[row][col] = true; //mark visit of current node
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(row, col)); //pushing current node to the queue
        int n = grid.length;
        int m = grid[0].length;
        while (!q.isEmpty()) {
            Pair p = q.poll();
            int i = p.first;
            int j = p.second;
            //find neighbours (checking all 8 directions of current node) and mark visit
            for (int delRow = -1; delRow <= 1; delRow++) {
                for (int delCol = -1; delCol <= 1; delCol++) {
                    int neighRow = i + delRow;
                    int neighCol = j + delCol;
                    if (neighRow >= 0 && neighRow < n && //to check for valid rows
                            neighCol >= 0 && neighCol < m && //to check for valid cols
                            grid[neighRow][neighCol] == '1' //to make sure that current node is a land
                            && !vis[neighRow][neighCol]) { //to make sure current node has not already been visited\
                        vis[neighRow][neighCol] = true;
                        q.add(new Pair(neighRow, neighCol)); //adding neighbours (lands) having value 1
                    }
                }
            }
        }
    }

    //flood fill
    //https://leetcode.com/problems/flood-fill/
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int initialColor = image[sr][sc];
        int[][] copy = image;
        int[] delRow = {-1, 0, 1, 0}; //possible values of row and col of all neighbours (in order)
        int[] delCol = {0, 1, 0, -1};
        dfs1(sr, sc, image, copy, color, initialColor, delRow, delCol);
        return copy;
    }

    public void dfs1(int sr, int sc, int[][] image, int[][] copy, int newColor, int initialColor, int[] delRow, int[] delCol) {
        copy[sr][sc] = newColor; //coloring starting pixel with new color
        int n = image.length;
        int m = image[0].length;
        //checking for neighbours (4 directional)
        for (int i = 0; i < 4; i++) {
            int neighRow = sr + delRow[i];
            int neighCol = sc + delCol[i];
            if (neighRow >= 0 && neighRow < n && //checking validity of rows and cols
                    neighCol >= 0 && neighCol < m
                    && image[neighRow][neighCol] == initialColor //checking for same color neighbour
                    && copy[neighRow][neighCol] != newColor) {  //checking if the neighbour has already been replaced with new color
                dfs1(neighRow, neighCol, image, copy, newColor, initialColor, delRow, delCol);
            }
        }
    }

    //rotten oranges
    //https://leetcode.com/problems/rotting-oranges/
    public class triad {
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
        int n = grid.length;
        int m = grid[0].length;
        int[][] vis = new int[n][m];
        Queue<triad> q = new LinkedList<>();
        int[] delRow = {-1, 0, 1, 0};
        int[] delCol = {0, 1, 0, -1};
        int timeCount = 0;
        int freshCount = 0; //to keep track of the fresh oranges, in case some are unreachable
        int count = 0; //to keep track of every fresh orange that has been rotten-ed
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 2) {
                    q.add(new triad(i, j, 0)); //add initial rotten to queue
                    vis[i][j] = 2; //mark visit
                } else if (grid[i][j] == 1) { //counting total fresh nodes initially present in the given matrix
                    freshCount++;
                }
            }
        }
        while (!q.isEmpty()) {
            int row = q.peek().row;
            int col = q.peek().col;
            int time = q.peek().time;
            timeCount = Math.max(timeCount, time);
            q.poll();
            for (int i = 0; i < 4; i++) { //checking for valid neighbours (4 directional)
                int neighRow = row + delRow[i];
                int neighCol = col + delCol[i];
                if (neighRow >= 0 && neighRow < n &&
                        neighCol >= 0 && neighCol < m &&
                        vis[neighRow][neighCol] != 2 &&
                        grid[neighRow][neighCol] == 1) {
                    q.add(new triad(neighRow, neighCol, time + 1));
                    vis[neighRow][neighCol] = 2;
                    count++;
                }
            }
        }
        if (count != freshCount) { //some fresh oranges were unreachable
            return -1;
        }
        return timeCount;
    }

    //Cycle Detection
    //https://practice.geeksforgeeks.org/problems/detect-cycle-in-an-undirected-graph/1

    //Using bfs
    public boolean isCycle(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] vis = new boolean[V]; //visited array
        for (int i = 0; i < V; i++) {
            if (!vis[i]) {
                if (checkCycle(i, adj, vis)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkCycle(int sn, ArrayList<ArrayList<Integer>> adj, boolean[] vis) {
        vis[sn] = true;
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(sn, -1)); //the starting node is coming from nowhere, thus its source is -1
        while (!q.isEmpty()) {
            Pair p = q.poll(); //popping front of queue
            int node = p.first;
            int parent = p.second;
            for (int i : adj.get(node)) { //neighbours of current node
                if (!vis[i]) {
                    vis[i] = true;
                    q.add(new Pair(i, node));
                } else if (parent != i) { //case we have a node which has been visited and is not a parent
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
            } else if (parent != i) { //when a neighbour other than parent has already been visited
                return true;
            }
        }
        return false;
    }

    public int numIslands1(char[][] isConnected) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        boolean[] vis = new boolean[isConnected.length];
        int count = 0;
        for (int i = 0; i < isConnected.length; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < isConnected.length; i++) { //converting adj matrix to adj list
            for (int j = 0; j < isConnected[0].length; j++) {
                if (isConnected[i][j] == '1' && i != j) {
                    adj.get(i).add(j);
                    adj.get(j).add(i);
                }
            }
        }
        for (int i = 0; i < isConnected.length; i++) {
            if (!vis[i]) {
                count++;
                dfs(i, adj, vis);
            }
        }
        return count;
    }

    //Distance of the nearest cell having 1
    //https://practice.geeksforgeeks.org/problems/distance-of-nearest-cell-having-1-1587115620/1
    public int[][] nearest(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        Queue<triad> q = new LinkedList<>();
        boolean[][] vis = new boolean[n][m];
        int[][] ans = new int[n][m];
        int[] delRow = {-1, 0, 1, 0};
        int[] delCol = {0, 1, 0, -1};
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    q.add(new triad(i, j, 0));
                    vis[i][j] = true;
                }
            }
        }
        while (!q.isEmpty()) {
            triad t = q.peek();
            int row = t.row;
            int col = t.col;
            int cnt = t.time;
            q.poll();
            ans[row][col] = cnt;
            for (int i = 0; i < 4; i++) {
                int neighRow = row + delRow[i];
                int neighCol = col + delCol[i];
                if (neighRow >= 0 && neighRow < grid.length &&
                        neighCol >= 0 && neighCol < grid[0].length &&
                        !vis[neighRow][neighCol]) {
                    vis[neighRow][neighCol] = true;
                    q.add(new triad(neighRow, neighCol, cnt + 1));
                }
            }
        }
        return ans;
    }

    //replace o's with x's
    //https://practice.geeksforgeeks.org/problems/replace-os-with-xs0052/1
    static char[][] fill(int n, int m, char a[][]) {
        int[] delRow = {-1, 0, 1, 0};
        int[] delCol = {0, 1, 0, -1};
        boolean[][] vis = new boolean[n][m];
        //traversing all boundaries and checking for 0's
        for (int j = 0; j < m; j++) {
            //first row
            if (a[0][j] == 'O' && !vis[0][j]) {
                dfs3(0, j, vis, a, delRow, delCol);
            }
            //last row
            if (a[n - 1][j] == 'O' && !vis[n - 1][j]) {
                dfs3(n - 1, j, vis, a, delRow, delCol);
            }
        }
        for (int i = 0; i < n; i++) {
            //first col
            if (a[i][0] == 'O' && !vis[i][0]) {
                dfs3(i, 0, vis, a, delRow, delCol);
            }
            //last col
            if (a[i][m - 1] == 'O' && !vis[i][m - 1]) {
                dfs3(i, m - 1, vis, a, delRow, delCol);
            }

        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!vis[i][j] && a[i][j] == 'O') { //converting 0s which were not visited by boundaries
                    a[i][j] = 'X';
                }
            }
        }
        return a;
    }

    public static void dfs3(int row, int col, boolean[][] vis, char[][] a, int[] delRow, int[] delCol) {
        vis[row][col] = true;
        int n = a.length;
        int m = a[0].length;
        for (int i = 0; i < 4; i++) {
            int neighRow = row + delRow[i];
            int neighCol = col + delCol[i];
            if (neighRow >= 0 && neighRow < n &&
                    neighCol >= 0 && neighCol < m &&
                    !vis[neighRow][neighCol] &&
                    a[neighRow][neighCol] == 'O') {
                dfs3(neighRow, neighCol, vis, a, delRow, delCol);
            }
        }
    }

    //number of enclaves (similar to previous problem)
    //https://practice.geeksforgeeks.org/problems/number-of-enclaves/1
    public int numberOfEnclaves(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int count = 0;
        int[] delRow = {-1, 0, 1, 0};
        int[] delCol = {0, 1, 0, -1};
        boolean[][] vis = new boolean[n][m];
        //traversing all boundaries
        for (int j = 0; j < m; j++) {
            if (grid[0][j] == 1 && !vis[0][j]) {
                dfs4(0, j, vis, grid, delRow, delCol);
            }
            if (grid[n - 1][j] == 1 && !vis[n - 1][j]) {
                dfs4(n - 1, j, vis, grid, delRow, delCol);
            }
        }
        for (int i = 0; i < n; i++) {
            if (grid[i][0] == 1 && !vis[i][0]) {
                dfs4(i, 0, vis, grid, delRow, delCol);
            }
            if (grid[i][m - 1] == 1 && !vis[i][m - 1]) {
                dfs4(i, m - 1, vis, grid, delRow, delCol);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1 && !vis[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void dfs4(int row, int col, boolean[][] vis, int[][] grid, int[] delRow, int[] delCol) {
        vis[row][col] = true;
        for (int i = 0; i < 4; i++) {
            int neighRow = row + delRow[i];
            int neighCol = col + delCol[i];
            if (neighRow >= 0 && neighRow < grid.length &&
                    neighCol >= 0 && neighCol < grid[0].length &&
                    !vis[neighRow][neighCol] &&
                    grid[neighRow][neighCol] == 1
            ) {
                dfs4(neighRow, neighCol, vis, grid, delRow, delCol);
            }
        }
    }

    //number of distinct islands
    //https://practice.geeksforgeeks.org/problems/number-of-distinct-islands/1
    int countDistinctIslands(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        boolean[][] vis = new boolean[n][m];
        HashSet<ArrayList<String>> st = new HashSet<>(); //to store the list of coordinate distances of every node on a particular island in a unique manner
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!vis[i][j] && grid[i][j] == 1) {
                    ArrayList<String> list = new ArrayList<>(); //since a hashset cannot detect duplicate lists of pair type, we store our coordinates in lists of string type
                    dfs5(i, j, vis, grid, list, i, j); //calling dfs for each 1 and taking i, j as base
                    st.add(list);
                }
            }
        }
        return st.size();
    }

    public void dfs5(int row, int col, boolean[][] vis, int[][] grid, ArrayList<String> list, int row0, int col0) {
        int n = grid.length;
        int m = grid[0].length;
        vis[row][col] = true;
        list.add(toString(row - row0, col - col0)); //storing the coordinate distances of each node in a current island
        //so that we can identify when two islands are exactly same by looking at their 1's coordinates in this list
        int[] delRow = {-1, 0, 1, 0};
        int[] delCol = {0, 1, 0, -1};
        for (int i = 0; i < 4; i++) {
            int neighRow = row + delRow[i];
            int neighCol = col + delCol[i];
            if (neighRow >= 0 && neighRow < n &&
                    neighCol >= 0 && neighCol < m &&
                    !vis[neighRow][neighCol] &&
                    grid[neighRow][neighCol] == 1) {
                dfs5(neighRow, neighCol, vis, grid, list, row0, col0);
            }
        }
    }

    public String toString(int row, int col) {
        return Integer.toString(row) + " , " + Integer.toString(col);
    }

    //check bipartite
    //https://practice.geeksforgeeks.org/problems/bipartite-graph/1
    //using bfs
    public boolean isBipartite(int V, ArrayList<ArrayList<Integer>> adj) {
        int[] color = new int[V];
        for (int i = 0; i < V; i++) {
            color[i] = -1; //mark all not colored
        }
        for (int i = 0; i < V; i++) { //checking for all components
            if (color[i] == -1) {
                if (!checkBipartite(i, V, adj, color)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkBipartite(int sn, int V, ArrayList<ArrayList<Integer>> adj, int[] color) {
        Queue<Integer> q = new LinkedList<>();
        color[sn] = 0;
        q.add(sn);
        while (!q.isEmpty()) {
            int node = q.poll();
            for (int i : adj.get(node)) {
                if (color[i] == -1) { //color the neighbour
                    color[i] = 1 - color[node];  //if color of node is 0, this will make color of its neighbour 1 o/w 0
                    q.add(i); //add to queue
                } else if (color[i] == color[node]) { //adjacent nodes have same colour
                    return false;
                }
            }
        }
        return true; //colored all nodes, no two adjacent nodes had same color
    }

    //using dfs
    public boolean isBipartite1(int V, ArrayList<ArrayList<Integer>> adj) {
        int[] color = new int[V];
        for (int i = 0; i < V; i++) {
            color[i] = -1; //mark all not colored
        }
        for (int i = 0; i < V; i++) { //checking for all components
            if (color[i] == -1) {
                if (!dfs6(i, 0, adj, color)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean dfs6(int node, int clr, ArrayList<ArrayList<Integer>> adj, int[] color) {
        color[node] = clr;
        for (int i : adj.get(node)) {
            if (color[i] == -1) {
                if (!dfs6(i, 1 - clr, adj, color)) {
                    return false;
                }
            } else if (color[i] == color[node]) {
                return false;
            }
        }
        return true;
    }

    //detect cycle in an undirected graph
    //https://practice.geeksforgeeks.org/problems/detect-cycle-in-a-directed-graph/1

    //dfs (using concept of path vis array)
    public boolean isCyclic(int V, ArrayList<ArrayList<Integer>> adj) {
        int[] vis = new int[V];
        int[] path = new int[V];
        for (int i = 0; i < V; i++) { //checking for each component
            if (vis[i] == 0) {
                if (dfs7(i, adj, vis, path)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dfs7(int node, ArrayList<ArrayList<Integer>> adj, int[] vis, int[] path) {
        vis[node] = 1;
        path[node] = 1; //mark path visit
        for (int i : adj.get(node)) {
            if (vis[i] == 0) {
                if (dfs7(i, adj, vis, path)) {
                    return true;
                }
            } else if (path[i] == 1) { //vis and path visited
                return true;
            }
        }

        path[node] = 0; //mark path unvisit if we don't find any cycle
        return false;
    }

    //bfs (using topological sort - bfs (kahn's algo))
    public boolean isCyclic1(int V, ArrayList<ArrayList<Integer>> adj) {
        int[] inDegree=new int[V];
        Queue<Integer> q=new LinkedList<>();
        List<Integer> list=new ArrayList<>();
       for(int i=0;i<V;i++){
           for(int j:adj.get(i)){
               inDegree[j]++;
           }
       }
        for(int i=0;i<V;i++){
            if(inDegree[i]==0){ //pushing all nodes with indegree zero into the queue
                q.add(i);
            }
        }
        while(!q.isEmpty()){
            int node=q.poll();
            list.add(node);
            for(int i:adj.get(node)){
                inDegree[i]--;
                if(inDegree[i]==0){
                    q.add(i);
                }
            }
        }
        if(list.size()<V){
            return true;
        }
        return false;


    }

    //eventual safe states
    //https://practice.geeksforgeeks.org/problems/eventual-safe-states/1
    List<Integer> eventualSafeNodes(int V, List<List<Integer>> adj) {
        int[] vis = new int[V];
        int[] path = new int[V];
        int[] check = new int[V];
        List<Integer> lis = new ArrayList<>();
        for (int i = 0; i < V; i++) { //checking for each component
            if (vis[i] == 0) {
                dfs8(i, adj, vis, path, check);
            }
        }
        for (int i = 0; i < V; i++) {
            if (check[i] == 1) {
                lis.add(i);
            }
        }
        return lis;
    }

    public boolean dfs8(int node, List<List<Integer>> adj, int[] vis, int[] path, int[] check) {
        vis[node] = 1;
        path[node] = 1; //mark path visit
        for (int i : adj.get(node)) {
            if (vis[i] == 0) {
                if (dfs8(i, adj, vis, path, check)) {
                    return true;
                }
            } else if (path[i] == 1) { //vis and path visited
                return true;
            }
        }
        check[node] = 1; //no cycle was encountered, mark node safe
        path[node] = 0; //mark path unvisited
        return false;
    }

    //course schedule
    //https://leetcode.com/problems/course-schedule/
    public boolean canFinish(int N, int[][] prerequisites) {
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        int[] inDegree=new int[N];
        Queue<Integer> q=new LinkedList<>();
        List<Integer> list=new ArrayList<>();
        for(int i=0;i<N;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<prerequisites.length;i++){
            adj.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }
        for(int i=0;i<adj.size();i++){
            for(int j=0;j<adj.get(i).size();j++){
                inDegree[adj.get(i).get(j)]++; //marking in-degree of all nodes
            }
        }
        for(int i=0;i<N;i++){
            if(inDegree[i]==0){
                q.add(i);
            }
        }
        while(!q.isEmpty()){
            int node=q.poll();
            list.add(node);
            for(int i:adj.get(node)){
                inDegree[i]--;
                if(inDegree[i]==0){
                    q.add(i);
                }
            }
        }
        if(list.size()<N){
            return false;
        }
        return true;
    }

    //alien dictionary
    //https://practice.geeksforgeeks.org/problems/alien-dictionary/1
    public String findOrder(String [] dict, int N, int K)
    {
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        for(int i=0;i<K;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<N-1;i++){ //traversing the dictionary till N-1 as we have to work in pairs and thus we have to access (i+1) element
            String s1=dict[i];
            String s2=dict[i+1];
            int len=Math.min(s1.length(),s2.length()); //checking the pair of strings till the length of the smaller string
            for(int ptr=0;ptr<len;ptr++){
                if(s1.charAt(ptr)!=s2.charAt(ptr)) { //an alphabet comes before the other, thus there is an edge between the two,
                    // implying that the first alphabet is directed to the second, therefore adding it to the adjacency
                    adj.get(s1.charAt(ptr) - 'a').add(s2.charAt(ptr) - 'a'); //adding the numeric value of the alphabets
                    break;
                }
            }
        }
        List<Integer> list=new ArrayList<>();
        Queue<Integer> q=new LinkedList<>();
        int[] inDegree=new int[K];

        String ans="";
        for(int i=0;i<adj.size();i++){
            for(int j=0;j<adj.get(i).size();j++){
                inDegree[adj.get(i).get(j)]++;
            }
        }
        for(int i=0;i<inDegree.length;i++){
            if(inDegree[i]==0){
                q.add(i);
            }
        }
        while(!q.isEmpty()){
            int node=q.poll();
            list.add(node);
            for(int i:adj.get(node)){
                inDegree[i]--;
                if(inDegree[i]==0){
                    q.add(i);
                }
            }
        }
        for(int i:list){
            ans+=(char)(i+(int)('a'));
        }
        return ans;
    }

    //shortest path in an acyclic graph
    //https://practice.geeksforgeeks.org/problems/shortest-path-in-undirected-graph/1
    public int[] shortestPath(int N,int M, int[][] edges) {
        int[] dist = new int[N];
        ArrayList<ArrayList<Pair>> adj = new ArrayList<>();
        boolean[] vis = new boolean[N];
        for (int i = 0; i < N; i++) { //initializing the adj list
            adj.add(new ArrayList<Pair>());
        }

        for (int i = 0; i < dist.length; i++) {
            dist[i] = (int)(1e9);
        }
        dist[0] = 0; //0 is the source node
        for (int i = 0; i <M; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int wt = edges[i][2];
            adj.get(u).add(new Pair(v, wt));
        }
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < N; i++) { //storing toposort in the stack
            if (!vis[i]) {
                dfs9(i, adj, vis, st);
            }
        }
        while (!st.isEmpty()) {
            int node = st.pop();
            for (int i = 0; i < adj.get(node).size(); i++) {
                int v = adj.get(node).get(i).first;
                int wt = adj.get(node).get(i).second;
                if (dist[node] + wt < dist[v]) {
                    dist[v] = dist[node] + wt; //update distance if it is smaller than current
                }
            }
        }
        for (int i = 0; i < N; i++) {
            if (dist[i] == (int)(1e9)) { //case when a node is unreachable from source
                dist[i] = -1;
            }
        }
        return dist;
    }
    public void dfs9(int node, ArrayList<ArrayList<Pair>> adj, boolean[] vis, Stack<Integer> st) {
        vis[node] = true;
        for (int i = 0; i < adj.get(node).size(); i++) {
            int v = adj.get(node).get(i).first;
            if (!vis[v]) {
                dfs9(v, adj, vis, st);
            }
        }
        st.push(node);
    }

    //shortest path in a directed graph having unit distance
    //https://practice.geeksforgeeks.org/problems/shortest-path-in-undirected-graph-having-unit-distance/1
    public int[] shortestPath(int[][] edges,int n,int m ,int src) {
        int[] dist=new int[n];
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<m;i++){
            int u=edges[i][0];
            int v=edges[i][1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
        Queue<Integer> q=new LinkedList<>();
        for(int i=0;i<n;i++){
            dist[i]=(int)(1e9);
        }
        dist[src]=0;
        q.add(src);
        while(!q.isEmpty()){
            int node=q.poll();
            for(int i:adj.get(node)) {
                if (dist[node] + 1 < dist[i]) {
                    dist[i] = dist[node] + 1;
                    q.add(i);
                }
            }
        }
        for(int i=0;i<n;i++){
            if(dist[i]==(int)(1e9)){
                dist[i]=-1;
            }
        }
        return dist;
    }

    //word ladder
    //https://practice.geeksforgeeks.org/problems/word-ladder/1
    class Pair1 {
        String first;
        int second;

        public Pair1(String first, int second) {
            this.first = first;
            this.second = second;
        }
    }
    public int wordLadderLength(String startWord, String targetWord, String[] wordList)
    {
        Queue<Pair1> q=new LinkedList<>();
        q.add(new Pair1(startWord,1)); //initial config
        Set<String> set=new HashSet<>();
        for(int i=0;i<wordList.length;i++){
            set.add(wordList[i]);
        }
        set.remove(startWord); //removing the start word from set, thus marking its visit
        while(!q.isEmpty()){
            String word=q.peek().first;
            int length=q.peek().second;
            q.poll();
            if(word.equals(targetWord)) { //found target word
                return length;
            }
            for(int i=0;i<word.length();i++){
                for(char ch='a';ch<='z';ch++){
                    char[] replacedCharArr=word.toCharArray();
                    replacedCharArr[i]=ch;
                    String replacedWord=new String(replacedCharArr);
                    if(set.contains(replacedWord)){
                        set.remove(replacedWord);
                        q.add(new Pair1(replacedWord,length+1));
                    }
                }
            }
        }
        return 0; //return 0 if q is empty
    }

    //word ladder 2
    //https://practice.geeksforgeeks.org/problems/word-ladder-ii/1
    //this soln works on gfg but not on leetcode
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        //initial config
        Set<String> set=new HashSet<>();
        for(int i=0;i<wordList.size();i++){
               set.add(wordList.get(i));
           }
        Queue<ArrayList<String>> q=new LinkedList<>();
        ArrayList<String> ls=new ArrayList<>();
        ls.add(beginWord);
        q.add(ls);
        List<List<String>> ans=new ArrayList<>();
        List<String> used=new ArrayList<>(); //to keep track of words that have been used in previous levels for transformations
        used.add(beginWord);
        int level=0;
        while(!q.isEmpty()){
            ArrayList<String> list=q.poll();
            //remove all words that have been previously used for transformation
            if(list.size()>level){ //when the new sequence has a size greater than current level
                level++;
                for(String s:used){
                    set.remove(s);
                }
            }
            String word=list.get(list.size()-1);
            if(word.equals(endWord)){ //found end word
                if(ans.size()==0){ //first ans sequence
                    ans.add(list);
                }
                else if(list.size()==ans.get(0).size()){ //storing only those sequences which match the length of the first ans sequence
                    ans.add(list);
                }
            }
            for(int i=0;i<word.length();i++){
                for(char ch='a';ch<='z';ch++){
                    char[] replacedCharArr=word.toCharArray();
                    replacedCharArr[i]=ch;
                    String replacedWord=new String(replacedCharArr);
                    if(set.contains(replacedWord)){
                        list.add(replacedWord);
                        ArrayList<String> temp=new ArrayList<>(list); //we store list in temp coz when we delete the last
                        // element from the list, it would be deleted everywhere
                        q.add(temp);
                        used.add(replacedWord); //marking the new replaced word as used after adding it to the sequence
                        // and adding that sequence to queue
                        list.remove(list.size()-1); //this is to remove the last element from the list that is to
                        // be added to the queue so that the new word can be inserted in place of the previous word
                    }
                }
            }
        }
        return ans;
    }

    //shortest distance in a binary maze
    //https://practice.geeksforgeeks.org/problems/shortest-path-in-a-binary-maze-1655453161/1
    class triad1{
        int dist;
        int row;
        int col;
        public triad1(int dist, int row, int col){
            this.dist=dist;
            this.row=row;
            this.col=col;
        }
    }
    int shortestPath(int[][] grid, int[] source, int[] destination) {
        if(source[0]==destination[0]&&source[1]==destination[1]){ //case when the source is the destination
            return 0;
        }
        int n=grid.length;
        int m=grid[0].length;
        Queue<triad1> q=new LinkedList<>();
        int[][] dist=new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                dist[i][j]=(int)(1e9);
            }
        }
        dist[source[0]][source[1]]=0;
        q.add(new triad1(0,source[0],source[1]));
        int[] delRow={-1,0,1,0};
        int[] delCol={0,1,0,-1};
        while(!q.isEmpty()){
            int dis=q.peek().dist;
            int row=q.peek().row;
            int col=q.peek().col;
            q.poll();
            for(int i=0;i<4;i++){ //moving 4-directionally
                int newRow=row+delRow[i];
                int newCol=col+delCol[i];
                if(newRow>=0&&newRow<n&&
                        newCol>=0&&newCol<m&&
                        grid[newRow][newCol]==1&&
                        dis+1<dist[newRow][newCol]){
                    dist[newRow][newCol]=dis+1;
                    if(newRow==destination[0]&&newCol==destination[1]){
                        return dis+1;
                    }
                    q.add(new triad1(dis+1, newRow, newCol));
                }
            }
        }
        return -1;
    }

    //leetcode soln(8-directional)
    //https://leetcode.com/problems/shortest-path-in-binary-matrix/description/
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n=grid.length;
        int[] source={0,0};
        int[] destination={n-1,n-1};
        if(grid[0][0]==1||grid[n-1][n-1]==1){
            return -1;
        }
        if(source[0]==destination[0]&&source[1]==destination[1]){ //case when the source is the destination
            return 1;
        }
        Queue<triad1> q=new LinkedList<>();
        int[][] dist=new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                dist[i][j]=(int)(1e9);
            }
        }
        dist[source[0]][source[1]]=0;
        q.add(new triad1(1,source[0],source[1]));
        int[] delRow={-1,-1,0,1,1,1,0,-1};
        int[] delCol={0,1,1,1,0,-1,-1,-1};
        while(!q.isEmpty()){
            int dis=q.peek().dist;
            int row=q.peek().row;
            int col=q.peek().col;
            q.poll();
            for(int i=0;i<8;i++){
                int newRow=row+delRow[i];
                int newCol=col+delCol[i];
                if(newRow>=0&&newRow<n&&
                        newCol>=0&&newCol<n&&
                        grid[newRow][newCol]==0&&
                        dis+1<dist[newRow][newCol]){
                    dist[newRow][newCol]=dis+1;
                    if(newRow==destination[0]&&newCol==destination[1]){
                        return dis+1;
                    }
                    q.add(new triad1(dis+1, newRow, newCol));
                }
            }
        }
        return -1;
    }

    //path with minimum effort
    //https://leetcode.com/problems/path-with-minimum-effort/
    class triad2{
        int diff;
        int row;
        int col;
        public triad2(int diff, int row, int col){
            this.diff=diff;
            this.row=row;
            this.col=col;
        }
    }
    public int minimumEffortPath(int[][] heights) {
        int n=heights.length;
        int m=heights[0].length;
        //initial config
        int[][] dist=new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                dist[i][j]=(int)(1e9);
            }
        }
        dist[0][0]=0;
        PriorityQueue<triad2> pq=new PriorityQueue<>((x,y)->x.diff-y.diff);
        pq.add(new triad2(0,0,0));
        int[] delRow={-1,0,1,0};
        int[] delCol={0,1,0,-1};
        while(!pq.isEmpty()){
            triad2 t=pq.poll();
            int diff=t.diff;
            int row=t.row;
            int col=t.col;
            if(row==n-1&&col==m-1){ //reached bottom right
                return diff; //this will return min effort
            }
            for(int i=0;i<4;i++){
                int nrow=row+delRow[i];
                int ncol=col+delCol[i];
                if(nrow>=0&&nrow<n&&
                        ncol>=0&&ncol<m){
                    int newEffort=Math.max(Math.abs(heights[row][col]-heights[nrow][ncol]),diff);
                    if(newEffort<dist[nrow][ncol]){
                        dist[nrow][ncol]=newEffort;
                        pq.add(new triad2(newEffort,nrow,ncol));
                    }
                }
            }
        }
        return 0;
    }

    //cheapest flights within k stops
    //https://leetcode.com/problems/cheapest-flights-within-k-stops/
    class triad3{
        int stops;
        int city;
        int cost;
        public triad3(int stops,int city,int cost){
            this.stops=stops;
            this.city=city;
            this.cost=cost;
        }
    }
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
        //building the graph
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i< flights.length;i++){
            adj.get(flights[i][0]).add(new Pair(flights[i][1],flights[i][2]));
        }
        Queue<triad3> q=new LinkedList<>();
        q.add(new triad3(0,src,0));
        int[] dist=new int[n];
        for(int i=0;i<n;i++){
            dist[i]=(int)(1e9);
        }
        dist[src]=0;
        while(!q.isEmpty()){
            triad3 t=q.poll();
            int stops=t.stops;
            int city=t.city;
            int cost=t.cost;
            if(stops>k){ //reject path if stops exceed k
                continue;
            }
            for(Pair p:adj.get(city)){
                int adjCity=p.first;
                int cst=p.second;
                if(cost+cst<dist[adjCity]){
                    dist[adjCity]=cost+cst;
                    q.add(new triad3(stops+1,adjCity,cost+cst));
                }
            }
        }
        if(dist[dst]==1e9){
            return -1;
        }
        return dist[dst];
    }

    //
    //
    int minimumMultiplications(int[] arr, int start, int end) {
        //initial config
        Queue<Pair> pq=new LinkedList<>();
        pq.add(new Pair(0,start));
        int[] dist=new int[100000];
        for(int i=0;i<dist.length;i++){
            dist[i]=(int)(1e9);
        }
        dist[start]=0;
        while(!pq.isEmpty()){
            Pair p=pq.poll();
            int steps=p.first;
            int num=p.second;
            for(int i=0;i<arr.length;i++){
                int newStart=(num*arr[i])%100000;
                if(steps+1<dist[newStart]){
                    dist[newStart]=steps+1;
                    if(newStart==end){
                        return steps+1;
                    }
                   pq.add(new Pair(steps+1,newStart));
                }
            }
        }
        return -1;
    }


}
