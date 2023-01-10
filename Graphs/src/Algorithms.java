import java.util.*;

public class Algorithms {
    //Prims
    //https://practice.geeksforgeeks.org/problems/minimum-spanning-tree/1
    static class Pair {
        int first;
        int second;

        public Pair(int first, int second) {  //weight,node
            this.first = first;
            this.second = second;
        }
    }

    static int spanningTree(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj) //For a weighted graph, every node in the
    // adjacency list is in the form of a pair in which first element is the node and second is the weight
    {
        //here we are not required to obtain the MST, we just have to find the sum of the weights of mst thus parent is not taken
        PriorityQueue<Pair> pq = new PriorityQueue<>((x, y) -> x.first - y.first); //to use a Pair in a min heap and sort
        // it according to weight
        boolean[] vis = new boolean[V];
        pq.add(new Pair(0, 0)); // initial config
        int sum = 0;
        while (!pq.isEmpty()) {
            Pair p = pq.poll();
            int weight = p.first;
            int node = p.second;
            if (vis[node]) {
                continue; //current node has already been visited
            }
            vis[node] = true; //mark visit
            //add to mst list here
            sum += weight; //add weight to sum
            for (int i = 0; i < adj.get(node).size(); i++) {
                int adjWt = adj.get(node).get(i).get(1); //since we are given an adjacency matrix
                int adjNode = adj.get(node).get(i).get(0);
                if (!vis[adjNode]) {
                    pq.add(new Pair(adjWt, adjNode));
                }
            }
        }
        return sum;
    }

    //Kruskal
    class Edge implements Comparable<Edge> { //comparable class is implemented to use the compareTo function to sort
        // the elements of the edge according to weight
        int wt;
        int src;
        int dest;
        public Edge(int wt, int src, int dest) {
            this.wt = wt;
            this.src = src;
            this.dest = dest;
        }
        public int compareTo(Edge compareEdge) { //sort function of collections makes use of this method
            return this.wt-compareEdge.wt;
        }
    }
    int spanningTree1(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj)//For a weighted graph, every node in the
    // adjacency list is in the form of a pair in which first element is the node and second is the weight
    {
        List<Edge> edges=new ArrayList<>();
        for(int i=0;i<V;i++){
            for(int j=0;j<adj.get(i).size();j++){
                int adjNode=adj.get(i).get(j).get(0);
                int weight=adj.get(i).get(j).get(1);
                int node=i;
                Edge edge=new Edge(weight,node,adjNode); //creating edges for each node
                edges.add(edge);
            }
        }
        DisjointSet ds=new DisjointSet(V);
        Collections.sort(edges); //sorting the list of edges based on their weight
        int mstWeight=0;
        for(int i=0;i<edges.size();i++){
            int weight=edges.get(i).wt;
            int node=edges.get(i).src;
            int adjNode=edges.get(i).dest;
            if(ds.findUltimateParent(node)!=ds.findUltimateParent(adjNode)){ //not in same component
                ds.unionBySize(node,adjNode);
                mstWeight+=weight;
            }
        }
        return mstWeight;
    }

    //Topological Sort (dfs)
    static int[] topoSort(int V, ArrayList<ArrayList<Integer>> adj)
    {
        boolean[] vis=new boolean[V];
        Stack<Integer> st=new Stack<>();
        List<Integer> list=new ArrayList<>();
        int[] arr=new int[V];
        for(int i=0;i<V;i++){ //check for all components
            if(!vis[i]){
                dfs(i,adj,vis,st);
            }
        }
       while (!st.isEmpty()){
           list.add(st.pop());
       }
       for(int i=0;i<list.size();i++){
           arr[i]=list.get(i);
       }
       return arr;
    }
    static void dfs(int node, ArrayList<ArrayList<Integer>> adj, boolean[] vis, Stack<Integer> st){
        vis[node]=true;
        for(int i:adj.get(node)){
            if(!vis[i]){
                dfs(i, adj,vis,st);
            }
        }
        st.push(node);
    }

