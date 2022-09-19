//https://leetcode.com/problems/palindrome-number/
public class Palindrome {
    public static void main(String[] args) {
        System.out.println(isPalindrome(-121));
    }
    static boolean isPalindrome(int n){
        if(n<0){
            return false;
        }
        return n==rev(n);

    }
    static int rev(int n){
        int digits=(int)(Math.log10(n)+1);
        return func(n, digits);
    }
    static int func(int n, int digits){
        if(n%10==n){
            return n;
        }
        int rem=n%10;
        return rem*(int)Math.pow(10,digits-1)+func(n/10,digits-1);
    }
}
