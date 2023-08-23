import java.util.*;

public class Contest {
    //sum in a matrix
    //https://leetcode.com/problems/sum-in-a-matrix/

    //first we sort each col, and then we iterate over each col and for each col, we iterate over every row, find the
    // max among each row for that particular col and then add it to our score
    //O(N2)
    public int matrixSum(int[][] nums) {
        int score=0;
        for(int[] rows:nums){
            Arrays.sort(rows);
        }
        for(int col=0;col<nums[0].length;col++){
            int max=0;
            for(int row=0;row<nums.length;row++){
                max=Math.max(max,nums[row][col]);
            }
            score+=max;
        }
        return score;
    }


    //buy two chocolates
    //https://leetcode.com/contest/biweekly-contest-105/problems/buy-two-chocolates/
    public static int buyChoco(int[] prices, int money) {
        Arrays.sort(prices);
        int i = 0;
        int j = prices.length - 1;
        int ans = money;
        while (i < j) {
            int sum = prices[i] + prices[j];
            if (sum > money) {
                j--;
            } else {
                ans = money - sum;
                i++;
            }
        }
        return ans;
    }

    //distance travelled
    //https://leetcode.com/problems/total-distance-traveled/
    public int distanceTraveled(int mainTank, int additionalTank) {
        int initialDist = mainTank * 10;
        int extraDist=0;
        while((mainTank/5)>=1){ //mainTank/5 basically gives us an estimate of the number of instances at which 5 litres
            // of fuel would be consumed from the main tank. This value changes after each iteration whenever we add an
            // additional litre of fuel to the main tank
            mainTank-=5;
            if(additionalTank>0){
                extraDist+=10; //this accounts for the distance travelled with the additional 1 litre of fuel that is transferred to the main tank
                mainTank+=1;
                additionalTank--;

            }else{
                break;
            }

        }
        return initialDist+extraDist;
    }

    //construct longest new string
    //https://leetcode.com/contest/biweekly-contest-107/problems/construct-the-longest-new-string/
    //simple maths

    public int longestString(int x, int y, int z) {
        int count=0;
        if(x==y){
            return 2*(x+y+z);
        }
        if(x<y){
            while(x>0){
                count+=2;
                x--;
            }
        }
        else{
            while(y>0){
                count+=2;
                y--;
            }
        }
        count++;
        return 2*(z+count);
    }


