import java.util.Scanner;

public class InputCalculator {
    public static void inputThenPrintSumAndAverage()
    {
        Scanner scanner=new Scanner(System.in);
        int sum=0;
        int count=0;
        int order=0;
        while(true)
        {
            boolean isAnInt=scanner.hasNextInt();
            if(isAnInt)
            {
                int myNum=scanner.nextInt();
                sum+=myNum;
                order=count+1;
                count++;


            }
            else
            {break;}
                scanner.nextLine();
        }

        System.out.println("SUM = " + sum + " AVG = " + Math.round((double)sum/order));
        scanner.close();
    }
}
