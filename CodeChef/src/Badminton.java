import java.util.Scanner;

public class Badminton {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        {
            int t=scanner.nextInt();
            for(int i=0;i<t;i++)
            {
                int pts=scanner.nextInt();
                System.out.println((pts/2)+1);
            }
        }scanner.close();
    }
}
