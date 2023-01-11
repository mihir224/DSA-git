public class GreatestCommonDivisor {
    public static int getGreatestCommonDivisor(int first, int second)
    {
        int min=first<second ? first:second; //check which one is min
        int num=0;
        if(first<10||second<10)
        {
            return -1;
        }
        for(int i=min;i>1;i--) //loop to find out the first common divisor(greatest)
        {
            if(first%i==0&&second%i==0)
            {
            return num=i; //to return highest divisor and break out of the loop
        }
        }
        return 1;//intentionally set to 1 for default 1

    }
}
