public class NumberToWords {
    public static void numberToWords(int number)
    {
        if(number<0)
        {
            System.out.println("Invalid Value");
        }
        int reverseNum=reverse(number);
        for(int i=0;i<getDigitCount(number);i++)
        {
            int mod=reverseNum%10;
            switch (mod)
            {
                case 0:
                    System.out.println("Zero");
                    break;
                case 1:
                    System.out.println("One");
                    break;
                case 2:
                    System.out.println("Two");
                    break;
                case 3:
                    System.out.println("Three");
                    break;
                case 4:
                    System.out.println("Four");
                    break;
                case 5:
                    System.out.println("Five");
                    break;
                case 6:
                    System.out.println("Six");
                    break;
                case 7:
                    System.out.println("Seven");
                    break;
                case 8:
                    System.out.println("Eight");
                    break;
                case 9:
                    System.out.println("Nine");
                    break;
                default:
                    System.out.println("Invalid Value");
            }
            reverseNum/=10;
        }
    }
    public static int reverse(int num)
    {
        int reverse=0;
        while(num!=0)
        {
            int mod=num%10;
            reverse=reverse*10+mod;
            num/=10;
        }
        return reverse;

    }
    public static int getDigitCount(int number)
    {
        int count=0;
        if(number<0)
        {
            return -1;
        }
                do
                {
                    number/=10;
                    count++;
                }while(number!=0);
                return count;
    }
}

