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
        while (i < n) {
            while (i < n && str.charAt(i) == ' ') {
                i++;
            }
            if (i >= n) {
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
        str += " ";
        String word = "";
        String ans = "";
        Stack<String> st = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                st.push(word);
                word = "";
            } else {
                word = word + str.charAt(i);
            }
        }
        while (st.size() != 1) {
            ans += st.pop() + " ";
        }
        ans += st.pop();
        return ans;
    }

    //optimal - O(N),O(1) - reversing the order as we iterate over the string
    public String reverseWords3(String str) {
        int i = 0;
        String word = "";
        String ans = "";
        while (i <= str.length() - 1) {
            if (str.charAt(i) != ' ') {
                word += str.charAt(i);
            } else {
                if (ans.isEmpty()) {
                    ans = word;
                } else {
                    ans = word + " " + ans;
                }
                word = "";
            }
            i++;
        }
        if (!word.isEmpty()) {
            if (ans.isEmpty()) {
                ans = word;
            } else {
                ans = word + " " + ans;
            }
        }
        return ans;
    }

    //roman to integer
    //https://leetcode.com/problems/roman-to-integer/
    public int romanToInt(String str) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int result = map.get(str.charAt(str.length() - 1));
        for (int i = str.length() - 2; i >= 0; i--) {
            if (map.get(str.charAt(i)) < map.get(str.charAt(i + 1))) {
                result -= map.get(str.charAt(i));
            } else {
                result += map.get(str.charAt(i));
            }
        }
        return result;
    }

    //integer to roman
    //https://leetcode.com/problems/integer-to-roman/
    //O (1)time and space

    //we know there are six instances when subtraction takes place - CM,CD,XC,XL,IX,IV. Now we have the roman symbols:
    // I,V,X,L,C,D,M. If didn't had the six conditions of subtraction, what we could've done to solve this problem would
    // that we would've taken the largest number M ie 1000 and dividing the given number by it would've given us the number
    // of times M appears in the roman version of this number. Then to get the balance, we would've updated num by doing mod
    // as num%M's value ie 1000 which would've given us the updated value of num that we would try dividing with the rest of
    // the numbers and doing the same thing with them. Now since we have these six extra instances, we can update the mapping
    // table of roman numerals and their integers, adding these six instances ie - 900,400,90,40,9,4 in their respective
    // positions and following the same approach to obtain the ans without them

    public String intToRoman(int num) {
        int[] arr={1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] str={"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        String ans="";
        for(int i=0;i<arr.length;i++){
            if((num/arr[i])>0){ // arr[i] can be placed in the string
                int count=num/arr[i]; //how many arr[i] should be placed in the ans string
                while(count>0){
                    ans+=str[i];
                    count--;
                }
                num%=arr[i]; //updating num
            }
        }
        return ans;
    }



    //longest common prefix
    //https://practice.geeksforgeeks.org/problems/longest-common-prefix-in-an-array5129/1

    //find min length string, iterate till that length (since in worst case lcp would be the whole shortest string) and
    // compare the char at ith position of first string with the rest of the strings. if it matches with their ith char,
    // add the char to the ans (here we check if flag is set to true meaning there was no mismatch), otherwise make flag
    // false and break.
    //O(N+(N*length of shortest string))

    public String longestCommonPrefix(String[] strs,int n) {
        if(strs.length==0){
            return "-1";
        }
        if(strs.length==1){
            return strs[0];
        }
        int minLength=Integer.MAX_VALUE;
        boolean flag=true;
        String ans="";
        for(String str:strs){
            if(str.length()<minLength){
                minLength=str.length();
            }
        }
        for(int i=0;i<minLength;i++){
            char ch=strs[0].charAt(i);
            for(int j=1;j<n;j++){
                if(strs[j].charAt(i)!=ch){ //found diff char
                    flag=false;
                    break;
                }
            }
            if(flag){
                ans+=ch; //adding common char to the ans
            }
        }
        return ans.isEmpty()?"-1":ans;
    }



    //longest palindromic substring
    //https://leetcode.com/problems/longest-palindromic-substring/submissions/

    //dp approach (O(N2))
    //here we combine three approaches - lps, lcsubstring, & print lcs ie we reverse the given string, then we find the
    // longest commom substring between them and then print it. Here a problem might occur that there might be a case where
    // the common substring between a string and its reverse is not palindromic. Thus we have to additionally check if the
    // resultant string is palindromic and largest and if the returned string is not palindromic, we can try all possible
    // substrings of that string and return the longest palindromic one. Moreover, some cases are not passing so there might
    // be a bug in the code which I'm currently not able to find. The expand around center approach works better for this
    // problem as it does not take any space

    public String longestPalindrome1(String a) {
        int n = a.length();
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        StringBuilder sb = new StringBuilder(a);
        String b = sb.reverse().toString();
        longestPalinSub(a, b, dp, n);
        StringBuilder ans = new StringBuilder();
        int i = n;
        int j = n;
        while (i > 0 && j > 0) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                ans.append(a.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        return ans.reverse().toString();
    }

    public void longestPalinSub(String a, String b, int[][] dp, int n) {
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 0;
                }
            }
        }
    }

    //better approach
    //O(N2) - using expand around center approach for odd and even lengths of palindromes. We know that a palindrome can
    // have an even length or an odd length. Thus, for both cases we take two pointers left and right. In the odd case,
    // we know that there's always going to be a middle element in the palindrome, and thus we put left at i-1 and right at i+1
    // for each element and check if they are equal. If they are, we keep iterating left-- and right++ till they are not equal,
    // or they have gone out of bounds. After their iteration is completed we calculate the length of the current palindrome using r-l-1
    // and set two pointers start and end at the end of the palindrome if the obtained length is > max len ie they store the start and
    // end positions of the longest palindromic substring. We do the same for even palindromes but since they don't have any middle element,
    // we directly start comparing l and r by  placing them at i and i+1. Rest procedure is same and in the end we just return a substring from start till end
    public String longestPalindrome(String s) {
        if (s.length() <= 1) {
            return s;
        }
        int maxLen = 1;
        int n = s.length();
        int start = 0;
        int end = 0;
        for (int i = 0; i < n - 1; i++) {
            int l = i;
            int r = i;
            while (l >= 0 && r < n) {
                if (s.charAt(l) == s.charAt(r)) {
                    l--;
                    r++;
                } else {
                    break;
                }
            }
            int len = r - l - 1;
            if (len > maxLen) {
                maxLen = len;
                start = l + 1; //start of palindrome
                end = r - 1; //end of palindrome
            }
        }
        for (int i = 0; i < n - 1; i++) {
            int l = i;
            int r = i + 1;
            while (l >= 0 && r < n) {
                if (s.charAt(l) == s.charAt(r)) {
                    l--;
                    r++;
                } else {
                    break;
                }
            }
            int len = r - l - 1;
            if (len > maxLen) {
                maxLen = len;
                start = l + 1;
                end = r - 1;
            }
        }
        return s.substring(start, end + 1);
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

    //find first occurrence of a string in another string
    //https://leetcode.com/problems/implement-strstr/

    //(O(N*M)time) - using 2 nested for loops to check each substring of length m in the haystack of length n
    public int strStr(String haystack, String needle) {
        int j=0;
        int n=haystack.length();
        int m=needle.length();
        if(needle.equals(haystack)){
            return 0;
        }
        if(m>n){
            return -1;
        }
        for(int i=0;i<=n-m;i++){
            for(j=0;j<m;j++){
                if(haystack.charAt(i+j)!=needle.charAt(j)){
                    break;
                }
            }
            if(j==m){
                return i;
            }
        }
        return -1;
    }

    //valid anagram
    //https://leetcode.com/problems/valid-anagram/
    //O(N) - using hashmap
    public boolean isAnagram(String s, String t) {
        char[] ch=s.toCharArray();
        Map<Character,Integer> map=new HashMap<>();
        if(s.length()!=t.length()){
            return false;
        }
        for(char c:ch){
            map.put(c,map.getOrDefault(c,0)+1);
        }
        char[] th=t.toCharArray();
        for(char c:th) {
            if (map.containsKey(c)) {
                if (map.get(c) == 1) {
                    map.remove(c);
                } else {
                    map.put(c, map.get(c) - 1);
                }
            }
        }
        return map.isEmpty();
    }

    //count and say
    //https://leetcode.com/problems/count-and-say/
    //O(N*length of nth string)
    //we take a counter to count the number of times a certain digit has occurred till we hit a different digit.
    // As soon as we do, we add the count of the previous digit to an empty string and then add that digit to that string.
    // Then we copy that string to our ans string. We do this operation n times to obtain the nth string and in each iteration
    // we initialize our ans string to ans+"&" where & acts as a delimiter so that when we've completely traversed the string
    // we are sure to include the last character as well (as when we reach the end of the string, we compare the delimiter &
    // with the last character of the string ie the one at str[i-1] and since they are not the same we add str[i-1]
    // to the ans and the iteration is over)
    public String countAndSay(int n) {
        if(n==1){
            return "1";
        }
        String s="1";
        int count=0;
        for(int i=2;i<=n;i++){
            String t="";
            count=1;
            s=s+"&"; //here '&' acts as a delimiter so that the last element is included when we've completely traversed the string
            for(int j=1;j<s.length();j++){
                if(s.charAt(j)!=s.charAt(j-1)){
                    t=t+count;
                    t=t+s.charAt(j-1);
                    count=1;
                }
                else{
                    count++;
                }
            }
            s=t;
        }
        return s;
    }

    //compare version numbers
    //https://leetcode.com/problems/compare-version-numbers/

    //O(N2) - We take two pointers i and j at the start of the first and second versions. Then we move them both until
    // we encounter a '.' period in each version. While moving the pointers, we simultaneously calculate the whole number
    // that we've encountered till that period using the formula: num=num*10+version[i]. Then we compare both the numbers
    // obtained from versions 1 and 2, returning 1 if num1>num2, -1 if num1<num2, moving both the pointers otherwise
    // (to make them go past the period '.'). Also we iterate till we have traversed both the strings.
    // In case both versions have been completely traversed then that would imply that both versions are equal,
    // and thus we return 0.
    public int compareVersion(String version1, String version2) {
        int n=version1.length();
        int m=version2.length();
        int i=0;
        int j=0;
        while(i<n||j<m){ //here we use OR to completely traverse one string if it is greater than the other
            int num1=0;
            int num2=0;
            while(i<n&&version1.charAt(i)!='.'){
                num1=num1*10+version1.charAt(i)-'0';
                i++;
            }
            while(j<m&&version2.charAt(j)!='.'){
                num2=num2*10+version2.charAt(j)-'0';
                j++;
            }
            if(num1<num2){
                return -1;
            }
            else if(num1>num2) {
                return 1;
            }
            i++;
            j++;
        }
        return 0; //completely traversed both versions and thus they are same
    }

    //min number of insertions in the FRONT to make string palindrome
    //https://www.interviewbit.com/problems/minimum-characters-required-to-make-a-string-palindromic

    //O(N)time and space

    //Here we use the concept of pattern matching (KMP algo). We are only allowed to make insertions in the front of the
    // string, and thus we make use of the lps array by concatenating the given string and its reverse and calculating the
    // lps of the whole string. This way, the last element of lps[] array will give us the length of the longest prefix that is
    // same as suffix. So, subtracting that from the length of the given string will give us the number of characters to
    // be inserted at the front to make the string palindrome. Intuition behind this is that - in a palindrome, the longest
    // prefix ie the whole string is equal to the longest suffix ie the whole string and thus using the lps logic we find
    // the longest prefix equal to suffix. So if it is of length 1, then we will need str.length()-1 more characters as prefix to
    // make the longest prefix(ie of length of string) equal to the longest suffix(of length of string). For eg: if the
    // string is abc, then the longest suffix and prefix would be abc.
    //basically, the idea is that in a palindrome, the longest prefix ie whole string is equal to the longest suffix ie
    // the whole string. Thus for the given string the last element of its lps with its reverse would give the length of the longest suffix
    // equal to prefix. So if it is 0, then it means that there are no characters common between the string and its palindrome
    // and thus we need to add n characters to the string to make it a palindrome so that longest prefix equals suffix
    // (as it is in a palindrome). If the lps value of last element is 1 then it means that part of the reverse of the
    // string that is same as given string is of length 1. So, to make it completely palindromic, we'd have to add N-1
    // more characters to it since suffix same as prefix of length 1 is already present and thus remaining length of
    // suffix containing characters same as prefix=N-1.

    //We add a '&' in between the string and its reverse so that it can be differentiated in case the string is already
    // a palindrome otherwise it would've considered the whole string as one, resulting in different lps value and then
    // lps[lps.length()-1] would give a value>n

    public int solve1(String a) {
        StringBuilder sb=new StringBuilder(a);
        String b=sb.reverse().toString();
        String str=a+"$"+b;
        int[] lps=findLps(str);
        int n=a.length();
        return n-lps[lps.length-1];
    }

    public static int[] findLps(String a){
        int n=a.length();
        int len=0;
        int[] lps=new int[n];
        int i=1;
        while(i<n){
            if(a.charAt(i)==a.charAt(len)){
                len++;
                lps[i]=len;
                i++;
            }
            else{
                if(len!=0){
                    len=lps[len-1];
                }
                else{
                    lps[i]=0;
                    i++;
                }
            }
        }
        return lps;
    }
}
