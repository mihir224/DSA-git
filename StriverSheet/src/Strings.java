import java.util.*;

public class Strings {
    //reverse words in a string
    //https://leetcode.com/problems/reverse-words-in-a-string/

    //case when soln takes trailing spaces before and after different words into account
    //brute force - (O(N*length of each word (since we are making substrings for each word))tc,O(N)sc)
    //we first take a pointer i starting from 0th char of the array and if it is a blank space, we move it till we encounter a non-space character,
    // Then we take a pointer j=i+1 and move it till we find a space character. This is done in order to find words in the given string.
    // So when i and j reach their positions ie i at start of a word and j just after that word, we store the word through substr method
    // by passing i,j and then add that word to our result such that it is always added at the start. We do this subsequently for further words.
    public String reverseWords(String str) {
        int i = 0;
        int j = 0;
        int n = str.length();
        String ans = "";
        while(i<n) {
            while (i < n && str.charAt(i) == ' ') {
                i++;
            }
            if(i>=n){
                break;
            }
            j = i + 1;
            while (j < n && str.charAt(j) != ' ') {
                j++;
            }
            String word = str.substring(i, j);
            if (ans == "") {
                ans = word;
            } else {
                ans = word + " " + ans;
            }
            i = j + 1;
        }
        return ans;
    }

    //case when soln does not take into account the trailing spaces before and after different words of the string and thus cannot be accepted on leetcode
    //brute(O(N),O(N))
    // Using a stack to store all the words inside the string so that they are stored in reverse order
    // and then removing them from the stack one by one

