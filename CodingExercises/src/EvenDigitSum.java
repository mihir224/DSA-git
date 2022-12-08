public class EvenDigitSum {
    public static int getEvenDigitSum(int number)
    {
        int sum=0;

        if(number<0)
        {
            return -1;
        }
        while(number>0)
        {
            int mod=number%10;
            if(mod%2==0){
            sum += mod;}
            number/=10;
        }
        return sum;
    }
}
