import java.util.Scanner;

public class Hostel {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        {
            int t=scanner.nextInt();
            for(int i=0;i<t;i++)
            {
                int x=scanner.nextInt();
                int y=scanner.nextInt();
                if(y==0)
                {
                    System.out.println(x);
                }
                else{
                    x=x+(y*2);
                    System.out.println(x);
                }
            }
        }scanner.close();
    }
}
