import java.util.*;

public class Heaps{
    //min heap implementation
    //https://www.codingninjas.com/codestudio/problems/min-heap_4691801?leftPanelTab=0

    //for insertion and deletion we follow the standard procedures ie for insertion, we insert the node at the last index
    // and then compare it with parents and recursively doing the same for the ancestors and for deletion, we basically
    // store the root somewhere and then we swap the root with the last element and perform heapify on reduced size of the
    // heap after removing the last element

    //IMP!!!!!
    //now the thing is, to heapify from bottom up, we use the parent approach instead of the child one. this is because
    // if we wanted to use the child approach, we'd have to heapify every node from  last node in the second last level
    // till the 1st node. this would've taken nlogn but we require the insertion and deletion operations to be logn. moreover,
    // in the buildheap function used in heapify and heapsort, it is necessary for us to convert every node to heap when
    // taken as a root. thus we have to use this nlogn approach in that problem as we can not implement buildheap from top down. it is necessary to implement it from bottom up

    //O(NlogN)time - since we use heapify in both insertion and deletion operations of N node and O(N)space - to store
    // the nodes in a list
    static int[] minHeap(int n, int[][] q) {
        List<Integer> ansList=new ArrayList<>();
        List<Integer> heap=new ArrayList<>();
        for(int[] query:q){
            if(query[0]==0){//normal insertion procedure
                heap.add(query[1]);
                bottomUpHeapify(heap.size()-1,heap);
            }else{ //deletion
                if(heap.size()==0){
                    continue;
                }
                ansList.add(heap.get(0));
                heap.set(0,heap.get(heap.size()-1));
                heap.remove(heap.size()-1);
                topDownHeapify(0,heap);
            }
        }
        int[] ans=new int[ansList.size()];
        for(int i=0;i<ansList.size();i++){
            ans[i]=ansList.get(i);
        }
        return ans;
    }
    //insertion
    static void bottomUpHeapify(int i,List<Integer> heap){
        int parent=(i-1)/2;
        if(heap.get(parent)>heap.get(i)){
            int temp=heap.get(parent);
            heap.set(parent,heap.get(i));
            heap.set(i,temp);
            bottomUpHeapify(parent,heap);
        }
    }
    //deletion
    static void topDownHeapify(int i,List<Integer> heap){
        int smallest=i;
        int l=2*i+1;
        int r=2*i+2;
        if(l<heap.size()&&heap.get(l)<heap.get(smallest)){
            smallest=l;
        }
        if(r<heap.size()&&heap.get(r)<heap.get(smallest)){
            smallest=r;
        }
        if(smallest!=i){ //ie value of smallest was changed above
            int temp=heap.get(i);
            heap.set(i,heap.get(smallest));
            heap.set(smallest,temp);
            topDownHeapify(smallest,heap);
        }
    }

    //heapsort
    //https://practice.geeksforgeeks.org/problems/heap-sort/1

    //O(NlogN) tc, O(1) sc
    //Function to build a Heap from array.
    void buildHeap(int arr[], int n) //the reason why we go bottom to up and not top to bottom in this function is because
    // that would've led to incorrect results. for eg: if at 0th level we have 30 and at second level we have 10 and 20,
    // at the third we have a node with val 60, then if we iterated from top to bottom ie from 0th level to last, we would've
    // compared 30 with its children and since no one is violating, we move to the second level. here we would've seen that
    // 20 is violating with 60 so we swap them. now the problem that arises here is that once we have moved this 60 to the
    // second level ie upwards, we won't be encountering it again. so even though 60 has to be at the top, it would be stuck
    // at the second level. thus to rectify this, we have to go from bottom to up so that whatever node we sent upwards, we
    // encounter it again and put it in its right place. this approach is only necessary when we have to heapify every node
    // in the given tree. we only use the basic insertion and deletion operations (which take logN time) when we know that
    // the entire tree is already a heap. in that case we don't have to check every node (to be a heap when taken as root).
    // in those cases, we can just compare the particular node that is inserted with its parent and recursively place it in
    // its place in case of insertion and do the same for the last node that has been inserted at the first position in case
    // of deletion.

