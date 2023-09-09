import java.util.*;

class Graphs{
    //clone the graph
    //https://leetcode.com/problems/clone-graph/

    //O(N) - for traversing all nodes+O(2M) - finding neighbours of each node ie (total degree of graph where M is the number of edges)
    //We have to create a deep copy of the whole graph ie we cannot just use the same node addresses as in the original
    // graph to form the clone. We have to make new nodes with new addresses and exact same values and positioning as original nodes.
    // For this purpose we use a map to map a node to its newly created copy. Then, we recursively traverse the original graph and
    // for each node that we are at, we create its new copy if it doesn't already exist in the map and, then we traverse the current
    // node's neighbours, create their copy recursively then and add them to the neighbours of the copy we just created.
    // If the map already contains current node's copy, we simply return it otherwise we return the new copy we just created
    // after adding neighbours to it. We do this recursively for each node.

    //so the map basically acts as a visited array to keep track of whether a node's copy has been created or not.

    class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
    public Node cloneGraph(Node node) {
        if(node==null){
            return null;
        }
        HashMap<Node,Node> map=new HashMap<>();
        return dfs(node,map);
    }
    public Node dfs(Node node, HashMap<Node,Node> map){
        if(map.containsKey(node)){
            return map.get(node);
        }
        Node copy=new Node(node.val); //creating a new copy of current node
        map.put(node,copy);
        for(Node i:node.neighbors){
            copy.neighbors.add(dfs(i,map)); //adding the copy of original node's neighbours to the list of neighbours of
            // copy of the original node
        }
        return copy;
    }

