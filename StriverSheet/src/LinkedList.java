import java.util.*;

public class LinkedList {
    class ListNode {
        int val;
        int key;
        int freq;
        ListNode next;
        ListNode prev;
        ListNode bottom;
        ListNode random;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
        public ListNode(int key,int val){
            this.key=key;
            this.val=val;
            this.freq=1;
        }
    }

    //reverse linked list
    //https://leetcode.com/problems/reverse-linked-list/

    //recursive
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) { //iterating till head is at the end
            return head;
        }
        ListNode dummy = reverseList(head.next);
        //switching directions
        head.next.next = head;
        head.next = null;
        return dummy;
    }

    //iterative
    public ListNode reverseList2(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode prev = null;
        ListNode present = head;
        ListNode next = present.next;
        while (present != null) {
            present.next = prev;
            prev = present;
            present = next;
            if (next != null) {
                next = next.next;
            }

        }
        return prev;
    }

    //middle of linked list
    //https://leetcode.com/problems/middle-of-the-linked-list/
    //fast-slow pointer
    public ListNode middleNode(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    //merge two sorted lists
    //https://leetcode.com/problems/merge-two-sorted-lists

    //storing ans in a new list (O(n) space)
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode head = new ListNode();
        ListNode temp = head;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                temp.next = list1;
                list1 = list1.next;
            } else {
                temp.next = list2;
                list2 = list2.next;
            }
            temp = temp.next;
        }
        temp.next = list1 != null ? list1 : list2;
        return head.next;
    }

    //in-place - Create an ans node pointing to list 1 which will store our ans list.
    // Always point list 1 to the smaller val among the two - list 1 and list 2. Then move list1 till it is smaller than list 2.
    //Before moving list1 to the next node, point a temp node to the current node of list 1 to keep track of the last smallest node.
    // Once list 1 node val becomes greater than list 2 node, point the temp ie point the last smallest node to list2 node, then
    //swap list1 and 2. Repeat till any of the list becomes null, then return the ans node.
    public ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.val > list2.val) { //point list 1 to smaller val
            ListNode temp = list1;
            list1 = list2;
            list2 = temp;
        }
        ListNode ans = list1;
        while (list1 != null && list2 != null) {
            ListNode temp = null;
            while (list1 != null && list1.val <= list2.val) {
                temp = list1;
                list1 = list1.next;
            }
            //list2 is smaller than list1, thus change direction and swap
            temp.next = list2; //change direction of the last smallest node to whatever list2 is pointing to
            //swap
            ListNode tmp = list1;
            list1 = list2;
            list2 = tmp;
        }
        return ans;
    }

    //remove nth node from the end
    //https://leetcode.com/problems/remove-nth-node-from-end-of-list/

    //brute force, applying the logic of removing a node at any index
    //(O(n+n=2n)) TC, space optimization much better than the other soln
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode temp = head;
        int size = 0;
        if (head == null || head.next == null) {
            return null;
        }
        while (temp != null) { //O(n)
            size++;
            temp = temp.next;
        }
        //first element is to be removed
        if (n == size) { //O(n)
            head = head.next;
            return head;
        }
        //last element is to be removed
        if (n == 1) { //O(n)
            ListNode p = get(head, size - 2);
            p.next = null;
            return head;
        }
        //O(n)
        ListNode prev = get(head, size - n - 1); //element previous to nth element from the last
        prev.next = prev.next.next;
        return head;
    }

    public ListNode get(ListNode head, int index) {
        ListNode node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    //fast and slow pointer approach - Point both slow and fast to head. Move fast by n times. Then move both fast and slow till fast's next becomes null
    //then point slow.next to slow.next.next. Edge case: when we wish to dlt the first node. In this case after n steps
    //fast will reach the last node and its next will be null. Thus, the case we will never be able to move the slow pointer
    //so, in this case also we simply point slow.next to slow.next.next
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode temp = new ListNode();
        temp.next = head;
        ListNode fast = temp;
        ListNode slow = temp;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return temp.next;
    }

    //add two numbers
    //https://leetcode.com/problems/add-two-numbers/

    //only one solution which is optimal
    //edge cases - two lists have different lengths, two lists have same length

    //create a dummy node, point a temp node to it. Initialize carry as 0 and iterate over both lists, adding the values of current l1
    // and l2 to a temp sum variable. If there's a carry, add it to carry variable and then create a new node which stores
    // the ones place value of the sum which contains the carry and point temp's next to that node (This will work in both cases - carry and no carry).
    // Then move temp. Iteration is done till list 1 and 2 become null and the carry becomes zero. Then return dummy.next which will be our ans list
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode temp = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int sum = 0;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            sum += carry;
            carry = sum / 10;
            temp.next = new ListNode(sum % 10);
            temp = temp.next;
        }
        return dummy.next;
    }

    //delete node in linked list (no head given)
    //https://leetcode.com/problems/delete-node-in-a-linked-list/
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    //intersection (optimal - O(n+m))
    //https://leetcode.com/problems/intersection-of-two-linked-lists/

    //brute (n*m) - traverse both nodes simultaneously and break if a node matches

    //better (n+m)tc, o(n+m)space - traverse both, add nodes in a map if not already there. If a node already exists, return that node

    //optimal o(n+m)tc, o(1)space - take 2 dummy nodes pointing to head1 and 2. Then move both by n and m times.
    // If any dummy becomes null, point it to the head of other list. Iterate till both dummies become equal.
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode temp1 = headA;
        ListNode temp2 = headB;
        while (temp1 != temp2) {
            temp1 = temp1 == null ? headB : temp1.next;
            temp2 = temp2 == null ? headA : temp2.next;
        }
        return temp1;
    }

    //cycle detection
    //https://leetcode.com/problems/linked-list-cycle/
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    //reverse nodes in k-grp
    //https://leetcode.com/problems/reverse-nodes-in-k-group/
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k < 1 || head == null) {
            return head;
        }
        int length = getLength(head);
        int numGrps = length / k;
        ListNode prev = null;
        ListNode current = head;
        while (numGrps > 0) {
            ListNode next = current.next;
            ListNode temp = prev;
            ListNode newEnd = current;
            for (int i = 0; current != null && i < k; i++) {
                current.next = prev;
                prev = current;
                current = next;
                if (next != null) {
                    next = next.next;
                }
            }
            if (temp != null) {
                temp.next = prev;
            } else {
                head = prev;
            }
            newEnd.next = current;
            prev = newEnd;
            numGrps--;
        }
        return head;
    }

    public int getLength(ListNode head) {
        ListNode temp = head;
        int count = 0;
        while (temp != null) {
            temp = temp.next;
            count++;
        }
        return count;
    }

    //palindrome linked list
    //https://leetcode.com/problems/palindrome-linked-list/
    public boolean isPalindrome(ListNode head) {
        ListNode mid = middleNode(head);
        ListNode midHead = reverseList(mid);
        ListNode rereverse = midHead;
        while (head != null && midHead != null) {
            if (head.val != midHead.val) //not a palindrome
                break;
            head = head.next;
            midHead = midHead.next;
        }
        reverseList(rereverse);
        return (head == null) || (midHead == null); //when either of the 2 lists were completely traversed
    }

    //starting node of cycle
    //https://leetcode.com/problems/linked-list-cycle-ii/
    //the hasCycle method only tells whether cycle exists or not. It is not necessary that fast and slow will always
    // meet at the starting point of the node, so we implement following approach to find starting node of cycle:
    ///find length of cycle. Take two pointers, move the second till length of cycle. Then move both till they meet
    public ListNode detectCycle(ListNode head) {
        int length = getCycleLength(head);
        if (length == 0) {
            return null;
        }
        ListNode first = head;
        ListNode second = head;
        while (length > 0) {
            second = second.next;
            length--;
        }
        while (first != second) {
            first = first.next;
            second = second.next;
        }
        return first;
    }

    public int getCycleLength(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        int length = 0;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                ListNode temp = slow;
                do {
                    temp = temp.next;
                    length++;
                } while (temp != slow);
                return length;
            }
        }
        return 0;
    }

    //flattening of linked list
    //https://practice.geeksforgeeks.org/problems/flattening-a-linked-list/1

    //O(total nodes)

    //recursively move till the last node, then while coming back, merge the last two nodes in each call
    ListNode flatten(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode lastNode = flatten(head.next);
        ListNode mergedHead = mergeTwoLists3(head, lastNode);
        return mergedHead;
    }

    public ListNode mergeTwoLists3(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.val > list2.val) { //point list 1 to smaller val
            ListNode temp = list1;
            list1 = list2;
            list2 = temp;
        }
        ListNode ans = list1;
        while (list1 != null && list2 != null) {
            ListNode temp = null;
            while (list1 != null && list1.val <= list2.val) {
                temp = list1;
                list1 = list1.bottom;
            }
            //list2 is smaller than list1, thus change direction and swap
            temp.bottom = list2; //change direction of the last smallest node to whatever list2 is pointing to
            //swap
            ListNode tmp = list1;
            list1 = list2;
            list2 = tmp;
        }
        return ans;
    }

    //rotate list
    //https://leetcode.com/problems/rotate-list/description/

    //brute force (O(n*k))
    // iterate k times and in each iteration find the last node, point it to head, then point its prev node to null and make the last node head

    //optimal (O(n))
    //find length, then find last, point it to head, then iterate till length-rotations-1 (to find the last element after k rotations)
    //then make the new last.next as head, and point the new last to null

    public ListNode rotateRight(ListNode head, int k) {
        int length=1;
        if(head==null||head.next==null||k<=0){
            return head;
        }
        ListNode lastNode=head;
        while(lastNode.next!=null){
            lastNode=lastNode.next;
            length++;
        }
        lastNode.next=head;
        int rotations=k%length; //in case k>length
        ListNode newLast=head;
        for(int i=0;i<length-rotations-1;i++){
            newLast=newLast.next;
        }
        head=newLast.next;
        newLast.next=null;
        return head;
    }


    //copy list with random pointer
    //https://leetcode.com/problems/copy-list-with-random-pointer/

    //brute: create a copy of all nodes in a hashmap, then connect all the next and random pointers of the nodes stored
    // in the hashmap according to the original list
    //O(N) time, O(N) space


    //optimal: 3 steps - O(3N) ie O(N) time,O(1) space
    public ListNode copyRandomList(ListNode head) {
        //step 1
        //create temp copy of nodes and point them next to original nodes
        ListNode iter=head;
        ListNode front=head;
        while(iter!=null){
            front=iter.next;
            ListNode copy=new ListNode(iter.val);
            iter.next=copy;
            copy.next=front;
            iter=front;
        }
        iter=head;
        //step 2
        //connecting random pointers of deep copy nodes
        while(iter!=null){
             if(iter.random!=null){
                 iter.next.random=iter.random.next; //pointing deep copy of current node to the deep copy of the node
                 // its original's random is pointing to
             }
             else
                 iter.next.random=null; //original's random pointing to null
             iter=iter.next.next;
        }

        //step 3 - connecting next pointers of the deep copy list retrieving the original list
        iter=head;
        ListNode pseudoHead=new ListNode(0); //head of deep copy list
        ListNode copy=pseudoHead;
        while(iter!=null){
            front=iter.next.next;
            copy.next=iter.next;
            iter.next=front;
            copy=copy.next;
            iter=iter.next;
        }
        return pseudoHead.next;
    }

    //3 sum
    //https://leetcode.com/problems/3sum/

    //brute O(N3logM)tc, (O(M)+O(N))sc - log m to put all the unique triplets that sum up to zero in a set in order to remove duplicates

    //better O(N2logM), logM for inserting in set - store frequency of each element in a hashmap, then take adjacent two pointers i and j where starts
    // from i+1 till the length of thr array and iterate over the array. In each iteration, check if -(a[i]+a[j]) exists
    //in the hashmap, if it does, then we have found a triplet, thus we add  a[i],a[j] and the element -(a[i]+a[j]) into a set
    //to ensure no duplicates are added, we sort the triplet before adding it to the set. In each iteration, we first reduce
    //the frequency of i and jth elements in the hashmap (this will make sure that we don't add an element in the triplet that does not exist in the array - DRY RUN!!)
    //and then after each iteration we again increase their frequencies back to normal so that they can be used in further iterations

    //dry run for better understanding
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list=new ArrayList<>();
        HashMap<Integer,Integer> map=new HashMap<>(); //storing frequencies
        List<Integer> t=new ArrayList<>();
        if(nums.length<3){
            return list;
        }
        for(int i:nums){
            if(map.containsKey(i)){
                map.put(i,map.get(i)+1);
            }
            else
                map.put(i,1);
        }
        HashSet<List<Integer>> set=new HashSet<>();
        for(int i=0;i<nums.length;i++){

            map.put(nums[i],map.get(nums[i])-1);
            for(int j=i+1;j<nums.length;j++){
                List<Integer> temp=new ArrayList<>();
                map.put(nums[j],map.get(nums[j])-1);
                int c=-(nums[i]+nums[j]);
                if(map.containsKey(c)&&map.get(c)>0){
                    temp.add(nums[i]);
                    temp.add(nums[j]);
                    temp.add(c);
                    Collections.sort(temp);
                    set.add(temp);
                }
                map.put(nums[j],map.get(nums[j])+1);
            }
            map.put(nums[i],map.get(nums[i])+1);
        }
        for(List<Integer> l:set ){
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

    public List<List<Integer>> threeSum2(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> list =new ArrayList<>();
        for(int i=0;i<nums.length-2;i++){
            if(i==0||(i>0&&nums[i]!=nums[i-1])){ //skipping duplicates for a
                int low=i+1;
                int hi=nums.length-1;
                int sum=0-nums[i];
                while(low<hi){
                    if(nums[low]+nums[hi]==sum){
                        list.add(List.of(nums[i],nums[low],nums[hi]));
                        while(low<hi&&nums[low]==nums[low+1]){ //skipping duplicates for b
                            low++;
                        }
                        while(low<hi&&nums[hi]==nums[hi-1]){ //skipping duplicates for c
                            hi--;
                        }
                        low++;
                        hi--;
                    }
                    else if(nums[low]+nums[hi]<sum)
                        low++;
                    else
                        hi--;
                }
            }
        }
        return list;
    }

    //trapping rainwater
    //https://leetcode.com/problems/trapping-rain-water/

    //brute force - find unit of water for every index, sum it all up
    //for any index i, units of water can be obtained as: min(leftMax(i),rightMax(i))-currentHeight(i)
    //for every index, we check left and right thus O(N2)tc, O(1)sc

    //better -  using prefix and suffix sum arrays to store left and right max for each element
    //think of prefix max array as the array which at any index i contains the max element from index 0 till index i.
    //suffix max array is similar to prefix max array but instead of storing the max from index 0 to i, it stores the maximun for index i to n-1
    //O(3N)tc - 2N for prefix and suffix max array and N for traversing each index, O(2N)space

    //optimal O(N) time, O(1) space - two pointer
    //intuition - In each iteration, we check if the left pointer's val is less than the right pointer's val. If it is,
    // then we check if the left max is smaller than current val of left. If it is, we update the val of left max.
    // If it isn't we come to the conclusion that on the right there is a value greater than or equal to the left val
    // and the left val is smaller than the left max, and it is obvious that we have crossed the left max at some point,
    // and thus there is someone greater than equal to the left max on the right, making it the minimal of the two.
    // Therefore, we add left max-left[i] to the ans. Similar approach follows for when right val< left val.
    // Through this, in each case we are sure that we have the minimal among the left and right maxes
    public int trap(int[] height) {
        int left=0;
        int right=height.length-1;
        int leftMax=0;
        int rightMax=0;
        int ans=0;
        while(left<=right){
            if(height[left]<=height[right]){
                if(leftMax<height[left]){
                    leftMax=height[left];
                }
                else{
                    ans+=leftMax-height[left];

                }
                left++;
            }
            else{
                if(rightMax<height[right]){
                    rightMax=height[right];
                }
                else{
                    ans+=rightMax-height[right];

                }
                right--;
            }
        }
        return ans;
    }

    //max consecutive ones
    //https://leetcode.com/problems/max-consecutive-ones
    public int findMaxConsecutiveOnes(int[] nums) {
        int count=0;
        HashSet<Integer> set=new HashSet<>();

        int max=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==1){
                count++;
            }
            else{
                count=0;
            }
            max=Math.max(count,max);
        }
        return max;
    }

    //remove duplicates from sorted array

    //brute (O(NlogN)+O(N))
    //put all elements in a set, then return size of set. We also have to modify the array in place such that its first k
    // elements are the unique elements where k is the size of the unique elements
    public int removeDuplicates(int[] nums) {
        HashSet<Integer> set=new HashSet<>();
        for(int i:nums){
            set.add(i);
        }
        int k=set.size();
        int c=0;
        for(int i:set){
            nums[c]=i;
            c++;
        }
        return k;
    }

    //optimal - O(N)
    //take two pointers i at first position and j at second. If i val==j val, move j by one, otherwise move i by one and make i val= j val
    //repeat until j goes out of bounds. In the end, i will be at the last unique element, and thus we return i+1;
    public int removeDuplicates2(int[] nums) {
        int i=0;
        int j=1;
        while(j<nums.length){
            if(nums[i]!=nums[j]){
                i++;
                nums[i]=nums[j];
            }
            else{
                j++;
            }
        }
        return i+1;
    }

    //LRU cache
    //https://leetcode.com/problems/lru-cache/

    //least recently used (lru) refers to that recently used key-value pair which is the one we have not quite recently
    // used and is the oldest one from all the key-value pairs we recently used
    //both the get() and put() functions must be in O(1) complexity

    //here we use a hashmap to store key value pairs and a doubly linked list to maintain the recently used order

    //complexity of put() and get() functions is O(1). Hashmap in worst case would have (N) tc if there are collisions.
    //A collision in a map is a situation where two or more key objects produce the same final hash value and hence point
    // to the same bucket location or array index. In JDK 8, hashmap has been modified such that it is implemented as a
    // tree if it becomes densely populated and thus wc tc is O(logN)
    class LRUCache {
        ListNode head=new ListNode(0,0);
        ListNode tail=new ListNode(0,0);
        Map<Integer,ListNode> map=new HashMap<>();
        int capacity=0;
        public LRUCache(int capacity) {
            this.capacity=capacity;
            head.next=tail;
            tail.prev=head;
        }
        public int get(int key) {
            if(map.containsKey(key)){
                ListNode node=map.get(key);
                remove(node); //updating recently used
                insert(node);
                return node.val;
            }
            return -1;
        }
        public void put(int key, int value) {
            ListNode newNode=new ListNode(key,value);
            if(map.containsKey(key)){
                remove(map.get(key));
            }
            if(map.size()==capacity){
                remove(tail.prev);
            }
            insert(newNode);
        }
        public void insert(ListNode node){
            map.put(node.key,node);
            ListNode temp=head.next;
            head.next=node;
            node.prev=head;
            node.next=temp;
            temp.prev=node;
        }
        public void remove(ListNode node){
            map.remove(node.key);
            ListNode p=node.prev;
            ListNode n=node.next;
            p.next=n;
            n.prev=p;
        }
    }

    //LFU(least frequently used) cache
    //This is similar to the lru approach
    //We have to modify the put function such that when the capacity is full, the least frequently used key is removed.
    //To keep track of lfu key, we maintain a separate hashmap(frequency,list) to store the map how frequently each key is being used. Each node is put in a separate list depending on its frequency. Min frequency will be set to the min key in the frequency map that does not have an empty list.
    //If there are multiple nodes in a list for a certain frequency, meaning that multiple nodes have the same frequency, then we apply
    //the lru logic in the list of that particular frequency
    class LFUCache {
        Map<Integer,DoublyLinkedList> freqMap;
        Map<Integer,ListNode> map;
        int capacity=0;
        int currentSize;
        int minFreq;
        public LFUCache(int capacity) {
            this.capacity=capacity;
            currentSize=0;
            minFreq=0;
            this.freqMap=new HashMap<>();
            this.map=new HashMap<>();
        }

        public int get(int key) {
            ListNode node=map.get(key);
            if(node==null){ //node does not exist
                return -1;
            }
            updateNode(node); //update frequency
            return node.val;
        }

        public void put(int key, int value) {
            ListNode node=new ListNode(key,value);
            if(capacity==0){
                return;
            }
            if(map.containsKey(key)){ //map already has key, thus no change in size, just update value
                ListNode temp=map.get(key);
                temp.val=value;
                updateNode(temp);
            }else{ //map doesn't contain key, thus we update size
                currentSize++;
                if(map.size()>=capacity){
                    DoublyLinkedList minFreqList=freqMap.get(minFreq);
                    map.remove(minFreqList.tail.prev.key);
                    minFreqList.remove(minFreqList.tail.prev);
                }
                minFreq=1; // min freq will be set to 1 as we are adding a new element which has been used only once
                DoublyLinkedList list=freqMap.getOrDefault(minFreq,new DoublyLinkedList());
                list.insert(node);
                freqMap.put(minFreq,list);
                map.put(key,node);
            }
        }
        public void updateNode(ListNode node){
            int freq=node.freq;
            DoublyLinkedList list=freqMap.get(freq); //retrieve old list of the node
            list.remove(node);
            if(freq==minFreq&&list.size==0){ //in case the freq was minimum, and now it has an empty list corresponding to it
                minFreq++;
            }
            node.freq++;
            DoublyLinkedList newList=freqMap.getOrDefault(node.freq,new DoublyLinkedList()); //retrieving new list with the new freq or creating one if it doesn't exist
            newList.insert(node);
            freqMap.put(node.freq,newList); //updating list at new freq after inserting the node
        }
    }
    class DoublyLinkedList{
        ListNode head;
        ListNode tail;
        int size;
        public DoublyLinkedList(){
            this.head=new ListNode(0,0);
            this.tail=new ListNode(0,0);
            head.next=tail;
            tail.prev=head;
            this.size=0;
        }
        public void insert(ListNode node){
            ListNode temp=head.next;
            head.next=node;
            node.prev=head;
            node.next=temp;
            temp.prev=node;
            size++;
        }

        public void remove(ListNode node){
            ListNode p=node.prev;
            ListNode n=node.next;
            p.next=n;
            n.prev=p;
            size--;
        }
    }


}
