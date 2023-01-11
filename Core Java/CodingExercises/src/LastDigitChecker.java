public class LastDigitChecker {
    public static boolean hasSameLastDigit(int num1, int num2, int num3)
    {
        if(num1<10||num1>1000||num2<10||num2>1000||num3<10||num3>1000)
        {
            return false;
        }
        else
        {
            int mod1=num1%10;
            int mod2=num2%10;
            int mod3=num3%10;
            if(mod1==mod2||mod2==mod3||mod1==mod3)
            {
                return true;
            }
            else if(mod1==mod2&&mod1==mod3)
            {
                return true;
            }
            else return false;
        }
    }
    public static boolean isValid(int num)
    {
        if(num<10||num>1000)
        {
            return false;
        }
        else return true;
    }
}
