import java.util.Arrays;
import java.util.HashSet;

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
}
