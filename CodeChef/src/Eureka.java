import java.util.Scanner;

public class Eureka {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        {
            int t=scanner.nextInt();
            {
                for(int i=0;i<t;i++)
                {
                    double n=scanner.nextDouble();
                    double a=0.143*n;
                    double b=Math.pow(a,n);
                    System.out.println(Math.round(b));
                }
            }
        }scanner.close();
    }
}
