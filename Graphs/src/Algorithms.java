import java.util.ArrayList;
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


}
