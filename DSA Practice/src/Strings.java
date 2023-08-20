import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

class Strings {

    //reverse words in a string
    //https://leetcode.com/problems/reverse-words-in-a-string

    //can use the s.trim() function to clamp all the leading and trailing spaces but that won't remove the spaces in
    // between different words. thus this is not feasible and we try the soln below

    //brutel for leetcode problem where the trailing spaces before and after each word are taken into account
    // and removed accordingly - just extract the words from s and put them in a stack, then retrieve them and attach in ans

    //O(N) time and space

    //optimal - instead of using a stack, just reverse while iterating over the string

    //when there are no spaces in the string, and the string is a single word, j would run from i to n
    //O(N)tc, O(1)sc - other than storing the ans, the program takes constant space
    public String reverseWords(String s) {
        int n = s.length();
        int i = 0;
        int j = 0;
        String ans = "";
        while (i < n) {
            while (i < n && s.charAt(i) == ' ') {
                i++;
            }
            if (i >= n) {
                break;
            }
            j = i + 1; //+1 because we certainly know that i is at a non-space character
            while (j < n && s.charAt(j) != ' ') {
                j++;
            }
            String word = s.substring(i, j);
            if (ans == "") {
                ans = word;
            } else {
                ans = word + " " + ans;
            }
            i = j + 1; //+1 because we certainly know that j is at a space character
        }
        return ans;
    }


    //case when trailing spaces before and after the words are not taken into account and the order of spaces is also reversed
    // as it is along with order of words and the trailing spaces are not removed
    //brute - O(N) time and space - using a stack to store all the words so that when we remove them from the stack,
    // they are in reverse order