    //longest even odd subarray with threshold
    //https://leetcode.com/contest/weekly-contest-352/problems/longest-even-odd-subarray-with-threshold/
    public int longestAlternatingSubarray(int[] nums, int threshold) {
        int len=0;
        int temp=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]%2==0&&nums[i]<=threshold){
                int l=i;
                int r=i+1;
                while(r<nums.length&&nums[r-1]%2!=nums[r]%2&&nums[r]<=threshold){
                    r++;
                }
                len=Math.max(len,r-l);
            }
        }
        return len;
    }


    //prime pairs with target sum
    //brute - using two sum to find x+y==n and then for every value of x and y checking if they're prime.
    //O(Nlog(sqrt(N))
    public List<List<Integer>> findPrimePairs(int n) {
        int x=2;
        int y=n;
        List<List<Integer>> ans=new ArrayList<>();
        while(x<=y){
            int sum=x+y;
            if(sum==n){
                if(isPrime(x)&&isPrime(y)){
                    List<Integer> list=new ArrayList<>();
                    list.add(x);
                    list.add(y);
                    ans.add(list);
                }
            }
            else if(sum<n){
                x++;
            }
            else{
                y--;
            }
        }
        return ans;
    }
    //if for N numbers we try to check whether they are prime or not, complexity would be O(N*sqrt(N)), thus we use the
    // sieve of eratosthenes
    public boolean isPrime(int a){
        if(a==0||a==1){
            return false;
        }
        for(int i=2;i<=Math.sqrt(a);i++){
            if(a%i==0){
                return false;
            }
        }
        return true;
    }

    //optimal - two sum + sieve of eratosthenes algo

    //we iterate till sqrt(N) only because marking the factors for all numbers from 2 to sqrt(N) would take care of the
    // rest of the numbers
    public List<List<Integer>> findPrimePairs1(int n) {
        //applying sieve of eratosthenes
        boolean[] isPrime=new boolean[n+1];
        Arrays.fill(isPrime,true);
        isPrime[0]=isPrime[1]=false;
        for(int i=2;i<=Math.sqrt(n);i++){
            for(int j=2*i;j<=n;j+=i){ //marking all factors of i as false, incrementing j by i times in each iteration
                isPrime[j]=false;
            }
        }
        int x=2;
        int y=n;
        List<List<Integer>> ans=new ArrayList<>();
        while(x<=y){
            int sum=x+y;
            if (sum == n && isPrime[x] && isPrime[y]) {
                List<Integer> list = new ArrayList<>();
                list.add(x);
                list.add(y);
                ans.add(list);
            }
            if(sum<=n){
                x++;
            }
            else{
                y--;
            }
        }
        return ans;
    }

    //weekly 347

    //difference of number of distinct values on diagonals
    //https://leetcode.com/contest/weekly-contest-347/problems/difference-of-number-of-distinct-values-on-diagonals/

    //brute - for each node, find top left and bottom right diagonals and store them in a set (to avoid duplicates), and
    // then save the value of ans[i][j] as set1.size()-set2.size()
    public int[][] differenceOfDistinctValues(int[][] grid) {
        int m=grid.length;
        int n=grid[0].length;
        int[][] ans=new int[m][n];
        //initialisation
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                Set<Integer> set1=new HashSet<>();
                Set<Integer> set2=new HashSet<>();
                int x=i;
                int y=j;
                while(x>0&&y>0){
                    set1.add(grid[x-1][y-1]);
                    x--;
                    y--;
                }
                x=i;
                y=j;
                while(x<m-1&&y<n-1){
                    set2.add(grid[x+1][y+1]);
                    x++;
                    y++;
                }
                ans[i][j]=Math.abs(set1.size()-set2.size());
            }
        }
        return ans;
    }

    //min cost to make all characters equal
    //https://leetcode.com/contest/weekly-contest-347/problems/minimum-cost-to-make-all-characters-equal/

    //we can either flip all the characters from 0 till i-1 (prefix) or from i till n-1 (suffix). so for each element,
    // we calculate the cost of inverting the prefix and suffix and we take whatever is the min from that and add it to
    // our cost
    //we're making sure that for any index i, all the elements till index i-1 have been flipped with min cost and we try
    // flipping the rest also with min cost
    public long minimumCost(String s) {
        int n=s.length();
        long cost=0;
        for(int i=1;i<s.length();i++){
            if(s.charAt(i)!=s.charAt(i-1)){
                int min=Math.min(i,n-i); //since i is 1 element ahead of prefix's last
                cost+=min;
            }
        }
        return cost;
    }

    //maximum strictly increasing cells in a matrix
    //https://www.youtube.com/watch?v=oBa7Y8ZyBWo
    class Pair{
        int row;
        int col;
        public Pair(int row, int col){
            this.row = row;
            this.col = col;
        }
    }
    public int maxIncreasingCells(int[][] mat) {
        int m=mat.length;
        int n=mat[0].length;
        Set<Integer> set=new TreeSet<>(); //tree set helps us store elements of the set in sorted order
        HashMap<Integer,List<Pair>> map=new HashMap<>(); //an element with the same value can be at multiple positions
        // thus we store all of its positions so that its max value can be marked in all the rows and cols it is present in
        int[] maxRow=new int[m]; //to store max ans in a particular row
        int[] maxCol=new int[n]; //to store the max ans in a particular col
        int[][] temp=new int[m][n];
        int ans=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(!map.containsKey(-1*mat[i][j])){
                    map.put(-1*mat[i][j],new ArrayList<>());
                }
                map.get(-1*mat[i][j]).add(new Pair(i,j));
                set.add(-1*mat[i][j]);
            }
        }
        for(Integer el:set){
            for(Pair p:map.get(el)){
                int row=p.row;
                int col=p.col;
                temp[row][col]=Math.max(maxRow[row],maxCol[col])+1; //finding the max no. of cells we can visit after the
                // current element from any element in the row and col of the current element and taking the max of that,
                // and adding 1 to it (current cell)
            }
            for(Pair p:map.get(el)){
                int row=p.row;
                int col=p.col;
                maxRow[row]=Math.max(maxRow[row],temp[row][col]); //we take the max here because there might be a case
                // where 2 or more elments with the same value lie in the same row or col and in that case temp[row][col]
                // would store different values for each of them so we take the max from current value of temp[row][col]
                // and prev values of maxRow and maxCol for the same element
                maxCol[col]=Math.max(maxCol[col],temp[row][col]);
            }
        }
        for(int i:maxRow){
            ans=Math.max(ans,i);
        }
        for(int i:maxCol){
            ans=Math.max(ans,i);
        }
        return ans;
    }

    //weekly 348

    //minimize string length
    //https://leetcode.com/contest/weekly-contest-348/problems/minimize-string-length/
    public int minimizedStringLength(String s) {
        HashSet<Character> set=new HashSet<>();
        for(char ch:s.toCharArray()){
            set.add(ch);
        }
        return set.size();
    }

    //semi order permutation
    //https://leetcode.com/problems/semi-ordered-permutation/

    //O(N) solution
    public int semiOrderedPermutation(int[] nums) {
        int count=0;
        int index=-1;
        if(nums[0]!=1){ //if 1 not at correct position
            for(int i=0;i<nums.length;i++){ //finding current position of 1
                if(nums[i]==1){
                    index=i;
                }
            }
            for(int i=index;i>0;i--){ //shifting all elements right till we reach 1
                nums[i]=nums[i-1];
                count++;
            }
            nums[0]=1; //putting 1 at its correct position
        }
        if(nums[nums.length-1]!=nums.length){ //n not at correct position
            for(int i=0;i<nums.length;i++){
                if(nums[i]==nums.length){ //finding correct index of n
                    index=i;
                }
            }
            for(int i=index;i<nums.length-1;i++){ //shifting all elements left from index where n is till we reach last element
                nums[i]=nums[i+1];
                count++;
            }
        }
        return count;
    }

    //sum of matrix after queries
    //https://leetcode.com/problems/sum-of-matrix-after-queries

    //brute - will give MLE
    public long matrixSumQueries(int n, int[][] queries) {
        long sum=0;
        int[][] matrix=new int[n][n];
        for(int[] query:queries){
            if(query[0]==0){
                for(int i=0;i<n;i++){
                    matrix[query[1]][i]=query[2];
                }
            }
            if(query[0]==1){
                for(int i=0;i<n;i++){
                    matrix[i][query[1]]=query[2];
                }
            }
        }
        for(int[] arr:matrix){
            for(int i:arr){
                sum+=i;
            }
        }
        return sum;
    }


    //optimal - O(queries)
    //intuition - https://www.youtube.com/watch?v=-n6Qi6BHKn8

    public long matrixSumQueries1(int n, int[][] queries) {
        boolean[] rowVis=new boolean[n];
        boolean[] colVis=new boolean[n];
        int row=n;
        int col=n;
        long sum=0;
        for(int i=queries.length-1;i>=0;i--){
            int type=queries[i][0];
            int index=queries[i][1];
            long val=(long)queries[i][2];
            if(type==0&&!rowVis[index]){ //filling the row only if it was not previously filled
                rowVis[index]=true;
                sum+=(long)col*val;
                row--;
            }
            else if(type==1&&!colVis[index]){
                colVis[index]=true;
                sum+=(long)row*val;
                col--;
            }
        }
        return sum;
    }

    //count of integers
    //brute - O(N*len of X) - gives TLE (constraints are huge - num1 & num2 can be at max 10^22 whereas the int range in
    // java is only -2^31 to 2^31-1 and also a long is -2^63 to 2^63-1 and thus these cannot be used to store x)
    public int count(String num1, String num2, int min_sum, int max_sum) {
        long n1=Long.parseLong(num1);
        long n2=Long.parseLong(num2);
        int count=0;
        for(long i=n1;i<=n2;i++){
            int sum=digit_sum(i);
            if(sum>=min_sum&&sum<=max_sum){
                count++;
            }
        }
        return count%(int)(Math.pow(10,9)+7);
    }
    public int digit_sum(long num){
        int sum=0;
        while(num>0){
            sum+=num%10;
            num/=10;
        }
        return sum;
    }

    //bi-weekly 106

    //find the longest semi repetitive substring
    //https://leetcode.com/contest/biweekly-contest-106/problems/find-the-longest-semi-repetitive-substring/

    //brute - find all possible substrings, return the longest one with not more than one consecutive pair that has same
    // characters

    //O(N2) time, O(1) space
    public int longestSemiRepetitiveSubstring(String s) {
        if(s.length()==1){
            return s.length();
        }
        String str="";
        int count=0;
        int ans=Integer.MIN_VALUE;
        for(int i=0;i<s.length();i++){
            str+=s.charAt(i);
            for(int j=i+1;j<s.length();j++){
                str+=s.charAt(j);
                if(s.charAt(j)==s.charAt(j-1)) {
                    count++;
                }
                if(count>1){
                    count=0;
                    break;
                }
                ans=Math.max(ans,str.length());
            }
            str="";
        }
        return ans;
    }


    //optimal - O(N), while loop would take almost O(N) time in the worst case but there will only be certain instances
    // (only few) when the while loop is called and thus the avg tc is O(N)

    //left is always pointing to the first char of the current substring and right is always pointing to the last char of
    // the current substring
    public int longestSemiRepetitiveSubstring1(String s) {
        int ans=1;
        int count=0;
        int left=0;
        for(int right=1;right<s.length();right++){
            if(s.charAt(right)==s.charAt(right-1)){
                count++;
            }
            while(count>1){ //moving till the first pair of equal characters and removing the first item from that pair
                // to make the rest of the string a valid one
                if(right>left&&s.charAt(left)==s.charAt(left+1)){
                    count--;
                }
                left++; //moving left till we find the additional pairs and move past them
            }
            ans=Math.max(ans,right-left+1);
        }
        return ans;
    }

    //movement of robots - really nice question
    //https://www.youtube.com/watch?v=fxhviazRNsE

    //basically the trick is that, suppose a robot is at -2 and another one at 0. After 1 unit of time, they both collide.
    // Now supposedly according to the question, we're told that after collision they change their directions, and thus
    // after 2 units of time of the command, they went back to their original positions and now they tend to move in
    // opposite directions. Now we don't know which robot is which ie both are same and don't have any character. so if
    // we just assume that there was no collision and the robots went where they were supposed to go initially ie the
    // first one right and the second one left, after two units of time, the first one would be at 0 going right and the
    // second one would be at -2 going left. Thus their collision doesn't concern us. after d units of time the robots
    // will be where they were supposed to be irrespective of which robot it was supposed to be at which place. thus we
    // basically add d to their current position if they were moving right initially and subtract it otherwise (since
    // after 1 unit of time they move 1 unit of distance) and then we can basically use the prefix sum trick to find
    // sum of distance between all pairs after we have obtained their final positions after d seconds
    public int sumDistance(int[] nums, String s, int d) {
        int mod=(int)Math.pow(10,9)+7;
        long prefSum=0;
        long sum=0;
        for(int i=0;i<nums.length;i++){
            if(s.charAt(i)=='L'){
                nums[i]-=d;
            }
            else{
                nums[i]+=d;
            }
        }
        Arrays.sort(nums);
        for(int i=0;i<nums.length;i++){
            sum=(sum+(i*(long)nums[i])-prefSum)%mod;
            prefSum+=nums[i];
        }
        return (int)sum;
    }

    //weekly - 349
    //

    //neither max nor min
    //https://leetcode.com/contest/weekly-contest-349/problems/neither-minimum-nor-maximum/
    //O(1) soln
    public int findNonMinOrMax(int[] nums) {
        if(nums.length<=2){
            return -1;
        }
        int max=Math.max(nums[0],nums[1]);
        int min=Math.min(nums[0],nums[1]);
        if(nums[2]<max&&nums[2]>min){
            return nums[2];
        }
        if(nums[2]>max){
            return max;
        }
        return min;
    }

    //lexicographically smaller string
    //great problem
    //https://leetcode.com/contest/weekly-contest-349/problems/lexicographically-smallest-string-after-substring-operation/

    //https://www.youtube.com/watch?v=O5xno566J0c
    //O(N) soln
    public String smallestString(String s) {
        boolean flag=false;
        StringBuilder ans=new StringBuilder();
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)!='a'&&!flag){
                flag=true;
                while(i<s.length()){
                    char ch=s.charAt(i);
                    if(ch!='a'){
                        ch--;
                        ans.append(ch);
                    }else{ //char is 'a'
                        i--;
                        break;
                    }
                    i++;
                }
            }
            else{ //either char is 'a' or we have already taken a substring before, transforming its characrters and thus marking the flag as true
                ans.append(s.charAt(i)); //appending all the remaining characters to the ans
            }
        }
        if(!flag){ //ie every character is a in the given string. now we're bound to perform the given operation exactly once ie we cannot avoid it and for a string with every char as 'a', the lexicographically smallest one would be the one obtained after transforming the last character
            ans=new StringBuilder();
            for(int i=0;i<s.length()-1;i++){
                ans.append(s.charAt(i));
            }
            ans.append('z');
        }
        return ans.toString();
    }

    //biweekly 108

    //longest alternating subarray
    //https://leetcode.com/contest/biweekly-contest-108/problems/longest-alternating-subarray/

    //basically find all possible valid subarrays, calculate their length and break off whenever there's discontinuity
    public int alternatingSubarray(int[] nums) {
        int max=1;
        for(int i=0;i<nums.length;i++){
            int len=1;
            for(int j=i+1;j<nums.length;j++){
                //we write separate else statements for both conditions because there might be a case that the first statement is not true but the other one is and that would contribute to the len even though the substring would not be valid in that case so whenever we find discontinuity, we break off
                if(nums[j]==nums[i]+1){
                    len++;
                }
                else{ //found discontinuity
                    break;
                }
                if(j+1<nums.length&&nums[++j]==nums[i]){
                    len++;
                }
                else{ //found discontinuity
                    break;
                }
            }
            max=Math.max(len,max);
        }
        return max==1?-1:max;
    }

    //weekly 353

    //find max achievable number
    //https://leetcode.com/contest/weekly-contest-353/problems/find-the-maximum-achievable-number/

    //we have two options - either we take a number less than num or we take a num greater than num, there will always
    // be an achievable number in both cases but the larger one would always be the one greater than num. thus we always
    // take the candidate greater than num. now in order to make these two numbers equal, we increase num and decrease
    // x. after t operations, whatever is the value of num, that is what we want to make x by decreasing it t times. so
    // x can be obtained using eqn: x-t=whatever we obtain after increasing num t times.

    public int theMaximumAchievableX(int num, int t) {
        for(int i=0;i<t;i++){
            num++;
        }
        return (num+t);
    }

    //max number of jumps to reach the last index
    //https://leetcode.com/contest/weekly-contest-353/problems/maximum-number-of-jumps-to-reach-the-last-index/

    //cannot use brute force here. suppose we start from 1st char and then try to jump to any of the characters on the
    // right and if we're able to jump to a valid one, we can use a pointer that will point to that valid number so that
    // we can further check for valid numbers on the right comparing it with this left pointer. but the problem in this
    // approach is that if we're unable to reach the end with these valid numbers, we cannot try jumping to other elements
    // than where j started from and i would simply be incremented further. thus we follow a recursive approach

    public int maximumJumps(int[] nums, int target) {
        int n=nums.length;
        int[] dp=new int[n];
        Arrays.fill(dp,-1);
        int ans=helper(0,nums,target,dp);
        return ans<0?-1:ans;
    }
    public int helper(int index,int[] nums,int target,int[] dp){
        if(index==nums.length-1){
            return 0;
        }
        if(dp[index]!=-1){
            return dp[index];
        }
        int ans=Integer.MIN_VALUE;
        for(int i=index+1;i<nums.length;i++){
            if(Math.abs(nums[i]-nums[index])<=target){
                ans=Math.max(ans,1+helper(i,nums,target,dp));
            }
        }
        return dp[index]=ans;
    }


    //bi-weekly 109

    //check if array is good

    //just till second last no. if arr[i]+1==arr[i+1] and if not, return false. otherwise at the end just compare the last
    // with second last and return the result accordingly

    //edge case - if there's only one el, return false
    

    //sort vowels in a string
    //https://leetcode.com/problems/sort-vowels-in-a-string/

    //store all vowels of the string in a list, sort it and then iterate over the string to and place the replace the vowels
    // with sorted ones
    //time - O(NlogN) - if there are N vowels + O(2N), space - O(N) for N vowels
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

    //visit array positions to maximize score
    //https://leetcode.com/contest/biweekly-contest-109/problems/visit-array-positions-to-maximize-score

    //intuition - if we come from an odd num to even, or even to odd, subtract penalty after adding current num in score.

    //we can keep track of the max sum up till now if we came from an even num and max sum up till now if we came from
    // an odd num and return the max of that

    //maxEven and maxOdd help us keep track of the max sum accumulated up till last even score and the last odd score

    public long maxScore(int[] nums, int x) {
        long max=0;
        long maxEven=0;
        long maxOdd=0;
        if(nums[0]%2==0){
            maxEven=nums[0];
            //IMP EDGE CASE
            maxOdd=nums[0]-x; //just assuming that we came from an odd to this even which is nums[0] thus deducting x.
            // this was necessary since we always have to consider nums[0]. if we'd had taken maxOdd as 0 then that
            // would've eliminated any contribution of nums[0] in the score. the intuition behind this is that we have
            // to take nums[0] one way or the other. So initially when we're considering that oddEnd is 0, it means
            // that max sum when we came from an odd element is 0. Now from this odd element we're bound to go to
            // nums[0] because nums[0] has to be included always. We cannot directly jump to any other element. We have
            // to go to other element through nums[0] So if we do that, we have to deduct the penalty of x if nums[0] is
            // even and update oddEnd accordingly
        }
        else{
            maxOdd=nums[0];
            maxEven=nums[0]-x;
        }
        for(int i=1;i<nums.length;i++){
            if(nums[i]%2==0){
                maxEven=Math.max(maxEven+nums[i],maxOdd+nums[i]-x); //num is even, so updating maxEven by adding max of (nums[i] to max we got coming from an even no and max we got from an odd no. (deducting x since cmng from odd to even))
            }
            else{
                maxOdd=Math.max(maxOdd+nums[i],maxEven+nums[i]-x);
            }
        }
        max=Math.max(maxEven,maxOdd);
        return max;
    }

    //ways to express an integer as a sum of powers

    //a typical recursion problem -  for every unique integer such that the integer when raised to the power of x, along
    // with addition with some other unique digits which themselves are also raised to power of x sum up to give the
    // given n, we have the option to consider it in forming the sum, or not consider it at all. In both cases, once
    // the element has been processed, we do not process it again and move further. If, with a combination of some
    // unique integers we're able to form the given n by raising them to the power of x, we return 1. this will happen
    // when after taking these elements and reducing n in each iteration, n would eventually become 0. that is when we
    // would know that the set of UI that we took were valid and we return 1. otherwise we return 0 (cases when n
    // became <0 or when any integer's power of x became > n). also to memoize our ans, we use a hashmap here. the keys
    // obv being the elements that are changing in each iteration ie i and n

    private static int MOD=1000000007;
    public int numberOfWays(int n, int x) {
        return helper(1,n,x,new HashMap<>())%MOD;
    }
    public int helper(int i, int num, int x,HashMap<String,Integer> map){
        //bc
        if(num==0){ //num eventually became zero because we ended up taking such integers which when raised to the power
            // of x and summed up, were equal to the given num
            return 1;
        }
        if(Math.pow(i,x)>num){
            return 0;
        }
        String key=num + " " + i;
        if(map.containsKey(key)){
            return map.get(key);
        }
        int ans=helper(i+1,num-(int)Math.pow(i,x),x,map)%MOD+helper(i+1,num,x,map)%MOD;
        map.put(key,ans);
        return ans;
    }


    //weekly 355

    //split string by separator
    //https://leetcode.com/contest/weekly-contest-355/problems/split-strings-by-separator/
    public List<String> splitWordsBySeparator(List<String> words, char separator) {
        List<String> list=new ArrayList<>();
        for(String word:words){
            String[] str=word.split("["+separator+"]"); //this will make sure that everything inside separator is
            // treated as a normal character and not a special character
            for(String st:str){
                if(st.length()>0){ //because after splitting, arr can contain empty strings
                    list.add(st);
                }
            }
        }
        return list;
    }

    //bi weekly 111
    //count pairs with sum<target
    //https://leetcode.com/problems/count-pairs-whose-sum-is-less-than-target

    //optimal - using two pointers

    public int countPairs(List<Integer> nums, int target) {
        int left=0;
        int right=nums.size()-1;
        Collections.sort(nums);
        int count=0;
        while(left<right){
            if(nums.get(left)+nums.get(right)<target){
                count+=right-left;
                left++;
            }
            else{
                right--;
            }
        }
        return count;
    }
}
