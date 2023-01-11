public class NumberPalindrome {
    public static boolean isPalindrome(int num)
    {
        int reverse=0;
        int mod;
        int number=num;
        while(number!=0)
        {
            mod=num%10;
            reverse=(reverse*10)+mod;
            num/=10;
        }
        if(number!=reverse)
        {
            return false;
        }
        return true;

    }
}