    public String reverseWords2(String str) {
        str+=" ";
        String word="";
        String ans="";
        Stack<String> st=new Stack<>();
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)==' '){
                st.push(word);
                word="";
            }
            else{
                word=word+str.charAt(i);
            }
        }
        while(st.size()!=1){
            ans+=st.pop()+" ";
        }
        ans+=st.pop();
        return ans;
    }
    //optimal - O(N),O(1) - reversing the order as we iterate over the string
    public String reverseWords3(String str) {
        int i=0;
        String word="";
        String ans="";
        while(i<=str.length()-1){
            if(str.charAt(i)!=' '){
                word+=str.charAt(i);
            }
            else{
                if(ans.isEmpty()){
                    ans=word;
                }
                else{
                    ans=word+" "+ans;
                }
                word="";
            }
            i++;
        }
        if (!word.isEmpty()) {
            if(ans.isEmpty()){
                ans=word;
            }
            else{
                ans=word+" "+ans;
            }
        }
        return ans;
    }

    //roman to integer
    //https://leetcode.com/problems/roman-to-integer/
    public int romanToInt(String str) {
        Map<Character, Integer> map=new HashMap<>();
        map.put('I',1);
        map.put('V',5);
        map.put('X',10);
        map.put('L',50);
        map.put('C',100);
        map.put('D',500);
        map.put('M',1000);
        int result=map.get(str.charAt(str.length()-1));
        for(int i=str.length()-2;i>=0;i--){
            if(map.get(str.charAt(i))<map.get(str.charAt(i+1))){
                result-=map.get(str.charAt(i));
            }
            else{
                result+=map.get(str.charAt(i));
            }
        }
        return result;
    }

    //integer to roman
    public String intToRoman(int num) {
        int[] arr={1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] str={"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        String ans="";
        for(int i=0;i<str.length;i++){
            while(num>=arr[i]){
                ans+=str[i];
                num-=arr[i];
            }
        }
        return ans;
    }

    //longest common prefix
    //https://leetcode.com/problems/longest-common-prefix/

    //O(n*m where m is the length of the shortest string) brute
    //we find length of the shortest string in the given array(since the whole shortest string can be a prefix in other strings)
    // and then iterate till this min length. In each iteration, we take the ith character of the first string and then run another
    // loop starting from the second string, checking if the ith character of the jth string matches the first string.
    // If it does, we add that character to the ans string otherwise we return the ans
    public String longestCommonPrefix(String[] str) {
        int minLen=findMinLen(str);
        String ans="";
        for(int i=0;i<minLen;i++){
            char ch=str[0].charAt(i);
            for(int j=1;j<str.length;j++){
                if(str[j].charAt(i)!=ch){
                    return ans;
                }
            }
            ans+=ch;
        }
        return ans;
    }
    public int findMinLen(String[] str)
    {
        int minLen=str[0].length();
        for(int i=1;i< str.length;i++){
            if(str[i].length()<minLen){
                minLen= str[i].length();
            }
        }
        return minLen;
    }

    //longest palindromic substring
    //not done
    //https://leetcode.com/problems/longest-palindromic-substring/
    public String longestPalindrome(String s) {
        int n=s.length();
        int[][] dp=new int[n+1][n+1];
        for(int[] arr: dp){ //initialisation
            Arrays.fill(arr,-1);
        }
        StringBuilder sb=new StringBuilder(s);
        String b=sb.reverse().toString();
        StringBuilder ans=new StringBuilder();
        longestPalinsubstr(s,dp);
        int i=n;
        int j=n;
        while(i>0&&j>0){
            if(s.charAt(i-1)==b.charAt(j-1)){
                ans.append(s.charAt(i));
                i--;
                j--;
            }
            else if(dp[i-1][j]>dp[i][j-1]){
                i--;
            }
            else{
                j--;
            }
        }
        return ans.reverse().toString();
    }
    public void longestPalinsubstr(String s,int[][] dp) {
        StringBuilder sb=new StringBuilder(s);
        String b=sb.reverse().toString();
        int n=s.length();
        int length=0;
        for(int i=1;i<n+1;i++){
            for(int j=1;j<n+1;j++){
                if(s.charAt(i-1)==b.charAt(j-1)){
                    dp[i][j]=1+dp[i-1][j-1];
                    if(dp[i][j]>length){
                        length=dp[i][j];
                    }
                }
                else{
                    dp[i][j]=0;
                }
            }
        }
    }

    //rabin karp algo
    //https://practice.geeksforgeeks.org/problems/31272eef104840f7430ad9fd1d43b434a4b9596b/1
    class Solution {
        private static final int d = 256; //total number of possible characters
        private static final int q = Integer.MAX_VALUE;

        ArrayList<Integer> search(String pat, String txt) {
            int p = 0; //hash val of pattern
            int t = 0; //hash val of current window of text
            int m = pat.length();
            int n = txt.length();
            int h = (int) Math.pow(d, m - 1) % q;
            int j = 0;
            int i = 0;
            ArrayList<Integer> ans = new ArrayList<>();
            for (i = 0; i < m; i++) {
                p = (d * p + pat.charAt(i)) % q;
                t = (d * t + txt.charAt(i)) % q;
            }
            for (i = 0; i <= n - m; i++) {
                if (p == t) { //window hash val matches pattern hash val
                    for (j = 0; j < m; j++) { //checking each char individually
                        if (txt.charAt(i + j) != pat.charAt(j)) {
                            break;
                        }
                    }
                    if (j == m) {
                        ans.add(i + 1);
                    }
                }
                if (i < n - m) { //'i' has not yet reached the last window thus we can update t
                    t = (d * (t - txt.charAt(i) * h) + txt.charAt(i + m)) % q;
                }
                if (t < 0) {
                    t += q;
                }
            }
            if (ans.size() == 0) {
                ans.add(-1);
                return ans;
            }
            return ans;
        }
    }
        //implement atoi
        //https://leetcode.com/problems/string-to-integer-atoi/
        //string.trim() removes any leading or trailing white spaces

    public int myAtoi(String s) {
        long ans = 0;
        s = s.trim();
        if (s.length() == 0) {
            return 0;
        }
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (i == 0 && (ch == '+' || ch == '-')) {
                continue;
            } else if (ch > '9' || ch < '0') { //not an integer
                break;
            } else if (ch == ' ') {
                continue;
            } else {
                if (ans < Integer.MIN_VALUE || ans > Integer.MAX_VALUE) { //incase ans goes out of bounds we break out from the loop and return ans;
                    break;
                }
                ans = ans * 10 + (ch - '0');
            }
        }
        if (s.charAt(0) == '-') {
            ans = 0 - ans;
        }
        if (ans < Integer.MIN_VALUE || ans > Integer.MAX_VALUE) {
            return ans < Integer.MIN_VALUE ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        return (int) ans;
    }

}