    {
        for(int i=n/2-1;i>=0;i--){
            heapify(arr,n,i);
        }
    }

    //Heapify function to maintain heap property.
    void heapify(int arr[], int n, int i)
    {
        int largest=i;
        int l=(2*i)+1;
        int r=(2*i)+2;
        if(l<n&&arr[l]>arr[largest]){
            largest=l;
        }
        if(r<n&&arr[r]>arr[largest]){
            largest=r;
        }
        if(largest!=i){
            swap(i,largest,arr);
            heapify(arr,n,largest);
        }
    }

    //Function to sort an array using Heap Sort.
    public void heapSort(int arr[], int n)
    {
        int[] nums={30,10,20,60};
        heapify(nums,nums.length,0);
        System.out.println(Arrays.toString(nums));
        buildHeap(arr,n);
        int size=n;
        while(size>1){ //we iterate only till size>1 because we're certain when
            //there's only one el left in the heap, it will be at its correct position
            swap(0,size-1,arr);
            size--; //this is done so that the nodes at the last index are no
            //longer accessbile by heapify function. if we didn't do this, then on
            //calling the heapify function, we would've ended up altering the elements at the end
            heapify(arr,size,0); //heapifying the reduced heap
        }
    }

    public void swap(int i, int j, int[] arr){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }

    //kth largest element in a stream
    //https://leetcode.com/problems/kth-largest-element-in-a-stream/

    //brute - maintain a sorted array of size k so that we can return the 1st element as the kth largest element in O(1) time.
    // To insert a new element, if it is smaller than the kth largest element ie the first element of the sorted array, we
    // ignore it otherwise we remove the smallest element from the array and put this element in place of that element,
    // & again sort the array. At the end we return the first element of the array

    //in each add operation, we sort the array, thus time complexity is - O(M*KlogK) and O(k) is the space complexity

    //optimal
    // we use min heap to store k elements so that we can retrieve the min ie the kth element in O(1) time. Initially we
    // try to insert the elements of the arr into the min heap such that size of min heap at all times==k. While inserting
    // an element from the array, if at any moment the size exceeds k, we pop the min element from the min heap. this ensures
    // that after all iterations are completed, min heap stores the last k elements of the sorted version of the given array.
    // This way the min heap at all times stores the kth largest element at the top. Now whenever we wish to add an element,
    // we push it into the min heap and remove the top (so that size goes back to k) and at the end return the current top.
    // Here we have to take care of the edge case that the initial array might have elements<k and thus initially the min
    // heap would have less than k elements. Thus while retrieving the top of the min heap in the add function, we always
    // check if the size of the min heap is > k or not.

    // If we had used a max heap, we'd have to traverse till the end of the heap if we stored k elements which would've taken
    // another O(logN)time along with logn time required to heapify the min heap after inserting an element

    //O(logK) for addition, O(NlogK) for initially inserting N elements into the heap of size k thus O(NlogK) is the overall time, & O(1) space

    class KthLargest {
        int k;
        PriorityQueue<Integer> minHeap=new PriorityQueue<>();
        public KthLargest(int k, int[] nums) {
            this.k=k;
            this.minHeap=new PriorityQueue<>();
            for(int i:nums){
                minHeap.offer(i);
                if(minHeap.size()>k){ // pop in case min heap size exceeds k
                    minHeap.poll();
                }
            }
        }
        public int add(int val) {
            minHeap.offer(val);
            if(minHeap.size()>k){ //checking in case min heap has <=k elements (if initial array had elements < k) and if that is not the case then only we pop the top
                minHeap.poll();
            }
            return minHeap.peek();
        }
    }

    //kth largest element in an unsorted array
    //brute - sort the array in decreasing order, then return the kth element
    //O(NlogN) time, O(1) space

