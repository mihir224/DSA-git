import java.util.*;

class Heaps {
    //imp thing to note: While dealing with heaps ie priority queues, for each problem we have to keep in mind that the
    // way heap would sort itself in a particular problem depends upon the key. For eg in largest element questions, the
    // heap sorts itself on the basis of the given elements of the array and in the top frequent elements problem the heap
    // sorts itself on the basis of the frequency of the given elements. Thus this key on the basis of which heap sorts itself
    // varies from problem to problem

    //IMP
    //in order to implement a pq with a custom pair class, we have to implement the Pair class from the comparable interface
    // and override the compareTo function so as to tell the heap in the pq on the basis of which item among the pair it
    // is going to sort the heap
    class Pair implements Comparable<Pair> {
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int compareTo(Pair other) {
            return Integer.compare(this.second, other.second);
        }
    }
    class ListNode {
        int val;
        ListNode next;
        public ListNode() {
        }
        public ListNode(int val) {
            this.val = val;
        }
        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

    }

    //min heap implementation
    //https://www.codingninjas.com/codestudio/problems/min-heap_4691801?topList=striver-sde-sheet-problems&utm_source=striver&utm_medium=website\
    //not all test cases are passing
    static int[] minHeap(int n, int[][] q) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> heap = new ArrayList<>();

        for (int[] i : q) {
            if (i[0] == 0) { //insertion
                heap.add(i[1]);
                bottomUpHeapify(heap.size() - 1, heap);
            } else { //deletion ie remove topmost element and save it in ans
                if (heap.size() == 0) {
                    continue;
                }
                int el = heap.get(0);
                heap.set(0, heap.get(heap.size() - 1));
                heap.remove(heap.size() - 1);
                list.add(el);
                topDownHeapify(0, heap); //to heapify the tree after adding last node at the root
            }
        }
        int[] ans = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }

    public static void topDownHeapify(int i, ArrayList<Integer> heap) {
        int smallest = i;
        int l = 2 * i + 1; //since in a list we're following 0 based indexing
        int r = 2 * i + 2;
        if (l < heap.size() && heap.get(l) < heap.get(smallest)) {
            smallest = l;
        }
        if (r < heap.size() && heap.get(r) < heap.get(smallest)) {
            smallest = r;
        }
        if (smallest != i) {
            int temp = heap.get(smallest);
            heap.set(smallest, i);
            heap.set(i, temp);
            topDownHeapify(smallest, heap);
        }
    }

    public static void bottomUpHeapify(int i, ArrayList<Integer> heap) {
        int parent = (i / 2) - 1;
        if (parent > 0 && heap.get(parent) > heap.get(i)) {
            int temp = heap.get(parent);
            heap.set(parent, i);
            heap.set(i, temp);
            bottomUpHeapify(parent, heap);
        }
    }

    //kth largest element in a stream
    //https://leetcode.com/problems/kth-largest-element-in-a-stream/
    // brute - sort the given array in decreasing order and then return kth element (O(NlogN))
    //using max heap would also take near about NlogN as we insert all N elements inside the max heap (worst case when k
    // is N) and then we'd have to traverse all the items inside the max heap since its top would contain the largest element.
    // Each insertion will take logN of time thus total complexity is NlogN whereas at all times we only insert k elements in min heap
    // and thus the kth largest element will always be on top and thus we use min heap

    //using priority queue (min heap)
    //O(N*logK)
    class KthLargest {
        int[] nums;
        int k;
        PriorityQueue<Integer> pq;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            this.nums = nums;
            this.pq = new PriorityQueue<>();
            for (int i : nums) {
                pq.offer(i);
                if (pq.size() > k) {
                    pq.poll();
                }
            }
        }

        public int add(int val) {
            pq.offer(val);
            if (pq.size() > k) {
                pq.poll();
            }
            return pq.peek();
        }
    }

    //kth largest element in an array
    //https://leetcode.com/problems/kth-largest-element-in-an-array/
    //O(NlogK)
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i : nums) {
            pq.add(i);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        return pq.peek();
    }

    //maximum sum combination
    //https://www.interviewbit.com/problems/maximum-sum-combinations/

    //brute - O(N2logN) - gives TLE on interview bit as it is not optimised. Basically find all possible sum combinations, store them in a pq and
    // follow the kth largest logic to keep at max C elements in PQ(min heap). Then, return the elements of PQ in reverse order
    public ArrayList<Integer> solve(ArrayList<Integer> A, ArrayList<Integer> B, int C) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i : A) {
            for (int j : B) {
                int sum = i + j;
                pq.offer(sum);
                if (pq.size() > C) {
                    pq.poll();
                }
            }
        }
        while (!pq.isEmpty()) {
            ans.add(0, pq.poll());
        }
        return ans;
    }

    //optimal (O(NlogN)) - java soln not working as the compareTo method is not implemented correctly and thus I've implemented this in c++
    public class Solution {
        class Pair implements Comparable<Pair> {
            int first;
            int second;
            Pair pair;

            public Pair(int first, int second) {
                this.first = first;
                this.second = second;
            }

            public Pair(int first, Pair p) {
                this.first = first;
                this.pair = p;
            }

            @Override
            public int compareTo(Pair other) {
                if (this.first != other.first) {
                    return Integer.compare(this.first, other.first);
                }
                return Integer.compare(this.second, other.second);
            }
        }

        public ArrayList<Integer> solve(ArrayList<Integer> A, ArrayList<Integer> B, int C) {
            ArrayList<Integer> ans = new ArrayList<>();
            PriorityQueue<Pair> pq = new PriorityQueue<>(Collections.reverseOrder());
            Set<Pair> set = new HashSet<>();
            Collections.sort(A);
            Collections.sort(B);
            int n = A.size();
            pq.offer(new Pair(A.get(n - 1) + B.get(n - 1), new Pair(n - 1, n - 1)));
            set.add(new Pair(n - 1, n - 1));
            while (C > 0) {
                Pair max = pq.poll();
                int left = max.pair.first;
                int right = max.pair.second;
                ans.add(max.first);
                if (left > 0 && right > 0 && !set.contains(new Pair(left - 1, right))) {
                    pq.add(new Pair(A.get(left - 1) + B.get(right), new Pair(left - 1, right)));
                    set.add(new Pair(left - 1, right));
                }
                if (left > 0 && right > 0 && !set.contains(new Pair(left, right - 1))) {
                    pq.add(new Pair(A.get(left) + B.get(right - 1), new Pair(left, right - 1)));
                    set.add(new Pair(left, right - 1));
                }
                C--;
            }
            return ans;
        }
    }

    //this code works fine and follows the same approach

