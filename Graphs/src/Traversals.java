import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Traversals {
    //BFS
    public ArrayList<Integer> bfsOfGraph(int N, ArrayList<ArrayList<Integer>> adj) {
        ArrayList<Integer> bfs = new ArrayList<>();
        boolean[] vis = new boolean[N]; //0 based indexing
        Queue<Integer> q = new LinkedList<>();
        q.add(0); //starting node is zero
        vis[0] = true;
        while (!q.isEmpty()) {
            int node = q.poll();
            bfs.add(node); //store level order
            for (int i : adj.get(node)) { //get neighbours of the current node from the adj matrix
                if (vis[i] == false) {
                    vis[i] = true; //mark visit if not already visited
                    q.add(i);
                }
            }
        }
        return bfs;
    }

    //DFS
    public ArrayList<Integer> dfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] vis=new boolean[V];
        vis[0]=true;
        ArrayList<Integer> ans=new ArrayList<>();
        dfs(0, vis, adj, ans); //to cover all separate components in a graph, we can use a for loop to check for
         // all non visited nodes, and call dfs on them
        return ans;
    }
    public void dfs(int node, boolean[] vis, ArrayList<ArrayList<Integer>> adj, ArrayList<Integer> ans){
        vis[node]=true;
        ans.add(node);
        for(int i: adj.get(node)){
            if(!vis[i]){
                dfs(i,vis,adj,ans);
            }
        }
    }

}
