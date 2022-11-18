import java.util.ArrayList;

public class AdjacencyList {
    public static void main(String[] args) {
        int n=5;
        int m=6;
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        for(int i=0;i<=n;i++){ //storing n+1 arrayLists in the adjacency list
            adj.add(new ArrayList<>());
        }
        //edge 1 & 2
        adj.get(1).add(2); //adding 2 to arraylist of index 1;
        adj.get(2).add(1); //simultaneously adding 1 to arraylist of index 2;

        //edge 1 & 3
        adj.get(1).add(3);
        adj.get(3).add(1);

        //edge 2 & 4
        adj.get(2).add(4);
        adj.get(4).add(2);

        //edge 2 & 5
        adj.get(2).add(5);
        adj.get(5).add(2);

        //edge 3 & 4
        adj.get(3).add(4);
        adj.get(4).add(3);

        //edge 4 & 5
        adj.get(4).add(5);
        adj.get(5).add(4);

        //print the graph (each node with its neighbours)
        for(int i=0;i<adj.size();i++){
            System.out.print(i + ": ");
            for(int j=0;j<adj.get(i).size();j++){
                System.out.print(adj.get(i).get(j)+" ");
            }
            System.out.println();
        }
    }
}
