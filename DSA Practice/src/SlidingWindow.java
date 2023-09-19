import java.util.*;

public class SlidingWindow {
    //max sum in sub arr of size k
    //https://practice.geeksforgeeks.org/problems/max-sum-subarray-of-size-k5313/1

    static long maximumSumSubarray(int k, ArrayList<Integer> Arr, int n){
        int i=0;
        int j=0;
        long ans=0;
        long sum=0;


        while(j<n){
            sum+=Arr.get(j);
            if(j-i+1<k){
                j++;
            }
            else if(j-i+1==k){
                ans=Math.max(ans,sum);
                sum-=Arr.get(i);
                i++;
                j++;
            }
        }
        return ans;
    }

    //first negative in every window of size k
    //https://practice.geeksforgeeks.org/problems/first-negative-integer-in-every-window-of-size-k3345/1?utm_source=geeksforgeeks&utm_medium=ml_article_practice_tab&utm_campaign=article_practice_tab
    public long[] printFirstNegativeInteger(long A[], int n, int k)
    {
        List<Long> list=new ArrayList<>();
        long[] arr=new long[n-k+1];
        int i=0;
        int j=0;
        int index=0;
        while(j<n){
            if(A[j]<0){
                list.add(A[j]);
            }
            if(j-i+1<k){
                j++;
            }
            else if(j-i+1==k){
                if(list.size()==0){
                    arr[index]=0;
                }
                else{
                    arr[index]=list.get(0);
                    if(A[i]==list.get(0)){
                        list.remove(0);
                    }
                }
                i++;
                j++;
                index++;
            }
        }
        return arr;
    }

    //count occurrences of anagrams
    //https://practice.geeksforgeeks.org/problems/count-occurences-of-anagrams5839/1
    int search(String pat, String txt) {
        Map<Character,Integer> map=new HashMap<>();

        for(char ch:pat.toCharArray()){
            map.put(ch,map.getOrDefault(ch,0)+1); //map is solely being used so that we can track the frequency
            // of pattern characters and to know when we have encountered all the characters of the pattern in the given
            // string (this helps us in case a same character occurs multiple times in the pattern and makes sure that all
            // occurrences of that pattern have been cancelled out)
        }
        int i=0;
        int j=0;
        int count=map.size();
        int k=pat.length();
        int n=txt.length();
        int ans=0;
        while(j<n){
            char ch=txt.charAt(j);
            if(map.containsKey(ch)){
                map.put(ch,map.get(ch)-1);
                if(map.get(ch)==0){
                    count--;
                }
            }
            if(j-i+1<k){
                j++;
            }
            else if(j-i+1==k){
                if(count==0){
                    ans++;
                }
                char cp=txt.charAt(i);
                if(map.containsKey(cp)){
                    map.put(cp,map.get(cp)+1);
                    if(map.get(cp)==1){ //freq became 1 from 0, thus inc count
                        count++;
                    }
                }
                i++;
                j++;
            }
        }
        return ans;
    }

    //variable window size

    //sliding window maximum
    //https://leetcode.com/problems/sliding-window-maximum/

    public int[] maxSlidingWindow(int[] nums, int k) {
        ArrayDeque<Integer> deq=new ArrayDeque<>();
        int n=nums.length;
        int[] ans=new int[n-k+1];
        int i=0;
        int j=0;
        while(j<n){
            while(!deq.isEmpty()&&deq.peekLast()<nums[j]){
                deq.pollLast();
            }
            deq.offer(nums[j]);
            if(j-i+1<k){
                j++;
            }
            else if(j-i+1==k){
                ans[i]=deq.peek();
                if(nums[i]==deq.peek()){
                    deq.poll();
                }
                i++;
                j++;
            }
        }
        return ans;
    }


    //longest substring with k distinct characters
    //https://practice.geeksforgeeks.org/problems/longest-k-unique-characters-substring0853/1

    public int longestkSubstr(String s, int k) {
        int i=0;
        int j=0;
        int ans=0;
        int n=s.length();
        HashMap<Character, Integer> map=new HashMap<>();
        while(j<n){
            char ch=s.charAt(j);
            map.put(ch,map.getOrDefault(ch,0)+1);
            if(map.size()<k){
                j++;
            }
            else if(map.size()==k){
                ans=Math.max(ans,j-i+1);
                j++;
            }
            else{ //map.size()>k, thus remove whatever i is currently pointing to
                while(map.size()>k){
                    char cp=s.charAt(i);
                    map.put(cp,map.get(cp)-1);
                    if(map.get(cp)==0){
                        map.remove(cp);
                    }
                    i++;
                }
                j++;
            }
        }
        return ans;
    }

    //longest substring without repeating characters (ie all distinct characters)
    //https://leetcode.com/problems/longest-substring-without-repeating-characters

    public int lengthOfLongestSubstring(String s) {
        HashMap<Character,Integer> map=new HashMap<>(); //map is used instead of set to store the frequency of a character
        // and remove it from the map when it is no longer part of the window
        int i=0;
        int j=0;
        int ans=0;
        int n=s.length();
        while(j<n){
            char ch=s.charAt(j);
            map.put(ch,map.getOrDefault(ch,0)+1);
            int len=j-i+1;
            if(len<map.size()){
                j++;
            }
            else if(len==map.size()){
                ans=Math.max(ans,j-i+1);
                j++;
            }
            else{ //len>map.size()
                while(j-i+1>map.size()){ //moving i until the duplicate is removed.
                    char cp=s.charAt(i);
                    map.put(cp,map.get(cp)-1);
                    if(map.get(cp)==0){
                        map.remove(cp);
                    }
                    i++;
                }
                j++;
            }
        }
        return ans;
    }

    //minimum window substring
    //https://leetcode.com/problems/minimum-window-substring/

    public String minWindow(String s, String t) {
        HashMap<Character,Integer> map=new HashMap<>();
        for(char ch:t.toCharArray()){
            map.put(ch,map.getOrDefault(ch,0)+1);
        }
        int i=0;
        int j=0;
        int start=0;
        int n=s.length();
        int ans=Integer.MAX_VALUE;
        int count=map.size();
        while(j<n){
            char ch=s.charAt(j);
            if(map.containsKey(ch)){
                map.put(ch,map.get(ch)-1);
                if(map.get(ch)==0){
                    count--;
                }
            }
            if(count>0){
                j++;
            }
            else if(count==0){
                while(count==0){
                    if(map.containsKey(s.charAt(i))){
                        map.put(s.charAt(i),map.get(s.charAt(i))+1);
                        if(map.get(s.charAt(i))==1){ //this means we're at first char of our ideal substring
                            if(j-i+1<ans){
                                start=i;
                                ans=j-i+1;
                            }
                            count++; //skipping i'th character
                        }
                    }
                    i++;
                }
                j++;
            }
        }
        if(ans==Integer.MAX_VALUE){
            return "";
        }
        return s.substring(start,ans+start);
    }
}