//    vector<int> Solution::solve(vector<int>& A, vector<int>& B, int C) {
//        vector<int> ans;
//        priority_queue<pair<int, pair<int, int>>> pq;
//        set<pair<int, int>> set;
//        sort(A.begin(), A.end());
//        sort(B.begin(), B.end());
//        int n = A.size();
//        pq.push(make_pair(A[n-1] + B[n-1], make_pair(n-1, n-1)));
//        set.insert(make_pair(n-1, n-1));
//        while(C > 0) {
//            pair<int, pair<int, int>> max = pq.top();
//            pq.pop();
//            int left = max.second.first;
//            int right = max.second.second;
//            ans.push_back(max.first);
//            if (left > 0 && right > 0 && set.find(make_pair(left-1, right)) == set.end()) {
//                pq.push(make_pair(A[left-1] + B[right], make_pair(left-1, right)));
//                set.insert(make_pair(left-1, right));
//            }
//            if (left > 0 && right > 0 && set.find(make_pair(left, right-1)) == set.end()) {
//                pq.push(make_pair(A[left] + B[right-1], make_pair(left, right-1)));
//                set.insert(make_pair(left, right-1));
//            }
//            C--;
//        }
//        return ans;
//    }

    //find median from data stream
    //https://leetcode.com/problems/find-median-from-data-stream/

    //brute would be to sort the elements and store them in a DS as they're being added from the stream and then find the median of the total elements up till now, the conventional way
    //O(N2)

    //optimal - O(NlogN) - we can divide the elements coming from the stream across a max and min heap. This way,
    // we wouldn't have to sort the incoming elements explicitly and we can just use the left of the medain (top of max heap)
    // and the right of the median (top of min heap) to find the median. The max heap would store all the elements on the l
    // eft of median (so that the top is left of median) and similarly the min heap would store all the elements on the right
    // of median. In case the total elements up till now are odd, we will store the larger amount of elements in max heap and
    // the median would thus be its top.
    class MedianFinder {
        PriorityQueue<Integer> maxHeap;
        PriorityQueue<Integer> minHeap;

        public MedianFinder() {
            this.maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            this.minHeap = new PriorityQueue<>();
        }

        public void addNum(int num) {
            if (maxHeap.isEmpty() || maxHeap.peek() >= num) {
                maxHeap.offer(num);
            } else {
                minHeap.offer(num);
            }
            if (maxHeap.size() > minHeap.size() + 1) { //max heap is allowed to store at most minHeap.size()+1 elements (case when total elements up till now are odd -then we store more elements in max heap)
                minHeap.offer(maxHeap.poll());
            } else if (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }
        }

        public double findMedian() {
            if (maxHeap.size() > minHeap.size()) { //odd elements
                return maxHeap.peek();
            }
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }

    //merge k sorted arrays
    //https://practice.geeksforgeeks.org/problems/merge-k-sorted-arrays/1
    //O(NlogK)
    class TreeNode implements Comparable<TreeNode> {
        TreeNode left;
        TreeNode right;
        TreeNode next;
        int val;
        int i;
        int j;

        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = this.next = null;
        }

        public TreeNode(int val, int i, int j) {
            this.val = val;
            this.i = i;
            this.j = j;
        }

        public int compareTo(TreeNode other) {
            return Integer.compare(this.val, other.val);
        }
    }

    public ArrayList<Integer> mergeKArrays(int[][] arr, int K) {
        PriorityQueue<TreeNode> pq = new PriorityQueue<>();
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            pq.offer(new TreeNode(arr[i][0], i, 0));
        }
        while (pq.size() > 0) {
            TreeNode temp = pq.poll();
            ans.add(temp.val);
            int i = temp.i;
            int j = temp.j;
            if (j + 1 < arr[i].length) { //next of current element exists in its respective array
                pq.offer(new TreeNode(arr[i][j + 1], i, j + 1));
            }
        }
        return ans;
    }

    //k most frequent elements
    //https://leetcode.com/problems/top-k-frequent-elements/

    //O(NlogK)
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(nums[i])) {
                map.put(nums[i], 1);
            } else {
                map.put(nums[i], map.get(nums[i]) + 1);
            }
        }
        for (int i : map.keySet()) {
            pq.offer(new Pair(i, map.get(i)));
            if (pq.size() > k) {
                pq.poll();
            }
        }
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[k - i - 1] = pq.poll().first;
        }
        return ans;
    }

    //distinct numbers in a window
    //https://www.interviewbit.com/problems/distinct-numbers-in-window/

    //brute force: iterate over every window and in each iteration run two for loops to count the number of distinct elements
    // in each window. To find the number distinct elements in a particular window or array, we take 2 pointers - i from 0 to
    // arr.length and j from i+1 to arr.length. In each iteration of i we check if arr[i]==arr[j] ie if element at ith index
    // appears somewhere else further in the array and if it doesn't we increase the count for that iteration of i.
    // Otherwise we don't. The inner 2 for loops take k^2 complexity and the outer loop for each window runs till N-K+1.
    //Thus total complexity is - O((N-K+1)*K2)

    //better - O(N) - using hashmap. For each window, we can store the frequency of each element inside a map and for
    // that window the size of the map would give us the number of distinct windows in that map. Thus we initially iterate
    // over tha first window, map the frequency of all elements and then we start iterating from the kth index ie right
    // where the first window ends. Then we obtain the previous elements through i-k and check if the i-kth element exists
    // in the map. If it does and its frequency is 1, we remove it from the map and if it is more than 1, we decrease its
    // frequency by 1. This will make sure that the previous elements which are not in the window anymore are removed(ie
    // their frequency is decreased). Size of the map in each iteration would give us our ans.

    public ArrayList<Integer> dNums(ArrayList<Integer> A, int k) {
        Map<Integer,Integer> map=new HashMap<>();
        ArrayList<Integer> ans=new ArrayList<>();
        for(int i=0;i<k;i++){
            map.put(A.get(i),map.getOrDefault(A.get(i),0)+1);
        }
        ans.add(map.size());
        for(int i=k;i<A.size();i++){
            if(map.get(A.get(i-k))==1){
                map.remove(A.get(i-k));
            }
            else{
                map.put(A.get(i-k),map.get(A.get(i-k))-1);
            }
            map.put(A.get(i),map.getOrDefault(A.get(i),0)+1);
            ans.add(map.size());
        }
        return ans;
    }

    //merge k sorted lists
    //https://leetcode.com/problems/merge-k-sorted-lists/

    //brute - merge all k linked lists into 1 and then sort it.
    //tc-O(N*klog(N*k) assuming all k lists have N elements)

    //optimal - O(NlogK) using same approach as merging k sorted arrays
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq=new PriorityQueue<>((a,b)->a.val-b.val);
        ListNode ans =new ListNode();
        ListNode temp=ans;
        for(ListNode lp:lists){
            if(lp!=null)
                pq.offer(lp);
        }
        while(pq.size()>0){
            ListNode l=pq.poll();
            temp.next=l;
            temp=temp.next;
            if(l.next!=null){
                pq.offer(l.next);
            }
        }
        return ans.next;
    }


}