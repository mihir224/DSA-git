import java.util.*;

class LL{
    ListNode head;
    ListNode tail;
    HashMap<Integer,ListNode> map;
    int capacity;

    class ListNode{
        int val;
        ListNode next;
        ListNode bottom;
        ListNode random;
        int key;
        ListNode prev;

        public ListNode(){}
        public ListNode(int val){
            this.val=val;
        }
        public ListNode(int val, ListNode next){
            this.val=val;
            this.next=next;
        }
        public ListNode(int key,int val){
            this.key=key;
            this.val=val;
            this.next=this.prev=null;
        }
    }

    //reorder list (good question)
    //https://leetcode.com/problems/reorder-list

    //be sure to dry run the odd length list case. in that case hf would in the end would point to itself before reaching
    // null. a node's next reference can be the node itself.
    public void reorderList(ListNode head){
        if(head==null||head.next==null){
            return;
        }
        ListNode mid=middleNode(head);
        ListNode headFirst=head;
        ListNode headSecond=reverseList(mid);
        ListNode temp=new ListNode();
        while(headFirst!=null&&headSecond!=null){
            temp=headFirst.next;
            headFirst.next=headSecond;
            headFirst=temp;
            temp=headSecond.next;
            headSecond.next=headFirst;
            headSecond=temp;
        }
        if(headFirst!=null){ //case of even length list, hs would reach null first
            headFirst.next=null;
        }
    }

