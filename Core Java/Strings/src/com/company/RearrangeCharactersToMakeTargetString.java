package com.company;
//https://leetcode.com/problems/rearrange-characters-to-make-target-string/
public class RearrangeCharactersToMakeTargetString {
    public static void main(String[] args) {
        String str="mihri";
        String target="ihri";
        System.out.println(maxCopies(str,target));
    }
    static int maxCopies(String str, String target){
        int ans=str.length();
        int[] strCount=new int[26];
        int[] targetCount=new int[26];
        for(char ch:str.toCharArray()){
            ++strCount[ch-'a'];//when you do "character math", subtracting any alphabetical character from any other
            // alphabetical character (of the same case) results in bits being flipped in such a way that,
            // if you were to interpret them as an int, would represent the distance between these characters
            // thus for eg, 'z' - 'a' would return 26-1=25
        }
        for(char ch: target.toCharArray()){
            ++targetCount[ch-'a']; //this would increase the number of occurrences of each char ch in the target string
        }
        for (char ch: target.toCharArray()){
            ans=Math.min(ans, strCount[ch-'a']/targetCount[ch-'a']);
        }
        return ans;
    }
}
