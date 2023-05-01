import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Graphs{
    //clone graph
    //https://leetcode.com/problems/clone-graph
    //O(N) ie O(Edges+Vertices) time
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
}