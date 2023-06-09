import java.util.*;
import java.util.LinkedList;

class Graphs{
    //clone graph
    //https://leetcode.com/problems/clone-graph

    //O(N) - for traversing all nodes+O(2M) - finding neighbours of each node ie (total degree of graph where M is the number of edges)
    //We have to create a deep copy of the whole graph ie we cannot just use the same node addresses as in the original
    // graph to form the clone. We have to make new nodes with new addresses and exact same values and positioning as original nodes.
    // For this purpose we use a map to map a node to its newly created copy. Then, we recursively traverse the original graph and
    // for each node that we are at, we create its new copy if it doesn't already exist in the map and, then we traverse the current
    // node's neighbours and add them to the neighbours of the copy we just created. If the map already contains current node's copy,
    // we simply return it otherwise we return the new copy we just created after adding neighbours to it. We do this recursively for each node.

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
        HashMap<Node, Node> map=new HashMap<>();
        return dfs(node,map);
    }
    public Node dfs(Node node, HashMap<Node, Node> map){
        if(map.containsKey(node)){
            return map.get(node);
        }
        Node copy=new Node(node.val);
        map.put(node,copy);
        for(Node N:node.neighbors){ //accessing neighbours of current node and their neighbours recursively
            copy.neighbors.add(dfs(N,map));
        }
        return copy;
    }

    //dfs
    //https://practice.geeksforgeeks.org/problems/depth-first-traversal-for-a-graph/1
    //O(N) - for traversing all nodes+O(2M) - finding neighbours of each node ie (total degree of graph where M is the number of edges)

    public ArrayList<Integer> dfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        ArrayList<Integer> dfs=new ArrayList<>();
        boolean[] vis=new boolean[V];
        DFS(0,dfs,vis,adj);
        return dfs;
    }
    public void DFS(int node,ArrayList<Integer> dfs,boolean[] vis, ArrayList<ArrayList<Integer>> adj){
        vis[node]=true;
        dfs.add(node);
        for(int i:adj.get(node)){
            if(!vis[i]){
                DFS(i,dfs,vis,adj);
            }
        }
    }

    //bfs
    //https://practice.geeksforgeeks.org/problems/bfs-traversal-of-graph/1
    //O(N) - for traversing all nodes+O(2M) - finding neighbours of each node ie (total degree of graph where M is the number of edges)
    public ArrayList<Integer> bfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] vis=new boolean[V];
        ArrayList<Integer> bfs=new ArrayList<>();
        Queue<Integer> queue=new LinkedList<>();
        queue.offer(0);
        while(!queue.isEmpty()){
            int node=queue.poll();
            vis[node]=true;
            bfs.add(node);
            for(int i:adj.get(node)){
                if(!vis[i]){
                    queue.offer(i);
                }
            }
        }
        return bfs;
    }

    //number of islands
    //https://leetcode.com/problems/number-of-islands/
    static class Pair{
        int first;
        int second;
        public Pair(int first,int second){
            this.first=first;
            this.second=second;
        }
    }

    public int numIslands(char[][] grid) {
        int m= grid.length;
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
    public void bfs(int row, int col, boolean[][] vis,char[][] grid, int m, int n) {
        vis[row][col] = true;
        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(row, col));
        while (!q.isEmpty()){
            Pair p=q.poll();
            int i=p.first;
            int j=p.second;
            for (int delRow = -1; delRow <= 1; delRow++) {
                for (int delCol = -1; delCol <= 1; delCol++) {
                    if (delRow == -1 && delCol == -1 || delRow == -1 && delCol == 1 || delRow == 1 && delCol == -1 || delRow == 1 && delCol == 1) {
                        continue;
                    }
                    int neighRow = i + delRow;
                    int neighCol = j + delCol;
                    while (neighRow>=0&&neighRow < m && neighCol>=0&&neighCol < n && !vis[neighRow][neighCol] && grid[neighRow][neighCol] == '1') {
                        vis[neighRow][neighCol]=true;
                        q.offer(new Pair(neighRow, neighCol));
                    }
                }
            }
        }
    }

    //cycle detection in undirected graph

    //bfs
    public static String cycleDetection(int[][] edges, int n, int m) {
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        for(int i=0;i<n+1;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<m;i++){
            adj.get(edges[i][0]).add(edges[i][1]);
            adj.get(edges[i][1]).add(edges[i][0]);
        }
        boolean[] vis=new boolean[n+1];
        for(int i=1;i<n+1;i++){
            if(!vis[i]){
                if(checkCycleBfs(i,adj,vis)){
                    return "Yes";
                }
            }
        }
        return "No";
    }
    public static boolean checkCycleBfs(int i, ArrayList<ArrayList<Integer>> adj, boolean[] vis){
        Queue<Pair> q=new LinkedList<>();
        q.add(new Pair(i,-1));
        while(!q.isEmpty()){
            Pair p=q.poll();
            int node=p.first;
            vis[node]=true;
            int parent=p.second;
            for(int n:adj.get(node)){
                if(!vis[n]){
                    q.offer(new Pair(n,node));
                }
                else if(parent!=n){
                    return true;
                }
            }
        }
        return false;
    }

    //dfs
    public static String cycleDetection1(int[][] edges, int n, int m) {
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        for(int i=0;i<n+1;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<m;i++){
            adj.get(edges[i][0]).add(edges[i][1]);
            adj.get(edges[i][1]).add(edges[i][0]);
        }
        boolean[] vis=new boolean[n+1];
        for(int i=1;i<n+1;i++){
            if(!vis[i]){
                if(checkCycleDfs(i,adj,vis,-1)){
                    return "Yes";
                }
            }
        }
        return "No";
    }
    public static boolean checkCycleDfs(int node,ArrayList<ArrayList<Integer>> adj,boolean[] vis,int parent){
        vis[node]=true;
        for(int i:adj.get(node)){
            if(!vis[i]){
                if(checkCycleDfs(i,adj,vis,node)) {
                    return true;
                }
            }
            if(parent!=i){
                return true;
            }
        }
        return false;
    }

    //detect cycle in directed graph
    //https://practice.geeksforgeeks.org/problems/detect-cycle-in-a-directed-graph/1

    //dfs
    public boolean isCyclic(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] vis=new boolean[V];
        boolean[] pathVis=new boolean[V];
        for(int i=0;i<V;i++){
            if(!vis[i]){
                if(dfsCycle(i,adj,vis,pathVis)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean dfsCycle(int node, ArrayList<ArrayList<Integer>> adj, boolean[] vis,boolean[] pathVis){
        vis[node]=true;
        pathVis[node]=true;
        for(int i:adj.get(node)){
            if(!vis[i]){
                if(dfsCycle(i,adj,vis,pathVis)) {
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

    //toposort
    //https://practice.geeksforgeeks.org/problems/topological-sort/1

    //dfs
    // O(V+E) time (since directed graph),
    //O(V) stack space and vis array
    static int[] topoSort(int V, ArrayList<ArrayList<Integer>> adj)
    {
        Stack<Integer> st=new Stack<>();
        boolean[] vis=new boolean[V];
        int[] ans=new int[V];
        for(int i=0;i<V;i++){
            if(!vis[i]){
                topoDfs(i,adj,vis,st);
            }
        }
        for(int i=0;i<V;i++){
            ans[i]=st.pop();
        }
        return ans;
    }

    public static void topoDfs(int node, ArrayList<ArrayList<Integer>> adj, boolean[] vis, Stack<Integer> st){
        vis[node]=true;
        for(int i:adj.get(node)){
            if(!vis[i]){
                topoDfs(i,adj,vis,st);
            }
        }
        st.push(node);
    }

    //bfs (kahn's algo)
    // O(V+E) time (since undirected graph),
    static int[] topoSort2(int V, ArrayList<ArrayList<Integer>> adj)
    {
        int[] indegree=new int[V];
        int[] ans=new int[V];
        ArrayList<Integer> ansList=new ArrayList<>();
        Queue<Integer> q=new LinkedList<>();
        for(int i=0;i<adj.size();i++){
            for(int j=0;j<adj.get(i).size();j++){
                indegree[adj.get(i).get(j)]++;
            }
        }
        for(int i=0;i<indegree.length;i++){
            if(indegree[i]==0){
                q.offer(i);
            }
        }
        while(!q.isEmpty()){
            int node=q.poll();
            ansList.add(node);
            for(int i:adj.get(node)){
                indegree[i]--;
                if(indegree[i]==0){
                    q.offer(i);
                }
            }
        }
        for(int i=0;i<V;i++){
            ans[i]=ansList.get(i);
        }
        return ans;
    }

    //course schedule
    //https://leetcode.com/problems/course-schedule

    //O(V+E)
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegree=new int [numCourses];
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        ArrayList<Integer> ans=new ArrayList<>();
        Queue<Integer> q=new LinkedList<>();
        for(int i=0;i<numCourses;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<prerequisites.length;i++){
            adj.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }
        for(int i=0;i<adj.size();i++){
            for(int j=0;j< adj.get(i).size();j++){
                indegree[adj.get(i).get(j)]++;
            }
        }
        for(int i=0;i<indegree.length;i++){
            if(indegree[i]==0) {
                q.offer(i);
            }
        }
        while(!q.isEmpty()){
            int node=q.poll();
            ans.add(node);
            for(int i:adj.get(node)){
                indegree[i]--;
                if(indegree[i]==0){
                    q.add(i);
                }
            }
        }
        return ans.size()==numCourses;
    }

    //check if graph is bipartite
    //https://leetcode.com/problems/is-graph-bipartite

    //O(V+2E)

    //bfs
    public boolean isBipartite(int[][] graph) {
        int V=graph.length;
        int[] color=new int[V];
        Arrays.fill(color,-1);
        for(int i=0;i<V;i++){
            if(color[i]==-1){
                if(!checkBipartiteBfs(i,color,graph)){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean checkBipartiteBfs(int node, int[] color, int[][] graph){
        Queue<Integer> q=new LinkedList<>();
        color[node]=0;
        q.offer(node);
        while(!q.isEmpty()){
            int np=q.poll();
            for(int i:graph[np]){
                if(color[i]==-1){
                    color[i]=1-color[np];
                    q.offer(i);
                }
                if(color[i]==color[np]){
                    return false;
                }
            }
        }
        return true;
    }

    //dfs
    public boolean isBipartite2(int[][] graph) {
        int V=graph.length;
        int[] color=new int[V];
        Arrays.fill(color,-1);
        for(int i=0;i<V;i++){
            if(color[i]==-1){
                if(!checkBipartiteDfs(i,0,color,graph)){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean checkBipartiteDfs(int node, int clr, int[] color, int[][] graph){
        color[node]=clr;
        for(int i:graph[node]){
            if(color[i]==-1){
                if(!checkBipartiteDfs(i,1-clr,color,graph)){
                    return false;
                }
            }
            if(color[i]==color[node]){
                return false;
            }
        }
        return true;
    }

    //flood fill algo
    //https://leetcode.com/problems/flood-fill/

    //time: O((N*M): case when all nodes are connected ie have same value * 4 (each node has 4 neighbours)) == O(N*M)
    //space: O(2(N*M)) - copy array, recursive stack space
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int[][] copy=image; //not modifying the original given array
        int[] delRow={0,-1,0,1};
        int[] delCol={-1,0,1,0};
        floodFillDfs(sr,sc,color,image[sr][sc],delRow,delCol,copy);
        return copy;
    }
    public void floodFillDfs(int sr,int sc,int color,int defaultColor,int[] delRow,int[] delCol,int[][] copy){
        copy[sr][sc]=color;
        for(int i=0;i<delRow.length;i++){
            int neighRow=sr+delRow[i];
            int neighCol=sc+delCol[i];
            if(neighRow<copy.length&&neighRow>=0&&
                    neighCol<copy[0].length&&neighCol>=0&&
                    copy[neighRow][neighCol]==defaultColor //neighbours have same color as defaultColor, thus we can flood fill them
                    &&copy[neighRow][neighCol]!=color) //case when new color and default color are same
            {
                floodFillDfs(neighRow,neighCol,color,defaultColor,delRow,delCol,copy);
            }
        }
    }

    //Kosarju's algo
    //https://practice.geeksforgeeks.org/problems/strongly-connected-components-kosarajus-algo/1

    //O(3(V+E))==O(V+E)
    public int kosaraju(int V, ArrayList<ArrayList<Integer>> adj)
    {
        Stack<Integer> st=new Stack<>();
        ArrayList<ArrayList<Integer>> adjTrans=new ArrayList<>();
        boolean[] vis=new boolean[V];
        //sorting nodes wrt their finishing time inside a stack
        for(int i=0;i<V;i++){
            if(!vis[i]){
                dfsFinishTime(i,vis,st,adj);
            }
        }
        //initialising adj list of reversed graph
        for(int i=0;i<V;i++){
            adjTrans.add(new ArrayList<>());
        }
        //reversing the graph
        for(int i=0;i<V;i++){
            vis[i]=false; //resetting the vis array since we have to use it again in another dfs to find the number of SCCs
            for(int adjNode:adj.get(i)){
                adjTrans.get(adjNode).add(i);
            }
        }
        //finding SCC count
        int scc=0;
        while(!st.isEmpty()){
            int i=st.pop();
            if(!vis[i]){
                dfsCountSCC(i,vis,adjTrans);
                scc++;
            }
        }
        return scc;
    }
    //dfs to sort the nodes acc to their finish time
    public void dfsFinishTime(int node, boolean[] vis, Stack<Integer> st, ArrayList<ArrayList<Integer>> adj){
        vis[node]=true;
        for(int i:adj.get(node)){
            if(!vis[i]){
                dfsFinishTime(i,vis,st,adj);
            }
        }
        st.push(node);
    }
    //dfs to find the number of SCCs
    public void dfsCountSCC(int node, boolean[] vis, ArrayList<ArrayList<Integer>> adjTrans){
        vis[node]=true;
        for(int i:adjTrans.get(node)){
            if(!vis[i]){
                dfsCountSCC(i,vis,adjTrans);
            }
        }
    }

    //Dijkstra's algo
    //https://practice.geeksforgeeks.org/problems/implementing-dijkstra-set-1-adjacency-matrix/1

    //3 methods - queue,pq,set

    //using pq
    //O(ElogV) - check derivation in notes (imp)
    static int[] dijkstra(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj, int S)
    {
        PriorityQueue<Pair> pq=new PriorityQueue<>((x,y)->x.first-y.first);
        int[] dist=new int[V];
        Arrays.fill(dist,(int)1e9);
        dist[S]=0;
        pq.offer(new Pair(0,S));
        while(!pq.isEmpty()){
            Pair p=pq.poll();
            int node=p.second;
            int dst=p.first;
                for(int j=0;j<adj.get(node).size();j++){
                    int adjNode=adj.get(node).get(j).get(0);
                    int wt=adj.get(node).get(j).get(1);
                    if(wt+dst<dist[adjNode]){
                        dist[adjNode]=wt+dst;
                        pq.offer(new Pair(dist[adjNode],adjNode));
                    }
                }
        }
        return dist;
    }

    //print shortest path in a weighted undirected graph
    //https://practice.geeksforgeeks.org/problems/shortest-path-in-weighted-undirected-graph/1

    //here we modify the Dijkstra's algo such that every time we encounter a shorter path for a node, we also store it's
    // parent node (the node it came from) in a parent array. At the end for each node we get its optimal parent ie the
    // one it came from to obtain the shortest distance from the source node. Now we have to find the shortest path till
    // nth node. Thus, we start from parent of nth node and backtrack our way to the source node, storing the current node
    // in each iteration in a DS. We do this till we find the node such that parent[node]=node ie node 1 after which we
    // add node 1 to our ans DS. Then, we simply reverse it to find the shortest path from node 1 to n. In case the nth
    // node is unreachable ie its distance is 1e9, we return -1 in a list as ans.

    //O(ElogV (Dijkstra) + N (worst case when path from 1 to n involves every node)) time
    public static List<Integer> shortestPath(int n, int m, int edges[][]) {
        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
        PriorityQueue<Pair> pq=new PriorityQueue<>((x,y)->x.first-y.first);
        int[] parent=new int[n+1];
        int[] dist=new int[n+1];
        Arrays.fill(dist,(int)1e9);
        dist[1]=0;
        for(int i=0;i<=n;i++){
            parent[i]=i;
            adj.add(new ArrayList<>());
        }
        //forming adj list
        for(int i=0;i<edges.length;i++){
            adj.get(edges[i][0]).add(new Pair(edges[i][1],edges[i][2]));
            adj.get(edges[i][1]).add(new Pair(edges[i][0],edges[i][2]));
        }
        pq.offer(new Pair(0,1));
        while(!pq.isEmpty()){
            Pair p=pq.poll();
            int dst=p.first;
            int node=p.second;
            for(Pair pt:adj.get(node)){
                int adjDst=pt.second;
                int adjNode=pt.first;
                if(dst+adjDst<dist[adjNode]){
                    dist[adjNode]=dst+adjDst;
                    pq.offer(new Pair(dist[adjNode],adjNode));
                    parent[adjNode]=node;
                }
            }
        }
        int node=n;
        List<Integer> ans=new ArrayList<>();
        if(dist[node]==(int)1e9) //can't access node n
        {
            ans.add(-1);
            return ans;
        }
        //O(N)
        while(parent[node]!=node){
            ans.add(node);
            node=parent[node];
        }
        ans.add(1);
        Collections.reverse(ans);
        return ans;
    }

    //difficult to implement the set method in java as it does not allow us to remove an item from the set.

    //Bellman Ford - works with negative weights as well, returns the shortest path, and detects negative cycles
    //https://practice.geeksforgeeks.org/problems/distance-from-the-source-bellman-ford-algorithm/0?fbclid=IwAR2_lL0T84DnciLyzMTQuVTMBOi82nTWNLuXjUgahnrtBgkphKiYk6xcyJU

    static int[] bellman_ford(int V, ArrayList<ArrayList<Integer>> edges, int S) {
        int[] dist=new int[V];
        Arrays.fill(dist,(int)1e8);
        dist[S]=0;
        for(int i=0;i<V-1;i++){
            for(ArrayList<Integer> edge:edges){
                int src=edge.get(0);
                int dst=edge.get(1);
                int wt=edge.get(2);
                if(dist[src]+wt<dist[dst]){
                    dist[dst]=dist[src]+wt;
                }
            }
        }
        for(ArrayList<Integer> edge:edges){
            if(dist[edge.get(0)]+edge.get(2)<dist[edge.get(1)]){ //dist array can still be updated at Nth iteration, meaning there's a negative cycle
                return new int[]{-1};
            }
        }
        return dist;
    }

    //Floyd Warshall
    //https://practice.geeksforgeeks.org/problems/implementing-floyd-warshall2042/1

    //O(N3)
    public void shortest_distance(int[][] matrix)
    {
        int n= matrix.length;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(matrix[i][j]==-1) {
                    matrix[i][j] = (int) 1e9;
                }
                if(i==j){
                    matrix[i][j]=0;
                }
            }
        }
        for(int k=0;k<n;k++){
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    matrix[i][j]=Math.min(matrix[i][j],matrix[i][k]+matrix[k][j]);
                }
            }
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(matrix[i][j]==(int)1e9){
                    matrix[i][j]=-1;
                }
            }
        }
    }


    //Disjoint set

    //O(4A)==O(CONSTANT) time for union() function
    static class DisjointSet{
        List<Integer> parent;
        List<Integer> rank;
        List<Integer> size;
        public DisjointSet(int V){
            this.parent=new ArrayList<>();
            this.rank=new ArrayList<>();
            this.size=new ArrayList<>();
            for(int i=0;i<=V;i++){
                parent.add(i);
                rank.add(0);
                size.add(1);
            }
        }
        public int findUltimateParent(int u){
            if(parent.get(u)==u){
                return u;
            }
            int up=findUltimateParent(parent.get(u));
            parent.set(u,up);
            return parent.get(u);
        }
        public void unionByRank(int u,int v){
            int pu=findUltimateParent(u);
            int pv=findUltimateParent(v);
            if(pu==pv){  //both u and v belong to the same component
                return;
            }
            if(rank.get(pu)<rank.get(pv)){ //rank won't be affected since we're doing path compression and rank of pu is already less than pv
                // and, it will be directly attached to pv
                parent.set(pu,pv);
            }
            else if(rank.get(pu)>rank.get(pv)){
                parent.set(pv,pu);
            }
            else{ //same rank
                parent.set(pu,pv); //attach either one to the other one
                rank.set(pu,rank.get(pu)+1);
            }
        }
        public void unionBySize(int u,int v){
            int pu=findUltimateParent(u);
            int pv=findUltimateParent(v);
            if (pu == pv) {
                return;
            }
            if(size.get(pu)<size.get(pv)){
                parent.set(pu,pv);
                size.set(pv,size.get(pv)+size.get(pu));
            }
            else{
                parent.set(pu,pv);
                size.set(pu,size.get(pu)+size.get(pv));
            }
        }
    }

    //MST ALGOs

    //Prim's algo
    //https://practice.geeksforgeeks.org/problems/minimum-spanning-tree/1
    //Elog(E) time

    static int spanningTree(int V, int E, int edges[][]){
        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();

        int sum=0;
        boolean[] vis=new boolean[V];
        PriorityQueue<Pair> pq=new PriorityQueue<>((x,y)->x.first-y.first);
        pq.add(new Pair(0,0));
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }
        //forming adj list
        for(int i=0;i<edges.length;i++){
            adj.get(edges[i][0]).add(new Pair(edges[i][1],edges[i][2]));
            adj.get(edges[i][1]).add(new Pair(edges[i][0],edges[i][2]));
        }
        while(!pq.isEmpty()){
            Pair p=pq.poll();
            int node=p.second;
            int weight=p.first;
            if(vis[node]){
                continue;
            }
            vis[node]=true;
            sum+=weight;
            for(int i=0;i<adj.get(node).size();i++){
                int adjNode=adj.get(node).get(i).first;
                int adjWeight=adj.get(node).get(i).second;
                if(!vis[adjNode]){
                    pq.offer(new Pair(adjWeight,adjNode));
                }
            }
        }
        return sum;
    }

    //kruskal's algo
    //O(E) - to form adj list+ O(N+E) to form edges =
    static class Edge implements Comparable<Edge>{
        int weight;
        int src;
        int dst;
        public Edge (int weight, int src, int dst){
            this.weight=weight;
            this.src=src;
            this.dst=dst;
        }
        @Override
        public int compareTo(Edge CompareEdge) {
            return this.weight-CompareEdge.weight;
        }
    }
    static int spanningTree2(int V, int E, int edges[][]){
        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
        int ans=0;
        DisjointSet ds=new DisjointSet(V);
        List<Edge> edgeList=new ArrayList<>();
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<E;i++){
            adj.get(edges[i][0]).add(new Pair(edges[i][1],edges[i][2]));
            adj.get(edges[i][1]).add(new Pair(edges[i][0],edges[i][2]));
        }
        for(int i=0;i<V;i++){
            for(int j=0;j<adj.get(i).size();j++){
                int adjNode=adj.get(i).get(j).first;
                int weight=adj.get(i).get(j).second;
                edgeList.add(new Edge(weight,i,adjNode));
            }
        }
        Collections.sort(edgeList);
        for(int i=0;i<edgeList.size();i++){
            int wt=edgeList.get(i).weight;
            int src=edgeList.get(i).src;
            int dst=edgeList.get(i).dst;
            if(ds.findUltimateParent(src)!=ds.findUltimateParent(dst)){ //union
                ds.unionByRank(src,dst);
                ans+=wt;
            }
        }
        return ans;
    }


}
