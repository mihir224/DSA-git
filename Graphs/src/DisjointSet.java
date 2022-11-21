import java.util.ArrayList;

public class DisjointSet {
    ArrayList<Integer> rank=new ArrayList<>();
    ArrayList<Integer> size=new ArrayList<>();
    ArrayList<Integer> parent=new ArrayList<>();

    public DisjointSet(int n){  //initial config
        for(int i=0;i<=n;i++){   //initially, ranks are marked as 0's, sizes are marked as 1's and parents are marked as the node itself
            rank.add(0);
            size.add(1);
            parent.add(i);
        }
    }

    public static void main(String[] args) {
        DisjointSet ds=new DisjointSet(7);
//        ds.unionByRank(1,2);
//        ds.unionByRank(2,3);
//        ds.unionByRank(4,5);
//        ds.unionByRank(6,7);
//        ds.unionByRank(5,6);
        ds.unionBySize(1,2);
        ds.unionBySize(2,3);
        ds.unionBySize(4,5);
        ds.unionBySize(6,7);
        ds.unionBySize(5,6);
        if(ds.findUltimateParent(3)==ds.findUltimateParent(7)){ //checking whether 3 and 7 belong to the same component
            System.out.println("yes");
        }
        else{
            System.out.println("no");
        }
        ds.unionByRank(3,7);
        if(ds.findUltimateParent(3)==ds.findUltimateParent(7)){ //checking whether 3 and 7 belong to the same component
            System.out.println("yes");
        }
        else{
            System.out.println("no");
        }


    }
    public int findUltimateParent(int node){
        if(node==parent.get(node)){
            return node;
        }
        int up=findUltimateParent(parent.get(node)); //recursively finding the ultimate parent of the given node and storing it for path compression
        parent.set(node,up); //replacing the immediate parent of the node with its ultimate parent (path compression)
        return parent.get(node);
    }
    public void unionByRank(int u, int v){
        int pu=findUltimateParent(u);
        int pv=findUltimateParent(v);
        if(pu==pv){ //u & v have the same ultimate parent ie they belong to the same component
            return;
        }
        if(rank.get(pu)<rank.get(pv)){ //u is smaller in rank than v
            parent.set(pu,pv); //attach pu to pv
        }
        else if(rank.get(pu)>rank.get(pv)){ //rank pv is smaller
            parent.set(pv,pu); //attach pv to pu
        }
        else{ //rank of both equal
            parent.set(pv,pu); //attach either pu to pv or pv to pu
            rank.set(pu,rank.get(pu)+1); //updating rank of parent
        }
    }

    public void unionBySize(int u, int v){
        int pu=findUltimateParent(u);
        int pv=findUltimateParent(v);
        if(pu==pv){ //u & v have the same ultimate parent ie they belong to the same component
            return;
        }
        if(size.get(pu)<size.get(pv)){
            parent.set(pu,pv);
            size.set(pv,size.get(pv)+size.get(pu)); //increasing size of pv
        }
        else{ //case when pu>=pv, a single else statement would cover both cases ie > and = in case of union by size
            parent.set(pv,pu); //attach either one to the other one
            size.set(pu,size.get(pu)+size.get(pv)); //increasing size of pu
        }

    }

}
