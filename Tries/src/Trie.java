public  class Trie {
    Node root;
    Node2 root2;
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
        public void put(char ch, Node node){
            arr[ch-'a']=node;
        }
        public Node next(char ch){
            return arr[ch-'a'];
        }
    }
    public Trie() {
        this.root=new Node();
        this.root2=new Node2();
    }

    //implement trie 1
    //https://leetcode.com/problems/implement-trie-prefix-tree/

    //insert, search, startsWith

    public void insert(String word) {
        Node node=root;
        for(int i=0;i<word.length();i++){
            if(!node.containsKey(word.charAt(i))){ //checking if current letter exists in the reference node
                node.put(word.charAt(i),new Node());
            }
            node=node.next(word.charAt(i));
        }
        node.flag=true; //traversed the whole word
    }

    public boolean search(String word) {
        Node node=root;
        for(int i=0;i<word.length();i++){
            if(!node.containsKey(word.charAt(i))){ //return false if any letter of the word doesn't exist
                return false;
            }
            node=node.next(word.charAt(i));
        }
        return node.flag; //last ref node - if flag is false, word doesn't exist
    }

    public boolean startsWith(String prefix) {
        Node node=root;
        for(int i=0;i<prefix.length();i++){
            if(!node.containsKey(prefix.charAt(i))){
                return false;
            }
            node=node.next(prefix.charAt(i));
        }
        return true; //traversed the whole prefix w/o returning false ie prefix exists
    }

    public boolean prefixExists(String prefix){
        boolean flag=true;
        Node node=root;
        for(int i=0;i<prefix.length();i++){
            if(!node.containsKey(prefix.charAt(i))){
                return false;
            }
            node=node.next(prefix.charAt(i));
            if(node.flag==false){
                return false;
            }
        }
        return flag;
    }

    //implement trie 2
    //https://www.codingninjas.com/codestudio/problems/implement-trie_1387095?utm_source=youtube&utm_medium=affiliate&utm_campaign=striver_tries_videos
    //insert, count words equal to "word", count words with prefix equal to "prefix", erase a word equal to "word"

    class Node2{
        Node2[] arr;
        int endsWith;
        int countPrefix;
        public Node2(){
            this.arr=new Node2[26];
            this.endsWith=0;
            this.countPrefix=0;
        }
        public boolean containsKey(char ch){
            return arr[ch-'a']!=null;
        }
        public void put(char ch, Node2 node){
            arr[ch-'a']=node;
        }
        public Node2 next(char ch){
            return arr[ch-'a'];
        }
    }

    public void insert2(String word) {
        Node2 node=root2;
        for(int i=0;i<word.length();i++){
            if(!node.containsKey(word.charAt(i))){
                node.put(word.charAt(i),new Node2());
            }
            node=node.next(word.charAt(i));
            node.countPrefix++;
        }
        node.endsWith++;
    }

    public int countWordsEqualTo(String word) {
        Node2 node=root2;
        for(int i=0;i<word.length();i++){
            if(!node.containsKey(word.charAt(i))){
                return 0;
            }
            node=node.next(word.charAt(i));
        }
        return node.endsWith; //returning the number of words that end with the given word
    }

    public int countWordsStartingWith(String word) {
        Node2 node=root2;
        for(int i=0;i<word.length();i++){
            if(!node.containsKey(word.charAt(i))){
                return 0;
            }
            node=node.next(word.charAt(i));
        }
        return node.countPrefix;
    }

    public void erase(String word) {
        Node2 node=root2;
        for(int i=0;i<word.length();i++){
            if(!node.containsKey(word.charAt(i))){
                return;
            }
            node=node.next(word.charAt(i));
            node.countPrefix--;
        }
        node.endsWith--;
    }

    //Longest String with All Prefixes
    //https://www.codingninjas.com/codestudio/problems/complete-string_2687860?utm_source=youtube&utm_medium=affiliate&utm_campaign=striver_tries_videos
    //tc O(2(N*M)) where M is the avg length of each string
    ///In tries we generally cannot predict the sc as there might be a possibility that the root might contain the ref
    // nodes for all 26 letters and all the ref nodes might contain 26 ref nodes individually thus it can go like 26*26*26...
    // but this generally doesn't happen as the words with same prefixes are reused in tries
    public String completeString(int n, String[] a) {
        for(int i=0;i<n;i++){
            insert(a[i]);
        }
        String longest="";
        for(int i=0;i<n;i++){
            if(prefixExists(a[i])){
                if(a[i].length()>longest.length()){
                    longest=a[i];
                }
                else if(a[i].length()==longest.length()&&a[i].compareTo(longest)<0){ //comparing lexicographically
                    longest=a[i];
                }
            }
        }
        if(longest.isEmpty()){
            return "none";
        }
        return longest;
    }
}
