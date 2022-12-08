import java.util.Scanner;

public class Salon {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        {
            int t=scanner.nextInt();
            {
                for(int i=0;i<t;i++)
                {
                    int n=scanner.nextInt();
                    int m=scanner.nextInt();
                    System.out.println(n*m);
                }
            }
        }scanner.close();
    }
}