    //reverse list
    //https://leetcode.com/problems/reverse-linked-list
    public ListNode reverseList(ListNode head) {
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

    //middle of linked list
    //https://leetcode.com/problems/middle-of-the-linked-list/
    public ListNode middleNode(ListNode head) {
        if(head==null){
            return head;
        }
        ListNode slow=head;
        ListNode fast=head;
        while(fast!=null&&fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }
        return slow;
    }

    //merge two sorted lists
    //https://leetcode.com/problems/merge-two-sorted-lists/

    //brute - storing merged list in a separate list
    //O(M) where M is the length of larger list, O(M+N) space
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1==null){
            return list2;
        }
        if(list2==null){
            return list1;
        }
        ListNode mergedHead=new ListNode();
        ListNode temp=mergedHead;
        while(list1!=null&&list2!=null){
            if(list1.val<list2.val){
                temp.next=list1;
                list1=list1.next;
            }else{
                temp.next=list2;
                list2=list2.next;
            }
            temp=temp.next;
        }
        if(list1==null){
            temp.next=list2;
        }
        else{
            temp.next=list1;
        }
        return mergedHead.next;
    }

    //optimal - merging the nodes in place
    //we basically take two heads head1 & head2 iterating over the two lists such that head1 always points to the smallest
    // node among whatever node the two  heads are pointing to in their lists. then while this head1 is pointing to a smaller
    // node, we move it until we reach a larger node. as soon as we do, we know for sure that current node in the other list
    // is smaller and we have to point whatever smaller node head1 was pointing to previously before it came to a larger
    // element to this smaller node. ie we have to keep track of the last smallest node head1 was pointing to. to do this
    // we use a temp node which moves along with head1 until it reaches a larger node. then we point this temp node to the
    // smaller node to which head2 is pointing to. then we basically swap head1 and head2 since head1 is supposed to be
    // always pointing to the smaller node. this process is repeated until either of the heads become null

    public ListNode mergeTwoLists1(ListNode list1, ListNode list2) {
        if(list1==null){
            return list2;
        }
        if(list2==null){
            return list1;
        }
        if(list1.val>list2.val){  //pointing list1 to smaller node
            ListNode temp=list1;
            list1=list2;
            list2=temp;
        }
        ListNode head=list1;
        while(list1!=null&&list2!=null){
            ListNode temp=null;
            while(list1!=null&&list1.val<=list2.val){
                temp=list1;
                list1=list1.next;
            }
            temp.next=list2; //pointing temp to smaller node
            //swapping l1 and l2
            ListNode swap=list1;
            list1=list2;
            list2=swap;
        }
        return head;
    }

    //remove nth node from the end of the list
    //https://leetcode.com/problems/remove-nth-node-from-end-of-list

    //we can use a brute here - basically removing a node at a particular index. only diff here is that we have to remove
    // it from the last. so we can find the element before nth el from the last at len-n, basically at index len-n-1 and do the necessary. there are
    // two imp edge cases here where this logic might fail so we have to take care of them separately. if (n==1 and len==2)
    // or n==len, our logic of len-n-1 won't work.
    //O(2N) time - first to find len and then to find node to be deleted

    public ListNode removeNthFromEnd(ListNode head, int n) {
        int len=getLen(head); //O(N)
        if(n==len){ //remove 1st element
            head=head.next;
            return head;
        }
        //O(N)
        ListNode node=getNode(head,len-n-1);
        node.next=node.next.next;
        return head;
    }
    public ListNode getNode(ListNode head, int index){
        ListNode temp=head;
        for(int i=0;i<index;i++){
            temp=temp.next;
        }
        return temp;
    }

    public int getLen(ListNode head){
        ListNode temp=head;
        int len=0;
        while(temp!=null){
            len++;
            temp=temp.next;
        }
        return len;
    }

    //optimal - traversing only once
    //O(N)

    //we basically take a dummy node and point its next to the head of LL. also at this dummy node we take a slow and fast
    // pointer. now we move fast by n times. then the distance left for it to traverse the whole list would be len-n. this
    // is the same distance it would take us to reach the node we wish to delete from the start of the linked list. now
    // since we used a dummy node, we started 1 node before the start and due to this reason when we will move the slow
    // pointer along with fast until we reach end, we know for sure that slow would cover a distance of len-n that is it
    // will point to the node before the one we wish to delete (since we started from the dummy node). and then we can do
    // the necessary operations. this covers edge cases as well

    public ListNode removeNthFromEnd1(ListNode head, int n) {
        int len=getLen(head);
        ListNode dummy=new ListNode();
        dummy.next=head;
        ListNode fast=dummy;
        ListNode slow=dummy;
        for(int i=0;i<n;i++){
            fast=fast.next;
        }
        while(fast.next!=null){
            fast=fast.next;
            slow=slow.next;
        }
        slow.next=slow.next.next;
        return dummy.next; //we return this instead of head because there can be a case when head is the node we have to
        // remove
    }

    //add two numbers as linked list
    //https://leetcode.com/problems/add-two-numbers/
    //since the digits are in reverse order, we basically perform the addition from left to right instead of doing that
    // from right to left
    //O(max(n,m))

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy=new ListNode();
        ListNode temp=dummy;
        int carry=0;
        int sum=0;
        while(l1!=null||l2!=null||carry!=0){
            sum=0;
            if(l1!=null){
                sum+=l1.val;
                l1=l1.next;
            }
            if(l2!=null){
                sum+=l2.val;
                l2=l2.next;
            }
            sum+=carry;
            temp.next=new ListNode(sum%10);
            carry=sum/10;
            temp=temp.next;
        }
        return dummy.next;
    }

    //delete node when head is not given
    //https://leetcode.com/problems/delete-node-in-a-linked-list/

    //it is given that node is not the last node and we don't have to dlt its address, we just have to dlt it in such a
    // way that its value should not exist in the linked list and the number of nodes should decrease by one. moreover
    // order of nodes before and after should be same
    public void deleteNode(ListNode node) {
        node.val=node.next.val;
        node.next=node.next.next;
    }

    //intersection of two linked lists
    //https://leetcode.com/problems/intersection-of-two-linked-lists

    //brute - compare every node (ie address) of list 1 with every node of list 2. as soon as we find a similar node in both
    // (ie with the same address), return that node
    //O(N*M)

    //better - store nodes (addresses) of list 1 in a hashmap and then iterate over list 2 and return as soon as we find
    // a node that is already in the map
    //O(N+M)

    //optimal - 1
    //take 2 dummy heads, find length of both lists and then move larger head by the difference to cover the difference.
    //then move both heads together until they meet

    //optimal - 2
    //O(2M) - if M is the larger LL, in the wc when there's no intersection, dh1 would traverse the whole list1 and then
    // again the whole list

    //if list 2 is larger, head2 starts b4 head 1. by the time head2 reaches end, head 1 would've covered the distance between them
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA==null||headB==null){
            return null;
        }
        ListNode dh1=headA;
        ListNode dh2=headB;
        while(dh1!=dh2){ //in case no intersection, both will eventually point to null together
            //IMP
            //it is necessary to perform the operations explicitly ie when a node reaches null, we put it at the other list's
            // head in the next iteration and not in the same iteration. otherwise it can cause error in sync when there's no intersection
            dh1=dh1==null?headB:dh1.next;
            dh2=dh2==null?headA:dh2.next;
        }
        return dh1;
    }

    //detect cycle
    //https://leetcode.com/problems/linked-list-cycle/

    public boolean hasCycle(ListNode head) {
        if(head==null||head.next==null){
            return false;
        }
        ListNode fast=head;
        ListNode slow=head;
        while(fast!=null&&fast.next!=null){
            fast=fast.next.next;
            slow=slow.next;
            if(fast==slow){
                return true;
            }
        }
        return false;
    }

    //reverse nodes in grp of k
    //https://leetcode.com/problems/reverse-nodes-in-k-group/
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head==null||head.next==null||k<=1){
            return head;
        }
        int len=getLen(head);
        int numGrps=len/k;
        ListNode prev=null;
        ListNode present=head;
        while(numGrps>0){
            ListNode newLast=present;
            ListNode temp=prev;
            ListNode next=present.next;
            for(int i=0;i<k;i++){ //here we're iterating till k only and so for that reason we dont have to explicitly check if present==null because if say k was 2 and there were 4 items, for both the grps, we would've traversed only k times and after that numGrps would've become zero and we wouldn't iterate further
                present.next=prev;
                prev=present;
                present=next;
                if(next!=null){
                    next=next.next;
                }
            }
            if(temp!=null){
                temp.next=prev;
            }
            else{ //for the starting grp, pointing head to 1st node of reversed list
                head=prev;
            }
            newLast.next=present;
            prev=newLast;
            numGrps--;
        }
        return head;
    }

    //palindromic linkedlist
    //https://leetcode.com/problems/palindrome-linked-list/

    public boolean isPalindrome(ListNode head) {
        if(head==null||head.next==null){
            return true;
        }
        ListNode mid=getMid(head);
        ListNode midHead=reverseList(mid);
        ListNode rereverse=midHead;
        while(head!=null&&midHead!=null){
            if(head.val!=midHead.val){
                return false;
            }
            head=head.next;
            midHead=midHead.next;
        }
        reverseList(rereverse); //maintaining original list
        return true;
    }
    public ListNode getMid(ListNode head){
        ListNode fast=head;
        ListNode slow=head;
        while(fast!=null&&fast.next!=null){
            fast=fast.next.next;
            slow=slow.next;
        }
        return slow;
    }

    //find starting point of linked list
    //https://leetcode.com/problems/linked-list-cycle-ii/
    public ListNode detectCycle(ListNode head) {
        if(head==null||head.next==null){
            return null;
        }
        int len=getCycleLen(head);
        if(len==0){ //no cycle
            return null;
        }
        ListNode slow=head;
        ListNode fast=head;
        while(len>0){
            slow=slow.next;
            len--;
        }
        while(slow!=fast){
            slow=slow.next;
            fast=fast.next;
        }
        return slow;
    }
    public int getCycleLen(ListNode head){
        ListNode fast=head;
        ListNode slow=head;
        while(fast!=null&&fast.next!=null){
            fast=fast.next.next;
            slow=slow.next;
            if(slow==fast){
                break;
            }
        }
        if(fast==null||fast.next==null){ //no cycle
            return 0;
        }
        int len=0;
        //at this point, fast and slow are at first point of collision. so we move slow until it meets fast again, simultaneously calculating the length
        do{
            slow=slow.next;
            len++;
        }while(slow!=fast);
        return len;
    }

    //flattening a linkedlist
    //https://practice.geeksforgeeks.org/problems/flattening-a-linked-list/1
    ListNode flatten(ListNode root)
    {
        if(root==null||root.next==null){
            return root;
        }
        ListNode lastNode=flatten(root.next);
        ListNode mergedHead=merge(root,lastNode);
        return mergedHead;
    }
    ListNode merge(ListNode list1, ListNode list2){
        //we don't have to check if any of the lists is null because we're already making sure that isn't the case in the flatten function
        //make list1 point to smaller node
        if(list1.val>list2.val){
            ListNode temp=list1;
            list1=list2;
            list2=temp;
        }
        ListNode head=list1;
        while(list1!=null&&list2!=null){
            ListNode temp=null;
            while(list1!=null&&list1.val<=list2.val){
                temp=list1;
                list1=list1.bottom;
            }
            temp.bottom=list2;
            ListNode swap=list1;
            list1=list2;
            list2=swap;
        }
        return head;
    }

    //rotate linked list
    //https://leetcode.com/problems/reorder-list
    //O(2N) time and O(1) space
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null||head.next==null||k<=0){
            return head;
        }
        int length=1;
        ListNode last=head;
        while(last.next!=null){
            last=last.next;
            length++;
        }
        last.next=head;
        int rotations=k%length;
        ListNode newLast=head;
        for(int i=0;i<length-rotations-1;i++){
            newLast=newLast.next;
        }
        head=newLast.next;
        newLast.next=null;
        return head;
    }


    //clone a linkedlist with random pointer
    //https://leetcode.com/problems/copy-list-with-random-pointer/

    //brute: create a copy of all nodes in a hashmap, then connect all the next and random pointers of the nodes stored
    // in the hashmap according to the original list
    //O(N) time, O(N) space
    public ListNode copyRandomList2(ListNode head) {
        HashMap<ListNode,ListNode> map=new HashMap<>();
        ListNode temp=head;
        while(temp!=null){
            map.put(temp,new ListNode(temp.val));
            temp=temp.next;
        }
        temp=head;
        while(temp!=null){
            ListNode node=map.get(temp);
            node.next=map.get(temp.next);
            node.random=map.get(temp.random);
            temp=temp.next;
        }
        temp=head;
        return map.get(temp);
    }

    //optimal: 3 steps - O(3N) ie O(N) time,O(1) space

    public ListNode copyRandomList(ListNode head) {
        //step1
        //create temp copy of nodes and point them next to original nodes
        ListNode temp=head;
        while(temp!=null){
            ListNode front=temp.next;
            ListNode copy=new ListNode(temp.val);
            copy.next=front;
            temp.next=copy;
            temp=front;
        }
        temp=head;

        //step 2
        //connecting random pointers of deep copy nodes
        while(temp!=null){
            if(temp.random!=null){
                temp.next.random=temp.random.next;
            }
            else{
                temp.next.random=null;
            }
            temp=temp.next.next;
        }
        temp=head;

        //step 3 - connecting next pointers of the deep copy list & retrieving the original list
        ListNode dummy=new ListNode(0);
        ListNode copy=dummy;
        while(temp!=null){
            ListNode front=temp.next.next;
            copy.next=temp.next;
            temp.next=front;
            temp=temp.next;
            copy=copy.next;
        }
        return dummy.next;
    }

    //3 sum
    //https://leetcode.com/problems/3sum/

    //brute O(N3logM)tc, (O(M)+O(N))sc - log m to put all the unique triplets that sum up to zero in a set in order to remove duplicates

    //better O(N2logM), logM for inserting in set - store frequency of each element in a hashmap, then take adjacent two pointers i and j where starts
    // from i+1 till the length of thr array and iterate over the array. In each iteration, check if -(a[i]+a[j]) exists
    //in the hashmap, if it does, then we have found a triplet, thus we add  a[i],a[j] and the element -(a[i]+a[j]) into a set
    //to ensure no duplicates are added, we sort the triplet before adding it to the set. In each iteration, we first reduce
    //the frequency of i and jth elements in the hashmap (this will make sure that we don't add an element in the triplet
    // that does not exist in the array. this can happen when the sum of two numbers when negated gives one of the two numbers.
    // in this case we might end up taking the same number again from the map. for eg 1st 2 numbers are 1 and -2, negation of their sum wil give 1  - DRY RUN!!)
    //and then after each iteration we again increase their frequencies back to normal so that they can be used in further iterations

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list=new ArrayList<>();
        if(nums.length<3){ //in case there are less than 3 elements in the arr
            return list;
        }
        HashMap<Integer,Integer> map=new HashMap<>();
        HashSet<List<Integer>> set=new HashSet<>();
        //O(logN) insertion of N elements in hashmap
        for(int i:nums){
            map.put(i,map.getOrDefault(i,0)+1);
        }
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],map.get(nums[i])-1);
            for(int j=i+1;j<nums.length;j++){
                map.put(nums[j],map.get(nums[j])-1);
                int sum=-(nums[i]+nums[j]);
                //O(MlogM)+O(logM)
                if(map.containsKey(sum)&&map.get(sum)>0){ //in case we get either of i or j as the sum and reducing their freq equals 0, we don't take them as it would mean there's only one i or j in the whole arr
                    List<Integer> li=new ArrayList<>();
                    li.add(nums[i]);
                    li.add(nums[j]);
                    li.add(sum);
                    Collections.sort(li);
                    set.add(li);
                }
                map.put(nums[j],map.get(nums[j])+1);
            }
            map.put(nums[i],map.get(nums[i])+1);
        }
        for(List<Integer> l:set){
            list.add(l);
        }
        return list;
    }

    //optimal (O(N2))tc - N to iterate pointer a and N to iterate pointers b and c using two sum approach. O(M)space for ans.
    // O(nlogn) complexity for sorting is very less compared to N2 thus whole complexity rounded off to N2
    //using 2 pointer approach - sort the array, take three pointers: a, b & c. a is iterated over the array such that
    // b and c relate to the two pointers of the two sum approach, which iterate over rest of the array from pointer a.
    // If b+c < -a. we move b otherwise we move c. If at any point their sum is equal to -a, we have found our triplet.
    // Sorting is done inorder to skip duplicate elements in a particular iteration

    public List<List<Integer>> threeSum1(int[] nums) {
        List<List<Integer>> ans=new ArrayList<>();
        Arrays.sort(nums);
        for(int i=0;i<nums.length-2;i++){
            if(i==0||(nums[i]!=nums[i-1])){
                int sum=0-nums[i];
                int left=i+1;
                int right=nums.length-1;
                while(left<right){
                    if(nums[left]+nums[right]==sum){
                        List<Integer> list=new ArrayList<>();
                        list.add(nums[i]);
                        list.add(nums[left]);
                        list.add(nums[right]);
                        ans.add(list);
                        while(left<right&&list.get(1)==nums[left]){ //skipping duplicates
                            left++;
                        }
                        while(left<right&&list.get(2)==nums[right]){
                            right--;
                        }
                    }
                    else if((nums[left]+nums[right])>sum){
                        right--;
                    }
                    else{
                        left++;
                    }
                }
            }
        }
        return ans;
    }


    //trap rain water
    //https://leetcode.com/problems/trapping-rain-water/

    //brute force - find unit of water for every index, sum it all up
    //for any index i, units of water can be obtained as: min(leftMax(i),rightMax(i))-currentHeight(i)
    //for every index, we check left and right thus O(N2)tc, O(1)sc

    //better - using prefix and suffix sum arrays to store left and right max for each element
    //think of prefix max array as the array which at any index i contains the max element from index 0 till index i.
    //suffix max array is similar to prefix max array but instead of storing the max from index 0 to i, it stores the maximu for index i to n-1
    //O(3N)tc - 2N for prefix and suffix max array and N for traversing each index, O(2N)space
    //O(3N) time, O(2N) space
    public int trap(int[] height) {
        int n=height.length;
        int[] prefixSum=new int[n];
        int[] suffixSum=new int[n];
        int sum=0;
        prefixSum[0]=height[0];
        suffixSum[n-1]=height[n-1];
        for(int i=1;i<n;i++){
            prefixSum[i]=Math.max(prefixSum[i-1],height[i]); //comparing el at i with largest till i-1
        }
        for(int i=n-2;i>=0;i--){
            suffixSum[i]=Math.max(suffixSum[i+1],height[i]);
        }
        for(int i=0;i<n;i++){
            int max=Math.min(prefixSum[i],suffixSum[i]);
            if(max==height[i]){
                continue;
            }
            sum+=max-height[i];
        }
        return sum;
    }

    //optimal
    //O(N)

    //intuition
    //intuition basically is that whatever is the valid candidate for being leftmax or right max, say we're dealing with
    // left max, then we know for sure that this candidate that we tried to compare with leftmax, we were only able to do
    // it because this candidate was smaller than something on the right. now if this cand becomes a valid leftmax, then I
    // know for sure that on the right there's someone greater. similarly when the next el is also smaller than something
    // on the right and is also smaller than left max, i can ideally find its unit of water by deducting its height from
    // lm because i know lm is smaller than something on the right and current el is smaller than lm

    //while the left is smaller than the right, we won't be moving right ptr. for this reason we know for sure that currently
    // any el that left points to, if it is smaller than right, we know that either that el can be the leftmax or it can be
    // smaller than that. in the former case we simply update leftmax and in the latter case we know that this el is smaller
    // than right. now the fact that we're able to reach this el was because left max was smaller than this right(because
    // that execution only lead us to this element). otherwise we would've moved right. so till it is larger, it won't be
    // moved and we're certain if an el is smaller on the left, it will either be leftmax or not in which case we calc the
    // units of water.
    public int trap1(int[] height) {
        int n=height.length;
        int left=0;
        int right=n-1;
        int leftMax=0;
        int rightMax=0;
        int sum=0;
        while(left<right){
            if(height[left]<=height[right]){
                if(height[left]>leftMax){
                    leftMax=height[left];
                }
                else{
                    sum+=leftMax-height[left];
                }
                left++;
            }
            else{
                if(height[right]>rightMax){
                    rightMax=height[right];
                }
                else{
                    sum+=rightMax-height[right];
                }
                right--;
            }
        }
        return sum;
    }

    //remove duplicates from sorted array
    //https://leetcode.com/problems/remove-duplicates-from-sorted-array/
    //tc (O(NlogN)+O(N))

    //brute - insert all elements in a set and return its size. also modify the first k (ie size of set) elements of the
    // original array to be same as the ones in set.

    //optimal - doing everything inplace without any external space. basically take two pointers i at 0 and j at 1. then
    // iterate over the array such that if nums[i]==nums[j], then move j fwd, basically skipping the duplicate elements
    // till we find a different one. as soon as we do, we move the i one step ahead so that right after the first occurrence
    // of the duplicate element, we place this distinct element that we found and we repeat this until j goes out of bounds.
    // this way we maintain the k distinct elements in the starting of the array after the iteration is done
    //O(N)

    public int removeDuplicates(int[] nums) {
        int i=0;
        int j=1;
        while(j<nums.length){
            if(nums[i]!=nums[j]){ //we're certain that i will always be <=j because when i equals j, then that means they're
                // pointing to the same el and thus j will be incremented as soon as it becomes equal to i. now since we're
                // ensuring j is always < n, since i<=j, i will also be always less than n
                i++;
                nums[i]=nums[j];
            }
            else{
                j++;
            }
        }
        return i+1;
    }

    //max consecutive ones
    //https://leetcode.com/problems/max-consecutive-ones/
    // brute - O(N)

    public int findMaxConsecutiveOnes(int[] nums) {
        int count=0;
        int ans=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==1){
                count++;
            }else{
                count=0;
            }
            ans=Math.max(count,ans);
        }
        return ans;
    }

    //LRU cache
   //https://leetcode.com/problems/lru-cache

    class LRUCache {
        ListNode head;
        ListNode tail;
        HashMap<Integer,ListNode> map;
        int capacity;
        public LRUCache(int capacity) {
            this.head=new ListNode(0,0);
            this.tail=new ListNode(0,0);
            this.head.next=tail;
            this.tail.prev=head;
            this.map=new HashMap<>();
            this.capacity=capacity;
        }

        public int get(int key) {
            if(map.containsKey(key)){
                int val=map.get(key).val;
                remove(map.get(key));
                insert(new ListNode(key,val));
                return val;
            }
            return -1;
        }

        public void put(int key, int value) {
            ListNode node=new ListNode(key,value);
            if(map.containsKey(key)){
                remove(map.get(key));
            }
            if(map.size()==capacity){
                remove(tail.prev); //removing lrc
            }
            insert(node);
        }

        public void insert(ListNode node){
            map.put(node.key,node);
            ListNode headNext=head.next;
            head.next=node;
            node.prev=head;
            node.next=headNext;
            headNext.prev=node;
        }
        public void remove(ListNode node){
            map.remove(node.key);
            ListNode nodePrev=node.prev;
            ListNode nodeNext=node.next;
            nodePrev.next=nodeNext;
            nodeNext.prev=nodePrev;
        }
    }

    //LFU cache
    //https://leetcode.com/problems/lfu-cache/

    class LFUCache {
        int capacity;
        int maxCapacity;
        int minFreq;
        HashMap<Integer,DoublyLinkedList> freqMap;
        HashMap<Integer,ListNode> map;
        class ListNode{
            int key;
            int val;
            int freq;
            ListNode next;
            ListNode prev;
            public ListNode(int key,int val){
                this.key=key;
                this.val=val;
                this.freq=1;
                this.next=this.prev=null;
            }
        }
        class DoublyLinkedList{
            ListNode head;
            ListNode tail;
            int size; //to track the number of nodes in the list (excluding head and prev)
            public DoublyLinkedList(){
                this.head=this.tail=new ListNode(0,0);
                head.next=tail;
                tail.prev=head;
                this.size=0;
            }
            public void insert(ListNode node){
                ListNode headNext=head.next;
                head.next=node;
                node.prev=head;
                node.next=headNext;
                headNext.prev=node;
                size++;
            }
            public void remove(ListNode node){
                ListNode nodePrev=node.prev;
                ListNode nodeNext=node.next;
                nodePrev.next=nodeNext;
                nodeNext.prev=nodePrev;
                size--;
            }
        }
        public LFUCache(int capacity) {
            this.maxCapacity=capacity;
            this.minFreq=0;
            this.freqMap=new HashMap<>();
            this.map=new HashMap<>();
        }

        public int get(int key) {
            if(map.containsKey(key)){
                ListNode node=map.get(key);
                updateNode(node); //updating freq
                return node.val;
            }
            return -1;
        }

        public void put(int key, int value) {
            ListNode node=new ListNode(key,value);
            if(map.containsKey(key)){
                ListNode temp=map.get(key);
                temp.val=value; //updating val of this already existing node and then updating its list
                updateNode(temp); //update freq of this node
            }else{
                if(map.size()>=maxCapacity){ //cache is full
                    DoublyLinkedList list=freqMap.get(minFreq); //retrieving min freq list and removing lfn or lrn accordingly
                    map.remove(list.tail.prev.key);
                    list.remove(list.tail.prev);
                }
                minFreq=1; //we're certain this node is new thus its freq is 1
                map.put(key,node);
                DoublyLinkedList list=freqMap.getOrDefault(minFreq,new DoublyLinkedList());
                list.insert(node);
                freqMap.put(minFreq,list);
            }
        }

        public void updateNode(ListNode node){
            //node that address of this node won't change. we're just updating its list ie putting it in the list of higher frequency thats it
            DoublyLinkedList list=freqMap.get(node.freq);
            list.remove(node);
            if(node.freq==minFreq&&list.size==0){ //in case the list containing this node has become empty after its removal, we update the minFreq
                minFreq++;
            }
            node.freq++;
            DoublyLinkedList newList=freqMap.getOrDefault(node.freq,new DoublyLinkedList());
            newList.insert(node);
            freqMap.put(node.freq,newList);
        }
    }

    //segregate odd even valued elements
    //https://practice.geeksforgeeks.org/problems/segregate-even-and-odd-nodes-in-a-linked-list5035/1
    ListNode divide(int N, ListNode head){
        ListNode current=head;
        ListNode evenHead=null;
        ListNode oddHead=null;
        ListNode evenEnd=null;
        ListNode oddEnd=null;
        while(current!=null){
            if(isEven(current.val)){
                if(evenHead==null){ //first even node
                    evenHead=current;
                    evenEnd=evenHead;
                }
                else{
                    evenEnd.next=current;
                    evenEnd=current;
                }
            }
            else {
                if(oddHead==null){ //first odd
                    oddHead=current;
                    oddEnd=oddHead;
                }
                else{
                    oddEnd.next=current;
                    oddEnd=current;
                }
            }
            current=current.next;
        }
        if(evenHead==null||oddHead==null){ //ie there were no even or odd elements
            return head;
        }
        oddEnd.next=null;
        evenEnd.next=oddHead;
        head=evenHead;
        return head;
    }
    public boolean isEven(int val){
        return val%2==0;
    }
}