    //dfs
    //https://practice.geeksforgeeks.org/problems/depth-first-traversal-for-a-graph/1
    public ArrayList<Integer> dfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        ArrayList<Integer> ans=new ArrayList<>();
        boolean[] vis=new boolean[V];
        dfs(0,ans,adj,vis);
        return ans;
    }
    public void dfs(int node, ArrayList<Integer> ans, ArrayList<ArrayList<Integer>> adj, boolean[] vis){
        ans.add(node);
        vis[node]=true;
        for(int i:adj.get(node)){
            if(!vis[i]){
                dfs(i,ans,adj,vis);
            }
        }
    }

    //bfs
    //https://practice.geeksforgeeks.org/problems/bfs-traversal-of-graph/1
    public ArrayList<Integer> bfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        Queue<Integer> q=new LinkedList<>();
        boolean[] vis=new boolean[V];
        ArrayList<Integer> ans=new ArrayList<>();
        q.offer(0);
        vis[0]=true;
        while(!q.isEmpty()){
            int node=q.poll();
            ans.add(node);
            for(int i:adj.get(node)){
                if(!vis[i]){
                    vis[i]=true;
                    q.add(i);
                }
            }
        }
        return ans;
    }


    //cycle detection in an undirected graph
    //https://www.codingninjas.com/studio/problems/1062670?topList=striver-sde-sheet-problems&utm_source=striver&utm_medium=website

    //bfs
    static class Pair{
        int first;
        int second;
        public Pair(int first, int second){
            this.first=first;
            this.second=second;
        }
    }
    public static String cycleDetection(int[][] edges, int n, int m) {
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        boolean[] vis=new boolean[n+1];
        for(int i=0;i<=n;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<m;i++){
            adj.get(edges[i][0]).add(edges[i][1]);
            adj.get(edges[i][1]).add(edges[i][0]);
        }
        for(int i=1;i<=n;i++){
            if(!vis[i]){
                if(bfs(i,adj,n,vis)){
                    return "Yes";
                }
            }
        }
        return "No";
    }

    public static boolean bfs(int node,ArrayList<ArrayList<Integer>> adj,int n,boolean[] vis){
        vis[node]=true;
        Queue<Pair> q=new LinkedList<>();
        q.offer(new Pair(node,-1));
        while(!q.isEmpty()){
            Pair p=q.poll();
            for(int i:adj.get(p.first)){
                if(!vis[i]){
                    vis[i]=true;
                    q.offer(new Pair(i,p.first));
                }
                else if(i!=p.second){
                    return true;
                }
            }
        }
        return false;
    }

    //dfs
    public static String cycleDetection1(int[][] edges, int n, int m) {
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        boolean[] vis=new boolean[n+1];
        for(int i=0;i<=n;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<m;i++){
            adj.get(edges[i][0]).add(edges[i][1]);
            adj.get(edges[i][1]).add(edges[i][0]);
        }
        for(int i=1;i<=n;i++){
            if(!vis[i]){
                if(dfs(i,-1,adj,vis)){
                    return "Yes";
                }
            }
        }
        return "No";
    }
    public static boolean dfs(int node, int parent, ArrayList<ArrayList<Integer>> adj, boolean[] vis){
        vis[node]=true;
        for(int i:adj.get(node)){
            if(!vis[i]){
                if(dfs(i,node,adj,vis)){
                    return true;
                }
            }
            else if(i!=parent){
                return true;
            }
        }
        return false;
    }

    //cycle detection in directed graph
    //https://leetcode.com/problems/course-schedule

    //dfs

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        boolean[] vis=new boolean[numCourses];
        boolean[] pathVis=new boolean[numCourses];
        for(int i=0;i<numCourses;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<prerequisites.length;i++){
            adj.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }
        for(int i=0;i<numCourses;i++){
            if(!vis[i]){
                if(dfs(i,vis,pathVis,adj)){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean dfs(int node,boolean[] vis,boolean[] pathVis,ArrayList<ArrayList<Integer>> adj){
        vis[node]=true;
        pathVis[node]=true;
        for(int i:adj.get(node)){
            if(!vis[i]){
                if(dfs(i,vis,pathVis,adj)){
                    return true;
                }
            }
            else if(pathVis[i]){
                return true;
            }
        }
        pathVis[node]=false;
        return false;
    }

    //bfs (kahn's algo - toposort)
    public boolean canFinish1(int numCourses, int[][] prerequisites) {
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        int[] inDegree=new int[numCourses];
        for(int i=0;i<numCourses;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<prerequisites.length;i++){
            adj.get(prerequisites[i][1]).add(prerequisites[i][0]);
            inDegree[prerequisites[i][0]]++;
        }
        if(bfs(inDegree,adj)){
            return true;
        }
        return false;
    }

    public boolean bfs(int[] inDegree, ArrayList<ArrayList<Integer>> adj){
        Queue<Integer> q=new LinkedList<>();
        ArrayList<Integer> topo=new ArrayList<>();
        for(int i=0;i<inDegree.length;i++){
            if(inDegree[i]==0){
                q.offer(i);
            }
        }
        while(!q.isEmpty()){
            int n=q.poll();
            topo.add(n);
            for(int i:adj.get(n)){
                inDegree[i]--;
                if(inDegree[i]==0){ //dealt with every node which has an edge directed to this node
                    q.offer(i);
                }
            }
        }
        return topo.size()==adj.size();
    }

    //topo sort
    //https://practice.geeksforgeeks.org/problems/topological-sort/1
    //dfs
    //O(V+E)

    static int[] topoSort(int V, ArrayList<ArrayList<Integer>> adj)
    {
        Stack<Integer> st=new Stack<>();
        int[] ans=new int[V];
        boolean[] vis=new boolean[V];
        ArrayList<Integer> a=new ArrayList<>();
        for(int i=0;i<V;i++){
            if(!vis[i]){
                dfs(i,adj,st,vis);
            }
        }
        while(!st.isEmpty()){
            a.add(st.pop());
        }
        for(int i=0;i<a.size();i++){
            ans[i]=a.get(i);
        }
        return ans;
    }
    public static void dfs(int node,ArrayList<ArrayList<Integer>> adj, Stack<Integer> st,boolean[] vis){
        vis[node]=true;
        for(int i:adj.get(node)){
            if(!vis[i]){
                dfs(i,adj,st,vis);
            }
        }
        st.push(node);
    }

    //bfs (already done above)

    //alien dictionary (good problem based on topo sort)
    //https://practice.geeksforgeeks.org/problems/alien-dictionary/1
    public String findOrder(String [] dict, int N, int K)
    {
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        int[] indegree=new int[K];
        StringBuilder sb=new StringBuilder();
        Queue<Integer> q=new LinkedList<>();
        //O(k)
        for(int i=0;i<K;i++){
            adj.add(new ArrayList<>());
        }
        //O(N*M) where M is the avg length of a string
        for(int i=0;i<dict.length-1;i++){
            String s1=dict[i];
            String s2=dict[i+1];
            int j=0;
            int min=Math.min(s1.length(),s2.length());
            while(j<min&&s1.charAt(j)==s2.charAt(j)){
                j++;
            }
            if(j==min){
                continue;
            }
            adj.get(s1.charAt(j)-'a').add(s2.charAt(j)-'a');
            indegree[s2.charAt(j)-'a']++;
        }
        //O(K)
        for(int i=0;i<indegree.length;i++){
            if(indegree[i]==0){
                q.offer(i);
            }
        }
        //O(V+E)
        while(!q.isEmpty()){
            int node=q.poll();
            sb.append((char)(node+'a'));
            for(int i:adj.get(node)){
                indegree[i]--;
                if(indegree[i]==0){ //dealt with every node which has an edge directed to this node
                    q.offer(i);
                }
            }
        }
        return sb.toString();
    }

    //number of islands
    //https://leetcode.com/problems/number-of-islands/

    //we can either convert the given matrix into a adj list or we can directly solve it through the given matrix. In both
    // approaches we can use either dfs or bfs. the graph approach would be just using basic dfs and counting the number
    // of times the dfs is being called whereas in the grid approach we do the same thing, the only difference being that
    // since here we don't have the adj list, we try to find the all the 4 directional neighbours for any node using the
    // conventional way. below we have used the grid approach

    //dfs
    //O(4*m*n)==O(m*n) time and O(m*n)space - since in the worst case we visit all cells in one dfs call
    public int numIslands(char[][] grid) {
        int m=grid.length;
        int n=grid[0].length;
        int count=0;
        boolean[][] vis=new boolean[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(!vis[i][j]&&grid[i][j]=='1'){
                    dfs(i,j,vis,grid,m,n);
                    count++;
                }
            }
        }
        return count;
    }
    public void dfs(int row,int col,boolean[][] vis,char[][] grid,int m, int n){
        vis[row][col]=true;
        for(int delrow=-1;delrow<=1;delrow++){
            for(int delcol=-1;delcol<=1;delcol++){
                if(delrow==-1&&delcol==-1||delrow==1&&delcol==-1||delrow==-1&&delcol==1||delrow==1&&delcol==1){ //ignoring diagonals because it is given that neighbours can only be vertical or horizontal
                continue;
            }
                int neighrow=row+delrow;
                int neighcol=col+delcol;
                if(neighrow<m&&neighrow>=0&&neighcol<n&&neighcol>=0&&!vis[neighrow][neighcol]&&grid[neighrow][neighcol]=='1'){
                    dfs(neighrow,neighcol,vis,grid,m,n);
                }
            }
        }
    }

    //bfs
    public int numIslands1(char[][] grid) {
        int m=grid.length;
        int n=grid[0].length;
        int count=0;
        boolean[][] vis=new boolean[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(!vis[i][j]&&grid[i][j]=='1'){
                    bfs(i,j,vis,grid,m,n);
                    count++;
                }
            }
        }
        return count;
    }
    public void bfs(int row,int col, boolean[][] vis, char[][] grid,int m, int n){
        Queue<Pair> q=new LinkedList<>();
        vis[row][col]=true;
        q.offer(new Pair(row,col));
        while(!q.isEmpty()){
            Pair p=q.poll();
            int r=p.first;
            int c=p.second;
            for(int delrow=-1;delrow<=1;delrow++){
                for(int delcol=-1;delcol<=1;delcol++){
                    if(delrow==-1&&delcol==-1||delrow==1&&delcol==-1||delrow==-1&&delcol==1||delrow==1&&delcol==1){ //ignoring diagonals because it is given that neighbours can only be vertical or horizontal
                        continue;
                    }
                    int neighrow=r+delrow;
                    int neighcol=c+delcol;
                    if(neighrow<m&&neighrow>=0&&neighcol<n&&neighcol>=0&&!vis[neighrow][neighcol]&&grid[neighrow][neighcol]=='1'){
                        vis[neighrow][neighcol]=true;
                        q.offer(new Pair(neighrow,neighcol));
                    }
                }
            }
        }
    }

    //bipartite graph
    //https://leetcode.com/problems/is-graph-bipartite/

    //dfs
    //O(V+2E)
    public boolean isBipartite(int[][] graph) {
        int[] color=new int[graph.length];
        Arrays.fill(color,-1);
        for(int i=0;i<graph.length;i++){
            if(color[i]==-1) {
                if (!dfsBipartite(i, graph, color, 0)) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean dfsBipartite(int node,int[][] graph,int[] color,int clr){
        color[node]=clr;
        for(int i:graph[node]){
            if(color[i]==-1){
                if(!dfsBipartite(i,graph,color,1-clr)){
                    return false;
                }
            }
            else if(color[node]==color[i]){
                return false;
            }
        }
        return true;
    }

    //bfs
    public boolean isBipartite1(int[][] graph) {
        int[] color=new int[graph.length];
        Arrays.fill(color,-1);
        for(int i=0;i<graph.length;i++){
            if(color[i]==-1) {
                if (!bfsBipartite(i, graph, color, 0)) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean bfsBipartite(int node, int[][] graph,int [] color, int clr){
        color[node]=clr;
        Queue<Integer> q=new LinkedList<>();
        q.offer(node);
        while(!q.isEmpty()){
            int n=q.poll();
            for(int i:graph[n]){
                if(color[i]==-1){
                    color[i]=1-color[n];
                    q.offer(i);
                }
                else if(color[i]==color[n]){
                    return false;
                }
            }
        }
        return true;
    }

    //flood fill
    //https://leetcode.com/problems/flood-fill/

    //time: O((N*M): case when all nodes are connected ie have same value * 4 (each node has 4 neighbours)) == O(N*M)
    //space: O(2(N*M)) - copy array, recursive stack space

    //dfs
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        if(image[sr][sc]==color){ //in case sn is already colored with the given color. here, since it is necessary for
            // the neighbours to have the same color as the sn to be flood filled with the given color, so if the sn is
            // colored with the given color, we'd want to flood fill those neighbours of this sn with the given color
            // which have the same color as the sn. now since sn color is same as given color, we'd end up coloring the
            // neighbours with the color they already have and thus we return image as it is.
            return image;
        }
        int[][] copy=image;
        int[] delrow={-1,0,1,0};
        int[] delcol={0,1,0,-1};
        dfs(sr,sc,delrow,delcol,copy,color,image[sr][sc]);
        return copy;
    }
    public void dfs(int sr, int sc,int[] delrow,int[] delcol,int[][] copy,int color,int initialClr){
        copy[sr][sc]=color;
        for(int i=0;i<4;i++){
            int nrow=sr+delrow[i];
            int ncol=sc+delcol[i];
            if(nrow>=0&&nrow<copy.length&&ncol>=0&&ncol<copy[0].length&&copy[nrow][ncol]==initialClr){
                dfs(nrow,ncol,delrow,delcol,copy,color,initialClr);
            }
        }
    }

    //bfs
    class Solution {
        public int[][] floodFill(int[][] image, int sr, int sc, int color) {
            int[] delrow={-1,0,1,0};
            int[] delcol={0,1,0,-1};
            int initialColor=image[sr][sc];
            if(initialColor==color){
                return image;
            }
            Queue<Pair> q=new LinkedList<>();
            image[sr][sc]=color;
            q.offer(new Pair(sr,sc));
            while(!q.isEmpty()){
                Pair p=q.poll();
                int row=p.row;
                int col=p.col;
                for(int i=0;i<4;i++){
                    int nrow=row+delrow[i];
                    int ncol=col+delcol[i];
                    if(nrow>=0&&nrow<image.length&&ncol>=0&&ncol<image[0].length&&image[nrow][ncol]==initialColor){
                        image[nrow][ncol]=color;
                        q.offer(new Pair(nrow,ncol));
                    }
                }
            }
            return image;
        }
        class Pair{
            int row;
            int col;
            public Pair(int row, int col){
                this.row=row;
                this.col=col;
            }
        }
    }

    //kosaraju's algo
    //https://practice.geeksforgeeks.org/problems/strongly-connected-components-kosarajus-algo/1

    //O(3(V+E)) time

    public int kosaraju(int V, ArrayList<ArrayList<Integer>> adj)
    {
        Stack<Integer> st=new Stack<>();
        boolean[] vis=new boolean[V];
        int count=0;
        //performing dfs on original graph
        for(int i=0;i<V;i++){
            if(!vis[i]){
                dfsOriginal(i,vis,adj,st);
            }
        }
        ArrayList<ArrayList<Integer>> adjRev=new ArrayList<>(); //adj list for reversed edges
        for(int i=0;i<V;i++){
            adjRev.add(new ArrayList<>());
        }
        for(int i=0;i<adj.size();i++){
            vis[i]=false; //resetting the vis array for use in the second dfs
            for(int adjNode:adj.get(i)){
                adjRev.get(adjNode).add(i);
            }
        }
        while(!st.isEmpty()){
            int node=st.pop();
            if(!vis[node]){
                dfs(node,vis,adjRev);
                count++;
            }
        }
        return count;
    }
    public void dfsOriginal(int node,boolean[] vis, ArrayList<ArrayList<Integer>> adj,Stack<Integer> st){
        vis[node]=true;
        for(int i:adj.get(node)){
            if(!vis[i]){
                dfsOriginal(i,vis,adj,st);
            }
        }
        st.push(node);
    }
    public void dfs(int node,boolean[] vis,ArrayList<ArrayList<Integer>> adj){
        vis[node]=true;
        for(int i:adj.get(node)){
            if(!vis[i]){
                dfs(i,vis,adj);
            }
        }
    }

    //dijkstra's algo
    //https://practice.geeksforgeeks.org/problems/implementing-dijkstra-set-1-adjacency-matrix/1

    //O(ElogV) time - big mathematical derivation (imp), O(V) space
    static int[] dijkstra(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj, int S)
    {
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.first-b.first);
        pq.offer(new Pair(0,S));
        int[] dist=new int[V];
        Arrays.fill(dist,(int)1e9);
        dist[S]=0;
        while(!pq.isEmpty()){
            Pair p=pq.poll();
            int dis=p.first;
            int node=p.second;
            for(ArrayList<Integer> list:adj.get(node)){
                int adjNode=list.get(0);
                int adjWt=list.get(1);
                if(dis+adjWt<dist[adjNode]){ //found better distance
                    dist[adjNode]=dis+adjWt;
                    pq.offer(new Pair(dist[adjNode],adjNode));
                }
            }
        }
        return dist;
    }

    //bellman ford
    //https://practice.geeksforgeeks.org/problems/distance-from-the-source-bellman-ford-algorithm/0?fbclid=IwAR2_lL0T84DnciLyzMTQuVTMBOi82nTWNLuXjUgahnrtBgkphKiYk6xcyJU

    //O(V*E) time, O(V) space
    static int[] bellman_ford(int V, ArrayList<ArrayList<Integer>> edges, int S) {
        int[] dist=new int[V];
        Arrays.fill(dist,(int)1e8);
        dist[S]=0;
        for(int i=0;i<V-1;i++){ //N-1 iterations. why not N? because from src to last node, the number of edges would be N-1
            for(ArrayList<Integer> edge:edges){
                int u=edge.get(0);
                int v=edge.get(1);
                int adjWt=edge.get(2);
                if(dist[u]+adjWt<dist[v]){
                    dist[v]=dist[u]+adjWt;
                }
            }
        }
        for(ArrayList<Integer> edge:edges){ //nth iteration, if we're still finding a better distance, we know for sure
            // that there's a negative cycle in the graph
            if(dist[edge.get(0)]+edge.get(2)<dist[edge.get(1)]){
                return new int[] {-1};
            }
        }
        return dist;
    }

    //floyd warshall
    //https://practice.geeksforgeeks.org/problems/implementing-floyd-warshall2042/1

    //O(N3) time, O(1) space - doing everything in place
    public void shortest_distance(int[][] matrix)
    {
        int V=matrix.length;
        for(int i=0;i<V;i++){
            for(int j=0;j<V;j++){
                if(matrix[i][j]==-1){
                    matrix[i][j]=(int)1e9;
                }
            }
        }
        for(int via=0;via<V;via++){
            for(int u=0;u<V;u++){
                for(int v=0;v<V;v++){
                    matrix[u][v]=Math.min(matrix[u][v],matrix[u][via]+matrix[via][v]);
                }
            }
        }
        for(int i=0;i<V;i++){
            for(int j=0;j<V;j++){
                if(matrix[i][j]==(int)1e9){
                    matrix[i][j]=-1;
                }
            }
        }
    }

    //mst

    //prim's
    //https://practice.geeksforgeeks.org/problems/minimum-spanning-tree/1

    //time - O(2ElogE) - E in total for iterating over all edges and in each we perform add() and poll() operations thus
    // E*(logE+logE)=2ElogE=ElogE
    //space - O(V)

    static int spanningTree(int V, int E, int edges[][]){
        int sum=0;
        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
        boolean[] vis=new boolean[V];
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.first-b.first);
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<edges.length;i++){
            adj.get(edges[i][0]).add(new Pair(edges[i][1],edges[i][2])); //storing {adjNode,edgeWt} for each node
            adj.get(edges[i][1]).add(new Pair(edges[i][0],edges[i][2]));
        }
        pq.offer(new Pair(0,0));
        while(!pq.isEmpty()){
            Pair p=pq.poll();
            int wt=p.first;
            int node=p.second;
            if(vis[node]){
                continue;
            }
            sum+=wt;
            vis[node]=true; //we're certain this is the best edge for current node
            for(Pair ap:adj.get(node)){
                int adjNode=ap.first;
                int adjWt=ap.second;
                if(!vis[adjNode]){ //checking if we haven't already assigned the most optimal edge for this node
                    pq.offer(new Pair(adjWt,adjNode));
                }
            }
        }
        return sum;
    }

    //kruskal
    //complexity: time - O(E) to find edges + O(ElogE) to sort the edges +  O(m*4alpha) using dsj union() for all (V-1) edges
    //space - O(V)
     static class Edge implements Comparable<Edge>{
        int wt;
        int u;
        int v;
        public Edge(int wt,int u, int v){
            this.wt=wt;
            this.u=u;
            this.v=v;
        }
        @Override
        public int compareTo(Edge o) {
            return this.wt-o.wt;
        }
    }

    static class DisjointSet{
        ArrayList<Integer> parent;
        ArrayList<Integer> size;
        public DisjointSet(int V){
            parent=new ArrayList<>();
            size=new ArrayList<>();
            for(int i=0;i<V;i++){
                parent.add(i);
                size.add(1);
            }
        }
        public int findUltimateParent(int node){
            if(parent.get(node)==node){ //node itself is the up
                return node;
            }
            int up=findUltimateParent(parent.get(node));
            parent.set(node,up);
            return parent.get(node);
        }
        //this union function takes O(const)=O(4alpha) time
        public void union(int u, int v){
            int pu=findUltimateParent(u);
            int pv=findUltimateParent(v);
            if(pu==pv){ //same component
                return;
            }
            if(size.get(pu)>size.get(pv)){
                parent.set(pv,pu);
                size.set(pu,size.get(pu)+size.get(pv));
            }else{ //case when size(pu)<=size(pv)
                parent.set(pu,pv);
                size.set(pv,size.get(pu)+size.get(pv));
            }
        }
    }
    static int spanningTree1(int V, int E, int edges[][]){
        List<Edge> list=new ArrayList<>();
        int sum=0;
        for(int i=0;i<E;i++){ //O(E)
            list.add(new Edge(edges[i][2],edges[i][0],edges[i][1]));
        }
        DisjointSet ds=new DisjointSet(V);
        Collections.sort(list); //O(ElogE)
        for(int i=0;i<E;i++){ //O(m*4alpha)
            Edge e=list.get(i);
            int wt=e.wt;
            int u=e.u;
            int v=e.v;
            if(ds.findUltimateParent(u)!=ds.findUltimateParent(v)){  //u,v don't belong to the same component, thus
                // perform union. if they did, then we know for sure that whatever edges we used to connect them to the
                // same component were the ones with minimal wt
                ds.union(u,v);
                sum+=wt; //adding wt to ans only if we perform their union
            }
        }
        return sum;
    }


    //cheapest flight with k stops
    //https://leetcode.com/problems/cheapest-flights-within-k-stops
    class Solut1ion {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
            int[] dist=new int[n];
            Arrays.fill(dist,(int)1e9);
            ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
            for(int i=0;i<n;i++){
                adj.add(new ArrayList<>());
            }
            for(int[] flight:flights){
                adj.get(flight[0]).add(new Pair(flight[1],flight[2]));
            }
            Queue<Triad> q=new LinkedList<>(); //not using dijsktra coz doing so might take the shortest path but with larger stops
            q.offer(new Triad(0,src,0));
            while(!q.isEmpty()){
                Triad t=q.poll();
                int dis=t.dist;
                int node=t.node;
                int stops=t.stops;
                if(stops>k){ //can't go further, exhausted all stops and thus won't be updating cost of adj nodes.
                    // if this is the dst, it's dist would've already been updated in the dist array
                    continue;
                }
                for(Pair p:adj.get(node)){
                    int adjNode=p.node;
                    int adjWt=p.wt;
                    if(dis+adjWt<dist[adjNode]){
                        dist[adjNode]=dis+adjWt;
                        q.offer(new Triad(dist[adjNode],adjNode,stops+1));
                    }
                }
            }
            return dist[dst]==(int)1e9?-1:dist[dst];

        }
        class Triad{
            int dist;
            int node;
            int stops;
            public Triad(int dist, int node, int stops){
                this.dist=dist;
                this.node=node;
                this.stops=stops;
            }
        }
        class Pair{
            int node;
            int wt;
            public Pair(int node, int wt){
                this.node=node;
                this.wt=wt;
            }
        }
    }



}