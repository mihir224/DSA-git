import java.util.*;

public class Main {
    public static void main(String[] args) {
        int a=8;
        int b=000;
        System.out.println(a+b);
    }
    public String sortVowels(String s) {
        List<Character> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();

        for(char ch:s.toCharArray()){
            if(isVowel(ch)){
                list.add(ch);
            }
            sb.append(ch);
        }
        Collections.sort(list);
        int index=0;
        for(int i=0;i<s.length();i++){
            if(isVowel(s.charAt(i))){
                sb.setCharAt(i,list.get(index));
                index++;
            }
        }
        return sb.toString();
    }
    public boolean isVowel(char ch){
        return ch=='a'||ch=='A'||ch=='e'||ch=='E'||ch=='i'||ch=='I'||ch=='o'||ch=='O'||ch=='u'||ch=='U';
    }
    static boolean test(int n){
        while (n > 1) {
            if (n % 5 != 0) {
                return false;
            }
            n /= 5;
        }
        return true;
    }
    public static long getInversions1(long arr[], int n) {
        return mergeSort(0,n-1,arr);
    }
    public static int mergeSort(int start,int end, long[] arr){
        int count=0;
        if(start>=end){
            return count;
        }
        int mid=(start+end)/2;
        count+=mergeSort(start,mid,arr);
        count+=mergeSort(mid+1,end,arr);
        count+=merge(start,mid,end,arr);
        return count;
    }
    public static int merge(int start,int mid,int end,long[] arr){
        int left=start;
        int right=mid+1;
        List<Integer> temp=new ArrayList<>();
        int count=0;
        while(left<=mid&&right<=end){
            if(arr[left]<arr[right]){
                temp.add((int)arr[left]);
                left++;
            }
            else{
                temp.add((int)arr[right]);
                count+=mid+1-left;
                right++;
            }
        }
        while(left<=mid){
            temp.add((int)arr[left]);
            left++;
        }
        while(right<=end){
            temp.add((int)arr[right]);
            right++;
        }
        for(int i=start;i<=end;i++){
            arr[i]=temp.get(i-start);
        }
        return count;
    }
    public List<List<Integer>> fourSum1(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> ans=new ArrayList<>();
        int n=nums.length;
        for(int i=0;i<nums.length;i++){
            long tp=target-nums[i];
            for(int j=i+1;j<nums.length;j++){
                tp-=nums[j];
                int left=j+1;
                int right=n-1;
                while(left<right){
                    if(nums[left]+nums[right]==target){
                        List<Integer> list=new ArrayList<>();
                        list.add(nums[i]);
                        list.add(nums[j]);
                        list.add(nums[left]);
                        list.add(nums[right]);
                        ans.add(list);
                        while(left<n&&nums[left]==list.get(2)){
                            left++;
                        }
                        while(right>=0&&nums[right]==list.get(3)){
                            right++;
                        }
                    }
                    if(nums[left]+nums[right]>tp){
                        right--;
                    }
                    else{
                        left++;
                    }
                }
                while(j<n-1&&nums[j]==nums[j+1]){
                    j++;
                }
            }
            while(i<n-1&&nums[i]==nums[i+1]){
                i++;
            }
        }
        return ans;
    }


}