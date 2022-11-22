import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

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
        PriorityQueue<Pair> pq = new PriorityQueue<>((x, y) -> x.first - y.first); //to use a Pair in a min heap
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
            //add to mst list
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

}
