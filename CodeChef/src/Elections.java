import java.util.Scanner;

public class Elections {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        {
            int T=scanner.nextInt();
            for(int i=0;i<T;i++)
            {
                int a=scanner.nextInt();
                int b=scanner.nextInt();
                int c=scanner.nextInt();
                if(a>50)
                {
                    System.out.println("A");
                }
                else if(b>50)
                {
                    System.out.println("B");

                }
                else if(c>50)
                {
                    System.out.println("C");
                }
                else System.out.println("NOTA");
            }
        }scanner.close();
    }
}
