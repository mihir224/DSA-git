public class HappyNumber {
    //https://leetcode.com/problems/happy-number/
    public boolean isHappy(int num){
        int slow=num;
        int fast=num;
        do{
            slow=findSquare(slow);
            fast=findSquare(findSquare(fast));
            if(slow==1) {
                return true;
            }
        }while(slow!=fast);
        return false;
    }
    public int findSquare(int num){
        int ans=0;
        while(num>0){
            int rem=num%10; //returns last digit of the number
            ans+=rem*rem;
            num/=10; //to find further digits of the number
        }
        return ans;
    }

}
