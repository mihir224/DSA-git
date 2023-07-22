import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tries {

    //implement trie - 1
    //https://leetcode.com/problems/implement-trie-prefix-tree
    //O(L) time for insert and search where L is the length of the key
    class Trie {
        Node root;
        class Node{
            Node[] arr;
            boolean flag;
            public Node(){
                this.arr=new Node[26];
                this.flag=false;
            }
            public boolean containsKey(char ch){
                return arr[ch-'a']!=null;
            }
            public Node next(char ch){
                return arr[ch-'a'];
            }
        }
        public Trie() {
            this.root=new Node();
        }

        public void insert(String word) {
            Node node=root;
            for(int i=0;i<word.length();i++){
                if(!node.containsKey(word.charAt(i))){
                    node.arr[word.charAt(i)-'a']=new Node(); //creating a new ref node for current letter
                }
                node=node.next(word.charAt(i));
            }
            node.flag=true;
        }

        public boolean search(String word) {
            Node node=root;
            for(int i=0;i<word.length();i++){
                if(!node.containsKey(word.charAt(i))){
                    return false;
                }
                node=node.next(word.charAt(i));
            }
            return node.flag;
        }

        public boolean startsWith(String prefix) {
            Node node=root;
            for(int i=0;i<prefix.length();i++){
                if(!node.containsKey(prefix.charAt(i))){
                    return false;
                }
                node=node.next(prefix.charAt(i));
            }
            return true;
        }
        public boolean prefixExists(String word){
            Node node=root;
            for(char ch:word.toCharArray()){
                node=node.next(ch);
                if(node.flag==false){ //a certain prefix of this word doesnt exist in trie
                    return false;
                }
            }
            return true;
        }
    }

    //implement trie - 2
    //https://www.codingninjas.com/studio/problems/implement-trie_1387095?utm_source=youtube&utm_medium=affiliate&utm_campaign=striver_tries_videos

    public class Trie2 {
        Node root;
        class Node{
            Node[] arr;
            int countPrefix;
            int endsWith;
            public Node(){
                this.arr=new Node[26];
                this.countPrefix=0;
                this.endsWith=0;
            }
            public boolean containsKey(char ch){
                return arr[ch-'a']!=null;
            }
            public Node next(char ch){
                return arr[ch-'a'];
            }
        }

        public Trie2() {
            this.root=new Node();
        }


        public void insert(String word) {
            Node node=root;
            for(char ch:word.toCharArray()){
                if(!node.containsKey(ch)){
                    node.arr[ch-'a']=new Node();
                }
                node=node.next(ch);
                node.countPrefix++;
            }
            node.endsWith++;
        }

        public int countWordsEqualTo(String word) {
            Node node=root;
            for(char ch:word.toCharArray()){
                if(!node.containsKey(ch)){
                    return 0;
                }
                node=node.next(ch);
            }
            return node.endsWith;
        }

        public int countWordsStartingWith(String word) {
            Node node=root;
            for(char ch:word.toCharArray()){
                if(!node.containsKey(ch)){
                    return 0;
                }
                node=node.next(ch);
            }
            return node.countPrefix;
        }

        public void erase(String word) {
            Node node=root;
            for(char ch:word.toCharArray()){
                if(!node.containsKey(ch)){ //trie doesn't contain the word, thus no need to erase
                    return;
                }
                node=node.next(ch);
                node.countPrefix--;
            }
            node.endsWith--;
        }
    }

    //longest string with all prefixes
    //https://takeuforward.org/interviews/strivers-sde-sheet-top-coding-interview-problems/

    public  String completeString(int n, String[] a) {

        Trie trie=new Trie();
        String max="";
        for(int i=0;i<a.length;i++){
            trie.insert(a[i]);
        }
        for(int i=0;i<a.length;i++){
            if(trie.prefixExists(a[i])){
                if(a[i].length()>max.length()||(a[i].length()==max.length()&&a[i].compareTo(max)<0)){ //taking lexicogra
                    // -phically smaller string if both have same length
                    max=a[i];
                }
            }
        }
        if(max.isEmpty()){
            return "None";
        }
        return max;
    }

    //count distinct substrings
    //https://www.codingninjas.com/studio/problems/count-distinct-substrings_985292?utm_source=youtube&utm_medium=affiliate&utm_campaign=striver_tries_videos&leftPanelTab=1

    static class Node{
        Node[] arr;
        boolean flag;
        public Node(){
            this.arr=new Node[26];
            this.flag=false;
        }
        public boolean containsKey(char ch){
            return arr[ch-'a']!=null;
        }
        public Node next(char ch){
            return arr[ch-'a'];
        }
    }
    public static int countDistinctSubstrings(String s)
    {
        int count=1; //considering empty string
        Node root=new Node();
        for(int i=0;i<s.length();i++){
            Node node=root;
            for(int j=i;j<s.length();j++){
                if(!node.containsKey(s.charAt(j))){
                    count++;
                    node.arr[s.charAt(j)-'a']=new Node();
                }
                node=node.next(s.charAt(j));
            }
        }
        return count;

    }

    //power set
    //https://practice.geeksforgeeks.org/problems/power-set4302/1#

    //O(2^n*n);
    public List<String> AllPossibleStrings(String s)
    {
        List<String> list=new ArrayList<>();
        int n=s.length();
        for(int i=0;i<(int)Math.pow(2,n);i++){
            String str="";
            for(int j=0;j<n;j++){
                if(((i>>j)&1)!=0){ //ie bit is set
                    str+=s.charAt(j);
                }
            }
            if(str.length()>0){
                list.add(str);
            }
        }
        Collections.sort(list);
        return list;
    }

    //max xor of two numbers in an array
    //https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array
    //O(N*32)+O(N*32) time
    class TrieBM{
        Node root;
        class Node{
            Node[] arr;
            public Node(){
                this.arr=new Node[2];
            }
            public boolean containsKey(int bit){
                return arr[bit]!=null;
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
            for(int i=31;i>=0;i--){
                int bit=(num>>i)&1;
                if(!node.containsKey(bit)){
                    node.arr[bit]=new Node();
                }
                node=node.next(bit);
            }
        }
        public int getMaxXor(int x){
            Node node=root;
            int maxXor=0;
            for(int i=31;i>=0;i--){
                int bit=(x>>i)&1;
                if(node.containsKey(1-bit)){ //current node contains opp bit, thus we're certain that this bit of max xor will be set
                    maxXor=(1<<(i)|maxXor);
                    node=node.next(1-bit);
                }
                else{
                    node=node.next(bit);
                }
            }
            return maxXor;
        }
    }
    public int findMaximumXOR(int[] nums) {
        TrieBM trie=new TrieBM();
        int max=-1;
        for(int i:nums){
            trie.insert(i);
        }
        for(int i:nums){
            max=Math.max(max,trie.getMaxXor(i));
        }
        return max;
    }

    //max xor with an element from the array
    //https://leetcode.com/problems/maximum-xor-with-an-element-from-array
    
    //O(QlogQ to sort Q queries + NlogN to sort items of the nums array +  N*32 to insert N elements from nums into the
    // trie + Q*32 to process the x of (32 bits) of each query inorder to find max xor))

    class Triad implements Comparable<Triad> {
        int m;
        int x;
        int idx;
        public Triad(int m, int x, int idx){
            this.m=m;
            this.x=x;
            this.idx=idx;
        }
        @Override
        public int compareTo(Triad o){
            return this.m-o.m;
        }
    }
    public int[] maximizeXor(int[] nums, int[][] queries) {
        TrieBM trie=new TrieBM();
        List<Triad> list=new ArrayList<>();
        int[] ans=new int[queries.length];
        int index=0;
        for(int i=0;i<queries.length;i++){
            list.add(new Triad(queries[i][1],queries[i][0],i));
        }
        Collections.sort(list);
        Arrays.sort(nums);
        for(int i=0;i<list.size();i++){
            Triad t=list.get(i);
            int m=t.m;
            int x=t.x;
            int idx=t.idx;
            while(index<nums.length&&nums[index]<=m){
                trie.insert(nums[index]);
                index++;
            }
            if(index==0){ //no valid answer to this query ie found no elements less than the smallest m
                ans[idx]=-1;
            }
            else{
                ans[idx]=trie.getMaxXor(x);
            }
        }
        return ans;
    }




}
