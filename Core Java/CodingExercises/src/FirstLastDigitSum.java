public class FirstLastDigitSum {
    public static int sumFirstAndLastDigit(int number)
    {
        int sum = 0;
        int n=number;
        int mod=number%10;
        if(n<0)
        {
            return -1;
        }
        while(number>=10)
        {
            number=number/10;
        }
        sum=number+mod;
        return sum;
    }
}

