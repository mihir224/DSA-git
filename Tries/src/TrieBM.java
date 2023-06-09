import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TrieBM {
    Node root;
    class Node {
        Node[] arr;
        public Node(){
            this.arr=new Node[2]; //we store bits in each node which can be either 0 or 1
        }
        public boolean containsKey(int bit){
            return arr[bit]!=null;
        }
        public void put(int bit, Node node){
            arr[bit]=node;
        }
        public Node next(int bit){
            return arr[bit];
        }
    }
    public TrieBM(){
        this.root=new Node();
    }
    public void insert(int num){
        Node node=root;
        for(int i=31;i>=0;i--){ //for each num, we're inserting its binary form ie of 32 bit into the trie
            int bit=(num>>i)&1;
            if(!node.containsKey(bit)){
                node.put(bit,new Node());
            }
            node=node.next(bit);
        }
    }
    public int getMax(int x){
        Node node=root;
        int maxNum=0;
        for(int i=31;i>=0;i--){
            int bit=(x>>i)&1;
            if(node.containsKey(1-bit)){ //trie contains opp bit, thus xor will give 1, and we know that with certainty.
                maxNum=(1<<i)|maxNum; // Thus, we set this bit of maxNum (ie we make it 1)
                node=node.next(1-bit);
            }else{ //trie does not contain opp bit, thus we have no option but to choose the same bit
                node=node.next(bit);
            }
        }
        return maxNum;
    }

    //Maximum XOR of Two Numbers in an Array
    //https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/

    //tc O((N*32) for inserting N 32 bit elements into the trie + (N*32) for searching the max xor from each num of arr
    // where we track each bit out of the 32 bits of that num)
    public int findMaximumXOR(int[] nums) {
        int max=0;
        for(int i:nums){
            insert(i);
        }
        for(int i:nums){
            max=Math.max(max,getMax(i));
        }
        return max;
    }

    //Maximum XOR With an Element From Array
    //https://leetcode.com/problems/maximum-xor-with-an-element-from-array/
    class Triad{
        int first;
        int second;
        int third;
        public Triad(int first, int second, int third){
            this.first=first;
            this.second=second;
            this.third=third;
        }
    }

    public int[] maximizeXor(int[] nums, int[][] queries) {
        TrieBM trie=new TrieBM();
        int q=queries.length;
        int n=nums.length;
        int[] ans=new int[q];
        ArrayList<Triad> offlineQueries=new ArrayList<>();
        for(int i=0;i<queries.length;i++){
            Triad t=new Triad(queries[i][1],queries[i][0],i);
            offlineQueries.add(t);
        }
        Collections.sort(offlineQueries, (a,b)->a.first-b.first); //lambda expression
        Arrays.sort(nums);
        int index=0;
        for(int i=0;i<q;i++){
            int mi=offlineQueries.get(i).first;
            int xi=offlineQueries.get(i).second;
            int qIndex=offlineQueries.get(i).third;
            while(index<n&&nums[index]<=mi){
                trie.insert(nums[index]);
                index++;
            }
            if(index!=0){ //meaning there are some elements in arr <= mi, and they have been inserted into the trie
                ans[qIndex]=trie.getMax(xi);
            }
            else{
                ans[qIndex]=-1;
            }
        }
        return ans;
    }
}
