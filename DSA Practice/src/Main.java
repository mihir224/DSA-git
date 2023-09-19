import java.util.*;

public class Main {
    public static void main(String[] args) {
//        HashMap<Pair, Integer> map=new HashMap<>();
//        map.put(new Pair(3,5),99);
//        System.out.println(map.containsKey(new Pair(3,5)));
//            int[] arr={1,2,4,5,2};
//            List<String> s=new ArrayList<>();
//            s.add("MIHIR");
//            HashMap<Integer,List<Integer>> map=new HashMap<>();
//            List<Integer> list=map.getOrDefault(5,new ArrayList<>());
//
//        System.out.println(s.contains("MIHIR"));
        StringBuilder sb=new StringBuilder("balle");
        System.out.println(sb.toString());
//        ListNode node=new ListNode(1);
//        ListNode a=new ListNode(2);
//
//        ListNode b=new ListNode(3);
//        ListNode c=new ListNode(4);
//
//        ListNode d=new ListNode(5);
//        ListNode head=node;
//
//        node.next=a;
//        a.next=b;
//        b.next=c;
//        ListNode temp=node;
//        c.next=d;
//
//        ListNode rev=reverseList(b);
//        reverseList(rev);
//
//        while(temp!=null){
//            System.out.println(temp.val);
//            temp=temp.next;
//        }
    }
    static class Pair{
        int first;
        String second;
        public Pair(int first, String second){
            this.first=first;
            this.second=second;
        }
    }
    public static ListNode reverseList(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode prev=null;
        ListNode present=head;
        ListNode next=present.next;
        while(present!=null){
            present.next=prev;
            prev=present;
            present=next;
            if(next!=null){
                next=next.next;
            }
        }
        head=prev;
        return head;
    }
    static class ListNode{
        int val;
        ListNode next;
        public ListNode(){

        }
        public ListNode(int val){
            this.val=val;
        }

    }
    static class Triad implements Comparable<Triad>{
        int first;
        int second;
        int third;
        public Triad(int first, int second, int third){
            this.first=first;
            this.second=second;
            this.third=third;
        }

        @Override
        public int compareTo(Triad o) {
            if(this.first!=o.first){
                return Integer.compare(this.first,o.first);
            }
            if(this.second!=o.second){
                return Integer.compare(this.second,o.second);
            }
            return Integer.compare(this.third,o.third); //first and second elements of both instances equal, thus comparing
            // third instance
        }
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