    public String reverseWords1(String s) {
        Stack<String> st = new Stack<>();
        s += " ";
        String word = "";
        String ans = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                st.push(word);
                word = "";
            } else {
                word = word + s.charAt(i);
            }
        }
        while (st.size() != 1) {
            ans += st.pop() + " ";
        }
        ans += st.pop();
        return ans;
    }

    //optimal - O(N) time, O(1) space - reversing the order as we move through the string

    //doesn't take leading spaces before a string into account
    public String reverseWords3(String s) {
        String word = "";
        String ans = "";
        s += " ";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                if (ans == "") {
                    if(word==""){ //if there are leading spaces in the given string
                        ans=" ";
                    }
                    else{
                        ans = word;
                    }
                } else {
                    ans = word + " " + ans;
                }
                word = "";
            } else {
                word = word + s.charAt(i);
            }
        }
        return ans;
    }

    //longest palindromic substring
    //https://leetcode.com/problems/longest-palindromic-substring

    //https://www.youtube.com/watch?v=Y0ZRYuL_S2Q

    //brute - we can try to find all possible substrings, and for the ones that are palindromic, we can compare their length
    //with the max len. the problem with this approach is that we might have to tackle some extra edge cases

    public String longestPalindrome(String s) {
        String ans="";
        int len=Integer.MIN_VALUE;
        for(int i=0;i<s.length();i++){
            for(int j=i;j<s.length();j++){
                if(isPalindrome(s,i,j)){
                    if((j-i+1)>len){
                        len=j-i+1;
                        ans=s.substring(i,j+1);
                    }
                }
            }
        }
        return ans;
    }
    public boolean isPalindrome(String s, int i, int j){
        while(i<j){
            if(s.charAt(i)!=s.charAt(j)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    //dp approach O(N2) (prefer not to tell in interview coz this approach takes extra space. Moreover, printing longest
    // common substring is cumbersome and difficult to explain)- combining lcsubstring,lps, and print lcs
    //basically we try to find the longest common substring between a string and its reverse. that would be the longest
    // palindromic substring in the given string. the only problem is that currently I don't know how to print lcsubstring
    // and & if we try to print it using print lcs mthod then it would give invalid results as the tabulation in both lcs
    // and  lcsubstring is different

    //if however an interviewer asks how would you print a lcsubstring: just tell them i'd store the row,col of the cell
    // with the largest length and then try to backtrack in order to find the lcsubstring

    //better - O(N2) - expand around center. Although this has the same time complexity as brute, but the number of comparisons
    // are reduced significantly and thus it takes less avg time than brute. Thus this is the best approach. Try dry running

    // for each index we try to expand from it in both directions left and right and move till left!=right. this way when
    // left becomes!=right, we know for sure that whatever is between left and right is a palindrome
    public String longestPalindrome1(String a) {
        int n = a.length();
        int maxLen = 0;
        int left = 0;
        int right = 0;
        int start = 0;
        int end = 0;
        for (int i = 0; i < n; i++) { //case of odd length palindrome
            left = i;
            right = i;
            while (left >= 0 && right < n) {
                if (a.charAt(left) == a.charAt(right)) {
                    left--;
                    right++;
                } else { //string from left till right not a palindrome anymore
                    break;
                }
            }
            int len = right - left - 1;
            if (len > maxLen) {
                maxLen = len;
                start = left + 1;
                end = right - 1;
            }
        }
        for (int i = 0; i < n; i++) { //case of even length palindrome
            left = i;
            right = i + 1;
            while (left >= 0 && right < n) {
                if (a.charAt(left) == a.charAt(right)) {
                    left--;
                    right++;
                } else { //string from left till right not a palindrome anymore
                    break;
                }
            }
            int len = right - left - 1;
            if (len > maxLen) {
                maxLen = len;
                start = left + 1;
                end = right - 1;
            }
        }
        return a.substring(start, end + 1);
    }


    //roman to integer
    //https://leetcode.com/problems/roman-to-integer/
    //O(N) time, O(1) space as the map stores only 7 characters

    //there's 2 ways to solve this, either we can move right to left or left to right

    //right to left
    // simply take a ptr at last char of the string and then start iterating backwards from the second last
    // character. if the current char's value is greater than char at ptr, then it means that the left char has a greater
    // val than right char and thus we simply add the current char to the ans. Otherwise we subtract it from the ans.
    // Note that here we can directly add or subtract the current element's value to the ans because we're moving right
    // to left and if, say the current element's value is lesser than the value at ptr, so we know for sure that this
    // element's val is not added in the ans as we subtract it.

    public int romanToInt(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int ptr = s.length() - 1;
        int ans = map.get(s.charAt(ptr));
        for (int i = s.length() - 2; i >= 0; i--) {
            if (map.get(s.charAt(i)) < map.get(s.charAt(ptr))) {
                ans -= map.get(s.charAt(i));
            } else {
                ans += map.get(s.charAt(i));
            }
            ptr--;
        }
        return ans;
    }

    //left to right

    // Here since we move left to right, we might add an element's value which is smaller than its
    // right to the ans. To fix this problem, we start from the 0th character, and we maintain a separate
    // variable to store the prev character's value initially set to 0. Then in each iteration, we check if current element
    // <prev element. In this case we add current val to the ans. Otherwise, ie in case the current element>prev then we
    // know that we have to subtract prev element from the current element's val. Moreover, we have also added this prev
    // element's value to the ans thus we subtract the prev element's value*2.

    public int romanToInt1(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int ans = 0;
        int prevVal = 0;
        for (int i = 0; i < s.length(); i++) {
            int currentVal = map.get(s.charAt(i));
            if (prevVal >= currentVal) {
                ans += currentVal;
            } else {
                ans += currentVal - 2 * prevVal;
            }
            prevVal = currentVal;
        }
        return ans;
    }

    //integer to roman
    //https://leetcode.com/problems/integer-to-roman/

    //https://www.youtube.com/watch?v=ohBNdSJyLh8
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
        int[] arr = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] str = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        String ans = "";
        for (int i = 0; i < arr.length; i++) {
            if ((num / arr[i]) > 0) { // arr[i] can be placed in the string
                int count = num / arr[i]; //how many arr[i] should be placed in the ans string
                while (count > 0) {
                    ans += str[i];
                    count--;
                }
                num %= arr[i]; //updating num
                if (num == 0) {
                    break;
                }
            }
        }
        return ans;
    }

    //implement atoi
    //https://leetcode.com/problems/string-to-integer-atoi/

    //O(2N) time, O(1) space

    public int myAtoi(String s) {
        s = s.trim(); //to remove any leading or trailing white spaces, takes O(N) since it has to iterate the whole string
        // in order to find leading and trailing whitespaces
        if (s.length() == 0) {
            return 0;
        }
        long ans = 0; //since ans can exceed the int range before we clamp it
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (i == 0 && (ch == '-' || ch == '+')) { //skip any +ve, -ve signs at the start as we assume the number
                // is positive in the beginning and deal with it's polarity later them later
                continue;
            } else if (ch > '9' || ch < '0') {//char not a valid digit
                break;
            } else {
                if (ans < Integer.MIN_VALUE || ans > Integer.MAX_VALUE) { //we have to make sure that as soon as we get
                    // an out of range ans, we clamp and return it otherwise if we don't clamp it until we've traversed
                    // the whole string, value of ans might change as it can also exceed the long range
                    break;
                }
                ans = ans * 10 + (ch - '0');
            }
        }
        if (s.charAt(0) == '-') {
            ans = -1 * ans;
        }
        if (ans < Integer.MIN_VALUE || ans > Integer.MAX_VALUE) {
            return ans < Integer.MIN_VALUE ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        return (int) ans;
    }

    //longest common prefix
    //https://practice.geeksforgeeks.org/problems/longest-common-prefix-in-an-array5129/1

    //find min length string, iterate till that length (since in worst case lcp would be the whole shortest string) and
    // compare the char at ith position of first string with the rest of the strings. if it matches with their ith char,
    // add the char to the ans (here we check if flag is set to true meaning there was no mismatch), otherwise make flag
    // false and break.
    //O(N+(N*length of shortest string))

    public String longestCommonPrefix(String[] strs, int n) {
        if (strs.length == 0) {
            return "-1";
        }
        if (strs.length == 1) {
            return strs[0];
        }
        int minLength = Integer.MAX_VALUE;
        boolean flag = true;
        String ans = "";
        for (String str : strs) {
            if (str.length() < minLength) {
                minLength = str.length();
            }
        }
        for (int i = 0; i < minLength; i++) {
            char ch = strs[0].charAt(i);
            for (int j = 1; j < n; j++) {
                if (strs[j].charAt(i) != ch) { //found diff char
                    flag = false;
                    break;
                }
            }
            if (flag) {
                ans += ch; //adding common char to the ans
            }
        }
        return ans.isEmpty() ? "-1" : ans;
    }

    //optimal - O(m+NLOGN)
    //lexicographically sort the array, then the longest common prefix in first and last string would be the lcp among all

    public String longestCommonPrefix(String[] strs) {
        int minLen=Integer.MAX_VALUE;
        int n=strs.length;
        String ans="";
        for(String str: strs){
            if(str.length()<minLen){
                minLen=str.length();
            }
        }
        Arrays.sort(strs);
        int i=0;
        while(i<minLen&&strs[0].charAt(i)==strs[n-1].charAt(i)){
            ans+=strs[0].charAt(i);
            i++;
        }
        return ans;
    }

    //rabin karp algo
    //https://practice.geeksforgeeks.org/problems/31272eef104840f7430ad9fd1d43b434a4b9596b/1

    //the idea is that we use the hash function to avoid checking pattern in the entire string. Whenever hash value of
    //pattern and window match, then only we check if both of them are equal

    //tc: O(n*m) wc, O(n-m+1) avg

    private static int d = 256; //static because we want this value to be same for all instances so that they all refer to
    // the same value and multiple copies are not created for multiple instances. 256 is the total number of possible characters
    private static int mod = Integer.MAX_VALUE;

    ArrayList<Integer> search(String pat, String S) {
        ArrayList<Integer> ans = new ArrayList<>();
        int i = 0;
        int j = 0;
        int p = 0; // pattern hash code
        int t = 0; //window hash code
        int m = pat.length();
        int n = S.length();
        int h = (int) Math.pow(d, m - 1) % mod;
        for (i = 0; i < m; i++) {
            p = (d * p + pat.charAt(i)) % mod; //mod because p might exceed int range
            t = (d * t + S.charAt(i)) % mod;
        }
        for (i = 0; i <= n - m; i++) { //iterating n-m+1 times
            if (p == t) { //hash code of window and pattern match
                for (j = 0; j < m; j++) { //checking each char
                    if (pat.charAt(j) != S.charAt(i + j)) { //mismatch
                        break;
                    }
                }
                if (j == m) { //ie window and pattern characters match
                    ans.add(i + 1); //since window starts from i and in the question we have to assume that string has
                    // 1-based indexing
                }
            }
            if (i < n - m) { //ie we haven't reached the last window yet, thus we slide the window
                t = ((d * (t - (S.charAt(i) * h))) + S.charAt(i + m)) % mod; //again, this might exceed the int range therefore, thus mod
            }
            if (t < 0) { //in case t becomes negative after the above subtraction operation
                t += mod; //this will put t in the valid hash value range and after modding it with mod, we'd be able to
                // retrieve the correct hash value
            }
        }
        if (ans.isEmpty()) {
            ans.add(-1);
            return ans;
        }
        return ans;
    }

    //find first occurrence of a string in another string
    //https://leetcode.com/problems/implement-strstr/

    //brute - check each window of size needle in the haystack and if in any window there was no mismatch ie if we completely
    // iterated a window and found no mismatch with the characters of the needle, we return the starting index of that window
    // O(N*M) wc tc - since in the worst case, we compare each window with the needle till we reach the last window
    // O(1) sc

    public int strStr(String haystack, String needle) {
        int n=haystack.length();
        int m=needle.length();
        int j=0;
        for(int i=0;i<=n-m;i++){
            if(haystack.charAt(i)==needle.charAt(0)){ //only comparing if the first char of the needle matches the current
                // substring's first char
                for(j=0;j<m;j++){
                    if(needle.charAt(j)!=haystack.charAt(i+j)){
                        break;
                    }
                }
                if(j==m){ //found needle in haystack;
                    return i;
                }
            }
        }
        return -1;
    }

    //optimal1 - using indexOf() method in java
    //internal working of indexOf() method: we basically iterate the given string and check if any character matches the
    // first character of the substring and if it does, we compare the rest of the characters and return the starting index
    // if all characters match, otherwise -1 ie it is similar to what we did previously. Now to optimize this, they have
    // used Boyer-More's algo to optimize the implementation to O(N)
    //O(N) time, O(1) space

    public int strStr1(String haystack, String needle) {
        return haystack.indexOf(needle);
    }

    //optimal2 - using kmp algo

    //basic idea is that if some part of the beginning of the string is appearing somewhere else then we can simply avoid
    // that comparison and not move i. consider the pattern ababd, now if we follow 1 based indexing, and if we find a mismatch
    // at j=4, ie arr[j+1]!=arr[i], then we know we can straight away move to the second index because that is where we found
    // the prefix ab at first. This way, we didn't have to move i backwards, repeatedly checking the same characters again and
    // also we did not move j to the start after mismatch which also reduced time as we started from the first occurrence
    // of the prefix and checked from there

    //https://www.youtube.com/watch?v=JoF0Z7nVSrA&t=64s

    //O(M+N) time

    public int strStr2(String haystack, String needle) {
        int i = 0; //here instead of 1-based indexing (as discussed in approach), we follow 0-based indexing and thus both
        // i & j start at 0 and so wherever we were using j+1 before, we use j there and wherever we were using j, we use
        // j-1 there
        int j = 0;
        int n = haystack.length();
        int m = needle.length();
        int[] lps = formLps(needle);
        if (n == 0) {
            return -1; //haystack is empty
        }
        if (m == 0) {
            return 0; //needle is empty, thus it can be said that it starts at index 0 of the haystack as an empty string
            // does not affect the positioning of other elements
        }
        while (i < n && j < m) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            } else {
                if (j > 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        if (j == m) { //completely traversed the pattern, thus it exists in the string and starts from index i-j
            return i - j;
        }
        return -1; //pattern does not exist in string
    }

    //function to form lps table
    //O(2N) time in worst case
    public int[] formLps(String s) {
        int i = 1;
        int index = 0;
        int[] lps = new int[s.length()];
        while (i < s.length()) {
            if (s.charAt(i) == s.charAt(index)) {//as soon as we find a suffix that starts with the same char as
                // the 1st char of prefix, we start checking the other characters, updating the lps accordingly until we
                // find a mismatch
                lps[i] = index + 1;
                index++; //until we find a suffix that starts with the same char as the prefix, we don't move index pointer.
                // ie if we're moving the index ptr, we're certain that whatever elements of the prefix the index is passing
                // through are same in a particular suffix. now tht we have moved the index ptr, it is compared with the
                // same positioned elements of the suffix we're comparing it with
                i++;
            } else {
                if (index > 0) {
                    //see the thing is that we wish to find the longest prefix same as suffix. consider
                    // the eg "aaacaaaa". in this particular case, when we reach index=a and i=c we see that the prefix
                    // aaa is not same as suffix aac. so we know for sure that there would be no prefix that is equal to aac.
                    // if there were, it we would've updated the lps for this to 3. but since there isn't, we can try the
                    // next possible suffix that might exist as prefix ie ac. how will we do this? by going to the 2nd element of
                    // the prefix aaa in this case. in most cases doing index-- here would work but this doesn't work for "adcadde"
                    // and we might get a wrong val. so basically doing index=lps[index-1] will make sure that we go to 0th
                    // index in case the suffix is entirely different from prefix. like in the previous case, when b and c
                    // were in comparison, we knew that although aab and aac were not same, but then we also checked aa of
                    // the prefix with ac of the suffix because both of them started with a so there was a possibility that
                    // they could've been same. now since we knew both of them started with a, we directly compared the second
                    // char of both skipping the first. we were able to do this by directly jumping to the index where we
                    // found the prefix aa but in case of "adcadde" when we reach the prefix adc and suffix add, we know
                    // that they aren't equal, and also, we can't take a smaller suffix from add as we did before (like ac)
                    // because we have to include the second d and if we do tht, the suffix would be dd. so in this case,
                    // we know for sure that there's no way there would be a prefix that equals dd because all prefixes
                    // start with a. thus in this case, lps[index-1] would directly take us to 0th index & we'll directly
                    // start comparing from the first position.

                     index = lps[index - 1];
                }
                else { //ie the current suffix doesn't start with the first char of prefix
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    //min number of insertions in the front to make string palindrome
    //https://www.interviewbit.com/problems/minimum-characters-required-to-make-a-string-palindromic

    //this is different from the dp problem coz in that problem we had the flexibility to place elements anywhere in the
    // given string to form it a palindrome. We can't do the same here.
    //O(N)time and space

    //Here we use the concept of pattern matching (KMP algo). We are only allowed to make insertions in the front of the
    // string, and thus we make use of the lps array by concatenating the given string and its reverse and calculating the
    // lps of the whole string. This way, the last element of lps[] array will give us the length of the longest prefix that is
    // same as suffix. this means that whatever is not same as prefix in the reverse is to be added at the front
    //So, subtracting that from the length of the given string will give us the number of characters to
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

    public int solve(String a) {
        StringBuilder sb = new StringBuilder(a);
        String b = sb.reverse().toString();
        String str = a + "$" + b;
        int[] lps = formLps(str);
        int n = a.length();
        return n - lps[lps.length - 1];
    }

    //one more approach that we can think of for this problem would be to find the longest palindromic substring in given array

    //valid anagram
    //https://leetcode.com/problems/valid-anagram

    //brute - sort both the strings and then check if they're equal
    //O((N+M)log(N+M)+(N+M)) time, O(N+M) space
    public boolean isAnagram34(String s, String t) {
        char[] str=s.toCharArray();
        char[] ttr=t.toCharArray();
        Arrays.sort(str);
        Arrays.sort(ttr);
        int n=str.length;
        int m=ttr.length;
        if(str.length!=ttr.length){
            return false;
        }
        int i=0;
        int j=0;
        while(i<n&&j<m){
            if(str[i]!=ttr[j]){
                return false;
            }
            i++;
            j++;
        }
        return true;
    }

    //better - store the frequency of characters of string 1 in a hashmap. Then iterate over the 2nd string and while iterating,
    // reduce the frequency of characters of string 2 in the map. At the end if map is empty, return true
    //O(NlogN + M) time, O(N) space
    public boolean isAnagram(String s, String t) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        for (char ch : t.toCharArray()) {
            if (map.containsKey(ch)) {
                if (map.get(ch) == 1) {
                    map.remove(ch);
                } else {
                    map.put(ch, map.get(ch) - 1);
                }
            } else {
                return false;
            }
        }
        return map.isEmpty();
    }

    //optimal - there are 256 different characters in the ascii character set because in cpp a char variable is a 1 byte
    // memory location thus it can store any values s nm from 0 to 255 Thus create an array of this size and increment frequency
    // of characters of str1. Then iterate over this array and decrease the freq count for characters of 0. If at the end
    // all elements have 0 frequency then we return true
    //O(N) time, O(1) space

    public boolean isAnagram1(String s, String t) {
        int[] freq = new int[d];
        for (char ch : s.toCharArray()) {
            freq[ch]++;
        }
        for (char ch : t.toCharArray()) {
            freq[ch]--;
        }
        for (int i : freq) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    //count and say
    //https://leetcode.com/problems/count-and-say

    //O(N*length of nth string)
    //we take a counter to count the number of times a certain digit has occurred till we hit a different digit.
    // As soon as we do, we add the count of the previous digit to an empty string and then add that digit to that string.
    // Then we copy that string to our ans string. We do this operation n times to obtain the nth string and in each iteration
    // we initialize our ans string to ans+"&" where & acts as a delimiter so that when we've completely traversed the string
    // we are sure to include the last character as well (as when we reach the end of the string, we compare the delimiter &
    // with the last character of the string ie the one at str[i-1] and since they are not the same we add str[i-1]
    // to the ans and the iteration is over)

    public String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }
        String ans = "1"; //we start from second level, initialising ans with 1st level
        for (int i = 2; i <= n; i++) {
            String t = "";
            ans += '&'; //delimiter
            int count = 1; //count of a specific digit
            for (int j = 1; j < ans.length(); j++) {
                if (ans.charAt(j) != ans.charAt(j - 1)) {
                    t += count;
                    t += ans.charAt(j - 1);
                    count = 1;
                } else {
                    count++;
                }
            }
            ans = t;
        }
        return ans;
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
        int i = 0;
        int j = 0;
        int n = version1.length();
        int m = version2.length();
        while (i < n || j < m) { //here we use OR to completely traverse one string if it is greater than the other
            int num1 = 0;
            int num2 = 0;
            while (i < n && version1.charAt(i) != '.') {
                num1 = num1 * 10 + (version1.charAt(i) - '0');
                i++;
            }
            while (j < m && version2.charAt(j) != '.') {
                num2 = num2 * 10 + (version2.charAt(j) - '0');
                j++;
            }
            if (num1 < num2) {
                return -1;
            } else if (num1 > num2) {
                return 1;
            }
            i++;
            j++;
        }
        return 0;
    }
    public void reverseString5(char[] s) {
        String sp=new String(s);
    }
}