    //Kahn's Algo (topological sort bfs)
    static int[] topoSort1(int V, ArrayList<ArrayList<Integer>> adj)
    {
        int[] inDegree=new int[V];
        Queue<Integer> q=new LinkedList<>();
        List<Integer> list=new ArrayList<>();
        int[] ans=new int[V];
        for(int i=0;i<adj.size();i++){
            for(int j=0;j<adj.get(i).size();j++){
                inDegree[adj.get(i).get(j)]++;
            }
        }
        for(int i=0;i<inDegree.length;i++){
            if(inDegree[i]==0){
                q.add(i); //inserting all nodes with in-degree 0 into the queue;
            }
        }
        while(!q.isEmpty()){
            int node=q.poll();
            list.add(node);
            for(int i:adj.get(node)) {
                inDegree[i]--;
                if (inDegree[i] == 0) {
                    q.offer(i);
                }
            }
        }
        for(int i=0;i<V;i++){
            ans[i]=list.get(i);
        }
        return ans;
    }

    //Dijkstra
    //https://practice.geeksforgeeks.org/problems/implementing-dijkstra-set-1-adjacency-matrix/1
    static int[] dijkstra(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj, int S)
    {
        PriorityQueue<Pair> pq=new PriorityQueue<>((x,y)->x.first-y.first);
        int[] dist=new int[V];
        for(int i=0;i<V;i++){
            dist[i]=(int)(1e9);
        }
        dist[S]=0;
        pq.add(new Pair(0,S)); //(dist,node)
        while(!pq.isEmpty()){
            int node=pq.peek().second;
            int dis=pq.peek().first;
            pq.poll();
            for(int i=0;i<adj.get(node).size();i++){
                int v=adj.get(node).get(i).get(0);
                int wt=adj.get(node).get(i).get(1);
                if((dis+wt)<dist[v]){
                    dist[v]=dis+wt;
                    pq.add(new Pair(dist[v],v));
                }
            }
        }
        return dist;
    }

    //Bellman Ford
    //https://practice.geeksforgeeks.org/problems/distance-from-the-source-bellman-ford-algorithm/1
    static int[] bellman_ford(int V, ArrayList<ArrayList<Integer>> edges, int S) {
        int[] dist=new int[V];
        for(int i=0;i<V;i++){ //setting up initial config
            dist[i]=(int)(1e8);
        }
        dist[S]=0;
        for(int i=0;i<V-1;i++){ //running for N-1 iterations where N is the no. of nodes
            for(ArrayList<Integer> it:edges){
                int u=it.get(0);
                int v=it.get(1);
                int wt=it.get(2);
                if(dist[u]+wt<dist[v]){
                    dist[v]=dist[u]+wt;
                }
            }
        }   //nth iteration
        for(ArrayList<Integer> it:edges){
            int u=it.get(0);
            int v=it.get(1);
            int wt=it.get(2);
            if(dist[u]+wt<dist[v]){ //case when relaxation can still be done
                return new int[] {-1};
            }
        }
        return dist;
    }

    //Floyd Warshall
    //https://practice.geeksforgeeks.org/problems/implementing-floyd-warshall2042/1
    public void shortest_distance(int[][] matrix)
    {
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                if(matrix[i][j]==-1){
                    matrix[i][j]=(int)(1e9);
                }
            }
        }
        for(int via=0;via<matrix.length;via++){ //doing the traversals via each node
            for(int i=0;i<matrix.length;i++){
                for(int j=0;j<matrix[i].length;j++){
                    matrix[i][j]=Math.min(matrix[i][j],matrix[i][via]+matrix[via][j]);
                }
            }
        }
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                if(matrix[i][j]==(int)(1e9)){
                    matrix[i][j]=-1;
                }
            }
        }

    }




}
