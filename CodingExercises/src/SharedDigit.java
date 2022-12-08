public class SharedDigit {
    public static boolean hasSharedDigit(int num1, int num2) {
        if (num1 < 10 || num1 > 99 || num2 < 10 || num2 > 99) {
            return false;
        } else {
            int mod1 = num1 % 10; //to find the right digit
           int mod2 = num2 % 10;
            int div1 = num1 / 10; //to find the left digit
            int div2 = num2 / 10;
            if (mod1 == mod2 || div1==div2){
                return true;
            }
            else if(mod1==div2 || mod2==div1)
            {
                return true;
        }
            else return false;
    }
}}
