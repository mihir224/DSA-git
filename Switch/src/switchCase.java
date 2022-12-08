public class switchCase {
    public static void main(String[] args) {
        int switchValue=2;
        switch(switchValue) //switch statement can be used with 4 primitive type: short, char, byte, int
        {
            case 1:
                System.out.println("VALUE IS 1");
                break; //breaks the flow if this case is executed and then executes the rest of the code outside the switch block
            case 2:
                System.out.println("VALUE IS 2");
                break;
            case 3: case 4: case 5: //grouping 3 or more cases together
                System.out.println("Value is either 3 or 4 or 5");
                System.out.println("Value is actually: " + switchValue);
                break;
            default:
                System.out.println("Value is neither 1, 2, 3 ,4, or 5");

        }
        //rest of the code
         }
}