    //optimal - use min heap & try to insert the elements of the array such that at any moment of time it stores k elements
    // at max. This way after all iterations, min heap would store the last k elements of the sorted version of the array
    //and top would give us the kth largest element
    //O(NlogK) - for inserting N elements in the min heap of size k, O(1) space

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap=new PriorityQueue<>();
        for(int i:nums){
            minHeap.offer(i);
            if(minHeap.size()>k){
                minHeap.poll();
            }
        }
        return minHeap.peek();
    }

    //max sum combinations
    //https://www.interviewbit.com/problems/maximum-sum-combinations

    //brute (gives TLE) - find all possible combinations and insert them in a min heap such that it could carry only C elements.
    // This way, after going through all possible combinations, min heap would store the last C combinations (if the
    // combinations were written in a sorted manner) and this way we can return the top C combinations
    // O((N*M)logC) time, O(C) space where c is the number of combination sums ie (2^n1)*(2^n2) - if we consider the ans list

    public ArrayList<Integer> solve1(ArrayList<Integer> A, ArrayList<Integer> B, int C) {
        ArrayList<Integer> ans=new ArrayList<>();
        PriorityQueue<Integer> minHeap=new PriorityQueue<>();
        for(int i:A){
            for(int j:B){
                int sum=i+j;
                minHeap.offer(sum);
                if(minHeap.size()>C){
                    minHeap.poll();
                }
            }
        }
        while(!minHeap.isEmpty()){
            ans.add(0,minHeap.poll());
        }
        return ans;
    }

    //optimal - O(NlogN)

    //We basically sort both of the arrays. Now we know for sure that last two elements of both the arrays would give the
    // max sum when taken as a pair. Thus we know with certainty that this sum would be the largest one among the C maximum
    // valid sum combinations. so we add it to a max heap. Why max heap? So that we can get the top C elements in O(1) time.
    // Along with the sum, we also keep track of the indices of both the elements involved in the combinations so that we
    // can avoid duplicate pairs. Now since we have to deal with triads, I haven't used the Collections.reverseOrder() method in
    // the pq to make a max heap. Instead, I've modified the compareTo function of the triad class to sort the objects in
    // descending order of sums. We could've implemented the normal compareTo functionality (ie ascending order) and then
    // Collections.reverseOrder() would've done its job, but I've decided to do it this way by sorting the elements in descending
    // order within the implementation of the triad class itself. So initially after inserting the sum of the last two elements
    // in the pq along with their indices, we insert these indices into a set of pair as well which would make sure that
    // we've already taken this pair of indices. Now to find the remaining C-1 elements, we use this analogy - We know that
    // if we take the diagonal pairs of the recently used pairs (which are the last elements of both the arrays in this case),
    // ie one pair containing one element before the last element of arr1 and the last element of the arr2, say a and the
    // other pair containing the last element of arr1 and the element before the last element of arr2 - say b, then we know with
    // certainty that these 2 sums a & b would surely be greater than the sum formed by taking a pair of elements before both
    // of the recently used elements ie the element before last of arr1 and the element before last of arr2. for eg: consider arr1
    // as 1236 and arr2 as 2789. now a=9+3=12 and b=6+8=14 would surely be greater than c=8+3=11. this is because if we
    // take 6 here instead of 3 or 9 instead of 8 then the sum would surely increase as the arrays are sorted. Thus, these
    // are the next possible set of candidates and, therefore we insert both of these diagonals in the max heap. this makes sure the pair with the max sum always remains at the top. we do this
    // till c>0, retrieving the top of the max heap in each iteration and storing it in the ans, and then we check the
    // possible pair of diagonals that can be obtained from this top. Now as we can see in the prev eq, b=6+8=14 was the
    // next possible candidate and thus in the next iteration it will be retrieved as the top. Now when we try to find
    // diagonals from the pair 6 and 8, we see that the possible candidates are 6 and the element before 8 ie 7 and 8
    // and the element before 6 ie 3. We insert these pairs into the max heap after making sure that they have not been
    // taken before and the same process is repeated for further iterations till we reach C=0. We can see that the pair
    // 3 & 8 ie c as discussed before was automatically included in the max heap by being the possible diagonal from 6 and
    // 8. Thus, we didn't have to explicitly put c in the max heap, and it was taken care of through the diagonal analogy.

    //some important things wrt implementation of this problem in java:

    //to use a priority queue which stores a pair or a triad, the pair class should implement the comparable interface and
    // the compareTo method should be overriden so that the pq knows according to which parameter it should sort the array
    // and in which order (ascending or descending). If we override this functions to sort the pairs in descending order,
    // while declaring the pq, we don't explicitly have to mention Collections.reverseOrder() as it will be implemented
    // as a max heap be default as we modified the compare to function to sort the elements in descending manner.

    //also for the set to store a pair, we have to override the equals function in the pair class so that it returns true
    // only when both of the elements of the pair match with the other pair. Also we modify the hashcode function which
    // basically calculates the index value of the pair

    public ArrayList<Integer> solve(ArrayList<Integer> A, ArrayList<Integer> B, int C) {
        Collections.sort(A);
        Collections.sort(B);
        ArrayList<Integer> ans=new ArrayList<>();
        int n=A.size();
        int m=B.size();
        PriorityQueue<Triad> pairSum=new PriorityQueue<>();
        pairSum.add(new Triad(A.get(n-1)+B.get(m-1),n-1,m-1)); //inserting the last elements of both
        // sorted arrays into the max heap. we track the indices of all combinations to avoid duplicates
        Set<Pair> set=new HashSet<>();
        set.add(new Pair(n-1,m-1));
        while(C>0){
            Triad t=pairSum.poll();
            ans.add(t.first);
            int l=t.second;
            int r=t.third;
            if(l>0&&r>0&&!set.contains(new Pair(l-1,r))){
                pairSum.offer(new Triad(A.get(l-1)+B.get(r),l-1,r));
                set.add(new Pair(l-1,r));
            }
            if(l>0&&r>0&&!set.contains(new Pair(l,r-1))){
                pairSum.offer(new Triad(A.get(l)+B.get(r-1),l,r-1));
                set.add(new Pair(l,r-1));
            }
            C--;
        }
        return ans;
    }
    class Pair{
        int first;
        int second;
        public Pair(int first, int second){
            this.first=first;
            this.second=second;
        }
        @Override
        public boolean equals(Object o) {
            Pair obj=(Pair)o;
            return (first == obj.first && second == obj.second); //comparing both the first and second indices to check
            // whether a combination exists in the set or not
        }
        //In Java, the hashCode() method is used to generate a unique integer value (hash code) for an object. This value
        // is typically used in hash-based data structures like HashSet, HashMap, and Hashtable to determine the object's
        // bucket or index for efficient storage and retrieval.

        //The hashCode() method uses the Objects.hash() method, which internally performs the following steps:
        //a. It converts each parameter to its hash code by calling the hashCode() method on each parameter. If the parameter is null, it assigns a default hash code of 0.
        //b. It combines the hash codes of all the parameters using a bitwise exclusive OR (XOR) operation.
        @Override public int hashCode()
        {
            return Objects.hash(first,second); //this function calls the hashCode() functions on each parameter and combines
            // the hashcodes of all parameters using bitwise OR or XOR operation
        }
    }
    class Triad implements Comparable<Triad>{
        int first;
        int second;
        int third;
        public Triad(int first, int second, int third){
            this.first=first;
            this.second=second;
            this.third=third;
        }

        @Override
        public int compareTo(Triad o) { // here o.first<this.first will give a positive value and thus first would be
            // placed before o.first, resulting in descending order
            return Integer.compare(o.first,this.first);
        }
    }


    //find median from data stream
    //https://leetcode.com/problems/find-median-from-data-stream/

    //brute would be to sort the data as it appears: can use insertion sort for that. we basically keep a pointer at the
    // last index and move it till we find an element < num, then we insert num at i+1 position. since we're using a list
    // to store the elements, adding an element at i+1 th position would automatically move all the elements to the right.
    // This way the DS would be sorted at all times and at any time, the median can be obtained through conventional formulas.

    //takes O(N2) time (for 1st insertion, loop runs for 0 times as the size of list has 0 elements initially, for 2nd insertion,
    // it runs for 1 time, as the size of the DS is now 1, then for 3rd insertion, it runs for 2 times and so on. So for
    // N insertions, we can accumulate the total time as - 0 + 1 + 2 + 3 + upto N times)=N*(N+1)/2=O(N2). Also, we're shifting
    // elements to the right to accommodate the new element at a specific index would also take O(N) time in the worst case
    //O(N)space to store N inserted elements

    class medianFinder {
        ArrayList<Integer> list=new ArrayList<>();
        public medianFinder() {
            this.list=new ArrayList<>();
        }

        public void addNum(int num) {
            int i=list.size()-1;
            while(i>=0&&list.get(i)>num){ //shifting all elements > num to right
                i--;
            }
            list.add(i+1,num);  //adding num immediately after the element>=num
        }
        public double findMedian() {
            int n=list.size();
            if(n%2==0){
                return (list.get((n/2)-1)+list.get(n/2))/2.0;
            }else{
                return list.get(n/2);
            }
        }
    }

    //optimal - O(NlogN) - we can divide the elements coming from the stream across a max and min heap. This way,
    // we wouldn't have to sort the incoming elements explicitly, and we can just use the left of the median (top of max heap)
    // and the right of the median (top of min heap) to find the median. The max heap would store all the elements on the l
    // eft of median (so that the top is left of median) and similarly the min heap would store all the elements on the right
    // of median. In case the total elements up till now are odd, we will store the larger amount of elements in max heap and
    // the median would thus be its top.
    class MedianFinder {
        PriorityQueue<Integer> maxHeap;
        PriorityQueue<Integer> minHeap;
        public MedianFinder() {
            this.maxHeap=new PriorityQueue<>(Collections.reverseOrder());
            this.minHeap=new PriorityQueue<>();
        }
        public void addNum(int num) {
            if(!maxHeap.isEmpty()&&maxHeap.peek()>=num){ //num belongs to left half
                maxHeap.offer(num);
            }else{ //num belongs to right half
                minHeap.offer(num);
            }
            //we make sure that minHeap never takes more elements than maxheap and maxheap is only allowed to store one
            // element extra. this ensures that when there are even elements, both store equal number of elements and
            // when there are odd, max heap stores one extra.
            if(maxHeap.size()>minHeap.size()+1){ //we try to store at max 1 element extra in maxHeap than minHeap (to accommodate median
                // in case of odd number of elements). ie we're explictly allowing it to store 1 element extra in case of odd length
                // If the size exceeds that, we poll

                minHeap.offer(maxHeap.poll());
            }
            if(minHeap.size()>maxHeap.size()){ //ie right half has more elements than left half, meaning we're not at the center
                maxHeap.offer(minHeap.poll());
            }
        }
        public double findMedian() {
            if(maxHeap.size()>minHeap.size()){ //odd length, thus maxHeap stores 1 element extra
                return maxHeap.peek();
            }
            return (maxHeap.peek()+minHeap.peek())/2.0;
        }
    }

    //merge k sorted arrays
    //brute - merge all the arrays in the given order, storing them in a DS and then sort that DS
    //O((N*K)log(N*K)) - assuming each array is of size N

    //optimal
    //inserting first element of all arrays into the pq, then iterating over the pq and retrieving the top, adding it to
    // the ans and inserting its next into the pq if it exists, then repeating this till pq is not empty
    public static ArrayList<Integer> mergeKArrays(int[][] arr,int K)
    {
        PriorityQueue<ListNode> pq=new PriorityQueue<>();
        ArrayList<Integer> ans=new ArrayList<>();
        for(int i=0;i<arr.length;i++){
            pq.offer(new ListNode(arr[i][0],i,0));
        }
        while(pq.size()>0){
            ListNode tp=pq.poll();
            ans.add(tp.first);
            int i=tp.second;
            int j=tp.third;
            if(j+1<arr[i].length){ //checking if next element of current element of this array exists
                pq.offer(new ListNode(arr[i][j+1],i,j+1));
            }
        }
        return ans;
    }

    static class ListNode implements Comparable<ListNode>{
        int first;
        int second;
        int third;
        ListNode next;
        public ListNode(){}
        public ListNode(int first, int second, int third){
            this.first=first;
            this.second=second;
            this.third=third;
        }
        public ListNode(int first, ListNode next){
            this.first=first;
            this.next=next;
        }
        @Override
        public int compareTo(ListNode o) {
            return Integer.compare(this.first,o.first);
        }
    }

    //merge k sorted lists
    //https://leetcode.com/problems/merge-k-sorted-lists
    //O(NLogN) - for inserting N nodes into the pq
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq=new PriorityQueue<>((a,b)->a.first-b.first);
        ListNode ans=new ListNode();
        ListNode temp=ans;
        for(ListNode node:lists){
            if(node!=null)
                pq.offer(node);
        }
        while(pq.size()>0){
            ListNode l=pq.poll();
            temp.next=l;
            if(l.next!=null){
                pq.offer(l.next);
            }
            temp=temp.next; //stores the ans
        }
        return ans.next;
    }

    //k most frequent elements
    //https://leetcode.com/problems/top-k-frequent-elements/

    //brute - mark frequency of all elements in a map and then iterate over that map to store the element-frequency as pairs
    // in a list, then sort that list. sorting is done in decreasing order of frequencies so that we can get the first k
    // elements as top frequent. sorting in the pair class is implemented such that if the frequencies of two elements are
    // equal, then we compare their element values and accordingly sort them in descending order

    //if D is the number of distinct elements in the array, then inserting D elements into the hashmap takes O(D) time and
    // sorting the list of D distinct pairs takes O(DlogD) time thus overall time complexity is O(logD for hashmap+D+DlogD) and space is O(D)

    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer,Integer> map=new HashMap<>();
        List<Pair3> list=new ArrayList<>();
        //logD
        for(int i:nums){
            map.put(i,map.getOrDefault(i,0)+1);
        }
        for(int key:map.keySet()){
            list.add(new Pair3(map.get(key),key));
        }
        Collections.sort(list);
        int[] ans=new int[k];
        for(int i=0;i<k;i++){
            ans[i]=list.get(i).second;
        }
        return ans;
    }
    class Pair2 implements Comparable<Pair3>{
        int first;
        int second;
        public Pair2(int first, int second){
            this.first=first;
            this.second=second;
        }

        @Override
        public int compareTo(Pair3 o) {
            if(o.first==this.first){
                return Integer.compare(o.second,this.second); //comparing the elements themselves if their frequency is equal
            }
            return Integer.compare(o.first,this.first); //sorting elements in decreasing order of their frequencies
        }
    }

    //slight better approach - we can follow a similar approach as above to store the frequency of the elements but instead of using a list,
    // we can use a min heap to store the frequency-element pair such that while iterating the map to push elements into
    // the heap, at max the size of the heap would be k and whenever it exceeds k, we basically pop the top. This way at
    // the end, the min heap stores the top k frequent elements. Now since we're using min heap, we have to sort the pairs
    // inside the pq in ascending order and not descending as we did in prev ques.

    //O(logD hashmap (retrieval, insertion) + D for inserting D elements into map + DlogK for inserting D distinct elements
    // in pq of size K + KlogK for retrieving k elements from the pq) , O(D+K) sc

    //explanation for DlogK complexity: The complexity of the pq would vary till only the first k insertions and after
    // that it would become logk for each insertions. Thus overall time taken to insert D elements is considered as O(DlogK),
    // logK being the avg insertion time for the pq.

    public int[] topKFrequent1(int[] nums, int k) {
        HashMap<Integer,Integer> map=new HashMap<>();
        PriorityQueue<Pair3> pq=new PriorityQueue<Pair3>();
        for(int i:nums){
            map.put(i,map.getOrDefault(i,0)+1);
        }
        for(int key:map.keySet()){
            pq.offer(new Pair3(map.get(key),key));
            if(pq.size()>k){ //min heap size exceeds k, thus pop the top
                pq.poll();
            }
        }
        int[] ans=new int[k];
        for(int i=0;i<k;i++){
            ans[i]=pq.poll().second;
        }
        return ans;
    }
    class Pair3 implements Comparable<Pair3>{
        int first;
        int second;
        public Pair3(int first, int second){
            this.first=first;
            this.second=second;
        }

        @Override
        public int compareTo(Pair3 o) {
            if(o.first==this.first){
                return Integer.compare(this.second,o.second); //comparing the elements themselves if their frequency is equal
            }
            return Integer.compare(this.first,o.first); //sorting elements in decreasing order of their frequencies
        }
    }
}
